package algorithms.search;

public class AState {
    AState StartState;
    Node node;
    int cost;
    public AState getStartState() {
        return StartState;
    }

    public Node getNode() {
        return node;
    }

    public int getCost() {
        return cost;
    }

    public AState(AState startState, Node node, int cost) {
        StartState = startState;
        this.node = node;
        this.cost = cost;
    }

    public void setStartState(AState startState) {
        StartState = startState;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
