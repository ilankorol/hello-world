package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.ISearchable;
import algorithms.search.MazeNode;
import algorithms.search.MazeState;

import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {
    Maze3D maze;
    AState start;
    AState goal;
    boolean[][][] visited;

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
        if(s.getCost()==-1)
        {
            s.setCost(0);
            for(int k=0;k<this.maze.getDepth();k++) {
                for (int i = 0; i < this.maze.getRows(); i++) {
                    for (int j = 0; j < this.maze.getColumns(); j++) {
                        if (this.maze.getWalls()[k][i][j] == 0)
                            this.visited[k][i][j] = false;
                        else
                            this.visited[k][i][j] = true;
                    }
                }
            }
        }
        int dep=((Position)s.getNode().getData()).getColumnIndex();
        int row=((Position)s.getNode().getData()).getRowIndex();
        int col=((Position)s.getNode().getData()).getColumnIndex();
        ArrayList<AState> Successors=new ArrayList<AState>();
        if(this.visited[dep][row][col]==false)
        {
            this.visited[dep][row][col]=true;
            int numRows = maze.getRows();
            int numCols = maze.getColumns();
            int numDeps=maze.getDepth();
            int goalRow = ((Position3D) goal.getNode().getData()).getRowIndex();
            int goalCol = ((Position3D) goal.getNode().getData()).getColumnIndex();
            int goalDep = ((Position3D) goal.getNode().getData()).getDepthIndex();
            if (row > 0) {
                if (maze.getWalls()[dep][row - 1][col] == 0 && !this.visited[dep][row-1][col]) {
                    Maze3DNode upNode = new Maze3DNode(new Position3D(dep,row - 1, col));
                    AState upSuccessor = new Maze3DState(s, upNode, s.getCost() + 10);
                    Successors.add(upSuccessor);
                    if (dep==goalDep && row - 1 == goalRow && col == goalCol && (getGoalState().getCost() == 0 || upSuccessor.getCost() < getGoalState().getCost()))
                        setGoal(upSuccessor);
                }
            }
            if (col > 0) {
                if (maze.getWalls()[dep][row][col - 1] == 0 && !this.visited[dep][row][col-1]) {
                    Maze3DNode leftNode = new Maze3DNode(new Position3D(dep,row, col - 1));
                    AState leftSuccessor = new Maze3DState(s, leftNode, s.getCost() + 10);
                    Successors.add(leftSuccessor);
                    if (dep==goalDep && row == goalRow && col - 1 == goalCol && (getGoalState().getCost() == 0 || leftSuccessor.getCost() < getGoalState().getCost()))
                        setGoal(leftSuccessor);
                }
            }
            if (row < numRows - 1) {
                if (maze.getWalls()[dep][row + 1][col] == 0 && !this.visited[dep][row+1][col]) {
                    Maze3DNode downNode = new Maze3DNode(new Position3D(dep,row + 1, col));
                    AState downSuccessor = new Maze3DState(s, downNode, s.getCost() + 10);
                    Successors.add(downSuccessor);
                    if (dep==goalDep && row + 1 == goalRow && col == goalCol && (getGoalState().getCost() == 0 || downSuccessor.getCost() < getGoalState().getCost()))
                        setGoal(downSuccessor);
                }
            }
            if (col < numCols - 1) {
                if (maze.getWalls()[dep][row][col + 1] == 0 && !this.visited[dep][row][col+1]) {
                    Maze3DNode rightNode = new Maze3DNode(new Position3D(dep,row, col + 1));
                    AState rightSuccessor = new MazeState(s, rightNode, s.getCost() + 10);
                    Successors.add(rightSuccessor);
                    if (dep==goalDep && row == goalRow && col + 1 == goalCol && (getGoalState().getCost() == 0 || rightSuccessor.getCost() < getGoalState().getCost()))
                        setGoal(rightSuccessor);
                }
            }
            if (dep > 0) {
                if (maze.getWalls()[dep-1][row][col] == 0 && !this.visited[dep-1][row][col]) {
                    Maze3DNode upNode = new Maze3DNode(new Position3D(dep-1,row, col));
                    AState upSuccessor = new Maze3DState(s, upNode, s.getCost() + 10);
                    Successors.add(upSuccessor);
                    if (dep - 1 == goalDep && row == goalRow && col == goalCol && (getGoalState().getCost() == 0 || upSuccessor.getCost() < getGoalState().getCost()))
                        setGoal(upSuccessor);
                }
            }
            if (dep < numDeps - 1) {
                if (maze.getWalls()[dep+1][row][col] == 0 && !this.visited[dep+1][row][col]) {
                    Maze3DNode downNode = new Maze3DNode(new Position3D(dep + 1,row, col));
                    AState downSuccessor = new Maze3DState(s, downNode, s.getCost() + 10);
                    Successors.add(downSuccessor);
                    if (dep+1 == goalDep && row == goalRow && col == goalCol && (getGoalState().getCost() == 0 || downSuccessor.getCost() < getGoalState().getCost()))
                        setGoal(downSuccessor);
                }
            }
        }
        return Successors;
    }

    public SearchableMaze3D(Maze3D m) throws Exception {
        if (m != null)
        {
            if (m.getGoalPosition() != null & m.getStartPosition() != null) {
                this.maze = m;
                Position3D start = maze.getStartPosition();
                AState s = new Maze3DState(null, new Maze3DNode(new Position3D(start.getDepthIndex(),start.getRowIndex(), start.getColumnIndex())), 0);
                this.start = s;
                Position3D end = maze.getGoalPosition();
                AState s2 = new Maze3DState(null, new Maze3DNode(new Position3D(end.getDepthIndex(),end.getRowIndex(), end.getColumnIndex())), 0);
                this.goal = s2;
                this.visited=new boolean[m.getDepth()][m.getRows()][m.getColumns()];
                for(int k=0;k<this.maze.getDepth();k++) {
                    for (int i = 0; i < this.maze.getRows(); i++) {
                        for (int j = 0; j < this.maze.getColumns(); j++) {
                            if (this.maze.getWalls()[k][i][j] == 0)
                                this.visited[k][i][j] = false;
                            else
                                this.visited[k][i][j] = true;
                        }
                    }
                }
            }
        }
        else
            throw new Exception("Maze Is null");
    }
}
