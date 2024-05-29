package it.polito.med;

public class Appointment {

    String id;
    Slot slot;
    Patient patient;
    Doctor doctor;
    String date;
    boolean complitionStatus;
    boolean acceptanceStatus;

    public Appointment(String id, Patient patient, Doctor doctor, Slot slot){
        this.id = id;
        this.slot = slot;
        this.complitionStatus = false;
        this.acceptanceStatus = false;
        this.doctor = doctor;
        this.patient = patient;
        this.date = this.slot.getDate();
    }

    public String getDate() {
        return date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Slot getSlot() {
        return slot;
    }

    public String toString(){
        return String.format("%s=%s", this.slot.getStart(), this.patient.getSsn());
    }

    public boolean isCompleted(){
        return this.complitionStatus;
    }

    public void setAsCompleted(){
        this.complitionStatus = true;
    }

    public String getId(){
        return this.id;
    }
    public void setAsAccepted(){
        this.acceptanceStatus = true;
    }

    public boolean isAccepted(){
        return this.acceptanceStatus;
    }
}
