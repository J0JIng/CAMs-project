package interfaces;

import java.util.List;

import models.Camp;
import models.Student;

/**
 * The {@link ICampStudentService} interface defines methods for retrieving
 * information about camps and managing a student's participation in camps.
 */
public interface ICampStudentService {

    /**
     * Retrieves a list of available camps that a student can register for.
     *
     * @return a {@link List} of {@link Camp} objects representing the available camps
     */
    public List<Camp> getAvailableCampsToRegister();

    /**
     * Retrieves a list of available camps that a student can register for.
     *
     * @return a {@link List} of {@link Camp} objects representing the available camps
     */
    public List<Camp> getAllCamps();

    /**
     * Retrieves a list of camps from which the student has withdrawn.
     *
     * @return a {@link List} of {@link Camp} objects representing the withdrawn camps
     */
    public List<Camp> getWithdrawnCamps();

    /**
     * Retrieves a list of camps for which the student is currently registered.
     *
     * @return a {@link List} of {@link Camp} objects representing the registered camps
     */
    public List<Camp> getRegisteredCamps();

    /**
     * Return camp for which the student is currently registered as a Committee Member.
     *
     * @return a {@link Camp} representing the camp registered by Committee Member. 
     */;
    public Camp getCampCommitteeCamp(Student student);

    /**
     * Registers the student for a specific camp.
     *
     * @param student the {@link Student} to register
     * @param camp the {@link Camp} to register for
     * @return {@code true} if registration is successful, {@code false} otherwise
     */
    public boolean registerCamp(Student student,Camp camp);

    /**
     * Withdraws the student from a specific camp.
     *
     * @param student the {@link Student} to withdraw
     * @param camp the {@link Camp} to withdraw from
     * @return {@code true} if withdrawal is successful, {@code false} otherwise
     */
    public boolean withdrawCamp(Student student,Camp camp);

    /**
     * Registers the student as a Committee Member for a specific camp.
     *
     * @param student the {@link Student} to register as a Committee Member
     * @param camp the {@link Camp} for which to register as a Committee Member
     * @return {@code true} if registration as a Committee Member is successful, {@code false} otherwise
     */
    public boolean registerAsCommittee(Student student,Camp camp);
}
