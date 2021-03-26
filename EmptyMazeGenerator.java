package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    public EmptyMazeGenerator() {
    }
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
                map[i][j] = 0;
            }
        }
        m.setWalls(map);
        return m;
    }
}
