package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import interfaces.ICampStaffService;
import interfaces.ICampValidationService;
import models.Camp;
import models.Staff;
import models.Student;
import stores.DataStore;
import utility.InputSelectionUtility;

/**
 * The {@link CampStaffService} class implements the {@link ICampStaffService} interface and
 * provides methods for managing staff-related camp services. These services aid in the overall 
 * staffs' desired operations. Services include creating/deleting camps and toggling visibility.
 */
public class CampStaffService implements ICampStaffService { 

	/**
	 * The {@code campValidationService} field represents an instance of
	 * {@link ICampValidationService} used for validating camp-related operations.
	 * It is initialized with an instance of {@link CampValidationService}.
	 * It is stored as a private variable to prevent direct usage of validation services
	 * outside the scope of a service.
	 */
	private final static ICampValidationService campValidationService = new CampValidationService();

	/**
     * Constructs an instance of the {@link CampStaffService} class.
     */
	public CampStaffService(){
	}

	// ---------- interface Service method implementation ---------- //
	
	/**
     * Retrieves a list of all camps.
     *
     * @return an {@link List} of {@link Camp} objects representing all camps
     */
	@Override
	public ArrayList<Camp> getAllCamps() {
    	Map<String, Camp> campsData = DataStore.getCampData();
    	
    	ArrayList<Camp> allCamps = campsData.values().stream()
                .sorted(Comparator.comparing(c -> c.getCampInformation().getCampName()))
                .collect(Collectors.toCollection(ArrayList::new));

    	return allCamps;
	}
	
	/**
     * Retrieves a list of camps created by the staff with the specified ID.
     *
     * @param staff the {@link Staff} object specified to obtain from.
     * @return an {@link List} of {@link Camp} objects representing the created camps
     */
	@Override
	public ArrayList<Camp> getStaffCreatedCamps(Staff staff) {
		Map<String, Camp> campsData = DataStore.getCampData();

		ArrayList<Camp> staffCreatedCamps = campsData.values().stream()
                .filter(camp -> camp.getCampInformation().getCampStaffInCharge().equals(staff.getName()))
                .sorted(Comparator.comparing(c -> c.getCampInformation().getCampName()))
                .collect(Collectors.toCollection(ArrayList::new));

		return staffCreatedCamps;
	}

	/**
     * Retrieves a list of students attending a specific camp as an Attendee .
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing attendees
     */
	@Override
	public ArrayList<Student> getCampAttendeeList(Camp camp) {
		Map<String, List<String>> campToRegisteredStudentData = DataStore.getCampToRegisteredStudentData();
		Map<String, Student> allStudentsData = DataStore.getStudentData();
		String campName = camp.getCampInformation().getCampName();

		// Retrieves the list of registered student names for the specified camp. If the camp is not found in the map, it defaults to an empty list.
		List<String> registeredStudents = campToRegisteredStudentData.getOrDefault(campName, Collections.emptyList());
		Map<String, Student> allStudentsDataNames = convertMapKey(allStudentsData);
        
		// Returns the list of students attending the specified camp.
		ArrayList<Student> campAttendeeList = registeredStudents.stream()
				.map(allStudentsDataNames::get)
				.filter(Objects::nonNull) // Filter out null students if any
				.collect(Collectors.toCollection(ArrayList::new));

		return campAttendeeList;
	}

	/**
     * Retrieves a list of students attending a specific camp as a Camp Committee.
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing CampCommittee
     */
	@Override
	public ArrayList<Student> getCampCommitteeList(Camp camp){
		Map<String, List<String>> campToRegisteredCampCommitteeData = DataStore.getCampToRegisteredCampCommitteeData();
		Map<String, Student> allStudentsData = DataStore.getStudentData();
		String campName = camp.getCampInformation().getCampName();

		//retrieves the list of registered CampCommittee names for the specified camp. If the camp is not found in the map, it defaults to an empty list.
		List<String> registeredCampCommittee = campToRegisteredCampCommitteeData.getOrDefault(campName, Collections.emptyList());
		Map<String, Student> allStudentsDataNames = convertMapKey(allStudentsData);

		//returns the list of CampCommittee attending the specified camp.
		ArrayList<Student> campCommitteeList = registeredCampCommittee.stream()
				.map(allStudentsDataNames::get)
				.filter(Objects::nonNull) // Filter out null students if any
				.collect(Collectors.toCollection(ArrayList::new));

		return campCommitteeList;
	}

	/**
     * Toggles the visibility of the specified camp to "on" or "off".
     *
     * @param camp the {@link Camp} object to update
	 * @return true if the toggle is successful, false otherwise
     */
	@Override
	public boolean toggleCampVisibility(Camp camp){
		if (camp != null) {
			boolean on = InputSelectionUtility.getSelectedBoolean(camp);	
			if(on) {
				// Turn on visibility
				camp.setVisibility(true);
				System.out.println("Camp visibility set to On.");
				return true ;
			}else if(!on && !campValidationService.isCampRegistered(camp)) {
				// Turn off visibility if No students are registered 
				camp.setVisibility(false);
				System.out.println("Camp visibility set to Off.");
				return true ;
			}else {
				System.out.println("Operation Unavailable.");
				return false ;
			}
		}else {
			// Handle when camp is null
			System.out.println("Invalid Camp.");
			return false;
		} 
	}

	/**
     * Creates new camps and adds them to the data store.
     *
     * @param camps a list of {@link Camp} objects to create
     * @return true if the creation is successful, false otherwise
     */
	@Override
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
     * Deletes the specified camp from the data store.
     *
     * @param camp the {@link Camp} object to delete
     * @return true if the deletion is successful, false otherwise
     */
	@Override
    public boolean deleteCamp(Camp camp){
		Map<String, Camp> campData = DataStore.getCampData();
		if (camp != null) {
			String campName = camp.getCampInformation().getCampName();
			if(campValidationService.isCampRegistered(camp)){
				System.out.println("Students have registered for this Camp!");
				return false;
			}else{
				campData.remove(campName);
				DataStore.setCampData(campData);
				return true;
			}
		} else {
			return false;
		}
	}

	// ---------- Helper Method ---------- //

	/**
     * Converts map keys from userID to studentName.
     *
     * @param inputMap the input {@link Map} with user IDs as keys
     * @return a new {@link Map} with student names as keys
     */
    public Map<String, Student> convertMapKey(Map<String, Student> inputMap) {
        Map<String, Student> convertedMap = new HashMap<>();

        // Populate the converted map using student names as keys
        inputMap.forEach((key, student) -> {
            // Assuming User is the superclass of Student and it has a 'name' attribute
            String studentName = student.getName();
            convertedMap.put(studentName, student);
        });

        return convertedMap;
    }
}

	