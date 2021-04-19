
package algorithms.search;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    @Test
    void solve2on2() throws Exception {
        ISearchingAlgorithm solver = new BestFirstSearch();
        AMazeGenerator Gen = new MyMazeGenerator();
        Maze m=Gen.generate(2,2);
        ISearchable sMaze=new SearchableMaze(m);
        Solution sol=solver.solve(sMaze);
        int nodes=solver.getNumberOfNodesEvaluated();
        ArrayList<AState> Path = sol.getSolutionPath();
        assertEquals(sMaze.getGoalState(), Path.get(Path.size()));
        assertTrue(nodes>0);
    }
    @Test
    void solve2on1000() throws Exception {
        ISearchingAlgorithm solver = new BestFirstSearch();
        AMazeGenerator Gen = new MyMazeGenerator();
        Maze m=Gen.generate(2,1000);
        ISearchable sMaze=new SearchableMaze(m);
        Solution sol=solver.solve(sMaze);
        int nodes=solver.getNumberOfNodesEvaluated();
        ArrayList<AState> Path = sol.getSolutionPath();
        assertEquals(sMaze.getGoalState(), Path.get(Path.size()));
        assertTrue(nodes>0);
    }
    @Test
    void solve1000on2() throws Exception {
        ISearchingAlgorithm solver = new BestFirstSearch();
        AMazeGenerator Gen = new MyMazeGenerator();
        Maze m=Gen.generate(1000,2);
        ISearchable sMaze=new SearchableMaze(m);
        Solution sol=solver.solve(sMaze);
        int nodes=solver.getNumberOfNodesEvaluated();
        ArrayList<AState> Path = sol.getSolutionPath();
        assertEquals(sMaze.getGoalState(), Path.get(Path.size()));
        assertTrue(nodes>0);
    }

    @Test
    void solvenull() throws Exception {
        ISearchingAlgorithm solver = new BestFirstSearch();
        AMazeGenerator Gen = new MyMazeGenerator();
        ISearchable sMaze = null;
        try {
            Solution sol=solver.solve(sMaze);
        }
        catch (Exception e)
        {
            assertEquals("Searchable Is null",e.getMessage());
        }
    }
    @Test
    void getName() {
        ISearchingAlgorithm solver = new BestFirstSearch();
        assertEquals("BestFirstSearch", solver.getName());
    }
}




