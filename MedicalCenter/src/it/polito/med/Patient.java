package it.polito.med;


public class Patient {
    
    private String ssn;
    private String name;
    private String surname;

    public Patient(String ssn, String name, String surname){
        this.ssn = ssn;
        this.name = name;
        this.surname = surname;
    }

    public String toString(){
        return this.name + " " + this.surname + " " + ssn;
    }
    public String getName() {
        return name;
    }
    public String getSsn() {
        return ssn;
    }
    public String getSurname() {
        return surname;
    }

}
