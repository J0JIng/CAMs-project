package interfaces;
import java.util.List;

import models.Camp;

public interface ICampStudentService {
    /**
     * Retrieves a list of available projects.
     *
     * @return an ArrayList of Camp objects representing the
     *         available projects
     */
    public List<Camp> getAvailableCamps();
    
}