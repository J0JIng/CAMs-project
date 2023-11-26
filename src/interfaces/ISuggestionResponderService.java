package interfaces;

import java.util.Map;

import models.Camp;
import models.Suggestion;

/**
 * The {@link ISuggestionResponderService} interface defines methods for responding to suggestions related to a specific camp.
 */
public interface ISuggestionResponderService {

	/**
     * Retrieves all suggestions for a specific camp.
     *
     * @param camp the {@link Camp} object for which to retrieve suggestions
     * @return a {@link Map} of {@link Suggestion} objects with their IDs as keys
     */
    public Map<Integer, Suggestion> getAllSuggestionsForCamp(Camp camp);
    
    /**
     * Reviews a suggestion and updates its status.
     *
     * @param suggestionID     the ID of the suggestion to be reviewed
     * @param suggestionStatus the status of the suggestion (true for accepted, false for rejected)
     * @return true if the suggestion is reviewed successfully, false otherwise
     */
    public boolean reviewSuggestion(int suggestionID, boolean suggestionStatus);
}
