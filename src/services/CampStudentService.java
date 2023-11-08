package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import controllers.CampServiceController;
import main.CAMs;
import stores.AuthStore;

import models.Camp;
import models.CampInformation;
import models.Student;
import models.Enquiry;

public class CampStudentService {
	Scanner scanner = new Scanner(System.in);
	
	public void withdrawCamp() {
		Student student = (Student) AuthStore.getCurrentUser();
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				// Check if already registered
				if (c.getRegisteredStudents().contains(student)) {
					// registered, proceed to withdraw student
					//Committee member cannot withdraw
					if (c.getCommitteeMembers().contains(student)){
						System.out.println("Committee members are not allowed to withdraw");
						return;
					}
					// The student is not allowed to register the same camp again
					student.getWithdrawnCamps().add(c);
					// Step 1: Remove the camp from the student's list of registered camps
    				student.getRegisteredCamps().remove(c);
    				// Step 2: Remove the student from the camp's list of registered students
					c.getRegisteredStudents().remove(student);
					System.out.println("Withdrawn from " + c.getCampInformation().getCampName());
				} else {
					// Not registered in camp
					System.out.println("You have not registered for this camp");
				}
				
                return;
            }
        }
		System.out.println("Cannot find " + campName);
	}
	
	public void registerCamp() {
		
		Student student = (Student) AuthStore.getCurrentUser();
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		Date currentDate = new Date(); // Get the current date and time
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				Date campDate = c.getCampInformation().getCampStartDate();
				//Check if the student previously withdraw from this camp
				if (student.getWithdrawnCamps().getCampInformation().getCampName().equalsIgnoreCase(campName)){
					System.out.println("You have previously withdrawn from this camp and not allowed to register again.");
					return;
				}
				// Check if the student is already registered for another camp with a date clash
            	if (hasDateClash(student, c)) {
                	System.out.println("Date clash with another registered camp!");
                	return;
            	}
				// Check if the current date is after the closing date
				if (currentDate.after(c.getCampInformation().getCampRegistrationClosingDate())) {
    				System.out.println("Camp registration has closed.");
				} else {
					// Need to check if any slots available
					if (c.getCampInformation().getCampTotalSlots()-c.getRegisteredStudents().size() > 0) {
						// Need to check is student is registered to prevent double registration
						if (c.getRegisteredStudents().contains(student)){
							System.out.println("Already Registered!");
						} else {
							// Step 1: Add the camp to the student's list of registered camps
							student.getRegisteredCamps().add(c);
							// Step 2: Add the student to the camp's list of registered students
							c.getRegisteredStudents().add(student);
							System.out.println("Registered for " + c.getCampInformation().getCampName());
						}
					} else {
					System.out.println("No slots available");
					}
				}
                return;
            }
        }
		System.out.println("Cannot find " + campName);
	}

	// Helper function to check for date clashes with other registered camps
	private boolean hasDateClash(Student student, Camp newCamp) {
    	for (Camp camp : student.getRegisteredCamps()) {
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

	public void registerAsCommittee(){
		Student student = (Student) AuthStore.getCurrentUser();
		//Checks whether student is already a committee member
		if (student.getCommitteeStatus() != null){
			System.out.println(student.getName() + " is already a committee member for " + student.getCommitteeStatus());
        	return;
		} else {
			scanner.nextLine();
        	System.out.print("Enter Camp Name: ");
        	String campName = scanner.nextLine();
        	for (Camp camp : student.getRegisteredCamps()) {
            	if (camp.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
                	if (camp.getCommitteeMembers().contains(student)) {
                    	System.out.println(student.getName() + " is already a committee member for " + campName);
                    	return;
                }
                if (camp.getCommitteeMembers().size() < camp.getCampInformation().getCampCommitteeSlots()) {
                    // Register student as a committee member for the selected camp
                    camp.getCommitteeMembers().add(student);
                    student.setCommitteeStatus(camp);
                    System.out.println(student.getName() + " registered as a committee member for " + campName);
                } else {
                    System.out.println("No committee slots available for " + campName);
                }
                	return;
            	}
        	}
        	System.out.println("Cannot find " + campName + " in your registered camps.");
			return;
    	}
	}

	public void withdrawFromCommittee(){
		Student student = (Student) AuthStore.getCurrentUser();
		//Checks whether student is a committee member
		if (student.getCommitteeStatus() == null){
			System.out.println(student.getName() + " is not a committee member for any camp.");
        	return;
		} else {
        	String campName = student.getCommitteeStatus();
        	for (Camp camp : student.getRegisteredCamps()) {
            	if (camp.getCampInformation().getCampName().equalsIgnoreCase(campName) && camp.getCommitteeMembers().contains(student)) {
						camp.getCommitteeMembers().remove(student);
						student.setCommitteeStatus(null);
						System.out.println("You are no longer a committee member.");
						return;
            	}
        	}
        }
    }

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
	}

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

	public void viewRemainingSlots() {
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				if (c.getRegisteredStudents().size() != 0) {
					System.out.println((c.getCampInformation().getCampTotalSlots()-c.getRegisteredStudents().size()) + " remaining slots" );
				} else {System.out.println("No students registered");}
                return;
            }
        }
		System.out.println("Cannot find " + campName);
	}
	
	public void submitEnquiry() {
        // Initialize an instance of EnquiryService
        EnquiryService enquiryService = new EnquiryService();
        Student student = (Student) AuthStore.getCurrentUser();
        scanner.nextLine();
        System.out.print("Enter Camp Name: ");
        String campName = scanner.nextLine();
		
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName) && c.getVisibility() == true){
				// Gather the necessary information for the enquiry
        		System.out.print("Enter your enquiry message: ");
        		String enquiryMessage = scanner.nextLine();
				// Call the submitEnquiry method of EnquiryService
        		System.out.print("Do you want to save this enquiry as a draft? (yes/no): ");
    			String saveAsDraft = scanner.nextLine().toLowerCase();

    			boolean isDraft = saveAsDraft.equals("yes");

    			int enquiryID = enquiryService.createEnquiry(student.getStudentID(), campName, enquiryMessage, isDraft);

    			if (enquiryID != 0) {
        			if (isDraft) {
            			System.out.println("Enquiry saved as a draft with ID: " + enquiryID);
        			} else {
            			System.out.println("Enquiry submitted successfully with ID: " + enquiryID);
        			}
    			} else {
       			 	System.out.println("Failed to create the enquiry.");
    			}
			} else {
				System.out.println("No such camp found or it's not avaliable to you.");
			}
    	}
	}

	public void viewEnquiries() {
    	Student student = (Student) AuthStore.getCurrentUser();
    	EnquiryService enquiryService = new EnquiryService();
    	Map<Integer, Enquiry> draftEnquiries = enquiryService.viewDraftEnquiries(student.getStudentID());
		Map<Integer, Enquiry> respondedEnquiries = enquiryService.viewRespondedEnquiries(student.getStudentID());
    	if (draftEnquiries.isEmpty() && respondedEnquiries.isEmpty()) {
        	System.out.println("You have no enquiries to display.");
    	} else {
			if (!respondedEnquiries.isEmpty()){
				System.out.println("Your Responded Enquiries:");
        		for (Enquiry enquiry : respondedEnquiries.values()) {
            		System.out.println("----------------------------");
            		System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
            		System.out.println("Camp Name: " + enquiry.getCampName());
            		System.out.println("Message: " + enquiry.getEnquiryMessage());
					System.out.println("Response: " + enquiry.getEnquiryResponse());
            		System.out.println();
        		}
        		System.out.println("----------------------------");
			}
			if (!draftEnquiries.isEmpty()){
				System.out.println("Your Draft Enquiries:");
        		for (Enquiry enquiry : draftEnquiries.values()) {
            		System.out.println("----------------------------");
            		System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
            		System.out.println("Camp Name: " + enquiry.getCampName());
            		System.out.println("Message: " + enquiry.getEnquiryMessage());
            		System.out.println();
        		}
        		System.out.println("----------------------------");
			}
        	
    	}
	}

	public void editEnquiry() {
    	Student student = (Student) AuthStore.getCurrentUser();
    	scanner.nextLine();
    	System.out.print("Enter the Enquiry ID to edit: ");
    	int enquiryID = scanner.nextInt();
    	EnquiryService enquiryService = new EnquiryService();
		System.out.print("Enter your new enquiry message: ");
        String newEnquiry = scanner.nextLine();
		System.out.println("Enquiry edited. Still save as draft? (yes/no): ");
		String saveAsDraft = scanner.nextLine().toLowerCase();
		boolean isDraft = saveAsDraft.equals("yes");

    	if (enquiryService.editDraftEnquiry(enquiryID, student.getStudentID(), newEnquiry, isDraft)) {
        	if (isDraft) {
            		System.out.println("Enquiry saved as a draft");
        		} else {
            		System.out.println("Enquiry submitted successfully");
        		}
    	} else {
       	System.out.println("Failed to edit the enquiry. Ensure it's your enquiry and it's in DRAFT status (not yet processed).");
    	}
	}

	public void deleteEnquiry() {
    	Student student = (Student) AuthStore.getCurrentUser();
    	scanner.nextLine();
    	System.out.print("Enter the Enquiry ID to delete: ");
    	int enquiryID = scanner.nextInt();

    	EnquiryService enquiryService = new EnquiryService();

    	if (enquiryService.deleteDraftEnquiry(enquiryID, student.getStudentID())) {
        	System.out.println("Enquiry deleted successfully.");
    	} else {
        	System.out.println("Failed to delete the enquiry. Ensure it's your enquiry and it's in DRAFT status (not yet processed).");
    	}
	}

	public void viewEnquiriesForCamp() {
    	String campName = student.getCommitteeStatus();
    	if (campName == null) {
        	System.out.println("Only committee members or staff can view enquiries.");
        	return;
    	}
		EnquiryService enquiryService = new EnquiryService();
    	// Retrieve and display enquiries for the camp
		Camp camp = null;
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				camp = c;
				break;
			}
		}
    	List<Enquiry> enquiries = enquiryService.getEnquiriesForCamp(camp);

    	if (enquiries.isEmpty()) {
        	System.out.println("No enquiries for this camp.");
    	} else {
        	System.out.println("Enquiries for Camp: " + campName);
        	for (Enquiry enquiry : enquiries) {
            	System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
            	System.out.println("Student Name: " + enquiry.getStudentName());
            	System.out.println("Enquiry Date: " + enquiry.getEnquiryDate());
            	System.out.println("Enquiry Message: " + enquiry.getMessage());
            	System.out.println("Enquiry Status: " + enquiry.getEnquiryStatus());
            	System.out.println("Response: " + enquiry.getEnquiryResponse());
            	System.out.println("-----------------------");
        	}
    	}
	}

	public boolean respondToEnquiry(int enquiryID) {
    	String campName = student.getCommitteeStatus();
    	if (campName == null) {
        	System.out.println("Only committee members or staff can respond to enquiries.");
        	return false;
    	}
    	// Check if the enquiry belongs to the camp
		EnquiryService enquiryService = new EnquiryService();
    	Enquiry enquiry = enquiryService.getEnquiry(enquiryID);
    	if (enquiry == null || !enquiry.getCampName().equalsIgnoreCase(camp.getCampInformation().getCampName())) {
        	System.out.println("Enquiry not found or does not belong to the camp.");
        	return false;
    	}
    	System.out.print("Enter your response to the enquiry: ");
    	scanner.nextLine(); // Consume the newline character
    	String response = scanner.nextLine();
    	boolean responseResult = enquiryService.respondToEnquiry(enquiryID, AuthStore.getCurrentUser().getName(), MessageStatus.ACCEPTED, response);
    	if (responseResult) {
        	System.out.println("Enquiry response sent successfully.");
    	} else {
        	System.println("Failed to send the response.");
    	}
    	return responseResult;
	}

	public void submitSuggestion() {
        // Initialize an instance of SuggestionService
        SuggestionService suggestionService = new SuggestionService();
        Student student = (Student) AuthStore.getCurrentUser();
		String campName = student.getCommitteeStatus();
		if (campName == null){
			System.out.println("Only committee members can submit suggestions.");
			return;
		}
        scanner.nextLine();

		System.out.print("Enter your suggestion: ");
		String suggestionNote = scanner.nextLine();
		System.out.print("Do you want to save this suggestion as a draft? (yes/no): ");
    	String saveAsDraft = scanner.nextLine().toLowerCase();
		boolean isDraft = saveAsDraft.equals("yes");
		int suggestionID = suggestionService.submitSuggestion(student.getStudentID(), campName, suggestionNote, isDraft);
    	if (suggestionID != 0) {
        	if (isDraft) {
            	System.out.println("Suggestion saved as a draft with ID: " + suggestionID);
        	} else {
            	System.out.println("Suggestion submitted successfully with ID: " + suggestionID);
        	}
    	} else {
       		System.out.println("Failed to submit the suggestion.");
    	}
	}

	// public void editSuggestion() {
    // 	Student student = (Student) AuthStore.getCurrentUser();
    // 	scanner.nextLine();
    // 	System.out.print("Enter the Suggestion ID to edit: ");
    // 	int enquiryID = scanner.nextInt();

    // 	SuggestionService suggestionService = new SuggestionService();
	// 	System.out.print("Enter your new suggestion: ");
    //     String newSuggestion = scanner.nextLine();
	// 	if (suggestionService.editSuggestion(suggestionID, student.getStudentID(), newSuggestion)) {
    //     	System.out.println("Suggestion edited successfully. Still save as draft? (yes/no): ");
	// 		String saveAsDraft = scanner.nextLine().toLowerCase();
	// 		boolean isDraft = saveAsDraft.equals("yes");
	// 		nextStep = enquiryService.confirmSuggestion(enquiryID, student.getStudentID(), campName, newEnquiry, isDraft);
    // 			if (nextStep) {
    //     			if (isDraft) {
    //         			System.out.println("Enquiry saved as a draft");
    //     			} else {
    //         			System.out.println("Enquiry submitted successfully with new ID: " + newEnquiryID);
    //     			}
    // 			} else {
    //    			 	System.out.println("Failed to edit the enquiry. Ensure it's your enquiry and it's in DRAFT status (not yet processed).");
    // 			}
    // 	} else {
    //    	System.out.println("Failed to edit the enquiry. Ensure it's your enquiry and it's in DRAFT status (not yet processed).");
    // 	}

    // 	if (suggestionService.editSuggestion(suggestionID, student.getStudentID(), newSuggestion)) {
    //     	System.out.println("Suggestion updated successfully.");
    // 	} else {
    //    	System.out.println("Failed to edit the suggestion. Ensure it's your enquiry and it's in DRAFT status (not yet processed).");
    // 	}
	// }


}
