package src;

/**
 * A node in the decision tree
 */
public interface INode {

    /**
     * Traverses a tree based on attribute values to retrieve a decision
     *
     * @param attrVals - an IAttributeDatum representing a given datum to
     *                 traverse the tree with
     * @return the decision
     */
    public Object lookupDecision(IAttributeDatum attrVals);

    // print node
    public void printNode(String leadspace);
}
