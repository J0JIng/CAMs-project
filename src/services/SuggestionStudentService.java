package services;

import models.Suggestion;
import stores.DataStore;
import enums.MessageStatus;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class SuggestionStudentService {
    private Map<Integer, Suggestion> suggestionData;

    public SuggestionStudentService() {
        suggestionData = DataStore.getSuggestionData(); 
    }

    public int submitSuggestion(String senderID, String campName, String suggestionDetails, boolean isDraft) {
        int suggestionID = UUID.randomUUID().hashCode();
        Suggestion suggestion = new Suggestion(suggestionID, campName, senderID, MessageStatus.DRAFT, suggestionDetails);
        if (isDraft) {
            suggestion.setSuggestionStatus(MessageStatus.DRAFT);
        } else {
            // Set other status for non-draft enquiries (e.g., PENDING)
            suggestion.setSuggestionStatus(MessageStatus.PENDING);
        }
        suggestionData.put(suggestionID, suggestion);
        DataStore.setSuggestionData(suggestionData);
        return suggestionID;
    }

    public boolean editSuggestion(int suggestionID, String senderID, String newDetails) {
        Suggestion suggestion = suggestionData.get(suggestionID);
        if (suggestion != null && suggestion.getSenderID().equals(senderID) && suggestion.getSuggestionStatus() == MessageStatus.DRAFT) {
            suggestion.setSuggestionMessage(newDetails);
            DataStore.setSuggestionData(suggestionData);
            return true;
        }
        return false;
    }

    // public boolean confirmSuggestion(int suggestionID, String senderID, String campName, String suggestionDetails, boolean isDraft) {
    //     if (suggestionData.containsKey(suggestionID)) {
    //         Suggestion enquiry = Suggestion(suggestionID, senderID, campName, enquiryMessage);
    //         if (isDraft) {
    //             enquiry.setEnquiryStatus(MessageStatus.DRAFT);
    //         } else {
    //             enquiry.setEnquiryStatus(MessageStatus.PENDING);
    //         }
    //         enquiryData.put(enquiryID, enquiry);
    //         DataStore.setEnquiryData(enquiryData);
    //         return true;
    //     }
    //     return false;
    // }

    public boolean deleteSuggestion(int suggestionID, String senderID) {
        Suggestion suggestion = suggestionData.get(suggestionID);
        if (suggestion != null && suggestion.getSenderID().equals(senderID) && suggestion.getSuggestionStatus() == MessageStatus.DRAFT) {
            suggestionData.remove(suggestionID);
            DataStore.setSuggestionData(suggestionData);
            return true;
        }
        return false;
    }

    public boolean reviewSuggestion(int suggestionID, MessageStatus suggestionStatus) {
        Suggestion suggestion = suggestionData.get(suggestionID);
        if (suggestion != null) {
            suggestion.setSuggestionStatus(suggestionStatus);
            DataStore.setSuggestionData(suggestionData);
            return true;
        }
        return false;
    }
}
