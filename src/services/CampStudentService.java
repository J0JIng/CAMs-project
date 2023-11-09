package services;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import controllers.CampServiceController;
//import controllers.CampServiceController;
//import controllers.CampServiceController;

import interfaces.ICampStudentService;
import utility.InputSelectionUtility;

import enums.MessageStatus;
import enums.FacultyGroups;
import enums.UserRole;

import stores.AuthStore;
import stores.DataStore;

import models.Camp;
import models.CampInformation;
import models.Student;
import models.User;
import models.Enquiry;

public class CampStudentService implements ICampStudentService {
	/**
	 * Constructs an instance of the CampStudentService class.
	 */
	public CampStudentService(){
	}

	// ---------- Helper Function ---------- //

	// Helper function to check for date clashes with other registered camps
	private boolean hasDateClash(Student student, Camp newCamp) {
		List<Camp> registeredCamps = getRegisteredCamps();
    	for (Camp camp : registeredCamps) {
        	if (dateClashExists(camp, newCamp)) {
            	return true;
        	}
    	}
    	return false;
	}

	// Helper function to check if there's a date clash between two camps
	private boolean dateClashExists(Camp camp1, Camp camp2) {
    	Date camp1StartDate = camp1.getCampInformation().getCampStartDate();
    	Date camp1EndDate = camp1.getCampInformation().getCampEndDate();
    	Date camp2StartDate = camp2.getCampInformation().getCampStartDate();
    	Date camp2EndDate = camp2.getCampInformation().getCampEndDate();

    	// Check if camp1 ends before camp2 starts or camp1 starts after camp2 ends
    	return camp1StartDate.before(camp2EndDate) && camp1EndDate.after(camp2StartDate);
	}

	// Helper function to check if camp is over
	private boolean isCampOver(Date currentDate ,Camp camp) {
		if(currentDate.after(camp.getCampInformation().getCampRegistrationClosingDate())) return true;
		return false;
	}

	// Helper function to check if Camp is Full 
	private boolean isCampFull(Camp camp){
		Map<String, List<String>> registeredStudentData = DataStore.getRegisteredStudentData();
		String campName = camp.getCampInformation().getCampName();
		int maxCampSlots = camp.getCampInformation().getCampTotalSlots();
		if(registeredStudentData.containsKey(campName) && registeredStudentData.get(campName).size() >= maxCampSlots ){
			return true ;
		}else{
			return false;
		}	
	}

	// Helper function to check if Camp committee slots is Full 
	private boolean isCampCommitteeFull(Camp camp){
		Map<String, List<String>> registeredCampCommitteeData = DataStore.getRegisteredCampCommitteeData();
		String campName = camp.getCampInformation().getCampName();
		int maxCampCommitteeSlots = camp.getCampInformation().getCampCommitteeSlots();

		if(registeredCampCommitteeData.containsKey(campName) && registeredCampCommitteeData.get(campName).size() >= maxCampCommitteeSlots ){
			return true ;
		}else{
			return false;
		}		
	}

