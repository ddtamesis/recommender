package sol;

import src.IAttributeDataset;
import tester.Tester;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * A test suite for the methods in IAttributeDatum, IAttributeDataset, and INode
 */
public class RecommenderTestSuite {

    public RecommenderTestSuite(){}

    /**
     * Creates the attributes to be used in creating the dataset for testing
     *
     * @return the list of attributes (columns)
     */
    public static LinkedList<String> createAttributes() {
        LinkedList<String> attributes = new LinkedList<>();
        attributes.addFirst("likeToEat");
        attributes.addFirst("highFiber");
        attributes.addFirst("lowCarb");
        attributes.addFirst("color");
        return attributes;
    }

    /**
     * Creates the vegetables to be used in creating the dataset for testing
     *
     * @return the list of vegetables (rows)
     */
    public static LinkedList<Vegetable> createVegetables() {
        Vegetable spinach = new Vegetable("green", true, true, false);
        Vegetable kale = new Vegetable("green", true, true, true);
        Vegetable peas = new Vegetable("green", false, true, true);
        Vegetable lettuce = new Vegetable("green", true, false, true);

        LinkedList<Vegetable> rowsOfVegs = new LinkedList<>();
        rowsOfVegs.addFirst(spinach);
        rowsOfVegs.addFirst(kale);
        rowsOfVegs.addFirst(peas);
        rowsOfVegs.addFirst(lettuce);
        return rowsOfVegs;
    }

    /**
     * Creates a dataset to use in testing
     *
     * @return the test dataset
     */
    public static ListObjsData<Vegetable> makeDataSet() {
        LinkedList<String> attr = createAttributes();
        LinkedList<Vegetable> rowOfVegs = createVegetables();
        return new ListObjsData<>(attr, rowOfVegs);
    }

    /**
     * Tests getValueOf method in IAttributeDatum
     *
     * @param t - tester
     */
    public void testGetValueOf(Tester t) {
        Vegetable spinach = new Vegetable("green", true, true, false);
        Vegetable carrot = new Vegetable("orange", false, false, false);

        t.checkExpect(spinach.getValueOf("color"), "green");
        t.checkExpect(carrot.getValueOf("color"), "orange");
        t.checkExpect(spinach.getValueOf("highFiber"), true);
        t.checkException(
                new RuntimeException("Attribute texture does not exist "
                        + "in Vegetable class"), carrot, "getValueOf",
                "texture");
    }

    /**
     * Tests getAttributes method in IAttributeDataset
     *
     * @param t - tester
     */
    public void testGetAttributes(Tester t) {
        ListObjsData<Vegetable> dataSet = makeDataSet();
        LinkedList<String> testAttr = new LinkedList<>(Arrays.asList("color",
                "lowCarb", "highFiber", "likeToEat"));

        t.checkExpect(dataSet.getAttributes(), testAttr);
    }

    /**
     * Tests allSameValue method in IAttributeDataset
     *
     * @param t - tester
     */
    public void testAllSameValue(Tester t) {
        ListObjsData<Vegetable> dataSet = makeDataSet();

        t.checkExpect(dataSet.allSameValue("color"),true);
        t.checkExpect(dataSet.allSameValue("likeToEat"),false);
    }

    /**
     * Tests size method in IAttributeDataset
     *
     * @param t - tester
     */
    public void testSize(Tester t) {
        ListObjsData<Vegetable> dataSet = makeDataSet();
        t.checkExpect(dataSet.size(), 4);

        dataSet.rows = new LinkedList<>();
        t.checkExpect(dataSet.size(), 0);
    }

    /**
     * Creates the resulting attributes
     * from testing the partition method
     *
     * @return the result list of attributes
     */
    public static LinkedList<String> createPartitionedAttributes() {
        LinkedList<String> attributes = new LinkedList<>();
        attributes.addFirst("color");
        attributes.addFirst("highFiber");
        attributes.addFirst("likeToEat");
        return attributes;
    }

    /**
     * Creates first resulting partitioned subset of rows
     * from testing the partition method
     *
     * @return a first partitioned list of rows
     */
    public static LinkedList<Vegetable> createPartitionedVegs() {
        Vegetable spinach = new Vegetable("green", true, true, false);
        Vegetable kale = new Vegetable("green", true, true, true);
        Vegetable lettuce = new Vegetable("green", true, false, true);

        LinkedList<Vegetable> rowsOfVegs = new LinkedList<>();
        rowsOfVegs.addFirst(lettuce);
        rowsOfVegs.addFirst(kale);
        rowsOfVegs.addFirst(spinach);
        return rowsOfVegs;
    }

