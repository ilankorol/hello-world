package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable,java.io.Serializable{
    Maze maze;
    AState start;
    AState goal;
    boolean[][] visited;

    public void setGoal(AState goal) throws Exception {
        if(goal==null)
            throw new Exception("Goal Is null");
        this.goal = goal;
    }

    @Override
    public AState getStartState() {
        return this.start;
    }

    @Override
    public AState getGoalState() {
        return this.goal;
    }

    @Override
    public ArrayList<AState> getAllSuccessors(AState s) throws Exception {
        if(s==null)
            throw new Exception("State Is null");
        if(s.cost==-1)
        {
            s.setCost(0);
            for (int i=0;i<this.maze.getRows();i++)
            {
                for(int j=0;j<this.maze.getColumns();j++)
                {
                    if(this.maze.getWalls()[i][j]==0)
                        this.visited[i][j]=false;
                    else
                        this.visited[i][j]=true;
                }
            }
        }
        int row=((Position)s.node.getData()).getRowIndex();
        int col=((Position)s.node.getData()).getColumnIndex();
        ArrayList<AState> Successors=new ArrayList<AState>();
        if(this.visited[row][col]==false)
        {
            this.visited[row][col]=true;
            int numRows = maze.getRows();
            int numCols = maze.getColumns();
            boolean left = false;
            boolean right = false;
            boolean up = false;
            boolean down = false;
            int goalRow = ((Position) goal.node.getData()).getRowIndex();
            int goalCol = ((Position) goal.node.getData()).getColumnIndex();
            if (row > 0) {
                if (maze.getWalls()[row - 1][col] == 0 && !this.visited[row-1][col]) {
                    MazeNode upNode = new MazeNode(new Position(row - 1, col));
                    AState upSuccessor = new MazeState(s, upNode, s.cost + 10);
                    up = true;
                    Successors.add(upSuccessor);
                    if (row - 1 == goalRow && col == goalCol && (getGoalState().cost == 0 || upSuccessor.cost < getGoalState().cost))
                        setGoal(upSuccessor);
                }
            }
            if (col > 0) {
                if (maze.getWalls()[row][col - 1] == 0 && !this.visited[row][col-1]) {
                    MazeNode leftNode = new MazeNode(new Position(row, col - 1));
                    AState leftSuccessor = new MazeState(s, leftNode, s.cost + 10);
                    left = true;
                    Successors.add(leftSuccessor);
                    if (row == goalRow && col - 1 == goalCol && (getGoalState().cost == 0 || leftSuccessor.cost < getGoalState().cost))
                        setGoal(leftSuccessor);
                }
            }
            if (row < numRows - 1) {
                if (maze.getWalls()[row + 1][col] == 0 && !this.visited[row+1][col]) {
                    MazeNode downNode = new MazeNode(new Position(row + 1, col));
                    AState downSuccessor = new MazeState(s, downNode, s.cost + 10);
                    down = true;
                    Successors.add(downSuccessor);
                    if (row + 1 == goalRow && col == goalCol && (getGoalState().cost == 0 || downSuccessor.cost < getGoalState().cost))
                        setGoal(downSuccessor);
                }
            }
            if (col < numCols - 1) {
                if (maze.getWalls()[row][col + 1] == 0 && !this.visited[row][col+1]) {
                    MazeNode rightNode = new MazeNode(new Position(row, col + 1));
                    AState rightSuccessor = new MazeState(s, rightNode, s.cost + 10);
                    right = true;
                    Successors.add(rightSuccessor);
                    if (row == goalRow && col + 1 == goalCol && (getGoalState().cost == 0 || rightSuccessor.cost < getGoalState().cost))
                        setGoal(rightSuccessor);
                }
            }
            if ((left && row > 0) || (up && col > 0)) {
                if (maze.getWalls()[row - 1][col - 1] == 0 && !this.visited[row-1][col-1]) {
                    MazeNode LeftUpNode = new MazeNode(new Position(row - 1, col - 1));
                    AState LeftUpSuccessor = new MazeState(s, LeftUpNode, s.cost + 15);
                    Successors.add(LeftUpSuccessor);
                    if (row - 1 == goalRow && col - 1 == goalCol && (getGoalState().cost == 0 || LeftUpSuccessor.cost < getGoalState().cost))
                        setGoal(LeftUpSuccessor);
                }
            }
            if ((left && row + 1 < numRows) || (down && col > 0)) {
                if (maze.getWalls()[row + 1][col - 1] == 0 && !this.visited[row+1][col-1]) {
                    MazeNode LeftDownNode = new MazeNode(new Position(row + 1, col - 1));
                    AState LeftDownSuccessor = new MazeState(s, LeftDownNode, s.cost + 15);
                    Successors.add(LeftDownSuccessor);
                    if (row + 1 == goalRow && col - 1 == goalCol && (getGoalState().cost == 0 || LeftDownSuccessor.cost < getGoalState().cost))
                        setGoal(LeftDownSuccessor);
                }
            }
            if ((right && row + 1 < numRows) || (down && col + 1 < numCols)) {
                if (maze.getWalls()[row + 1][col + 1] == 0 && !this.visited[row+1][col+1]) {
                    MazeNode RightDownNode = new MazeNode(new Position(row + 1, col + 1));
                    AState RightDownSuccessor = new MazeState(s, RightDownNode, s.cost + 15);
                    Successors.add(RightDownSuccessor);
                    if (row + 1 == goalRow && col + 1 == goalCol && (getGoalState().cost == 0 || RightDownSuccessor.cost < getGoalState().cost))
                        setGoal(RightDownSuccessor);
                }
            }
            if ((right && row > 0) || (up && col + 1 < numCols)) {
                if (maze.getWalls()[row - 1][col + 1] == 0 && !this.visited[row-1][col+1]) {
                    MazeNode RightUpNode = new MazeNode(new Position(row - 1, col + 1));
                    AState RightUpSuccessor = new MazeState(s, RightUpNode, s.cost + 15);
                    Successors.add(RightUpSuccessor);
                    if (row - 1 == goalRow && col + 1 == goalCol && (getGoalState().cost == 0 || RightUpSuccessor.cost < getGoalState().cost))
                        setGoal(RightUpSuccessor);
                }
            }
        }
        return Successors;
    }

    public SearchableMaze(Maze m) throws Exception {
        if (m != null)
        {
            if (m.getGoalPosition() != null & m.getStartPosition() != null) {
                this.maze = m;
                Position start = maze.getStartPosition();
                AState s = new MazeState(null, new MazeNode(new Position(start.getRowIndex(), start.getColumnIndex())), 0);
                this.start = s;
                Position end = maze.getGoalPosition();
                AState s2 = new MazeState(null, new MazeNode(new Position(end.getRowIndex(), end.getColumnIndex())), 0);
                this.goal = s2;
                this.visited=new boolean[m.getRows()][m.getColumns()];
                for (int i=0;i<m.getRows();i++)
                {
                    for(int j=0;j<m.getColumns();j++)
                    {
                        if(m.getWalls()[i][j]==0)
                            this.visited[i][j]=false;
                        else
                            this.visited[i][j]=true;
                    }
                }
            }
        }
        else
            throw new Exception("Maze Is null");
    }
}