	// Helper function to check if Student is the CampCommittee for the camp
	private boolean isUserCampCommitteeForCamp(Student student, Camp camp) {
		Map<String, String> campCommitteeData = DataStore.getCampCommitteeData();
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

	// Helper function to check if Student is a CampCommittee 
	private boolean isUserCampCommittee(Student student) {
		Map<String, String> campCommitteeData = DataStore.getCampCommitteeData();
		String studentName = student.getName();

		if (campCommitteeData.containsKey(studentName)) {
			// The student is a camp committee member for the specified camp
			return true;
		} else {
			// The student is not a camp committee member for the specified camp
			return false;
		}
	}

	// Helper function to check if Student has withdrawn from the camp
	private boolean isUserWithdrawnFromCamp(Student student, Camp camp) {
		Map<String, List<String>> withdrawnCampData = DataStore.getWithdrawnCampData();
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();
	
		if (withdrawnCampData.containsKey(studentName) && withdrawnCampData.get(studentName).contains(campName)) {
			// The student has withdrawn from the specified camp
			return true;
		} else {
			// The student has not withdrawn from the specified camp
			return false;
		}
	}
	
	// Helper function to check if Student has registered with the camp
	private boolean isUserRegisteredWithCamp(Student student, Camp camp) {
		Map<String, List<String>> registeredCampData = DataStore.getRegisteredCampData();
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();
	
		if (registeredCampData.containsKey(studentName) && registeredCampData.get(studentName).contains(campName)) {
			// The student has registered with the specified camp
			return true;
		} else {
			// The student has not registered with the specified camp
			return false;
		}
	}

	// ---------- interface method implementation ---------- //
	
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

	@Override
	public List<Camp> getWithdrawnCamps() {
		Student student = (Student) AuthStore.getCurrentUser();
		Map<String, List<String>> withdrawnCampsData = DataStore.getWithdrawnCampData();
	
		List<Camp> withdrawnCamps = withdrawnCampsData.entrySet().stream()
				.filter(entry -> entry.getValue().contains(student.getName()))
				.map(entry -> DataStore.getCampData().get(entry.getKey()))
				.collect(Collectors.toList());
	
		return withdrawnCamps;
	}
	
	@Override
	public List<Camp> getRegisteredCamps() {
		Student student = (Student) AuthStore.getCurrentUser();
		Map<String, List<String>> registeredCampsData = DataStore.getRegisteredCampData();
	
		List<Camp> registeredCamps = registeredCampsData.entrySet().stream()
				.filter(entry -> entry.getValue().contains(student.getName()))
				.map(entry -> DataStore.getCampData().get(entry.getKey()))
				.collect(Collectors.toList());
	
		return registeredCamps;
	}
	

	// ---------- Service method implementation ---------- //

	// Register for Camp
	public boolean registerCamp() {

		Student student = (Student) AuthStore.getCurrentUser();
		Date currentDate = new Date();

		// Get Data
		List<Camp> availableCamps = getAvailableCamps();
		Map<String, List<String>> registeredCampsData = DataStore.getRegisteredCampData();
		Map<String, List<String>> registeredStudentData = DataStore.getRegisteredStudentData();

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
		DataStore.setRegisteredCampData(registeredCampsData);
		DataStore.setRegisteredStudentData(registeredStudentData);
		System.out.println("Registered to chosen camp successfully!");

		return true;
	}

	// Withdraw From Camp (Handle logic for Both Atendee and Campcommittee member)
	public boolean withdrawCamp() {
		
		Student student = (Student) AuthStore.getCurrentUser();

		// Get Data
		List<Camp> RegisteredCamps = getRegisteredCamps();
		Map<String, List<String>> withdrawnCampsData = DataStore.getWithdrawnCampData();
		Map<String, List<String>> registeredCampsData = DataStore.getRegisteredCampData();
		Map<String, List<String>> registeredStudentData = DataStore.getRegisteredStudentData();

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
		DataStore.setRegisteredCampData(registeredCampsData);
		DataStore.setRegisteredStudentData(registeredStudentData);
		DataStore.setWithdrawnCampData(withdrawnCampsData);

		System.out.println("Registered to chosen camp successfully!");
		return true;
	}

	// Register As Committee
	public boolean registerAsCommittee(){

		Student student = (Student) AuthStore.getCurrentUser();
		Date currentDate = new Date();

		// Get Data
		List<Camp> availableCamps = getAvailableCamps();
		Map<String, List<String>> registeredCampCommitteeData = DataStore.getRegisteredCampCommitteeData();
		Map<String, String> campCommitteeData = DataStore.getCampCommitteeData();

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
		DataStore.setRegisteredCampCommitteeData(registeredCampCommitteeData);
		DataStore.setCampCommitteeData(campCommitteeData);
		System.out.println("Registered to chosen camp successfully!");
		AuthStore.getCurrentUser().setRole(UserRole.COMMITTEE);
		return true;
	}

	////////////////////////////////////////////////////////////////

	// Place in Boundary Class
	public void viewRegisteredCamps() {
    	Student student = (Student) AuthStore.getCurrentUser();
    	if (student.getRegisteredCamps().isEmpty()) {
        	System.out.println("You are not registered in a camp.");
        	return;
    	}
    	System.out.println("----------------------------");
    	System.out.println("|          Camps           |");
    	System.out.println("----------------------------");

    	boolean committeeCampPrinted = false;
    	List<String> otherCampNames = new ArrayList<>();

    	for (Camp camp : student.getRegisteredCamps()) {
        	if (camp.getCampInformation().getCampName() == student.getCommitteeStatus() && !committeeCampPrinted) {
            	System.out.println("Committee Camp: " + camp.getCampInformation().getCampName());
				System.out.println("Start Date: " + camp.getCampInformation().getCampStartDate());
				System.out.println("End Date: " + camp.getCampInformation().getCampEndDate());
            	System.out.println("Registration Closing Date: " + camp.getCampInformation().getCampRegistrationClosingDate());
            	System.out.println("User Group: " + camp.getCampInformation().getCampUserGroup());
            	System.out.println("Location: " + camp.getCampInformation().getCampLocation());
            	System.out.println("Total Slots: " + camp.getCampInformation().getCampTotalSlots());
            	System.out.println("Committee Slots: " + camp.getCampInformation().getCampCommitteeSlots());
            	System.out.println("Description: " + camp.getCampInformation().getCampDescription());
            	System.out.println("Staff In Charge: " + camp.getCampInformation().getCampStaffInCharge());
            	System.out.println("------------------------------");
            	committeeCampPrinted = true;
        	} else {
            	if (!committeeCampPrinted) {
                	System.out.println("Committee Camp: None");
                	System.out.println("------------------------------");
                	committeeCampPrinted = true;
            	}

            	otherCampNames.add(camp.getCampInformation().getCampName());
        	}
    	}
    	// Sort the otherCampNames list alphabetically
    	Collections.sort(otherCampNames);

    	System.out.println("Registered camps as an attendee:");
    	for (String campName : otherCampNames) {
        	System.out.println("Camp: " + campName);
    	}
	}

	// Place in Boundary Class
	public void viewAllCampsWithFilters() {
    	Scanner scanner = new Scanner(System.in);

    	System.out.println("Filter Options:");
    	System.out.println("1. Filter by Date");
    	System.out.println("2. Filter by Location");
    	System.out.println("3. Sort by Name (Alphabetical Order)");
    	System.out.print("Enter the filter option (1/2/3): ");

    	int option = scanner.nextInt();
    	scanner.nextLine(); // Consume the newline character

    	if (option == 1) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        	Date filterDate = null;
        	while (filterDate == null) {
            	System.out.print("Enter the date to filter by (dd/MM/yyyy): ");
            	String dateFilter = scanner.nextLine();
            	try {
                	filterDate = dateFormat.parse(dateFilter);
            	} catch (ParseException e) {
                	System.out.println("Invalid date format. Please enter a date in the format dd/MM/yyyy.");
            	}
        	}
			viewAllCamps("date", filterDate, null);
    	} else if (option == 2) {
        	System.out.print("Enter the location to filter by: ");
        	String locationFilter = scanner.nextLine();
        	viewAllCamps("location", null, locationFilter); // Pass the location filter
    	} else if (option == 3) {
        	viewAllCamps(null, null, null);  // Sort by alphabetical order
    	} else {
        	System.out.println("Invalid option.");
    	}
		scanner.close();
	}

