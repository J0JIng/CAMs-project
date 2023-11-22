package interfaces;

import java.util.ArrayList;
import java.util.List;

import models.Camp;
import models.Staff;
import models.Student;

/**
 * The {@link IStaffCampService} interface defines a contract for managing
 * staff-related camp services.
 */
public interface ICampStaffService{
    /**
     * Retrieves a list of all camps.
     *
     * @return an {@link List} of {@link Camp} objects representing all camps
     */
    public List<Camp> getAllCamps();

    /**
     * Retrieves a list of camps created by the staff with the specified ID.
     *
     * @return an {@link List} of {@link Camp} objects representing the created camps
     */
    public List<Camp> getStaffCreatedCamps(Staff staff);

    /**
     * Retrieves a list of students attending a specific camp as an Attendee .
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing attendees
     */
    public List<Student> getCampAttendeeList(Camp camp);

    /**
     * Retrieves a list of students attending a specific camp as a Camp Committee.
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing CampCommittee
     */
	public List<Student> getCampCommitteeList(Camp camp);

    public boolean createCamp(ArrayList<Camp> camps);

    public boolean deleteCamp(Camp camp);

    public void toggleCampVisibility(Camp camp);

    

}
