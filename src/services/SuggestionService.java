package services;

import models.Suggestion;
import stores.DataStore;
import enums.MessageStatus;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class SuggestionService {
    private Map<Integer, Suggestion> suggestionData;

    public SuggestionService() {
        suggestionData = DataStore.getSuggestionData(); 
    }

    public boolean submitSuggestion(String senderID, String campName, String suggestionDetails) {
        int suggestionID = UUID.randomUUID().hashCode();
        Suggestion suggestion = new Suggestion(suggestionID, campName, senderID, MessageStatus.PENDING, suggestionDetails);
        suggestion.setSuggestionStatus(MessageStatus.PENDING);
        suggestionData.put(suggestionID, suggestion);
        DataStore.setSuggestionData(suggestionData);
        return true;
    }

    public boolean editSuggestion(int suggestionID, String senderID, String newDetails) {
        Suggestion suggestion = suggestionData.get(suggestionID);
        if (suggestion != null && suggestion.getSenderID().equals(senderID) && suggestion.getSuggestionStatus() == MessageStatus.DRAFT) {
            suggestion.setSuggestionDetails(newDetails);
            DataStore.setSuggestionData(suggestionData);
            return true;
        }
        return false;
    }

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
