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

    public LinkedList<String> attributes; // lists names of columns
    public LinkedList<T> rows;

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

    /*
    public ListObjsData<T> subsetFilter(String attr) {
        LinkedList<T> subsetRows = new LinkedList<>();
        Object firstVal = this.rows.get(0).getValueOf(attr);
        for (T r : this.rows) {
            if (r.getValueOf(attr) == firstVal) {
                subsetRows.addFirst(r);
                this.rows.remove(r);
            }
        }
        return new ListObjsData<T>(this.attributes, subsetRows);
    }

    public LinkedList<IAttributeDataset<T>> partition2(String onAttribute) {
        if (this.rows.isEmpty()) {
            ...
        } else {
            ListObjsData<T> subset = this.subsetFilter(onAttribute);
            this.partition2(onAttribute).addFirst(subset);
        }
    }*/

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
        return null;
    }

    @Override
    public Object getSharedValue(String ofAttribute) {
        // TODO: Implement.
        return this.rows.get(0).getValueOf(ofAttribute);
    }

    @Override
    public Object mostCommonValue(String ofAttribute) {
        // TODO: Implement.
        int longest = 0;
        IAttributeDataset<T> longestSubset = 
                new ListObjsData<>(new LinkedList<String>(), new LinkedList<T>());
        
        LinkedList<IAttributeDataset<T>> partitionedL = this.partition(ofAttribute);
        
        for (IAttributeDataset<T> lod : partitionedL) {
            if (lod.size() > longest) {
                longest = lod.size();
                longestSubset = lod;
            }
        }
        return longestSubset.getSharedValue(ofAttribute);
    }
}
