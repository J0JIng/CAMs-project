package interfaces;

import java.util.ArrayList;
import java.util.List;

import models.Camp;
import models.Staff;
import models.Student;

/**
 * The {@link ICampStaffService} interface defines a contract for managing
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
     * @param staff the {@link Staff} object specified to obtain from.
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

	/**
     * Creates one or more camps.
     *
     * @param camps an {@link ArrayList} of {@link Camp} objects to be created
     * @return {@code true} if the creation is successful, {@code false} otherwise
     */
    public boolean createCamp(ArrayList<Camp> camps);

    /**
     * Deletes a specific camp.
     *
     * @param camp the {@link Camp} object to be deleted
     * @return {@code true} if the deletion is successful, {@code false} otherwise
     */
    public boolean deleteCamp(Camp camp);

    /**
     * Toggles the visibility of a specific camp.
     *
     * @param camp the {@link Camp} object for which to toggle visibility
     * @return true if the toggle is successful, false otherwise
     */
    public boolean toggleCampVisibility(Camp camp);
    
}
