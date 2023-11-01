package services;

import controllers.CampServiceController;
import models.Camp;
import models.CampInformation;

public class CampStudentService {
	public void withdrawCamp(Camp camp) {
		
	}
	
	public void registerCamp(Camp camp) {
		
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
	            System.out.println("Camp Visibility: " + CampServiceController.camps.get(i).getVisibility());
				i++;
			}
		}
        System.out.println("----------------------------");
        if (CampServiceController.camps.size() == 0) {System.out.println("No camps");}
	}
}
