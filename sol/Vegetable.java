package sol;

import src.IAttributeDatum;

/**
 * Class representing a vegetable like examples in handout to use in testing
 */
public class Vegetable implements IAttributeDatum {

    String color;
    Boolean lowCarb;
    Boolean highFiber;
    Boolean likeToEat;

    /**
     * Constructor
     *
     * @param color - a String representing the color
     * @param lowCarb - a Boolean representing if it is low carb
     * @param highFiber - a Boolean representing if it is high fiber
     * @param likeToEat - a Boolean representing if it we like to eat it
     */
    public Vegetable(String color, Boolean lowCarb, Boolean highFiber, Boolean likeToEat){
        this.color = color;
        this.lowCarb = lowCarb;
        this.highFiber = highFiber;
        this.likeToEat = likeToEat;
    }

    @Override
    public Object getValueOf(String attributeName) {
        switch (attributeName){
            case "color":
                return this.color;
            case "lowCarb":
                return this.lowCarb;
            case "highFiber":
                return this.highFiber;
            case "likeToEat":
                return this.likeToEat;
            default:
                throw new RuntimeException("Attribute " +
                        attributeName + " does not exist in Vegetable class");
        }
    }
}
