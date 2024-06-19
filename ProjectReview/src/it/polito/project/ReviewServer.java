package it.polito.project;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public class ReviewServer {

	/**
	 * adds a set of student groups to the list of groups
	 * The method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param groups the project groups
	 */
	public void addGroups(String... groups) {

	}

	/**
	 * retrieves the list of available groups
	 * 
	 * @return list of groups
	 */
	public Collection<String> getGroups() {
		return null;
	}
	
	
	/**
	 * adds a new review with a given group
	 * 
	 * @param title		title of review
	 * @param topic	subject of review
	 * @param group  group of the review
	 * @return a unique id of the review
	 * @throws ReviewException in case of non-existing group
	 */
	public String addReview(String title, String topic, String group) throws ReviewException {
		return null;
	}

	/**
	 * retrieves the list of reviews with the given group
	 * 
	 * @param group 	required group
	 * @return list of review ids
	 */
	public Collection<String> getReviews(String group) {
		return null;
	}

	/**
	 * retrieves the title of the review with the given id
	 * 
	 * @param reviewId  id of the review 
	 * @return the title
	 */
	public String getReviewTitle(String reviewId) {
		return null;
	}

	/**
	 * retrieves the topic of the review with the given id
	 * 
	 * @param reviewId  id of the review 
	 * @return the topic of the review
	 */
	public String getReviewTopic(String reviewId) {
		return null;
	}

	// R2
		
	/**
	 * Add a new option slot for a review as a date and a start and end time.
	 * The slot must not overlap with an existing slot for the same review.
	 * 
	 * @param reviewId	id of the review
	 * @param date		date of slot
	 * @param start		start time
	 * @param end		end time
	 * @return the length in hours of the slot
	 * @throws ReviewException in case of slot overlap or wrong review id
	 */
	public double addOption(String reviewId, String date, String start, String end) throws ReviewException {
		return -1.0;
	}

	/**
	 * retrieves the time slots available for a given review.
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-15:30".
	 * 
	 * @param reviewId		id of the review
	 * @return a map date -> list of slots
	 */
	public Map<String, List<String>> showSlots(String reviewId) {
		return null;
	}

	/**
	 * Declare a review open for collecting preferences for the slots.
	 * 
	 * @param reviewId	is of the review
	 */
	public void openPoll(String reviewId) {
		return;
	}


	/**
	 * Records a preference of a student for a specific slot/option of a review.
	 * Preferences can be recorded only for review for which poll has been opened.
	 * 
	 * @param email		email of participant
	 * @param name		name of the participant
	 * @param surname	surname of the participant
	 * @param reviewId	id of the review
	 * @param date		date of the selected slot
	 * @param slot		time range of the slot
	 * @return the number of preferences for the slot
	 * @throws ReviewException	in case of invalid id or slot
	 */
	public int selectPreference(String email, String name, String surname, String reviewId, String date, String slot) throws ReviewException {
		return -1;
	}

	/**
	 * retrieves the list of the preferences expressed for a review.
	 * Preferences are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=EMAIL", including date, time interval, and email separated
	 * respectively by "T" and "="
	 * 
	 * @param reviewId review id
	 * @return list of preferences for the review
	 */
	public Collection<String> listPreferences(String reviewId) {
		return null;
	}

	/**
	 * close the poll associated to a review and returns
	 * the most preferred options, i.e. those that have receive the highest number of preferences.
	 * The options are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=##", including date, time interval, and preference count separated
	 * respectively by "T" and "="
	 * 
	 * @param reviewId	id of the review
	 */
	public Collection<String> closePoll(String reviewId) {
		return null;
	}

	
	/**
	 * returns the preference count for each slot of a review
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots with preferences described as strings with the format 
	 * "hh:mm-hh:mm=###", including the time interval and the number of preferences 
	 * e.g. "14:00-15:30=2".
	 * 
	 * All possible dates are reported and for each date only 
	 * the slots with at least one preference are listed.
	 * 
* @param reviewId	the id of the review
	 * @return the map data -> slot preferences
	 */
	public Map<String, List<String>> reviewPreferences(String reviewId) {
		return null;
	}


	/**
	 * computes the number preferences collected for each review
	 * The result is a map that associates to each review id the relative count of preferences expressed
	 * 
	 * @return the map id : preferences -> count
	 */
	public Map<String, Integer> preferenceCount() {
		return null;
	}
}
