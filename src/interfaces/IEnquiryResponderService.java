package interfaces;

import java.util.Map;

import enums.MessageStatus;
import models.Camp;
import models.Enquiry;

public interface IEnquiryResponderService {

    public Map<Integer, Enquiry> getAllPendingEnquiriesForCamp(Camp camp);

    public Map<Integer, Enquiry> getAllEnquiriesForCamp(Camp camp);

    public boolean respondToEnquiry(int enquiryID, String responderID, MessageStatus status, String response);
    
}
