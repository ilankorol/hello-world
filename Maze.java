package algorithms.mazeGenerators;

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

    public Position getStartPosition() {
        return Start;
    }

    public Position getGoalPosition() {
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
