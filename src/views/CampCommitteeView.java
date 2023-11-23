package views;

import java.util.Map;

import models.Suggestion;
import stores.AuthStore;
import utility.ViewUtility;

public class CampCommitteeView extends StudentView {

	/**
	 * Displays the menu selection for camp committee
	 */
	@Override
	public void displayMenuView() {
		//ViewUtility.clearScreen();
		
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
        ViewUtility.displayInMenuNumbered("View All Camps", 1);
        ViewUtility.displayInMenuNumbered("View Camps with Filters", 2);
        ViewUtility.displayInMenuNumbered("Register for Camp", 3);
        ViewUtility.displayInMenuNumbered("Withdraw from Camp", 4);
        ViewUtility.displayInMenuNumbered("View Registered Slots of Camp", 5);
        ViewUtility.displayInMenuNumbered("View Registered Camps", 6);
        ViewUtility.displayInMenuNumbered("View Enquiries For Camp", 7);
        ViewUtility.displayInMenuNumbered("Respond to Enquiry", 8);
        ViewUtility.displayInMenuNumbered("Submit Suggestion", 9);
        ViewUtility.displayInMenuNumbered("Edit Suggestion", 10);
        ViewUtility.displayInMenuNumbered("Delete Suggestion", 11);
        ViewUtility.displayInMenuNumbered("View Suggestion", 12);
        ViewUtility.displayInMenuNumbered("Generate Report", 13);
        ViewUtility.displayInMenuNumbered("Change Password", 14);
        ViewUtility.displayInMenuNumbered("Logout", 15);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("Select an option: ");
	}

	public void displaySuggestions(Map<Integer, Suggestion> suggestion) {

		System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════╗");
		System.out.println("║                                                                                            ║"); 
		System.out.println("║ ███████╗██╗   ██╗ ██████╗  ██████╗ ███████╗███████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗ ║");
		System.out.println("║ ██╔════╝██║   ██║██╔════╝ ██╔════╝ ██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝ ║");
		System.out.println("║ ██████╗ ██║   ██║██║  ███╗██║  ███╗█████╗  ███████╗   ██║   ██║██║   ██║██╔██╗ ██║███████╗ ║");
		System.out.println("║ ╚════██║██║   ██║██║   ██║██║   ██║██╔══╝  ╚════██║   ██║   ██║██║   ██║██║╚██╗██║╚════██║ ║");
		System.out.println("║ ███████║╚██████╔╝╚██████╔╝╚██████╔╝███████╗███████║   ██║   ██║╚██████╔╝██║ ╚████║███████║ ║");
		System.out.println("║ ╚══════╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚══════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝ ║");
		System.out.println("║                                                                                            ║");
		if (suggestion.isEmpty()) {
			System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
			System.out.println("║                                  No Suggestions to Display.                                ║");
		} else {
			for (Suggestion s : suggestion.values()) {
			System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");
			ViewUtility.displayInMenuBulletLarge("Suggestion ID: " + s.getSuggestionID());
			ViewUtility.displayInMenuBulletLarge("Camp Name: " + s.getCampName());
			ViewUtility.displayInMenuBulletLarge("Message: " + s.getSuggestionMessage());
			ViewUtility.displayInMenuBulletLarge("Status: " + s.getSuggestionStatus());
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
				System.out.println("║                                  --- Draft Suggestions ---                                 ║"); break;
				case 1: suggestions = submittedSuggestions; 
				System.out.println("║                                --- Submitted Suggestions ---                               ║"); break;
				case 2: suggestions = acceptedSuggestions;
				System.out.println("║                                 --- Accepted Suggestions ---                               ║"); break;
				case 3: suggestions = rejectedSuggestions;
				System.out.println("║                                 --- Rejected Suggestions ---                               ║"); break;
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

	public void showFilterInput() {
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

}
