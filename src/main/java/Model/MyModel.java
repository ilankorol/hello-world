//package Model;
//import View.MazeDisplayer;
//import ViewModel.MyViewModel;
//import algorithms.mazeGenerators.*;
//import algorithms.search.*;
//
//import java.io.File;
//
//import javafx.fxml.Initializable;
//import javafx.scene.control.Alert;
//
//import java.io.FileInputStream;
//import java.net.URL;
//import java.util.ArrayList;
//import Config.Configurations;
//import javafx.scene.control.*;
//import javafx.scene.input.KeyEvent;
//import javafx.stage.FileChooser;
//import main.java.Model.IModel;
//
//import javax.sound.sampled.Clip;
//import java.io.*;
//import java.util.*;
//
//public class MyModel extends Observable implements IModel, Initializable {
//    Clip clip;
//    public IMazeGenerator generator;
//    public TextField textField_mazeRows;
//    public TextField textField_mazeColumns;
//    public MazeDisplayer mazeDisplayer;
//    public int playerRow;
//    public int playerCol;
//    public int[][] maze;
//    public Maze mazeObj;
//    public ISearchingAlgorithm searcher;
//    public Solution solution;
//    public ArrayList<AState> solutionPath;
//    public Button GenerateButton;
//    public Button SolveButton;
//    public ChoiceBox MazeGeneratorChoice;
//    public ChoiceBox SolvingAlgorithmChoice;
//    public ChoiceBox BackgroundColor;
//
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        this.MazeGeneratorChoice.getItems().add("MyMazeGenerator");
//        this.MazeGeneratorChoice.getItems().add("EmptyMazeGenerator");
//        this.MazeGeneratorChoice.getItems().add("SimpleMazeGenerator");
//        this.SolvingAlgorithmChoice.getItems().add("BestFirstSearch");
//        this.SolvingAlgorithmChoice.getItems().add("BreadthFirstSearch");
//        this.SolvingAlgorithmChoice.getItems().add("DepthFirstSearch");
//        BackgroundColor.getItems().add("Red");
//        BackgroundColor.getItems().add("Pale Blue");
//        BackgroundColor.getItems().add("Dark Blue");
//        BackgroundColor.getItems().add("Pink");
//        BackgroundColor.getItems().add("Orange");
//        BackgroundColor.getItems().add("Turquoise");
//        BackgroundColor.getItems().add("Yellow");
//        }
//
//
//    public Maze getMaze() {
//        return mazeObj;
//    }
//    public void saveFile(Maze maze){
//        FileChooser fc = new FileChooser();
//        fc.setTitle("Save maze");
//        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
//        fc.setInitialDirectory(new File("./resources"));
//        File chosen = fc.showSaveDialog(null);
//        String absPath=chosen.getAbsolutePath();
//        String toWrite="";
//        if(this.mazeObj!=null)
//        {
//            toWrite=toWrite+mazeObj.getRows()+"\n";
//            toWrite=toWrite+mazeObj.getColumns()+"\n";
//            toWrite=toWrite+mazeObj.getGoalPosition().getRowIndex()+"\n";
//            toWrite=toWrite+mazeObj.getGoalPosition().getColumnIndex();
//            for(int i=0;i<mazeObj.getRows();i++)
//            {
//                for (int j=0;j<mazeObj.getColumns();j++)
//                {
//                    toWrite=toWrite+"\n"+maze.getWalls()[i][j];
//                }
//            }
//        }
//
//        try {
//            FileWriter myWriter = new FileWriter(absPath);
//            myWriter.write(toWrite);
//            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }
//
//    public int getPlayerRow() {
//        return playerRow;
//    }
//
//    public int getPlayerCol() {
//        return playerCol;
//    }
//    @Override
//    public void generate(javafx.event.ActionEvent act){
//        //if (generator == null)
//           // mazeDisplayer.clip = null;
//        if(MazeGeneratorChoice.getValue()!=null) {
//            try(InputStream input=new FileInputStream((new File("resources/config/config.properties")).getAbsolutePath())) {
//                Properties prop = new Properties();
//                prop.load(input);
//                int poolSize = Integer.valueOf(prop.getProperty("threadPoolSize"));
//                String searcherAlgo = prop.getProperty("mazeSearchingAlgorithm");
//                String color = prop.getProperty("color");
//                if (MazeGeneratorChoice.getValue().toString().equals("EmptyMazeGenerator")) {
//                    Configurations.getInstance().setProperties(poolSize, "EmptyMazeGenerator", searcherAlgo,color);
//                    generator = new EmptyMazeGenerator();
//                }
//                if (MazeGeneratorChoice.getValue().toString().equals("SimpleMazeGenerator")) {
//                    Configurations.getInstance().setProperties(poolSize, "SimpleMazeGenerator", searcherAlgo,color);
//                    generator = new SimpleMazeGenerator();
//                }
//                if (MazeGeneratorChoice.getValue().toString().equals("MyMazeGenerator")) {
//                    Configurations.getInstance().setProperties(poolSize, "MyMazeGenerator", searcherAlgo,color);
//                    generator = new MyMazeGenerator();
//                }
//            }
//            catch (Exception e){generator=new MyMazeGenerator();}
//        }
//        else
//            generator=new MyMazeGenerator();
//        SolveButton.setDisable(false);
//
//        int rows = Integer.valueOf(textField_mazeRows.getText());
//        int cols = Integer.valueOf(textField_mazeColumns.getText());
//
//        int[][] maze = new int[0][];
//        try {
//            this.mazeObj = generator.generate(rows, cols);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        maze=mazeObj.getWalls();
//        this.maze=maze;
//        mazeDisplayer.solution=null;
//        mazeDisplayer.drawMaze(mazeObj);
//        setPlayerPosition(0, 0);
//    }
//
//    @Override
//    public void MyMazeGenerator(int rows, int cols) {
//        generator = new MyMazeGenerator();
//        try {
//            this.mazeObj = generator.generate(rows, cols);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void SimpleMazeGenerator(int rows, int cols) {
//        generator = new SimpleMazeGenerator();
//        try {
//            this.mazeObj = generator.generate(rows, cols);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void EmptyMazeGenerator(int rows, int cols) {
//        generator = new EmptyMazeGenerator();
//        try {
//            this.mazeObj = generator.generate(rows, cols);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
////    public void BreadthFirstSearch(Maze maze) {
////
////    }
////
////    @Override
////    public void DepthFirstSearch(Maze maze) {
////
////    }
////
////    @Override
////    public void BestFirstSearch(Maze maze) {
////
////    }
//
//    public void openFile(){
//        FileChooser fc = new FileChooser();
//        fc.setTitle("Open maze");
//        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
//        fc.setInitialDirectory(new File("./resources"));
//        File chosen = fc.showOpenDialog(null);
//        String absPath=chosen.getAbsolutePath();
//        int count=1;
//        int countRows=0;
//        int countCols=0;
//        int mazeRows=0;
//        int mazeCols=0;
//        int goalRow=0;
//        int goalCol=0;
//        int[][] walls=null;
//        try {
//            Scanner sc = new Scanner(new File(absPath));
//            while (sc.hasNextLine()) {
//                String lineString = sc.nextLine();
//                int lineInt=Integer.valueOf(lineString);
//                switch (count) {
//                    case 1:
//                        mazeRows=lineInt;
//                        break;
//                    case 2 :
//                        mazeCols=lineInt;
//                        break;
//                    case 3:
//                        goalRow=lineInt;
//                        break;
//                    case 4 :
//                        goalCol=lineInt;
//                        walls = new int[mazeRows][mazeCols];
//                        break;
//                }
//                if(count>4)
//                {
//                    walls[countRows][countCols]=lineInt;
//                    countCols++;
//                    if(countCols==mazeCols)
//                    {
//                        countCols=0;
//                        countRows++;
//                    }
//                }
//                count++;
//            }
//            GenerateLoadedMaze(mazeRows,mazeCols,walls,goalRow,goalCol);
//            SolveButton.setDisable(false);
//        }
//        catch (Exception e)
//        {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Error");
//            alert.setHeaderText("Wrong File Content");
//            alert.show();
//        }
//        setChanged();
//        notifyObservers("maze loaded");
//        // start position:
//    }
//
//    public void GenerateLoadedMaze(int mazeRows,int mazeCols,int[][] walls,int goalRow,int goalCol){
//        if(generator == null) {
//            if(SolvingAlgorithmChoice.getValue().toString().equals(""))
//                generator = new MyMazeGenerator();
//            //mazeDisplayer.clip=null;
//        }
//        try {
//            this.mazeObj = generator.generate(mazeRows, mazeCols);
//            mazeDisplayer.mazeObj=this.mazeObj;
//            mazeDisplayer.mazeObj.setWalls(walls);
//            mazeDisplayer.maze=walls;
//            this.mazeObj.setWalls(walls);
//            this.maze=walls;
//
//        } catch (Exception e) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Error");
//            alert.setHeaderText("Wrong File Content");
//            alert.show();
//        }
//        solution=null;
//        mazeDisplayer.drawMaze(mazeObj);
//        mazeDisplayer.setPlayerPosition(playerRow,playerCol);
//    }
//
//    public void keyPressed(KeyEvent move){
//        int row = mazeDisplayer.getPlayerRow();
//        int col = mazeDisplayer.getPlayerCol();
//
//        switch (move.getCode()) {
//            case UP:
//            case DIGIT8 :
//            case NUMPAD8:
//                if(row-1>=0) {
//                    if (this.maze[row-1][col] == 0)
//                        row -= 1;
//                }
//                break;
//            case DOWN :
//            case DIGIT2 :
//            case NUMPAD2:
//                if(row+1<this.maze.length) {
//                    if (this.maze[row+1][col] == 0)
//                        row += 1;
//                }
//                break;
//            case RIGHT:
//            case DIGIT6:
//            case NUMPAD6:
//                if(col+1<this.maze[0].length) {
//                    if (this.maze[row][col + 1] == 0)
//                        col += 1;
//                }
//                break;
//            case LEFT :
//            case DIGIT4:
//            case NUMPAD4:
//                if(col-1>=0) {
//                    if (this.maze[row][col - 1] == 0)
//                        col -= 1;
//                }
//                break;
//            case DIGIT1:
//            case NUMPAD1:
//                if(row+1<this.maze.length&&col-1>=0)
//                {
//                    if(maze[row+1][col-1]==0)
//                    {
//                        if(maze[row][col-1]==0 || maze[row+1][col]==0)
//                        {
//                            row += 1;
//                            col -= 1;
//                        }
//                    }
//                }
//                break;
//            case DIGIT3:
//            case NUMPAD3:
//                if(row+1<this.maze.length&&col+1<this.maze[0].length)
//                {
//                    if(maze[row+1][col+1]==0)
//                    {
//                        if(maze[row+1][col]==0 || maze[row][col+1]==0)
//                        {
//                            row+=1;
//                            col+=1;
//                        }
//                    }
//                }
//                break;
//            case DIGIT7:
//            case NUMPAD7:
//                if(row-1>=0 && col-1>=0)
//                {
//                    if(maze[row-1][col-1]==0)
//                    {
//                        if(maze[row-1][col]==0 || maze[row][col-1]==0)
//                        {
//                            row-=1;
//                            col-=1;
//                        }
//                    }
//                }
//                break;
//            case DIGIT9:
//            case NUMPAD9:
//                if(row-1>=0 && col+1<this.maze[0].length)
//                {
//                    if(maze[row-1][col+1]==0)
//                    {
//                        if(maze[row-1][col]==0 || maze[row][col+1]==0)
//                        {
//                            row-=1;
//                            col+=1;
//                        }
//                    }
//                }
//        }
//        mazeDisplayer.setPlayerPosition(row,col);
//
//        move.consume();
//    }
//    private void movePlayer(int row, int col){
//        this.playerRow = row;
//        this.playerCol = col;
//        setChanged();
//        notifyObservers("player moved");
//    }
//
//
//    @Override
//    public void assignObserver(MyViewModel o) {
//
//            this.addObserver(o);
//
//    }
//
//
//
//    public void solve(){
//        if(SolvingAlgorithmChoice.getValue()!=null) {
//            try(InputStream input=new FileInputStream((new File("resources/config/config.properties")).getAbsolutePath())) {
//                Properties prop = new Properties();
//                prop.load(input);
//                String genName = prop.getProperty("mazeGeneratingAlgorithm");
//                String color = prop.getProperty("color");
//                int poolSize = Integer.valueOf(prop.getProperty("threadPoolSize"));
//                if (SolvingAlgorithmChoice.getValue().toString().equals("BreadthFirstSearch")) {
//                    Configurations.getInstance().setProperties(poolSize, genName, "BreadthFirstSearch",color);
//                    searcher = new BreadthFirstSearch();
//                }
//                if (SolvingAlgorithmChoice.getValue().toString().equals("DepthFirstSearch")) {
//                    Configurations.getInstance().setProperties(poolSize, genName, "DepthFirstSearch",color);
//                    searcher = new DepthFirstSearch();
//                }
//                if (SolvingAlgorithmChoice.getValue().toString().equals("BestFirstSearch")) {
//                    Configurations.getInstance().setProperties(poolSize, genName, "BestFirstSearch",color);
//                    searcher = new BestFirstSearch();
//                }
//            }
//            catch (Exception e){searcher = new BestFirstSearch();}
//        }
//        else
//            searcher=new BestFirstSearch();
//
//        Solution sol = null;
//        try {
//            sol = searcher.solve(new SearchableMaze(this.mazeObj));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        this.solution=sol;
//        this.solutionPath=this.solution.getSolutionPath();
//        notifyObservers("maze solved");
//    }
//
//
//    public Solution getSolution() {
//        return solution;
//    }
//
//    @Override
//    public void setPlayerPosition(int row, int col) {
//
//    }
//
//   //col @Override
//  //  public void updatePlayerLocation(movementDirection direction) {
//
//    }
//
//
//}
//
