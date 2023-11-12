package services;

import models.Enquiry;
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
        int suggestionID = Math.abs(UUID.randomUUID().hashCode());
        Suggestion suggestion = new Suggestion(suggestionID, senderID, campName, suggestionDetails);
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

    public Map<Integer, Suggestion> viewDraftSuggestion(String studentID) {
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.DRAFT)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }
    public Map<Integer, Suggestion> viewSubmittedSuggestion(String studentID) {
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.PENDING)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }
    public Map<Integer, Suggestion> viewAcceptedSuggestion(String studentID) {
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.ACCEPTED)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }
    public Map<Integer, Suggestion> viewRejectedSuggestion(String studentID) {
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.REJECTED)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }

    public boolean editSuggestion(int suggestionID, String senderID, String newDetails, boolean isDraft) {
        if (suggestionData.containsKey(suggestionID)) {
            Suggestion suggestion = suggestionData.get(suggestionID);
            if (suggestion.getSenderID().equals(senderID) && suggestion.getSuggestionStatus() == MessageStatus.DRAFT) {
                suggestion.setSuggestionMessage(newDetails);
                if (!isDraft) {
                    suggestion.setSuggestionStatus(MessageStatus.PENDING);
                }
                DataStore.setSuggestionData(suggestionData);
                return true;
            }
        }
        return false;
    }

    public boolean deleteDraftSuggestion(int suggestionID, String senderID) {
        Suggestion suggestion = suggestionData.get(suggestionID);
        if (suggestion != null && suggestion.getSenderID().equals(senderID) && suggestion.getSuggestionStatus() == MessageStatus.DRAFT) {
            suggestionData.remove(suggestionID);
            DataStore.setSuggestionData(suggestionData);
            return true;
        }
        return false;
    }

    public boolean reviewSuggestion(int suggestionID, boolean suggestionStatus) {
        Suggestion suggestion = suggestionData.get(suggestionID);
        if (suggestion != null) {
            if (suggestionStatus) {
                suggestion.setSuggestionStatus(MessageStatus.ACCEPTED);
            } else {
                suggestion.setSuggestionStatus(MessageStatus.REJECTED);
            }
            DataStore.setSuggestionData(suggestionData);
            return true;
        }
        return false;
    }
}
