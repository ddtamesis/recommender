package sol;

import src.IAttributeDatum;
import src.INode;

import java.util.LinkedList;
import java.util.List;

public class Node implements INode {
    String attribute;
    List<Edge> values = new LinkedList<>(); // calculated later
    Object defaultValue; // default value, after calculation (mostCommonValue)

    public Node(String attribute, Object defaultValue) {
        this.attribute = attribute;
        this.defaultValue = defaultValue;
    }

    /*
     The lookupDecision method returns Object because the type of
     decisions can vary across datasets and individual attributes
     to be predicted (e.g. a color attribute might have a String “blue”
     value rather than just a boolean value).
     */
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
