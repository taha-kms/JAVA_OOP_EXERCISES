package it.polito.med;

import java.util.*;
public class Doctor {
    
    private String id; 
    private String name; 
    private String surname; 
    private String speciality;
    public Map<String , Schedule> dailySchedule;

    public Doctor(String id, String name, String surname, String speciality){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.speciality = speciality;
        this.dailySchedule = new HashMap<>();
    }
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getSpeciality() {
        return speciality;
    }
    
    public String getSurname() {
        return surname;
    }

    public int getSlotNumber(String date){

        return this.dailySchedule.get(date).getSlotsSize();
    }

}
