package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import enums.MessageStatus;
import interfaces.ICampStaffService;

import enums.UserRole;
import enums.FacultyGroups;
import models.Camp;
import models.Staff;
import models.Student;
import stores.AuthStore;
import stores.DataStore;
import views.StaffView;
import services.*;
import models.*;

import utility.InputSelectionUtility;

public class CampStaffService implements ICampStaffService {
	private static final int MAX_COMMITTEE_SLOTS = 10; 
	Scanner scanner = new Scanner(System.in);
	StaffView view = new StaffView();

	// ---------- Helper Function ---------- //

	/**
     * Toggles the visibility of the specified camp to "on" or "off".
     *
     * @param camp the {@link Camp} object to update
     */
	public void toggleCampVisibility(Camp camp){
		if (camp != null) {
			boolean on = InputSelectionUtility.toggleSelector(camp);	
			if(on) {
				camp.setVisibility(true);
			}else if(!on && !campIsRegistered(camp)) {
				camp.setVisibility(false);
			}
		} else {
			return;
		}
	}

	/**
     * Checks if a camp is registered by students or camp committees.
     *
     * @param camp the {@link Camp} object to check
     * @return true if the camp is registered, false otherwise
     */
=======
     * @param camp      the {@link Camp} object to update
     * @param visibility the new visibility status ("on" or "off")
     * @return true if the camp visibility was updated successfully, false otherwise
     */
	public void toggleCampVisibility(Staff staff){
		List<Camp> staffCreatedCamps = getStaffCreatedCamps(staff);
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);
		boolean on = InputSelectionUtility.toggleSelector(staffCreatedCamps);	
		if(on) {
			camp.setVisibility(true);
		}else if(!on && !campIsRegistered(camp)) {
			camp.setVisibility(false);
		}
	}

>>>>>>> hq
	public boolean campIsRegistered(Camp camp) {
		Map<String, List<String>> registeredStudents = DataStore.getCampToRegisteredStudentData();
		Map<String, List<String>> registeredCampCommittees = DataStore.getCampToRegisteredCampCommitteeData();
	
		String campName = camp.getCampInformation().getCampName();
		
		// Check if the camp has registered students
		List<String> registeredStudentList = registeredStudents.get(campName);
		boolean hasRegisteredStudents = registeredStudentList != null && !registeredStudentList.isEmpty();
	
		// Check if the camp has registered camp committees
		List<String> registeredCampCommitteeList = registeredCampCommittees.get(campName);
		boolean hasRegisteredCampCommittees = registeredCampCommitteeList != null && !registeredCampCommitteeList.isEmpty();
	
		// Return true if the camp has both registered students and camp committees
		return hasRegisteredStudents && hasRegisteredCampCommittees;
	}
	

<<<<<<< HEAD
=======
	// ---------- interface method implementation ---------- //

>>>>>>> hq
	/**
     * Retrieves a list of all camps.
     *
     * @return an {@link List} of {@link Camp} objects representing all camps
     */
	@Override
