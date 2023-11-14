package controllers;

import java.util.Scanner;
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
import views.StudentView;
import interfaces.ICampStudentService;
import models.Student;
import models.Suggestion;
import models.Camp;
import models.Enquiry;
import models.Staff;
import services.CampStudentService;
import services.EnquiryResponderService;
import services.SuggestionSenderService;
import stores.AuthStore;
import stores.DataStore;
import views.StudentView;
import utility.InputSelectionUtility;


public class CampCommitteeController extends StudentController {

    private final CampStudentService campStudentService = new CampStudentService();
    private final EnquiryResponderService enquiryCampComitteeService = new EnquiryResponderService();
    private final SuggestionSenderService suggestionCampComitteeService = new SuggestionSenderService();
	private final StudentView view = new StudentView();
	private final Scanner scanner = new Scanner(System.in);

    public void start() {
        Student student = (Student) AuthStore.getCurrentUser();
        while (true) {
            view.displayCampComitteeMenuView();

            // Checks for invalid inputs
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input!! ");
                scanner.next();
                System.out.print("Select an option: ");
            }
            int choice = scanner.nextInt();

            // Sanity Check
            if (student.getUserRole() == UserRole.COMMITTEE) {
            	// Student selection menu
	            switch (choice) {
	                case 1: 
	                    viewAllCamps();
	                    break;
	                case 2: 
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
	                	viewEnquiriesForCamp();
	                	break;
	                case 8: 
	                	respondToEnquiry();
	                	break;
	                case 9: 
	                	submitSuggestion();
	                	break;
	                case 10: 
	                	editSuggestion();
	                	break;
                    case 11:
                        deleteSuggestion();
                        break;
                    case 12:
                        viewSuggestions();
                        break;
                    case 13:
                        // report generator 
                        break;
	                case 14: 
	                	// Change password
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

    protected void viewEnquiriesForCamp() {
		// Get list of Staff created camps
        Student student = (Student) AuthStore.getCurrentUser();
        Camp camp = campStudentService.getCampCommitteeCamp(student);
        if (camp == null) {
            System.out.println("You are not registered as a camp comittee for any camp.");
            return;
        } 
		Map<Integer, Enquiry> campEnquiries = enquiryCampComitteeService.getAllEnquiriesForCamp(camp);
		view.displayEnquiries(campEnquiries);
	}

	protected void respondToEnquiry() {
        Student student = (Student) AuthStore.getCurrentUser();
       
        Camp camp = campStudentService.getCampCommitteeCamp(student);
        if (camp == null) {
            System.out.println("You are not registered for any camp.");
            return;
        } 

		// Get enquiries for the selected camp
		Map<Integer, Enquiry> campEnquiries = enquiryCampComitteeService.getAllEnquiriesForCamp(camp);
		// Check if there are draft enquiries to edit
		if (campEnquiries.isEmpty()) {
			System.out.println("You have no student enquiries to reply to.");
			return;
		}

		// Get User input
		Enquiry selectedEnquiry = InputSelectionUtility.enquirySelector(campEnquiries);
		String response = InputSelectionUtility.getStringInput("Enter response: ");

		// Respond using EnquiryStudentService
		boolean success = enquiryCampComitteeService.respondToEnquiry(selectedEnquiry.getEnquiryID(), student.getStudentID(), MessageStatus.ACCEPTED, response);
        System.out.println(success? "Suggestion responded successfully" :"Error responding to suggestion ");
    }

    protected void submitSuggestion() {
		Student student = (Student) AuthStore.getCurrentUser();

		if(!campStudentService.isUserCampCommittee(student)){
			System.out.println("Only committee members can submit suggestions!");
			return;
		}
		// Get User's committee camp
		Camp committeecamp = campStudentService.getCampCommitteeCamp(student);

		// Get User input
		String campName = committeecamp.getCampInformation().getCampName();
		String suggestionMessage = InputSelectionUtility.getStringInput("Enter suggestion: ");
		// Prompt the user whether they'd like the suggestion to be saved as draft (1: Yes, 2: No)
		int draftChoice = InputSelectionUtility.getIntInput("Do you want to save the suggestion as a draft? (1: Yes, 2: No): ");
		boolean isDraft = (draftChoice == 1);
		if (!isDraft){student.incrementStudentPoints();}

		// Create a new suggestion using SuggestionStudentService
		int suggestionID = suggestionCampComitteeService.submitSuggestion(student.getStudentID(), campName, suggestionMessage, isDraft);

		System.out.println("Suggestion submitted with ID: " + suggestionID);
	}

    protected void viewSuggestions() {
		Student student = (Student) AuthStore.getCurrentUser();

		if(!campStudentService.isUserCampCommittee(student)){
			System.out.println("Only committee members can view suggestions!");
			return;
		}
		// Get draft, pending and responded suggestions
		Map<Integer, Suggestion> draftSuggestions = suggestionCampComitteeService.viewDraftSuggestion(student.getStudentID());
		Map<Integer, Suggestion> submittedSuggestions = suggestionCampComitteeService.viewSubmittedSuggestion(student.getStudentID());
		Map<Integer, Suggestion> acceptedSuggestions = suggestionCampComitteeService.viewAcceptedSuggestion(student.getStudentID());
		Map<Integer, Suggestion> rejectedSuggestions = suggestionCampComitteeService.viewRejectedSuggestion(student.getStudentID());

		// Display Suggestions
		view.displaySuggestions(draftSuggestions, submittedSuggestions, acceptedSuggestions, rejectedSuggestions);
	}

	protected boolean editSuggestion() {
		Student student = (Student) AuthStore.getCurrentUser();

		if(!campStudentService.isUserCampCommittee(student)){
			System.out.println("Only committee members can edit suggestions!");
			return false;
		}
		// Get Data
		Map<Integer, Suggestion> draftSuggestions = suggestionCampComitteeService.viewDraftSuggestion(student.getStudentID());

		// Check if there are draft suggestions to edit
		if (draftSuggestions.isEmpty()) {
			System.out.println("You have no draft suggestions to edit.");
			return false;
		}
		// Get User input
		Suggestion selectedSuggestion = InputSelectionUtility.suggestionSelector(draftSuggestions);

		if (selectedSuggestion != null) {
			String newDetails = InputSelectionUtility.getStringInput("Enter your new suggestion: ");

			// Prompt the user whether they'd like the enquiry to be saved as draft (1: Yes, 2: No)
			int draftChoice = InputSelectionUtility.getIntInput("Do you want to save the suggestion as a draft? (1: Yes, 2: No): ");
			boolean isDraft = (draftChoice == 1);
			if (!isDraft){student.incrementStudentPoints();}

			// Edit the selected draft suggestion using SuggestionStudentService
			return suggestionCampComitteeService.editSuggestion(selectedSuggestion.getSuggestionID(), student.getStudentID(), newDetails, isDraft);
		}
		return false;
	}

	protected void deleteSuggestion() {
		Student student = (Student) AuthStore.getCurrentUser();

		if(!campStudentService.isUserCampCommittee(student)){
			System.out.println("Only committee members can delete suggestions!");
			return;
		}
		// Get Data
		Map<Integer, Suggestion> draftSuggestions = suggestionCampComitteeService.viewDraftSuggestion(student.getStudentID());

		// Check if there are draft suggestions to delete
		if (draftSuggestions.isEmpty()) {
			System.out.println("You have no draft suggestions to delete.");
			return;
		}
		// Get User input
		Suggestion selectedSuggestion = InputSelectionUtility.suggestionSelector(draftSuggestions);

		if (selectedSuggestion != null) {
			// Confirm deletion
			int confirmChoice = InputSelectionUtility.getIntInput("Are you sure you want to delete this enquiry? (1: Yes, 2: No): ");
			if (confirmChoice == 1) {
				// Delete the selected draft enquiry using EnquiryStudentService
				boolean deleted = suggestionCampComitteeService.deleteDraftSuggestion(selectedSuggestion.getSuggestionID(), student.getStudentID());
				if (deleted) {
					System.out.println("Suggestion deleted successfully.");
				} else {
					System.out.println("Failed to delete the suggestion. Please try again.");
				}
			} else {
				System.out.println("Suggestion deletion canceled.");
			}
		}
	}






}
