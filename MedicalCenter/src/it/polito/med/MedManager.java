package it.polito.med;

import java.util.*;
import java.util.stream.Collectors;

public class MedManager {

    private Integer nextAppointmentId;
    private Map<String, Patient> patientMap;
    private Map<String, Appointment> appointmentMap;
    private Map<String, List<Doctor>> specialityMap;
    private Map<String, Doctor> doctorsMap;
    
    
    public MedManager(){
        this.nextAppointmentId = 1000;
        this.appointmentMap = new HashMap<>();
        this.specialityMap = new HashMap<>();
        this.doctorsMap = new HashMap<>();
        this.patientMap = new HashMap<>();
    }
	/**
	 * add a set of medical specialities to the list of specialities
	 * offered by the med centre.
	 * Method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param specialities the specialities
	 */
	public void addSpecialities(String... specialities) {
        for (String string : specialities) {
            this.specialityMap.putIfAbsent(string, new ArrayList<>());
        }
	}

	/**
	 * retrieves the list of specialities offered in the med centre
	 * 
	 * @return list of specialities
	 */
	public Collection<String> getSpecialities() {
		return this.specialityMap.keySet();
	}
	
	
	/**
	 * adds a new doctor with the list of their specialities
	 * 
	 * @param id		unique id of doctor
	 * @param name		name of doctor
	 * @param surname	surname of doctor
	 * @param speciality speciality of the doctor
	 * @throws MedException in case of duplicate id or non-existing speciality
	 */
	public void addDoctor(String id, String name, String surname, String speciality) throws MedException {
        if(!this.specialityMap.containsKey(speciality) || this.doctorsMap.containsKey(id)) throw new MedException("addDoctor error");

        Doctor newDoctor = new Doctor(id, name, surname, speciality);
        
        this.doctorsMap.put(id, newDoctor);
        this.specialityMap.get(speciality).add(newDoctor);

	}

	/**
	 * retrieves the list of doctors with the given speciality
	 * 
	 * @param speciality required speciality
	 * @return the list of doctor ids
	 */
	public Collection<String> getSpecialists(String speciality) {
		return this.doctorsMap.values().stream().filter(d -> d.getSpeciality().equals(speciality)).map(Doctor::getId).collect(Collectors.toList());
	}

	/**
	 * retrieves the name of the doctor with the given code
	 * 
	 * @param code code id of the doctor 
	 * @return the name
	 */
	public String getDocName(String code) {
		return this.doctorsMap.get(code).getName();
	}

	/**
	 * retrieves the surname of the doctor with the given code
	 * 
	 * @param code code id of the doctor 
	 * @return the surname
	 */
	public String getDocSurname(String code) {
		return this.doctorsMap.get(code).getSurname();
	}

	/**
	 * Define a schedule for a doctor on a given day.
	 * Slots are created between start and end hours with a 
	 * duration expressed in minutes.
	 * 
	 * @param code	doctor id code
	 * @param date	date of schedule
	 * @param start	start time
	 * @param end	end time
	 * @param duration duration in minutes
	 * @return the number of slots defined
	 */
	public int addDailySchedule(String code, String date, String start, String end, int duration) {

        this.doctorsMap.get(code).getSchedules().put(date, new Schedule(date, start, end, duration));

        
		return this.doctorsMap.get(code).getSlotNumber(date);
	}

	/**
	 * retrieves the available slots available on a given date for a speciality.
	 * The returned map contains an entry for each doctor that has slots scheduled on the date.
	 * The map contains a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-14:30" describes a slot starting at 14:00 and lasting 30 minutes.
	 * 
	 * @param date			date to look for
	 * @param speciality	required speciality
	 * @return a map doc-id -> list of slots in the schedule
	 */
	public Map<String, List<String>> findSlots(String date, String speciality) {
        Map<String, List<String>> result = new HashMap<>();

        for(Doctor doctor: this.specialityMap.get(speciality)){
            List<String> slots = doctor.getSchedules().get(date).getSlots();
            String doctorId = doctor.getId();

            result.put(doctorId, slots);
        }
		return result;
	}

