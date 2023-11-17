package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import enums.FacultyGroups;

import interfaces.ICampStudentService;

import models.Camp;
import models.Student;

import stores.AuthStore;
import stores.DataStore;

import views.StudentView;

/**
 * The {@code CampStudentService} class implements the {@link ICampStudentService} interface
 * and provides services related to camp registration and management.
 */
public class CampStudentService implements ICampStudentService {
	StudentView view = new StudentView();
	Scanner scanner = new Scanner(System.in);
	/**
     * Constructs an instance of the {@code CampStudentService} class.
     */
	public CampStudentService(){
	}

	// ---------- Helper Function ---------- //

	/**
     * Checks for date clashes with other registered camps.
     *
     * @param student The {@link student} for whom to check date clashes.
     * @param newCamp The new {@link camp} for which to check date clashes.
     * @return {@code true} if there is a date clash, {@code false} otherwise.
     */
	public boolean hasDateClash(Student student, Camp newCamp) {
		List<Camp> registeredCamps = getRegisteredCamps();
		return registeredCamps.stream().anyMatch(camp -> dateClashExists(camp, newCamp));
	}

	/**
     * Checks if there's a date clash between two camps.
     *
     * @param camp1 The first {@link camp} .
     * @param camp2 The second {@link camp} .
     * @return {@code true} if there is a date clash, {@code false} otherwise.
     */
	public boolean dateClashExists(Camp camp1, Camp camp2) {
    	Date camp1StartDate = camp1.getCampInformation().getCampStartDate();
    	Date camp1EndDate = camp1.getCampInformation().getCampEndDate();
    	Date camp2StartDate = camp2.getCampInformation().getCampStartDate();
    	Date camp2EndDate = camp2.getCampInformation().getCampEndDate();

    	// Check if camp1 ends before camp2 starts or camp1 starts after camp2 ends
    	return camp1StartDate.before(camp2EndDate) && camp1EndDate.after(camp2StartDate);
	}

	/**
	 * Checks if the {@link camp} is over based on the current date and the {@link camp}'s registration closing date.
	 *
	 * @param currentDate The current date.
	 * @param camp The {@link camp}  to check.
	 * @return {@code true} if the {@link camp} is over, {@code false} otherwise.
	 */
	public boolean isCampOver(Date currentDate, Camp camp) {
		return currentDate.after(camp.getCampInformation().getCampRegistrationClosingDate());
	}
	

	/**
	 * Checks if the {@link camp} is full by comparing the number of registered students with the maximum allowed slots.
	 *
	 * @param camp The {@link camp} to check.
	 * @return {@code true} if the {@link camp} is full, {@code false} otherwise.
	 */
	public boolean isCampFull(Camp camp) {
		Map<String, List<String>> registeredStudentData = DataStore.getCampToRegisteredStudentData();
		String campName = camp.getCampInformation().getCampName();
		int maxCampSlots = camp.getCampInformation().getCampTotalSlots();

		return registeredStudentData.containsKey(campName) && registeredStudentData.get(campName).size() >= maxCampSlots;
	}

	/**
	 * Checks if the camp committee slots are full by comparing the number of registered committee members
	 * with the maximum allowed committee slots.
	 *
	 * @param camp The {@link camp} to check.
	 * @return {@code true} if the camp committee slots are full, {@code false} otherwise.
	 */
	public boolean isCampCommitteeFull(Camp camp) {
		Map<String, List<String>> registeredCampCommitteeData = DataStore.getCampToRegisteredCampCommitteeData();
		String campName = camp.getCampInformation().getCampName();
		int maxCampCommitteeSlots = camp.getCampInformation().getCampCommitteeSlots();

		return registeredCampCommitteeData.containsKey(campName) && registeredCampCommitteeData.get(campName).size() >= maxCampCommitteeSlots;
	}
	

	/**
	 * Checks if the {@link student} is the camp committee member for the specified camp.
	 *
	 * @param student The {@link student} to check.
	 * @param camp The {@link camp} to check.
	 * @return {@code true} if the {@link student} is a camp committee member for the specified {@link camp}, {@code false} otherwise.
	 */
	public boolean isUserCampCommitteeForCamp(Student student, Camp camp) {
		Map<String, String> campCommitteeData = DataStore.getCampCommitteeToCampRegisteredData();
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();

		return campCommitteeData.containsKey(studentName) && campCommitteeData.get(studentName).equals(campName);
	}


