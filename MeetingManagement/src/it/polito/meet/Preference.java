package it.polito.meet;


public class Preference {
    String email;
    String name;
    String surname;
 
    public Preference(String email, String name, String surname) {
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

}
