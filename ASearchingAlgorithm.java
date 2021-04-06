package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    protected PriorityQueue<AState> openList;
    protected PriorityQueue<AState> closedList;
    private int visitedNodes;

    private boolean MyContains(PriorityQueue<AState> q,AState s)
    {
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
    public boolean openContains(AState s)
    {
        return MyContains(openList,s);
    }
    public boolean closedContains(AState s)
    {
        return MyContains(closedList,s);
    }

    public void setVisitedNodes(int visitedNodes) {
        this.visitedNodes = visitedNodes;
    }

    public ASearchingAlgorithm(){
        openList=new PriorityQueue<AState>();
        closedList=new PriorityQueue<AState>();
        visitedNodes=0;
    }
    protected AState popOpenList(){
        visitedNodes++;
        return openList.poll();
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return visitedNodes;
    }

}
