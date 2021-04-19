package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    protected PriorityQueue<AState> openList;
    protected PriorityQueue<AState> closedList;
    private int visitedNodes;

    private boolean MyContains(PriorityQueue<AState> q,AState s) throws Exception {
        if(q==null)
            throw new Exception("The Queue Is null");
        if(s==null)
            throw new Exception("The State Is null");
        AState curr;
        Iterator<AState> iter=q.iterator();
        while(iter.hasNext())
        {
            curr=iter.next();
            if(curr.compareTo(s)==0)
                return true;
        }
        return false;
    }
    public boolean openContains(AState s) throws Exception {
        if(s==null)
            throw new Exception("The State Is null");
        return MyContains(openList,s);
    }

    public void setVisitedNodes(int visitedNodes) throws Exception {
        if(visitedNodes<1)
            throw new Exception("Not Enough Visited Nodes");
        this.visitedNodes = visitedNodes;
    }

    public ASearchingAlgorithm(){
        openList=new PriorityQueue<AState>();
        closedList=new PriorityQueue<AState>();
        visitedNodes=0;
    }
    protected Solution SolGetter(ISearchable s) throws Exception {
        if(s==null)
            throw new Exception("Searchable Is null");
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
    public int getNumberOfNodesEvaluated() {
        return visitedNodes;
    }

}
