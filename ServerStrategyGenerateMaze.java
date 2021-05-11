package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    public ServerStrategyGenerateMaze() {
    }

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try(InputStream input=new FileInputStream((new File("resources/config.properties")).getAbsolutePath())){
            Properties prop=new Properties();
            prop.load(input);
            IMazeGenerator gen=null;
            String genName=prop.getProperty("mazeGeneratingAlgorithm");
            ObjectInputStream fromClient=new ObjectInputStream(inFromClient);
            OutputStream out=new MyCompressorOutputStream(outToClient);
            int[] mazeSize=(int[]) fromClient.readObject();
            if(genName!=null) {
                if (genName.equalsIgnoreCase("emptymazegenerator"))
                    gen = new EmptyMazeGenerator();

                if (genName.equalsIgnoreCase("mymazegenerator"))
                    gen = new MyMazeGenerator();

                if (genName.equalsIgnoreCase("simplemazegenerator"))
                    gen = new SimpleMazeGenerator();

                if(gen==null)
                    System.err.println("Invalid generation algorithm name");
                if(gen!=null)
                {
                    Maze m = gen.generate(mazeSize[0], mazeSize[1]);
                    out.write(m.toByteArray());
                    out.flush();
                }
            }
            else{
                System.out.println("Couldn't find algorithm name");
            }
            fromClient.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
