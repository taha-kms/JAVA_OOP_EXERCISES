package it.polito.library;

public class Reader {
	
	final private String name;
	final private String surname;
	final private String id;
	
	public Reader(String name, String surname, String id) {
		this.name = name;
		this.surname = surname;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return name + " " + surname;
	}

}
