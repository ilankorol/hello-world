package View;

import algorithms.mazeGenerators.*;
import algorithms.search.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import main.java.Config.Configurations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MyViewController implements Initializable {
    private static final Logger LOG = LogManager.getLogger();
    public IMazeGenerator generator;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;
    public Label playerRow;
    public Label playerCol;
    public int[][] maze;
    public Maze mazeObj;
    public ISearchingAlgorithm searcher;
    public Solution solution;
    public ArrayList<AState> solutionPath;
    public Button GenerateButton;
    public Button SolveButton;
    public ChoiceBox MazeGeneratorChoice;
    public ChoiceBox SolvingAlgorithmChoice;
    public ChoiceBox BackgroundColor;
    public double mouseX;
    public double mouseY;
    public boolean dragDetect;
    public boolean isControllPressed = false;
    public GridPane gridPane;
    public ColumnConstraints gridCol1;
    public ColumnConstraints gridCol2;
    public int deltaScroll;
    public int currSizePercent;
    public VBox leftVBox;
    public RowConstraints gridRow1;
    public RowConstraints gridRow2;
    public RowConstraints gridRow3;
    public RowConstraints gridRow4;
    public RowConstraints gridRow5;
    public RowConstraints gridRow6;
    public Pane leftBottomPane;
    public Label labelMazeRows;
    public Label labelMazeCols;
    public Label labelPlayerRow;
    public Label labelPlayerCol;
    public Label labelMazeGenerator;
    public Label labelBackgroundColor;
    public Label labelSolvingAlgorithm;

    public ChoiceBox getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(ChoiceBox backgroundColor) {
        BackgroundColor = backgroundColor;
    }



    public IMazeGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(IMazeGenerator generator) {
        this.generator = generator;
    }

    public ChoiceBox getMazeGeneratorChoice() {
        return MazeGeneratorChoice;
    }

    public void setMazeGeneratorChoice(ChoiceBox mazeGeneratorChoice) {
        MazeGeneratorChoice = mazeGeneratorChoice;
    }



    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();

    public String getUpdatePlayerRow() {
        return updatePlayerRow.get();
    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public String getUpdatePlayerCol() {
        return updatePlayerCol.get();
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerRow.textProperty().bind(updatePlayerRow);
        playerCol.textProperty().bind(updatePlayerCol);
        this.MazeGeneratorChoice.getItems().add("MyMazeGenerator");
        this.MazeGeneratorChoice.getItems().add("EmptyMazeGenerator");
        this.MazeGeneratorChoice.getItems().add("SimpleMazeGenerator");
        this.SolvingAlgorithmChoice.getItems().add("BestFirstSearch");
        this.SolvingAlgorithmChoice.getItems().add("BreadthFirstSearch");
        this.SolvingAlgorithmChoice.getItems().add("DepthFirstSearch");
        BackgroundColor.getItems().add("Red");
        BackgroundColor.getItems().add("Pale Blue");
        BackgroundColor.getItems().add("Dark Blue");
        BackgroundColor.getItems().add("Pink");
        BackgroundColor.getItems().add("Orange");
        BackgroundColor.getItems().add("Turquoise");
        BackgroundColor.getItems().add("Yellow");
        this.deltaScroll=0;
        this.currSizePercent=100;
        this.mouseX=-1;
        this.mouseY=-1;
        this.dragDetect=false;
        BackgroundColor.setOnAction((event)->{
            appendColorToCss(this.BackgroundColor.getValue().toString());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Color was chosen");
            alert.setHeaderText("Chosen color: "+BackgroundColor.getValue().toString()+"\nYou will need to exit and open again to see the changed color");
            alert.show();
        });
    }

    public static void appendColorToCss(String chosenColor) {
        try {
            String filePath = "src/View/MainStyle.css";
            Scanner sc = new Scanner(new File(filePath));
            StringBuffer buffer = new StringBuffer();
            int count = 1;
            String colorLine = "";
            String line = "";
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                buffer.append(line + System.lineSeparator());
                if (count == 2)
                    colorLine = line;
                count++;
            }
            String fileContents = buffer.toString();
            sc.close();
            String oldLine = colorLine;
            String newLine = "    -fx-background-color: ";
            if (chosenColor.equals("Red"))
                newLine += "#9b0909;";
            if(chosenColor.equals("Pale Blue"))
                newLine += "#c4e3ff;";
            if(chosenColor.equals("Dark Blue"))
                newLine+="#3f58d6;";
            if(chosenColor.equals("Turquoise"))
                newLine+="#c4ffee;";
            if(chosenColor.equals("Yellow"))
                newLine+="#fdffc4;";
            if(chosenColor.equals("Pink"))
                newLine+="#ffc4f9;";
            if(chosenColor.equals("Orange"))
                newLine+="#f19141;";
            fileContents = fileContents.replaceAll(oldLine, newLine);
            FileWriter writer = new FileWriter(filePath);
            writer.append(fileContents);
            writer.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateMaze(ActionEvent actionEvent) {
        int rows = Integer.valueOf(textField_mazeRows.getText());
        int cols = Integer.valueOf(textField_mazeColumns.getText());
        if (generator == null)
            mazeDisplayer.clip = null;
        if (MazeGeneratorChoice.getValue() != null) {
            try (InputStream input = new FileInputStream((new File("resources/config/config.properties")).getAbsolutePath())) {
                Properties prop = new Properties();
                prop.load(input);
                int poolSize = Integer.valueOf(prop.getProperty("threadPoolSize"));
                String searcherAlgo = prop.getProperty("mazeSearchingAlgorithm");
                String color = prop.getProperty("color");
                if (MazeGeneratorChoice.getValue().toString().equals("EmptyMazeGenerator")) {
                    Configurations.getInstance().setProperties(poolSize, "EmptyMazeGenerator", searcherAlgo, color);
                    generator = new EmptyMazeGenerator();
                    LOG.info("The maze size: " + rows + "X" + cols + " ,the type of Maze : EmptyMaze");
                }
                if (MazeGeneratorChoice.getValue().toString().equals("SimpleMazeGenerator")) {
                    Configurations.getInstance().setProperties(poolSize, "SimpleMazeGenerator", searcherAlgo, color);
                    generator = new SimpleMazeGenerator();
                    LOG.info("The maze size: " + rows + "X" + cols + " ,the type of Maze : SimpleMaze");

                }
                if (MazeGeneratorChoice.getValue().toString().equals("MyMazeGenerator")) {
                    Configurations.getInstance().setProperties(poolSize, "MyMazeGenerator", searcherAlgo, color);
                    generator = new MyMazeGenerator();
                    LOG.info("The maze size: " + rows + "X" + cols + " ,the type of Maze : MyMaze");
                }
            } catch (Exception e) {
                generator = new MyMazeGenerator();
                LOG.info("The maze size: " + rows + "X" + cols + " ,the type of Maze : MyMaze");
            }
        } else {
            generator = new MyMazeGenerator();
            LOG.info("The maze size: " + rows + "X" + cols + " ,the type of Maze : MyMaze");
        }

        SolveButton.setDisable(false);



        int[][] maze = new int[0][];
        try {
            this.mazeObj = generator.generate(rows, cols);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Your Maze is TOO smallllll");
        }
        maze=mazeObj.getWalls();
        this.maze=maze;
        mazeDisplayer.solution=null;
        mazeDisplayer.drawMaze(mazeObj);
        setPlayerPosition(0, 0);
    }

    public void solveMaze(ActionEvent actionEvent) {
        if(SolvingAlgorithmChoice.getValue()!=null) {
            try(InputStream input=new FileInputStream((new File("resources/config/config.properties")).getAbsolutePath())) {
                Properties prop = new Properties();
                prop.load(input);
                String genName = prop.getProperty("mazeGeneratingAlgorithm");
                String color = prop.getProperty("color");
                int poolSize = Integer.valueOf(prop.getProperty("threadPoolSize"));
                if (SolvingAlgorithmChoice.getValue().toString().equals("BreadthFirstSearch")) {
                    Configurations.getInstance().setProperties(poolSize, genName, "BreadthFirstSearch",color);
                    searcher = new BreadthFirstSearch();
                    LOG.info("The maze Solved by : BFS");
                }
                if (SolvingAlgorithmChoice.getValue().toString().equals("DepthFirstSearch")) {
                    Configurations.getInstance().setProperties(poolSize, genName, "DepthFirstSearch",color);
                    searcher = new DepthFirstSearch();
                    LOG.info("The maze Solved by : DFS");
                }
                if (SolvingAlgorithmChoice.getValue().toString().equals("BestFirstSearch")) {
                    Configurations.getInstance().setProperties(poolSize, genName, "BestFirstSearch",color);
                    searcher = new BestFirstSearch();
                    LOG.info("The maze Solved by : Best First Search");
                }
            }
            catch (Exception e){searcher = new BestFirstSearch();
                LOG.info("The maze Solved by : Best First Search");
            }
        }
        else {
            searcher = new BestFirstSearch();
            LOG.info("The maze Solved by : Best First Search");
        }

        Solution sol = null;
        try {
            sol = searcher.solve(new SearchableMaze(this.mazeObj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.solution=sol;
        this.solutionPath=this.solution.getSolutionPath();
        LOG.info("the solution is:"+solution.getSolutionPath());
        mazeDisplayer.drawSolution(solution);
    }

    public void openFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showOpenDialog(null);
        String absPath=chosen.getAbsolutePath();
        int count=1;
        int countRows=0;
        int countCols=0;
        int mazeRows=0;
        int mazeCols=0;
        int goalRow=0;
        int goalCol=0;
        int[][] walls=null;
        try {
            Scanner sc = new Scanner(new File(absPath));
            while (sc.hasNextLine()) {
                String lineString = sc.nextLine();
                int lineInt=Integer.valueOf(lineString);
                switch (count) {
                    case 1:
                        mazeRows=lineInt;
                        break;
                    case 2 :
                        mazeCols=lineInt;
                        break;
                    case 3:
                        goalRow=lineInt;
                        break;
                    case 4 :
                        goalCol=lineInt;
                        walls = new int[mazeRows][mazeCols];
                        break;
                }
                if(count>4)
                {
                    walls[countRows][countCols]=lineInt;
                    countCols++;
                    if(countCols==mazeCols)
                    {
                        countCols=0;
                        countRows++;
                    }
                }
                count++;
            }
            GenerateLoadedMaze(mazeRows,mazeCols,walls,goalRow,goalCol);
            SolveButton.setDisable(false);
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Error");
//            alert.setHeaderText("Wrong File Content");
//            alert.show();
            LOG.error("Wrong File Content");
        }
    }
    public void GenerateLoadedMaze(int mazeRows,int mazeCols,int[][] walls,int goalRow,int goalCol){
        if(generator == null) {
            if(SolvingAlgorithmChoice.getValue().toString().equals(""))
                generator = new MyMazeGenerator();
            LOG.info("User open Maze " + mazeRows + "X" + mazeCols );

            mazeDisplayer.clip=null;
        }
        try {
            this.mazeObj = generator.generate(mazeRows, mazeCols);
            mazeDisplayer.mazeObj=this.mazeObj;
            mazeDisplayer.mazeObj.setWalls(walls);
            mazeDisplayer.maze=walls;
            this.mazeObj.setWalls(walls);
            this.maze=walls;

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong File Content");
            alert.show();
        }
        mazeDisplayer.solution=null;
        mazeDisplayer.drawMaze(mazeObj);
        setPlayerPosition(0, 0);
    }

    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.CONTROL){
            isControllPressed = true;
        }
        else{


            switch (keyEvent.getCode()) {
                case UP:
                case DIGIT8 :
                case NUMPAD8:
                    updateLocation(8,keyEvent);
                    break;
                case DOWN :
                case DIGIT2 :
                case NUMPAD2:
                    updateLocation(2,keyEvent);
                    break;
                case RIGHT:
                case DIGIT6:
                case NUMPAD6:
                    updateLocation(6,keyEvent);
                    break;
                case LEFT :
                case DIGIT4:
                case NUMPAD4:
                    updateLocation(4,keyEvent);
                    break;
                case DIGIT1:
                case NUMPAD1:
                    updateLocation(1,keyEvent);
                    break;
                case DIGIT3:
                case NUMPAD3:
                    updateLocation(3,keyEvent);
                    break;
                case DIGIT7:
                case NUMPAD7:
                    updateLocation(7,keyEvent);
                    break;
                case DIGIT9:
                case NUMPAD9:
                    updateLocation(9,keyEvent);
            }
        }

    }

    public void setPlayerPosition(int row, int col){
        mazeDisplayer.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    public void saveFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showSaveDialog(null);
        String absPath=chosen.getAbsolutePath();
        String toWrite="";
        if(this.mazeObj!=null)
        {
            toWrite=toWrite+mazeObj.getRows()+"\n";
            toWrite=toWrite+mazeObj.getColumns()+"\n";
            toWrite=toWrite+mazeObj.getGoalPosition().getRowIndex()+"\n";
            toWrite=toWrite+mazeObj.getGoalPosition().getColumnIndex();
            LOG.info("The User saved maze "+ mazeObj.getRows()+"X"+mazeObj.getColumns()+" The soultion is in: "+mazeObj.getGoalPosition().getRowIndex()+ "X"+ mazeObj.getGoalPosition().getColumnIndex());
            for(int i=0;i<mazeObj.getRows();i++)
            {
                for (int j=0;j<mazeObj.getColumns();j++)
                {
                    toWrite=toWrite+"\n"+maze[i][j];
                }
            }
        }

        try {
            FileWriter myWriter = new FileWriter(absPath);
            myWriter.write(toWrite);
            myWriter.close();
            LOG.info("Successfully wrote to the file.");
        } catch (IOException e) {
            LOG.error("An error occurred.");
            e.printStackTrace();
        }
    }

    public void Exit(ActionEvent actionEvent) {
        Platform.exit();
    }


    public void SetProperties(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Properties");
        String alertString="";
        try(InputStream input=new FileInputStream((new File("resources/config/config.properties")).getAbsolutePath())) {
            Properties prop = new Properties();
            prop.load(input);
            int poolSize = Integer.valueOf(prop.getProperty("threadPoolSize"));
            String searcherAlgo = prop.getProperty("mazeSearchingAlgorithm");
            String genName = prop.getProperty("mazeGeneratingAlgorithm");
            alertString = "Pool Size: " + poolSize + "\n";
            alertString = alertString + "Solving Algorithm: " + searcherAlgo + "\n";
            alertString = alertString + "Maze Generator: " + genName;
        }
        catch(Exception e) {
            alertString = "Config File Not Found, Default: MyMazeGenerator,BestFirstSearch,Pool Size 10";
        }
        alert.setHeaderText(alertString);
        alert.show();
    }

    public void ChoiceBox(ActionEvent actionEvent) {

    }

    public void HowToPlay(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How To Play");
        alert.setHeaderText("Click 'Generate Maze' to start.The character at (0,0) is you and the goal is to get to the trophy by moving(while the blocks represent walls you can't step on).\n Move by clicking 2,4,6,8 to move down,left,right,up and 1,3,7,9 to move diagonally.\n You can also move by dragging the character with the mouse.\n");
        alert.show();
    }

    public void About(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Creators: Shahar Cohen 322866070, Ilan Karol 208185397.\nStaff: Jordan Rotem, Rotem Lev Lehman, Dudi Ben Shimon.\nThe algorithms for generating are: MyMazeGenerator,SimpleMazeGenerator,EmptyMazeGenerator.\nThe algorithms for solving are: BestFirstSearch,DepthFirstSearch,BreadthFirstSearch.\nYou can find the currently used algorithms by clicking Options->Properties.\n");
        alert.show();
    }

    public void checkIfDistancedEnoughAndMove(MouseEvent mouseEvent,double cellHeight,double cellWidth,double locX,double locY){
        if(maze!=null){
            int row=mazeDisplayer.getPlayerRow();
            int col=mazeDisplayer.getPlayerCol();
            if(!mouseEvent.isControlDown()) {
                boolean up = mouseEvent.getY() < locY && (Math.abs(mouseEvent.getY() - locY) > cellHeight);
                boolean down = mouseEvent.getY() > locY && (Math.abs(mouseEvent.getY() - locY) > cellHeight);
                boolean left = mouseEvent.getX() < locX && Math.abs(mouseEvent.getX() - locX) > cellWidth;
                boolean right = mouseEvent.getX() > locX && Math.abs(mouseEvent.getX() - locX) > cellWidth;
                boolean upEmpty = maze[row - 1][col] == 0;
                boolean downEmpty = maze[row + 1][col] == 0;
                boolean leftEmpty = maze[row][col - 1] == 0;
                boolean rightEmpty = maze[row][col + 1] == 0;
                boolean upLeftEmpty = maze[row - 1][col - 1] == 0;
                boolean upRightEmpty = maze[row - 1][col + 1] == 0;
                boolean downLeftEmpty = maze[row + 1][col - 1] == 0;
                boolean downRightEmpty = maze[row + 1][col + 1] == 0;
                if (up) {
                    if (right && upRightEmpty) {
                        if (upEmpty || rightEmpty) {//up right
                            row--;
                            col++;
                        }
                    } else if (left && upLeftEmpty) {
                        if (upEmpty || leftEmpty) {//up left
                            row--;
                            col--;
                        }
                    } else if (!right && !left) {
                        if (upEmpty) {//up
                            row--;
                        }
                    }
                }
                if (down) {
                    if (right && downRightEmpty) {
                        if (rightEmpty || downEmpty) {//down right
                            row++;
                            col++;
                        }
                    } else if (left && downLeftEmpty) {
                        if (leftEmpty || downEmpty) {//down left
                            row++;
                            col--;
                        }
                    } else if (!left && !right) {
                        if (downEmpty) {//down
                            row++;
                        }
                    }
                }
                if (!up && !down && left) {
                    if (leftEmpty) {//left
                        col--;
                    }
                }
                if (!up && !down && right) {
                    if (rightEmpty) {//right
                        col++;
                    }
                }
                setPlayerPosition(row, col);

            }
        }
    }
    public void mouseDragged(MouseEvent mouseEvent) {
        int rows = maze.length;
        int cols = maze[0].length;
        double canvasHeight = mazeDisplayer.getHeight();
        double canvasWidth = mazeDisplayer.getWidth();
        double cellHeight = canvasHeight / rows;
        double cellWidth = canvasWidth / cols;
        if(this.dragDetect==true)
            checkIfDistancedEnoughAndMove(mouseEvent,cellHeight,cellWidth, mouseX,mouseY);
    }
    public void updateLocation(int Case,KeyEvent keyEvent)
    {
        int row = mazeDisplayer.getPlayerRow();
        int col = mazeDisplayer.getPlayerCol();
        if(Case==1)
        {
            if(row+1<this.maze.length&&col-1>=0)
            {
                if(maze[row+1][col-1]==0)
                {
                    if(maze[row][col-1]==0 || maze[row+1][col]==0)
                    {
                        row += 1;
                        col -= 1;
                    }
                }
            }
        }
        if(Case==2)
        {
            if(row+1<this.maze.length) {
                if (this.maze[row+1][col] == 0)
                    row += 1;
            }
        }
        if(Case==3)
        {
            if(row+1<this.maze.length&&col+1<this.maze[0].length)
            {
                if(maze[row+1][col+1]==0)
                {
                    if(maze[row+1][col]==0 || maze[row][col+1]==0)
                    {
                        row+=1;
                        col+=1;
                    }
                }
            }
        }
        if(Case==4)
        {
            if(col-1>=0) {
                if (this.maze[row][col - 1] == 0)
                    col -= 1;
            }
        }
        if(Case==6)
        {
            if(col+1<this.maze[0].length) {
                if (this.maze[row][col + 1] == 0)
                    col += 1;
            }
        }
        if(Case==7)
        {
            if(row-1>=0 && col-1>=0)
            {
                if(maze[row-1][col-1]==0)
                {
                    if(maze[row-1][col]==0 || maze[row][col-1]==0)
                    {
                        row-=1;
                        col-=1;
                    }
                }
            }
        }
        if(Case==8)
        {
            if(row-1>=0) {
                if (this.maze[row-1][col] == 0)
                    row -= 1;
            }
        }
        if(Case==9)
        {
            if(row-1>=0 && col+1<this.maze[0].length)
            {
                if(maze[row-1][col+1]==0)
                {
                    if(maze[row-1][col]==0 || maze[row][col+1]==0)
                    {
                        row-=1;
                        col+=1;
                    }
                }
            }
        }
        setPlayerPosition(row, col);
        if(keyEvent!=null)
            keyEvent.consume();
    }


    public void dragDetected(MouseEvent mouseEvent) {
        this.dragDetect=true;
        this.mouseX=mouseEvent.getX();
        this.mouseY=mouseEvent.getY();
    }

    public void mousePressed(MouseEvent mouseEvent) {
        if(maze!=null) {
            this.mouseY = mouseEvent.getY() ;
            this.mouseX = mouseEvent.getX() ;
        }
    }



    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.CONTROL){
            isControllPressed = false;
        }
    }

    public void zoom(ScrollEvent scrollEvent) {
        if(isControllPressed){
            if(scrollEvent.getDeltaY() > 0){
                mazeDisplayer.zoomIn();
            }
            else{
                mazeDisplayer.zoomOut();
            }
        }
        scrollEvent.consume();
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        dragDetect=false;
        mouseY = mouseEvent.getY();
        mouseX = mouseEvent.getX();
        mouseEvent.consume();
    }
}
