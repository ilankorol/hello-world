package algorithms.search;

import java.util.ArrayList;

public class Solution {
    private ArrayList<AState> SolutionPath;
 
    public Solution(ArrayList<AState> solutionPath) {
        SolutionPath = solutionPath;
    }

    public ArrayList<AState> getSolutionPath(){
        return SolutionPath;
    }
}
