package sol;

import src.IAttributeDatum;
import src.INode;

import java.util.List;

public class Leaf implements INode {

    boolean likeToEat;
    List<Boolean> finalValues;

    public Leaf(){}

    @Override
    public Object lookupDecision(IAttributeDatum attrVals) {
        if (!this.finalValues.isEmpty()) {
            if (this.finalValues.size() == 1) {
                return this.finalValues.get(0);
            } // otherwise, toss up for most common value (maybe node??)
        } // if no corresponding value, need to go up the prev node?
        return null;
    }

    @Override
    public void printNode(String leadspace) {
        System.out.println(this);
    }
}
