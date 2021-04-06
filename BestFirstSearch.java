package algorithms.search;

import java.util.ArrayList;
import java.util.Iterator;

public class BestFirstSearch extends ASearchingAlgorithm{

    @Override
    public Solution solve(ISearchable s) {
        this.openList.add(s.getStartState());
        AState LowestScoreState=s.getStartState();
        AState currState;
        int lowestScore;
        while(!openList.isEmpty())//s.getGoalState().getPreviousState()==null
        {
            if(!openList.isEmpty()) {
                LowestScoreState = openList.peek();
            }
            lowestScore=LowestScoreState.cost;
            Iterator<AState> iter=openList.iterator();
            while(iter.hasNext())
            {
                currState=iter.next();
                if(currState.cost<lowestScore)
                    LowestScoreState=currState;
            }
            if(!openList.isEmpty()) {
                openList.remove(LowestScoreState);
            }
            closedList.add(LowestScoreState);
            ArrayList<AState> successors=s.getAllSuccessors(LowestScoreState);
            Iterator<AState> sIter=successors.iterator();
            while(sIter.hasNext())
            {
                AState currSuccessor=sIter.next();
                if((!closedContains(currSuccessor)) && (!openContains(currSuccessor)))
                    openList.add(currSuccessor);
            }
        }
        setVisitedNodes(closedList.size());
        ArrayList<AState> path=new ArrayList<>();
        AState currPathState=s.getGoalState();
        while(currPathState!=null)
        {
            path.add(currPathState);
            currPathState=currPathState.getPreviousState();
        }
        ArrayList<AState> revPath=new ArrayList<>();
        System.out.println(path.get(0));
        for (int i = path.size()-2; i >= 0; i--) {
            revPath.add(path.get(i));
        }
        Solution sol=new Solution(revPath);
        return sol;
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }
}
