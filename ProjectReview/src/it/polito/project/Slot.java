package it.polito.project;

public class Slot {
    
    String date;
    String start;
    String end;
    double length;

    public Slot(String date, String start, String end){
        this.date = date;
        this.start = start;
        this.end = end;
        this.length = calculateLength();
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
}
