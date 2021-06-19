package View;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {
    public int[][] maze;
    public Maze mazeObj;
    // player position:
    private int playerRow = 0;
    private int playerCol = 0;
    // wall and player images:
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameGoal = new SimpleStringProperty();
    Clip clip;


    public Solution solution;
    ArrayList<AState> solutionPath;

    public ArrayList<AState> getSolutionPath() {
        return solutionPath;
    }

    public void setSolutionPath(ArrayList<AState> solutionPath) {
        this.solutionPath = solutionPath;
    }


    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.imageFileNameGoal.set(imageFileNameGoal);
    }

    public String getImageFileNameGoal() {
        return imageFileNameGoal.get();
    }

    public String imageFileNameGoalProperty() {
        return imageFileNameGoal.get();
    }


    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        if(row==mazeObj.getGoalPosition().getRowIndex()&&col==mazeObj.getGoalPosition().getColumnIndex()) {
            try{
                PlayMusic("resources/music/EndMusic.wav");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Finished Maze");
                alert.setHeaderText("תותח!");
                alert.show();
            }
            catch(Exception e)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Ending Music Not Found");
                alert.show();
            }
        }
        draw();
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String imageFileNameWallProperty() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String imageFileNamePlayerProperty() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }
    public void PlayMusic(String FilePath)throws Exception{
        File file = new File(FilePath);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        if(FilePath.equals("resources/music/StartMusic.wav"))
            this.clip=clip;
    }
    public void drawMaze(Maze maze) {
        this.mazeObj=maze;
        this.maze = mazeObj.getWalls();
        try {
            if (this.clip != null)
                this.clip.stop();
            PlayMusic("resources/music/StartMusic.wav");
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Starting Music Not Found");
            alert.show();
        }
        draw();
    }

    private void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.length;
            int cols = maze[0].length;

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            drawMazeGoal(graphicsContext, cellHeight, cellWidth);
            drawPlayer(graphicsContext, cellHeight, cellWidth);
            if(this.solution!=null){
                drawSolutionFinal(graphicsContext,cellHeight,cellWidth);
            }
        }
    }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        graphicsContext.setFill(Color.RED);
        Image wallImage = null;
        try{
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(maze[i][j] == 1){
                    //if it is a wall:
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if(wallImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }
            }
        }
    }
    private void drawMazeGoal(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        graphicsContext.setFill(Color.RED);
        Image goalImage = null;
        try{
            goalImage = new Image(new FileInputStream(getImageFileNameGoal()));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("There's No Goal Image");
            alert.show();
        }

        double x = mazeObj.getGoalPosition().getColumnIndex() * cellWidth;
        double y = mazeObj.getGoalPosition().getRowIndex() * cellHeight;
        if(goalImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(goalImage, x, y, cellWidth, cellHeight);
    }


    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.GREEN);

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }

    public void drawSolution(Solution sol) {
        this.solution=sol;
        this.solutionPath = sol.getSolutionPath();
        EndingMusic();
        draw();
    }
    public void EndingMusic(){
    }


    private void drawSolutionFinal(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        graphicsContext.setFill(Color.GREEN);
        //System.out.println(String.format("%s.%s", i, solutionPath.get(i)));
        for (int i = 0; i < this.solutionPath.size(); i++) {
            int solRow = ((Position) solutionPath.get(i).getNode().getData()).getRowIndex();
            int solCol = ((Position) solutionPath.get(i).getNode().getData()).getColumnIndex();
            int goalRow=this.mazeObj.getGoalPosition().getRowIndex();
            int goalCol=this.mazeObj.getGoalPosition().getColumnIndex();
            if ((playerRow != solRow || playerCol != solCol)&&(solRow != goalRow || solCol != goalCol)) {
                //if the player or the Goal isn't there:
                double x = solCol * cellWidth;
                double y = solRow * cellHeight;
                graphicsContext.fillRect(x, y, cellWidth, cellHeight);
            }
        }
        if(playerRow!=0 || playerCol!=0)
            graphicsContext.fillRect(0, 0, cellWidth, cellHeight);
    }

    public void zoomIn(){
        setHeight(getHeight() + 15);
        setWidth(getWidth() + 15);
        draw();
    }

    public void zoomOut(){
        setHeight(getHeight() - 15);
        setWidth(getWidth() - 15);
        draw();
    }
/*    public void Zoom() {
        setOnScroll(event -> {
            if (event.isControlDown()) {
                double change = event.getDeltaY();
                double zoomConst = 1.03;
                if (change < 0) {
                    zoomConst = 0.97;
                }

                setScaleY(getScaleY() * zoomConst);
                setScaleX(getScaleX() * zoomConst);
                event.consume();
            }
        });
    }*/
}
