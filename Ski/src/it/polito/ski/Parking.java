package it.polito.ski;

import java.util.ArrayList;
import java.util.List;

public class Parking {

    private String name;
    private int slotNum;
    private List<Lift> servedLifts;

    public Parking(String name, int slotNum){

        this.name = name;
        this.slotNum = slotNum;
        this.servedLifts = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public List<Lift> getServedLifts() {
        return this.servedLifts;
    }

    public int getSlotNum() {
        return this.slotNum;
    }

    public void addToServedList(Lift lift){
        this.servedLifts.add(lift);
    }

    public boolean isProportionate(){
        
        int sum = 1;

        for(Lift lift: this.servedLifts) {sum = lift.getType().getCapacity() + sum;}
    
        return ((this.slotNum / sum) > 30);
    }
}
