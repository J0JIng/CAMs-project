package interfaces;

import java.util.Map;

import models.Camp;
import models.Suggestion;

public interface ISuggestionResponderService {

    public Map<Integer, Suggestion> getAllSuggestionsForCamp(Camp camp);
    
    public boolean reviewSuggestion(int suggestionID, boolean suggestionStatus);
}
