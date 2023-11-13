package interfaces;

import java.util.List;

import models.Camp;
import models.Staff;
import models.Student;
import models.Suggestion;

public interface ISuggestionService {
    /**
     * Retrieves a list of all suggestions related to camps.
     * 
     * This method returns a list containing all the suggestions made by the camp
     * committee for various camps. Each element in the list represents a
     * {@link Suggestion} object. The list may be empty if there are no suggestions recorded.
     * 
     * @return a {@link List} of {@link Suggestion} objects representing all camp suggestions
     * 
     */
    public List<Suggestion> getAllCampSuggestion();

    /**
     * Approves suggestions to changes in camp details from camp committee.
     *
     * @param suggestion the {@link Suggestion} object representing the proposed change
     * @param camp       the {@link Camp} object for which the suggestion is made
     * @return true if the suggestion was approved successfully, false otherwise
     */
    public boolean approveCampSuggestion(Suggestion suggestion, Camp camp);

    /**
     * Retrieves a list of suggestions made by the student.
     *
     * @param student the {@link Student} object
     * @return a {@link List} of {@link Suggestion} objects representing the student's suggestions
     */
    public List<Suggestion> getStudentSuggestions(Student student);


    /**
     * Creates a suggestion request from the student to a staff member for a specific camp.
     *
     * @param sender the {@link Student} object sending the suggestion
     * @param receiver the {@link Staff} object receiving the suggestion
     * @param camp the {@link Camp} object related to the suggestion
     * @return true if the suggestion request was created successfully, false otherwise
     */
    public boolean createSuggestionRequest(Student sender, Staff receiver, Camp camp);
}
