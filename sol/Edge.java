package sol;

import src.INode;

public class Edge {
    String value;
    INode nextNode;

    public Edge(String value, INode nextNode) {
        this.value = value;
        this.nextNode = nextNode;
    }
}
