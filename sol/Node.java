package sol;

import src.IAttributeDatum;
import src.INode;

import java.util.List;

public class Node implements INode {
    String attribute;
    List<Edge> values;
    Object actualValue; // hm?

    public Node(String attribute, List<Edge> values, Object actualValue) {
        this.attribute = attribute;
        this.values = values;
        this.actualValue = actualValue;
    }

    /*
     The lookupDecision method returns Object because the type of
     decisions can vary across datasets and individual attributes
     to be predicted (e.g. a color attribute might have a String “blue”
     value rather than just a boolean value).
     */
    @Override
    public Object lookupDecision(IAttributeDatum attrVals) {
        int counter = 0;
        if (attrVals == this.actualValue) {
            return attrVals;
        }
        return this.actualValue;
    }

    @Override
    public void printNode(String leadspace) {
    }
}
