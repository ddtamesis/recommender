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
        this.dataset = initTrainingData;
    }

    @Override
    public INode buildClassifier(String targetAttr) {
        LinkedList<String> attrL = this.dataset.getAttributes();

        String nodeAttr = attrL.getFirst();

        while (nodeAttr.equals(targetAttr)) {
            Random rand = new Random();
            int randIndex = rand.nextInt(attrL.size());

            nodeAttr = attrL.get(randIndex);
        }

//        if (nodeAttr.equals(targetAttr)) {
//            nodeAttr = attrL.get(1);
//        }

        Node nodeToReturn = new Node(nodeAttr,
                    this.dataset.mostCommonValue(nodeAttr));
        LinkedList<IAttributeDataset<T>> partitionRoot =
                this.dataset.partition(nodeAttr);

        for (IAttributeDataset<T> subset : partitionRoot) {
            if (subset.size() == 0) {
                return new Leaf(nodeToReturn.defaultValue);
            }
            else if (subset.allSameValue(targetAttr)) {
                return new Leaf(subset.getSharedValue(targetAttr));
            }
            else {
                Object edgeValue = subset.getSharedValue(nodeAttr);
                TreeGenerator<T> subtree = new TreeGenerator<T>(subset);
                Edge edge = new Edge(edgeValue,
                        subtree.buildClassifier(targetAttr));
                // we forgot to create the list of edges for the nodeToReturn
            }
        }
        return nodeToReturn;
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
        System.out.println();
    }
}