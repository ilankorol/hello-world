package algorithms.search;

public abstract class AState implements Comparable<AState> {
    AState PreviousState;
    Node node;
    int cost;
    public AState getPreviousState() {
        return PreviousState;
    }

    public Node getNode() {
        return node;
    }

    public int getCost() {
        return cost;
    }

    public AState(AState previousState, Node node, int cost) {
        PreviousState = previousState;
        this.node = node;
        this.cost = cost;
    }

    public void setPreviousState(AState previousState) {
        this.PreviousState = previousState;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    //public abstract int compare(AState a);
    @Override
    public abstract int compareTo(AState o);
    @Override
    public abstract String toString();


}
