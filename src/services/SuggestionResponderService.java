package services;

import java.util.Map;
import java.util.stream.Collectors;

import enums.MessageStatus;
import models.Camp;
import models.Suggestion;
import stores.DataStore;

public class SuggestionResponderService {

    public Map<Integer, Suggestion> getAllSuggestionsForCamp(Camp camp) {
		Map<Integer, Suggestion> suggestionData = DataStore.getSuggestionData();
		String campName = camp.getCampInformation().getCampName();

		// Returns a Map<Integer, Enquiry> with Enquiries having the specified Camp name.
		Map<Integer, Suggestion> suggestionsForCampMap = suggestionData.values().stream()
				.filter(suggestion -> campName.equals(suggestion.getCampName()))
				.collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));

		return suggestionsForCampMap;
	}

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
