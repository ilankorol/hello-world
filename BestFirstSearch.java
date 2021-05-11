package algorithms.search;

import java.util.ArrayList;
import java.util.Iterator;

public class BestFirstSearch extends ASearchingAlgorithm implements java.io.Serializable{

    protected AState FindBestState()
    {
        AState currState;
        AState BestScoreState=openList.peek();
        if(!openList.isEmpty()) {
            BestScoreState = openList.peek();
        }
        int lowestScore=BestScoreState.cost;
        Iterator<AState> iter=openList.iterator();
        while(iter.hasNext())
        {
            currState=iter.next();
            if(currState.cost<lowestScore)
                BestScoreState=currState;
        }
        return BestScoreState;
    }

    protected void WhileLoop(ISearchable s,boolean CountCostOrNot) throws Exception {
        if(s==null)
            throw new Exception("Searchable Is null");
        boolean condition;
        if(CountCostOrNot==true)
            condition=(!openList.isEmpty());
        else
            condition=(s.getGoalState().getPreviousState()==null);
        AState LowestScoreState=s.getStartState();
        while(condition)
        {
            LowestScoreState=FindBestState();
            if(!openList.isEmpty()) {
                openList.remove(LowestScoreState);
            }
            closedList.add(LowestScoreState);
            ArrayList<AState> successors=s.getAllSuccessors(LowestScoreState);
            Iterator<AState> sIter=successors.iterator();
            while(sIter.hasNext())
            {
                AState currSuccessor=sIter.next();
                if(!openContains(currSuccessor))
                    openList.add(currSuccessor);
            }
            if(CountCostOrNot==true)
                condition=(!openList.isEmpty());
            else
                condition=(s.getGoalState().getPreviousState()==null);
        }
    }
    @Override
    public Solution solve(ISearchable s) throws Exception {
        if(s==null)
            throw new Exception("Searchable Is null");
        s.getStartState().setCost(-1);
        s.getGoalState().setCost(0);
        this.openList.add(s.getStartState());
        s.getGoalState().setPreviousState(null);
        WhileLoop(s,true);
        setVisitedNodes(closedList.size());
        return SolGetter(s);
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }
}
