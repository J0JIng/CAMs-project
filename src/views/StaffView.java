package views;

import java.util.List;
import java.util.Map;

import interfaces.IMenuView;
import models.Camp;
import models.CampInformation;
import models.Enquiry;
import models.Suggestion;
import stores.AuthStore;
import stores.DataStore;
import utility.ViewUtility;

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
        System.out.println("║[1]  Create Camp                                          ║");
        System.out.println("║[2]  Delete Camp                                          ║");
        System.out.println("║[3]  Edit Camp                                            ║");
        System.out.println("║[4]  Set Camp Visibility                                  ║");
        System.out.println("║[5]  View All Camps                                       ║");
        System.out.println("║[6]  View Camps with Filters                              ║");
        System.out.println("║[7]  View Created Camps                                   ║");
        System.out.println("║[8]  View Student List                                    ║");
        System.out.println("║[9]  View Enquiries for Camp                              ║");
        System.out.println("║[10] Respond to Enquiries to Camp                         ║");
        System.out.println("║[11] View Suggestions                                     ║");
        System.out.println("║[12] Respond to Suggestions                               ║");
        System.out.println("║[13] Generate Camp Report                                 ║");
        System.out.println("║[14] Generate Camp Committee Performance Report           ║");
        System.out.println("║[15] Generate Camp Student's Enquiry Report               ║");
        System.out.println("║[16] Change Password                                      ║");
        System.out.println("║[17] Logout                                               ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("Select an option: ");
	}
	
	public void studentChoiceView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		System.out.println("║                   - Choose List Type -                   ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║[1] Registered Students                                   ║");
        System.out.println("║[2] Committe Members                                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("Select an option: ");
	}
	
	public void editCampView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		System.out.println("║                 Select a field to update:                ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║[1] Camp Name                                             ║");
        System.out.println("║[2] Camp Start Date                                       ║");
        System.out.println("║[3] Camp End Date                                         ║");
        System.out.println("║[4] Camp Registration Closing Date                        ║");
        System.out.println("║[5] Camp Location                                         ║");
        System.out.println("║[6] Camp Total Slots                                      ║");
        System.out.println("║[7] Camp Camp Committee Slots                             ║");
        System.out.println("║[8] Description                                           ║");
        System.out.println("║[9] User Group                                            ║");
		System.out.println("║[10] Exit User                                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
	
	public void viewStudentList(List<String> students, List<String> committeeMembers, Camp c) {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		ViewUtility.displayInMenuCentered(" - Students Registered in " + c.getCampInformation().getCampName() + " - ");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
		if (students == null || students.size() == 0) {
			ViewUtility.displayInMenuCentered(" - No Students Registered in " + c.getCampInformation().getCampName() + " - ");
		} else {
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
        	for (int i = 0; i < committeeMembers.size(); i++) {
    	        ViewUtility.displayInMenuNumbered(committeeMembers.get(i), i+1);
    	    }
        }
        System.out.println("╚══════════════════════════════════════════════════════════╝");
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
    	System.out.println("║[1] Filter by Date                                        ║");
    	System.out.println("║[2] Filter by Location                                    ║");	
    	System.out.println("║[3] Sort by Name (Alphabetical Order)                     ║");
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

	
}
