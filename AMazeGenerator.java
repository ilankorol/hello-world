package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        if(rows<1||columns<1)
            return -1;
        long before=System.currentTimeMillis();
        generate(rows,columns);
        long after=System.currentTimeMillis();
        return (long)after-before;
    }
}
