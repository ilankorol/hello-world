package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState{

    public MazeState(AState previousState, Node node, int cost) {
        super(previousState, node, cost);
    }

    @Override
    public int compareTo(AState o) {
        if(((Position)this.node.getData()).getRowIndex()==((Position)o.node.getData()).getRowIndex()&&
                ((Position)this.node.getData()).getColumnIndex()==((Position)o.node.getData()).getColumnIndex())
            return 0;
        return -1;
    }

    @Override
    public String toString() {return node.getData().toString();}

}
