package algorithms.search;

import java.util.ArrayList;

public class Solution {
    private ArrayList<AState> SolutionPath;
 
    public Solution(ArrayList<AState> solutionPath) throws Exception {
        if(solutionPath==null)
            throw new Exception("Path Is null");
        SolutionPath = solutionPath;
    }

    public ArrayList<AState> getSolutionPath(){
        return SolutionPath;
    }

    public void print()
    {
        ArrayList<AState> solutionPath = getSolutionPath();
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s.%s", i, solutionPath.get(i)));
        }
    }
}
