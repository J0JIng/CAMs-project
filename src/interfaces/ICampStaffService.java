package interfaces;

import java.util.ArrayList;
import java.util.List;

import models.Camp;
import models.CampInformation;
import models.Staff;
import models.Student;

/**
 * The {@link IStaffCampService} interface defines a contract for managing
 * staff-related camp services.
 */
public interface ICampStaffService {
    /**
     * Creates new camps from the provided list of camps.
     *
     * @param staffID the ID of the {@link Staff} creating the camp
     * @return true if the camps were created successfully, false otherwise
     */
    public boolean createCamp(Staff staffID);

    /**
     * Updates the details of the specified camp if the staff ID matches the
     * camp's creator.
     *
     * @param camp    the {@link Camp} object to update
     * @param details the new details for the camp
     * @param staffID the ID of the {@link Staff} making the update
     * @return true if the camp details were updated successfully, false otherwise
     */
    public boolean updateCampDetails(Camp camp, CampInformation details, Staff staffID);

    /**
     * Deletes the specified camp if the staff ID matches the camp's creator.
     *
     * @param camp    the {@link Camp} object to delete
     * @param staffID the ID of the {@link Staff} making the delete request
     * @return true if the camp was deleted successfully, false otherwise
     */
    public boolean deleteCamp(Camp camp, Staff staffID);

    /**
     * Toggles the visibility of the specified camp to "on" or "off".
     *
     * @param camp      the {@link Camp} object to update
     * @param visibility the new visibility status ("on" or "off")
     * @return true if the camp visibility was updated successfully, false otherwise
     */
    public boolean toggleCampVisibility(Camp camp, boolean visibility);

    /**
     * Retrieves a list of all camps.
     *
     * @return an {@link List} of {@link Camp} objects representing all camps
     */
    public List<Camp> getAllCamps();

    /**
     * Retrieves a list of camps created by the staff with the specified ID.
     *
     * @param staffID the ID of the {@link Staff}
     * @return an {@link List} of {@link Camp} objects representing the created camps
     */
    public ArrayList<Camp> getStaffCreatedCamps(Staff staffID);

    /**
     * Retrieves a list of students attending a specific camp.
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing attendees
     */
    public List<Student> getCampAttendeeList(Camp camp);

}
