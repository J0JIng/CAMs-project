package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import controllers.CampServiceController;
import models.Camp;
import models.CampInformation;
import models.Student;
import stores.AuthStore;

public class CampStaffService {
	Scanner scanner = new Scanner(System.in);
	
	public void editCamp() {
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				String newCampName = null;
            	boolean isUniqueName = false;

            	while (!isUniqueName) {
                	System.out.print("Enter new camp name: ");
                	String newCampNameCheck = scanner.nextLine(); // Created for checking if camp name is already chosen
                	newCampName = newCampNameCheck;
                	isUniqueName = CampServiceController.camps.stream().noneMatch(existingCamp -> existingCamp.getCampInformation().getCampName().equalsIgnoreCase(newCampNameCheck));

                	if (!isUniqueName) {
                    	System.out.println("Camp name is not unique. Please choose a different name.");
                	}
            	}

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    			Date campStartDate = null;
    			while (campStartDate == null) {
    				System.out.print("Enter camp start date (dd/MM/yyyy): ");
    				String StartDateOfCamp = scanner.nextLine();
    				try {
        				campStartDate = dateFormat.parse(StartDateOfCamp);
    				} catch (Exception e) {
        				System.out.println("Invalid date format for camp start date. Please try again.");
    				}
				}

    			Date campEndDate = null;
    			while (campEndDate == null) {
    				System.out.print("Enter camp end date (dd/MM/yyyy): ");
    				String EndDateOfCamp = scanner.nextLine();
    				try {
        				campEndDate = dateFormat.parse(EndDateOfCamp);
    				} catch (Exception e) {
        				System.out.println("Invalid date format for camp end date. Please try again.");
    				}
				}

    			Date campRegistrationClosingDate = null;
    			while (campRegistrationClosingDate == null) {
    				System.out.print("Enter registration closing date (dd/MM/yyyy): ");
    				String closingDate = scanner.nextLine();
    				try {
        				campRegistrationClosingDate = dateFormat.parse(closingDate);
    				} catch (Exception e) {
        				System.out.println("Invalid date format for camp closing date. Please try again.");
    				}
				}

		        System.out.print("Enter user group: ");
		        String campUserGroup = scanner.nextLine();

		        System.out.print("Enter camp location: ");
		        String campLocation = scanner.nextLine();

		        System.out.print("Enter camp description: ");
		        String campDescription = scanner.nextLine();

		        System.out.print("Enter total slots: ");
		        int campTotalSlots = scanner.nextInt();

		        System.out.print("Enter committee slots: ");
		        int campCommitteeSlots = scanner.nextInt();
		        
				c.setCampInformation(new CampInformation(newCampName, campStartDate, campEndDate, campRegistrationClosingDate, campUserGroup, campLocation, campTotalSlots, campCommitteeSlots, campDescription, AuthStore.getCurrentUser().getName()));
				System.out.println("Edited " + c.getCampInformation().getCampName());
                return;
            }
        }
		System.out.println("Cannot find " + campName);
	}
	
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
        	System.out.println("Camp Visibility: " + CampServiceController.camps.get(i).getVisibility());
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
        	viewAllCamps("date", filterDate);
    	} else if (option == 2) {
        	System.out.print("Enter the location to filter by: ");
        	String locationFilter = scanner.nextLine();
        	viewAllCamps("location", locationFilter); // Pass the location filter
    	} else if (option == 3) {
        	viewAllCamps("name", null); // Sort by alphabetical order
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
	
	public void createCamp() {
		boolean isUniqueName = false;
    	String campName = "";
		
		while (!isUniqueName) {
        	System.out.print("Enter camp name: ");
        	String campNameCheck = scanner.nextLine();
        	campName = campNameCheck; // Created for checking if camp name is already chosen
        	// Check if a camp with the same name already exists
        	boolean nameExists = CampServiceController.camps.stream().anyMatch(existingCamp -> existingCamp.getCampInformation().getCampName().equalsIgnoreCase(campNameCheck));

        	if (!nameExists) {
            	isUniqueName = true;
        	} else {
            	System.out.println("Camp with the same name already exists. Please choose a different name.");
        	}
    	}

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	Date campStartDate = null;
    	while (campStartDate == null) {
    		System.out.print("Enter camp start date (dd/MM/yyyy): ");
    		String StartDateOfCamp = scanner.nextLine();
    		try {
        		campStartDate = dateFormat.parse(StartDateOfCamp);
    		} catch (Exception e) {
        		System.out.println("Invalid date format for camp start date. Please try again.");
    		}
		}

    	Date campEndDate = null;
    	while (campEndDate == null) {
    		System.out.print("Enter camp end date (dd/MM/yyyy): ");
    		String EndDateOfCamp = scanner.nextLine();
    		try {
        		campEndDate = dateFormat.parse(EndDateOfCamp);
    		} catch (Exception e) {
        		System.out.println("Invalid date format for camp end date. Please try again.");
    		}
		}

    	Date campRegistrationClosingDate = null;
    	while (campRegistrationClosingDate == null) {
    		System.out.print("Enter registration closing date (dd/MM/yyyy): ");
    		String closingDate = scanner.nextLine();
    		try {
        		campRegistrationClosingDate = dateFormat.parse(closingDate);
    		} catch (Exception e) {
        		System.out.println("Invalid date format for camp closing date. Please try again.");
    		}
		}

        System.out.print("Enter user group: ");
        String campUserGroup = scanner.nextLine();

        System.out.print("Enter camp location: ");
        String campLocation = scanner.nextLine();

        System.out.print("Enter camp description: ");
        String campDescription = scanner.nextLine();

        System.out.print("Enter total slots: ");
        int campTotalSlots = scanner.nextInt();

        System.out.print("Enter committee slots: ");
        int campCommitteeSlots = scanner.nextInt();
        
		Camp c = new Camp(new CampInformation(campName, campStartDate, campEndDate, campRegistrationClosingDate, campUserGroup, campLocation, campTotalSlots, campCommitteeSlots, campDescription, AuthStore.getCurrentUser().getName()));
		CampServiceController.camps.add(c);
        System.out.println("Created " + c.getCampInformation().getCampName());
	}
	
	public void deleteCamp() {
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
                CampServiceController.camps.remove(c);
                System.out.println("Deleted " + campName);
                return;
            }
        }
        System.out.println("Error deleting " + campName);
	}
	
	public void setVisibility() {
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
    	System.out.print("Enter Visibility: ");
    	boolean b = scanner.nextBoolean();
        for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
                c.setVisibility(b);
                System.out.println(c.getCampInformation().getCampName() +" visibility set to " + b);
                return;
            }
        }
        System.out.println("Cannot find camp " + campName);
	}
	
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

	public void viewEnquiriesForCamp(String campName) {
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
            	System.out.println("Enquiry Message: " + enquiry.getMessage());
            	System.out.println("Enquiry Status: " + enquiry.getEnquiryStatus());
            	System.out.println("Response: " + enquiry.getEnquiryResponse());
            	System.out.println("-----------------------");
        	}
    	}
	}

	public boolean respondToEnquiry(int enquiryID) {
    	// Check if the current user is the staff in charge of the camp
    	// if (!AuthStore.getCurrentUser().getRole().equals("staff")) {
        // 	System.out.println("You must be a staff member to respond to enquiries.");
        // 	return false;
    	// }
    	Camp camp = null;
    	for (Camp c : CampServiceController.camps) {
        	if (c.getCampInformation().getCampName().equalsIgnoreCase(campName) && c.getCampInformation().getCampStaffInCharge().equals(AuthStore.getCurrentUser().getName())) {
            	camp = c;
            	break;
        	}
    	}
    	if (camp == null) {
        	System.out.println("Camp not found or you are not the staff in charge.");
        	return false;
    	}
    	// Check if the enquiry belongs to the camp
    	Enquiry enquiry = EnquiryService.getEnquiry(enquiryID);
    	if (enquiry == null || !enquiry.getCampName().equalsIgnoreCase(camp.getCampInformation().getCampName())) {
        	System.out.println("Enquiry not found or does not belong to the camp.");
        	return false;
    	}
    	System.out.print("Enter your response to the enquiry: ");
    	scanner.nextLine(); // Consume the newline character
    	String response = scanner.nextLine();
    	boolean responseResult = EnquiryService.respondToEnquiry(enquiryID, AuthStore.getCurrentUser().getName(), MessageStatus.ACCEPTED, response);
    	if (responseResult) {
        	System.out.println("Enquiry response sent successfully.");
    	} else {
        	System.println("Failed to send the response.");
    	}

    	return responseResult;
	}


}

