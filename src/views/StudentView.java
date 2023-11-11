package views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import enums.UserRole;
import interfaces.IMenuView;
import models.Camp;
import models.CampInformation;
import models.Student;
import stores.AuthStore;
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
        System.out.println("║[6]  Register as Committee                                ║");
        System.out.println("║[7]  Withdraw from Committee                              ║");
        System.out.println("║[8]  View Registered Camps                                ║");
        System.out.println("║[9]  Submit Enquiry                                       ║");
        System.out.println("║[10] View Enquiry                                         ║");
        System.out.println("║[11] Edit Enquiry                                         ║");
        System.out.println("║[12] Delete Enquiry                                       ║");
        System.out.println("║[13] Change Password                                      ║");
        if (AuthStore.getCurrentUser().getUserRole() == UserRole.COMMITTEE) {
        // Committee Specific Functions:
        System.out.println("║[14] View Enquiries for Camp                              ║");
        System.out.println("║[15] Reply Enquiries for Camp                             ║");
        System.out.println("║[16] View Suggestion                                      ║");
        System.out.println("║[17] Edit Suggestion                                      ║");
        System.out.println("║[18] Delete Suggestion                                    ║");
        System.out.println("║[19] Submit Suggestion                                    ║");
        System.out.println("║[20] Generate Camp Report                                 ║");
        System.out.println("║[21] Logout                                               ║");
        } else {
        System.out.println("║[14] Logout                                               ║");
        }
        
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("Select an option: ");
	}

    ////////////////////////////////////////////////////////////////
    // FOLLOWING VIEW CLASS NOT DONE // fix this @ ojing

    /* 
	// Place in Boundary Class
	public void viewRegisteredCamps() {
    	Student student = (Student) AuthStore.getCurrentUser();
    	if (student.getRegisteredCamps().isEmpty()) {
        	System.out.println("You are not registered in a camp.");
        	return;
    	}
    	System.out.println("----------------------------");
    	System.out.println("|          Camps           |");
    	System.out.println("----------------------------");

    	boolean committeeCampPrinted = false;
    	List<String> otherCampNames = new ArrayList<>();

    	for (Camp camp : student.getRegisteredCamps()) {
        	if (camp.getCampInformation().getCampName() == student.getCommitteeStatus() && !committeeCampPrinted) {
            	System.out.println("Committee Camp: " + camp.getCampInformation().getCampName());
				System.out.println("Start Date: " + camp.getCampInformation().getCampStartDate());
				System.out.println("End Date: " + camp.getCampInformation().getCampEndDate());
            	System.out.println("Registration Closing Date: " + camp.getCampInformation().getCampRegistrationClosingDate());
            	System.out.println("User Group: " + camp.getCampInformation().getCampUserGroup());
            	System.out.println("Location: " + camp.getCampInformation().getCampLocation());
            	System.out.println("Total Slots: " + camp.getCampInformation().getCampTotalSlots());
            	System.out.println("Committee Slots: " + camp.getCampInformation().getCampCommitteeSlots());
            	System.out.println("Description: " + camp.getCampInformation().getCampDescription());
            	System.out.println("Staff In Charge: " + camp.getCampInformation().getCampStaffInCharge());
            	System.out.println("------------------------------");
            	committeeCampPrinted = true;
        	} else {
            	if (!committeeCampPrinted) {
                	System.out.println("Committee Camp: None");
                	System.out.println("------------------------------");
                	committeeCampPrinted = true;
            	}

            	otherCampNames.add(camp.getCampInformation().getCampName());
        	}
    	}
    	// Sort the otherCampNames list alphabetically
    	Collections.sort(otherCampNames);

    	System.out.println("Registered camps as an attendee:");
    	for (String campName : otherCampNames) {
        	System.out.println("Camp: " + campName);
    	}
	}

	// Place in Boundary Class
	public void viewAllCampsWithFilters() {
    	Scanner scanner = new Scanner(System.in);

    	System.out.println("Filter Options:");
    	System.out.println("1. Filter by Date");
    	System.out.println("2. Filter by Location");
    	System.out.println("3. Sort by Name (Alphabetical Order)");
    	System.out.print("Enter the filter option (1/2/3): ");

    	int option = scanner.nextInt();
    	scanner.nextLine(); // Consume the newline character

    	if (option == 1) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        	Date filterDate = null;
        	while (filterDate == null) {
            	System.out.print("Enter the date to filter by (dd/MM/yyyy): ");
            	String dateFilter = scanner.nextLine();
            	try {
                	filterDate = dateFormat.parse(dateFilter);
            	} catch (ParseException e) {
                	System.out.println("Invalid date format. Please enter a date in the format dd/MM/yyyy.");
            	}
        	}
			viewAllCamps("date", filterDate, null);
    	} else if (option == 2) {
        	System.out.print("Enter the location to filter by: ");
        	String locationFilter = scanner.nextLine();
        	viewAllCamps("location", null, locationFilter); // Pass the location filter
    	} else if (option == 3) {
        	viewAllCamps(null, null, null);  // Sort by alphabetical order
    	} else {
        	System.out.println("Invalid option.");
    	}
		scanner.close();
	}

	// Place in Boundary Class
	public void viewAllCamps(String filterBy, Date filterDate, String locationFilter) {
    	List<Camp> filteredCamps = new ArrayList<>();
    	for (Camp c : CampServiceController.camps) {
        	if (c.getVisibility()) {
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
    	}

    	// Sort filteredCamps here based on the chosen filter (e.g., by name, date, or location)
		Collections.sort(filteredCamps, (camp1, camp2) -> {
    		String name1 = camp1.getCampInformation().getCampName();
    		String name2 = camp2.getCampInformation().getCampName();
    		return name1.compareTo(name2);
		});
    	if (filteredCamps.isEmpty()) {
        	System.out.println("No matching camps found.");
    	} else {
        	// Print the filtered and sorted camps
        	System.out.println("----------------------------");
        	System.out.println("|          Camps           |");
        	System.out.println("----------------------------");

        	int i = 1;
        	for (Camp c : filteredCamps) {
            	CampInformation campInfo = c.getCampInformation();
            	System.out.println("----------------------------");
            	System.out.println("Camp " + (i) + ":");
            	System.out.println("Name: " + campInfo.getCampName());
            	System.out.println("Start Date: " + campInfo.getCampStartDate());
				System.out.println("End Date: " + campInfo.getCampEndDate());
            	System.out.println("Registration Closing Date: " + campInfo.getCampRegistrationClosingDate());
            	System.out.println("User Group: " + campInfo.getCampUserGroup());
            	System.out.println("Location: " + campInfo.getCampLocation());
            	System.out.println("Total Slots: " + campInfo.getCampTotalSlots());
            	System.out.println("Committee Slots: " + campInfo.getCampCommitteeSlots());
            	System.out.println("Description: " + campInfo.getCampDescription());
            	System.out.println("Staff In Charge: " + campInfo.getCampStaffInCharge());
            	i++;
        	}
    	}
	}

	// Place in Boundary Class
	public void viewRemainingSlots() {
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				if (c.getRegisteredStudents().size() != 0) {
					System.out.println((c.getCampInformation().getCampTotalSlots()-c.getRegisteredStudents().size()) + " remaining slots" );
				} else {System.out.println("No students registered");}
				scanner.close();
                return;
            }
        }
		System.out.println("Cannot find " + campName);
		scanner.close();
	}
    */
}
