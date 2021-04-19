package algorithms.mazeGenerators;

public class Position {
    private int row;
    private int column;

    public String toString() {
        return "{"+row+","+column+"}";
    }

    public Position(int row, int column) throws Exception {
        if(row < 0)
            throw new Exception("There's No Negative Rows");
        if(column < 0)
            throw new Exception("There's No Negative Columns");
        this.row = row;
        this.column = column;

    }


    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
        return column;
    }
}
