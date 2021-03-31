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
        for(int col=0;col<=randCol;col++)
        {
            m.setCell(0,col,0);
        }
        for(int row=0;row<=randRow;row++)
        {
            m.setCell(row,randCol,0);
        }
        m.setEnd(new Position(randRow,randCol));
        return m;

    }
}
