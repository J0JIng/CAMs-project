package services;

import java.util.Scanner;

import controllers.CampServiceController;
import main.CAMs;
import models.Camp;
import models.CampInformation;
import models.Student;

public class CampStudentService {
	Scanner scanner = new Scanner(System.in);
	
	public void withdrawCamp() {
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				if (c.getRegisteredStudents().contains((Student)(CAMs.currentUser))) {
					// registered, proceed to withdraw student
					c.getRegisteredStudents().remove((Student)(CAMs.currentUser));
			        // Check if already registered
					System.out.println("Withdrawn from " + c.getCampInformation().getCampName());
				} else {
					// Not registered in camp
					System.out.println("You have not registered for this camp");
				}
				
                return;
            }
        }
		System.out.println("Cannot find " + campName);
	}
	
	public void registerCamp() {
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				// Need to check if any slots available
				if (c.getCampInformation().getCampTotalSlots()-c.getRegisteredStudents().size() > 0) {
					// Need to check is student is registered to prevent double registration, havent added
					c.getRegisteredStudents().add((Student)(CAMs.currentUser));
			        
					System.out.println("Registered for " + c.getCampInformation().getCampName());
				} else {
					System.out.println("No slots available");
				}
                return;
            }
        }
		System.out.println("Cannot find " + campName);
	}
	
	public void viewCamps() {
		System.out.println("----------------------------");
        System.out.println("|          Camps           |");
        System.out.println("----------------------------");
		int i = 1;
		for (Camp c : CampServiceController.camps) {
			if (c.getVisibility() == true) {
				CampInformation campInfo = c.getCampInformation();
				System.out.println("----------------------------");
	            System.out.println("Camp " + (i) + ":");
	            System.out.println("Name: " + campInfo.getCampName());
	            System.out.println("Date: " + campInfo.getCampDate());
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
        System.out.println("----------------------------");
        if (CampServiceController.camps.size() == 0) {System.out.println("No camps");}
	}
	
	public void viewRemainingSlots() {
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				if (c.getRegisteredStudents().size() != 0) {
					System.out.println((c.getCampInformation().getCampTotalSlots()-c.getRegisteredStudents().size()) + " remaining slots" );
				} else {System.out.println("No students registered");}
                return;
            }
        }
		System.out.println("Cannot find " + campName);
	}
}
