package it.polito.ski;



public class Slope {
    
    String name;
    String difficulty;
    Lift lift;

    public Slope(String name, String difficulty,Lift lift){

        this.name = name;
        this.difficulty = difficulty;
        this.lift = lift;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public Lift getLift() {
        return lift;
    }
    public String getName() {
        return name;
    }
}
