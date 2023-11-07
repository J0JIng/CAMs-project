package services;

import models.Enquiry;
import stores.DataStore;
import enums.MessageStatus;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class EnquiryService {
    private Map<Integer, Enquiry> enquiryData;

    public EnquiryService(){
        enquiryData = DataStore.getEnquiryData(); 
    }
    
    public boolean editEnquiry(int enquiryID, String studentID) {
    	return false;
    }
    
    public boolean deleteEnquiry(int enquiryID, String studentID) {
    	return false;
    }
    
    public List<Enquiry> getStudentEnquiries(String studentID) {
    	return null;
    }

    public int createEnquiry(String senderID, String campName, String enquiryMessage, boolean isDraft) {
        int enquiryID = UUID.randomUUID().hashCode();
        Enquiry enquiry = new Enquiry(enquiryID, senderID, campName, enquiryMessage);
        if (isDraft) {
            enquiry.setEnquiryStatus(MessageStatus.DRAFT);
        } else {
            // Set other status for non-draft enquiries (e.g., PENDING)
            enquiry.setEnquiryStatus(MessageStatus.PENDING);
        }
        enquiryData.put(enquiryID, enquiry);
        DataStore.setEnquiryData(enquiryData);
        return enquiryID;
    }

    public boolean submitEnquiry(int enquiryID, String senderID, String campName, String enquiryMessage, boolean isDraft) {
        if (enquiryData.containsKey(enquiryID)) {
            Enquiry enquiry = Enquiry(enquiryID, senderID, campName, enquiryMessage);
            if (isDraft) {
                enquiry.setEnquiryStatus(MessageStatus.DRAFT);
            } else {
                enquiry.setEnquiryStatus(MessageStatus.PENDING);
            }
            enquiryData.put(enquiryID, enquiry);
            DataStore.setEnquiryData(enquiryData);
            return true;
        }
        return false;
    }
    
    public Map<Integer, Enquiry> viewDraftEnquiries(String studentID) {
         return enquiryData.values().stream()
                 .filter(enquiry -> enquiry.getSenderID().equals(studentID) && enquiry.getEnquiryStatus() == MessageStatus.DRAFT)
                 .collect(Collectors.toMap(Enquiry::getEnquiryID, enquiry -> enquiry));
        }

    public boolean editDraftEnquiry(int enquiryID, String studentID, String newMessage) {
            if (enquiryData.containsKey(enquiryID)) {
                Enquiry enquiry = enquiryData.get(enquiryID);
                if (enquiry.getSenderID().equals(studentID) && enquiry.getEnquiryStatus() == MessageStatus.DRAFT) {
                    enquiry.setEnquiryMessage(newMessage);
                    DataStore.setEnquiryData(enquiryData);
                    return true;
                }
            }
            return false;
        }
    
        public boolean deleteDraftEnquiry(int enquiryID, String studentID) {
            if (enquiryData.containsKey(enquiryID)) {
                Enquiry enquiry = enquiryData.get(enquiryID);
                if (enquiry.getSenderID().equals(studentID) && enquiry.getEnquiryStatus() == MessageStatus.DRAFT) {
                    enquiryData.remove(enquiryID);
                    DataStore.setEnquiryData(enquiryData);
                    return true;
                }
            }
            return false;
        }
   
     public boolean respondToEnquiry(int enquiryID, String responderID, MessageStatus status, String response) {
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

