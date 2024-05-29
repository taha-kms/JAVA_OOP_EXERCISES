package it.polito.med;

public class Slot {
    String info;
    String start;
    String end;
    String date;


    public Slot(String date, String info){
        this.info = info;
        this.date = date;
        this.start = this.info.split("-")[0];
        this.end = this.info.split("-")[1];


    }
    public String getDate() {
        return this.date;
    }
    public String getInfo() {
        return this.info;
    }

    public String getEnd() {
        return end;
    }
    public String getStart() {
        return start;
    }
}
