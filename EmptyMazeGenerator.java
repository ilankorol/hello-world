package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    public EmptyMazeGenerator() {
    }
    public static Maze generate1(int rows, int columns) throws Exception {
        if(rows<2)
            throw new Exception("Not Enough Rows");
        if(columns<2)
            throw new Exception("Not Enough Columns");
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
        return m;
    }
    @Override
    public Maze generate(int rows, int columns) throws Exception {
        if(rows<2)
            throw new Exception("Not Enough Rows");
        if(columns<2)
            throw new Exception("Not Enough Columns");
        Maze m=EmptyMazeGenerator.generate1(rows,columns);
        for(int i=0;i<rows;i++) {
            for (int j = 0; j < columns; j++) {
                m.setCell(i,j,0);
            }
        }
        int randRow=(int)Math.floor(Math.random() * rows);
        int randCol=(int)Math.floor(Math.random() * columns);
        while(randRow==0&&randCol==0)
        {
            randRow=(int)Math.floor(Math.random() * rows);
            randCol=(int)Math.floor(Math.random() * columns);
        }
        m.setStart(new Position(0,0));
        m.setEnd(new Position(randRow,randCol));
        return m;
    }
}
