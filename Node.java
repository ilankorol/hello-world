package algorithms.search;

public abstract class Node implements java.io.Serializable{
    private Object data;

    public Node(Object data) throws Exception {
        if(data==null)
            throw new Exception("Data Is null");
        this.data = data;
    }

    public Object getData() {
        return data;
    }





}
