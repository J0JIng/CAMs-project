package services;

public class RequestStaffService {
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
}