	/**
	 * Checks if the {@link student} is a camp committee member for any {@link camp}.
	 *
	 * @param student The {@link student} to check.
	 * @return {@code true} if the {@link student} is a camp committee member, {@code false} otherwise.
	 */
	public boolean isUserCampCommittee(Student student) {
		Map<String, String> campCommitteeData = DataStore.getCampCommitteeToCampRegisteredData();
		String studentName = student.getName();
	
		return campCommitteeData.containsKey(studentName);
	}
	
	/**
	 * Checks if the {@link Student} has withdrawn from the specified {@link Camp}.
	 *
	 * @param student The {@link Student} to check.
	 * @param camp The {@link Camp} to check.
	 * @return {@code true} if the {@link Student} has withdrawn from the specified {@link Camp}, {@code false} otherwise.
	 */
	public boolean isUserWithdrawnFromCamp(Student student, Camp camp) {
		Map<String, List<String>> withdrawnCampData = DataStore.getStudentToCampsWithdrawnData();
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();

		return withdrawnCampData.containsKey(studentName) && withdrawnCampData.get(studentName).contains(campName);
	}
	
	/**
	 * Checks if the {@link student} is registered with the specified {@link camp}.
	 *
	 * @param student The {@link student} to check.
	 * @param camp The {@link camp} to check.
	 * @return {@code true} if the {@link student} is registered with the specified {@link camp}, {@code false} otherwise.
 	*/
	public boolean isUserRegisteredWithCamp(Student student, Camp camp) {
		Map<String, List<String>> registeredCampData = DataStore.getStudentsToCampsRegisteredData();
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();

		return registeredCampData.containsKey(studentName) && registeredCampData.get(studentName).contains(campName);
	}

	// ---------- interface method implementation ---------- //
	
	/**
     * Retrieves a list of available camps for the currently logged-in student.
     *
     * @return List of available camps.
     */
	@Override
	public List<Camp> getAvailableCampsToRegister(){
		Student student = (Student) AuthStore.getCurrentUser();
		Map<String, Camp> campsData = DataStore.getCampData();
		//Date currentDate = new Date();

		List<Camp> availableCamps = campsData.values().stream()
		.filter(camp -> (camp.getCampInformation().getFacultyGroup() == student.getFaculty()
					  ||camp.getCampInformation().getFacultyGroup() == FacultyGroups.ALL)
					  &&camp.getVisibility() == true
					  &&!isCampFull(camp))
		.collect(Collectors.toCollection(ArrayList::new));

		return availableCamps;		
	}
	
	/**
     * Retrieves a list of available camps for the currently logged-in student.
     *
     * @return List of all regardless of registration requirements camps.
     */
	public List<Camp> getAllCamps(){
		Student student = (Student) AuthStore.getCurrentUser();
		Map<String, Camp> campsData = DataStore.getCampData();
		//Date currentDate = new Date();

		List<Camp> availableCamps = campsData.values().stream()
		.filter(camp -> (camp.getCampInformation().getFacultyGroup() == student.getFaculty()
					  ||camp.getCampInformation().getFacultyGroup() == FacultyGroups.ALL)
					  &&camp.getVisibility() == true)
		.collect(Collectors.toCollection(ArrayList::new));

		return availableCamps;		
	}

	 /**
     * Retrieves a list of camps from which the student has withdrawn.
     *
     * @return List of withdrawn camps.
     */
	@Override
	public List<Camp> getWithdrawnCamps() {
		Student student = (Student) AuthStore.getCurrentUser();
		Map<String, List<String>> studentToCampsWithdrawnData = DataStore.getStudentToCampsWithdrawnData();
	
		List<Camp> withdrawnCamps = studentToCampsWithdrawnData.entrySet().stream()
				.filter(entry -> entry.getValue().contains(student.getName()))
				.map(entry -> DataStore.getCampData().get(entry.getKey()))
				.collect(Collectors.toList());
	
		return withdrawnCamps;
	}
	
	/**
     * Retrieves a list of camps for which the student is registered.
     *
     * @return List of registered camps.
     */
	@Override
	public List<Camp> getRegisteredCamps() {
		Student student = (Student) AuthStore.getCurrentUser();
		Map<String, List<String>> registeredCampsData = DataStore.getStudentsToCampsRegisteredData();
	    
	    List<Camp> registeredCamps = registeredCampsData.entrySet().stream()
	    	    .filter(entry -> entry.getKey().equals(student.getName()))
	    	    .flatMap(entry -> entry.getValue().stream())
	    	    .map(campName -> DataStore.getCampData().get(campName))
	    	    .collect(Collectors.toList());
	
		return registeredCamps;
	}

