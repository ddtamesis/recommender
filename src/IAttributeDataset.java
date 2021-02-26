package src;

import java.util.LinkedList;

/**
 * Interface for a dataset
 *
 * @param <T> - the object type of datum
 */
public interface IAttributeDataset<T extends IAttributeDatum> {

    /**
     * Get all the attributes in the dataset
     *
     * @return the list of attributes in the dataset
     */
    public LinkedList<String> getAttributes();

    /**
     * Checks if all the rows/data have the same value for a given attribute
     *
     * @param ofAttribute - a String representing the attribute to evaluate
     * @return true if every row/datum has the same value for the given
     * attribute/column, false otherwise
     */
    public boolean allSameValue(String ofAttribute);

    /**
     * Evaluates the size of the list of rows
     *
     * @return the number of data/rows in the dataset
     */
    public int size();

    /**
     * Partitions rows into subsets such that each subset has same value of
     * onAttribute
     *
     * @param onAttribute - a String representing the attribute to partition on
     * @return a list of partitioned subsets
     */
    public LinkedList<IAttributeDataset<T>> partition(String onAttribute);

    /**
     * Get the value for an attribute, which is assumed to be common across
     * all rows
     *
     * @param ofAttribute - a String representing the attribute to get the
     *                    shared value of
     * @return the shared value of the input attribute
     */
    public Object getSharedValue(String ofAttribute);

    /**
     * Get the most common value for a given attribute in the dataset
     *
     * @param ofAttribute - a String representing the attribute to get the
     *                    most common value of
     * @return the most common value of the given attribute
     */
    public Object mostCommonValue(String ofAttribute);
}
