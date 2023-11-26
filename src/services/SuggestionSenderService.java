package services;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import interfaces.ISuggestionSenderService;
import enums.MessageStatus;
import models.Suggestion;
import stores.DataStore;

/**
 * {@link SuggestionSenderService} class for managing the sending, editing, and retrieval of suggestions.
 * Implements the {@link ISuggestionSenderService} interface.
 */
public class SuggestionSenderService implements ISuggestionSenderService {

	/**
     * Default constructor for the {@link SuggestionSenderService} class.
     */
    public SuggestionSenderService() {
    }

    /**
     * Retrieves draft suggestions of a specific student.
     *
     * @param studentID The ID of the student whose draft suggestions are to be retrieved.
     * @return A Map containing draft suggestions submitted by the specified student, keyed by suggestion ID.
     */
    @Override
    public Map<Integer, Suggestion> getDraftSuggestion(String studentID) {
        Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData(); 
    	return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.DRAFT)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }

    /**
     * Retrieves submitted suggestions submitted by a specific student.
     *
     * @param studentID The ID of the student whose submitted suggestions are to be retrieved.
     * @return A Map containing submitted suggestions submitted by the specified student, keyed by suggestion ID.
     */
    @Override
    public Map<Integer, Suggestion> getSubmittedSuggestion(String studentID) {
        Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData(); 
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.PENDING)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }

    /**
     * Retrieves accepted suggestions submitted by a specific student.
     *
     * @param studentID The ID of the student whose accepted suggestions are to be retrieved.
     * @return A Map containing accepted suggestions submitted by the specified student, keyed by suggestion ID.
     */
    @Override
    public Map<Integer, Suggestion> getAcceptedSuggestion(String studentID) {
        Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData(); 
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.ACCEPTED)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }

    /**
     * Retrieves rejected suggestions submitted by a specific student.
     *
     * @param studentID The ID of the student whose rejected suggestions are to be retrieved.
     * @return A Map containing rejected suggestions submitted by the specified student, keyed by suggestion ID.
     */
    @Override
    public Map<Integer, Suggestion> getRejectedSuggestion(String studentID) {
        Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData(); 
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.REJECTED)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }

    /**
     * Submits a new suggestion or updates an existing suggestion.
     *
     * @param senderID         The ID of the student submitting the suggestion.
     * @param campName         The name of the camp related to the suggestion.
     * @param suggestionDetails The details of the suggestion.
     * @param isDraft          True if the suggestion is a draft, false otherwise.
     * @return The ID of the submitted or updated suggestion.
     */
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

    /**
     * Edits an existing draft suggestion.
     *
     * @param suggestionID The ID of the suggestion to be edited.
     * @param senderID     The ID of the student submitting the suggestion.
     * @param newDetails   The new details for the suggestion.
     * @param isDraft      True if the suggestion should remain a draft after editing, false otherwise.
     * @return True if the suggestion is successfully edited, false otherwise.
     */
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

    /**
     * Deletes a draft suggestion.
     *
     * @param suggestionID The ID of the draft suggestion to be deleted.
     * @param senderID     The ID of the student submitting the suggestion.
     * @return True if the draft suggestion is successfully deleted, false otherwise.
     */
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