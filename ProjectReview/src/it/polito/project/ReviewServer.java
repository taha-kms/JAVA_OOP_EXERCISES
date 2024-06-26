package it.polito.project;

import java.util.*;
import java.util.stream.Collectors;


public class ReviewServer {

	private Set<String> groupsSet;
	private Map<String, Review> reviewMap;
	private Integer nextReviewId;

	public ReviewServer(){
		this.nextReviewId = 100;
		this.groupsSet = new TreeSet<>();
		this.reviewMap = new HashMap<>();
	}

	/**
	 * adds a set of student groups to the list of groups
	 * The method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param groups the project groups
	 */
	public void addGroups(String... groups) {
		for (String group : groups) this.groupsSet.add(group);
	}

	/**
	 * retrieves the list of available groups
	 * 
	 * @return list of groups
	 */
	public Collection<String> getGroups() {
		return this.groupsSet;
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
		if(!this.groupsSet.contains(group)) throw new ReviewException();

		String newReviewId = "R-" + Integer.toString(this.nextReviewId);

		this.reviewMap.put(newReviewId, new Review(newReviewId, title, topic, group));
		this.nextReviewId++;
		return newReviewId;
	}

	/**
	 * retrieves the list of reviews with the given group
	 * 
	 * @param group 	required group
	 * @return list of review ids
	 */
	public Collection<String> getReviews(String group) {
		return this.reviewMap.values().stream()
									  .filter(r -> r.getGroup().equals(group))
									  .map(Review::getId)
									  .collect(Collectors.toList());
	}

	/**
	 * retrieves the title of the review with the given id
	 * 
	 * @param reviewId  id of the review 
	 * @return the title
	 */
	public String getReviewTitle(String reviewId) {
		return this.reviewMap.get(reviewId).getTitle();
	}

	/**
	 * retrieves the topic of the review with the given id
	 * 
	 * @param reviewId  id of the review 
	 * @return the topic of the review
	 */
	public String getReviewTopic(String reviewId) {
		return this.reviewMap.get(reviewId).getTopic();
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
		if(!this.reviewMap.containsKey(reviewId)) throw new ReviewException();
		return this.reviewMap.get(reviewId).addSlot(date, start, end);
		
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

		Map<String, List<String>> result = new HashMap<>();
		Map<String, List<Slot>> slotsMap = this.reviewMap.get(reviewId).getSlotMap();

		for (Map.Entry<String, List<Slot>> options : slotsMap.entrySet()) {
			String date = options.getKey();
			List<String> timeRanges = options.getValue().stream().map(Slot::toString).collect(Collectors.toList());
			result.put(date, timeRanges);
		}

		return result;
	}

	/**
	 * Declare a review open for collecting preferences for the slots.
	 * 
	 * @param reviewId	is of the review
	 */
	public void openPoll(String reviewId) {
		this.reviewMap.get(reviewId).setPoolOpen();
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
		if (!this.reviewMap.containsKey(reviewId)) throw new ReviewException("Invalid review ID");
		
		if (!this.reviewMap.get(reviewId).isPoolOpen()) {
			throw new ReviewException("Poll is not open");
		}

		Slot reviewSlot = this.reviewMap.get(reviewId).getSlot(date, slot);
		if (reviewSlot == null)	throw new ReviewException("Invalid slot");
		
		return reviewSlot.addPreference(email, name, surname);
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

		List<String> result = new ArrayList<>();
	
		for(Map.Entry<String, List<Slot>> entry : this.reviewMap.get(reviewId).getSlotMap().entrySet()) {

			String date = entry.getKey();
			for (Slot slot : entry.getValue()) {

				String timeRanges = slot.toString();
				for (Preference preference : slot.getPreferenceList()) {
					
					String email = preference.getEmail();
					result.add(date+ "T" + timeRanges + "=" + email);
				}
			}
			
		}
		return result;
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

        Review review = this.reviewMap.get(reviewId);
        review.setPoolClose();

        Map<String, Integer> slotPreferences = new HashMap<>();


		for (Map.Entry<String, List<Slot>> entry : review.getSlotMap().entrySet()) {
            String date = entry.getKey();
            for (Slot slot : entry.getValue()) {
                String slotKey = date + "T" + slot.toString();
                slotPreferences.put(slotKey, slot.getPreferenceSize());
            }
        }

        int maxPreferences = slotPreferences.values().stream().max(Integer::compare).orElse(0);

        List<String> mostPreferredSlots = slotPreferences.entrySet().stream()
            .filter(entry -> entry.getValue() == maxPreferences)
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.toList());

        return mostPreferredSlots;
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
	public Map<String, List<String>> reviewPreferences(String reviewId) throws ReviewException {
		if (!this.reviewMap.containsKey(reviewId)) {
			throw new ReviewException("Invalid review ID");
		}

		Map<String, List<String>> result = new HashMap<>();
		for (Map.Entry<String, List<Slot>> entry : this.reviewMap.get(reviewId).getSlotMap().entrySet()) {
			String date = entry.getKey();
			List<String> slotPreferences = entry.getValue().stream()
					.filter(slot -> slot.getPreferenceSize() > 0)
					.map(slot -> slot.toString() + "=" + slot.getPreferenceSize())
					.collect(Collectors.toList());
			
			if (!slotPreferences.isEmpty()) {
				result.put(date, slotPreferences);
			}
		}

		return result;
	}

	/**
	 * Computes the number of preferences collected for each review.
	 * The result is a map that associates to each review ID the relative count of preferences expressed.
	 * 
	 * @return the map id : preferences -> count
	 */
	public Map<String, Integer> preferenceCount() {
		Map<String, Integer> result = new HashMap<>();

		for (Review review : this.reviewMap.values()) {
			int totalPreferences = review.getSlotMap().values().stream()
				.flatMap(List::stream)
				.mapToInt(Slot::getPreferenceSize)
				.sum();
			result.put(review.getId(), totalPreferences);
		}

		return result;
	}
}
