package interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Camp;
import models.Student;

/**
 * The {@link ICampCommitteeService} interface defines methods for camp committee camp-related operations.
 */
public interface ICampCommitteeService {

    /**
     * Retrieves a list of students attending a specific camp as an Attendee .
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing attendees
     */
    public ArrayList<Student> getCampAttendeeList(Camp camp);   

    /**
     * Retrieves a list of students attending a specific camp as a Camp Committee.
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing CampCommittee
     */
    public ArrayList<Student> getCampCommitteeList(Camp camp);

    /**
     * Converts map keys from userID to studentName.
     *
     * @param inputMap the input {@link Map} with user IDs as keys
     * @return a new {@link Map} with student names as keys
     */
    public Map<String, Student> convertMapKey(Map<String, Student> inputMap);
}
