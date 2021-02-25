package sol;

import src.IAttributeDataset;
import src.IAttributeDatum;
import src.IGenerator;
import src.INode;

import java.util.LinkedList;
import java.util.Random;

/*
 * Class for creating and interacting with a decision tree given a dataset.
 *
 * T is the type of object that we are trying to classify.
 * (like sol.Vegetable)
 */
public class TreeGenerator<T extends IAttributeDatum> implements IGenerator {

    public IAttributeDataset<T> dataset;

    /**
     * Constructor for this class.
     *
     * @param initTrainingData - IAttributeDataset of the data table
     */
    public TreeGenerator(IAttributeDataset<T> initTrainingData) {
        // TODO: Implement.
        this.dataset = initTrainingData;
    }

    @Override
    public INode buildClassifier(String targetAttr) {
        // TODO: Implement.
        // should we be trying to optimize this tree,
        // or just always start from the first attribute in the list?

        LinkedList<String> attrL = this.dataset.getAttributes();

        for (String attr : attrL) {
            if (!attr.equals(targetAttr)) {

            }
        }
        return null;
    }

    @Override
    public Object lookupRecommendation(IAttributeDatum forVals) {
        // TODO: Implement.
        // just call on top Node of tree
        return null;
    }

    @Override
    public void printTree() {
        // TODO: Implement.
    }
}