<<<<<<< HEAD
	public ArrayList<Camp> getAllCamps() {
    	Map<String, Camp> campsData = DataStore.getCampData();
    	ArrayList<Camp> allCamps = new ArrayList<>(campsData.values());
=======
	public List<Camp> getAllCamps() {
    	Map<String, Camp> campsData = DataStore.getCampData();
    	List<Camp> allCamps = new ArrayList<>(campsData.values());
>>>>>>> hq
    	return allCamps;
	}
	
	/**
     * Retrieves a list of camps created by the staff with the specified ID.
     *
     * @param staffID the ID of the {@link Staff}
     * @return an {@link List} of {@link Camp} objects representing the created camps
     */
	@Override
<<<<<<< HEAD
	public ArrayList<Camp> getStaffCreatedCamps(Staff staff) {
		Map<String, Camp> campsData = DataStore.getCampData();

		ArrayList<Camp> staffCreatedCamps = campsData.values().stream()
				.filter(camp -> camp.getCampInformation().getCampStaffInCharge().equals(staff.getName()))
				.collect(Collectors.toCollection(ArrayList::new));
=======
	public List<Camp> getStaffCreatedCamps(Staff staff) {
		Map<String, Camp> campsData = DataStore.getCampData();

		List<Camp> staffCreatedCamps = campsData.values().stream()
				.filter(camp -> camp.getCampInformation().getCampStaffInCharge().equals(staff.getName()))
				.collect(Collectors.toList());
>>>>>>> hq

		return staffCreatedCamps;
	}

	/**
     * Retrieves a list of students attending a specific camp as an Attendee .
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing attendees
     */
	@Override
<<<<<<< HEAD
	public ArrayList<Student> getCampAttendeeList(Camp camp) {
=======
	public List<Student> getCampAttendeeList(Camp camp) {
>>>>>>> hq
		Map<String, List<String>> campToRegisteredStudentData = DataStore.getCampToRegisteredStudentData();
		Map<String, Student> allStudentsData = DataStore.getStudentData();
		String campName = camp.getCampInformation().getCampName();

<<<<<<< HEAD
		// Retrieves the list of registered student names for the specified camp. If the camp is not found in the map, it defaults to an empty list.
		List<String> registeredStudents = campToRegisteredStudentData.getOrDefault(campName, Collections.emptyList());

		// Returns the list of students attending the specified camp.
		ArrayList<Student> campAttendeeList = registeredStudents.stream()
				.map(allStudentsData::get)
				.filter(Objects::nonNull) // Filter out null students if any
				.collect(Collectors.toCollection(ArrayList::new));
=======
		//retrieves the list of registered student names for the specified camp. If the camp is not found in the map, it defaults to an empty list.
		List<String> registeredStudents = campToRegisteredStudentData.getOrDefault(campName, Collections.emptyList());

		//returns the list of students attending the specified camp.
		List<Student> campAttendeeList = registeredStudents.stream()
				.map(allStudentsData::get)
				.filter(Objects::nonNull) // Filter out null students if any
				.collect(Collectors.toList());
>>>>>>> hq

		return campAttendeeList;
	}

	/**
     * Retrieves a list of students attending a specific camp as a Camp Committee.
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing CampCommittee
     */
	@Override
<<<<<<< HEAD
	public ArrayList<Student> getCampCommitteeList(Camp camp){
=======
	public List<Student> getCampCommitteeList(Camp camp){
>>>>>>> hq
		Map<String, List<String>> campToRegisteredCampCommitteeData = DataStore.getCampToRegisteredCampCommitteeData();
		Map<String, Student> allStudentsData = DataStore.getStudentData();
		String campName = camp.getCampInformation().getCampName();

		//retrieves the list of registered CampCommittee names for the specified camp. If the camp is not found in the map, it defaults to an empty list.
		List<String> registeredCampCommittee = campToRegisteredCampCommitteeData.getOrDefault(campName, Collections.emptyList());

		//returns the list of CampCommittee attending the specified camp.
<<<<<<< HEAD
		ArrayList<Student> campCommitteeList = registeredCampCommittee.stream()
				.map(allStudentsData::get)
				.filter(Objects::nonNull) // Filter out null students if any
				.collect(Collectors.toCollection(ArrayList::new));
=======
		List<Student> campCommitteeList = registeredCampCommittee.stream()
				.map(allStudentsData::get)
				.filter(Objects::nonNull) // Filter out null students if any
				.collect(Collectors.toList());
>>>>>>> hq

		return campCommitteeList;
	}

	// ---------- Service method implementation ---------- //

<<<<<<< HEAD

	/**
     * Creates new camps and adds them to the data store.
     *
     * @param camps the list of {@link Camp} objects to create
     * @return true if camps are created successfully, false otherwise
     */
    public boolean createCamp(ArrayList<Camp> camps) {
        Map<String, Camp> campData = DataStore.getCampData();
		Map<String, Camp> newCampData = new HashMap<String, Camp>();

		for (Camp camp : camps) {
			newCampData.put(camp.getCampInformation().getCampName(), camp);
		}

		campData.putAll(newCampData);
		DataStore.setCampData(campData);
        return true;
    }

	/**
     * Checks if a camp name is unique among existing camps.
     *
     * @param existingCamps the list of existing {@link Camp} objects
     * @param newCampName   the new camp name to check
     * @return true if the camp name is unique, false otherwise
     */
	public boolean isValidName(List<Camp> existingCamps, String newCampName) {
		for (Camp camp : existingCamps) {
			if (camp.getCampInformation().getCampName().equalsIgnoreCase(newCampName)) {
				return false;
			}
		}
		return true;
	}

	/**
     * Updates the name of a camp if the new name is unique.
     *
     * @param camp        the {@link Camp} object to update
     * @param newCampName the new camp name
     * @return true if the camp name is updated successfully, false otherwise
     * @throws IllegalArgumentException if the new camp name is not unique
     */
	public boolean updateCampName(Camp camp, String newCampName) {
		ArrayList<Camp> existingCamps = getAllCamps();
		boolean isUniqueName = isValidName(existingCamps,newCampName);
		if(isUniqueName){
			camp.getCampInformation().setCampName(newCampName);
		}else{
			throw new IllegalArgumentException("Camp with the same name already exists. Please choose a different name.");
		}
		return isUniqueName;
	}

	/**
	 * Updates the start date of a camp if the new date is valid.
	 *
	 * @param camp           the {@link Camp} object to update
	 * @param newStartDate   the new start date
	 * @throws IllegalArgumentException if the new start date is invalid
	 */
	public void updateCampStartDate(Camp camp, Date newStartDate) throws IllegalArgumentException {
		if (newStartDate.after(camp.getCampInformation().getCampEndDate())) {
			throw new IllegalArgumentException("Invalid start date. The new start date must be before the current end date.");
		} else if (newStartDate.before(camp.getCampInformation().getCampRegistrationClosingDate())) {
			throw new IllegalArgumentException("Invalid start date. The new start date must be after the registration end date.");
		} else {
			camp.getCampInformation().setCampStartDate(newStartDate);
		}
	}

	/**
	 * Updates the end date of a camp if the new date is valid.
	 *
	 * @param camp         the {@link Camp} object to update
	 * @param newEndDate   the new end date
	 * @throws IllegalArgumentException if the new end date is invalid
	 */
	public void updateCampEndDate(Camp camp, Date newEndDate) throws IllegalArgumentException {
		if (newEndDate.before(camp.getCampInformation().getCampStartDate())) {
			throw new IllegalArgumentException("Invalid End date. The new End date must be after the current start date.");
		} else if (newEndDate.before(camp.getCampInformation().getCampRegistrationClosingDate())) {
			throw new IllegalArgumentException("Invalid End date. The new End date must be after the registration end date.");
		} else {
			camp.getCampInformation().setCampEndDate(newEndDate);
		}
	}	

	/**
	 * Updates the registration closing date of a camp if the new date is valid.
	 *
	 * @param camp                    the {@link Camp} object to update
	 * @param newRegistrationClosingDate the new registration closing date
	 * @throws IllegalArgumentException if the new registration closing date is invalid
	 */
	public void updateRegistrationEndDate(Camp camp, Date newRegistrationClosingDate) throws IllegalArgumentException {
		if (newRegistrationClosingDate.after(camp.getCampInformation().getCampStartDate())) {
			throw new IllegalArgumentException("Invalid registration closing date. The new date must be before the current start date.");
		} else if (newRegistrationClosingDate.after(camp.getCampInformation().getCampEndDate())) {
			throw new IllegalArgumentException("Invalid registration closing date. The new date must be before the current end date.");
		} else {
			camp.getCampInformation().setCampRegistrationClosingDate(newRegistrationClosingDate);
		}
	}	

	/**
	 * Updates the location of a camp.
	 *
	 * @param camp       the {@link Camp} object to update
	 * @param newLocation the new location
	 * @throws IllegalArgumentException if the new location is invalid
	 */
	public void updateCampLocation(Camp camp, String newLocation) throws IllegalArgumentException {
		// Add validation logic to check if newLocation is valid
		if (newLocation == null || newLocation.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid location. Location cannot be empty or null.");
		}
	
		// Update the camp location
		camp.getCampInformation().setCampLocation(newLocation);
	}

	/**
	 * Updates the total slots of a camp if the new total slots are valid.
	 *
	 * @param camp              the {@link Camp} object to update
	 * @param newCampTotalSlots the new total slots
	 * @throws IllegalArgumentException if the new total slots are invalid
	 */
	public void updateCampTotalSlot(Camp camp, int newCampTotalSlots) throws IllegalArgumentException {
		if (newCampTotalSlots < camp.getCampInformation().getCampCommitteeSlots()) {
			throw new IllegalArgumentException("Total slots must be greater than or equal to camp committee slots.");
		}
		camp.getCampInformation().setCampTotalSlots(newCampTotalSlots);
	}
	
	/**
	 * Updates the committee slots of a camp if the new committee slots are valid.
	 *
	 * @param camp             the {@link Camp} object to update
	 * @param newCommitteeSlots the new committee slots
	 * @throws IllegalArgumentException if the new committee slots are invalid
	 */
	public void updateCampCommitteeSlots(Camp camp, int newCommitteeSlots) throws IllegalArgumentException {
		if (newCommitteeSlots > camp.getCampInformation().getCampTotalSlots()) {
			throw new IllegalArgumentException("Committee slots must be less than or equal to total slots.");
		}else if(newCommitteeSlots > MAX_COMMITTEE_SLOTS){
			newCommitteeSlots = MAX_COMMITTEE_SLOTS;
			camp.getCampInformation().setCampCommitteeSlots(newCommitteeSlots);
			throw new IllegalArgumentException("Committee slots must be less than allowed maximum camp committe slots. Max default of 10 set.");
		}
		camp.getCampInformation().setCampCommitteeSlots(newCommitteeSlots);
	}
	
	
	/**
	 * Updates the description of a camp.
	 *
	 * @param camp             the {@link Camp} object to update
	 * @param newDescription   the new description
	 * @throws IllegalArgumentException if the new description is invalid
	 */
	public void updateCampDescription(Camp camp, String newDescription) throws IllegalArgumentException {
		// Add validation logic to check if newLocation is valid
		if (newDescription == null || newDescription.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid Description. Description cannot be empty or null.");
		}
	
		// Update the camp location
		camp.getCampInformation().setCampDescription(newDescription);
	}

	/**
	 * Updates the faculty group of a camp if the new faculty group is valid.
	 *
	 * @param camp        the {@link Camp} object to update
	 * @param userInput   the new faculty group input
	 * @throws IllegalArgumentException if the new faculty group is invalid
	 */
	public void updateCampFacultyGroup(Camp camp, String userInput) throws IllegalArgumentException {
		// Add validation logic to check if userInput is valid
		if (userInput == null || userInput.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid Input. Input cannot be empty or null.");
		}
	
		try {
			// Validate userInput against valid FacultyGroups
			FacultyGroups newFacultyGroup = FacultyGroups.valueOf(userInput.toUpperCase());
			// Update the camp faculty group
			camp.getCampInformation().setFacultyGroup(newFacultyGroup);
		} catch (IllegalArgumentException e) {
			// Throw a specific exception indicating an invalid faculty group
			throw new IllegalArgumentException("Invalid faculty group. Please enter a valid faculty group.", e);
		}
	}

	/**
     * Deletes a camp if it is not registered by students or camp committees.
     *
     * @param camp the {@link Camp} object to delete
     * @return true if the camp is deleted successfully, false otherwise
     */
    public boolean deleteCamp(Camp camp){
		Map<String, Camp> campData = DataStore.getCampData();
		if (camp != null) {
			String campName = camp.getCampInformation().getCampName();
			if(campIsRegistered(camp)){
				System.out.println("Students have registered for this Camp!");
				return false;
			}else{
				campData.remove(campName);
				return true;
			}
		} else {
			return false;
		}
=======
	/**
     * Creates new camps from the provided list of camps.
     *
     * @param staffID the ID of the {@link Staff} creating the camp
     * @return true if the camps were created successfully, false otherwise
     */
    public boolean createCamp(Staff staff) {
        Map<String, Camp> campData = DataStore.getCampData();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Creating a new camp...");
        Camp newCamp = InputSelectionUtility.createCampInput(getAllCamps(),staff,dateFormat);
        campData.put(newCamp.getCampInformation().getCampName().toUpperCase(), newCamp);
        System.out.println("Created " + newCamp.getCampInformation().getCampName());
        return true;
    }
	
	/**
     * Updates the details of the specified camp if the staff ID matches the
     * camp's creator.
	 * 
     * @param staffID the ID of the {@link Staff} making the update
     * @return true if the camp details were updated successfully, false otherwise
     */
    public boolean updateCampDetails(Staff staff){
		System.out.println("Updating camp Details...");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		List<Camp> staffCreatedCamps = getStaffCreatedCamps(staff);
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);
		boolean success = InputSelectionUtility.updateCampInput(camp,staff,dateFormat);
		return success;
	}
	
	/**
     * Deletes the specified camp if the staff ID matches the camp's creator.
     *
     * @param staffID the ID of the {@link Staff} making the delete request
     * @return true if the camp was deleted successfully, false otherwise
     */
    public boolean deleteCamp(Staff staff){
		Map<String, Camp> campData = DataStore.getCampData();
		List<Camp> staffCreatedCamps = getStaffCreatedCamps(staff);
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);
		String campName = camp.getCampInformation().getCampName();
		
		if(campIsRegistered(camp)){
			System.out.println("Error deleting " + campName);
			System.out.println("Students have registered for the camp!");
			return false;
		}else{
			System.out.println("Deleted " + campName);
			campData.remove(campName);
			return true;
		}
	}

	private Map<Integer, Enquiry> getAllEnquiriesForCamp(Camp camp) {
		Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
		String campName = camp.getCampInformation().getCampName();

		// Returns a Map<Integer, Enquiry> with Enquiries having the specified Camp name.
		Map<Integer, Enquiry> enquiriesForCampMap = enquiryData.values().stream()
				.filter(enquiry -> campName.equals(enquiry.getCampName()))
				.collect(Collectors.toMap(Enquiry::getEnquiryID, enquiry -> enquiry));

		return enquiriesForCampMap;
	}

	private Map<Integer, Suggestion> getAllSuggestionsForCamp(Camp camp) {
		Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData();
		String campName = camp.getCampInformation().getCampName();

		// Returns a Map<Integer, Enquiry> with Enquiries having the specified Camp name.
		Map<Integer, Suggestion> suggestionsForCampMap = suggestionData.values().stream()
				.filter(suggestion -> campName.equals(suggestion.getCampName()))
				.collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));

		return suggestionsForCampMap;
	}

	private void displayEnquiries(Map<Integer, Enquiry> enquiries) {
		if (enquiries.isEmpty()) {
			System.out.println("No enquiries to display.");
			return;
		}
		for (Enquiry enquiry : enquiries.values()) {
			System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
			System.out.println("Camp Name: " + enquiry.getCampName());
			System.out.println("Message: " + enquiry.getEnquiryMessage());
			System.out.println("Status: " + enquiry.getEnquiryStatus());
			System.out.println("Response: " + enquiry.getEnquiryResponse());
			System.out.println("Responder ID: " + enquiry.getResponderID());
			System.out.println("------");
		}
	}

	public void viewEnquiriesForCamp(Staff staff) {
		EnquiryStudentService enquiryService = new EnquiryStudentService();

		// Get list of Staff created camps
		List<Camp> staffCreatedCamps = getStaffCreatedCamps(staff);
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);
		Map<Integer, Enquiry> campEnquiries = getAllEnquiriesForCamp(camp);
		displayEnquiries(campEnquiries);
	}

	public boolean respondToEnquiry(Staff staff) {
		EnquiryStudentService enquiryService = new EnquiryStudentService();

		// Get list of Staff created camps
		List<Camp> staffCreatedCamps = getStaffCreatedCamps(staff);
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);

		// Get enquiries for the selected camp
		Map<Integer, Enquiry> campEnquiries = getAllEnquiriesForCamp(camp);
		// Check if there are draft enquiries to edit
		if (campEnquiries.isEmpty()) {
			System.out.println("You have no enquiries to reply to.");
			return false;
		}

		// Get User input
		Enquiry selectedEnquiry = InputSelectionUtility.enquirySelector(campEnquiries);
		String response = InputSelectionUtility.getStringInput("Enter response: ");

		// Respond using EnquiryStudentService
		return enquiryService.respondToEnquiry(selectedEnquiry.getEnquiryID(), staff.getStaffID(), MessageStatus.ACCEPTED, response);
	}

	public boolean respondToSuggestion(Staff staff) {
		SuggestionStudentService suggestionService = new SuggestionStudentService();

		// Get list of Staff created camps
		List<Camp> staffCreatedCamps = getStaffCreatedCamps(staff);
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);

		// Get enquiries for the selected camp
		Map<Integer, Suggestion> campSuggestions = getAllSuggestionsForCamp(camp);
		// Check if there are draft suggestions to respond
		if (campSuggestions.isEmpty()) {
			System.out.println("You have no suggestions to review.");
			return false;
		}

		// Get User input
		Suggestion selectedSuggestion = InputSelectionUtility.suggestionSelector(campSuggestions);
		int reviewOption = InputSelectionUtility.getIntInput("Do you want to accept this suggestion? (1: Yes, 2: No): ");
		boolean suggestionStatus = (reviewOption == 1);
		Map<String, Student> students = DataStore.getStudentData();
		Student sender = students.get(selectedSuggestion.getSenderID());
		if (suggestionStatus){sender.incrementStudentPoints();}

		// Respond using EnquiryStudentService
		return suggestionService.reviewSuggestion(selectedSuggestion.getSuggestionID(), suggestionStatus);
>>>>>>> hq
	}
}

	