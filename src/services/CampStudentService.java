package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import enums.FacultyGroups;
import enums.MessageStatus;
import enums.UserRole;
import interfaces.ICampStudentService;
import models.Camp;
import models.Student;
import models.Enquiry;
import stores.AuthStore;
import stores.DataStore;
import utility.InputSelectionUtility;

/**
 * The {@code CampStudentService} class implements the {@link ICampStudentService} interface
 * and provides services related to camp registration and management.
 */
public class CampStudentService implements ICampStudentService {
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
	private boolean hasDateClash(Student student, Camp newCamp) {
		List<Camp> registeredCamps = getRegisteredCamps();
    	for (Camp camp : registeredCamps) {
        	if (dateClashExists(camp, newCamp)) {
            	return true;
        	}
    	}
    	return false;
	}

	/**
     * Checks if there's a date clash between two camps.
     *
     * @param camp1 The first {@link camp} .
     * @param camp2 The second {@link camp} .
     * @return {@code true} if there is a date clash, {@code false} otherwise.
     */
	private boolean dateClashExists(Camp camp1, Camp camp2) {
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
	private boolean isCampOver(Date currentDate ,Camp camp) {
		if(currentDate.after(camp.getCampInformation().getCampRegistrationClosingDate())) return true;
		return false;
	}

	/**
	 * Checks if the {@link camp} is full by comparing the number of registered students with the maximum allowed slots.
	 *
	 * @param camp The {@link camp} to check.
	 * @return {@code true} if the {@link camp} is full, {@code false} otherwise.
	 */
	private boolean isCampFull(Camp camp){
		Map<String, List<String>> registeredStudentData = DataStore.getCampToRegisteredStudentData();
		String campName = camp.getCampInformation().getCampName();
		int maxCampSlots = camp.getCampInformation().getCampTotalSlots();
		if(registeredStudentData.containsKey(campName) && registeredStudentData.get(campName).size() >= maxCampSlots ){
			return true ;
		}else{
			return false;
		}	
	}

	/**
	 * Checks if the camp committee slots are full by comparing the number of registered committee members
	 * with the maximum allowed committee slots.
	 *
	 * @param camp The {@link camp} to check.
	 * @return {@code true} if the camp committee slots are full, {@code false} otherwise.
	 */
	private boolean isCampCommitteeFull(Camp camp){
		Map<String, List<String>> registeredCampCommitteeData = DataStore.getCampToRegisteredCampCommitteeData();
		String campName = camp.getCampInformation().getCampName();
		int maxCampCommitteeSlots = camp.getCampInformation().getCampCommitteeSlots();

		if(registeredCampCommitteeData.containsKey(campName) && registeredCampCommitteeData.get(campName).size() >= maxCampCommitteeSlots ){
			return true ;
		}else{
			return false;
		}		
	}

	/**
	 * Checks if the {@link student} is the camp committee member for the specified camp.
	 *
	 * @param student The {@link student} to check.
	 * @param camp The {@link camp} to check.
	 * @return {@code true} if the {@link student} is a camp committee member for the specified {@link camp}, {@code false} otherwise.
	 */
	private boolean isUserCampCommitteeForCamp(Student student, Camp camp) {
		Map<String, String> campCommitteeData = DataStore.getCampCommitteeToCampRegisteredData();
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();
	
		if (campCommitteeData.containsKey(studentName) && campCommitteeData.get(studentName).equals(campName)) {
			// The student is a camp committee member for the specified camp
			return true;
		} else {
			// The student is not a camp committee member for the specified camp
			return false;
		}
	}

	/**
	 * Checks if the {@link student} is a camp committee member for any {@link camp}.
	 *
	 * @param student The {@link student} to check.
	 * @return {@code true} if the {@link student} is a camp committee member, {@code false} otherwise.
	 */
	private boolean isUserCampCommittee(Student student) {
		Map<String, String> campCommitteeData = DataStore.getCampCommitteeToCampRegisteredData();
		String studentName = student.getName();

		if (campCommitteeData.containsKey(studentName)) {
			// The student is a camp committee member for the specified camp
			return true;
		} else {
			// The student is not a camp committee member for the specified camp
			return false;
		}
	}

	/**
	 * Checks if the {@link Student} has withdrawn from the specified {@link Camp}.
	 *
	 * @param student The {@link Student} to check.
	 * @param camp The {@link Camp} to check.
	 * @return {@code true} if the {@link Student} has withdrawn from the specified {@link Camp}, {@code false} otherwise.
	 */
	private boolean isUserWithdrawnFromCamp(Student student, Camp camp) {
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
	private boolean isUserRegisteredWithCamp(Student student, Camp camp) {
		Map<String, List<String>> registeredCampData = DataStore.getStudentsToCampsRegisteredData();
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();

		return registeredCampData.containsKey(studentName) && registeredCampData.get(studentName).contains(campName);
	}

	/**
	 * Displays the details of the given enquiries.
	 *
	 * @param enquiries the enquiries to display.
	 */
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


	// ---------- interface method implementation ---------- //
	
	/**
     * Retrieves a list of available camps for the currently logged-in student.
     *
     * @return List of available camps.
     */
	@Override
	public List<Camp> getAvailableCamps(){
		Student student = (Student) AuthStore.getCurrentUser();
		Map<String, Camp> campsData = DataStore.getCampData();
		//Date currentDate = new Date();

		List<Camp> availableCamps = campsData.values().stream()
		.filter(camp -> camp.getCampInformation().getFacultyGroup() == student.getFaculty()
					  ||camp.getCampInformation().getFacultyGroup() == FacultyGroups.ALL
					  &&camp.getVisibility() == true
					  &&!isCampFull(camp)
					  //&&!isCampOver(currentDate,camp)
					  //&&!isUserWithdrawnFromCamp(withdrawnCampsData, camp)
					  //&&!hasDateClash(student, camp)
					  )
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
				.filter(entry -> entry.getValue().contains(student.getName()))
				.map(entry -> DataStore.getCampData().get(entry.getKey()))
				.collect(Collectors.toList());
	
		return registeredCamps;
	}
	
	// ---------- Service method implementation ---------- //

	/**
     * Registers the student for a camp based on user input and various checks.
     *
     * @return {@code true} if registration is successful, {@code false} otherwise.
     */
	public boolean registerCamp() {

		Student student = (Student) AuthStore.getCurrentUser();
		Date currentDate = new Date();

		// Get Data
		List<Camp> availableCamps = getAvailableCamps();
		Map<String, List<String>> registeredCampsData = DataStore.getStudentsToCampsRegisteredData();
		Map<String, List<String>> registeredStudentData = DataStore.getCampToRegisteredStudentData();

		// Get User input
		Camp camp = InputSelectionUtility.campSelector(availableCamps);
        if (camp == null) {
            return false;
        }

		if(isUserWithdrawnFromCamp(student,camp)){
			System.out.println("You are not allowed to register for a camp you have withdrawn!");
			return false;
		}

		if(isUserRegisteredWithCamp(student,camp)){
			System.out.println("You are already registered for the camp!");
			return false;
		}

		if(isCampOver(currentDate,camp)){
			System.out.println("The camp chosen is over!");
			return false;
		}

		if(hasDateClash(student,camp)){
			System.out.println("The camp chosen conflict with registered camp !");
			return false;
		}

		if(isUserCampCommitteeForCamp(student, camp)){
			System.out.println("You are already registered for the camp as a camp committee member!");
			return false;
		}
		
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();

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
	public boolean withdrawCamp() {
		
		Student student = (Student) AuthStore.getCurrentUser();

		// Get Data
		List<Camp> RegisteredCamps = getRegisteredCamps();
		Map<String, List<String>> withdrawnCampsData = DataStore.getStudentToCampsWithdrawnData();
		Map<String, List<String>> registeredCampsData = DataStore.getStudentsToCampsRegisteredData();
		Map<String, List<String>> registeredStudentData = DataStore.getCampToRegisteredStudentData();

		// Get user input
    	Camp camp = InputSelectionUtility.campSelector(RegisteredCamps);
        if (camp == null) {
            return false;
        }

		if(isUserWithdrawnFromCamp(student,camp)){
			System.out.println("You are have already withdrawn from the camp!");
			return false;
		}

		if(isUserCampCommitteeForCamp(student,camp)){
			System.out.println("You are not allowed to withdraw from a camp you have registered as a camp committee member!");
			return false;
		}

		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();

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
		
		System.out.println("Registered to chosen camp successfully!");
		return true;
	}

	/**
     * Registers the student as a camp committee member for a selected camp.
     *
     * @return {@code true} if registration as a committee member is successful, {@code false} otherwise.
     */
	public boolean registerAsCommittee(){

		Student student = (Student) AuthStore.getCurrentUser();
		Date currentDate = new Date();

		// Get Data
		List<Camp> availableCamps = getAvailableCamps();
		Map<String, List<String>> registeredCampCommitteeData = DataStore.getCampToRegisteredCampCommitteeData();
		Map<String, String> campCommitteeData = DataStore.getCampCommitteeToCampRegisteredData();

		// Get User input
		Camp camp = InputSelectionUtility.campSelector(availableCamps);
        if (camp == null) {
            return false;
        }

		if(isUserRegisteredWithCamp(student,camp)){
			System.out.println("You are already registered for the camp!");
			return false;
		}

		if(isUserWithdrawnFromCamp(student,camp)){
			System.out.println("You are not allowed to register for a camp you have withdrawn!");
			return false;
		}

		if(isCampOver(currentDate,camp)){
			System.out.println("The camp chosen is over!");
			return false;
		}

		if(hasDateClash(student,camp)){
			System.out.println("The camp chosen conflict with registered camp !");
			return false;
		}

		if(isUserCampCommittee(student)){
			System.out.println("You are not allowed to register as a Camp committee for more than one camp!");
			return false;
		}

		if(isCampCommitteeFull(camp)){
			System.out.println("You are not allowed to register as a Camp committee slots is full!");
			return false;
		}
		
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
		System.out.println("Registered to chosen camp successfully!");
		AuthStore.getCurrentUser().setRole(UserRole.COMMITTEE);
		return true;
	}
	
	/**
     * Submits an enquiry for the currently logged-in student.
     *
     * @param enquiryMessage The message of the enquiry.
     * @param isDraft        Whether the enquiry is a draft.
     * @return The ID of the submitted enquiry.
     */
    public void submitEnquiry() {
    	Student student = (Student) AuthStore.getCurrentUser();
        EnquiryStudentService enquiryService = new EnquiryStudentService();
        
        //Get Data
        List<Camp> availableCamps = getAvailableCamps();

		// Get User input
		Camp selectedCamp = InputSelectionUtility.campSelector(availableCamps);
        assert selectedCamp != null;
        String campName = selectedCamp.getCampInformation().getCampName();
		String enquiryMessage = InputSelectionUtility.getStringInput("Enter enquiry message: ");
		// Prompt the user whether they'd like the enquiry to be saved as draft (1: Yes, 2: No)
	    int draftChoice = InputSelectionUtility.getIntInput("Do you want to save the enquiry as a draft? (1: Yes, 2: No): ");
	    boolean isDraft = (draftChoice == 1);

        // Create a new enquiry using EnquiryStudentService
        int enquiryID = enquiryService.createEnquiry(student.getStudentID(), campName, enquiryMessage, isDraft);

        System.out.println("Enquiry submitted with ID: " + enquiryID);
    }

	/**
	 * Displays the draft, pending and responded enquiries for the current student.
	 * Draft enquiries are those that have not been submitted, and responded enquiries
	 * are those that have received a response.
	 */
	public void viewEnquiries() {
		Student student = (Student) AuthStore.getCurrentUser();
		EnquiryStudentService enquiryService = new EnquiryStudentService();

		// Get draft, pending and responded enquiries
		Map<Integer, Enquiry> draftEnquiries = enquiryService.viewDraftEnquiries(student.getStudentID());
		Map<Integer, Enquiry> submittedEnquiries = enquiryService.viewSubmittedEnquiries(student.getStudentID());
		Map<Integer, Enquiry> respondedEnquiries = enquiryService.viewRespondedEnquiries(student.getStudentID());

		// Display draft enquiries
		System.out.println("Draft Enquiries:");
		displayEnquiries(draftEnquiries);

		// Display submitted enquiries
		System.out.println("\nSubmitted Enquiries:");
		displayEnquiries(submittedEnquiries);

		// Display responded enquiries
		System.out.println("\nResponded Enquiries:");
		displayEnquiries(respondedEnquiries);
	}

	/**
	 * Handles the editing of draft enquiries.
	 *
	 * @return {@code true} if editing is successful, {@code false} otherwise.
	 */
	public boolean editEnquiry() {
		Student student = (Student) AuthStore.getCurrentUser();
		EnquiryStudentService enquiryService = new EnquiryStudentService();

		// Get Data
		Map<Integer, Enquiry> draftEnquiries = enquiryService.viewDraftEnquiries(student.getStudentID());

		// Check if there are draft enquiries to edit
		if (draftEnquiries.isEmpty()) {
			System.out.println("You have no draft enquiries to edit.");
			return false;
		}

		// Get User input
		Enquiry selectedEnquiry = InputSelectionUtility.enquirySelector(draftEnquiries);

		if (selectedEnquiry != null) {
			String newMessage = InputSelectionUtility.getStringInput("Enter the new enquiry message: ");

			// Prompt the user whether they'd like the enquiry to be saved as draft (1: Yes, 2: No)
			int draftChoice = InputSelectionUtility.getIntInput("Do you want to save the enquiry as a draft? (1: Yes, 2: No): ");
			boolean isDraft = (draftChoice == 1);

			// Edit the selected draft enquiry using EnquiryStudentService
			return enquiryService.editDraftEnquiry(selectedEnquiry.getEnquiryID(), student.getStudentID(), newMessage, isDraft);
		}
		return false;
	}

	/**
	 * Handles the deletion of a draft enquiry for a student.
	 * Prompts the user to select a draft enquiry and confirms the deletion.
	 * Deletes the selected draft enquiry if confirmed.
	 */
	public void deleteEnquiry() {
		Student student = (Student) AuthStore.getCurrentUser();
		EnquiryStudentService enquiryService = new EnquiryStudentService();

		// Get Data
		Map<Integer, Enquiry> draftEnquiries = enquiryService.viewDraftEnquiries(student.getStudentID());

		// Check if there are draft enquiries to delete
		if (draftEnquiries.isEmpty()) {
			System.out.println("You have no draft enquiries to delete.");
			return;
		}

		// Get User input
		Enquiry selectedEnquiry = InputSelectionUtility.enquirySelector(draftEnquiries);

		if (selectedEnquiry != null) {
			// Confirm deletion
			int confirmChoice = InputSelectionUtility.getIntInput("Are you sure you want to delete this enquiry? (1: Yes, 2: No): ");
			if (confirmChoice == 1) {
				// Delete the selected draft enquiry using EnquiryStudentService
				boolean deleted = enquiryService.deleteDraftEnquiry(selectedEnquiry.getEnquiryID(), student.getStudentID());
				if (deleted) {
					System.out.println("Enquiry deleted successfully.");
				} else {
					System.out.println("Failed to delete the enquiry. Please try again.");
				}
			} else {
				System.out.println("Enquiry deletion canceled.");
			}
		}
	}

	/**
	 * Handles the viewing of enquiries for a specific camp committee member.
	 * Prompts the camp committee member to select a camp and displays the enquiries related to that camp.
	 * Enquiries include both draft and responded enquiries.
	 * Available options are presented for each enquiry, including viewing details and responding.
	 */
	public void viewEnquiriesForCamp() {
		Student student = (Student) AuthStore.getCurrentUser();
		EnquiryStudentService enquiryService = new EnquiryStudentService();
		// Check if the student is a camp committee member
		if(!isUserCampCommittee(student)){
			System.out.println("Only committee members can view enquiries!");
			return;
		}
		// Get User's committee camp
		Camp committeecamp = getCommitteeCamp(student);
		Map<Integer, Enquiry> campEnquiries = getAllEnquiriesForCamp(committeecamp);
		displayEnquiries(campEnquiries);
	}


	public Map<Integer, Enquiry> getAllEnquiriesForCamp(Camp camp) {
		Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
		String campName = camp.getCampInformation().getCampName();

		// Returns a Map<Integer, Enquiry> with Enquiries having the specified Camp name.
		Map<Integer, Enquiry> enquiriesForCampMap = enquiryData.values().stream()
				.filter(enquiry -> campName.equals(enquiry.getCampName()))
				.collect(Collectors.toMap(Enquiry::getEnquiryID, enquiry -> enquiry));

		return enquiriesForCampMap;
	}

	public static Camp getCommitteeCamp(Student student) {
		// Step 1: Get the camp name associated with the student (committee member)
		Map<String, String> committeeToCampData = DataStore.getCampCommitteeToCampRegisteredData();
		String studentName = student.getName();
		String campName = committeeToCampData.get(studentName);

		// Debugging statement
		System.out.println("Student Name: " + studentName);
		System.out.println("Camp Name from Committee Data: " + campName);

		// Step 2: Get the Camp object using the camp name (case-insensitive)
		Map<String, Camp> campData = DataStore.getCampData();
		Camp committeeCamp = campData.values().stream()
				.filter(camp -> camp.getCampInformation().getCampName().equalsIgnoreCase(campName))
				.findFirst()
				.orElse(null);

		// Debugging statement
		System.out.println("Committee Camp: " + committeeCamp);

		return committeeCamp;
	}


	public boolean respondEnquiry() {
		Student student = (Student) AuthStore.getCurrentUser();
		EnquiryStudentService enquiryService = new EnquiryStudentService();

		if(!isUserCampCommittee(student)){
			System.out.println("Only committee members can respond to enquiries!");
			return false;
		}

		// Get Data
		Camp selectedCamp = getCommitteeCamp(student);
		// Get enquiries for the selected camp
		Map<Integer, Enquiry> campEnquiries = getAllEnquiriesForCamp(selectedCamp);
		// Check if there are draft enquiries to edit
		if (campEnquiries.isEmpty()) {
			System.out.println("You have no enquiries to reply to.");
			return false;
		}

		// Get User input
		Enquiry selectedEnquiry = InputSelectionUtility.enquirySelector(campEnquiries);
        String response = InputSelectionUtility.getStringInput("Enter response: ");
		student.incrementStudentPoints();
        // Respond using EnquiryStudentService
        return enquiryService.respondToEnquiry(selectedEnquiry.getEnquiryID(), student.getStudentID(), MessageStatus.ACCEPTED, response);
    }


}
