package it.polito.project;

import java.util.*;

public class Slot {
    
    String date;
    String start;
    String end;
    double length;
    boolean poolStatus;
    List<Preference> preferenceList;

    public Slot(String date, String start, String end){
        this.date = date;
        this.start = start;
        this.end = end;
        this.length = calculateLength();
        this.poolStatus = false;
        this.preferenceList = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public String getEnd() {
        return end;
    }

    public double getLength() {
        return length;
    }

    public String getStart() {
        return start;
    }

    private double calculateLength() {
        int startMinutes = convertToMinutes(this.start);
        int endMinutes = convertToMinutes(this.end);
        int lengthInMinutes = endMinutes - startMinutes;
        return lengthInMinutes / 60.0;
    }

    private int convertToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    public String toString(){
        return this.start + "-" + this.end;
    }

    public int addPreference(String email, String name, String surname){
        this.preferenceList.add(new Preference(email,name,surname));
        return getPreferenceSize();
    }
    
    public int getPreferenceSize(){
        return this.preferenceList.size();
    }

    public List<Preference> getPreferenceList() {
        return this.preferenceList;
    }
}
