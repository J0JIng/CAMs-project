package views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

import enums.UserRole;
import interfaces.IMenuView;
import models.Camp;
import models.CampInformation;
import models.Enquiry;
import models.Student;
import models.Suggestion;
import stores.AuthStore;
import stores.DataStore;
import utility.ViewUtility;

public class StudentView implements IMenuView{

	@Override
	public void displayMenuView() {
		ViewUtility.clearScreen();
		
		String welcomeMessage = "Welcome " + AuthStore.getCurrentUser().getName();
		int totalLength = welcomeMessage.length();
		int spaces = (58 - totalLength) / 2;

		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");   
		System.out.println("║             ██████╗ █████╗ ███╗   ███╗███████╗           ║");
		System.out.println("║            ██╔════╝██╔══██╗████╗ ████║██╔════╝           ║");
		System.out.println("║            ██║     ███████║██╔████╔██║███████╗           ║");
		System.out.println("║            ██║     ██╔══██║██║╚██╔╝██║╚════██║           ║");
		System.out.println("║            ╚██████╗██║  ██║██║ ╚═╝ ██║███████║           ║");
		System.out.println("║             ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝           ║");
        System.out.println("║                                                          ║");   
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║                     - Student Menu -                     ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║" + " ".repeat(spaces) + welcomeMessage + " ".repeat(58 - totalLength - spaces) + "║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║[1]  View All Camps                                       ║");
        System.out.println("║[2]  View Camps with Filters                              ║");
        System.out.println("║[3]  Register for Camp                                    ║");
        System.out.println("║[4]  Withdraw from Camp                                   ║");
        System.out.println("║[5]  View Remaining Slots of Camp                         ║");
        System.out.println("║[6]  View Registered Camps                                ║");
        System.out.println("║[7]  Submit Enquiry                                       ║");
        System.out.println("║[8]  View Enquiry                                         ║");
        System.out.println("║[9]  Edit Enquiry                                         ║");
        System.out.println("║[10] Delete Enquiry                                       ║");
        System.out.println("║[11] Register as Committee                                ║");
        System.out.println("║[12] Change Password                                      ║");
        System.out.println("║[13] Logout                                               ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("Select an option: ");
	}

	public void displayCampComitteeMenuView() {
		ViewUtility.clearScreen();
		
		String welcomeMessage = "Welcome " + AuthStore.getCurrentUser().getName();
		int totalLength = welcomeMessage.length();
		int spaces = (58 - totalLength) / 2;

		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");   
		System.out.println("║             ██████╗ █████╗ ███╗   ███╗███████╗           ║");
		System.out.println("║            ██╔════╝██╔══██╗████╗ ████║██╔════╝           ║");
		System.out.println("║            ██║     ███████║██╔████╔██║███████╗           ║");
		System.out.println("║            ██║     ██╔══██║██║╚██╔╝██║╚════██║           ║");
		System.out.println("║            ╚██████╗██║  ██║██║ ╚═╝ ██║███████║           ║");
		System.out.println("║             ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝           ║");
        System.out.println("║                                                          ║");   
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║                  - Camp Committee Menu -                 ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║" + " ".repeat(spaces) + welcomeMessage + " ".repeat(58 - totalLength - spaces) + "║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║[1]  View All Camps                                       ║");
        System.out.println("║[2]  View Camps with Filters                              ║");
        System.out.println("║[3]  Register for Camp                                    ║");
        System.out.println("║[4]  Withdraw from Camp                                   ║");
        System.out.println("║[5]  View Remaining Slots of Camp                         ║");
        System.out.println("║[6]  View Registered Camps                                ║");
        System.out.println("║[7]  View Enquiries For Camp                              ║");
        System.out.println("║[8]  Respond to Enquiry                                   ║");
        System.out.println("║[9]  Submit Suggestion                                    ║");
        System.out.println("║[10] Edit Suggestion                                      ║");
        System.out.println("║[11] Delete Suggestion                                    ║");
        System.out.println("║[12] View Suggestion                                      ║");
        System.out.println("║[13] Generate Report                                      ║");
		System.out.println("║[14] Change Password                                      ║");
		System.out.println("║[15] Logout                                               ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("Select an option: ");
	}
	
	/**
	 * Displays all camps without any filters
	 * @param list 
	 */
	public void viewCamps(List<Camp> campData, String title) {
    	// Print the filtered and sorted camps
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");   
		System.out.println("║         ██████╗ █████╗ ███╗   ███╗██████╗ ███████╗       ║");
		System.out.println("║        ██╔════╝██╔══██╗████╗ ████║██╔══██╗██╔════╝       ║");
		System.out.println("║        ██║     ███████║██╔████╔██║██████╔╝███████╗       ║");
		System.out.println("║        ██║     ██╔══██║██║╚██╔╝██║██╔═══╝ ╚════██║       ║");
		System.out.println("║        ╚██████╗██║  ██║██║ ╚═╝ ██║██║     ███████║       ║");
		System.out.println("║         ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝     ╚══════╝       ║");
        System.out.println("║                                                          ║");                                  
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        ViewUtility.displayInMenuCentered(title);
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        int i = 1;
    	for (Camp c : campData) {
    		ViewUtility.displayInMenuNumbered(c.getCampInformation().getCampName(), i);
        	i++;
    	}
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
	
	/**
	 * Displays the camp information of the camp
	 * @param Camp c
	 */
	public void viewCampInformation(Camp c) {
    	// Print the filtered and sorted camps
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		ViewUtility.displayInMenuCentered(" - " + c.getCampInformation().getCampName() + " - ");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
        int i = 1;
    	//for (Camp c : campData) {
    		CampInformation campInfo = c.getCampInformation();
    		ViewUtility.displayInMenuBullet("Name: " + campInfo.getCampName());
    		ViewUtility.displayInMenuBullet("Start Date: " + campInfo.getCampStartDate());
    		ViewUtility.displayInMenuBullet("End Date: " + campInfo.getCampEndDate());
    		ViewUtility.displayInMenuBullet("Registration Closing Date: " + campInfo.getCampRegistrationClosingDate());
    		ViewUtility.displayInMenuBullet("User Group: " + campInfo.getFacultyGroup());
    		ViewUtility.displayInMenuBullet("Location: " + campInfo.getCampLocation());
    		ViewUtility.displayInMenuBullet("Total Slots: " + campInfo.getCampTotalSlots());
    		ViewUtility.displayInMenuBullet("Committee Slots: " + campInfo.getCampCommitteeSlots());
    		ViewUtility.displayInMenuBullet("Description: " + campInfo.getCampDescription());
    		ViewUtility.displayInMenuBullet("Staff In Charge: " + campInfo.getCampStaffInCharge());
        	i++;
    	//}
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("(Press Enter to return) ");
	}
	
	/**
	 * Displays all camps without any filters
	 * @param list 
	 */
	public void viewCampsSlots(List<Camp> campData) {
		Map<String , List<String>> registeredStudents = DataStore.getCampToRegisteredCampCommitteeData(); 
		Map<String , List<String>> registeredCampCommittee = DataStore.getCampToRegisteredStudentData();
    	// Print the filtered and sorted camps
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");   
		System.out.println("║         ██████╗ █████╗ ███╗   ███╗██████╗ ███████╗       ║");
		System.out.println("║        ██╔════╝██╔══██╗████╗ ████║██╔══██╗██╔════╝       ║");
		System.out.println("║        ██║     ███████║██╔████╔██║██████╔╝███████╗       ║");
		System.out.println("║        ██║     ██╔══██║██║╚██╔╝██║██╔═══╝ ╚════██║       ║");
		System.out.println("║        ╚██████╗██║  ██║██║ ╚═╝ ██║██║     ███████║       ║");
		System.out.println("║         ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝     ╚══════╝       ║");
        System.out.println("║                                                          ║");                                  
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        ViewUtility.displayInMenuCentered(" - Slots taken for each camp - ");
        System.out.println("╠════════════════════════════════════════════════╦═════════╣");
        System.out.println("║                   Camp Name                    ║  Slots  ║");
        int i = 1;
    	for (Camp c : campData) {
//			int filledSlots = registeredStudents.get(c.getCampInformation().getCampName()).size()+registeredCampCommittee.get(c.getCampInformation().getCampName()).size();
//			int totalSlots = c.getCampInformation().getCampTotalSlots();
			String campName = c.getCampInformation().getCampName();

	        List<String> students = registeredStudents.get(campName);
	        List<String> committeeMembers = registeredCampCommittee.get(campName);

	        int filledSlots = 0;
	        if (students != null) {
	            filledSlots += students.size();
	        }
	        if (committeeMembers != null) {
	            filledSlots += committeeMembers.size();
	        }

	        int totalSlots = c.getCampInformation().getCampTotalSlots();
			ViewUtility.displayInMenuNumberedTwoColumns(c.getCampInformation().getCampName(), i ,filledSlots, totalSlots);
			i++;
		}
        System.out.println("╚════════════════════════════════════════════════╩═════════╝");
        System.out.print("(Press Enter to return) ");
	}

	public void viewFliterOption() {
    	// Print the filtered and sorted camps
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		ViewUtility.displayInMenuCentered(" - Filter Options - ");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
    	System.out.println("║[1] Filter by Date							    		   ║");
    	System.out.println("║[2] Filter by Location						    		   ║");	
    	System.out.println("║[3] Sort by Name (Alphabetical Order)		    		   ║");
		System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	public void displayEnquiries(Map<Integer, Enquiry> enquiries) {
		System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                                            ║"); 
	    System.out.println("║             ███████╗███╗   ██╗ ██████╗ ██╗   ██╗██╗██████╗ ██╗███████╗███████╗             ║");
	    System.out.println("║             ██╔════╝████╗  ██║██╔═══██╗██║   ██║██║██╔══██╗██║██╔════╝██╔════╝             ║");
	    System.out.println("║             █████╗  ██╔██╗ ██║██║   ██║██║   ██║██║██████╔╝██║█████╗  ███████╗             ║");
	    System.out.println("║             ██╔══╝  ██║╚██╗██║██║▄▄ ██║██║   ██║██║██╔══██╗██║██╔══╝  ╚════██║             ║");
	    System.out.println("║             ███████╗██║ ╚████║╚██████╔╝╚██████╔╝██║██║  ██║██║███████╗███████║             ║");
	    System.out.println("║             ╚══════╝╚═╝  ╚═══╝ ╚══▀▀═╝  ╚═════╝ ╚═╝╚═╝  ╚═╝╚═╝╚══════╝╚══════╝             ║");
        System.out.println("║                                                                                            ║");
		if (enquiries.isEmpty()) {
			System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
			System.out.println("║                                   No Enquiries to Display.                                 ║");
		} else {
			System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
			for (Enquiry enquiry : enquiries.values()) {
				ViewUtility.displayInMenuBulletLarge("Enquiry ID: " + enquiry.getEnquiryID());
				ViewUtility.displayInMenuBulletLarge("Camp Name: " + enquiry.getCampName());
				ViewUtility.displayInMenuBulletLarge("Message: " + enquiry.getEnquiryMessage());
				ViewUtility.displayInMenuBulletLarge("Status: " + enquiry.getEnquiryStatus());
				ViewUtility.displayInMenuBulletLarge("Response: " + enquiry.getEnquiryResponse());
				ViewUtility.displayInMenuBulletLarge("Responder ID: " + enquiry.getResponderID());
				System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
			}
		}
		System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");
	}

	public void displaySuggestions(Map<Integer, Suggestion> draftSuggestions, Map<Integer, Suggestion> submittedSuggestions,
									Map<Integer, Suggestion> acceptedSuggestions, Map<Integer, Suggestion> rejectedSuggestions) {
		
	    System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                                            ║"); 
	    System.out.println("║ ███████╗██╗   ██╗ ██████╗  ██████╗ ███████╗███████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗ ║");
	    System.out.println("║ ██╔════╝██║   ██║██╔════╝ ██╔════╝ ██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝ ║");
	    System.out.println("║ ██████╗ ██║   ██║██║  ███╗██║  ███╗█████╗  ███████╗   ██║   ██║██║   ██║██╔██╗ ██║███████╗ ║");
	    System.out.println("║ ╚════██║██║   ██║██║   ██║██║   ██║██╔══╝  ╚════██║   ██║   ██║██║   ██║██║╚██╗██║╚════██║ ║");
	    System.out.println("║ ███████║╚██████╔╝╚██████╔╝╚██████╔╝███████╗███████║   ██║   ██║╚██████╔╝██║ ╚████║███████║ ║");
	    System.out.println("║ ╚══════╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚══════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝ ║");
        System.out.println("║                                                                                            ║");
		Map<Integer, Suggestion> suggestions = null;
		for (int i = 0; i < 4; i++) {
			System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
			switch (i) {
				case 0: suggestions = draftSuggestions; 
				System.out.println("║                                      Draft Suggestions                                     ║"); break;
				case 1: suggestions = submittedSuggestions; 
				System.out.println("║                                    Submitted Suggestions                                   ║"); break;
				case 2: suggestions = acceptedSuggestions;
				System.out.println("║                                     Accepted Suggestions                                   ║"); break;
				case 3: suggestions = rejectedSuggestions;
				System.out.println("║                                     Rejected Suggestions                                   ║"); break;
			}
			if (suggestions.isEmpty()) {
				System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
				System.out.println("║                                  No Suggestions to Display.                                ║");
	        } else {
			    for (Suggestion suggestion : suggestions.values()) {
			    	System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
			        ViewUtility.displayInMenuBulletLarge("Suggestion ID: " + suggestion.getSuggestionID());
			        ViewUtility.displayInMenuBulletLarge("Camp Name: " + suggestion.getCampName());
			        ViewUtility.displayInMenuBulletLarge("Message: " + suggestion.getSuggestionMessage());
			        ViewUtility.displayInMenuBulletLarge("Status: " + suggestion.getSuggestionStatus());
			        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
			    }
	        }
		}
	    System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");
	}
}
