package services;
import java.util.Date;
import java.util.List;
import java.util.Map;

import interfaces.ICampValidationService;
import interfaces.ICampStudentService;
import models.Camp;
import models.Student;
import stores.DataStore;

/**
 * The {@code CampValidationService} class implements the {@link ICampValidationService} interface
 * and provides services for validating camp-related operations.
 */
public class CampValidationService implements ICampValidationService {

	/**
     * The {@code campStudentService} field represents an instance of
     * {@link ICampStudentService} used for running camp student related services.
     * It is kept private to ensure its use is exclusively in services that require it.
     */
    private final static ICampStudentService campStudentService = new CampStudentService();

    /**
     * Constructs an instance of the {@code CampValidationService} class.
     */
    public CampValidationService(){
    }

    // ---------- interface Service method implementation ---------- //

	/**
     * Checks for date clashes with other registered camps.
     *
     * @param student The {@link Student} for whom to check date clashes.
     * @param newCamp The new {@link Camp} for which to check date clashes.
     * @return {@code true} if there is a date clash, {@code false} otherwise.
     */
    @Override
	public boolean hasDateClash(Student student, Camp newCamp) {
		List<Camp> registeredCamps = campStudentService.getRegisteredCamps();
		return registeredCamps.stream().anyMatch(camp -> dateClashExists(camp, newCamp));
	}

	/**
     * Checks if there's a date clash between two camps.
     *
     * @param camp1 The first {@link Camp} .
     * @param camp2 The second {@link Camp} .
     * @return {@code true} if there is a date clash, {@code false} otherwise.
     */
    @Override
	public boolean dateClashExists(Camp camp1, Camp camp2) {
    	Date camp1StartDate = camp1.getCampInformation().getCampStartDate();
    	Date camp1EndDate = camp1.getCampInformation().getCampEndDate();
    	Date camp2StartDate = camp2.getCampInformation().getCampStartDate();
    	Date camp2EndDate = camp2.getCampInformation().getCampEndDate();

    	// Check if camp1 ends before camp2 starts or camp1 starts after camp2 ends
    	return camp1StartDate.before(camp2EndDate) && camp1EndDate.after(camp2StartDate);
	}

	/**
	 * Checks if the {@link Camp} is over based on the current date and the {@link Camp}'s registration closing date.
	 *
	 * @param currentDate The current date.
	 * @param camp The {@link Camp}  to check.
	 * @return {@code true} if the {@link Camp} is over, {@code false} otherwise.
	 */
    @Override
	public boolean isCampOver(Date currentDate, Camp camp) {
		return currentDate.after(camp.getCampInformation().getCampRegistrationClosingDate());
	}
	

	/**
	 * Checks if the {@link Camp} is full by comparing the number of registered students with the maximum allowed slots.
	 *
	 * @param camp The {@link Camp} to check.
	 * @return {@code true} if the {@link Camp} is full, {@code false} otherwise.
	 */
    @Override
	public boolean isCampFull(Camp camp) {
		Map<String, List<String>> registeredStudentData = DataStore.getCampToRegisteredStudentData();
		String campName = camp.getCampInformation().getCampName();
		int maxCampSlots = camp.getCampInformation().getCampTotalSlots();

		return registeredStudentData.containsKey(campName) && registeredStudentData.get(campName).size() >= maxCampSlots;
	}

	/**
	 * Checks if the camp committee slots are full by comparing the number of registered committee members
	 * with the maximum allowed committee slots.
	 *
	 * @param camp The {@link Camp} to check.
	 * @return {@code true} if the camp committee slots are full, {@code false} otherwise.
	 */
    @Override
	public boolean isCampCommitteeFull(Camp camp) {
		Map<String, List<String>> registeredCampCommitteeData = DataStore.getCampToRegisteredCampCommitteeData();
		String campName = camp.getCampInformation().getCampName();
		int maxCampCommitteeSlots = camp.getCampInformation().getCampCommitteeSlots();

		return registeredCampCommitteeData.containsKey(campName) && registeredCampCommitteeData.get(campName).size() >= maxCampCommitteeSlots;
	}
	

