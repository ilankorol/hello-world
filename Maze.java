package algorithms.mazeGenerators;
import java.util.*;

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

    public void setCell(int row, int column, int val) {
        if (row >= 0 & column >= 0 & row < rows & column < columns & (val == 0 | val == 1)) {
            Walls[row][column] = val;
        }
    }

    public Stack<Position> GetAvailableNeighbors(Maze m, Position p) {
        if (p == null)
            return null;
        Stack<Position> k = new Stack<>();
        if (p.getColumnIndex() - 1 >= 0) {
            if (m[p.getRowIndex()+1][p.getColumnIndex() - 1] == 1 && m[p.getRowIndex() - 1][p.getColumnIndex()-1] == 1 && m[p.getRowIndex()][p.getColumnIndex()-2] == 1 && p.getColumnIndex()-2>=0 && p.getRowIndex()-1>=0 && p.getRowIndex()+1<m.rows) {
                Position pos = new Position(p.getRowIndex(), p.getColumnIndex() - 1);
                k.push(pos);
            }
        }
        if (p.getRowIndex() - 1 >= 0) {
            if (m[p.getRowIndex()-1][p.getColumnIndex() - 1] == 1 && m[p.getRowIndex() - 1][p.getColumnIndex()+1] == 1 && m[p.getRowIndex()-2][p.getColumnIndex()] == 1 && p.getColumnIndex()-1>=0 && p.getColumnIndex()+1>=m.columns && p.getRowIndex()-2>=0) {
                Position pos = new Position(p.getRowIndex()-1, p.getColumnIndex() );
                k.push(pos);
            }
            }
        if (p.getRowIndex() + 1 <m.rows) {
            if (m[p.getRowIndex()+1][p.getColumnIndex() - 1] == 1 && m[p.getRowIndex() + 1][p.getColumnIndex()+1] == 1 && m[p.getRowIndex()+2][p.getColumnIndex()] == 1 && p.getColumnIndex()-1>=0 && p.getColumnIndex()+1>=m.columns && p.getRowIndex()+2<m.rows) {
                Position pos = new Position(p.getRowIndex()+1, p.getColumnIndex() );
                k.push(pos);
            }
        }
        if (p.getColumnIndex() + 1 <m.columns) {
            if (m[p.getRowIndex()+1][p.getColumnIndex() + 1] == 1 && m[p.getRowIndex() - 1][p.getColumnIndex()+1] == 1 && m[p.getRowIndex()][p.getColumnIndex()+2] == 1 && p.getColumnIndex()+2<m.columns && p.getRowIndex()-1>=0 && p.getRowIndex()+1<m.rows) {
                Position pos = new Position(p.getRowIndex(), p.getColumnIndex() - 1);
                k.push(pos);
            }
        }
        return k;
        }
    }

