package sol;

import src.IAttributeDataset;
import src.IAttributeDatum;

import java.util.LinkedList;

/*
 * Class for a specific representation of rows in a data table. This uses a list
 * of objects (one object per row).
 * The type T is the object that forms the "rows" of the data table
 */
public class ListObjsData<T extends IAttributeDatum> implements IAttributeDataset<T> {

    LinkedList<String> attributes; // lists names of columns
    LinkedList<T> rows;

    public ListObjsData(LinkedList<String> attributes, LinkedList<T> rows) {
        this.attributes = attributes;
        this.rows = rows;
    }

    @Override
    public LinkedList<String> getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean allSameValue(String ofAttribute) {
        int numSameValues = 0;
        // for loop to check value in each row & increment if matches first value
        Object firstValue = rows.get(0).getValueOf(ofAttribute);
        for (T attributeValue: rows) {
            if (attributeValue.getValueOf(ofAttribute).equals(firstValue)) {
                numSameValues++;
            }
        } // if all values same down the column, numSameValues will be the same as # of rows
        if (numSameValues == rows.size()) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size();
    }

    @Override
    public LinkedList<IAttributeDataset<T>> partition(String onAttribute) {
        // TODO: Implement.
        return null;
    }

    @Override
    public Object getSharedValue(String ofAttribute) {
        // TODO: Implement.
        return rows.get(0).getValueOf(ofAttribute);
    }

    @Override
    public Object mostCommonValue(String ofAttribute) {
        // TODO: Implement.
        int numSameValues = 0;
        int index = 0;
        Object mostCommonValue = rows.get(index).getValueOf(ofAttribute);
        // use recursion??
        while (numSameValues < rows.size()/2) {
            // for loop to check value in each row & increment if matches first value
            for (T attributeValue : rows) {
                if (attributeValue.getValueOf(ofAttribute).equals(mostCommonValue)) {
                    numSameValues = +1;
                }
            }
        }
        // if value is all the same down the column,
        // numSameValues will be the same as the number of row
        return mostCommonValue;
    }
}
