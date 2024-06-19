package it.polito.project;

public class Preference {
    private String email;
    private String name;
    private String surname;
    
    Preference(String email, String name, String surname){

        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
