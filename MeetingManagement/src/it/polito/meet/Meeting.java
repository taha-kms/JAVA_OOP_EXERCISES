package it.polito.meet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;

public class Meeting {

    boolean pollOpen;
    String title;
    String topic;
    String category;
    Map<String, List<Slot>> slots;
    ArrayList<String> preferedSlots;

    public Meeting(String title, String topic, String category) {
        this.title = title;
        this.topic = topic;
        this.category = category;
        this.slots = new HashMap<>();
        this.preferedSlots = new ArrayList<>();
    }

    public void setpollOpen() {
        this.pollOpen = true;

        slots.entrySet()
                .stream()
                .forEach(meeting -> meeting.getValue()
                        .stream()
                        .forEach(slot -> slot.openPoll()));
    }

    public void setPollClose() {

        this.pollOpen = false;
    }

    public boolean getpollstatus() {

        return pollOpen;
    }

    public double addOption(String date, String startTime, String endTime) {

        // Check if the date already exists
        for (List<Slot> slotList : slots.values()) {
            for (Slot existingSlot : slotList) {
                if (date.equals(existingSlot.getDate())) {
                    LocalTime newStartTime = LocalTime.parse(startTime);
                    LocalTime newEndTime = LocalTime.parse(endTime);
                    LocalTime existingStartTime = LocalTime.parse(existingSlot.getStartTime());
                    LocalTime existingEndTime = LocalTime.parse(existingSlot.getEndTime());

                    // Check for overlap
                    if ((newStartTime.isAfter(existingStartTime) && newStartTime.isBefore(existingEndTime)) ||
                            (newEndTime.isAfter(existingStartTime) && newEndTime.isBefore(existingEndTime)) ||
                            (newStartTime.isBefore(existingStartTime) && newEndTime.isAfter(existingEndTime))) {
                        // There is an overlap, so return -1 to indicate failure
                        return -1;
                    }
                }
            }
        }

        // Add the new slot to the map
        Slot slot = new Slot(date, startTime, endTime);

        // creates a list or Add the new slot to the list of slots for the given date
        slots.computeIfAbsent(date, k -> new ArrayList<>()).add(slot);

        // Parse start and end time strings to LocalTime objects
        LocalTime parsedStartTime = LocalTime.parse(startTime);
        LocalTime parsedEndTime = LocalTime.parse(endTime);

        // Calculate duration in hours
        long durationSeconds = parsedStartTime.until(parsedEndTime, ChronoUnit.SECONDS);
        double durationHours = durationSeconds / 3600.0; // 3600 seconds in an hour

        return durationHours;
    }

    public Map<String, List<String>> showSlots() {
        
        Map<String, List<String>> slotsInfo = new HashMap<>();

        for (Map.Entry<String, List<Slot>> entry : slots.entrySet()) {

            String date = entry.getKey();
            List<Slot> slotList = entry.getValue();
            List<String> slotInfoList = new ArrayList<>();

            for (Slot slot : slotList) {
                String slotInfo = slot.getStartTime() + "-" + slot.getEndTime();
                slotInfoList.add(slotInfo);
            }
            slotsInfo.put(date, slotInfoList);
        }

        return slotsInfo;

    }

    public ArrayList<String> getPreferedSlots() {
        
        Set<String> preferedSlotsSet = new HashSet<>();

        for (Map.Entry<String, List<Slot>> entry : slots.entrySet()) {
            String date = entry.getKey();
            List<Slot> slotList = entry.getValue();

            slotList.forEach(slot -> {
                slot.preferences.forEach(preference -> {
                    if(preference != null){
                        preferedSlotsSet.add(date + "T" + slot.getStartTime() + "-" + slot.getEndTime() + "=" + slot.getPreferenceCount());
                    }
                });
            });
        }
        
        // Convert the set to ArrayList
        ArrayList<String> preferedSlotsList = new ArrayList<>(preferedSlotsSet);
        
        return preferedSlotsList;
}
}