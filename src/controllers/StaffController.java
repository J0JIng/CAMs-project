package controllers;

//import static controllers.UserController.sc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import enums.MessageStatus;
import enums.UserRole;
import models.Camp;
import models.CampInformation;
import models.Enquiry;
import models.Student;
import models.Suggestion;
import models.Staff;
import services.CampStaffService;
import services.EnquiryResponderService;
import services.SuggestionResponderService;
import stores.AuthStore;
import stores.DataStore;
import utility.InputSelectionUtility;
import utility.ViewUtility;
import views.StaffView;

public class StaffController extends UserController {

    private final Scanner scanner = new Scanner(System.in);
    private final CampStaffService campStaffService = new CampStaffService();
    private final EnquiryResponderService enquiryStaffService = new EnquiryResponderService();
    private final SuggestionResponderService suggestionStaffService = new SuggestionResponderService();
    private final StaffView view = new StaffView();

    public StaffController() {
    }

    public void start() {
        while (true) {
            view.displayMenuView();

            // Checks for invalid inputs
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input!! ");
                scanner.next();
                System.out.print("Select an option: ");
            }
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createCamp();
                    break;
                case 2:
                    deleteCamp();
                    break;
                case 3:
                    updateCampDetails();
                    break;
                case 4:
                    toggleCampVisibility();
                    break;
                case 5:
                    viewAllCamps();
                    break;
                case 6:
                    viewAllCampsWithFilters();
                    break;
                case 7:
                    viewCreatedCamps();
                    break;
                case 8:
                    viewStudentList();
                    break;
                case 9:
                	viewEnquiriesForCamp(); 
                	break;
                case 10:
                	respondToEnquiry(); 
                	break;
                case 11:
                    viewSuggestionForCamp();
                	break;
                case 12:
                    respondToSuggestion();
                	break;
                case 13:
                    // Generate camp Report with filters 
                	break;
                case 14:
                    // Camp enquiry report
                	break;
                case 15:

                	break;
                case 16:
                	// Change password
                    if (changePassword()) {
                        // Restart session by logging out
                        AuthController.endSession();
                        return;
                    }
                	break;
                default:
                    System.out.println("Exiting Staff menu");
                    AuthController.endSession();
                    return;  
            }
        }
    }

    // ---------- Helper Methods ---------- //

    protected boolean isCampRegistered(Camp camp) {
        return campStaffService.campIsRegistered(camp);
    }

    protected void toggleCampVisibility() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
        List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
        view.viewCamps(staffCreatedCamps, " - Choose Camp to Toggle Visibility - ");
        Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);
        view.viewToggleOption(camp);
        campStaffService.toggleCampVisibility(camp);
    }

    protected void createCamp() {
        // Get number of projects to create from user
        Staff staff = (Staff) AuthStore.getCurrentUser();
        int campCount =  InputSelectionUtility.getIntInput("Enter the number of Camps to create: ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<Camp> listNewCamps = new ArrayList<Camp>();

        for(int i = 0 ; i<campCount ; i++){
            CampInformation campInfo = InputSelectionUtility.getCampInput(campStaffService.getAllCamps(),staff,dateFormat);
            Camp newCamp= new Camp(campInfo);
            System.out.println("Created " + newCamp.getCampInformation().getCampName());
            listNewCamps.add(newCamp);
        }        

        if (campStaffService.createCamp(listNewCamps)) {
            System.out.println("Camps created successfully!");
        } else { //debug
            System.out.println("Error creating camps. Please check your input and try again.");
        }

    }

    protected void updateCampDetails() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
        ArrayList<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
        viewCreatedCamps();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);
        if (camp != null) {
			view.editCampView();
			boolean success = InputSelectionUtility.updateCampInput(camp,staff,dateFormat);
			System.out.println(success ? "Camp  updated successfully!" : "Camp update fail!");
		} else {
			System.out.println("No camps updated.");;
		}

    }

    protected void deleteCamp() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
        List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
		view.viewCamps(staffCreatedCamps, " - Choose Camp to Delete - ");
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);
        String campName = camp.getCampInformation().getCampName();
        
        boolean success = campStaffService.deleteCamp(camp);
        System.out.println(success? "Deleted "+campName+" successfully" :"Error deleting " +campName);
    }
    
    /**
     * Shows the list of all camps
     */
    protected void viewAllCamps() {
        while (true) {
            view.viewCamps(campStaffService.getAllCamps(), " - List of Camps - ");
            Camp selectedCamp = InputSelectionUtility.campSelector(campStaffService.getAllCamps());
            if (selectedCamp != null) {
                view.viewCampInformation(selectedCamp);
                scanner.nextLine();
            } else {
                return;
            }
        }
    }

    /**
     * Shows the list of camps created
     */
    protected void viewCreatedCamps() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
        view.viewCamps(campStaffService.getStaffCreatedCamps(staff),
                " - Camps Created by " + AuthStore.getCurrentUser().getName() + " - ");
        System.out.print("(Press Enter to return) ");
        scanner.nextLine();
    }

    /**
     * Shows the list of Students
     */
    protected void viewStudentList() {
        while (true) {
            view.viewCamps(campStaffService.getAllCamps(), " - Choose Camp to view Student list - ");
            Camp selectedCamp = InputSelectionUtility.campSelector(campStaffService.getAllCamps());
            if (selectedCamp != null) {
                List<String> students = campStaffService.getCampAttendeeList(selectedCamp).stream()
                        .map(Student::getName)
                        .collect(Collectors.toList());
                List<String> committeeMembers = campStaffService.getCampCommitteeList(selectedCamp).stream()
                        .map(Student::getName)
                        .collect(Collectors.toList());

                view.viewStudentList(students, committeeMembers, selectedCamp);
            } else {
                return;
            }
        }
    }

    /**
     * Shows the list of camps using user specified filter
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
    	for (Camp c : campStaffService.getAllCamps()) {
        	if (filterBy == null) {
            	// Default sorting by name
            	filteredCamps.add(c);
        	} else if (filterBy.equals("date")) {
            	if (filterDate != null) {
                	Date campDate = c.getCampInformation().getCampStartDate();
                	if (campDate.equals(filterDate)) {
                		System.out.println("Added " + c.getCampInformation().getCampName());
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
    		System.out.println("Not empty");
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
    	System.out.println("(Press Enter to return)");
    	scanner.nextLine();
	}

	protected void viewEnquiriesForCamp() {
		// Get list of Staff created camps
        Staff staff = (Staff) AuthStore.getCurrentUser();
		List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);
		Map<Integer, Enquiry> campEnquiries = enquiryStaffService.getAllEnquiriesForCamp(camp);
		view.displayEnquiries(campEnquiries);
	}

	protected void respondToEnquiry() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
		// Get list of Staff created camps
		List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);

		// Get enquiries for the selected camp
		Map<Integer, Enquiry> campEnquiries = enquiryStaffService.getAllEnquiriesForCamp(camp);
		// Check if there are draft enquiries to edit
		if (campEnquiries.isEmpty()) {
			System.out.println("You have no student enquiries to reply to.");
			return;
		}

		// Get User input
		Enquiry selectedEnquiry = InputSelectionUtility.enquirySelector(campEnquiries);
		String response = InputSelectionUtility.getStringInput("Enter response: ");

		// Respond using EnquiryStudentService
		boolean success = enquiryStaffService.respondToEnquiry(selectedEnquiry.getEnquiryID(), staff.getStaffID(), MessageStatus.ACCEPTED, response);
        System.out.println(success? "Suggestion responded successfully" :"Error responding to suggestion ");
    }

    protected void viewSuggestionForCamp() {
		// Get list of Staff created camps
        Staff staff = (Staff) AuthStore.getCurrentUser();
		List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
        // Display all the camps with suggestion @ToImplement 
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);
		Map<Integer, Suggestion> campSuggestion = suggestionStaffService.getAllSuggestionsForCamp(camp);
		view.displaySuggestions(campSuggestion);
	}

    public void respondToSuggestion() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
		// Get list of Staff created camps
		List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
		Camp camp = InputSelectionUtility.campSelector(staffCreatedCamps);

		// Get Suggestion for the selected camp
		Map<Integer, Suggestion> campSuggestions = suggestionStaffService.getAllSuggestionsForCamp(camp);
		// Check if there are draft suggestions to respond
		if (campSuggestions.isEmpty()) {
			System.out.println("You have no suggestions to review.");
			return;
		}

		// Get User input
		Suggestion selectedSuggestion = InputSelectionUtility.suggestionSelector(campSuggestions);
		int reviewOption = InputSelectionUtility.getIntInput("Do you want to accept this suggestion? (1: Yes, 2: No): ");
		boolean suggestionStatus = (reviewOption == 1);
		Map<String, Student> students = DataStore.getStudentData();
		Student sender = students.get(selectedSuggestion.getSenderID());
		if (suggestionStatus){sender.incrementStudentPoints();}

		// Respond using EnquiryStudentService
        boolean success = suggestionStaffService.reviewSuggestion(selectedSuggestion.getSuggestionID(), suggestionStatus);
		System.out.println(success? "Suggestion responded successfully" :"Error responding to suggestion ");
	}

    
}
