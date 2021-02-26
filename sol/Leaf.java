package sol;

import src.IAttributeDatum;
import src.INode;

/**
 * A class representing a decision leaf of a decision tree that implements INode
 */
public class Leaf implements INode {
    public Object decision;

    /**
     * Constructor
     *
     * @param decision - an Object representing the decision in the leaf
     */
    public Leaf(Object decision) {
        this.decision = decision;
    }

    @Override
    public Object lookupDecision(IAttributeDatum attrVals) {
        return this.decision;
    }

    @Override
    public void printNode(String leadspace) {
        System.out.println(this);
    }
}

