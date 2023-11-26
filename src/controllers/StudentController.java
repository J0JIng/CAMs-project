package controllers;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import enums.UserRole;
import interfaces.ICampStudentService;
import interfaces.ICampValidationService;
import interfaces.IEnquirySenderService;
import models.Student;
import models.Camp;
import models.Enquiry;
import services.CampStudentService;
import services.CampValidationService;
import services.EnquirySenderService;
import stores.AuthStore;
import utility.InputSelectionUtility;
import views.StudentView;
import views.MessageView;

/**
 * The {@link StudentController} class is the main controller for students.
 * It extends from the {@link UserController} class to utilize {@link UserController} password related services
 * {@link StudentController} class provides the allocation of student's desired operations to the respective methods.
 * Such methods include viewing camps, registering and withdrawing from camps and sending enquiries.
 * It utilizes many services such as {@link ICampStudentService} interface to run student permitted 
 * camp related services to perform its desired action.
 */
public class StudentController extends UserController {

	/**
	 * Scanner object for receiving input from the student.
	 */
	private final Scanner scanner = new Scanner(System.in);
	
	/**
	 * Service for handling student-related operations specific to camps.
	 */
	private final ICampStudentService campStudentService = new CampStudentService();
	
	/**
	 * Service for validating camp-related operations for the student.
	 */
	private final ICampValidationService campValidationService = new CampValidationService();
	
	/**
	 * Service for creating, editing and sending enquiries for the student.
	 */
	private final IEnquirySenderService enquiryStudentService = new EnquirySenderService();
	
	/**
	 * View responsible for displaying information to the student.
	 */
	private final StudentView view = new StudentView();

    /**
     * Initiates the student menu, allowing students to perform various operations.
     * The method enters a loop where it repeatedly displays the student menu, takes user input,
     * and executes the corresponding operation based on the user's choice. 
     * The loop continues until the user chooses to log out.
     */
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

