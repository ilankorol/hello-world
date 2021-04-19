package algorithms.search;

import java.util.Iterator;

public class BreadthFirstSearch extends BestFirstSearch{
    @Override
    public Solution solve(ISearchable s) throws Exception {
        if(s==null)
            throw new Exception("Searchable Is null");
        s.getStartState().setCost(-1);
        this.openList.add(s.getStartState());
        s.getGoalState().setPreviousState(null);
        s.getGoalState().setCost(0);
        WhileLoop(s,false);
        setVisitedNodes(closedList.size());
        return SolGetter(s);
    }
    protected AState FindBestState()
    {
        AState currState;
        AState BestScoreState=openList.peek();
        if(!openList.isEmpty()) {
            BestScoreState = openList.peek();
        }
        int lowestScore=BestScoreState.AmountOfPreviousStates;
        Iterator<AState> iter=openList.iterator();
        while(iter.hasNext())
        {
            currState=iter.next();
            if(currState.AmountOfPreviousStates<lowestScore)
                BestScoreState=currState;
        }
        return BestScoreState;
    }
    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }
}
