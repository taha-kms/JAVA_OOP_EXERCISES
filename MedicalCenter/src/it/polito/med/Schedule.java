package it.polito.med;

import java.util.*;

public class Schedule {
    
    String start;
    String end;
    String date;
    int duration;

    List<String> slots;

    public Schedule(String date, String start, String end, int duration){
        this.date = date;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.slots = new ArrayList<>();
        this.defineSlots();
    }

    public String getDate() {
        return this.date;
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public String getEnd() {
        return this.end;
    }
    
    public List<String> getSlots() {
        return this.slots;
    }
    
    public String getStart() {
        return this.start;
    }


    public void defineSlots() {
        int startHour = Integer.parseInt(start.split(":")[0]);
        int startMinute = Integer.parseInt(start.split(":")[1]);
        int endHour = Integer.parseInt(end.split(":")[0]);
        int endMinute = Integer.parseInt(end.split(":")[1]);
    
        int totalTimeInMinutes = startMinute + endMinute + ((endHour - startHour) * 60);
        int numberOfSlots = totalTimeInMinutes / this.duration;
    
        int lastSlotMin = startMinute;
        int lastSlotHour = startHour;
    
        for (int i = 0; i < numberOfSlots && (lastSlotHour < endHour || (lastSlotHour == endHour && lastSlotMin < endMinute)); i++) {
            int newSlotMin = lastSlotMin + this.duration;
            int newSlotHour = lastSlotHour + (newSlotMin / 60);
            newSlotMin = newSlotMin % 60;
    
            // If the new slot is beyond the end time, break the loop
            if (newSlotHour > endHour || (newSlotHour == endHour && newSlotMin > endMinute)) {
                break;
            }
    
 
            String startTime = String.format("%02d:%02d", lastSlotHour, lastSlotMin);
            String endTime = String.format("%02d:%02d", newSlotHour, newSlotMin);
            String result = startTime + "-" + endTime;


            this.slots.add(result);
            lastSlotHour = newSlotHour;
            lastSlotMin = newSlotMin;
        }
    }


    public int getSlotsSize(){
       return this.slots.size();
    }
}
