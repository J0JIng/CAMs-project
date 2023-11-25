package services;

import java.util.Map;
import java.util.stream.Collectors;

import enums.MessageStatus;
import interfaces.IEnquiryResponderService;
import models.Camp;
import models.Enquiry;
import stores.DataStore;

/**
 * The {@code EnquiryResponderService} class implements the {@link IEnquiryResponderService} interface
 * and provides services for handling enquiries to support responding to enquiries related to a camp.
 */

public class EnquiryResponderService implements IEnquiryResponderService {

	/**
     * Constructs an instance of the {@code EnquiryResponderService} class.
     */
	public EnquiryResponderService() {
	}

	/**
     * Retrieves all pending enquiries for a specific camp.
     *
     * @param camp The {@link Camp} for which to retrieve pending enquiries.
     * @return A {@link Map} of pending enquiries, where the key is the enquiry ID and the value is the {@link Enquiry} object.
     */
	@Override
	public Map<Integer, Enquiry> getAllPendingEnquiriesForCamp(Camp camp) {
		Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
		String campName = camp.getCampInformation().getCampName();

		// Returns a Map<Integer, Enquiry> with Enquiries having the specified Camp name.
		Map<Integer, Enquiry> enquiriesForCampMap = enquiryData.values().stream()
				.filter(enquiry -> campName.equals(enquiry.getCampName()) && enquiry.getEnquiryStatus() == MessageStatus.PENDING)
				.collect(Collectors.toMap(Enquiry::getEnquiryID, enquiry -> enquiry));

		return enquiriesForCampMap;
	}

	/**
     * Retrieves all enquiries for a specific camp, excluding draft enquiries.
     *
     * @param camp The {@link Camp} for which to retrieve enquiries.
     * @return A {@link Map} of enquiries, where the key is the enquiry ID and the value is the {@link Enquiry} object.
     */
	@Override
	public Map<Integer, Enquiry> getAllEnquiriesForCamp(Camp camp) {
		Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
		String campName = camp.getCampInformation().getCampName();

		// Returns a Map<Integer, Enquiry> with Enquiries having the specified Camp name.
		Map<Integer, Enquiry> enquiriesForCampMap = enquiryData.values().stream()
				.filter(enquiry -> campName.equals(enquiry.getCampName()) && enquiry.getEnquiryStatus() != MessageStatus.DRAFT)
				.collect(Collectors.toMap(Enquiry::getEnquiryID, enquiry -> enquiry));

		return enquiriesForCampMap;
	}

	/**
     * Responds to a specific enquiry with the provided details.
     *
     * @param enquiryID   The ID of the enquiry to respond to.
     * @param responderID The ID of the responder.
     * @param status      The status of the response, such as {@link MessageStatus#ACCEPTED} or {@link MessageStatus#REJECTED}.
     * @param response    The response message to the enquiry.
     * @return {@code true} if the response is successful, {@code false} otherwise.
     */
	@Override
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