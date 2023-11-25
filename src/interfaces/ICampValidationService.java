package interfaces;

import java.util.Date;
import java.util.List;

import models.Camp;
import models.Student;

/**
 * The {@link ICampValidationService} interface defines methods for validating criterions for camp-related operations.
 */
public interface ICampValidationService {

	/**
     * Checks if adding the specified camp for the given student causes a date clash.
     *
     * @param student the {@link Student} for whom to check the date clash
     * @param newCamp the {@link Camp} to check for date clash
     * @return {@code true} if a date clash exists, {@code false} otherwise
     */
    public boolean hasDateClash(Student student, Camp newCamp);
	
    /**
     * Checks if a date clash exists between two camps.
     *
     * @param camp1 the first {@link Camp} to compare
     * @param camp2 the second {@link Camp} to compare
     * @return {@code true} if a date clash exists, {@code false} otherwise
     */
	public boolean dateClashExists(Camp camp1, Camp camp2);

	/**
     * Checks if the specified camp is over based on the current date.
     *
     * @param currentDate the current date
     * @param camp         the {@link Camp} to check
     * @return {@code true} if the camp is over, {@code false} otherwise
     */
	public boolean isCampOver(Date currentDate, Camp camp);
	
	/**
     * Checks if the specified camp is full.
     *
     * @param camp the {@link Camp} to check
     * @return {@code true} if the camp is full, {@code false} otherwise
     */
	public boolean isCampFull(Camp camp);

	/**
     * Checks if the camp committee for the specified camp is full.
     *
     * @param camp the {@link Camp} to check
     * @return {@code true} if the camp committee is full, {@code false} otherwise
     */
	public boolean isCampCommitteeFull(Camp camp);
	
	/**
     * Checks if the specified student is part of the camp committee for the given camp.
     *
     * @param student the {@link Student} to check
     * @param camp    the {@link Camp} for which to check the committee membership
     * @return {@code true} if the student is a committee member, {@code false} otherwise
     */
	public boolean isUserCampCommitteeForCamp(Student student, Camp camp);

	/**
     * Checks if the specified student is part of any camp committee.
     *
     * @param student the {@link Student} to check
     * @return {@code true} if the student is a committee member, {@code false} otherwise
     */
	public boolean isUserCampCommittee(Student student);
	
	/**
     * Checks if the specified student has withdrawn from the given camp.
     *
     * @param student the {@link Student} to check
     * @param camp    the {@link Camp} for which to check withdrawal
     * @return {@code true} if the student has withdrawn, {@code false} otherwise
     */
	public boolean isUserWithdrawnFromCamp(Student student, Camp camp);
	
	/**
     * Checks if the specified student is registered for the given camp.
     *
     * @param student the {@link Student} to check
     * @param camp    the {@link Camp} for which to check registration
     * @return {@code true} if the student is registered, {@code false} otherwise
     */
	public boolean isUserRegisteredWithCamp(Student student, Camp camp);

	/**
     * Checks if the specified camp is registered.
     *
     * @param camp the {@link Camp} to check
     * @return {@code true} if the camp is registered, {@code false} otherwise
     */
    public boolean isCampRegistered(Camp camp);

    /**
     * Checks if the specified camp name is valid, i.e., not already used by existing camps.
     *
     * @param existingCamps a {@link List} of existing {@link Camp} objects
     * @param newCampName   the new camp name to validate
     * @return {@code true} if the name is valid, {@code false} otherwise
     */
    public boolean isValidName(List<Camp> existingCamps, String newCampName);
    
}
