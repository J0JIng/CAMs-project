package services;

import java.util.Map;
import java.util.stream.Collectors;

<<<<<<< HEAD
import interfaces.ISuggestionResponderService;
=======
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
import enums.MessageStatus;
import models.Camp;
import models.Suggestion;
import stores.DataStore;

<<<<<<< HEAD
/**
 * {@link SuggestionResponderService} class for managing and responding to suggestions related to a camp.
 * Implements the {@link ISuggestionResponderService} interface.
 */
public class SuggestionResponderService implements ISuggestionResponderService {

	/**
     * Default constructor for the {@link SuggestionResponderService} class.
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
=======
public class SuggestionResponderService {

>>>>>>> eefd642a51ec84d47395937eac077761227004ce
    public Map<Integer, Suggestion> getAllSuggestionsForCamp(Camp camp) {
		Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData();
		String campName = camp.getCampInformation().getCampName();

		// Returns a Map<Integer, Enquiry> with Enquiries having the specified Camp name.
		Map<Integer, Suggestion> suggestionsForCampMap = suggestionData.values().stream()
<<<<<<< HEAD
				.filter(suggestion -> campName.equals(suggestion.getCampName()) && suggestion.getSuggestionStatus() != MessageStatus.DRAFT)
=======
				.filter(suggestion -> campName.equals(suggestion.getCampName()))
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
				.collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));

		return suggestionsForCampMap;
	}

<<<<<<< HEAD
    /**
     * Reviews a suggestion and updates its status (Accepted/Rejected).
     *
     * @param suggestionID    The ID of the suggestion to be reviewed.
     * @param suggestionStatus The status to be set for the suggestion (true for Accepted, false for Rejected).
     * @return True if the suggestion is successfully reviewed and updated, false otherwise.
     */
    @Override
=======
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
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
<<<<<<< HEAD
=======

>>>>>>> eefd642a51ec84d47395937eac077761227004ce
}