	/**
	 * Checks if the {@link Student} is the camp committee member for the specified camp.
	 *
	 * @param student The {@link Student} to check.
	 * @param camp The {@link Camp} to check.
	 * @return {@code true} if the {@link Student} is a camp committee member for the specified {@link Camp}, {@code false} otherwise.
	 */
    @Override
	public boolean isUserCampCommitteeForCamp(Student student, Camp camp) {
		Map<String, String> campCommitteeData = DataStore.getCampCommitteeToCampRegisteredData();
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();

		return campCommitteeData.containsKey(studentName) && campCommitteeData.get(studentName).equals(campName);
	}


	/**
	 * Checks if the {@link Student} is a camp committee member for any {@link Camp}.
	 *
	 * @param student The {@link Student} to check.
	 * @return {@code true} if the {@link Student} is a camp committee member, {@code false} otherwise.
	 */
    @Override
	public boolean isUserCampCommittee(Student student) {
		Map<String, String> campCommitteeData = DataStore.getCampCommitteeToCampRegisteredData();
		String studentName = student.getName();
	
		return campCommitteeData.containsKey(studentName);
	}
	
	/**
	 * Checks if the {@link Student} has withdrawn from the specified {@link Camp}.
	 *
	 * @param student The {@link Student} to check.
	 * @param camp The {@link Camp} to check.
	 * @return {@code true} if the {@link Student} has withdrawn from the specified {@link Camp}, {@code false} otherwise.
	 */
    @Override
	public boolean isUserWithdrawnFromCamp(Student student, Camp camp) {
		Map<String, List<String>> withdrawnCampData = DataStore.getStudentToCampsWithdrawnData();
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();

		return withdrawnCampData.containsKey(studentName) && withdrawnCampData.get(studentName).contains(campName);
	}
	
	/**
	 * Checks if the {@link Student} is registered with the specified {@link Camp}.
	 *
	 * @param student The {@link Student} to check.
	 * @param camp The {@link Camp} to check.
	 * @return {@code true} if the {@link Student} is registered with the specified {@link Camp}, {@code false} otherwise.
 	*/
     @Override
	public boolean isUserRegisteredWithCamp(Student student, Camp camp) {
		Map<String, List<String>> registeredCampData = DataStore.getStudentsToCampsRegisteredData();
		String studentName = student.getName();
		String campName = camp.getCampInformation().getCampName();

		return registeredCampData.containsKey(studentName) && registeredCampData.get(studentName).contains(campName);
	}

	/**
     * Checks if a camp is registered by students or camp committees.
     *
     * @param camp the {@link Camp} object to check
     * @return true if the camp is registered, false otherwise
     */
    @Override
	public boolean isCampRegistered(Camp camp) {
		Map<String, List<String>> registeredStudents = DataStore.getCampToRegisteredStudentData();
		Map<String, List<String>> registeredCampCommittees = DataStore.getCampToRegisteredCampCommitteeData();
	
		String campName = camp.getCampInformation().getCampName();
		
		// Check if the camp has registered students
		List<String> registeredStudentList = registeredStudents.get(campName);
		boolean hasRegisteredStudents = registeredStudentList != null && !registeredStudentList.isEmpty();
	
		// Check if the camp has registered camp committees
		List<String> registeredCampCommitteeList = registeredCampCommittees.get(campName);
		boolean hasRegisteredCampCommittees = registeredCampCommitteeList != null && !registeredCampCommitteeList.isEmpty();
	
		// Return true if the camp has either registered students or camp committees
		return hasRegisteredStudents || hasRegisteredCampCommittees;
	}

	/**
     * Checks if a camp name is unique among existing camps.
     *
     * @param existingCamps the list of existing {@link Camp} objects
     * @param newCampName   the new camp name to check
     * @return true if the camp name is unique, false otherwise
     */
    @Override
	public boolean isValidName(List<Camp> existingCamps, String newCampName) {
		for (Camp camp : existingCamps) {
			if (camp.getCampInformation().getCampName().equalsIgnoreCase(newCampName)) {
				return false;
			}
		}
		return true;
	}
    
}