    /**
     * Tests partition method in IAttributeDataset
     *
     * @param t - tester
     */
    public void testPartition(Tester t) {
        ListObjsData<Vegetable> dataSet = makeDataSet();

        LinkedList<String> resultAttr = createPartitionedAttributes();

        LinkedList<Vegetable> partition1 = createPartitionedVegs();
        LinkedList<Vegetable> partition2 = new LinkedList<>();
        Vegetable peas = new Vegetable("green", false, true, true);
        partition2.addFirst(peas);

        ListObjsData<Vegetable> resultDataSet1 =
                new ListObjsData<>(resultAttr, partition1);
        ListObjsData<Vegetable> resultDataSet2 =
                new ListObjsData<>(resultAttr, partition2);

        LinkedList<IAttributeDataset<Vegetable>> resultPartition =
                new LinkedList<>();
        resultPartition.addFirst(resultDataSet1);
        resultPartition.addFirst(resultDataSet2);

        t.checkExpect(dataSet.partition("lowCarb"), resultPartition);
    }

    /**
     * Tests getSharedValue method in IAttributeDataset
     *
     * @param t - tester
     */
    public void testGetSharedValue(Tester t) {
        ListObjsData<Vegetable> dataSet = makeDataSet();
        t.checkExpect(dataSet.getSharedValue("color"), "green");

        LinkedList<IAttributeDataset<Vegetable>> partitionedDataset =
                dataSet.partition("likeToEat");
        t.checkExpect(partitionedDataset.get(0).getSharedValue("likeToEat"),
                false);
        t.checkExpect(partitionedDataset.get(1).getSharedValue("likeToEat"),
                true);
    }

    /**
     * Tests mostCommonValue method in IAttributeDataset
     *
     * @param t - tester
     */
    public void testMostCommonValue(Tester t) {
        ListObjsData<Vegetable> dataSet = makeDataSet();
        t.checkExpect(dataSet.mostCommonValue("color"), "green");
        t.checkExpect(dataSet.mostCommonValue("likeToEat"), true);
    }

    /**
     * Tests lookUpDecision method in INode, by testing on a manually-created
     * tree to predict likeToEat
     *
     * The tree is constructed in the following code in order from leaf to root
     *
     * @param t - tester
     */
    public void testLookUpDecision(Tester t) {
        Node colorNode = new Node("color", "green");
        LinkedList<Edge> colorEdges = new LinkedList<>();
        Edge colorEdgeG = new Edge("green", new Leaf(true));
        colorEdges.addFirst(colorEdgeG);
        colorNode.values = colorEdges;

        Node lowCarbNodeT = new Node("lowCarb", true);
        LinkedList<Edge> lowCarbNodeTEdges = new LinkedList<>();
        Edge lowCarbNodeTEdgeT = new Edge(true, colorNode);
        Edge lowCarbNodeTEdgeF = new Edge(false, new Leaf(true));
        lowCarbNodeTEdges.addFirst(lowCarbNodeTEdgeF);
        lowCarbNodeTEdges.addFirst(lowCarbNodeTEdgeT);
        lowCarbNodeT.values = lowCarbNodeTEdges;

        Node lowCarbNodeF = new Node("lowCarb", true);
        LinkedList<Edge> lowCarbNodeFEdges = new LinkedList<>();
        Edge lowCarbNodeFEdgeT = new Edge(true, new Leaf(true));
        Edge lowCarbNodeFEdgeF = new Edge(false, new Leaf(false));
        lowCarbNodeFEdges.addFirst(lowCarbNodeFEdgeF);
        lowCarbNodeFEdges.addFirst(lowCarbNodeFEdgeT);
        lowCarbNodeF.values = lowCarbNodeFEdges;

        Node rootNode = new Node("highFiber", true);
        Edge rootEdgeT = new Edge(true, lowCarbNodeT);
        Edge rootEdgeF = new Edge(false, lowCarbNodeF);
        LinkedList<Edge> rootEdges = new LinkedList<>();
        rootEdges.addFirst(rootEdgeF);
        rootEdges.addFirst(rootEdgeT);
        rootNode.values = rootEdges;

        Vegetable corn = new Vegetable("yellow", false, true, true);
        Vegetable beets = new Vegetable("purple", false, false, true);
        Vegetable testVeggie = new Vegetable("red", false, false, true);

        t.checkExpect(rootNode.lookupDecision(corn), true);
        t.checkExpect(rootNode.lookupDecision(beets), false);
        t.checkExpect(rootNode.lookupDecision(testVeggie), false);
    }

    /**
     * main method
     */
    public static void main(String[] args) {
        Tester.run(new RecommenderTestSuite());
    }
}
