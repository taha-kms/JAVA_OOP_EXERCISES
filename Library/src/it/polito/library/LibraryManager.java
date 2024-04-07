package it.polito.library;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class LibraryManager {
	    
    // R1: Readers and Books 
	
	private Map<String, Book> books = new HashMap<>();
	Map<String, Reader> readers = new HashMap<>();
	List<Rental> rentals = new ArrayList<>();
    
    /**
	 * adds a book to the library archive
	 * The method can be invoked multiple times.
	 * If a book with the same title is already present,
	 * it increases the number of copies available for the book
	 * 
	 * @param title the title of the added book
	 * @return the ID of the book added 
	 */
    public String addBook(String title) {
    	
    	Book book = new Book(title);
    	int id = books.size() +  1000;
    	book.setId(String.valueOf(id));
    	books.put(String.valueOf(id), book);
        return id + "";
    }
    
    /**
	 * Returns the book titles available in the library
	 * sorted alphabetically, each one linked to the
	 * number of copies available for that title.
	 * 
	 * @return a map of the titles liked to the number of available copies
	 */
    public SortedMap<String, Integer> getTitles() {    	
    	
    	Map<String, Long> m = books.values().stream().collect(Collectors.groupingBy(Book :: getTitle, Collectors.counting()));
    	SortedMap<String, Integer> list = new TreeMap<>();
    	for(String b : m.keySet()) {
    		int num = m.get(b).intValue();
    		list.put(b, num);
    	}
    	return list;
  
    }
    
    /**
	 * Returns the books available in the library
	 * 
	 * @return a set of the titles liked to the number of available copies
	 */
    public Set<String> getBooks() {
    	return books.values().stream().map(b -> b.getId()).collect(Collectors.toSet());
    }
    
    /**
	 * Adds a new reader
	 * 
	 * @param name first name of the reader
	 * @param surname last name of the reader
	 */
    public void addReader(String name, String surname) {
    	
    	int id = readers.size() + 1000;
    	Reader reader = new Reader(name, surname, id + "");
    	readers.put(String.valueOf(id), reader);
    	
    }
    
    
    /**
	 * Returns the reader name associated to a unique reader ID
	 * 
	 * @param readerID the unique reader ID
	 * @return the reader name
	 * @throws LibException if the readerID is not present in the archive
	 */
    public String getReaderName(String readerID) throws LibException {
    	
    	if(!readers.containsKey(readerID))
    		throw new LibException();
        return readers.get(readerID).toString();
    }    
    
    
    // R2: Rentals Management
    
    
    /**
	 * Retrieves the bookID of a copy of a book if available
	 * 
	 * @param bookTitle the title of the book
	 * @return the unique book ID of a copy of the book or the message "Not available"
	 * @throws LibException  an exception if the book is not present in the archive
	 */
    public String getAvailableBook(String bookTitle) throws LibException {
    	
    	if(books.values().stream().noneMatch(b -> b.getTitle().equals(bookTitle)))
    		throw new LibException();
    	
    	return books.values().stream().filter(b -> b.getTitle().equals(bookTitle) && b.isAvailable())
    		.map(b -> b.getId()).sorted(Comparator.naturalOrder()).findFirst().orElse("Not available");
 
    }   

    /**
	 * Starts a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param startingDate the starting date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is already renting a book, or if the book copy is already rented
	 */
	public void startRental(String bookID, String readerID, String startingDate) throws LibException {
		
		if(!books.containsKey(bookID) || !readers.containsKey(readerID) || !books.get(bookID).isAvailable() || 
				rentals.stream().anyMatch(r -> r.getReaderId().equals(readerID) && r.getEndingDate() == null))
			throw new LibException();
		Rental rental = new Rental(bookID, readerID, startingDate);
		books.get(bookID).setAvailable(false);
		rentals.add(rental);
		
    }
    
	/**
	 * Ends a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param endingDate the ending date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is not renting a book, or if the book copy is not rented
	 */
    public void endRental(String bookID, String readerID, String endingDate) throws LibException {
    	
    	if(!books.containsKey(bookID) || !readers.containsKey(readerID))
			throw new LibException();
    	if(books.get(bookID).isAvailable())
    		throw new LibException();
    	
    	rentals.stream().filter(r -> r.getReaderId().equals(readerID) && r.getEndingDate() == null)
    		.findFirst().orElse(null).setEndingDate(endingDate);;
    	//rentals.get(readerID).setEndingDate(endingDate);
    	books.get(bookID).setAvailable(true);
    }
    
    
   /**
	* Retrieves the list of readers that rented a specific book.
	* It takes a unique book ID as input, and returns the readers' reader IDs and the starting and ending dates of each rental
	* 
	* @param bookID the unique book ID of the book copy
	* @return the map linking reader IDs with rentals starting and ending dates
	* @throws LibException  an exception if the book copy or the reader are not present in the archive,
	* if the reader is not renting a book, or if the book copy is not rented
	*/
    public SortedMap<String, String> getRentals(String bookID) throws LibException {
    	
    	if(!books.containsKey(bookID))
			throw new LibException();
    	if(rentals.stream().noneMatch(r -> r.getBookId().equals(bookID)))
    		throw new LibException();
    	
    	List<Rental> rents = rentals.stream().filter(r -> r.getBookId().equals(bookID)).collect(Collectors.toList());
    	SortedMap<String, String> output = new TreeMap<>();
    	for(Rental r : rents)
    		output.put(r.getReaderId(), r.getStartingDate() + " " + (r.getEndingDate() == null ? "ONGOING" : r.getEndingDate()));
    	
        return output;
    }
    
    
    // R3: Book Donations
    
    /**
	* Collects books donated to the library.
	* 
	* @param donatedTitles It takes in input book titles in the format "First title,Second title"
	*/
    public void receiveDonation(String donatedTitles) {
    	for(String t : donatedTitles.split(",")) {
    		Book b = new Book(t);
    		b.setId((books.keySet().size() + 1000) + "");
    		books.put((books.keySet().size() + 1000) + "", b);
    	}
    }
    
    // R4: Archive Management

    /**
	* Retrieves all the active rentals.
	* 
	* @return the map linking reader IDs with their active rentals

	*/
    public Map<String, String> getOngoingRentals() {
    	
    	List<Rental> rents = rentals.stream().filter(r -> r.getEndingDate()==null)
    		.collect(Collectors.toList());
    	Map<String, String> output = new HashMap<>();
    	for(Rental r : rents)
    		output.put(r.getReaderId(), r.getBookId());
    		
        return output;
    }
    
    /**
	* Removes from the archives all book copies, independently of the title, that were never rented.
	* 
	*/
    public void removeBooks() {
    	List<String> b = books.keySet().stream().collect(Collectors.toList());
    	for(String id : b) {
    		if(rentals.stream().noneMatch(r -> r.getBookId().equals(id))) {
    			books.remove(id);
    		}
    	}
    }
    	
    // R5: Stats
    
    /**
	* Finds the reader with the highest number of rentals
	* and returns their unique ID.
	* 
	* @return the uniqueID of the reader with the highest number of rentals
	*/
    public String findBookWorm() {
    	
    	return rentals.stream().max(Comparator
    		.comparing( r -> rentals.stream().filter(a -> a.getReaderId().equals(r.getReaderId())).count()))
    		.map(r -> r.getReaderId()).orElse("");
    }
    
    /**
	* Returns the total number of rentals by title. 
	* 
	* @return the map linking a title with the number of rentals
	*/
    public Map<String, Integer> rentalCounts() {
    	return books.values().stream().collect(Collectors.groupingBy(Book :: getTitle, 
    			Collectors.summingInt(b -> (int) rentals.stream().filter(r -> r.getBookId().equals(b.getId())).count())));
    }

}
