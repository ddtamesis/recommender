package sol;

import src.IAttributeDatum;
import src.INode;

import java.util.List;

public class Leaf implements INode {

    List<Object> finalValues;

    public Leaf(List<Object> finalValues) {
        this.finalValues = finalValues;
    }

    /*
    public Object findMostCommonValue(List<Object> finalValues) {
        Object referenceValue = finalValues.get(0);
        for (Object obj: finalValues) {
            if (referenceValue != obj) {
                Object referenceValue2 = obj;
            }
        }
        Object mostCommonValue = value1;
        return mostCommonValue;
    }*/

    @Override
    public Object lookupDecision(IAttributeDatum attrVals) {
        if (this.finalValues.size() == 1) {
            return this.finalValues.get(0);
        } else { // otherwise, toss up for most common value (maybe node??)
            int length = this.finalValues.size();

        }
        // if no corresponding value, need to go up the prev node?
        return null;


    }

    @Override
    public void printNode(String leadspace) {
        System.out.println(this);
    }
}
