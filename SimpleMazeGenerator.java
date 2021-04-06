package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator {

    public Maze generate(int rows, int columns) {
        Maze m= EmptyMazeGenerator.generate1(rows,columns);
        int UnableToGetToCells=rows*columns;
        m.setStart(new Position(0,0));
        m.setCell(0,0,0);
        for (int currRow = 0; currRow < rows; currRow++) {
            for (int currCol = 0; currCol < columns; currCol++) {
                if((int) Math.floor(Math.random() * 5)<3)
                    m.setCell(currRow, currCol,0);
            }
        }
        int randRow=(int)Math.floor(Math.random() * rows);
        int randCol=(int)Math.floor(Math.random() * columns);
        while(randRow==0&&randCol==0)
        {
            randRow=(int)Math.floor(Math.random() * rows);
            randCol=(int)Math.floor(Math.random() * columns);
        }
        int RightOrDown=0;
        int col=0;
        int row=0;
        boolean Arrived=false;
        while((row<=randRow||col<=randCol) && (!Arrived)) {
            RightOrDown = (int) Math.floor(Math.random() * 2);
            if (row == randRow)
                RightOrDown = 0;
            if (col == randCol)
                RightOrDown = 1;
            if (RightOrDown == 0)
                col++;
            if (RightOrDown == 1)
                row++;
            m.setCell(row, col, 0);
            if (row == randRow && col == randCol)
                Arrived = true;
        }
        m.setEnd(new Position(randRow,randCol));
        return m;

    }
}
