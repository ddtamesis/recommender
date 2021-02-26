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
    public INode root;

    /**
     * Constructor for this class.
     *
     * @param initTrainingData - IAttributeDataset of the data table
     */
    public TreeGenerator(IAttributeDataset<T> initTrainingData) {
        // TODO: Implement.
        this.dataset = initTrainingData;
        this.root = null;
    }

    @Override
    public INode buildClassifier(String targetAttr) {
        // TODO: Implement.
        // should we be trying to optimize this tree,
        // or just always start from the first attribute in the list?

        /*
        LinkedList<IAttributeDataset<T>> partitionedData =
                this.dataset.partition(targetAttr);

        for (IAttributeDataset<T> subset : partitionedData) {
            INode decision = new Leaf(subset.getSharedValue(targetAttr));
            for (String attr : subset.getAttributes()) {

            }
        } */

        LinkedList<String> attrL = this.dataset.getAttributes();

        for (String attr : attrL) {
            if (!attr.equals(targetAttr)) {
                LinkedList<IAttributeDataset<T>> partitionedData =
                        this.dataset.partition(attr);

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