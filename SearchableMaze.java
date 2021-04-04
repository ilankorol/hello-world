package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable{
    Maze maze;

    @Override
    public AState getStartState() {
        return null;
    }

    @Override
    public AState getGoalState() {
        return null;
    }

    @Override
    public ArrayList<AState> getAllSuccessors(AState s) {
        return null;
    }

    public SearchableMaze(Maze m)
    {
        this.maze=m;
    }
}
