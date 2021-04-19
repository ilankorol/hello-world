package algorithms.search;

public abstract class AState implements Comparable<AState> {
    protected AState PreviousState;
    protected Node node;
    protected int cost;
    protected int AmountOfPreviousStates;
    public AState getPreviousState() {
        return PreviousState;
    }
    public Node getNode()
    {
        return this.node;
    }

    public int getCost() {
        return cost;
    }

    public AState(AState previousState, Node node, int cost) throws Exception {
        if(node==null)
            throw new Exception("node is null");
        if(cost<0)
            throw new Exception("No Negative Cost");
        PreviousState = previousState;
        this.node = node;
        this.cost = cost;
        if(previousState!=null)
            AmountOfPreviousStates=previousState.AmountOfPreviousStates+1;
        else
            AmountOfPreviousStates=0;
    }

    public void setPreviousState(AState previousState) {
        this.PreviousState = previousState;
    }

    public void setNode(Node node) throws Exception {
        if(node==null)
            throw new Exception("node is null");
        this.node = node;
    }

    public void setCost(int cost) throws Exception {
        this.cost = cost;
    }
    @Override
    public abstract int compareTo(AState o);
    @Override
    public abstract String toString();


}
