package sol;

import src.IAttributeDatum;
import src.INode;

import java.util.LinkedList;
import java.util.List;

/**
 * A class representing a node of a decision tree that implements INode
 */
public class Node implements INode {
    String attribute;
    List<Edge> values = new LinkedList<>(); // calculated later
    Object defaultValue; // default value, after calculation (mostCommonValue)

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
        Object obj = new Object();
        for (Edge e: this.values) {
            if (attrVals.getValueOf(attribute) == e.value) {
                obj = e.nextNode.lookupDecision(attrVals);
            }
        }
        return obj;
    }

    @Override
    public void printNode(String leadspace) {
    }
}
