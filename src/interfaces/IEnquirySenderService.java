package interfaces;

import java.util.Map;

import models.Enquiry;

public interface IEnquirySenderService {

    public Map<Integer, Enquiry> getRespondedEnquiries(String studentID);

    public Map<Integer, Enquiry> getSubmittedEnquiries(String studentID);

    public Map<Integer, Enquiry> getStudentDraftEnquiries(String studentID);

    public int createEnquiry(String senderID, String campName, String enquiryMessage, boolean isDraft);
    
    public boolean editDraftEnquiry(int enquiryID, String studentID, String newMessage, boolean isDraft);

    public boolean deleteDraftEnquiry(int enquiryID, String studentID);
    
}
