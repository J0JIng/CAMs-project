package services;

import java.util.Map;
import java.util.stream.Collectors;

import interfaces.ISuggestionResponderService;
import enums.MessageStatus;
import models.Camp;
import models.Suggestion;
import stores.DataStore;

/**
 * Service class for managing and responding to suggestions related to a camp.
 * Implements the {@link ISuggestionResponderService} interface.
 */
public class SuggestionResponderService implements ISuggestionResponderService {

	/**
     * Default constructor for the SuggestionResponderService class.
     */
    public SuggestionResponderService(){
    }

    /**
     * Retrieves all suggestions for a specific camp.
     *
     * @param camp The camp for which suggestions are to be retrieved.
     * @return A Map containing suggestions for the specified camp, keyed by suggestion ID.
     */
    @Override
    public Map<Integer, Suggestion> getAllSuggestionsForCamp(Camp camp) {
		Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData();
		String campName = camp.getCampInformation().getCampName();

		// Returns a Map<Integer, Enquiry> with Enquiries having the specified Camp name.
		Map<Integer, Suggestion> suggestionsForCampMap = suggestionData.values().stream()
				.filter(suggestion -> campName.equals(suggestion.getCampName()))
				.collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));

		return suggestionsForCampMap;
	}

    /**
     * Reviews a suggestion and updates its status (Accepted/Rejected).
     *
     * @param suggestionID    The ID of the suggestion to be reviewed.
     * @param suggestionStatus The status to be set for the suggestion (true for Accepted, false for Rejected).
     * @return True if the suggestion is successfully reviewed and updated, false otherwise.
     */
    @Override
    public boolean reviewSuggestion(int suggestionID, boolean suggestionStatus) {
        Map<Integer,Suggestion> suggestionData = DataStore.getSuggestionData();
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
