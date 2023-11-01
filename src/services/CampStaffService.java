package services;

import controllers.CampServiceController;
import models.Camp;
import models.CampInformation;
import main.CAMs;

public class CampStaffService {
	public void editCamp(Camp camp, CampInformation info) {
		camp.setCampInformation(info);
		System.out.println("Edited " + camp.getCampInformation().getCampName());
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
	
	public void createCamp(Camp newCamp) {
		CampServiceController.camps.add(newCamp);
        System.out.println("Added " + newCamp.getCampInformation().getCampName());
	}
	
	public void deleteCamp(Camp campToDelete) {
		boolean result = CampServiceController.camps.remove(campToDelete);
        if (result) {
            System.out.println("Deleted " + campToDelete.getCampInformation().getCampName());
        } else {
            System.out.println("Error deleting " + campToDelete.getCampInformation().getCampName());
        }
	}
	
	public void setVisibility(String campName, boolean b) {
        for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
                c.setVisibility(b);
                System.out.println(c.getCampInformation().getCampName() +" visibility set to " + b);
                return;
            }
        }
        System.out.println("Cannot find camp " + campName);
	}

	public void viewRemainingSlots(Camp camp) {}
}

