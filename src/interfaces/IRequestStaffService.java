package interfaces;

import java.util.List;

import models.Student;
import models.Camp;
import models.Enquiry;
import models.Suggestion;

public interface IRequestStaffService {

   

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
     * Handles enquiries from students related to the specified camp.
     *
     * @param enquiry the {@link Enquiry} object representing the student's enquiry
     * @param camp    the {@link Student} object related to the enquiry
     * @return true if the enquiry was handled successfully, false otherwise
     */
    public boolean handleCampEnquiry(Enquiry enquiry, Student student);

    /**
     * Approves suggestions to changes in camp details from camp committee.
     *
     * @param suggestion the {@link Suggestion} object representing the proposed change
     * @param camp       the {@link Camp} object for which the suggestion is made
     * @return true if the suggestion was approved successfully, false otherwise
     */
    public boolean approveCampSuggestion(Suggestion suggestion, Camp camp);

    /**
     * Generates a report of the list of students attending a specific camp.
     *
     * @param camp    the {@link Camp} object for which to generate the report
     * @param filters filters for the report (e.g., attendee, camp committee)
     * @return true if the report is created successfully, false otherwise
     */
    public boolean generateCampAttendeeReport(Camp camp, String filters);

    /**
     * Generates a performance report of camp committee members for a specific camp.
     *
     * @param camp the {@link Camp} object for which to generate the performance report
     * @return true if the report is created successfully, false otherwise
     */
    public boolean generateCampCommitteePerformanceReport(Camp camp);
    
} 