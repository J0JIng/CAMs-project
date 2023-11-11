package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import interfaces.ICampStaffService;

import enums.UserRole;
import enums.FacultyGroups;
import models.Camp;
import models.CampInformation;
import models.Staff;
import models.Student;
import stores.AuthStore;
import stores.DataStore;
import views.StaffView;

import utility.InputSelectionUtility;

public class CampStaffService implements ICampStaffService {
	Scanner scanner = new Scanner(System.in);
	StaffView view = new StaffView();

	// ---------- Helper Function ---------- //

	/**
     * Toggles the visibility of the specified camp to "on" or "off".
     *
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
	

	// ---------- interface method implementation ---------- //

	/**
     * Retrieves a list of all camps.
     *
     * @return an {@link List} of {@link Camp} objects representing all camps
     */
	@Override
	public List<Camp> getAllCamps() {
    	Map<String, Camp> campsData = DataStore.getCampData();
    	List<Camp> allCamps = new ArrayList<>(campsData.values());
    	return allCamps;
	}
	
	/**
     * Retrieves a list of camps created by the staff with the specified ID.
     *
     * @param staffID the ID of the {@link Staff}
     * @return an {@link List} of {@link Camp} objects representing the created camps
     */
	@Override
	public List<Camp> getStaffCreatedCamps(Staff staff) {
		Map<String, Camp> campsData = DataStore.getCampData();

		List<Camp> staffCreatedCamps = campsData.values().stream()
				.filter(camp -> camp.getCampInformation().getCampStaffInCharge().equals(staff.getName()))
				.collect(Collectors.toList());

		return staffCreatedCamps;
	}

	/**
     * Retrieves a list of students attending a specific camp as an Attendee .
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing attendees
     */
	@Override
	public List<Student> getCampAttendeeList(Camp camp) {
		Map<String, List<String>> campToRegisteredStudentData = DataStore.getCampToRegisteredStudentData();
		Map<String, Student> allStudentsData = DataStore.getStudentData();
		String campName = camp.getCampInformation().getCampName();

		//retrieves the list of registered student names for the specified camp. If the camp is not found in the map, it defaults to an empty list.
		List<String> registeredStudents = campToRegisteredStudentData.getOrDefault(campName, Collections.emptyList());

		//returns the list of students attending the specified camp.
		List<Student> campAttendeeList = registeredStudents.stream()
				.map(allStudentsData::get)
				.filter(Objects::nonNull) // Filter out null students if any
				.collect(Collectors.toList());

		return campAttendeeList;
	}

	/**
     * Retrieves a list of students attending a specific camp as a Camp Committee.
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing CampCommittee
     */
	@Override
	public List<Student> getCampCommitteeList(Camp camp){
		Map<String, List<String>> campToRegisteredCampCommitteeData = DataStore.getCampToRegisteredCampCommitteeData();
		Map<String, Student> allStudentsData = DataStore.getStudentData();
		String campName = camp.getCampInformation().getCampName();

		//retrieves the list of registered CampCommittee names for the specified camp. If the camp is not found in the map, it defaults to an empty list.
		List<String> registeredCampCommittee = campToRegisteredCampCommitteeData.getOrDefault(campName, Collections.emptyList());

		//returns the list of CampCommittee attending the specified camp.
		List<Student> campCommitteeList = registeredCampCommittee.stream()
				.map(allStudentsData::get)
				.filter(Objects::nonNull) // Filter out null students if any
				.collect(Collectors.toList());

		return campCommitteeList;
	}

	// ---------- Service method implementation ---------- //

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
		view.viewCamps(staffCreatedCamps);
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);
		if (camp != null) {
			view.editCampView();
			boolean success = InputSelectionUtility.updateCampInput(camp,staff,dateFormat);
			return success;
		} else {
			return false;
		}
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
		view.viewCamps(staffCreatedCamps);
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);
		if (camp != null) {
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
		} else {
			return false;
		}
	}
    
    public void viewAllCamps() {
    	while (true) {
    		view.viewCamps(getAllCamps());
    		Camp c = InputSelectionUtility.campSelector(getAllCamps());
    		if (c != null) {
	    		view.viewCampInformation(c);
	    		scanner.nextLine();
    		} else {
    			return;
    		}
    	}
    }
}

	