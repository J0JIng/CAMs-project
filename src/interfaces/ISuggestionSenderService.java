package interfaces;

import java.util.Map;

import models.Suggestion;

public interface ISuggestionSenderService {
    
    public Map<Integer, Suggestion> getDraftSuggestion(String studentID);

    public Map<Integer, Suggestion> getSubmittedSuggestion(String studentID);

    public Map<Integer, Suggestion> getAcceptedSuggestion(String studentID);

    public Map<Integer, Suggestion> getRejectedSuggestion(String studentID);

    public int submitSuggestion(String senderID, String campName, String suggestionDetails, boolean isDraft);

    public boolean editSuggestion(int suggestionID, String senderID, String newDetails, boolean isDraft);

    public boolean deleteDraftSuggestion(int suggestionID, String senderID);

}
