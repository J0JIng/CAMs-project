package services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import controllers.CampServiceController;
import models.Camp;
import models.CampInformation;
import models.Student;
import main.CAMs;

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
                	newCampName = scanner.nextLine();
                	isUniqueName = CampServiceController.camps.stream().noneMatch(existingCamp -> existingCamp.getCampInformation().getCampName().equalsIgnoreCase(newCampName));

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

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
		        
				c.setCampInformation(new CampInformation(newCampName, campDate, campRegistrationClosingDate, campUserGroup, campLocation, campTotalSlots, campCommitteeSlots, campDescription, CAMs.currentUser.getName()));
				System.out.println("Edited " + c.getCampInformation().getCampName());
                return;
            }
        }
		System.out.println("Cannot find " + campName);
	}
	
	public void viewCreatedCamps() {
		System.out.println("----------------------------");
        System.out.println("|    Camps under " + CAMs.currentUser.getName() + "    |");
        System.out.println("----------------------------");
        for (int i = 0; i < CampServiceController.camps.size(); i++) {
            CampInformation campInfo = CampServiceController.camps.get(i).getCampInformation();
            if (campInfo.getCampStaffInCharge() == CAMs.currentUser.getName()) {
	            System.out.println("----------------------------");
	            System.out.println("Name: " + campInfo.getCampName());
	            System.out.println("Date: " + campInfo.getCampDate());
	            System.out.println("Registration Closing Date: " + campInfo.getCampRegistrationClosingDate());
	            System.out.println("User Group: " + campInfo.getCampUserGroup());
	            System.out.println("Location: " + campInfo.getCampLocation());
	            System.out.println("Total Slots: " + campInfo.getCampTotalSlots());
	            System.out.println("Committee Slots: " + campInfo.getCampCommitteeSlots());
	            System.out.println("Description: " + campInfo.getCampDescription());
	            System.out.println("Staff In Charge: " + campInfo.getCampStaffInCharge());
	            System.out.println("Camp Visibility: " + CampServiceController.camps.get(i).getVisibility());
            }
        }
        System.out.println("----------------------------");
	}
	
	public void viewAllCamps() {
		System.out.println("----------------------------");
        System.out.println("|          Camps           |");
        System.out.println("----------------------------");
        for (int i = 0; i < CampServiceController.camps.size(); i++) {
            CampInformation campInfo = CampServiceController.camps.get(i).getCampInformation();
            System.out.println("----------------------------");
            System.out.println("Camp " + (i+1) + ":");
            System.out.println("Name: " + campInfo.getCampName());
            System.out.println("Date: " + campInfo.getCampDate());
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
        if (CampServiceController.camps.size() == 0) {System.out.println("No camps");}
	}
	
	public void createCamp() {
		boolean isUniqueName = false;
    	String campName = "";
		
		while (!isUniqueName) {
        	System.out.print("Enter camp name: ");
        	campName = scanner.nextLine();

        	// Check if a camp with the same name already exists
        	boolean nameExists = CampServiceController.camps.stream().anyMatch(existingCamp -> existingCamp.getCampInformation().getCampName().equalsIgnoreCase(campName));

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

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
        
		Camp c = new Camp(new CampInformation(campName, campStartDate, campEndDate, campRegistrationClosingDate, campUserGroup, campLocation, campTotalSlots, campCommitteeSlots, campDescription, CAMs.currentUser.getName()));
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
}

