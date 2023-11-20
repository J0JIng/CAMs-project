package controllers;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import enums.UserRole;

import interfaces.ICampStudentService;

import models.Student;
import models.Camp;
import models.Enquiry;

import services.CampStudentService;
import services.EnquirySenderService;

import stores.AuthStore;

import views.StudentView;
import views.MessageView;

import utility.InputSelectionUtility;

import views.StudentView;

public class StudentController extends UserController {

	private final CampStudentService campStudentService = new CampStudentService();
	private final EnquirySenderService enquiryStudentService = new EnquirySenderService();
	private final StudentView view = new StudentView();
	private final Scanner scanner = new Scanner(System.in);

	public void start() {
        Student student = (Student) AuthStore.getCurrentUser();
	    while (true) {
            view.displayMenuView();
            
            // Checks for invalid inputs
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input!! ");
                scanner.next();
                System.out.print("Select an option: ");
            }

            int choice = scanner.nextInt();
            
			// sanity check 
            if (student.getUserRole() == UserRole.STUDENT) {
            	// Student selection menu
	            switch (choice) {
	                case 1: // View all camps viewable by Student
	                    viewAllCamps();
	                    break;
	                case 2: // View all camps viewable by Student with Filters
	                	viewAllCampsWithFilters();
	                	break;
	                case 3:
						registerCamp();
	                    break;
	                case 4:
						withdrawCamp();
	                    break;
	                case 5:
						viewRemainingSlots();
	                    break;
	                case 6:
						viewRegisteredCamps();
	                    break;
	                case 7:
	                	submitEnquiry();
	                    break;
	                case 8:
	                	viewEnquiries();
	                    break;
	                case 9: 
	                	editEnquiry();
	                	break;
	                case 10: 
	                	deleteEnquiry();
	                	break;
	                case 11: 
	                	if(registerAsCommittee()){
							AuthController.endSession();
							return;
						}
	                	break;
	                case 12: 
						// change password
						if (changePassword()) {
							// Restart session by logging out
							AuthController.endSession();
							return;
						}
						break;
	                default: 
	                    System.out.println("Exiting student menu");
	                    AuthController.endSession();
	                    return;
	            }
            } 
			else{
				// REMOVE ONCE DONE 
				System.out.println("Error Debug");
				AuthController.endSession();
			}
        }
	}

	protected void registerCamp() {
		// Get the current student
		//scanner.nextLine();
		Student student = (Student) AuthStore.getCurrentUser();
		// Get the current date
		Date currentDate = new Date();
		// Get a list of available camps for registration
		List<Camp> availableCamps = campStudentService.getAvailableCampsToRegister();
		// Display the available camps for the student to choose
		view.viewCamps(availableCamps, " - Choose Camp to Register - ");
		// Select a camp for registration
		Camp camp = InputSelectionUtility.campSelector(availableCamps);
	
		if (camp == null) {
			// Invalid input, exit the registration process
			System.out.println("Input Invalid.");
			return;
		}
	
		if (campStudentService.isUserWithdrawnFromCamp(student, camp)) {
			// The student has already withdrawn from the camp
			System.out.println("You are not allowed to register for a camp you have withdrawn!");
			return;
		}
	
		if (campStudentService.isUserRegisteredWithCamp(student, camp)) {
			// The student is already registered for the selected camp
			System.out.println("You are already registered for the camp!");
			return;
		}
	
		if (campStudentService.isCampOver(currentDate, camp)) {
			// The selected camp is already over
			System.out.println("The camp chosen is over!");
			return;
		}
	
		if (campStudentService.hasDateClash(student, camp)) {
			// The selected camp conflicts with a camp the student is already registered for
			System.out.println("The camp chosen conflicts with a registered camp!");
			return;
		}
	
		if (campStudentService.isUserCampCommitteeForCamp(student, camp)) {
			// The student is already registered for the camp as a camp committee member
			System.out.println("You are already registered for the camp as a camp committee member!");
			return;
		}
	
		// Register the student for the selected camp
		String campName = camp.getCampInformation().getCampName();
		boolean success = campStudentService.registerCamp(student, camp);
		String message;
		if (success) message = "Registration for " + campName + " successful!"; else message = "Registration for " + campName + " unsuccessful!";
		MessageView.endMessage(scanner, message, false);
		//System.out.println(success ? "Registration for " + campName + " successful!" : "Registration for " + campName + " unsuccessful!");
	}
	
	protected void withdrawCamp() {
		// Get the current student
		Student student = (Student) AuthStore.getCurrentUser();
		// Get a list of camps the student is registered for
		List<Camp> registeredCamps = campStudentService.getRegisteredCamps();
		// Display the registered camps for the student to choose for withdrawal
		view.viewCamps(registeredCamps, " - Choose Camp to Withdraw - ");
		// Select a camp for withdrawal
		Camp camp = InputSelectionUtility.campSelector(registeredCamps);
	
		if (camp == null) {
			// Invalid input, exit the withdrawal process
			return;
		}
	
		if (campStudentService.isUserWithdrawnFromCamp(student, camp)) {
			// The student has already withdrawn from the camp
			System.out.println("You have already withdrawn from the camp!");
			return;
		}
	
		if (campStudentService.isUserCampCommitteeForCamp(student, camp)) {
			// The student is not allowed to withdraw from a camp they have registered as a camp committee member
			System.out.println("You are not allowed to withdraw from a camp you have registered as a camp committee member!");
			return;
		}
	
		// Withdraw the student from the selected camp
		String campName = camp.getCampInformation().getCampName();
		boolean success = campStudentService.withdrawCamp(student, camp);
		String message;
		if (success) message = "Withdrawal from " + campName + " successful!"; else message = "Withdrawal from " + campName + " unsuccessful!";
		MessageView.endMessage(scanner, message, false);
		//System.out.println(success ? "Withdrawal from " + campName + " successful!" : "Withdrawal from " + campName + " unsuccessful!");
	}


	protected boolean registerAsCommittee(){
		//scanner.nextLine();
		Student student = (Student) AuthStore.getCurrentUser();
		Date currentDate = new Date();

		// Get Data
		List<Camp> availableCamps = campStudentService.getAvailableCampsToRegister();

		// check no camps
		if (availableCamps.isEmpty()) {
			System.out.println("There are currently no available camps to join");
			return false;
		}

		// Display available camps to register
		view.viewCamps(availableCamps, " - Choose Camp to join as Committee Member - ");
		
		// Get User input
		Camp camp = InputSelectionUtility.campSelector(availableCamps);
        if (camp == null) {
            return false;
        }

		if(campStudentService.isUserRegisteredWithCamp(student,camp)){
			System.out.println("You are already registered for the camp!");
			return false;
		}

		if(campStudentService.isUserWithdrawnFromCamp(student,camp)){
			System.out.println("You are not allowed to register for a camp you have withdrawn!");
			return false;
		}

		if(campStudentService.isCampOver(currentDate,camp)){
			System.out.println("The camp chosen is over!");
			return false; 
		}

		if(campStudentService.hasDateClash(student,camp)){
			System.out.println("The camp chosen conflict with registered camp !");
			return false;
		}

		if(campStudentService.isUserCampCommittee(student)){
			System.out.println("You are not allowed to register as a Camp committee for more than one camp!");
			return false;
		}

		if(campStudentService.isCampCommitteeFull(camp)){
			System.out.println("You are not allowed to register as a Camp committee slots is full!");
			return false;
		}
		
		// Register the student for the selected camp
		String campName = camp.getCampInformation().getCampName();
		boolean success = campStudentService.registerAsCommittee(student, camp);
		String message;
		if(success) message = "Registration for " + campName + " successful!"; else message = "Registration for " + campName + " unsuccessful!";
		MessageView.endMessage(scanner, message, false);
		//		System.out.println(success ? "Camp committee registration for " + campName + " successful!, Please Relogin" : 
//									 "Camp committee registration for " + campName + " unsuccessful!");
		
		AuthStore.getCurrentUser().setRole(UserRole.COMMITTEE);
		return true;
	}
	

	/**
     * Shows all no. of remaining slots for all the camps.
     */
    protected void viewRemainingSlots() {
		scanner.nextLine();
		String userInput;
        do {
            view.viewCampsSlots(campStudentService.getAllCamps());
            userInput = scanner.nextLine().trim();
        } while (!userInput.isEmpty());
    }
	
	/**
     * Shows all camps viewable by the student.
     */
    protected void viewAllCamps() {
    	scanner.nextLine();
    	while (true) {
    		view.viewCamps(campStudentService.getAllCamps(), " - List of Camps - ");
    		Camp c = InputSelectionUtility.campSelector(campStudentService.getAllCamps());
    		if (c != null) {
	    		view.viewCampInformation(c);
	    		scanner.nextLine();
    		} else {
    			return;
    		}
    	}
    }
    
    /**
     * Shows the list of camps using user specified filter
     */
	protected void viewAllCampsWithFilters() {
		scanner.nextLine();
		// Various filters for camps
    	String filterBy = null; 		// Type of filter
    	Date filterDate = null;			// Filter date
    	String locationFilter = null;	// Filter location

		view.viewFliterOption();
    	
    	int option = 0;
    	do {
	    	option = InputSelectionUtility.getIntInput("Enter the filter option (1/2/3): ");
	    	switch (option) {
	    		case 1:
	    			filterBy = "date";
		        	filterDate = InputSelectionUtility.getDateInput("Enter the start date to filter by (dd/MM/yyyy): ", new SimpleDateFormat("dd/MM/yyyy"));
					break;
	    		case 2:
	    			filterBy = "location";
		        	locationFilter = InputSelectionUtility.getStringInput("Enter the location to filter by: ");
		        	break;
	        	case 3:
	        		System.out.println("Sorting by alphabetical order..."); // Sort by alphabetical order
	        		break;
	        	default: System.out.println("Invalid option."); 
	        		break;
	    	}
    	} while (option != 0 && option > 3);
    	
    	List<Camp> filteredCamps = new ArrayList<>(); // The filtered list
    	
    	// Chooses to add each camp to filtered list based on filtering criteria chosen
    	for (Camp c : campStudentService.getAllCamps()) {
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
    	
    	if (filteredCamps.isEmpty()) {
        	System.out.println("No matching camps found.");
    	} else {
    		//System.out.println("Not empty");
    		// Displays the filtered list of camps
    		switch (option) {
	    		case 1:
	    			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    			view.viewCamps(filteredCamps, " - List of Camps starting on " + dateFormat.format(filterDate) + " - ");
					break;
	    		case 2:
	    			view.viewCamps(filteredCamps, " - List of Camps in " + locationFilter + " - ");
		        	break;
	        	default: 
	        		view.viewCamps(filteredCamps, " - List of Camps - "); 
	        		break;
    		}
    	}
    	MessageView.endMessage(scanner, null, false);
	}
	
	/**
	 * Shows the camps that the student is registered in.
	 */
	protected void viewRegisteredCamps() {
		List<Camp> list = campStudentService.getRegisteredCamps();
    	while (true) {
    		view.viewCamps(list," - Camps registered by " + AuthStore.getCurrentUser().getName() + " - ");
    		Camp camp = InputSelectionUtility.campSelector(list);
    		if (camp != null) {
	    		view.viewCampInformation(camp);
	    		scanner.nextLine();
	    		scanner.nextLine();
    		} else {
    			return;
    		}
    	}
	}

	public void submitEnquiry() {
    	Student student = (Student) AuthStore.getCurrentUser();
        
        //Get Data
        List<Camp> availableCamps = campStudentService.getAllCamps();

		// Get User input
		view.viewCamps(availableCamps, " - Choose Camp to send enquiry - ");
		// Select a camp for withdrawal
		//Camp camp = InputSelectionUtility.campSelector(registeredCamps);
		Camp selectedCamp = InputSelectionUtility.campSelector(availableCamps);
        assert selectedCamp != null;
        if (selectedCamp == null) {
			// Invalid input, exit the withdrawal process
			return;
		}
        String campName = selectedCamp.getCampInformation().getCampName();
		String enquiryMessage = InputSelectionUtility.getStringInput("Enter enquiry message: ");
		// Prompt the user whether they'd like the enquiry to be saved as draft (1: Yes, 2: No)
	    int draftChoice = InputSelectionUtility.getIntInput("Do you want to save the enquiry as a draft or submit? (1: Draft, 2: Submit): ");
	    boolean isDraft = (draftChoice == 1);

        // Create a new enquiry using EnquiryStudentService
        int enquiryID = enquiryStudentService.createEnquiry(student.getStudentID(), campName, enquiryMessage, isDraft);

        if (isDraft) {
        	MessageView.endMessage(scanner, "Saved Draft Enquiry with ID: " + enquiryID, false);
        	System.out.println();
        } else {
        	MessageView.endMessage(scanner, "Enquiry submitted with ID: " + enquiryID, false);
        }
    }

	/**
	 * Displays the draft, pending and responded enquiries for the current student.
	 * Draft enquiries are those that have not been submitted, and responded enquiries
	 * are those that have received a response.
	 */
	public void viewEnquiries() {
		Student student = (Student) AuthStore.getCurrentUser();

		// Get draft, pending and responded enquiries
		Map<Integer, Enquiry> draftEnquiries = enquiryStudentService.getStudentDraftEnquiries(student.getStudentID());
		Map<Integer, Enquiry> submittedEnquiries = enquiryStudentService.getSubmittedEnquiries(student.getStudentID());
		Map<Integer, Enquiry> respondedEnquiries = enquiryStudentService.getRespondedEnquiries(student.getStudentID());

		// Display enquiries
		view.displayStudentEnquiries(draftEnquiries, submittedEnquiries, respondedEnquiries);

		MessageView.endMessage(scanner, null, true);
	}

	/**
	 * Handles the editing of draft enquiries.
	 *
	 * @return {@code true} if editing is successful, {@code false} otherwise.
	 */
	public boolean editEnquiry() {
		Student student = (Student) AuthStore.getCurrentUser();
		

		// Get Data
		Map<Integer, Enquiry> draftEnquiries = enquiryStudentService.getStudentDraftEnquiries(student.getStudentID());

		// Check if there are draft enquiries to edit
		if (draftEnquiries.isEmpty()) {
			MessageView.endMessage(scanner, "You have no draft enquiries to edit.", false);
			return false;
		}
		
		// Display draft enquiries
		view.displayEnquiries(draftEnquiries);
		
		// Get User input
		Enquiry selectedEnquiry = InputSelectionUtility.enquirySelector(draftEnquiries);

		if (selectedEnquiry != null) {
			String newMessage = InputSelectionUtility.getStringInput("Enter the new enquiry message: ");

			// Prompt the user whether they'd like the enquiry to be saved as draft (1: Yes, 2: No)
			int draftChoice = InputSelectionUtility.getIntInput("Do you want to save the enquiry as a draft or submit? (1: Draft, 2: Submit): ");
			boolean isDraft = (draftChoice == 1);

			MessageView.endMessage(scanner, null, true);
			// Edit the selected draft enquiry using EnquiryStudentService
			return enquiryStudentService.editDraftEnquiry(selectedEnquiry.getEnquiryID(), student.getStudentID(), newMessage, isDraft);
		}
		return false;
	}

	public void deleteEnquiry() {
		Student student = (Student) AuthStore.getCurrentUser();

		// Get Data
		Map<Integer, Enquiry> draftEnquiries = enquiryStudentService.getStudentDraftEnquiries(student.getStudentID());

		// Check if there are draft enquiries to delete
		if (draftEnquiries.isEmpty()) {
			System.out.println("You have no draft enquiries to delete.");
			return;
		}

		// Display draft enquiries
		view.displayEnquiries(draftEnquiries);
		
		// Get User input
		Enquiry selectedEnquiry = InputSelectionUtility.enquirySelector(draftEnquiries);

		if (selectedEnquiry != null) {
			// Confirm deletion
			int confirmChoice = InputSelectionUtility.getIntInput("Are you sure you want to delete this enquiry? (1: Yes, 2: No): ");
			if (confirmChoice == 1) {
				// Delete the selected draft enquiry using EnquiryStudentService
				boolean deleted = enquiryStudentService.deleteDraftEnquiry(selectedEnquiry.getEnquiryID(), student.getStudentID());
				if (deleted) {
					MessageView.endMessage(scanner, "Enquiry deleted successfully.", true);
				} else {
					MessageView.endMessage(scanner, "Failed to delete the enquiry. Please try again.", true);
				}
			} else {
				MessageView.endMessage(scanner, "Enquiry deletion canceled.", true);
			}
		}
	}
}

