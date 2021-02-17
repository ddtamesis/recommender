package sol;

import src.IAttributeDatum;

public class Vegetable implements IAttributeDatum {

    String color;
    Boolean lowCarb;
    Boolean highFiber;
    Boolean likeToEat;

    public Vegetable(String color, Boolean lowCarb, Boolean highFiber,Boolean likeToEat){
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
                throw new RuntimeException("Attribute " + attributeName + "does not exist in Vegetable class");
        }
    }
}
