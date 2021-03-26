package algorithms.mazeGenerators;
import java.util.*;

public class SimpleMazeGenerator extends AMazeGenerator{
    @Override
    public Maze generate(int rows, int columns) {
        if(rows<1|columns<1)
            return null;
        Maze m=new Maze();
        m.setRows(rows);
        m.setColumns(columns);
        int[][] map=new int[rows][columns];
        for(int i=0;i<rows;i++) {
            for (int j = 0; j < columns; j++) {
                map[i][j] = 1;
            }
        }
        m.setWalls(map);
        Stack<Position> Pathstk= new Stack<>();
        int randRow=(int)Math.floor(Math.random() * rows);
        int randCol=(int)Math.floor(Math.random() * columns);
        Pathstk.push(new Position(randRow,randCol));
        while(!Pathstk.isEmpty())
        {
            Position currCell = Pathstk.pop();
            Stack<Position> Neighbors=m.GetAvailableNeighbors(currCell);
            if (!Neighbors.isEmpty())
            {
                Position randNeighborPos = null;//ignore this line-only for no errors at push(42)
                int randNeighborInd=(int)Math.floor(Math.random() * (Neighbors.size()));
                for (int i = 0; i < Neighbors.size(); ++i)
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
        //need to add here a start and end positions to m
        return m;
    }
}
