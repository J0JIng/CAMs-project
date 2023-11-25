package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import enums.MessageStatus;
import interfaces.ICampStaffService;
import interfaces.IEnquiryResponderService;
import interfaces.ISuggestionResponderService;
import interfaces.IReportStaffService;
import models.Camp;
import models.CampInformation;
import models.Enquiry;
import models.Student;
import models.Suggestion;
import models.Staff;
import services.CampStaffService;
import services.EnquiryResponderService;
import services.SuggestionResponderService;
import services.ReportStaffService;
import stores.AuthStore;
import stores.DataStore;
import utility.FilePathsUtility;
import utility.InputSelectionUtility;
import views.MessageView;
import views.StaffView;

/**
 * The {@link StaffController} class is the main controller for staff members.
 * It extends from the {@link UserController} class to utilize {@link UserController} password related services
 * {@link StaffController} class provides the allocation of {@link Staff}'s desired operations to the respective methods.
 * Such methods include creating camps, deleting camps, viewing camps, handling enquiries and suggestions
 * It utilizes many services such as {@link ICampStaffService} interface to run camp related services to perform
 * its desired action.
 */
public class StaffController extends UserController {
	
	/** The {@code Scanner} object for reading user input. */
	private final Scanner scanner = new Scanner(System.in);
    
    /**
     * Service for handling operations related to camp staff.
     */
    private static final ICampStaffService campStaffService = new CampStaffService();
    
    /**
     * Service for responding to enquiries as a staff member.
     */
    private static final IEnquiryResponderService enquiryStaffService = new EnquiryResponderService();
    
    /**
     * Service for responding to suggestions as a staff member.
     */
    private static final ISuggestionResponderService suggestionStaffService = new SuggestionResponderService();
    
    /**
     * Service for generating reports as a staff member.
     */
    private static final IReportStaffService reportStaffService = new ReportStaffService();
    
    /**
     * View class responsible for displaying staff-related operations.
     */
    private static final StaffView view = new StaffView();

    /**
     * Constructs a new {@code StaffController}.
     */
    public StaffController() {
    }

    /**
     * Initiates the staff menu, allowing staff members to perform various operations.
     * The method enters a loop where it repeatedly displays the staff menu, takes user input,
     * and executes the corresponding operation based on the user's choice. 
     * The loop continues until the user chooses to log out.
     */
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
                    generateReport();
                	break;
                case 14:
                    generatePerformanceReport();
                	break;
                case 15:
                    generateEnquiryReport();
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