	public Camp getCampCommitteeCamp(Student student) {
		Map<String, String> registeredCamp = DataStore.getCampCommitteeToCampRegisteredData();
		String campID = registeredCamp.get(student.getStudentID());
		Map<String, Camp> campData = DataStore.getCampData();
	
		if (campID != null && campData.containsKey(campID)) {
			return campData.get(campID);
		} else {
			return null; 
		}
	}

	// ---------- Service method implementation ---------- //

	/**
     * Registers the student for a camp based on user input and various checks.
     *
     * @return {@code true} if registration is successful, {@code false} otherwise.
     */
	public boolean registerCamp(Student student,Camp camp) {
		
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();
		Map<String, List<String>> registeredCampsData = DataStore.getStudentsToCampsRegisteredData();
		Map<String, List<String>> registeredStudentData = DataStore.getCampToRegisteredStudentData();

		// Add the camp name to the list of registered camps for the current user
		if (!registeredCampsData.containsKey(studentName)) {
			registeredCampsData.put(studentName, new ArrayList<String>());
		}
		registeredCampsData.get(studentName).add(campName);

		// Add the student to the list of registered student for the chosen camp
		if(!registeredStudentData.containsKey(campName)){
			registeredStudentData.put(campName, new ArrayList<String>());
		}
		registeredStudentData.get(campName).add(studentName);
				
		// Save into DataStore
		DataStore.setStudentsToCampsRegisteredData(registeredCampsData);
		DataStore.setCampToRegisteredStudentData(registeredStudentData);
		System.out.println("Registered to chosen camp successfully!");

		return true;
	}

	/**
     * Handles the withdrawal from a camp for both regular attendees and camp committee members.
     *
     * @return {@code true} if withdrawal is successful, {@code false} otherwise.
     */
	public boolean withdrawCamp(Student student,Camp camp) {
		
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();
		Map<String, List<String>> withdrawnCampsData = DataStore.getStudentToCampsWithdrawnData();
		Map<String, List<String>> registeredCampsData = DataStore.getStudentsToCampsRegisteredData();
		Map<String, List<String>> registeredStudentData = DataStore.getCampToRegisteredStudentData();

		// remove the camp name from the list of registered camps for the current user
		registeredCampsData.get(studentName).remove(campName);

		// remove the student from the list of registered student for the chosen camp
		registeredStudentData.get(campName).remove(studentName);

		// add the camp to the list of withdrawn camps for the current user
		if (!withdrawnCampsData.containsKey(studentName)) {
			withdrawnCampsData.put(studentName, new ArrayList<String>());
		}
		withdrawnCampsData.get(studentName).add(campName);

		// Save into DataStore
		DataStore.setStudentsToCampsRegisteredData(registeredCampsData);
		DataStore.setCampToRegisteredStudentData(registeredStudentData);
		DataStore.setStudentToCampsWithdrawnData(withdrawnCampsData);
		return true;
	}

	/**
     * Registers the student as a camp committee member for a selected camp.
     *
     * @return {@code true} if registration as a committee member is successful, {@code false} otherwise.
     */
	public boolean registerAsCommittee(Student student,Camp camp){

		Map<String, List<String>> registeredCampCommitteeData = DataStore.getCampToRegisteredCampCommitteeData();
		Map<String, String> campCommitteeData = DataStore.getCampCommitteeToCampRegisteredData();
		
		String campCommitteeName = student.getName();
		String campName = camp.getCampInformation().getCampName();

		// Add the CampCommittee name to the list of registered CampCommittee for the camp
		if (!registeredCampCommitteeData.containsKey(campName)) {
			registeredCampCommitteeData.put(campName, new ArrayList<String>());
		}
		registeredCampCommitteeData.get(campName).add(campCommitteeName);

		// Add the camp as the value of student-campcommitee key pair 
		campCommitteeData.put(campCommitteeName,campName);
		
		// Save into DataStore
		DataStore.setCampToRegisteredCampCommitteeData(registeredCampCommitteeData);
		DataStore.setCampCommitteeToCampRegisteredData(campCommitteeData);
		return true;
	}

}
