package interfaces;

import java.util.Map;

import models.Enquiry;

/**
 * The {@link IEnquirySenderService} interface defines methods for managing the creation, editing, and deletion of enquiries
 * by a student, as well as retrieving responded, submitted, and draft enquiries.
 */
public interface IEnquirySenderService {

	/**
     * Retrieves all responded enquiries submitted by the specified student.
     *
     * @param studentID the ID of the student for whom to retrieve responded enquiries
     * @return a {@link Map} of responded enquiries, where the key is the enquiry ID and the value is the {@link Enquiry} object
     */
    public Map<Integer, Enquiry> getRespondedEnquiries(String studentID);

    /**
     * Retrieves all submitted enquiries by the specified student.
     *
     * @param studentID the ID of the student for whom to retrieve submitted enquiries
     * @return a {@link Map} of submitted enquiries, where the key is the enquiry ID and the value is the {@link Enquiry} object
     */
    public Map<Integer, Enquiry> getSubmittedEnquiries(String studentID);

    /**
     * Retrieves all draft enquiries created by the specified student.
     *
     * @param studentID the ID of the student for whom to retrieve draft enquiries
     * @return a {@link Map} of draft enquiries, where the key is the enquiry ID and the value is the {@link Enquiry} object
     */
    public Map<Integer, Enquiry> getStudentDraftEnquiries(String studentID);

    /**
     * Creates a new enquiry with the provided details.
     *
     * @param senderID        the ID of the student sending the enquiry
     * @param campName        the name of the camp related to the enquiry
     * @param enquiryMessage  the message content of the enquiry
     * @param isDraft         {@code true} if the enquiry is a draft, {@code false} otherwise
     * @return the ID of the created enquiry
     */
    public int createEnquiry(String senderID, String campName, String enquiryMessage, boolean isDraft);
    
    /**
     * Edits the specified draft enquiry with new details.
     *
     * @param enquiryID      the ID of the draft enquiry to edit
     * @param studentID      the ID of the student editing the draft
     * @param newMessage     the new message content for the draft enquiry
     * @param isDraft        {@code true} if the edited enquiry remains a draft, {@code false} if it is submitted
     * @return {@code true} if the edit is successful, {@code false} otherwise
     */
    public boolean editDraftEnquiry(int enquiryID, String studentID, String newMessage, boolean isDraft);

    /**
     * Deletes the specified draft enquiry created by the student.
     *
     * @param enquiryID the ID of the draft enquiry to delete
     * @param studentID the ID of the student deleting the draft enquiry
     * @return {@code true} if the deletion is successful, {@code false} otherwise
     */
    public boolean deleteDraftEnquiry(int enquiryID, String studentID);
    
}
