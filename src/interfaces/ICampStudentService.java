package interfaces;

import java.util.List;

import models.Camp;

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
}
