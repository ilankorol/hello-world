package algorithms.mazeGenerators;

public class Position {
    private int row;
    private int column;

    public String toString() {
        return "{"+row+","+column+"}";
    }

    public Position(int row, int column) {
        if(row > -1 & column > -1) {
            this.row = row;
            this.column = column;
        }
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
        return column;
    }
}
