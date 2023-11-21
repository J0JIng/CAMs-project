package services;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import interfaces.ISuggestionSenderService;
import enums.MessageStatus;
import models.Suggestion;
import stores.DataStore;

public class SuggestionSenderService implements ISuggestionSenderService {

    public SuggestionSenderService() {
    }

    @Override
    public Map<Integer, Suggestion> getDraftSuggestion(String studentID) {
        Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData(); 
    	return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.DRAFT)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }

    @Override
    public Map<Integer, Suggestion> getSubmittedSuggestion(String studentID) {
        Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData(); 
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.PENDING)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }

    @Override
    public Map<Integer, Suggestion> getAcceptedSuggestion(String studentID) {
        Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData(); 
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.ACCEPTED)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }

    @Override
    public Map<Integer, Suggestion> getRejectedSuggestion(String studentID) {
        Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData(); 
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.REJECTED)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }

    @Override
    public int submitSuggestion(String senderID, String campName, String suggestionDetails, boolean isDraft) {
        Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData(); 
        int suggestionID = Math.abs(UUID.randomUUID().hashCode());
        Suggestion suggestion = new Suggestion(suggestionID, campName, senderID, suggestionDetails);
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

    @Override
    public boolean editSuggestion(int suggestionID, String senderID, String newDetails, boolean isDraft) {
        Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData(); 
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

    @Override
    public boolean deleteDraftSuggestion(int suggestionID, String senderID) {
        Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData(); 
        Suggestion suggestion = suggestionData.get(suggestionID);
        if (suggestion != null && suggestion.getSenderID().equals(senderID) && suggestion.getSuggestionStatus() == MessageStatus.DRAFT) {
            suggestionData.remove(suggestionID);
            DataStore.setSuggestionData(suggestionData);
            return true;
        }
        return false;
    }
}