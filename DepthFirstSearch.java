package algorithms.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm{
    @Override
    public Solution solve(ISearchable s) throws Exception {
        if (s == null)
            throw new Exception("Searchable Is null");
        Stack<AState> stack = new Stack<>();
        s.getStartState().setCost(-1);
        stack.push(s.getStartState());
        s.getGoalState().setPreviousState(null);
        s.getGoalState().setCost(0);
        while(s.getGoalState().getPreviousState()==null)
        {
            AState currState=stack.peek();
            stack.pop();
            ArrayList<AState> neighbors=s.getAllSuccessors(currState);
            Iterator<AState> itr = neighbors.iterator();
            while(itr.hasNext())
            {
                AState neighbor=itr.next();
                stack.push(neighbor);
            }
            closedList.add(currState);
        }
        setVisitedNodes(closedList.size());
        return SolGetter(s);
    }
    @Override
    public String getName() {
        return "DepthFirstSearch";
    }
}
