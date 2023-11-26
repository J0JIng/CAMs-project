package views;

import java.util.List;
<<<<<<< HEAD
import java.util.Map;

import interfaces.IUserMenuView;
import models.Camp;
import models.CampInformation;
import models.Enquiry;
=======
import java.util.Scanner;
import java.util.Map;

import enums.UserRole;
import interfaces.IMenuView;
import models.Camp;
import models.CampInformation;
import models.Enquiry;
import models.Student;
import models.Suggestion;
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
import stores.AuthStore;
import stores.DataStore;
import utility.ViewUtility;

/**
 * The {@link StudentView} class implements the {@link IUserMenuView} interface
 * and is responsible for displaying the menu and related views for students.
 */
public class StudentView implements IUserMenuView {

    /**
     * Displays the menu selection for students, including options to view camps,
     * register for camps, submit and view enquiries, and more.
     */
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
<<<<<<< HEAD
        ViewUtility.displayInMenuNumbered("View All Camps", 1);
        ViewUtility.displayInMenuNumbered("View Camps with Filters", 2);
        ViewUtility.displayInMenuNumbered("Register for Camp", 3);
        ViewUtility.displayInMenuNumbered("Withdraw from Camp", 4);
        ViewUtility.displayInMenuNumbered("View Remaining Slots of Camp", 5);
        ViewUtility.displayInMenuNumbered("View Registered Camps", 6);
        ViewUtility.displayInMenuNumbered("Submit Enquiry", 7);
        ViewUtility.displayInMenuNumbered("View Enquiry", 8);
        ViewUtility.displayInMenuNumbered("Edit Enquiry", 9);
        ViewUtility.displayInMenuNumbered("Delete Enquiry", 10);
        ViewUtility.displayInMenuNumbered("Register as Committee", 11);
        ViewUtility.displayInMenuNumbered("Change Password", 12);
        ViewUtility.displayInMenuNumbered("Logout", 13);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("Select an option: ");
	}
	
	/**
     * Displays a list of camps specified without any filters.
     *
     * @param campData the list of camps to be displayed
     * @param title    the title for the header
     */
	public void viewCamps(List<Camp> campData, String title) {
=======
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
        System.out.println("║[13] Logout     				                             ║");
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
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
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
<<<<<<< HEAD
     * Displays the detailed information of a specific camp.
     *
     * @param c the camp for which information is to be displayed
     */
	public void viewCampInformation(Camp c) {
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
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
	
	/**
     * Displays the number of slots taken for each camp.
     *
     * @param campData the list of camps to be displayed
     */
	public void viewCampsSlots(List<Camp> campData) {
		Map<String , List<String>> registeredStudents = DataStore.getCampToRegisteredCampCommitteeData(); 
		Map<String , List<String>> registeredCampCommittee = DataStore.getCampToRegisteredStudentData();
=======
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
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
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
<<<<<<< HEAD
        ViewUtility.displayInMenuCentered(" - Slots taken for each camp - ");
        System.out.println("╠════════════════════════════════════════════════╦═════════╣");
        System.out.println("║                   Camp Name                    ║  Slots  ║");
        int i = 1;
    	for (Camp c : campData) {
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
	}

	/**
     * Displays filter options for viewing camps, including options to filter by date,
     * location, and sort by name (alphabetical order).
     */
	public void viewFliterOption() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		ViewUtility.displayInMenuCentered(" - Filter Options - ");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
		ViewUtility.displayInMenuNumbered("Filter by Date", 1);
		ViewUtility.displayInMenuNumbered("Filter by Location", 2);
		ViewUtility.displayInMenuNumbered("Sort by Name (Alphabetical Order)", 3);
		System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	/**
     * Displays a formatted view of draft, submitted, and responded enquiries.
     *
     * @param enquiries a map of draft enquiries to display
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
     * Displays a formatted view of draft, submitted, and responded enquiries.
     *
     * @param draftEnquiries      a map of draft enquiries to display
     * @param submittedEnquiries  a map of submitted enquiries to display
     * @param respondedEnquiries  a map of responded enquiries to display
     */
	public void displayEnquiries(Map<Integer, Enquiry> draftEnquiries, Map<Integer, Enquiry> submittedEnquiries, Map<Integer, Enquiry> respondedEnquiries) {
		System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                                            ║"); 
	    System.out.println("║             ███████╗███╗   ██╗ ██████╗ ██╗   ██╗██╗██████╗ ██╗███████╗███████╗             ║");
	    System.out.println("║             ██╔════╝████╗  ██║██╔═══██╗██║   ██║██║██╔══██╗██║██╔════╝██╔════╝             ║");
	    System.out.println("║             █████╗  ██╔██╗ ██║██║   ██║██║   ██║██║██████╔╝██║█████╗  ███████╗             ║");
	    System.out.println("║             ██╔══╝  ██║╚██╗██║██║▄▄ ██║██║   ██║██║██╔══██╗██║██╔══╝  ╚════██║             ║");
	    System.out.println("║             ███████╗██║ ╚████║╚██████╔╝╚██████╔╝██║██║  ██║██║███████╗███████║             ║");
	    System.out.println("║             ╚══════╝╚═╝  ╚═══╝ ╚══▀▀═╝  ╚═════╝ ╚═╝╚═╝  ╚═╝╚═╝╚══════╝╚══════╝             ║");
        System.out.println("║                                                                                            ║");
        Map<Integer, Enquiry> enquiries = null;
		for (int i = 0; i < 3; i++) {
			System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
			switch (i) {
				case 0: enquiries = draftEnquiries; 
				System.out.println("║                                   --- Draft Enquiries ---                                  ║"); break;
				case 1: enquiries = submittedEnquiries; 
				System.out.println("║                                 --- Submitted Enquiries ---                                ║"); break;
				case 2: enquiries = respondedEnquiries;
				System.out.println("║                                 --- Responded Enquiries ---                                ║"); break;
			}
			if (enquiries.isEmpty()) {
				System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
				System.out.println("║                                   No Enquiries to Display.                                 ║");
	        } else {
	        	for (Enquiry enquiry : enquiries.values()) {
			    	System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
			    	ViewUtility.displayInMenuBulletLarge("Enquiry ID: " + enquiry.getEnquiryID());
					ViewUtility.displayInMenuBulletLarge("Camp Name: " + enquiry.getCampName());
					ViewUtility.displayInMenuBulletLarge("Message: " + enquiry.getEnquiryMessage());
					ViewUtility.displayInMenuBulletLarge("Status: " + enquiry.getEnquiryStatus());
					ViewUtility.displayInMenuBulletLarge("Response: " + enquiry.getEnquiryResponse());
					ViewUtility.displayInMenuBulletLarge("Responder ID: " + enquiry.getResponderID());
			        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
			    }
	        }
		}
		System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");
	}
=======
        ViewUtility.displayInMenuCentered(" - Slots remaining for each camp - ");
        System.out.println("╠══════════════════════════════════════════════════╦═══════╣");
        System.out.println("║               Camp Name           				 ║ Slots ║");
        int i = 1;
    	for (Camp c : campData) {
			int filledSlots = registeredStudents.get(c).size()+registeredCampCommittee.get(c).size();
			int totalSlots = c.getCampInformation().getCampTotalSlots();
			ViewUtility.displayInMenuNumberedTwoColumns(c.getCampInformation().getCampName(), i ,filledSlots, totalSlots);
			i++;
		}
        System.out.println("╚════════════════════════════════════╩═════════════╩═══════╝");
        System.out.print("(Press Enter to return) ");
	}

	public void viewFliterOption() {
    	// Print the filtered and sorted camps
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		System.out.println("║      				Filter Options:						 ║");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
    	System.out.println("║   1. Filter by Date										 ║");
    	System.out.println("║   2. Filter by Location								     ║");	
    	System.out.println("║   3. Sort by Name (Alphabetical Order)					 ║");
		System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	public void displayEnquiries(Map<Integer, Enquiry> enquiries) {
		if (enquiries.isEmpty()) {
			System.out.println("No enquiries to display.");
			return;
		}
		for (Enquiry enquiry : enquiries.values()) {
			System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
			System.out.println("Camp Name: " + enquiry.getCampName());
			System.out.println("Message: " + enquiry.getEnquiryMessage());
			System.out.println("Status: " + enquiry.getEnquiryStatus());
			System.out.println("Response: " + enquiry.getEnquiryResponse());
			System.out.println("Responder ID: " + enquiry.getResponderID());
			System.out.println("------");
		}
	}

	public void displaySuggestion(Map<Integer, Suggestion> suggestions) {
        if (suggestions.isEmpty()) {
            System.out.println("No suggestions to display.");
            return;
        }
        for (Suggestion suggestion : suggestions.values()) {
            System.out.println("Suggestion ID: " + suggestion.getSuggestionID());
            System.out.println("Camp Name: " + suggestion.getCampName());
            System.out.println("Message: " + suggestion.getSuggestionMessage());
            System.out.println("Status: " + suggestion.getSuggestionStatus());
            System.out.println("------");
        }
    }
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
}