    /**
     * Toggles the visibility of a camp created by the staff member.
     * The staff member is prompted to choose a camp from the list of camps they created.
     * The chosen camp's visibility is then toggled.
     * If the staff member has not created any camps, a blank list is displayed,
     * asking for them to press 'Return' and end the method.
     */
    protected void toggleCampVisibility() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
        List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
        view.viewCamps(staffCreatedCamps, " - Choose Camp to Toggle Visibility - ");
        if(staffCreatedCamps.isEmpty()){
        	MessageView.endMessage(scanner, null, false);
            return;
        }
        Camp camp = InputSelectionUtility.getSelectedCamp(staffCreatedCamps);
        if (camp != null) {
	        view.viewToggleOption(camp);
	        campStaffService.toggleCampVisibility(camp);
        }
    }

    /**
     * Creates a specified number of camps based on user input.
     * The staff member is prompted to enter the number of camps to create.
     * For each camp, the staff member provides information, and new camps are created.
     * An acknowledgement message is shown to indicate success or error.
     */
    protected void createCamp() {
        // Get number of projects to create from user
        Staff staff = (Staff) AuthStore.getCurrentUser();
        int campCount =  InputSelectionUtility.getIntInput("Enter the number of Camps to create: ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<Camp> listNewCamps = new ArrayList<Camp>();
        
        if (campCount != 0) {
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
        } else {
        	System.out.println("No Camps created");
        }

    }

    /**
     * Updates details of a camp created by the staff member.
     * The staff member is prompted to choose a camp from the list of camps they created.
     * The staff member then updates the chosen camp's details.
     * If the staff member has not created any camps, a blank list is displayed,
     * asking for them to press 'Return' and end the method.
     */
    protected void updateCampDetails() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
        List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
        viewEditableCamps();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        Camp camp = InputSelectionUtility.getSelectedCamp(staffCreatedCamps);
        if (camp != null) {
			view.editCampView();
			boolean success = InputSelectionUtility.updateCampInput(camp,staff,dateFormat);
			MessageView.endMessage(scanner, success ? "Camp updated successfully!" : "Camp update fail!", false);
		} else {
			System.out.println("No camps updated.");
		}

    }

    /**
     * Deletes a camp created by the staff member.
     * The staff member is prompted to choose a camp from the list of camps they created.
     * The chosen camp is deleted, and a success or error message is displayed.
     * If the staff member has not created any camps, a blank list is displayed,
     * asking for them to press 'Return' and end the method.
     */
    protected void deleteCamp() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
        List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
		view.viewCamps(staffCreatedCamps, " - Choose Camp to Delete - ");
		Camp camp = InputSelectionUtility.getSelectedCamp(staffCreatedCamps);
		if (camp != null) {
	        String campName = camp.getCampInformation().getCampName();
	        boolean success = campStaffService.deleteCamp(camp);
	        System.out.println(success? "Deleted "+campName+" successfully" :"Error deleting " +campName);
		}
    }
    
    /**
     * Displays a list of all camps regardless of camp's visibility
     * Staff member can choose to select a camp to display its information.
     */
    protected void viewAllCamps() {
        view.viewCamps(campStaffService.getAllCamps(), " - List of Camps - ");
        Camp selectedCamp = InputSelectionUtility.getSelectedCamp(campStaffService.getAllCamps());
        if (selectedCamp != null) {
            view.viewCampInformation(selectedCamp);
            MessageView.endMessage(scanner, null, false);
        } else {
            return;
        }
    }

    /**
     * Displays a list of camps created by the current staff member.
     * If the staff member has not created any camps, a blank list is displayed,
     * asking for them to press 'Return' and end the method.
     */
    protected void viewCreatedCamps() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
        view.viewCamps(campStaffService.getStaffCreatedCamps(staff),
                " - Camps Created by " + AuthStore.getCurrentUser().getName() + " - ");
        MessageView.endMessage(scanner, null, false);
    }
    
    /**
     * Displays a list of camps editable by the current staff member.
     * The staff member is prompted to choose a camp from the list.
     */
    protected void viewEditableCamps() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
        view.viewCamps(campStaffService.getStaffCreatedCamps(staff),
                " - Choose camp to edit - ");
    }

    /**
     * Displays a list of students registered for a selected camp.
     * The staff member chooses a camp, and the list of students and committee members is displayed
     * using the viewStudentList service of StaffView.
     */
    protected void viewStudentList() {
        view.viewCamps(campStaffService.getAllCamps(), " - Choose Camp to view Student list - ");
        Camp selectedCamp = InputSelectionUtility.getSelectedCamp(campStaffService.getAllCamps());
        if (selectedCamp != null) {
            List<String> students = campStaffService.getCampAttendeeList(selectedCamp).stream()
                    .map(Student::getName)
                    .collect(Collectors.toList());
            List<String> committeeMembers = campStaffService.getCampCommitteeList(selectedCamp).stream()
                    .map(Student::getName)
                    .collect(Collectors.toList());
            view.viewStudentList(students, committeeMembers, selectedCamp);
            MessageView.endMessage(scanner, null, false);
        }
    }

    /**
     * Displays a list of all camps with optional user-specified filters.
     * The staff member can filter camps by start date, location, or view all camps alphabetically.
     * The filtered list is displayed with additional information based on the chosen filter.
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
	 * Displays a list of enquiries for a selected camp that is created by the current staff member.
	 * The staff member chooses a camp, and the list of pending enquiries is displayed.
	 */
	protected void viewEnquiriesForCamp() {
		// Get list of Staff created camps
        Staff staff = (Staff) AuthStore.getCurrentUser();
		List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
		view.viewCamps(campStaffService.getStaffCreatedCamps(staff),
	                " - Choose Camp to View Enquiry - ");
		Camp camp = InputSelectionUtility.getSelectedCamp(staffCreatedCamps);
		if (camp == null) {
			// Invalid input, exit the process
			return;
		}
		Map<Integer, Enquiry> campEnquiries = enquiryStaffService.getAllPendingEnquiriesForCamp(camp);
		view.displayEnquiries(campEnquiries);
		MessageView.endMessage(scanner, null, false);
	}

	/**
	 * Allows the staff member to respond to an enquiry for a selected camp.
	 * The staff member chooses a camp, selects an enquiry, and enters a response.
	 * The response is processed using the EnquiryResponderService.
     * An acknowledgement message is shown to indicate success or error.
	 */
	protected void respondToEnquiry() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
		// Get list of Staff created camps
		List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
		view.viewCamps(campStaffService.getStaffCreatedCamps(staff),
                " - Camps by " + AuthStore.getCurrentUser().getName() + " - ");
		Camp camp = InputSelectionUtility.getSelectedCamp(staffCreatedCamps);
		if (camp == null) {
			// Invalid input, exit the process
			return;
		}

		// Get enquiries for the selected camp
		Map<Integer, Enquiry> campEnquiries = enquiryStaffService.getAllPendingEnquiriesForCamp(camp);
		// Check if there are draft enquiries to edit
		if (campEnquiries.isEmpty()) {
			MessageView.endMessage(scanner, "You have no student enquiries to reply to.", false);
			return;
		}

		// Get User input
		Enquiry selectedEnquiry = InputSelectionUtility.getSelectedEnquiry(campEnquiries);
		String response = InputSelectionUtility.getStringInput("Enter response: ");

		// Respond using EnquiryStudentService
		boolean success = enquiryStaffService.respondToEnquiry(selectedEnquiry.getEnquiryID(), staff.getStaffID(), MessageStatus.ACCEPTED, response);
		MessageView.endMessage(scanner, success ? "Enquiry responded successfully" : "Error responding to suggestion", true);
    }

	/**
	 * Displays a list of suggestions for a selected camp created by the current staff member.
	 * The staff member chooses a camp, and the list of suggestions is displayed.
	 * Otherwise, a message indicating no camp is shown and method ends.
	 */
    protected void viewSuggestionForCamp() {
		// Get list of Staff created camps
        Staff staff = (Staff) AuthStore.getCurrentUser();
		List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
        // Display all the camps with suggestion @ToImplement 
		view.viewCamps(staffCreatedCamps, " - Choose Camp to view Suggestions - ");
		Camp camp = InputSelectionUtility.getSelectedCamp(staffCreatedCamps);
		if (camp != null) {
			Map<Integer, Suggestion> campSuggestion = suggestionStaffService.getAllSuggestionsForCamp(camp);
			view.displaySuggestion(campSuggestion);
			MessageView.endMessage(scanner, null, false);
		} else {
			System.out.println("No camp selected");
		}
	}

    /**
     * Allows the staff member to respond to a suggestion for a selected camp.
     * The staff member chooses a camp, selects a suggestion, and decides to accept or reject it.
     * The suggestion is processed using the SuggestionResponderService.
     */
    protected void respondToSuggestion() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
		// Get list of Staff created camps
		List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);
		view.viewCamps(staffCreatedCamps, " - Choose Camp to Respond to Suggestions - ");
		Camp camp = InputSelectionUtility.getSelectedCamp(staffCreatedCamps);
		
		if (camp != null) {
			// Get Suggestion for the selected camp
			Map<Integer, Suggestion> campSuggestions = suggestionStaffService.getAllSuggestionsForCamp(camp);
			// Check if there are draft suggestions to respond
			if (campSuggestions.isEmpty()) {
				MessageView.endMessage(scanner, "You have no suggestions to review.", false);
				return;
			}
	
			view.displaySuggestion(campSuggestions);
			// Get User input
			Suggestion selectedSuggestion = InputSelectionUtility.getSelectedSuggestion(campSuggestions);
			if (selectedSuggestion != null) {
				int reviewOption = InputSelectionUtility.getIntInput("Do you want to accept this suggestion? (1: Yes, 2: No): ");
				boolean suggestionStatus = (reviewOption == 1);
				Map<String, Student> students = DataStore.getStudentData();
				Student sender = students.get(selectedSuggestion.getSenderID());
				if (suggestionStatus){sender.incrementStudentPoints();}
		
				// Respond using EnquiryStudentService
		        boolean success = suggestionStaffService.reviewSuggestion(selectedSuggestion.getSuggestionID(), suggestionStatus);
		        MessageView.endMessage(scanner, success ? "Suggestion responded successfully" : "Error responding to suggestion", true);
			}
		}
	}

    /**
     * Generates a report based on user-specified filters. The staff member can choose to generate
     * a report for all camps, camps created by the staff member, or a specific camp. The report can
     * be filtered by various criteria, and the resulting report is saved to a CSV file.
     */
    protected void generateReport() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
        List<Camp> allCreatedCamps = campStaffService.getAllCamps();
        List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);

        int option = 0;
        boolean success = false;
        List<String> filter = null;

        // View report menu
        view.viewReportMenu();
        do {
        	option = InputSelectionUtility.getIntInput("Enter the filter option (1/2/3, 4 to exit): ");
        	if (option == 4) {
        		MessageView.endMessage(scanner, "Exiting From Report Generation", false);
        		return;
        	}
        } while (option <= 0 || option > 3);
        switch (option) {
            case 1:
                // get the filters
                if (allCreatedCamps != null && !allCreatedCamps.isEmpty()) {
                    view.showFilterInput();
                    filter = InputSelectionUtility.getFilterInput();
                    if (filter != null) success = reportStaffService.generateReport(filter, allCreatedCamps,FilePathsUtility.csvFilePaths());
                }else{
                    success = false;
                    System.out.println("Error: allCreatedCamps is null or empty");
                }
                break;

            case 2:
                // get the filters
            	if (staffCreatedCamps != null && !staffCreatedCamps.isEmpty()) {
                    view.showFilterInput();
                    filter = InputSelectionUtility.getFilterInput();
                    if (filter != null) success = reportStaffService.generateReport(filter, staffCreatedCamps,FilePathsUtility.csvFilePaths());
                }else{
                    success = false;
                    System.out.println("Error: staffCreatedCamps is null or empty");
                }

            	break;

            case 3:
                // Show ALL camps to select from
        		view.viewCamps(allCreatedCamps, " - Choose Camp to Generate Report - ");
                List<Camp> camps = new ArrayList<>();
                Camp selectedCamp = InputSelectionUtility.getSelectedCamp(allCreatedCamps);
                camps.add(selectedCamp);

                if (selectedCamp == null) {
                    System.out.println("Invalid camp selection.");
                    success = false;
                } else {
                	view.showFilterInput();
                    filter = InputSelectionUtility.getFilterInput();
                    if (filter != null) success = reportStaffService.generateReport(filter,camps,FilePathsUtility.csvFilePaths());
                }
                break;
        	}
        if (filter != null) {
        	MessageView.endMessage(scanner, success ? "Report generated successfully" : "Error generating report", false);
        }
    }


    /**
     * Generates a performance report based on user-specified filters. A menu is shown for user to choose the type of report desired. 
     * The staff member can choose to generate a performance report for all camps, camps created by the staff member, or a specific camp.
     * The performance report can be filtered by various criteria, and the resulting report is saved to a CSV file.
     */
    protected void generatePerformanceReport() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
        List<Camp> allCreatedCamps = campStaffService.getAllCamps();
        List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);

        int option = 0;
        boolean success = false;
        List<String> filter = null;

        // View report menu
        view.viewPerformanceReportMenu();
        do {
        	option = InputSelectionUtility.getIntInput("Enter the filter option (1/2/3, 4 to exit): ");
        	if (option == 4) {
        		MessageView.endMessage(scanner, "Exiting From Report Generation", false);
        		return;
        	}
        } while (option <= 0 || option > 3);
        switch (option) {
            case 1:
                // get the filters
                if (allCreatedCamps != null && !allCreatedCamps.isEmpty()) {
                    view.showPerformanceFilterInput();
                    filter = InputSelectionUtility.getPerformanceFilterInput();
                    if (filter != null) success = reportStaffService.generatePerformanceReport(filter, allCreatedCamps,FilePathsUtility.csvFilePaths());
                }else{
                    success = false;
                    System.out.println("Error: allCreatedCamps is null or empty");
                }
            	
                break;

            case 2:
                // get the filters
                if (staffCreatedCamps != null && !staffCreatedCamps.isEmpty()) {
                    view.showPerformanceFilterInput();
                    filter = InputSelectionUtility.getPerformanceFilterInput();
                    if (filter != null) success = reportStaffService.generatePerformanceReport(filter, staffCreatedCamps,FilePathsUtility.csvFilePaths());
                }else{
                    success = false;
                    System.out.println("Error: staffCreatedCamps is null or empty");
                }

                break;

            case 3:
                // Show ALL camps to select from
                viewAllCamps();
                List<Camp> camps = new ArrayList<>();
                Camp selectedCamp = InputSelectionUtility.getSelectedCamp(allCreatedCamps);
                camps.add(selectedCamp);

                if (selectedCamp == null) {
                    System.out.println("Invalid camp selection.");
                    success = false;
                } else {
                	view.showFilterInput();
                    filter = InputSelectionUtility.getPerformanceFilterInput();
                    if (filter != null) success = reportStaffService.generatePerformanceReport(filter,camps,FilePathsUtility.csvFilePaths());
                }
                break;
            }
        if (filter != null) {
        	MessageView.endMessage(scanner, success ? "Report generated successfully" : "Error generating report", false);
        }
    }

    /**
     * Generates an enquiry report based on user-specified filters.
     * A menu is shown for user to choose the type of report desired. The staff member can choose to generate
     * an enquiry report for all camps, camps created by the staff member, or a specific camp. The report can
     * be filtered by various criteria, and the resulting report is saved to a CSV file.
     */
    protected void generateEnquiryReport() {
        Staff staff = (Staff) AuthStore.getCurrentUser();
        List<Camp> allCreatedCamps = campStaffService.getAllCamps();
        List<Camp> staffCreatedCamps = campStaffService.getStaffCreatedCamps(staff);

        int option = 0;
        boolean success = false;
        List<String> filter = null;

        // View report menu
        view.viewEnquiryReportMenu();
        do {
        	option = InputSelectionUtility.getIntInput("Enter the filter option (1/2/3, 4 to exit): ");
        	if (option == 4) {
        		MessageView.endMessage(scanner, "Exiting From Report Generation", false);
        		return;
        	}
        } while (option <= 0 || option > 3);            
        switch (option) {
            case 1:
                // get the filters
                if (allCreatedCamps != null && !allCreatedCamps.isEmpty()) {
                    view.showEnquiryFilterInput();
                    filter = InputSelectionUtility.getEnquiryFilterInput();
                    if (filter != null) success = reportStaffService.generateEnquiryReport(filter, allCreatedCamps,FilePathsUtility.csvFilePaths());
                }else{
                    success = false;
                    System.out.println("Error: allCreatedCamps is null or empty");
                }
            	
                break;

            case 2:
                // get the filters
                if (staffCreatedCamps != null && !staffCreatedCamps.isEmpty()) {
                    view.showEnquiryFilterInput();
                    filter = InputSelectionUtility.getEnquiryFilterInput();
                    if (filter != null) success = reportStaffService.generateEnquiryReport(filter, staffCreatedCamps,FilePathsUtility.csvFilePaths());
                }else{
                    success = false;
                    System.out.println("Error: staffCreatedCamps is null or empty");
                }

                break;

            case 3:
                // Show ALL camps to select from
                view.viewCamps(campStaffService.getAllCamps(), " - List of Camps - ");
                List<Camp> camps = new ArrayList<>();
                Camp selectedCamp = InputSelectionUtility.getSelectedCamp(allCreatedCamps);
                camps.add(selectedCamp);

                if (selectedCamp == null) {
                    System.out.println("Invalid camp selection.");
                    success = false;
                } else {
                	view.showEnquiryFilterInput();
                    filter = InputSelectionUtility.getEnquiryFilterInput();
                    if (filter != null) success = reportStaffService.generateEnquiryReport(filter,camps,FilePathsUtility.csvFilePaths());
                }
                break;
        	}
        if (filter != null) {
        	MessageView.endMessage(scanner, success ? "Report generated successfully" : "Error generating report", false);
        }
    }
    
    
}
