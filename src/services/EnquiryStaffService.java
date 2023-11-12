package services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import models.Camp;
import models.Enquiry;
import models.Student;
import stores.DataStore;

public class EnquiryStaffService {
    /* 
	public boolean respondToEnquiry() {
		System.out.print("Enter EnquiryID: ");
    	int enquiryID = scanner.nextInt();
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
        	System.out.println("Failed to send the response.");
    	}

    	return responseResult;
	}*/

	public List<Enquiry> getAllEnquiriesForCamp (Camp camp){
		Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
		String campName = camp.getCampInformation().getCampName();

		//returns the list of Enquiries with Camp name.
		List<Enquiry> enquiriesForCampList = enquiryData.values().stream()
				.filter(enquiry -> campName.equals(enquiry.getCampName()))
				.collect(Collectors.toList());

		return enquiriesForCampList;
	}
}

