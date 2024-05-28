package it.polito.ski;

public class LiftType {
    String code;
    String category;
    int capacity;


    public LiftType(String code, String category, int capacity){
        this.code = code;
        this.category = category;
        this.capacity = capacity;
    }
    public int getCapacity() {
        return this.capacity;
    }
    public String getCategory() {
        return this.category;
    }
    public String getCode() {
        return this.code;
    }

}
