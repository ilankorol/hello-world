package algorithms.mazeGenerators;

public class Maze {
    private int rows;
    private int columns;
    private int[][] Walls;
    private Position start;
    private Position end;

    public void setWalls(int[][] walls) throws Exception {
        if(walls==null)
            throw new Exception("Walls Are Null");
        this.Walls = walls;
    }

    public int[][] getWalls() {
        return Walls;
    }

    public void setStart(Position start) throws Exception {
        if(start==null)
            throw new Exception("start is null");
        this.start = start;
    }

    public void setEnd(Position end) throws Exception {
        this.end = end;
    }

    public Position getStartPosition() {
        return start;
    }

    public Position getGoalPosition() {
        return end;
    }

    public void setRows(int rows) throws Exception {
        if(rows<2)
            throw new Exception("Amount Of Rows Not Enough");
        this.rows = rows;
    }

    public void setColumns(int columns) throws Exception {
        if(columns<2)
            throw new Exception("Amount Of Columns Not Enough");
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
    public void setCell(int row,int column,int val) throws Exception {
        if(row>=0&column>=0&row<rows&column<columns&(val==0|val==1))
        {
            Walls[row][column]=val;
        }
        else
            throw new Exception("invalid cell or value");

    }
    public void print()
    {
        boolean StartRedColor=false;
        boolean EndGreenColor=false;
        for(int currRow=0;currRow<getRows();currRow++)
        {
            if(currRow!=0)
                System.out.println(" }");
            System.out.print("{ ");
            for (int currCol=0;currCol<getColumns();currCol++)
            {
                StartRedColor=false;
                EndGreenColor=false;
                if(getStartPosition().getRowIndex()==currRow & getStartPosition().getColumnIndex()==currCol)
                    StartRedColor=true;
                if(getGoalPosition().getRowIndex()==currRow & getGoalPosition().getColumnIndex()==currCol)
                    EndGreenColor=true;
                if(StartRedColor)
                    System.out.print("\033[0;31m"+"S");
                if(EndGreenColor)
                    System.out.print("\033[0;32m"+"E");
                if(!StartRedColor&&!EndGreenColor)
                    System.out.print(getWalls()[currRow][currCol]);
                if(StartRedColor||EndGreenColor)
                    System.out.print("\033[0m");
                if(currCol!=columns-1)
                    System.out.print(" ");
            }
        }
        System.out.println(" }");
    }
}
