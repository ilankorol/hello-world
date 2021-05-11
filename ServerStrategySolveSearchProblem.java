package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.util.Properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy{

    public ServerStrategySolveSearchProblem() {
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
                    Solution sol = searcher.solve(new SearchableMaze(maze));
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
