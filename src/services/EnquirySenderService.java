package services;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import enums.MessageStatus;
import interfaces.IEnquirySenderService;
import models.Enquiry;
import stores.DataStore;

/**
 * The {@code EnquirySenderService} class implements the {@link IEnquirySenderService} interface
 * and provides services for retrieving enquiries on varying statuses, creating, editing, and deleting enquiries.
 */
public class EnquirySenderService implements IEnquirySenderService {

	/**
     * Constructs an instance of the {@code EnquirySenderService} class.
     */
    public EnquirySenderService(){
    }

    /**
     * Retrieves all responded enquiries for a specific student.
     *
     * @param studentID The ID of the student for whom to retrieve responded enquiries.
     * @return A {@link Map} of responded enquiries, where the key is the enquiry ID and the value is the {@link Enquiry} object.
     */
    @Override
    public Map<Integer, Enquiry> getRespondedEnquiries(String studentID) {
        Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
         return enquiryData.values().stream()
                 .filter(enquiry -> enquiry.getSenderID().equals(studentID) && enquiry.getEnquiryStatus() == MessageStatus.ACCEPTED)
                 .collect(Collectors.toMap(Enquiry::getEnquiryID, enquiry -> enquiry));
    }
    
    /**
     * Retrieves all submitted (pending) enquiries for a specific student.
     *
     * @param studentID The ID of the student for whom to retrieve submitted enquiries.
     * @return A {@link Map} of submitted enquiries, where the key is the enquiry ID and the value is the {@link Enquiry} object.
     */
    @Override
    public Map<Integer, Enquiry> getSubmittedEnquiries(String studentID) {
        Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
         return enquiryData.values().stream()
                 .filter(enquiry -> enquiry.getSenderID().equals(studentID) && enquiry.getEnquiryStatus() == MessageStatus.PENDING)
                 .collect(Collectors.toMap(Enquiry::getEnquiryID, enquiry -> enquiry));
    }

    /**
     * Retrieves all draft enquiries for a specific student.
     *
     * @param studentID The ID of the student for whom to retrieve draft enquiries.
     * @return A {@link Map} of draft enquiries, where the key is the enquiry ID and the value is the {@link Enquiry} object.
     */
    @Override
    public Map<Integer, Enquiry> getStudentDraftEnquiries(String studentID) {
        Map<Integer, Enquiry> enquiryData = DataStore.getEnquiryData();
        return enquiryData.values().stream()
                .filter(enquiry -> enquiry.getSenderID().equals(studentID) && enquiry.getEnquiryStatus() == MessageStatus.DRAFT)
                .collect(Collectors.toMap(Enquiry::getEnquiryID, enquiry -> enquiry));
    }

    /**
     * Creates a new enquiry and returns its ID.
     *
     * @param senderID       The ID of the student sending the enquiry.
     * @param campName       The name of the camp related to the enquiry.
     * @param enquiryMessage The message content of the enquiry.
     * @param isDraft        {@code true} if the enquiry is a draft, {@code false} otherwise.
     * @return The ID of the created enquiry.
     */
    @Override
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

    /**
     * Edits a draft enquiry with the provided details.
     *
     * @param enquiryID The ID of the enquiry to edit.
     * @param studentID The ID of the student editing the enquiry.
     * @param newMessage The new message content for the enquiry.
     * @param isDraft    {@code true} if the enquiry is still a draft after editing, {@code false} otherwise.
     * @return {@code true} if the edit is successful, {@code false} otherwise.
     */
    @Override
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
    
    /**
     * Deletes a draft enquiry.
     *
     * @param enquiryID The ID of the draft enquiry to delete.
     * @param studentID The ID of the student deleting the enquiry.
     * @return {@code true} if the deletion is successful, {@code false} otherwise.
     */
    @Override
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