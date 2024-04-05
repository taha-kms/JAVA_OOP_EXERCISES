package it.polito.meet;

import java.util.ArrayList;
import java.util.Collection;

public class Slot {

    private String date;
    private String startTime;
    private String endTime;
    Collection<Preference> preferences;

    public Slot(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.preferences = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void openPoll() {
        if(preferences == null){preferences.clear();}
    }

    public void addPreference(String email, String name, String surname) {
        Preference preference = new Preference(email, name, surname);
        preferences.add(preference);
    }


    public int getPreferenceCount(){
        return preferences.size();
    }

    public Collection<Preference> getPreferences(){
        return preferences;
    }
}





