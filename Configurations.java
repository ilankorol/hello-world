package Server;

import java.io.*;
import java.util.Properties;

public class Configurations {

    private static Configurations instance = null;
    String path;


    private Configurations(){
        this.path=(new File("resources/config.properties")).getAbsolutePath();
    };

    public static Configurations getInstance(){
        if(instance == null){
            instance = new Configurations();
        }
        return instance;
    }

    public void setProperties(int threadPoolSize,String mazeGeneratingAlgorithm,String mazeSearchingAlgorithm) {
        try(OutputStream output= new FileOutputStream(path)) {
            Properties prop= new Properties();
            prop.setProperty("threadPoolSize",String.valueOf(threadPoolSize));
            prop.setProperty("mazeGeneratingAlgorithm",mazeGeneratingAlgorithm);
            prop.setProperty("mazeSearchingAlgorithm",mazeSearchingAlgorithm);
            prop.store(output,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
