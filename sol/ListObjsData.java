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
        Object firstValue = this.rows.get(0).getValueOf(ofAttribute);
        for (T row : this.rows) {
            if (row.getValueOf(ofAttribute).equals(firstValue)) {
                numSameValues++;
            }
        } // if all values same down the column, numSameValues will be the same as # of rows
        if (numSameValues == this.rows.size()) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return this.rows.size();
    }

    @Override
    public LinkedList<IAttributeDataset<T>> partition(String onAttribute) {
        // TODO: Implement.
        LinkedList<IAttributeDataset<T>> listToReturn = new LinkedList<>();
        LinkedList<T> subsetRows1 = new LinkedList<>();
        T row1 = this.rows.get(0);
        subsetRows1.addFirst(row1);

        Object row1Value = row1.getValueOf(onAttribute);

        for (T r : this.rows) {
            Object rValue = r.getValueOf(onAttribute);

            if (rValue.equals(row1Value)) {
                subsetRows1.addFirst(r);
            }
            
            // lists created here disappear after loop closes
        }
    }

    @Override
    public Object getSharedValue(String ofAttribute) {
        // TODO: Implement.
        return this.rows.get(0).getValueOf(ofAttribute);
    }

    @Override
    public Object mostCommonValue(String ofAttribute) {
        // TODO: Implement.
        return null;
    }
}
