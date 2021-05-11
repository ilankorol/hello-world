package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Properties;

public class ServerStrategyStringReverser implements IServerStrategy{
    public ServerStrategyStringReverser() {
    }
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try{
            ObjectInputStream fromClient=new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient=new ObjectOutputStream(outToClient);
            String string=(String) fromClient.readObject();
            String revString = new StringBuilder(string).reverse().toString();
            toClient.writeObject(revString);
            toClient.flush();
            fromClient.close();
            toClient.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
