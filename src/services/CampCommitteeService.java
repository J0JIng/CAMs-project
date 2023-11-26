package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import interfaces.ICampCommitteeService;
import models.Camp;
import models.Student;
import stores.DataStore;

/**
 * The {@link CampCommitteeService} class implements the {@link ICampCommitteeService} interface and
 * provides methods for managing committee-related camp services. These services aid in the overall 
 * committees' desired operations.
 */
public class CampCommitteeService implements ICampCommitteeService  {

    /**
     * Constructs an instance of the {@link CampCommitteeService} class.
     */
    public CampCommitteeService(){
	}

	/**
     * Retrieves a list of students attending a specific camp as an Attendee .
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing attendees
     */
	@Override
	public ArrayList<Student> getCampAttendeeList(Camp camp) {
		Map<String, List<String>> campToRegisteredStudentData = DataStore.getCampToRegisteredStudentData();
		Map<String, Student> allStudentsData = DataStore.getStudentData();
		String campName = camp.getCampInformation().getCampName();

		// Retrieves the list of registered student names for the specified camp. If the camp is not found in the map, it defaults to an empty list.
		List<String> registeredStudents = campToRegisteredStudentData.getOrDefault(campName, Collections.emptyList());
		Map<String, Student> allStudentsDataNames = convertMapKey(allStudentsData);
        
		// Returns the list of students attending the specified camp.
		ArrayList<Student> campAttendeeList = registeredStudents.stream()
				.map(allStudentsDataNames::get)
				.filter(Objects::nonNull) // Filter out null students if any
				.collect(Collectors.toCollection(ArrayList::new));

		return campAttendeeList;
	}

    /**
     * Retrieves a list of students attending a specific camp as a Camp Committee.
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing CampCommittee
     */
	@Override
	public ArrayList<Student> getCampCommitteeList(Camp camp){
		Map<String, List<String>> campToRegisteredCampCommitteeData = DataStore.getCampToRegisteredCampCommitteeData();
		Map<String, Student> allStudentsData = DataStore.getStudentData();
		String campName = camp.getCampInformation().getCampName();

		//retrieves the list of registered CampCommittee names for the specified camp. If the camp is not found in the map, it defaults to an empty list.
		List<String> registeredCampCommittee = campToRegisteredCampCommitteeData.getOrDefault(campName, Collections.emptyList());
		Map<String, Student> allStudentsDataNames = convertMapKey(allStudentsData);

		//returns the list of CampCommittee attending the specified camp.
		ArrayList<Student> campCommitteeList = registeredCampCommittee.stream()
				.map(allStudentsDataNames::get)
				.filter(Objects::nonNull) // Filter out null students if any
				.collect(Collectors.toCollection(ArrayList::new));

		return campCommitteeList;
	}

    // ---------- Helper Method ---------- //

	/**
     * Converts map keys from userID to studentName.
     *
     * @param inputMap the input {@link Map} with user IDs as keys
     * @return a new {@link Map} with student names as keys
     */
    public Map<String, Student> convertMapKey(Map<String, Student> inputMap) {
        Map<String, Student> convertedMap = new HashMap<>();

        // Populate the converted map using student names as keys
        inputMap.forEach((key, student) -> {
            // Assuming User is the superclass of Student and it has a 'name' attribute
            String studentName = student.getName();
            convertedMap.put(studentName, student);
        });

        return convertedMap;
    }
    
}
