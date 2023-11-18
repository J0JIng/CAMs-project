package services;

import java.util.Map;
import java.util.stream.Collectors;

import models.Camp;
import models.Enquiry;
import stores.DataStore;
import enums.MessageStatus;
import interfaces.IEnquiryStaffService;

public class EnquiryResponderService implements IEnquiryStaffService {

	public EnquiryResponderService() {
	};

	public Map<Integer, Enquiry> getAllEnquiriesForCamp(Camp camp) {
		Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
		String campName = camp.getCampInformation().getCampName();

		// Returns a Map<Integer, Enquiry> with Enquiries having the specified Camp name.
		Map<Integer, Enquiry> enquiriesForCampMap = enquiryData.values().stream()
				.filter(enquiry -> campName.equals(enquiry.getCampName()) && enquiry.getEnquiryStatus() == MessageStatus.PENDING)
				.collect(Collectors.toMap(Enquiry::getEnquiryID, enquiry -> enquiry));

		return enquiriesForCampMap;
	}

	public boolean respondToEnquiry(int enquiryID, String responderID, MessageStatus status, String response) {
		Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
	    if (enquiryData.containsKey(enquiryID)) {
	        Enquiry enquiry = enquiryData.get(enquiryID);
	        enquiry.setResponderID(responderID);
	        enquiry.setEnquiryResponse(response);
	        enquiry.setEnquiryStatus(status);
	        DataStore.setEnquiryData(enquiryData);
	        return true;
	    }
	    return false;
	}
}
