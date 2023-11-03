package services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import controllers.CampServiceController;
import main.CAMs;
import models.Camp;
import models.CampInformation;
import models.Student;

public class CampStudentService {
	Scanner scanner = new Scanner(System.in);
	
	public void withdrawCamp() {
		Student student = (Student) CAMs.currentUser;
		//But the student is not allowed to register the same camp again.
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				// Check if already registered
				if (c.getRegisteredStudents().contains(student)) {
					// registered, proceed to withdraw student
					// Step 1: Remove the camp from the student's list of registered camps
    				student.getRegisteredCamps().remove(camp);
    				// Step 2: Remove the student from the camp's list of registered students
					c.getRegisteredStudents().remove(student);
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
		
		Student student = (Student) CAMs.currentUser;
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		Date currentDate = new Date(); // Get the current date and time
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				Date campDate = c.getCampInformation().getCampDate();
				// Check if the student is already registered for another camp with a date clash
            	if (hasDateClash(student, campDate)) {
                	System.out.println("You are already registered for a camp with a date clash.");
                	return;
            	}
				// Check if the current date is after the closing date
				if (currentDate.after(c.getCampInformation().getCampRegistrationClosingDate())) {
    				System.out.println("Camp registration has closed.");
				} else {
					// Need to check if any slots available
					if (c.getCampInformation().getCampTotalSlots()-c.getRegisteredStudents().size() > 0) {
						// Need to check is student is registered to prevent double registration
						if (c.getRegisteredStudents().contains(student)){
							System.out.println("Already Registered!");
						} else {
							// Step 1: Add the camp to the student's list of registered camps
							student.getRegisteredCamps().add(camp);
							// Step 2: Add the student to the camp's list of registered students
							c.getRegisteredStudents().add(student);
							System.out.println("Registered for " + c.getCampInformation().getCampName());
						}
					} else {
					System.out.println("No slots available");
					}
				}
                return;
            }
        }
		System.out.println("Cannot find " + campName);
	}

	private boolean hasDateClash(Student student, Date campDate) {
    	// Check if the student is already registered for another camp with a date clash
    	for (Camp registeredCamp : student.getRegisteredCamps()) {
        	Date registeredCampDate = registeredCamp.getCampInformation().getCampDate();
        	if (registeredCampDate.equals(campDate)) {
            	return true; // There's a date clash
        	}
    	}
    	return false; // No date clash found
	}

	public void registerAsCommittee(){
		Student student = (Student) CAMs.currentUser;
		//Checks whether student is already a committee member
		if (student.getCommitteeStatus() != null){
			System.out.println(student.getName() + " is already a committee member for " + student.getCommitteeStatus().getCampInformation().getCampName());
        	return;
		} else {
			scanner.nextLine();
        	System.out.print("Enter Camp Name: ");
        	String campName = scanner.nextLine();
        	for (Camp camp : student.getRegisteredCamps()) {
            	if (camp.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
                	if (camp.getCommitteeMembers().contains(student)) {
                    	System.out.println(student.getName() + " is already a committee member for " + campName);
                    	return;
                }
                if (camp.getCommitteeMembers().size() < camp.getCampInformation().getCampCommitteeSlots()) {
                    // Register student as a committee member for the selected camp
                    camp.getCommitteeMembers().add(student);
                    student.setCommitteeStatus(camp);
                    System.out.println(student.getName() + " registered as a committee member for " + campName);
                } else {
                    System.out.println("No committee slots available for " + campName);
                }
                	return;
            	}
        	}
        	System.out.println("Cannot find " + campName + " in your registered camps.");
			return;
    	}
	}

	public void withdrawFromCommittee(){
		Student student = (Student) CAMs.currentUser;
		//Checks whether student is a committee member
		if (student.getCommitteeStatus() == null){
			System.out.println(student.getName() + " is not a committee member for any camp.");
        	return;
		} else {
        	String campName = student.getCommitteeStatus();
			student.getCommitteeStatus().getCommitteeMembers().remove(student);
			student.setCommitteeStatus(null);
			System.out.println("You are no longer a committee member.");
        }
    }

	public void viewRegisteredCamps() {
        Student student = (Student) CAMs.currentUser;

        if (student.getRegisteredCamps().isEmpty()) {
            System.out.println("You are not registered in a camp.");
            return;
        }

        System.out.println("----------------------------");
        System.out.println("|          Camps           |");
        System.out.println("----------------------------");

        boolean committeeCampPrinted = false;

        for (Camp camp : student.getRegisteredCamps()) {
            if (camp == student.getCommitteeStatus() && !committeeCampPrinted) {
                System.out.println("Committee Camp: " + camp.getCampInformation().getCampName());
                System.out.println("------------------------------");
                committeeCampPrinted = true;
            } else {
                if (!committeeCampPrinted) {
                    System.out.println("Committee Camp: None");
                    System.out.println("------------------------------");
                    committeeCampPrinted = true;
                }
            }

            if (camp != student.getCommitteeStatus()) {
                if (!committeeCampPrinted) {
                    System.out.println("Committee Camp: None");
                    System.out.println("------------------------------");
                    committeeCampPrinted = true;
                }
                System.out.println("Other registered camps:");
                System.out.println("Camp: " + camp.getCampInformation().getCampName());
            }
        }
	}


	
	public void viewAllCamps() {
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
