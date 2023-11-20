package views;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import interfaces.IMenuView;

import models.Camp;
import models.CampInformation;
import models.Enquiry;
import models.Suggestion;

import stores.AuthStore;

import utility.ViewUtility;
import utility.SortUtility;

public class StaffView implements IMenuView {

	@Override
	public void displayMenuView() {
		//ViewUtility.clearScreen();

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
        System.out.println("║                      - Staff Menu -                      ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        ViewUtility.displayInMenuCentered("Welcome " + AuthStore.getCurrentUser().getName());
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        ViewUtility.displayInMenuNumbered("Create Camp", 1);
        ViewUtility.displayInMenuNumbered("Delete Camp", 2);
        ViewUtility.displayInMenuNumbered("Edit Camp", 3);
        ViewUtility.displayInMenuNumbered("Set Camp Visibility", 4);
        ViewUtility.displayInMenuNumbered("View All Camps", 5);
        ViewUtility.displayInMenuNumbered("View Camps with Filters", 6);
        ViewUtility.displayInMenuNumbered("View Created Camps", 7);
        ViewUtility.displayInMenuNumbered("View Student List", 8);
        ViewUtility.displayInMenuNumbered("View Enquiries for Camp", 9);
        ViewUtility.displayInMenuNumbered("Respond to Enquiries to Camp", 10);
        ViewUtility.displayInMenuNumbered("View Suggestions", 11);
        ViewUtility.displayInMenuNumbered("Respond to Suggestions", 12);
        ViewUtility.displayInMenuNumbered("Generate Camp Report", 13);
        ViewUtility.displayInMenuNumbered("Generate Camp Committee Performance Report", 14);
        ViewUtility.displayInMenuNumbered("Generate Camp Student's Enquiry Report", 15);
        ViewUtility.displayInMenuNumbered("Change Password", 16);
        ViewUtility.displayInMenuNumbered("Logout", 17);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("Select an option: ");
	}
	
	public void studentChoiceView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		System.out.println("║                   - Choose List Type -                   ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        ViewUtility.displayInMenuNumbered("Registered Students", 1);
        ViewUtility.displayInMenuNumbered("Committee Members", 2);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("Select an option: ");
	}
	
	public void editCampView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		System.out.println("║                 Select a field to update:                ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        ViewUtility.displayInMenuNumbered("Camp Name", 1);
        ViewUtility.displayInMenuNumbered("Camp Start Date", 2);
        ViewUtility.displayInMenuNumbered("Camp End Date", 3);
        ViewUtility.displayInMenuNumbered("Camp Registration Closing Date", 4);
        ViewUtility.displayInMenuNumbered("Camp Location", 5);
        ViewUtility.displayInMenuNumbered("Camp Total Slots", 6);
        ViewUtility.displayInMenuNumbered("Camp Committee Slots", 7);
        ViewUtility.displayInMenuNumbered("Description", 8);
        ViewUtility.displayInMenuNumbered("User Group", 9);
        ViewUtility.displayInMenuNumbered("Exit User", 10);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
	
	public void viewStudentList(List<String> students, List<String> committeeMembers, Camp c) {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		ViewUtility.displayInMenuCentered(" - Students Registered in " + c.getCampInformation().getCampName() + " - ");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
		if (students == null || students.size() == 0) {
			ViewUtility.displayInMenuCentered(" - No Students Registered in " + c.getCampInformation().getCampName() + " - ");
		} else {
			SortUtility.alphabeticalSort(students);
			for (int i = 0; i < students.size(); i++) {
		        ViewUtility.displayInMenuNumbered(students.get(i), i+1);
		    }
		}
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        ViewUtility.displayInMenuCentered(" - Committe Members in " + c.getCampInformation().getCampName() + " - ");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        if (committeeMembers == null || committeeMembers.size() == 0) {
        	ViewUtility.displayInMenuCentered(" - No Committe Members in " + c.getCampInformation().getCampName() + " - ");
        } else {
			SortUtility.alphabeticalSort(committeeMembers);
        	for (int i = 0; i < committeeMembers.size(); i++) {
    	        ViewUtility.displayInMenuNumbered(committeeMembers.get(i), i+1);
    	    }
        }
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println("(Press Enter to return) ");
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
		Collections.sort(campData, Comparator.comparing(c -> c.getCampInformation().getCampName()));
    	for (Camp c : campData) {
    		ViewUtility.displayInMenuNumbered(c.getCampInformation().getCampName(), i);
        	i++;
    	}
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
	
	/**
	 * Displays the camp information of the camp.
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
    		ViewUtility.displayInMenuBullet("Camp Visibility: " + c.getVisibility());
        	i++;
    	//}
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("(Press Enter to return) ");
	}

	public void viewFliterOption() {
    	// Print the filtered and sorted camps
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		ViewUtility.displayInMenuCentered(" - Filter Options - ");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
    	ViewUtility.displayInMenuNumbered("Filter by Date", 1);
    	ViewUtility.displayInMenuNumbered("Filter by Location", 2);
    	ViewUtility.displayInMenuNumbered("Sort by Name (Alphabetical Order)", 3);
		System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	public void viewToggleOption(Camp camp) {
    	// Print menu
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		ViewUtility.displayInMenuCentered(" - " + camp.getCampInformation().getCampName() + " - ");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
		ViewUtility.displayInMenuCentered("Select Camp Visibility [Exit press Enter]:");
		ViewUtility.displayInMenuNumbered("Off", 0);
		ViewUtility.displayInMenuNumbered("On", 1);
		System.out.println("╚══════════════════════════════════════════════════════════╝");
		System.out.println("Select option: ");
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

	public void displaySuggestions(Map<Integer, Suggestion> suggestions) {	    
	    System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                                            ║"); 
	    System.out.println("║ ███████╗██╗   ██╗ ██████╗  ██████╗ ███████╗███████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗ ║");
	    System.out.println("║ ██╔════╝██║   ██║██╔════╝ ██╔════╝ ██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝ ║");
	    System.out.println("║ ██████╗ ██║   ██║██║  ███╗██║  ███╗█████╗  ███████╗   ██║   ██║██║   ██║██╔██╗ ██║███████╗ ║");
	    System.out.println("║ ╚════██║██║   ██║██║   ██║██║   ██║██╔══╝  ╚════██║   ██║   ██║██║   ██║██║╚██╗██║╚════██║ ║");
	    System.out.println("║ ███████║╚██████╔╝╚██████╔╝╚██████╔╝███████╗███████║   ██║   ██║╚██████╔╝██║ ╚████║███████║ ║");
	    System.out.println("║ ╚══════╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚══════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝ ║");
        System.out.println("║                                                                                            ║");
		if (suggestions.isEmpty()) {
			System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
			System.out.println("║                                  No Suggestions to Display.                                ║");
        } else {
        	System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
		    for (Suggestion suggestion : suggestions.values()) {
		        ViewUtility.displayInMenuBulletLarge("Suggestion ID: " + suggestion.getSuggestionID());
		        ViewUtility.displayInMenuBulletLarge("Camp Name: " + suggestion.getCampName());
		        ViewUtility.displayInMenuBulletLarge("Message: " + suggestion.getSuggestionMessage());
		        ViewUtility.displayInMenuBulletLarge("Status: " + suggestion.getSuggestionStatus());
		        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
		    }
        }
	    System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");
	}

	public void viewReportMenu() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		System.out.println("║                                                          ║");
		System.out.println("║██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗███████╗║");
		System.out.println("║██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝██╔════╝║");
		System.out.println("║██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   ███████╗║");
		System.out.println("║██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   ╚════██║║");
		System.out.println("║██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   ███████║║");
		System.out.println("║╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚══════╝║");
        System.out.println("║                                                          ║");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
        ViewUtility.displayInMenuNumbered("Generate Reports For ALL Camps", 1);
        ViewUtility.displayInMenuNumbered("Generate Reports For Staff Camps", 2);
        ViewUtility.displayInMenuNumbered("Generate Report For Selected Camp", 3);
		System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	public void showFilterInput() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		ViewUtility.displayInMenuCentered(" - Choose Filters - ");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
		ViewUtility.displayInMenuNumbered("No Filter", 1);
		ViewUtility.displayInMenuNumbered("Camp Information", 2);
		ViewUtility.displayInMenuNumbered("Attendee", 3);
		ViewUtility.displayInMenuNumbered("Camp Committee", 4);
		ViewUtility.displayInMenuNumbered("Location", 5);
		ViewUtility.displayInMenuNumbered("Date of Camp", 6);
		System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	public void viewPerformanceReportMenu() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		System.out.println("║                                                          ║");
		System.out.println("║██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗███████╗║");
		System.out.println("║██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝██╔════╝║");
		System.out.println("║██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   ███████╗║");
		System.out.println("║██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   ╚════██║║");
		System.out.println("║██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   ███████║║");
		System.out.println("║╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚══════╝║");
        System.out.println("║                                                          ║");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
        ViewUtility.displayInMenuNumbered("Generate Performance Reports For ALL Camps", 1);
        ViewUtility.displayInMenuNumbered("Generate Performance Reports For Staff Camps", 2);
        ViewUtility.displayInMenuNumbered("Generate Performance Report For Selected Camp", 3);
		System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	public void showPerformanceFilterInput() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		ViewUtility.displayInMenuCentered(" - Choose Filters - ");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
		ViewUtility.displayInMenuNumbered("No Filter", 1);
		ViewUtility.displayInMenuNumbered("Committee ID", 2);
		ViewUtility.displayInMenuNumbered("Committee Name", 3);
		ViewUtility.displayInMenuNumbered("Committee Point", 4);
		System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	public void viewEnquiryReportMenu() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		System.out.println("║                                                          ║");
		System.out.println("║██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗███████╗║");
		System.out.println("║██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝██╔════╝║");
		System.out.println("║██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   ███████╗║");
		System.out.println("║██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   ╚════██║║");
		System.out.println("║██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   ███████║║");
		System.out.println("║╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚══════╝║");
        System.out.println("║                                                          ║");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
        ViewUtility.displayInMenuNumbered("Generate Enquiry Reports For ALL Camps", 1);
        ViewUtility.displayInMenuNumbered("Generate Enquiry Reports For Staff Camps", 2);
        ViewUtility.displayInMenuNumbered("Generate Enquiry Report For Selected Camp", 3);
		System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	public void showEnquiryFilterInput() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		ViewUtility.displayInMenuCentered(" - Choose Filters - ");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
		ViewUtility.displayInMenuNumbered("No Filter", 1);
		ViewUtility.displayInMenuNumbered("Enquiry ID", 2);
		ViewUtility.displayInMenuNumbered("Sender Name", 3);
		ViewUtility.displayInMenuNumbered("Responder Point", 4);
		ViewUtility.displayInMenuNumbered("Enquiry Status", 5);
		ViewUtility.displayInMenuNumbered("Enquiry Message", 6);
		ViewUtility.displayInMenuNumbered("Enquiry Response", 7);
		System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	
}
