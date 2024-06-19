package it.polito.project;

import java.util.*;

public class Review {

    private boolean poolStatus;

    private String id;
    private String title;
    private String group;
    private String topic;
    private Map<String, List<Slot>> slotsMap;
    
    public Review(String id, String title, String topic, String group){
        this.title = title;
        this.topic = topic;
        this.id = id;
        this.group = group;
        this.slotsMap = new HashMap<>();
        this.poolStatus = false;

    }

    public String getGroup() {
        return group;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

	public boolean isOverlapped(String date, String start, String end) {

		for(Slot slot: this.slotsMap.getOrDefault(date, new ArrayList<>())) {
			if (start.compareTo(slot.getEnd()) < 0 && end.compareTo(slot.getStart()) > 0) {
				return true;
			}
		}
		return false;
    }

	public double addSlot(String date, String start, String end) throws ReviewException {
		if(isOverlapped(date, start, end)) throw new ReviewException("Slot overlap detected");
		Slot newSlot = new Slot(date, start, end);
		this.slotsMap.computeIfAbsent(date, k -> new ArrayList<>()).add(newSlot);
		return newSlot.getLength();
	}

    public boolean isPoolOpen(){
        return this.poolStatus;
    }

    public void setPoolOpen(){
        this.poolStatus = true;
    }

    public void setPoolClose(){
        this.poolStatus = false;
    }

    public Slot getSlot(String date,String slot) throws ReviewException{
        
        for (Slot savedSlot : this.slotsMap.getOrDefault(date, new ArrayList<>())) {
            if(savedSlot.toString().equals(slot)) return savedSlot;
        }
        return null;
    }

    public Map<String, List<Slot>> getSlotMap(){
        return this.slotsMap;
    }
}
