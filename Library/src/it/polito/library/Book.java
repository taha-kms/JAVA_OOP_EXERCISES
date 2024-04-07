package it.polito.library;

public class Book {
	
	final private String title;
	private String id; 
	private boolean isAvailable;

	
	public Book(String title) {
		this.title = title;
		isAvailable = true;
	}

	public String getTitle() {
		return title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

}
