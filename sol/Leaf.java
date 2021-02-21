package sol;

import src.IAttributeDatum;
import src.INode;


public class Leaf implements INode {

    Object decision;

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

