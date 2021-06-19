package Model;

//import ViewModel.MyViewModel;
//import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;

public interface IModel {
   // void generate(ActionEvent actionEvent);
    void solve();
    //void assignObserver(MyViewModel myViewModel);
    Maze getMaze();
    void saveFile(Maze maze);
    void openFile();
    void keyPressed(KeyEvent move);
    int getPlayerRow();

    int getPlayerCol();

    Solution getSolution();

    void setPlayerPosition(int row, int col);

   // void updatePlayerLocation(movementDirection direction);

    void generate(javafx.event.ActionEvent act);

 void MyMazeGenerator(int rows, int cols);

 void SimpleMazeGenerator(int rows, int cols);

 void EmptyMazeGenerator(int rows, int cols);

 //void assignObserver(MyViewModel viewModel);

// void BreadthFirstSearch(Maze maze);
//
// void DepthFirstSearch(Maze maze);
//
// void BestFirstSearch(Maze maze);
}
