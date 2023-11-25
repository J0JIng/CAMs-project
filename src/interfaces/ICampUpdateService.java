package interfaces;

import java.util.Date;

import models.Camp;

/**
 * The {@link ICampUpdateService} interface defines methods for updating camp information.
 */
public interface ICampUpdateService {

	/**
     * Updates the name of the specified camp.
     *
     * @param camp         the {@link Camp} to update
     * @param newCampName  the new name for the camp
     * @return {@code true} if the update is successful, {@code false} otherwise
     */
    public boolean updateCampName(Camp camp, String newCampName);

    /**
     * Updates the start date of the specified camp.
     *
     * @param camp         the {@link Camp} to update
     * @param newStartDate the new start date for the camp
     * @return {@code true} if the update is successful, {@code false} otherwise
     */
    public boolean updateCampStartDate(Camp camp, Date newStartDate);

    /**
     * Updates the end date of the specified camp.
     *
     * @param camp       the {@link Camp} to update
     * @param newEndDate the new end date for the camp
     * @return {@code true} if the update is successful, {@code false} otherwise
     */
    public boolean updateCampEndDate(Camp camp, Date newEndDate);

    /**
     * Updates the registration closing date of the specified camp.
     *
     * @param camp                       the {@link Camp} to update
     * @param newRegistrationClosingDate the new registration closing date for the camp
     * @return {@code true} if the update is successful, {@code false} otherwise
     */
    public boolean updateRegistrationEndDate(Camp camp, Date newRegistrationClosingDate);

    /**
     * Updates the location of the specified camp.
     *
     * @param camp           the {@link Camp} to update
     * @param newLocation    the new location for the camp
     * @return {@code true} if the update is successful, {@code false} otherwise
     */
    public boolean updateCampLocation(Camp camp, String newLocation);

    /**
     * Updates the total slots of the specified camp.
     *
     * @param camp              the {@link Camp} to update
     * @param newCampTotalSlots the new total slots for the camp
     * @return {@code true} if the update is successful, {@code false} otherwise
     */
    public boolean updateCampTotalSlot(Camp camp, int newCampTotalSlots);

    /**
     * Updates the committee slots of the specified camp.
     *
     * @param camp              the {@link Camp} to update
     * @param newCommitteeSlots the new committee slots for the camp
     * @return {@code true} if the update is successful, {@code false} otherwise
     */
    public boolean updateCampCommitteeSlots(Camp camp, int newCommitteeSlots);

    /**
     * Updates the description of the specified camp.
     *
     * @param camp              the {@link Camp} to update
     * @param newDescription    the new description for the camp
     * @return {@code true} if the update is successful, {@code false} otherwise
     */
    public boolean updateCampDescription(Camp camp, String newDescription);

    /**
     * Updates the faculty group of the specified camp.
     *
     * @param camp        the {@link Camp} to update
     * @param userInput   the user input for updating the faculty group
     * @return {@code true} if the update is successful, {@code false} otherwise
     */
    public boolean updateCampFacultyGroup(Camp camp, String userInput);
    
} 