package sol;

import src.IAttributeDataset;
import src.IAttributeDatum;

import java.util.LinkedList;

/**
 * A class representing a dataset in the form of a data table,
 * using a list of objects for rows
 *
 * @param <T> - the datum object type that forms the "rows" of the data table
 */
public class ListObjsData<T extends IAttributeDatum>
        implements IAttributeDataset<T> {

    public LinkedList<String> attributes;
    public LinkedList<T> rows;

    /**
     * Constructor
     *
     * @param attributes - a LinkedList of Strings representing the attributes,
     *                   where each string is a "column" of the dataset
     * @param rows - a LinkedList representing the rows of the dataset,
     *             where each element is a "row"
     */
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
        LinkedList<String> newAttr = new LinkedList<>();
        for (String attr : this.getAttributes()) {
            if (!attr.equals(onAttribute)) {
                newAttr.addFirst(attr);
            }
        }

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
                listToReturn.addFirst(new ListObjsData<T>(newAttr, subsetRows));
            }
        }
        return listToReturn;
    }

    @Override
    public Object getSharedValue(String ofAttribute) {
        return this.rows.get(0).getValueOf(ofAttribute);
    }

    @Override
    public Object mostCommonValue(String ofAttribute) {
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