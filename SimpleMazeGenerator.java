package algorithms.mazeGenerators;
import java.util.*;

public class SimpleMazeGenerator extends AMazeGenerator{

    public Maze generate(int rows, int columns) {
        Maze m=EmptyMazeGenerator.generate1(rows,columns);
        int UnableToGetToCells=rows*columns;
        m.setStart(new Position(0,0));
        m.setCell(0,0,0);
        while(UnableToGetToCells>0) {
            UnableToGetToCells=0;
            for (int currRow = 0; currRow < rows; currRow++) {
                for (int currCol = 0; currCol < columns; currCol++) {
                    boolean Unable = true;
                    if (m.getWalls()[currRow][currCol] == 0)
                        Unable = false;
                    if (currRow - 1 >= 0)
                    {
                        if(m.getWalls()[currRow - 1][currCol] == 0)
                            Unable = false;
                    }
                    if (currCol - 1 >= 0)
                    {
                        if(m.getWalls()[currRow][currCol - 1] == 0)
                            Unable = false;
                    }
                    if (currRow + 1 < rows)
                    {
                        if(m.getWalls()[currRow + 1][currCol] == 0)
                            Unable = false;
                    }
                    if (currCol + 1 < columns )
                    {
                        if(m.getWalls()[currRow][currCol + 1] == 0)
                            Unable = false;
                    }
                    if (Unable) {
                        UnableToGetToCells++;
                        m.setCell(currRow, currCol, (int) Math.floor(Math.random() * 2));
                    }
                }
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
