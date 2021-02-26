package src;

/**
 * A node in the decision tree
 */
public interface INode {
    // traverse tree based on attribute values to retrieve decision

    /**
     * Traverses a tree based on attribute values to retrieve a decision
     *
     * @param attrVals - an IAttributeDatum representing a given datum to
     *                 traverse the tree with
     * @return the decision
     */
    public Object lookupDecision(IAttributeDatum attrVals);

    // print tree
    public void printNode(String leadspace);
}
