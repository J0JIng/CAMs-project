package services;

import models.Enquiry;
import stores.DataStore;
import enums.MessageStatus;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EnquiryStaffService {
    private Map<Integer, Enquiry> enquiryData;

    public EnquiryStaffService(){
        enquiryData = DataStore.getEnquiryData();
    }

    /*public boolean respondToEnquiry(int enquiryID, String responderID, MessageStatus status, String response) {
        if (enquiryData.containsKey(enquiryID)) {
            Enquiry enquiry = enquiryData.get(enquiryID);
            enquiry.setResponderID(responderID);
            enquiry.setEnquiryResponse(response);
            enquiry.setEnquiryStatus(status);
            DataStore.setEnquiryData(enquiryData);
            return true;
        }
        return false;
    } */
}
