package models;

import enums.MessageStatus;

/**
 * Represents an enquiry in the system, containing information about the sender, responder, message content,
 * and response to the enquiry.
 */
public class Enquiry {

    /**
     * The unique identifier for the enquiry.
     */
    private int enquiryID;
    
    /**
     * The name of the camp associated with the enquiry.
     */
    private String campName;

    /**
     * The ID of the sender of the enquiry.
     */
    private String senderID;

    /**
     * The ID of the responder, if applicable.
     */
    private String responderID;

    /**
     * The status of the enquiry message (e.g., DRAFT, SENT).
     */
    private MessageStatus enquiryStatus;

    /**
     * The content of the enquiry message.
     */
    private String enquiryMessage;

    /**
     * The response to the enquiry, if received.
     */
    private String enquiryResponse;

    /**
     * Constructs a new Enquiry object with the specified attributes.
     *
     * @param enquiryID      The unique identifier for the enquiry.
     * @param senderID       The ID of the sender of the enquiry.
     * @param campName       The name of the camp associated with the enquiry.
     * @param message        The content of the enquiry message.
     */
    public Enquiry(int enquiryID, String senderID, String campName, String message) {
        this.enquiryID = enquiryID;
        this.senderID = senderID;
        this.campName = campName;
        this.enquiryMessage = message;
        this.enquiryStatus = MessageStatus.DRAFT;
        this.enquiryResponse = null;
        this.responderID = null;
    }

    /**
     * Gets the unique identifier for the enquiry.
     *
     * @return The unique identifier for the enquiry.
     */
    public int getEnquiryID() {
        return this.enquiryID;
    }

    /**
     * Sets the unique identifier for the enquiry.
     *
     * @param enquiryID The unique identifier for the enquiry.
     */
    public void setEnquiryID(int enquiryID) {
        this.enquiryID = enquiryID;
    }

    /**
     * Gets the ID of the sender of the enquiry.
     *
     * @return The ID of the sender of the enquiry.
     */
    public String getSenderID() {
        return this.senderID;
    }

    /**
     * Sets the ID of the sender of the enquiry.
     *
     * @param senderID The ID of the sender of the enquiry.
     */
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    /**
     * Gets the name of the camp associated with the enquiry.
     *
     * @return The name of the camp associated with the enquiry.
     */
    public String getCampName() {
        return this.campName;
    }

    /**
     * Sets the name of the camp associated with the enquiry.
     *
     * @param campName The name of the camp associated with the enquiry.
     */
    public void setCampName(String campName) {
        this.campName = campName;
    }

    /**
     * Gets the ID of the responder, if applicable.
     *
     * @return The ID of the responder.
     */
    public String getResponderID() {
        return this.responderID;
    }

    /**
     * Sets the ID of the responder, if applicable.
     *
     * @param responderID The ID of the responder.
     */
    public void setResponderID(String responderID) {
        this.responderID = responderID;
    }

    /**
     * Gets the status of the enquiry message.
     *
     * @return The status of the enquiry message.
     */
    public MessageStatus getEnquiryStatus() {
        return this.enquiryStatus;
    }

    /**
     * Sets the status of the enquiry message.
     *
     * @param enquiryStatus The status of the enquiry message.
     */
    public void setEnquiryStatus(MessageStatus enquiryStatus) {
        this.enquiryStatus = enquiryStatus;
    }

    /**
     * Gets the content of the enquiry message.
     *
     * @return The content of the enquiry message.
     */
    public String getEnquiryMessage() {
        return this.enquiryMessage;
    }

    /**
     * Sets the content of the enquiry message.
     *
     * @param enquiryMessage The content of the enquiry message.
     */
    public void setEnquiryMessage(String enquiryMessage) {
        this.enquiryMessage = enquiryMessage;
    }

    /**
     * Gets the response to the enquiry, if received.
     *
     * @return The response to the enquiry.
     */
    public String getEnquiryResponse() {
        return this.enquiryResponse;
    }

    /**
     * Sets the response to the enquiry.
     *
     * @param enquiryResponse The response to the enquiry.
     */
    public void setEnquiryResponse(String enquiryResponse) {
        this.enquiryResponse = enquiryResponse;
    }
}
