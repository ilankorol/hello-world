package algorithms.mazeGenerators;

public interface IMazeGenerator {
    public Maze generate(int rows, int columns) throws Exception;
    public long measureAlgorithmTimeMillis(int rows, int columns) throws Exception;
}
