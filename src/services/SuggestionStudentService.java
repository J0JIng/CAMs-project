package services;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
import models.Enquiry;
>>>>>>> hq
=======
>>>>>>> parent of b566f67 (Resolve merge conflicts from hq branch)
=======
>>>>>>> parent of b566f67 (Resolve merge conflicts from hq branch)
import models.Suggestion;
import stores.DataStore;
import enums.MessageStatus;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class SuggestionStudentService {
    private Map<Integer, Suggestion> suggestionData;

    public SuggestionStudentService() {
        suggestionData = DataStore.getSuggestionData(); 
    }

    public int submitSuggestion(String senderID, String campName, String suggestionDetails, boolean isDraft) {
<<<<<<< HEAD
        int suggestionID = UUID.randomUUID().hashCode();
        Suggestion suggestion = new Suggestion(suggestionID, campName, senderID, MessageStatus.DRAFT, suggestionDetails);
<<<<<<< HEAD
<<<<<<< HEAD
=======
        int suggestionID = Math.abs(UUID.randomUUID().hashCode());
        Suggestion suggestion = new Suggestion(suggestionID, senderID, campName, suggestionDetails);
>>>>>>> hq
=======
>>>>>>> parent of b566f67 (Resolve merge conflicts from hq branch)
=======
>>>>>>> parent of b566f67 (Resolve merge conflicts from hq branch)
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

<<<<<<< HEAD
    public boolean editSuggestion(int suggestionID, String senderID, String newDetails) {
        Suggestion suggestion = suggestionData.get(suggestionID);
        if (suggestion != null && suggestion.getSenderID().equals(senderID) && suggestion.getSuggestionStatus() == MessageStatus.DRAFT) {
            suggestion.setSuggestionMessage(newDetails);
            DataStore.setSuggestionData(suggestionData);
            return true;
<<<<<<< HEAD
<<<<<<< HEAD
=======
    public Map<Integer, Suggestion> viewDraftSuggestion(String studentID) {
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.DRAFT)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }
    public Map<Integer, Suggestion> viewSubmittedSuggestion(String studentID) {
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.PENDING)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }
    public Map<Integer, Suggestion> viewAcceptedSuggestion(String studentID) {
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.ACCEPTED)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }
    public Map<Integer, Suggestion> viewRejectedSuggestion(String studentID) {
        return suggestionData.values().stream()
                .filter(suggestion -> suggestion.getSenderID().equals(studentID) && suggestion.getSuggestionStatus() == MessageStatus.REJECTED)
                .collect(Collectors.toMap(Suggestion::getSuggestionID, suggestion -> suggestion));
    }

    public boolean editSuggestion(int suggestionID, String senderID, String newDetails, boolean isDraft) {
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
>>>>>>> hq
=======
>>>>>>> parent of b566f67 (Resolve merge conflicts from hq branch)
=======
>>>>>>> parent of b566f67 (Resolve merge conflicts from hq branch)
        }
        return false;
    }

<<<<<<< HEAD
    // public boolean confirmSuggestion(int suggestionID, String senderID, String campName, String suggestionDetails, boolean isDraft) {
    //     if (suggestionData.containsKey(suggestionID)) {
    //         Suggestion enquiry = Suggestion(suggestionID, senderID, campName, enquiryMessage);
    //         if (isDraft) {
    //             enquiry.setEnquiryStatus(MessageStatus.DRAFT);
    //         } else {
    //             enquiry.setEnquiryStatus(MessageStatus.PENDING);
    //         }
    //         enquiryData.put(enquiryID, enquiry);
    //         DataStore.setEnquiryData(enquiryData);
    //         return true;
    //     }
    //     return false;
    // }

    public boolean deleteSuggestion(int suggestionID, String senderID) {
<<<<<<< HEAD
<<<<<<< HEAD
=======
    public boolean deleteDraftSuggestion(int suggestionID, String senderID) {
>>>>>>> hq
=======
>>>>>>> parent of b566f67 (Resolve merge conflicts from hq branch)
=======
>>>>>>> parent of b566f67 (Resolve merge conflicts from hq branch)
        Suggestion suggestion = suggestionData.get(suggestionID);
        if (suggestion != null && suggestion.getSenderID().equals(senderID) && suggestion.getSuggestionStatus() == MessageStatus.DRAFT) {
            suggestionData.remove(suggestionID);
            DataStore.setSuggestionData(suggestionData);
            return true;
        }
        return false;
    }

<<<<<<< HEAD
    public boolean reviewSuggestion(int suggestionID, MessageStatus suggestionStatus) {
        Suggestion suggestion = suggestionData.get(suggestionID);
        if (suggestion != null) {
            suggestion.setSuggestionStatus(suggestionStatus);
<<<<<<< HEAD
<<<<<<< HEAD
=======
    public boolean reviewSuggestion(int suggestionID, boolean suggestionStatus) {
        Suggestion suggestion = suggestionData.get(suggestionID);
        if (suggestion != null) {
            if (suggestionStatus) {
                suggestion.setSuggestionStatus(MessageStatus.ACCEPTED);
            } else {
                suggestion.setSuggestionStatus(MessageStatus.REJECTED);
            }
>>>>>>> hq
=======
>>>>>>> parent of b566f67 (Resolve merge conflicts from hq branch)
=======
>>>>>>> parent of b566f67 (Resolve merge conflicts from hq branch)
            DataStore.setSuggestionData(suggestionData);
            return true;
        }
        return false;
    }
}
