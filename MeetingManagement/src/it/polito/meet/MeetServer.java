package it.polito.meet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MeetServer {

	private TreeSet<String> categorylist = new TreeSet<>();
	private TreeMap<String, Meeting> meetingsmap = new TreeMap<>();
	//private	Map<String, List<String>> preferences;

	/**
	 * adds a set of meeting categories to the list of categories
	 * The method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param categories the meeting categories
	 */

	public void addCategories(String... categories) {
		for (String category : categories) {
			categorylist.add(category);
		}

	}

	/**
	 * retrieves the list of available categories
	 * 
	 * @return list of categories
	 */
	public Collection<String> getCategories() {
		List<String> sortedKeys = new ArrayList<>(categorylist);
		Collections.sort(sortedKeys);
		return sortedKeys;

	}

	/**
	 * adds a new meeting with a given category
	 * 
	 * @param title    title of meeting
	 * @param topic    subject of meeting
	 * @param category category of the meeting
	 * @return a unique id of the meeting
	 * @throws MeetException in case of non-existing category
	 */
	public String addMeeting(String title, String topic, String category) throws MeetException {

		if (meetingsmap.containsKey(title))
			throw new MeetException("Duplicate product name");
		if (!categorylist.contains(category))
			throw new MeetException("It Doesn't exist this category");

		Meeting meeting = new Meeting(title, topic, category);
		String key = String.valueOf(meetingsmap.size() + 1);
		meetingsmap.put(key, meeting);

		return key;
	}

	/**
	 * retrieves the list of meetings with the given category
	 * 
	 * @param category required category
	 * @return list of meeting ids
	 */
	public Collection<String> getMeetings(String category) {
		Collection<String> meetings = new ArrayList<>();

		meetings = meetingsmap.entrySet().stream()
				.filter(entry -> entry.getValue().category.equals(category))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());

		return meetings;
	}

	/**
	 * retrieves the title of the meeting with the given id
	 * 
	 * @param meetingId id of the meeting
	 * @return the title
	 */
	public String getMeetingTitle(String meetingId) {
		return meetingsmap.get(meetingId).title;
	}

	/**
	 * retrieves the topic of the meeting with the given id
	 * 
	 * @param meetingId id of the meeting
	 * @return the topic of the meeting
	 */
	public String getMeetingTopic(String meetingId) {
		return meetingsmap.get(meetingId).topic;
	}

	// R2

	/**
	 * Add a new option slot for a meeting as a date and a start and end time.
	 * The slot must not overlap with an existing slot for the same meeting.
	 * 
	 * @param meetingId id of the meeting
	 * @param date      date of slot
	 * @param start     start time
	 * @param end       end time
	 * @return the length in hours of the slot
	 * @throws MeetException in case of slot overlap or wrong meeting id
	 */
	public double addOption(String meetingId, String date, String start, String end) throws MeetException {
		if (!meetingsmap.containsKey(meetingId))
			throw new MeetException("Wrong ID");
		double durationHours = meetingsmap.get(meetingId).addOption(date, start, end);
		if (durationHours == -1)
			throw new MeetException("Slot overlap");
		return durationHours;
	}

	/**
	 * retrieves the slots available for a given meeting.
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-15:30".
	 * 
	 * @param meetingId id of the meeting
	 * @return a map date -> list of slots
	 */
	public Map<String, List<String>> showSlots(String meetingId) {
		return meetingsmap.get(meetingId).showSlots();
	}

	/**
	 * Declare a meeting open for collecting preferences for the slots.
	 * 
	 * @param meetingId is of the meeting
	 */
	public void openPoll(String meetingId) {

		meetingsmap.get(meetingId).setpollOpen();

	}

	/**
	 * Records a preference of a user for a specific slot/option of a meeting.
	 * Preferences can be recorded only for meeting for which poll has been opened.
	 * 
	 * @param email     email of participant
	 * @param name      name of the participant
	 * @param surname   surname of the participant
	 * @param meetingId id of the meeting
	 * @param date      date of the selected slot
	 * @param slot      time range of the slot
	 * @return the number of preferences for the slot
	 * @throws MeetException in case of invalid id or slot
	 */
	public int selectPreference(String email, String name, String surname, String meetingId, String date, String slot)
			throws MeetException {

		if (!meetingsmap.containsKey(meetingId))
			throw new MeetException("Wrong ID");
		else if (!meetingsmap.get(meetingId).getpollstatus())
			throw new MeetException("Poll not open");
		else if (!meetingsmap.get(meetingId).slots.containsKey(date)) 
			throw new MeetException("Poll not open");

		else {

			List<Slot> meetingSlots = meetingsmap.get(meetingId).slots.get(date);
			Slot selectedSlot = meetingSlots.stream()
													  .filter(s -> (s.getStartTime() + '-' + s.getEndTime()).equals(slot))
													  .findFirst()
													  .orElseThrow(() -> new MeetException("Invalid slot"));

			
			selectedSlot.addPreference(email, name, surname);
			return selectedSlot.getPreferenceCount();
		}
	}

	/**
	 * retrieves the list of the preferences expressed for a meeting.
	 * Preferences are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=EMAIL", including date, time interval, and email
	 * separated
	 * respectively by "T" and "="
	 * 
	 * @param meetingId meeting id
	 * @return list of preferences for the meeting
	 */
	public Collection<String> listPreferences(String meetingId) {
		List<String> meetingPreferencs = new ArrayList<>();

		for (Map.Entry<String, List<Slot>> dateEntry : meetingsmap.get(meetingId).slots.entrySet()) {
			for (Slot slot : dateEntry.getValue()) {
				for(Preference preference : slot.getPreferences()) {
					meetingPreferencs.add(slot.getDate() + "T" + slot.getStartTime() + "-" + slot.getEndTime() + "=" + preference.getEmail());
				}
			}
		}
		return meetingPreferencs;
	}

	/**
	 * close the poll associated to a meeting and returns
	 * the most preferred options, i.e. those that have receive the highest number
	 * of preferences.
	 * The options are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=##", including date, time interval, and preference
	 * count separated
	 * respectively by "T" and "="
	 * 
	 * @param meetingId id of the meeting
	 */
	public Collection<String> closePoll(String meetingId) {
			meetingsmap.get(meetingId).setPollClose();

			Collection<String> selectedSlots = meetingsmap.get(meetingId).getPreferedSlots();

			

			return selectedSlots;
	}

	/**
	 * returns the preference count for each slot of a meeting
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots with preferences described as strings with the format
	 * "hh:mm-hh:mm=###", including the time interval and the number of preferences
	 * e.g. "14:00-15:30=2".
	 * 
	 * @param meetingId the id of the meeting
	 * @return the map data -> slot preferences
	 */
	public Map<String, List<String>> meetingPreferences(String meetingId) {
		// Initialize a map to store preferences for each slot
		Map<String, List<String>> preferences = new HashMap<>();
		
		// Retrieve the meeting slots associated with the given meetingId
		// Assuming 'meetingsmap' is a map that contains meetings mapped to their IDs
		// and each meeting has slots
		meetingsmap.get(meetingId).slots.entrySet().forEach(s -> {
			// For each slot, extract preferences
			List<String> slotPreferences = s.getValue().stream()
				// Filter out preferences that are not null
				.filter(p -> p.getPreferences() != null)
				// Map each preference to a string representation
				.map(p -> p.getStartTime() + "-" + p.getEndTime() + "=" + p.getPreferenceCount())
				// Collect the string representations into a list
				.collect(Collectors.toList());
			
			// Put the list of preferences for this slot into the preferences map
			preferences.put(s.getKey(), slotPreferences);
		});
		
		// Return the map containing meeting preferences
		return preferences;
	}
	/**
	 * computes the number preferences collected for each meeting
	 * The result is a map that associates to each meeting id the relative count of
	 * preferences expressed
	 * 
	 * @return the map id : preferences -> count
	 */
	public Map<String, Integer> preferenceCount() {

		Map<String, Integer> preferences = new HashMap<>();

		for (Map.Entry<String, Meeting> meeting : meetingsmap.entrySet()) {

			preferences.putIfAbsent(meeting.getKey(), 0);
			for(Map.Entry<String, List<Slot>> dateEntry : meeting.getValue().slots.entrySet()) {

				int sum = 0;
	
				for (Slot slot : dateEntry.getValue()) {
					sum = sum + slot.getPreferenceCount();
				}
			
				preferences.put(meeting.getKey(), sum);
			}
	
		
		}

	System.out.println(meetingsmap);

	return preferences;
	}
}
