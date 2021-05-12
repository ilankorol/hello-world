package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class ServerStrategySolveSearchProblem implements IServerStrategy{

    public ServerStrategySolveSearchProblem() {
    }
    public static Solution FindSol(Maze m){
        String FilePath=System.getProperty("java.io.tmpdir");
        for(int i=0;i<m.toByteArray().length;i++)
        {
            FilePath=FilePath+m.toByteArray()[i];
        }
        File f=new File(FilePath);
        if(f.exists()){
            ArrayList<AState> SolutionPath=new ArrayList<>();
            try {
                String data="";
                int count=0;
                Scanner myReader = new Scanner(f);
                while (myReader.hasNextLine()) {
                    data =myReader.nextLine();
                    if(data.length()>5)
                    {
                        int row = Integer.parseInt(data.substring(1, data.indexOf(",")));
                        int col = Integer.parseInt(data.substring(data.indexOf(",") + 1, data.indexOf("}")));
                        int cost = Integer.parseInt(data.substring(data.indexOf("}") + 1, data.length()));
                        if(count==0) {
                            SolutionPath.add(new MazeState(null, new MazeNode(new Position(row, col)), cost));
                        }
                        else
                            SolutionPath.add(new MazeState(SolutionPath.get(count-1), new MazeNode(new Position(row, col)), cost));
                    }
                }
                Solution sol=new Solution(SolutionPath);
                myReader.close();
                return sol;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static void ApplySolutionToFile(Maze m,Solution sol){
        String FilePath=System.getProperty("java.io.tmpdir");
        for(int i=0;i<m.toByteArray().length;i++)
        {
            FilePath=FilePath+m.toByteArray()[i];
        }
        ArrayList<AState> solutionPath=sol.getSolutionPath();
        String Sol="{0,0}0"+"\n";
        for (int i = 0; i < solutionPath.size(); i++) {
            Sol=Sol+String.format("%s", solutionPath.get(i));
            Sol=Sol+solutionPath.get(i).getCost();
            if(i!=solutionPath.size()-1)
                Sol=Sol+"\n";
        }
        try {
            Files.writeString(Path.of(FilePath),Sol);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try(InputStream input=new FileInputStream((new File("resources/config.properties")).getAbsolutePath())){
            Properties prop=new Properties();
            prop.load(input);
            ISearchingAlgorithm searcher=null;
            String searcherName=prop.getProperty("mazeSearchingAlgorithm");
            ObjectInputStream fromClient=new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient=new ObjectOutputStream(outToClient);
            Maze maze=(Maze) fromClient.readObject();
            if(searcherName!=null) {
                if (searcherName.equalsIgnoreCase("bestfirstsearch"))
                    searcher = new BestFirstSearch();

                if (searcherName.equalsIgnoreCase("breadthfirstsearch"))
                    searcher = new BreadthFirstSearch();

                if (searcherName.equalsIgnoreCase("depthfirstsearch"))
                    searcher = new DepthFirstSearch();

                if(searcher==null)
                    System.err.println("Invalid searching algorithm name");
                if(searcher!=null)
                {
                    Solution sol=FindSol(maze);
                    if(sol==null){
                        sol = searcher.solve(new SearchableMaze(maze));
                        ApplySolutionToFile(maze,sol);
                    }
                    toClient.writeObject(sol);
                    toClient.flush();
                }
            }
            else
            {
                System.out.println("Couldn't find algorithm name");
            }
            fromClient.close();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
