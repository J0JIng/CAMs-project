package views;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import interfaces.IUserMenuView;
import models.Camp;
import models.CampInformation;
import models.Enquiry;
import models.Suggestion;

import stores.AuthStore;

import utility.ViewUtility;
import utility.SortUtility;

/**
 * {@link StaffView} class implements the {@link IUserMenuView} interface and is responsible for displaying the staff menu
 * with various options. It includes options for creating, deleting, and editing camps,
 * managing camp visibility, viewing reports, and more. Users can choose an option by entering
 * the corresponding option number.
 */
public class StaffView implements IUserMenuView {

	/**
	 * Displays the staff menu with various options.
	 * Options include creating, deleting, and editing camps,
	 * managing camp visibility, viewing reports, and more.
	 * The menu allows user to choose an option by entering the option number.
	 */
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
	
	/**
	 * Displays the menu view for editing a camp's details.
	 * Users are prompted to select an option for the corresponding field to update.
	 * The fields to edit are camp name, start date, end date,
	 * registration closing date, location, total slots, committee slots,
	 * description and user group.
	 */
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
	
	/**
	 * Displays the list of students and committee members registered in a camp.
	 * If no students or committee members are registered, It returns appropriate
	 * messages like "No Students Registered".
	 * The list is sorted alphabetically, and each entry is numbered.
	 * @param students List of students registered in the camp.
	 * @param committeeMembers List of committee members registered in the camp.
	 * @param c The camp for which the student list is displayed.
	 */
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
        //System.out.println("(Press Enter to return) ");
	}
	
	/**
	 * Displays the list of camps provided with a specified title.
	 * The list includes the camp names sorted alphabetically.
	 * Each camp is numbered in the menu for easy reference.
	 * @param campData The list of camps to be displayed.
	 * @param title The customizable title to be centered at the top of the menu.
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
	 * Displays detailed information about a specific camp, including its name, start and end dates,
	 * registration closing date, user group, location, total slots, committee slots, description,
	 * staff in charge, and visibility status.
	 * @param c The camp's information to be displayed.
	 */
	public void viewCampInformation(Camp c) {
    	// Print the filtered and sorted camps
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		ViewUtility.displayInMenuCentered(" - " + c.getCampInformation().getCampName() + " - ");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
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
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        //System.out.print("(Press Enter to return) ");
	}

	/**
	 * Displays the filter options menu for camps, including options to filter by date,
	 * filter by location, and sort by name in alphabetical order.
	 */
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

	/**
	 * Displays the toggle options menu for setting camp visibility, allowing the user to choose
	 * between "On" and "Off" visibility states for a specific camp.
	 * @param camp The camp for which visibility options are displayed.
	 */
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

	/**
	 * Displays a formatted view of the enquiries, including details such as camp name, message,
	 * status, response, and responder ID for each enquiry. If there are no enquiries, a corresponding
	 * message "No Enquiries to Display" is displayed.
	 * @param enquiries A map of enquiries where the key is the enquiry ID and the value is the Enquiry object.
	 */
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

	/**
	 * Displays a formatted view of the suggestions, including details such as suggestion ID, camp name,
	 * message, and status for each suggestion. If there are no suggestions, 
	 * a corresponding message "No Suggestions to Display" is displayed.
	 * @param suggestions A map of suggestions where the key is the suggestion ID and the value is the Suggestion object.
	 */
	public void displaySuggestion(Map<Integer, Suggestion> suggestions) {	    
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

	/**
	 * Displays the menu for generating various types of reports, 
	 * such as reports for all camps, the current staff user's camps,
	 * or a selected camp.
	 */
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

	/**
	 * Displays the filter options for report generation, choosing between
	 * between different filtering options such as No Filter, Camp Information, Attendee, 
	 * Camp Committee, Location, and Date of Camp.
	 */
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

	/**
	 * Displays the menu for generating performance reports, providing options for generating reports
	 * for all camps, staff camps, or a selected camp.
	 */
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

	/**
	 * Displays the filter options for generating performance reports, allowing the user to choose
	 * between different filtering options such as Committee ID, Committee Name, and Committee Points.
	 */
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

	/**
	 * Displays the menu for generating enquiry reports, providing options for generating reports for
	 * all camps, staff camps, or a selected camp.
	 */
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

	/**
	 * Displays the filter options for generating enquiry reports, allowing the user to choose between
	 * different filtering options such as Enquiry ID, Sender Name, Responder Point, Enquiry Status,
	 * Enquiry Message, and Enquiry Response.
	 */
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
