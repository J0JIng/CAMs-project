package models;

import enums.MessageStatus;

/**
 * A class that contains the model of a suggestion made for a camp.
 */
public class Suggestion {
	
	/**
     * The unique identifier (ID) of the suggestion.
     */
    private int suggestionID;
    
    /**
     * The name of the camp associated with the suggestion.
     */
    private String campName;
    
    /**
     * The identifier (ID) of the sender of the suggestion.
     */
    private String senderID; 
    
    /**
     * The status of the suggestion (e.g., DRAFT, SENT, PENDING).
     */
    private MessageStatus suggestionStatus;
    
    /**
     * The string content of the suggestion message.
     */
    private String suggestionMessage;

    /**
     * Constructs a suggestion with the provided details.
     *
     * @param suggestionID      The unique identifier (ID) for the suggestion.
     * @param campName          The name of the camp associated with the suggestion.
     * @param senderID          The identifier of the sender of the suggestion.
     * @param suggestionMessage The string content of the suggestion message.
     */
    public Suggestion(int suggestionID, String campName, String senderID, String suggestionMessage) {
        this.suggestionID = suggestionID;
        this.campName = campName;
        this.senderID = senderID;
        this.suggestionMessage = suggestionMessage;
        this.suggestionStatus = MessageStatus.DRAFT;
    }

    /**
     * Gets the unique identifier of the suggestion.
     *
     * @return The suggestion's unique identifier.
     */
    public int getSuggestionID() {
        return suggestionID;
    }

    /**
     * Sets the unique identifier for the suggestion.
     *
     * @param suggestionID The new unique identifier for the suggestion.
     */
    public void setSuggestionID(int suggestionID) {
        this.suggestionID = suggestionID;
    }

    /**
     * Gets the name of the camp associated with the suggestion.
     *
     * @return The name of the camp.
     */
    public String getCampName() {
        return campName;
    }

    /**
     * Sets the name of the camp associated with the suggestion.
     *
     * @param campName The new name of the camp.
     */
    public void setCampName(String campName) {
         this.campName = campName;
    }

    /**
     * Gets the identifier of the sender of the suggestion.
     *
     * @return The sender's identifier.
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * Sets the identifier of the sender of the suggestion.
     *
     * @param senderID The new sender's identifier.
     */
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    /**
     * Gets the status of the suggestion (e.g., DRAFT, SENT).
     *
     * @return The status of the suggestion.
     */
    public MessageStatus getSuggestionStatus() {
        return suggestionStatus;
    }
    
    /**
     * Sets the status of the suggestion (e.g., DRAFT, SENT).
     *
     * @param suggestionStatus The new status of the suggestion.
     */
    public void setSuggestionStatus(MessageStatus suggestionStatus) {
        this.suggestionStatus = suggestionStatus;
    }

    /**
     * Gets the content of the suggestion message.
     *
     * @return The content of the suggestion message.
     */
    public String getSuggestionMessage() {
        return suggestionMessage;
    }

    /**
     * Sets the content of the suggestion message.
     *
     * @param suggestionMessage The new content of the suggestion message.
     */
    public void setSuggestionMessage(String suggestionMessage) {
        this.suggestionMessage = suggestionMessage;
    }
}
