package interfaces;

import java.util.List;
import models.User;
import models.Staff;
import models.Student;
import models.Camp;
import models.Enquiry;
import models.Suggestion;

/**
 * The {@link IRequestStudentService} interface defines a contract for handling student-related requests.
 */
public interface IRequestStudentService {

    /**
     * Retrieves a list of enquiries submitted by the student.
     *
     * @param student the {@link Student} object
     * @return a {@link List} of {@link Enquiry} objects representing the student's enquiries
     */
    public List<Enquiry> getStudentEnquiries(Student student);

    /**
     * Retrieves a list of suggestions made by the student.
     *
     * @param student the {@link Student} object
     * @return a {@link List} of {@link Suggestion} objects representing the student's suggestions
     */
    public List<Suggestion> getStudentSuggestions(Student student);

    /**
     * Creates an enquiry request from the student to the personnel in charge of the camp.
     *
     * @param sender the {@link Student} object sending the enquiry
     * @param receiver the {@link User} object receiving the enquiry (personnel in charge of the camp)
     * @param camp the {@link Camp} object related to the enquiry
     * @return true if the enquiry request was created successfully, false otherwise
     */
    public boolean createEnquiryRequest(Student sender, User receiver, Camp camp);

    /**
     * Creates a suggestion request from the student to a staff member for a specific camp.
     *
     * @param sender the {@link Student} object sending the suggestion
     * @param receiver the {@link Staff} object receiving the suggestion
     * @param camp the {@link Camp} object related to the suggestion
     * @return true if the suggestion request was created successfully, false otherwise
     */
    public boolean createSuggestionRequest(Student sender, Staff receiver, Camp camp);

    /**
     * Camp committee member handles an enquiry from the student related to the specified camp.
     *
     * @param enquiry the {@link Enquiry} object representing the student's enquiry
     * @param camp the {@link Camp} object related to the enquiry
     * @return true if the enquiry was handled successfully, false otherwise
     */
    public boolean handleCampEnquiry(Enquiry enquiry, Camp camp);
}