	/**
	 * Registers the current student for a selected camp. Various validations are performed before registration.
	 * These include whether they have withdrawn previously, registered currently, date clash 
	 * Displays available camps and allows the student to choose the camp for registration.
	 */
	protected void registerCamp() {
		// Get the current student
		Student student = (Student) AuthStore.getCurrentUser();
		// Get the current date
		Date currentDate = new Date();
		// Get a list of available camps for registration
		List<Camp> availableCamps = campStudentService.getAvailableCampsToRegister();
		// Display the available camps for the student to choose
		view.viewCamps(availableCamps, " - Choose Camp to Register - ");
		// Select a camp for registration
		Camp camp = InputSelectionUtility.getSelectedCamp(availableCamps);
	
		if (camp == null) {
			// Invalid input, exit the registration process
			System.out.println("Input Invalid.");
			return;
		}
	
		if (campValidationService.isUserWithdrawnFromCamp(student, camp)) {
			// The student has already withdrawn from the camp
			System.out.println("You are not allowed to register for a camp you have withdrawn!");
			return;
		}
	
		if (campValidationService.isUserRegisteredWithCamp(student, camp)) {
			// The student is already registered for the selected camp
			System.out.println("You are already registered for the camp!");
			return;
		}
	
		if (campValidationService.isCampOver(currentDate, camp)) {
			// The selected camp is already over
			System.out.println("The camp chosen is over!");
			return;
		}
	
		if (campValidationService.hasDateClash(student, camp)) {
			// The selected camp conflicts with a camp the student is already registered for
			System.out.println("The camp chosen conflicts with a registered camp!");
			return;
		}
	
		if (campValidationService.isUserCampCommitteeForCamp(student, camp)) {
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
	}
	
	/**
	 * Withdraws the current student from a selected camp. Various validations are performed before withdrawal.
	 * These include whether they have withdrawn, or are a committee member.
	 * Displays the camps the student is registered for and allows the student to choose the camp for withdrawal.
	 */
	protected void withdrawCamp() {
		// Get the current student
		Student student = (Student) AuthStore.getCurrentUser();
		// Get a list of camps the student is registered for
		List<Camp> registeredCamps = campStudentService.getRegisteredCamps();
		// Display the registered camps for the student to choose for withdrawal
		view.viewCamps(registeredCamps, " - Choose Camp to Withdraw - ");
		// Select a camp for withdrawal
		Camp camp = InputSelectionUtility.getSelectedCamp(registeredCamps);
	
		if (camp == null) {
			// Invalid input, exit the withdrawal process
			return;
		}
	
		if (campValidationService.isUserWithdrawnFromCamp(student, camp)) {
			// The student has already withdrawn from the camp
			System.out.println("You have already withdrawn from the camp!");
			return;
		}
	
		if (campValidationService.isUserCampCommitteeForCamp(student, camp)) {
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
	}

	/**
	 * Registers the current student as a committee member for a selected camp. Various validations are performed before registration.
	 * These include whether they have withdrawn previously, registered currently, date clash or is a committee member in another camp.
	 * Displays available camps and allows the student to choose the camp for committee registration.
	 *
	 * @return True if the student successfully registers as a committee member, false otherwise.
	 */
	protected boolean registerAsCommittee(){
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
		Camp camp = InputSelectionUtility.getSelectedCamp(availableCamps);
        if (camp == null) {
            return false;
        }

		if(campValidationService.isUserRegisteredWithCamp(student,camp)){
			System.out.println("You are already registered for the camp!");
			return false;
		}

		if(campValidationService.isUserWithdrawnFromCamp(student,camp)){
			System.out.println("You are not allowed to register for a camp you have withdrawn!");
			return false;
		}

		if(campValidationService.isCampOver(currentDate,camp)){
			System.out.println("The camp chosen is over!");
			return false; 
		}

		if(campValidationService.hasDateClash(student,camp)){
			System.out.println("The camp chosen conflict with registered camp !");
			return false;
		}

		if(campValidationService.isUserCampCommittee(student)){
			System.out.println("You are not allowed to register as a Camp committee for more than one camp!");
			return false;
		}

		if(campValidationService.isCampCommitteeFull(camp)){
			System.out.println("You are not allowed to register as a Camp committee slots is full!");
			return false;
		}
		
		// Register the student for the selected camp
		String campName = camp.getCampInformation().getCampName();
		boolean success = campStudentService.registerAsCommittee(student, camp);
		String message;
		if(success) message = "Joined " + campName + " committee successfully!"; else message = "Joined " + campName + " committee unsuccessful!";
		MessageView.endMessage(scanner, message, false);
		AuthStore.getCurrentUser().setRole(UserRole.COMMITTEE);
		return true;
	}
	

	/**
     * Shows all number of remaining slots for all the camps visible to the student's user group.
     */
    protected void viewRemainingSlots() {
        view.viewCampsSlots(campStudentService.getAllCamps());
        MessageView.endMessage(scanner, null, false);
    }
	
	/**
     * Shows all camps viewable by the student based on user group and visibility.
     */
    protected void viewAllCamps() {
		view.viewCamps(campStudentService.getAllCamps(), " - List of Camps - ");
		Camp c = InputSelectionUtility.getSelectedCamp(campStudentService.getAllCamps());
		if (c != null) {
    		view.viewCampInformation(c);
    		MessageView.endMessage(scanner, null, false);
		} else {
			return;
		}
    }
    
    /**
     * Displays a filtered list of camps based on user-defined criteria such as date, location, or alphabetical order.
     * Allows the user to choose a filtering option and show the resulting list of camps.
     */
	protected void viewAllCampsWithFilters() {
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
	 * Displays a list of camps that the current student is registered in. 
	 * Allows the user to view detailed information about a selected camp.
	 */
	protected void viewRegisteredCamps() {
		List<Camp> list = campStudentService.getRegisteredCamps();
    	while (true) {
    		view.viewCamps(list," - Camps registered by " + AuthStore.getCurrentUser().getName() + " - ");
    		Camp camp = InputSelectionUtility.getSelectedCamp(list);
    		if (camp != null) {
	    		view.viewCampInformation(camp);
	    		scanner.nextLine();
	    		scanner.nextLine();
    		} else {
    			return;
    		}
    	}
	}

	/**
	 * Submits an enquiry for a selected camp. 
	 * Allows the user to choose a camp, enter an enquiry message, and decide whether to save the enquiry as a draft or submit it.
	 */
	protected void submitEnquiry() {
    	Student student = (Student) AuthStore.getCurrentUser();
        
        //Get Data
        List<Camp> availableCamps = campStudentService.getAllCamps();
		// Get User input
		view.viewCamps(availableCamps, " - Choose Camp to send enquiry - ");
		// Select a camp for withdrawal
		Camp selectedCamp = InputSelectionUtility.getSelectedCamp(availableCamps);
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
	protected void viewEnquiries() {
		Student student = (Student) AuthStore.getCurrentUser();

		// Get draft, pending and responded enquiries
		Map<Integer, Enquiry> draftEnquiries = enquiryStudentService.getStudentDraftEnquiries(student.getStudentID());
		Map<Integer, Enquiry> submittedEnquiries = enquiryStudentService.getSubmittedEnquiries(student.getStudentID());
		Map<Integer, Enquiry> respondedEnquiries = enquiryStudentService.getRespondedEnquiries(student.getStudentID());

		// Display enquiries
		view.displayEnquiries(draftEnquiries, submittedEnquiries, respondedEnquiries);

		MessageView.endMessage(scanner, null, true);
	}

	/**
	 * Handles the editing of draft enquiries.
	 *
	 * @return {@code true} if editing is successful, {@code false} otherwise.
	 */
	protected boolean editEnquiry() {
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
		Enquiry selectedEnquiry = InputSelectionUtility.getSelectedEnquiry(draftEnquiries);

		if (selectedEnquiry != null) {
			String newMessage = InputSelectionUtility.getStringInput("Enter the new enquiry message: ");

			// Prompt the user whether they'd like the enquiry to be saved as draft (1: Yes, 2: No)
			int draftChoice = InputSelectionUtility.getIntInput("Do you want to save the enquiry as a draft or submit? (1: Draft, 2: Submit): ");
			boolean isDraft = (draftChoice == 1);

			if (isDraft) {
	        	MessageView.endMessage(scanner, "Saved Draft Enquiry with ID: " + selectedEnquiry.getEnquiryID(), true);
	        	System.out.println();
	        } else {
	        	MessageView.endMessage(scanner, "Enquiry submitted with ID: " + selectedEnquiry.getEnquiryID(), true);
	        }
			// Edit the selected draft enquiry using EnquiryStudentService
			return enquiryStudentService.editDraftEnquiry(selectedEnquiry.getEnquiryID(), student.getStudentID(), newMessage, isDraft);
		}
		return false;
	}

	/**
	 * Allows the student to delete a draft enquiry. Displays a list of draft enquiries,
	 * prompts the user to select an enquiry for deletion, and confirms the deletion.
	 * If confirmed, the selected draft enquiry is deleted using EnquiryStudentService.
	 */
	protected void deleteEnquiry() {
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
		Enquiry selectedEnquiry = InputSelectionUtility.getSelectedEnquiry(draftEnquiries);

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

