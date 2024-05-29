package it.polito.med;

public class Appointment {
    Slot slot;
    Patient patient;
    Doctor doctor;
    String date;

    public Appointment(Patient patient, Doctor doctor, Slot slot){

        this.slot = slot;
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
}
