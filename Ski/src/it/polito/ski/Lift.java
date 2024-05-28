package it.polito.ski;

public class Lift {

    String name;
    LiftType type;

    public Lift(String name, LiftType type){
        this.name = name;
        this.type = type;

    }
    public String getName() {
        return name;
    }
    public LiftType getType() {
        return type;
    }
}
