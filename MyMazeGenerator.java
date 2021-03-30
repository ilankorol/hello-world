package algorithms.mazeGenerators;

import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator{
    public static boolean CheckUpNeighbor(Maze m,Position p)
    {
        boolean upValid=false;
        int row=p.getRowIndex();
        int col=p.getColumnIndex();
        if (row - 1 >= 0) {
            upValid=true;
            if(row-2>=0)
                if(m.getWalls()[row-2][col]==0) {
                    upValid = false;
                }
            if(col-1>=0)
                if(m.getWalls()[row-1][col-1]==0) {
                    upValid = false;
                }
            if(col+1<m.getColumns())
                if(m.getWalls()[row-1][col+1]==0) {
                    upValid = false;
                }
        }
        return upValid;
    }
    public static boolean CheckDownNeighbor(Maze m,Position p)
    {
        boolean downValid=false;
        int row=p.getRowIndex();
        int col=p.getColumnIndex();
        if (row + 1 < m.getRows()) {
            downValid=true;
            if(row+2<m.getRows())
                if(m.getWalls()[row+2][col]==0) {
                    downValid = false;
                }
            if(col-1>=0)
                if(m.getWalls()[row+1][col-1]==0) {
                    downValid = false;
                }
            if(col+1<m.getColumns())
                if(m.getWalls()[row+1][col+1]==0) {
                    downValid = false;
                }
        }
        return downValid;
    }
    public static boolean CheckLeftNeighbor(Maze m,Position p)
    {
        boolean leftValid=false;
        int row=p.getRowIndex();
        int col=p.getColumnIndex();
        if (col - 1 >= 0) {
            leftValid=true;
            if(col-2>=0)
                if(m.getWalls()[row][col-2]==0) {
                    leftValid = false;
                }
            if(row-1>=0)
                if(m.getWalls()[row-1][col-1]==0) {
                    leftValid = false;
                }
            if(row+1<m.getRows())
                if(m.getWalls()[row+1][col-1]==0) {
                    leftValid = false;
                }
        }
        return leftValid;
    }
    public static boolean CheckRightNeighbor(Maze m,Position p)
    {
        boolean rightValid=false;
        int row=p.getRowIndex();
        int col=p.getColumnIndex();
        if (col + 1 < m.getColumns()) {
            rightValid=true;
            if(col+2 < m.getColumns())
                if(m.getWalls()[row][col+2]==0) {
                    rightValid = false;
                }
            if(row-1>=0)
                if(m.getWalls()[row-1][col+1]==0) {
                    rightValid = false;
                }
            if(row+1<m.getRows())
                if(m.getWalls()[row+1][col+1]==0) {
                    rightValid = false;
                }
        }
        return rightValid;
    }
    public static Stack<Position> GetAvailableNeighbors(Maze m, Position p) {
        if (p == null)
            return null;
        Stack<Position> k = new Stack<>();
        if (MyMazeGenerator.CheckUpNeighbor(m, p)) {
            Position pos = new Position(p.getRowIndex() - 1, p.getColumnIndex());
            k.push(pos);
        }
        if (MyMazeGenerator.CheckDownNeighbor(m, p)) {
            Position pos = new Position(p.getRowIndex() + 1, p.getColumnIndex());
            k.push(pos);
        }
        if (MyMazeGenerator.CheckLeftNeighbor(m, p)) {
            Position pos = new Position(p.getRowIndex(), p.getColumnIndex() - 1);
            k.push(pos);
        }
        if (MyMazeGenerator.CheckRightNeighbor(m, p)) {
            Position pos = new Position(p.getRowIndex(), p.getColumnIndex() + 1);
            k.push(pos);
        }
        return k;
    }
    @Override
    public Maze generate(int rows, int columns) {
        if(rows<1|columns<1)
            return null;
        Maze m=EmptyMazeGenerator.generate1(rows,columns);
        Stack<Position> Pathstk= new Stack<>();
        m.setStart(new Position(0,0));
        Pathstk.push(new Position(0,0));
        Position randNeighborPos = null;//ignore this line-only for no errors at push(152)
        while(!Pathstk.isEmpty())
        {
            Position currCell = Pathstk.pop();
            Stack<Position> Neighbors=MyMazeGenerator.GetAvailableNeighbors(m,currCell);
            if (!Neighbors.isEmpty())
            {
                int randNeighborInd=(int)Math.floor(Math.random() * (Neighbors.size()));
                int NSize=Neighbors.size();
                for (int i = 0; i < NSize; ++i)
                {
                    Position CurrNeighbor= Neighbors.pop();
                    if(i==randNeighborInd) {
                        randNeighborPos =CurrNeighbor;
                    }
                    else {
                        Pathstk.push(CurrNeighbor);
                    }
                    m.setCell(CurrNeighbor.getRowIndex(),CurrNeighbor.getColumnIndex(),0);
                }
                Pathstk.push(randNeighborPos);
            }
        }
        int randRow=(int)Math.floor(Math.random() * rows);
        int randCol=(int)Math.floor(Math.random() * columns);
        m.setEnd(null);
        while (m.getEnd()==null)
        {
            int halfRowsAndCols=(m.getRows()+m.getColumns())/2;//for making interesting mazes
            Position pos = new Position(randRow, randCol);
            if(randRow+randCol>=halfRowsAndCols & m.getWalls()[randCol][randRow]==0)
                m.setEnd(pos);
        }
        return m;
    }
}
