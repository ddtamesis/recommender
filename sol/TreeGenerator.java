package sol;

import src.IAttributeDataset;
import src.IAttributeDatum;
import src.IGenerator;
import src.INode;

import java.util.LinkedList;
import java.util.Random;

/**
 * Class for creating and interacting with a decision tree given a dataset that
 * implements IGenerator
 *
 * @param <T> - the type of object that we are trying to classify,
 *           (like sol.Vegetable)
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
        this.root = null;
    }

    @Override
    public INode buildClassifier(String targetAttr) {
        LinkedList<String> attrL = this.dataset.getAttributes();

        String nodeAttr = null;

        while (nodeAttr == null || nodeAttr.equals(targetAttr)) {
            Random rand = new Random();
            int randIndex = rand.nextInt(attrL.size());
            nodeAttr = attrL.get(randIndex);
        }

        Node nodeToReturn = new Node(nodeAttr,
                    this.dataset.mostCommonValue(targetAttr));
        LinkedList<IAttributeDataset<T>> partitionFromNode =
                this.dataset.partition(nodeAttr);

        LinkedList<Edge> edgeList = new LinkedList<>();

        for (IAttributeDataset<T> subset : partitionFromNode) {
            Object edgeValue = subset.getSharedValue(nodeAttr);

            if (subset.size() == 0) {
                Leaf decision = new Leaf(nodeToReturn.defaultValue);
                Edge edge = new Edge(edgeValue, decision);
                edgeList.addFirst(edge);
            }
            else if (subset.allSameValue(targetAttr)) {
                Leaf decision = new Leaf(subset.getSharedValue(targetAttr));
                Edge edge = new Edge(edgeValue, decision);
                edgeList.addFirst(edge);
            }
            else if (subset.getAttributes().size() == 1) {
                Leaf decision = new Leaf(subset.mostCommonValue(targetAttr));
                Edge edge = new Edge(edgeValue, decision);
                edgeList.addFirst(edge);
            }
            else {
                TreeGenerator<T> subtree = new TreeGenerator<T>(subset);
                Edge edge = new Edge(edgeValue,
                        subtree.buildClassifier(targetAttr));
                edgeList.addFirst(edge);
            }
        }
        nodeToReturn.values = edgeList;
        this.root = nodeToReturn;
        return nodeToReturn;
    }

    @Override
    public Object lookupRecommendation(IAttributeDatum forVals) {
        if (this.root == null) {
            throw new RuntimeException("Tree with target attribute has not " +
                    "been constructed. Call buildClassifier first.");
        }
        else {
            return this.root.lookupDecision(forVals);
        }
    }

    @Override
    public void printTree() {
    }
}