package interfaces;

import java.util.Map;

import enums.MessageStatus;
import models.Camp;
import models.Enquiry;

/**
 * The {@link IEnquiryResponderService} interface defines methods for pertaining to the response to camp-related enquiries.
 */
public interface IEnquiryResponderService {

	/**
     * Retrieves all pending enquiries for the specified camp.
     *
     * @param camp the {@link Camp} for which to retrieve pending enquiries
     * @return a {@link Map} of pending enquiries, where the key is the enquiry ID and the value is the {@link Enquiry} object
     */
    public Map<Integer, Enquiry> getAllPendingEnquiriesForCamp(Camp camp);

    /**
     * Retrieves all enquiries for the specified camp.
     *
     * @param camp the {@link Camp} for which to retrieve enquiries
     * @return a {@link Map} of all enquiries, where the key is the enquiry ID and the value is the {@link Enquiry} object
     */
    public Map<Integer, Enquiry> getAllEnquiriesForCamp(Camp camp);

    /**
     * Responds to the specified enquiry with the provided details.
     *
     * @param enquiryID   the ID of the enquiry to respond to
     * @param responderID the ID of the responder (e.g., student or committee member)
     * @param status      the {@link MessageStatus} indicating the response status
     * @param response    the response message
     * @return {@code true} if the response is successful, {@code false} otherwise
     */
    public boolean respondToEnquiry(int enquiryID, String responderID, MessageStatus status, String response);
    
}
