package sol;

import src.IAttributeDatum;
import src.INode;

import java.util.LinkedList;
import java.util.List;

/**
 * A class representing a node of a decision tree that implements INode
 */
public class Node implements INode {
    public String attribute;
    public LinkedList<Edge> values = new LinkedList<>();
    public Object defaultValue;

    /**
     * Constructor
     *
     * @param attribute - a String representing the attribute for the node
     * @param defaultValue - an Object representing the default value
     *                     if a value arises that is not in the original dataset
     */
    public Node(String attribute, Object defaultValue) {
        this.attribute = attribute;
        this.defaultValue = defaultValue;
    }

    @Override
    public Object lookupDecision(IAttributeDatum attrVals) {
        for (Edge e: this.values) {
            if (attrVals.getValueOf(this.attribute).equals(e.value)) {
                return e.nextNode.lookupDecision(attrVals);
            }
        }
        return this.defaultValue;
    }

    @Override
    public void printNode(String leadspace) {
    }
}
