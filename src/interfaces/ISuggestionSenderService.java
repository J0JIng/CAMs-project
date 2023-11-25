package interfaces;

import java.util.Map;

import models.Suggestion;

/**
 * The {@link ISuggestionSenderService} interface defines methods for managing suggestions sent by a student.
 */
public interface ISuggestionSenderService {
    
	/**
     * Retrieves draft suggestions submitted by a student.
     *
     * @param studentID the ID of the student
     * @return a {@link Map} of {@link Suggestion} objects representing draft suggestions with their IDs as keys
     */
    public Map<Integer, Suggestion> getDraftSuggestion(String studentID);

    /**
     * Retrieves submitted suggestions by a student.
     *
     * @param studentID the ID of the student
     * @return a {@link Map} of {@link Suggestion} objects representing submitted suggestions with their IDs as keys
     */
    public Map<Integer, Suggestion> getSubmittedSuggestion(String studentID);

    /**
     * Retrieves accepted suggestions by a student.
     *
     * @param studentID the ID of the student
     * @return a {@link Map} of {@link Suggestion} objects representing accepted suggestions with their IDs as keys
     */
    public Map<Integer, Suggestion> getAcceptedSuggestion(String studentID);

    /**
     * Retrieves rejected suggestions by a student.
     *
     * @param studentID the ID of the student
     * @return a {@link Map} of {@link Suggestion} objects representing rejected suggestions with their IDs as keys
     */
    public Map<Integer, Suggestion> getRejectedSuggestion(String studentID);

    /**
     * Submits a suggestion.
     *
     * @param senderID         the ID of the student submitting the suggestion
     * @param campName         the name of the camp related to the suggestion
     * @param suggestionDetails the details of the suggestion
     * @param isDraft          true if the suggestion is a draft, false otherwise
     * @return the ID of the submitted suggestion
     */
    public int submitSuggestion(String senderID, String campName, String suggestionDetails, boolean isDraft);

    /**
     * Edits a draft suggestion.
     *
     * @param suggestionID the ID of the suggestion to be edited
     * @param senderID     the ID of the student editing the suggestion
     * @param newDetails   the updated details of the suggestion
     * @param isDraft      true if the suggestion remains a draft, false otherwise
     * @return true if the suggestion is edited successfully, false otherwise
     */
    public boolean editSuggestion(int suggestionID, String senderID, String newDetails, boolean isDraft);

    /**
     * Deletes a draft suggestion.
     *
     * @param suggestionID the ID of the draft suggestion to be deleted
     * @param senderID     the ID of the student deleting the draft suggestion
     * @return true if the draft suggestion is deleted successfully, false otherwise
     */
    public boolean deleteDraftSuggestion(int suggestionID, String senderID);

}
