package services;

import models.Enquiry;
import stores.DataStore;
import enums.MessageStatus;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


public class EnquirySenderService {

    public EnquirySenderService(){
    }

    public Map<Integer, Enquiry> getRespondedEnquiries(String studentID) {
        Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
         return enquiryData.values().stream()
                 .filter(enquiry -> enquiry.getSenderID().equals(studentID) && enquiry.getEnquiryStatus() == MessageStatus.ACCEPTED)
                 .collect(Collectors.toMap(Enquiry::getEnquiryID, enquiry -> enquiry));
    }
    
    public Map<Integer, Enquiry> getSubmittedEnquiries(String studentID) {
        Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
         return enquiryData.values().stream()
                 .filter(enquiry -> enquiry.getSenderID().equals(studentID) && enquiry.getEnquiryStatus() == MessageStatus.PENDING)
                 .collect(Collectors.toMap(Enquiry::getEnquiryID, enquiry -> enquiry));
    }

    public Map<Integer, Enquiry> getStudentDraftEnquiries(String studentID) {
        Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
        return enquiryData.values().stream()
                .filter(enquiry -> enquiry.getSenderID().equals(studentID) && enquiry.getEnquiryStatus() == MessageStatus.DRAFT)
                .collect(Collectors.toMap(Enquiry::getEnquiryID, enquiry -> enquiry));
    }

    public int createEnquiry(String senderID, String campName, String enquiryMessage, boolean isDraft) {
        Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
        int enquiryID = Math.abs(UUID.randomUUID().hashCode());
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

    public boolean editDraftEnquiry(int enquiryID, String studentID, String newMessage, boolean isDraft) {
        Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
        if (enquiryData.containsKey(enquiryID)) {
            Enquiry enquiry = enquiryData.get(enquiryID);
            if (enquiry.getSenderID().equals(studentID) && enquiry.getEnquiryStatus() == MessageStatus.DRAFT) {
                enquiry.setEnquiryMessage(newMessage);
                if (!isDraft) {
                    enquiry.setEnquiryStatus(MessageStatus.PENDING);
                }
                DataStore.setEnquiryData(enquiryData);
                return true;
            }
        }
        return false;
    }
    
    public boolean deleteDraftEnquiry(int enquiryID, String studentID) {
        Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
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
   
	
    
    
}