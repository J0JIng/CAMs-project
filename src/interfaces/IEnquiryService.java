package interfaces;

import java.util.List;

import models.Camp;
import models.Enquiry;
import models.Student;
import models.User;

public interface IEnquiryService {
    /**
     * Retrieves a list of enquiries submitted by the student.
     *
     * @param student the {@link Student} object
     * @return a {@link List} of {@link Enquiry} objects representing the student's enquiries
     */
    public List<Enquiry> getStudentEnquiries(Student student);

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
     * Retrieves a list of all enquiries related to camps.
     * 
     * This method returns a list containing all the enquiries submitted by students
     * for various camps. Each element in the list represents an {@link Enquiry} object.
     * The list may be empty if there are no enquiries recorded.
     * 
     * @return a {@link List} of {@link Enquiry} objects representing all camp enquiries
     *
     */
    public List<Enquiry> getAllCampEnquiry();

    /**
     * Handles enquiries from students related to the specified camp.
     *
     * @param enquiry the {@link Enquiry} object representing the student's enquiry
     * @param camp    the {@link Student} object related to the enquiry
     * @return true if the enquiry was handled successfully, false otherwise
     */
    public boolean handleCampEnquiry(Enquiry enquiry, Student student);
    
} 