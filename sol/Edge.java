package sol;

import src.INode;

/**
 * A class representing an edge of a node in a decision tree
 */
public class Edge {
    Object value;
    INode nextNode;

    /**
     * Constructor
     *
     * @param value - an Object representing the value of the edge
     * @param nextNode - an INode representing the next node the edge leads to
     */
    public Edge(Object value, INode nextNode) {
        this.value = value;
        this.nextNode = nextNode;
    }
}