	/**
	 * Define an appointment for a patient in an existing slot of a doctor's schedule
	 * 
	 * @param ssn		ssn of the patient
	 * @param name		name of the patient
	 * @param surname	surname of the patient
	 * @param code		code id of the doctor
	 * @param date		date of the appointment
	 * @param slot		slot to be booked
	 * @return a unique id for the appointment
	 * @throws MedException	in case of invalid code, date or slot
	 */
	public String setAppointment(String ssn, String name, String surname, String code, String date, String givenSlot) throws MedException {
        if(
            !this.doctorsMap.containsKey(code) ||
            !this.doctorsMap.get(code).getSchedules().containsKey(date) ||
            !this.doctorsMap.get(code).getSchedules().get(date).getSlots().contains(givenSlot)
        ){
            throw new MedException("setAppointment ERROR");
        } 

        this.patientMap.put(ssn, new Patient(ssn, name, surname));

        String appointmentId = "APP" + this.nextAppointmentId;
        Patient patient = this.patientMap.get(ssn);
        Doctor doctor = this.doctorsMap.get(code);
        Slot slot = this.doctorsMap.get(code).getSchedules().get(date).getSlot(givenSlot);

        this.appointmentMap.put(appointmentId, new Appointment(patient, doctor, slot));
        this.nextAppointmentId++;

		return appointmentId;
	}

	/**
	 * retrieves the doctor for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return doctor code id
	 */
	public String getAppointmentDoctor(String idAppointment) {
		return this.appointmentMap.get(idAppointment).getDoctor().getId();
	}

	/**
	 * retrieves the patient for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return doctor patient ssn
	 */
	public String getAppointmentPatient(String idAppointment) {
		return this.appointmentMap.get(idAppointment).getPatient().getSsn();
	}

	/**
	 * retrieves the time for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return time of appointment
	 */
	public String getAppointmentTime(String idAppointment) {
		return this.appointmentMap.get(idAppointment).getSlot().getStart();
	}

	/**
	 * retrieves the date for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return date
	 */
	public String getAppointmentDate(String idAppointment) {
		return this.appointmentMap.get(idAppointment).getDate();
	}

	/**
	 * retrieves the list of a doctor appointments for a given day.
	 * Appointments are reported as string with the format
	 * "hh:mm=SSN"
	 * 
	 * @param code doctor id
	 * @param date date required
	 * @return list of appointments
	 */
	public Collection<String> listAppointments(String code, String date) {
		return this.appointmentMap.values().stream()
                                           .filter(a -> a.getDate().equals(date))
                                           .filter(a -> a.getDoctor().getId().equals(code))
                                           .map(Appointment::toString)
                                           .collect(Collectors.toList());
	}

	/**
	 * Define the current date for the medical centre
	 * The date will be used to accept patients arriving at the centre.
	 * 
	 * @param date	current date
	 * @return the number of total appointments for the day
	 */
	public int setCurrentDate(String date) {
		return -1;
	}

	/**
	 * mark the patient as accepted by the med centre reception
	 * 
	 * @param ssn SSN of the patient
	 */
	public void accept(String ssn) {

	}

	/**
	 * returns the next appointment of a patient that has been accepted.
	 * Returns the id of the earliest appointment whose patient has been
	 * accepted and the appointment not completed yet.
	 * Returns null if no such appointment is available.
	 * 
	 * @param code	code id of the doctor
	 * @return appointment id
	 */
	public String nextAppointment(String code) {
		return null;
	}

	/**
	 * mark an appointment as complete.
	 * The appointment must be with the doctor with the given code
	 * the patient must have been accepted
	 * 
	 * @param code		doctor code id
	 * @param appId		appointment id
	 * @throws MedException in case code or appointment code not valid,
	 * 						or appointment with another doctor
	 * 						or patient not accepted
	 * 						or appointment not for the current day
	 */
	public void completeAppointment(String code, String appId)  throws MedException {

	}

	/**
	 * computes the show rate for the appointments of a doctor on a given date.
	 * The rate is the ratio of accepted patients over the number of appointments
	 *  
	 * @param code		doctor id
	 * @param date		reference date
	 * @return	no show rate
	 */
	public double showRate(String code, String date) {
		return -1.0;
	}

	/**
	 * computes the schedule completeness for all doctors of the med centre.
	 * The completeness for a doctor is the ratio of the number of appointments
	 * over the number of slots in the schedule.
	 * The result is a map that associates to each doctor id the relative completeness
	 * 
	 * @return the map id : completeness
	 */
	public Map<String, Double> scheduleCompleteness() {
		return null;
	}


	
}
