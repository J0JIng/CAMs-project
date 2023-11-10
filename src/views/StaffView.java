package views;

import interfaces.IMenuView;
import stores.AuthStore;

public class StaffView implements IMenuView{

	@Override
	public void displayMenuView() {
		System.out.println("-----------------------------------");
		String welcomeMessage = "Welcome " + AuthStore.getCurrentUser().getName();
		int totalLength = welcomeMessage.length();
		int spaces = (33 - totalLength) / 2;

		System.out.println("|" + " ".repeat(spaces) + welcomeMessage + " ".repeat(33 - totalLength - spaces) + "|");
        System.out.println("|           Staff Menu            |");
        System.out.println("|---------------------------------|");
        System.out.println("| 1. Create Camp                  |");
        System.out.println("| 2. Delete Camp                  |");
        System.out.println("| 3. Edit Camp                    |");
        System.out.println("| 4. Set Camp Visibility          |");
        System.out.println("| 5. View All Camps               |");
        System.out.println("| 6. View Camps with Filters      |");
        System.out.println("| 7. View Created Camps           |");
        System.out.println("| 8. View Student List            |");
        System.out.println("| 9. View Enquiries for Camp      |");
        System.out.println("| 10. Respond to Enquiries        |");
        System.out.println("| 11. Logout                      |");
        System.out.println("-----------------------------------");
        System.out.print("Select an option: ");
	}

    /* 
	public void viewCreatedCamps() {
    	System.out.println("----------------------------");
    	System.out.println("|    Camps under " + AuthStore.getCurrentUser().getName() + "    |");
    	System.out.println("----------------------------");

    	List<Camp> userCamps = new ArrayList<>();
    
    	for (Camp camp : CampServiceController.camps) {
        	CampInformation campInfo = camp.getCampInformation();
        	if (campInfo.getCampStaffInCharge().equals(AuthStore.getCurrentUser().getName())) {
            	userCamps.add(camp);
        	}
    	}
    	// Sort the userCamps alphabetically based on camp name
    	Collections.sort(userCamps, (camp1, camp2) -> {
        	String name1 = camp1.getCampInformation().getCampName();
        	String name2 = camp2.getCampInformation().getCampName();
        	return name1.compareTo(name2);
    	});
    	for (Camp camp : userCamps) {
        	CampInformation campInfo = camp.getCampInformation();
        	System.out.println("----------------------------");
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
        	System.out.println("Camp Visibility: " + camp.getVisibility());
			}
		System.out.println("----------------------------");
    }
    	
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
        	viewAllCamps(null, null, null); // Sort by alphabetical order
    	} else {
        	System.out.println("Invalid option.");
    	}
	}

	public void viewAllCamps(String filterBy, Date filterDate, String locationFilter) {
    	List<Camp> filteredCamps = new ArrayList<>();
    	for (Camp c : CampServiceController.camps) {
        	//if (c.getVisibility()) {
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
        	//}
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
				System.out.println("Camp Visibility: " + c.getVisibility());
            	i++;
        	}
    	}
	}
	*/

    /* 
	public void viewStudentList() {
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
        for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				Student s;
				if (c.getRegisteredStudents().size() != 0) {
					for (int i = 1; i <= c.getRegisteredStudents().size(); i++) {
						s = c.getRegisteredStudents().get(i);
						System.out.println(i + s.getName());
					}
				} else {System.out.println("No students registered");}
                return;
            }
        }
        System.out.println("Cannot find camp " + campName);
	}

	public void viewEnquiriesForCamp() {
		System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
    	// Get the camp object by campName
    	Camp camp = null;
    	for (Camp c : CampServiceController.camps) {
        	if (c.getCampInformation().getCampName().equalsIgnoreCase(campName) && c.getCampInformation().getCampStaffInCharge().equals(AuthStore.getCurrentUser().getName())) {
            	camp = c;
            	break;
        	}
    	}
    	if (camp == null) {
        	System.out.println("Camp not found or you are not the staff in charge.");
        	return;
    	}
    	// Retrieve and display enquiries for the camp
    	List<Enquiry> enquiries = EnquiryService.getEnquiriesForCamp(camp);

    	if (enquiries.isEmpty()) {
        	System.out.println("No enquiries for this camp.");
    	} else {
        	System.out.println("Enquiries for Camp: " + campName);
        	for (Enquiry enquiry : enquiries) {
            	System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
            	System.out.println("Student Name: " + enquiry.getStudentName());
            	System.out.println("Enquiry Date: " + enquiry.getEnquiryDate());
            	System.out.println("Enquiry Message: " + enquiry.getEnquiryMessage());
            	System.out.println("Enquiry Status: " + enquiry.getEnquiryStatus());
            	System.out.println("Response: " + enquiry.getEnquiryResponse());
            	System.out.println("-----------------------");
        	}
    	}
	}
	*/
	
}
