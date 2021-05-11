package Server;

import Server.IServerStrategy;
import algorithms.mazeGenerators.IMazeGenerator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.*;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private boolean stop;
    private ExecutorService threadPool;
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        String path=(new File("resources/config.properties")).getAbsolutePath();
        try {
            InputStream input = new FileInputStream((new File("resources/config.properties")).getAbsolutePath());
            Properties prop = new Properties();
            prop.load(input);
            if (prop.getProperty("threadPoolSize") == null) {
                this.threadPool = Executors.newFixedThreadPool(2);
                System.out.println("ThreadPool size isn't set correctly, the size is now 2 by default");
            } else {
                int poolSize = Integer.parseInt(prop.getProperty("threadPoolSize"));
                this.threadPool = Executors.newFixedThreadPool(poolSize);
            }
        }
        catch(IOException e){
            System.err.println(e);
        }
    }
    public void start(){
        new Thread(() -> {
            runServer();
        }).start();
    }
    public void runServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println("Starting server at port = " + port);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client accepted: " + clientSocket.toString());
                    threadPool.execute(() ->{
                        handleClient(clientSocket);
                    });

                } catch (SocketTimeoutException e){
                    System.out.println("Socket timeout");
                }

            }
            serverSocket.close();
            threadPool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleClient(Socket clientSocket) {
        try {
            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            System.out.println("Done handling client: " + clientSocket.toString());
            clientSocket.close();
        } catch (IOException e){
            System.out.println("IOException "+ e);
        }
    }

    public void stop(){
        stop = true;
    }
}
