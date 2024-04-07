package it.polito.library;

public class Rental {
	
	final private String bookId;
	final private String readerId;
	final private String startingDate;
	private String endingDate;
	
	public Rental(String bookId, String readerId, String date) {
		this.bookId = bookId;
		this.readerId = readerId;
		this.startingDate = date;
		endingDate = null;
	}

	public String getBookId() {
		return bookId;
	}

	public String getReaderId() {
		return readerId;
	}

	public String getStartingDate() {
		return startingDate;
	}

	public String getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}



}
