package models;

import enums.MessageStatus;

public class Suggestion {
    private int suggestionID;
    private String campName;
    private String senderID; 
    private MessageStatus suggestionStatus;
    private String suggestionMessage;

    public Suggestion(int suggestionID, String campName, String senderID, MessageStatus suggestionStatus, String suggestionMessage) {
        this.suggestionID = suggestionID;
        this.campName = campName;
        this.senderID = senderID;
        this.suggestionMessage = suggestionMessage;
        this.suggestionStatus = MessageStatus.DRAFT;
    }

    
    public int getSuggestionID() {
        return suggestionID;
    }

    public void setSuggestionID(int suggestionID) {
        this.suggestionID = suggestionID;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
         this.campName = campName;
    }

    public String getSenderID() {
        return senderID;
    }
       
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public MessageStatus getSuggestionStatus() {
        return suggestionStatus;
    }
      public void setSuggestionStatus(MessageStatus suggestionStatus) {
        this.suggestionStatus = suggestionStatus;
    }

    public String getSuggestionMessage() {
        return suggestionMessage;
    }

    public void setSuggestionMessage(String suggestionMessage) {
        this.suggestionMessage = suggestionMessage;
    }
}
