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

    @Override
    public LinkedList<IAttributeDataset<T>> partition(String onAttribute) {
        LinkedList<IAttributeDataset<T>> listToReturn = new LinkedList<>();
        LinkedList<Object> evaluatedVals = new LinkedList<>();
        // remove onAttribute from listToReturn to make sure we don't use it again
        this.attributes.remove(onAttribute); // is it ok to do this.attributes????
        // nested for loop
        for (T r1 : rows) {
            Object r1Value = r1.getValueOf(onAttribute);
            if (!evaluatedVals.contains(r1Value)) {
                LinkedList<T> subsetRows = new LinkedList<>();
                for (T r2 : rows) {
                    Object r2Value = r2.getValueOf(onAttribute);
                    if (r1Value.equals(r2Value)) {
                        subsetRows.addFirst(r2);
                    }
                }
                evaluatedVals.addFirst(r1Value);
                listToReturn.addFirst(new ListObjsData<T>(this.attributes, subsetRows));
            }
        }
        return listToReturn;
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