	// Place in Boundary Class
	public void viewAllCamps(String filterBy, Date filterDate, String locationFilter) {
    	List<Camp> filteredCamps = new ArrayList<>();
    	for (Camp c : CampServiceController.camps) {
        	if (c.getVisibility()) {
            	if (filterBy == null) {
                	// Default sorting by name
                	filteredCamps.add(c);
            	} else if (filterBy.equals("date")) {
                	if (filterDate != null) {
                    	Date campDate = c.getCampInformation().getCampStartDate();
                    	if (campDate.equals(filterDate)) {
                        	filteredCamps.add(c);
                    	}
                	}
            	} else if (filterBy.equals("location")) {
                	String campLocation = c.getCampInformation().getCampLocation();
                	if (campLocation.equalsIgnoreCase(locationFilter)) { // Compare with the provided location filter
                    	filteredCamps.add(c);
                	}
            	}
        	}
    	}

    	// Sort filteredCamps here based on the chosen filter (e.g., by name, date, or location)
		Collections.sort(filteredCamps, (camp1, camp2) -> {
    		String name1 = camp1.getCampInformation().getCampName();
    		String name2 = camp2.getCampInformation().getCampName();
    		return name1.compareTo(name2);
		});
    	if (filteredCamps.isEmpty()) {
        	System.out.println("No matching camps found.");
    	} else {
        	// Print the filtered and sorted camps
        	System.out.println("----------------------------");
        	System.out.println("|          Camps           |");
        	System.out.println("----------------------------");

        	int i = 1;
        	for (Camp c : filteredCamps) {
            	CampInformation campInfo = c.getCampInformation();
            	System.out.println("----------------------------");
            	System.out.println("Camp " + (i) + ":");
            	System.out.println("Name: " + campInfo.getCampName());
            	System.out.println("Start Date: " + campInfo.getCampStartDate());
				System.out.println("End Date: " + campInfo.getCampEndDate());
            	System.out.println("Registration Closing Date: " + campInfo.getCampRegistrationClosingDate());
            	System.out.println("User Group: " + campInfo.getCampUserGroup());
            	System.out.println("Location: " + campInfo.getCampLocation());
            	System.out.println("Total Slots: " + campInfo.getCampTotalSlots());
            	System.out.println("Committee Slots: " + campInfo.getCampCommitteeSlots());
            	System.out.println("Description: " + campInfo.getCampDescription());
            	System.out.println("Staff In Charge: " + campInfo.getCampStaffInCharge());
            	i++;
        	}
    	}
	}

	// Place in Boundary Class
	public void viewRemainingSlots() {
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				if (c.getRegisteredStudents().size() != 0) {
					System.out.println((c.getCampInformation().getCampTotalSlots()-c.getRegisteredStudents().size()) + " remaining slots" );
				} else {System.out.println("No students registered");}
				scanner.close();
                return;
            }
        }
		System.out.println("Cannot find " + campName);
		scanner.close();
	}
	
}
