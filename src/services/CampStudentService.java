package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import enums.FacultyGroups;
import interfaces.ICampStudentService;
import interfaces.ICampValidationService;
import models.Camp;
import models.Student;
import stores.AuthStore;
import stores.DataStore;

/**
 * The {@link CampStudentService} class implements the {@link ICampStudentService} interface
 * and provides services related to camp registration and management.
 */
public class CampStudentService implements ICampStudentService {
	
	/**
	 * The {@link campValidationService} field represents an instance of
	 * {@link ICampValidationService} used for validating camp-related operations.
	 * It is initialized with an instance of {@link CampValidationService}.
	 * It is stored as a private variable to prevent direct usage of validation services
	 * outside the scope of a service.
	 */
	private final static ICampValidationService campValidationService = new CampValidationService();

	/**
     * Constructs an instance of the {@link CampStudentService} class.
     */
	public CampStudentService(){
	}

	// ---------- interface Service method implementation ---------- //

	/**
     * Retrieves a list of available camps that a student can register for.
     * Restricted based on student's faculty group, camp's visibility to students,
     * and whether slots are still available.
     *
     * @return a {@link List} of {@link Camp} objects representing the available camps
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
					  &&!campValidationService.isCampFull(camp))
		.collect(Collectors.toCollection(ArrayList::new));

		return availableCamps;		
	}
	
	/**
     * Retrieves a list of all camps visible to a student.
     * Restricted based on student's faculty group and camp's visibility to students.
     *
     * @return a {@link List} of {@link Camp} objects representing all camps
     */
	@Override
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
     * Retrieves a list of camps from which the student has withdrawn from.
     *
     * @return a {@link List} of {@link Camp} objects representing the withdrawn camps
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
     * Retrieves a list of camps for which the student is currently registered in.
     *
     * @return a {@link List} of {@link Camp} objects representing the registered camps
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

	/**
     * Return camp for which the student is currently registered as a Committee Member.
     *
     * @return a {@link Camp} representing the camp registered by Committee Member.
     */
	@Override
	public Camp getCampCommitteeCamp(Student student) {
		Map<String, String> registeredCamp = DataStore.getCampCommitteeToCampRegisteredData();
		String campID = registeredCamp.get(student.getName());
		Map<String, Camp> campData = DataStore.getCampData();
	
		if (campID != null && campData.containsKey(campID)) {
			return campData.get(campID);
		} else {
			return null; 
		}
	}

	/**
     * Registers the student for a camp based on user input and various checks.
     *
     * @return {@code true} if registration is successful, {@code false} otherwise.
     */
	@Override
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
	@Override
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
	@Override
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
