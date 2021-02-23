package sol;

import src.IAttributeDataset;
import tester.Tester;

import java.util.Arrays;
import java.util.LinkedList;

public class RecommenderTestSuite {

    public RecommenderTestSuite(){}

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

    public static LinkedList<Vegetable> createVegetables(){
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

    public static LinkedList<Vegetable> createPartitionedVegs(){
        Vegetable spinach = new Vegetable("green", true, true, false);
        Vegetable kale = new Vegetable("green", true, true, true);
        Vegetable lettuce = new Vegetable("green", true, false, true);
        LinkedList<Vegetable> rowsOfVegs = new LinkedList<>();
        rowsOfVegs.addFirst(spinach);
        rowsOfVegs.addFirst(kale);
        rowsOfVegs.addFirst(lettuce);
        return rowsOfVegs;
    }

    public void testGetAttributes(Tester t) {
        LinkedList<String> attr = createAttributes();
        LinkedList<Vegetable> rowOfVegs = createVegetables();
        ListObjsData<Vegetable> dataSet = new ListObjsData<>(attr, rowOfVegs);
        LinkedList<String> testAttr = new LinkedList<>(Arrays.asList("color","lowCarb","highFiber","likeToEat"));
        t.checkExpect(dataSet.getAttributes(),testAttr);
    }

    public void testAllSameValue(Tester t) {
        LinkedList<String> attr = createAttributes();
        LinkedList<Vegetable> rowOfVegs = createVegetables();
        ListObjsData<Vegetable> dataSet = new ListObjsData<>(attr, rowOfVegs);
        t.checkExpect(dataSet.allSameValue("color"),true);
        t.checkExpect(dataSet.allSameValue("likeToEat"),false);
//        Vegetable carrot = new Vegetable("orange", false, false, false);
//        rowOfVegs.addFirst(carrot);
//        t.checkExpect(dataSet.allSameValue("color"),true);
    }


    public void testSize(Tester t) {
        LinkedList<String> attr = createAttributes();
        LinkedList<Vegetable> rowOfVegs = createVegetables();
        ListObjsData<Vegetable> dataSet = new ListObjsData<>(attr, rowOfVegs);
        t.checkExpect(dataSet.size(),4);
    }

    public void testPartition(Tester t) {
        LinkedList<String> attr = createAttributes();
        LinkedList<Vegetable> rowOfVegs = createVegetables();
        ListObjsData<Vegetable> dataSet = new ListObjsData<>(attr, rowOfVegs);

        LinkedList<Vegetable> partition1 = createPartitionedVegs();
        LinkedList<Vegetable> partition2 = new LinkedList<>();
        Vegetable peas = new Vegetable("green", false, true, true);
        partition2.addFirst(peas);
        ListObjsData<Vegetable> testDataSet1 = new ListObjsData<>(attr,partition1);
        ListObjsData<Vegetable> testDataSet2 = new ListObjsData<>(attr,partition2);
        LinkedList<IAttributeDataset<Vegetable>> testPartition = new LinkedList<>();
        testPartition.addFirst(testDataSet1);
        testPartition.addFirst(testDataSet2);
        t.checkExpect(dataSet.partition("lowCarb"),testPartition);
    }

    public void testGetSharedValue(Tester t) {
        LinkedList<String> attr = createAttributes();
        LinkedList<Vegetable> rowOfVegs = createVegetables();
        ListObjsData<Vegetable>  dataSet = new ListObjsData<>(attr, rowOfVegs);
        t.checkExpect(dataSet.getSharedValue("color"),"green");
    }

    public void testMostCommonValue(Tester t) {
        LinkedList<String> attr = createAttributes();
        LinkedList<Vegetable> rowOfVegs = createVegetables();
        ListObjsData<Vegetable>  dataSet = new ListObjsData<>(attr, rowOfVegs);
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
