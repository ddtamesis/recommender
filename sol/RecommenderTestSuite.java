package sol;

import src.IAttributeDataset;
import tester.Tester;

import java.util.Arrays;
import java.util.LinkedList;

public class RecommenderTestSuite {

    public RecommenderTestSuite(){}

    public static ListObjsData<Vegetable> makeDataSet() {
        LinkedList<String> attr = createAttributes();
        LinkedList<Vegetable> rowOfVegs = createVegetables();
        return new ListObjsData<>(attr, rowOfVegs);
    }

    public static LinkedList<String> createAttributes() {
        LinkedList<String> attributes = new LinkedList<>();
        String color = "color";
        String lowCarb = "lowCarb";
        String highFiber = "highFiber";
        String likeToEat = "likeToEat";
        attributes.addFirst(likeToEat);
        attributes.addFirst(highFiber);
        attributes.addFirst(lowCarb);
        attributes.addFirst(color);
        return attributes;
    }

    public static LinkedList<String> createPartitionedAttributes() {
        LinkedList<String> attributes = new LinkedList<>();
        String color = "color";
        String highFiber = "highFiber";
        String likeToEat = "likeToEat";
        attributes.addFirst(likeToEat);
        attributes.addFirst(highFiber);
        attributes.addFirst(color);
        return attributes;
    }

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

    public static LinkedList<Vegetable> createPartitionedVegs() {
        Vegetable spinach = new Vegetable("green", true, true, false);
        Vegetable kale = new Vegetable("green", true, true, true);
        Vegetable lettuce = new Vegetable("green", true, false, true);
        LinkedList<Vegetable> rowsOfVegs = new LinkedList<>();
        rowsOfVegs.addFirst(lettuce); // why does order need to be switched?
        // b/c partition does addFirst each time it encounters the matching attr
        // to add to the subset going thru the list (instead of addLast)
        // so the order is reversed
        rowsOfVegs.addFirst(kale);
        rowsOfVegs.addFirst(spinach);
        return rowsOfVegs;
    }

    public void testGetValueOf(Tester t) {
        Vegetable spinach = new Vegetable("green", true, true, false);
        Vegetable carrot = new Vegetable("orange", false, false, false);

        t.checkExpect(spinach.getValueOf("color"), "green");
        t.checkExpect(carrot.getValueOf("color"), "orange");
        t.checkExpect(spinach.getValueOf("highFiber"), true);
        t.checkException(
                new RuntimeException("Attribute texture does not exist in Vegetable class"),
                carrot, "getValueOf", "texture");
    }

    public void testGetAttributes(Tester t) {
        ListObjsData<Vegetable> dataSet = makeDataSet();
        LinkedList<String> testAttr = new LinkedList<>(Arrays.asList("color","lowCarb","highFiber","likeToEat"));
        // what is Arrays.asList? I've never seen this--is it smth we're allowed to use if we never did in class?
        t.checkExpect(dataSet.getAttributes(), testAttr);
    }

    public void testAllSameValue(Tester t) {
        ListObjsData<Vegetable> dataSet = makeDataSet();
        t.checkExpect(dataSet.allSameValue("color"),true);
        t.checkExpect(dataSet.allSameValue("likeToEat"),false);
    }

    public void testSize(Tester t) {
        ListObjsData<Vegetable> dataSet = makeDataSet();
        t.checkExpect(dataSet.size(),4);
    }

    public void testPartition(Tester t) {
        ListObjsData<Vegetable> dataSet = makeDataSet();

        LinkedList<String> testAttr = createPartitionedAttributes();
        LinkedList<Vegetable> partition1 = createPartitionedVegs();
        LinkedList<Vegetable> partition2 = new LinkedList<>();
        Vegetable peas = new Vegetable("green", false, true, true);
        partition2.addFirst(peas);
        ListObjsData<Vegetable> testDataSet1 = new ListObjsData<>(testAttr, partition1);
        ListObjsData<Vegetable> testDataSet2 = new ListObjsData<>(testAttr, partition2);
        LinkedList<IAttributeDataset<Vegetable>> testPartition = new LinkedList<>();
        testPartition.addFirst(testDataSet1);
        testPartition.addFirst(testDataSet2);
        t.checkExpect(dataSet.partition("lowCarb"), testPartition);
    }

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

    public void testMostCommonValue(Tester t) {
        ListObjsData<Vegetable> dataSet = makeDataSet();
        t.checkExpect(dataSet.mostCommonValue("color"),"green");
        t.checkExpect(dataSet.mostCommonValue("likeToEat"),true);
    }

    /**
     * main method
     */
    public static void main(String[] args) {
        Tester.run(new RecommenderTestSuite());
    }
}
