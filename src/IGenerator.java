package src;

/**
 * Interface for a generator
 */
public interface IGenerator {

    /**
     * Builds a decision tree to predict the named attribute
     *
     * @param targetAttr - a String representing the target attribute to predict
     * @return the root node that holds the entire tree
     */
    public INode buildClassifier(String targetAttr);

    /**
     * Produces the decision predicted for the given datum
     *
     * @param forVals - an IAttributeDatum representing a given datum to
     *                traverse the tree with
     * @return the recommended decision of the target attribute for the datum
     */
    public Object lookupRecommendation(IAttributeDatum forVals);

    /**
     * Print the decision tree
     */
    public void printTree();
}