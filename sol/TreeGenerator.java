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
                    this.dataset.mostCommonValue(nodeAttr));
        LinkedList<IAttributeDataset<T>> partitionRoot =
                this.dataset.partition(nodeAttr);

        LinkedList<Edge> edgeList = new LinkedList<>();

        for (IAttributeDataset<T> subset : partitionRoot) {
            Object edgeValue = subset.getSharedValue(nodeAttr);
            if (subset.size() == 0) {
                Edge edge = new Edge(edgeValue,
                        new Leaf(nodeToReturn.defaultValue));
                edgeList.addFirst(edge);
            }
            else if (subset.allSameValue(targetAttr)) {
                Edge edge = new Edge(edgeValue,
                        new Leaf(subset.getSharedValue(targetAttr)));
                edgeList.addFirst(edge);
            }
            else {
                if (subset.getAttributes().size() == 1) {
                    Leaf decision = new Leaf(subset.mostCommonValue(targetAttr));
                    Edge edge = new Edge(edgeValue,decision);
                    edgeList.addFirst(edge);
                } else {
                    TreeGenerator<T> subtree = new TreeGenerator<T>(subset);
                    Edge edge = new Edge(edgeValue,
                            subtree.buildClassifier(targetAttr));
                    edgeList.addFirst(edge);
                }
            }
        }
        nodeToReturn.values = edgeList;
        this.root = nodeToReturn;
        return nodeToReturn;
    }

    @Override
    public Object lookupRecommendation(IAttributeDatum forVals) {
        // TODO: Implement.
        // just call lookUpDecision on root Node of tree
        // add root field to TreeGenerator to call lookUpDecision on rootNode?
        // put random selection in constructor & check if it equals the targetAttr
        // in buildClassifier & change this.root if necessary

        // check if edges.contains value for particular attribute node comparison
        if (this.root == null) {
            throw new RuntimeException("Tree with target attribute has not been"
                    + " constructed");
        }
        else {
            return this.root.lookupDecision(forVals);
        }
    }

    @Override
    public void printTree() {
        // TODO: Implement.
    }
}