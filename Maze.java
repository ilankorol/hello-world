package algorithms.mazeGenerators;

import java.util.Stack;

public class Maze {
    private int rows;
    private int columns;
    private int[][] Walls;
    private Position start;
    private Position end;

    public void setWalls(int[][] walls) {
        Walls = walls;
    }

    public int[][] getWalls() {
        return Walls;
    }

    public void setStart(Position start) {
        Start = start;
    }

    public void setEnd(Position end) {
        End = end;
    }

    public Position getStart() {
        return Start;
    }

    public Position getEnd() {
        return End;
    }

    private Position Start;
    private Position End;
    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
    public void setCell(int row,int column,int val)
    {
        if(row>=0&column>=0&row<rows&column<columns&(val==0|val==1))
        {
            Walls[row][column]=val;
        }
    }
    public Stack<Position> GetAvailableNeighbors(Position p){
        return null;//need to write it down
    }
}
