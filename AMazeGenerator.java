package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) throws Exception {
        if(rows<2)
            throw new Exception("Not Enough Rows");
        if(columns<2)
            throw new Exception("Not Enough Columns");
        long before=System.currentTimeMillis();
        generate(rows,columns);
        long after=System.currentTimeMillis();
        return (long)after-before;
    }
    public abstract Maze generate(int rows, int columns) throws Exception;
}
