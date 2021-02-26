package sol;

import src.IAttributeDataset;
import src.IAttributeDatum;
import src.INode;
import tester.Tester;

import java.util.Arrays;
import java.util.LinkedList;

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
//        Vegetable carrot = new Vegetable("orange", false, false, false);
        Vegetable lettuce = new Vegetable("green", true, false, true);
        LinkedList<Vegetable> rowsOfVegs = new LinkedList<>();
        rowsOfVegs.addFirst(spinach);
        rowsOfVegs.addFirst(kale);
        rowsOfVegs.addFirst(peas);
//        rowsOfVegs.addFirst(carrot);
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
        // what is Arrays.asList? I've never seen this--is it smth we're
        // allowed to use if we never did in class?
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
        t.checkExpect(dataSet.size(),4);
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
        Vegetable spinach = new Vegetable("green", true,
                true, false);
        Vegetable kale = new Vegetable("green", true,
                true, true);
        Vegetable lettuce = new Vegetable("green", true,
                false, true);
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
        Vegetable peas = new Vegetable("green", false,
                true, true);
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
        t.checkExpect(dataSet.getSharedValue("color"),
                "green");

        LinkedList<IAttributeDataset<Vegetable>> partitionedDataset =
                dataSet.partition("likeToEat");
        t.checkExpect(
                partitionedDataset.get(0).getSharedValue("likeToEat"),
                false);
        t.checkExpect(
                partitionedDataset.get(1).getSharedValue("likeToEat"),
                true);
    }

    /**
     * Tests mostCommonValue method in IAttributeDataset
     *
     * @param t - tester
     */
    public void testMostCommonValue(Tester t) {
        ListObjsData<Vegetable> dataSet = makeDataSet();
        t.checkExpect(dataSet.mostCommonValue("color"),"green");
        t.checkExpect(dataSet.mostCommonValue("likeToEat"),true);
    }

    /*
    public void testBuildClassifier(Tester t){
        ListObjsData<Vegetable> dataSet = makeDataSet();
        TreeGenerator<Vegetable> testTree = new TreeGenerator<>(dataSet);
        t.checkExpect(testTree.buildClassifier("likeToEat"),new Leaf(false));
    }*/

    /**
     * Tests lookUpDecision method in INode, predicting likeToEat
     *
     * @param t - tester
     */
    public void testLookUpDecision(Tester t) {
        Node colorNode = new Node("color", "green");
        LinkedList<Edge> colorEdges = new LinkedList<>();
        Edge colorEdgeG = new Edge("green", new Leaf(true));
        colorEdges.addFirst(colorEdgeG);
        colorNode.values = colorEdges;

        Node lowCarbNode = new Node("lowCarb", true);
        LinkedList<Edge> lowCarbEdges = new LinkedList<>();
        Edge lowCarbEdgeT = new Edge(true, colorNode);
        Edge lowCarbEdgeF = new Edge(false, new Leaf(true));
        lowCarbEdges.addFirst(lowCarbEdgeF);
        lowCarbEdges.addFirst(lowCarbEdgeT);
        lowCarbNode.values = lowCarbEdges;

        Node rootNode = new Node("highFiber", true);
        Edge rootEdgeT = new Edge(true, lowCarbNode);
        Edge rootEdgeF = new Edge(false, new Leaf(true));
        LinkedList<Edge> rootEdges = new LinkedList<>();
        rootEdges.addFirst(rootEdgeF);
        rootEdges.addFirst(rootEdgeT);
        rootNode.values = rootEdges;

        Vegetable corn = new Vegetable("yellow", false, true, true); // how to create object if likeToEat is unknown?
        Vegetable beets = new Vegetable("purple", false, false, true);

        t.checkExpect(rootNode.lookupDecision(corn), true);
        t.checkExpect(rootNode.lookupDecision(beets), true);
    }

    /**
     * main method
     */
    public static void main(String[] args) {
        Tester.run(new RecommenderTestSuite());
    }
}

/*
actual:                                 expected:
 new sol.Node:1(........................ new sol.Leaf:1(
  this.attribute =  "highFiber"           this.decision = false)
  this.values =
   new java.util.LinkedList:2(){
    Iterable[0]
     new sol.Edge:3(
      this.value = false
      this.nextNode =
       new sol.Leaf:4(
        this.decision = true)),
    Iterable[1]
     new sol.Edge:5(
      this.value = true
      this.nextNode =
       new sol.Node:6(
        this.attribute =  "lowCarb"
        this.values =
         new java.util.LinkedList:7(){
          Iterable[0]
           new sol.Edge:8(
            this.value = true
            this.nextNode =
             new sol.Node:9(
              this.attribute =  "color"
              this.values =
               new java.util.LinkedList:10(){
                Iterable[0]
                 new sol.Edge:11(
                  this.value =  "green"
                  this.nextNode =
                   new sol.Leaf:12(
                    this.decision = true))}
              this.defaultValue =  "green")),
          Iterable[1]
           new sol.Edge:13(
            this.value = false
            this.nextNode =
             new sol.Leaf:14(
              this.decision = true))}
        this.defaultValue = true))}
  this.defaultValue = true)
 */
