package interfaces;

import java.util.Map;

import models.Camp;
//import models.Enquiry; // @TODO
//import models.Suggestion; // @TODO
import models.Student;
import models.Staff;


public interface IFileDataService {
	// ---------- Student ---------- //
	/**
	 * Imports student data from the specified file paths.
	 *
	 * @param usersFilePath    the file path of the users file
	 * @param studentsFilePath the file path of the students file
	 * @return a {@link Map} of {@link Student} objects with their IDs as keys
	 */
	Map<String, Student> importStudentData(String usersFilePath, String studentsFilePath);
	
	/**
	 * Exports student data to the specified file paths.
	 *
	 * @param usersFilePath    the file path of the users file
	 * @param studentsFilePath the file path of the students file
	 * @param studentMap       a {@link Map} of {@link Student} objects with their
	 *                         IDs as keys
	 * @return true if the data was exported successfully, false otherwise
	 */
	boolean exportStudentData(String usersFilePath, String studentsFilePath, Map<String, Student> studentMap);

	// ---------- Staff ---------- //
	/**
	 * Imports Staff data from the specified file paths.
	 *
	 * @param usersFilePath      the file path of the users file
	 * @param StaffFilePath the file path of the Staffs file
	 * @return a {@link Map} of {@link Staff} objects with their IDs as keys
	 */
	Map<String, Staff> importStaffData(String usersFilePath, String StaffFilePath);
	
	/**
	 * Exports Staff data to the specified file paths.
	 *
	 * @param usersFilePath       the file path of the users file
	 * @param StaffsFilePath the file path of the Staffs file
	 * @param StaffMap       a {@link Map} of {@link Staff} objects with
	 *                            their IDs as keys
	 * @return true if the data was exported successfully, false otherwise
	 */
	boolean exportStaffData(String usersFilePath, String StaffsFilePath, Map<String, Staff> StaffMap);

	// ---------- Camp ---------- //
	/**
	 * Imports Camp data from the specified file paths.
	 *
	 * @param CampsFilePath        the file path of the Camps file
	 * @param usersFilePath           the file path of the users file
	 * @param studentsFilePath        the file path of the students file
	 * @param StaffsFilePath     the file path of the Staffs file

	 * @return a {@link Map} of {@link Camp} objects with their IDs as keys
	 */
	Map<Integer, Camp> importCampData(String CampsFilePath, String usersFilePath, String studentsFilePath, String StaffsFilePath);
	
	/**
	 * Exports Camp data to the specified file path.
	 *
	 * @param CampsFilePath the file path of the Camps file
	 * @param CampMap       a {@link Map} of {@link Camp} objects with their
	 *                         IDs as keys
	 * @return true if the data was exported successfully, false otherwise
	 */
	boolean exportCampData(String CampsFilePath, Map<Integer, Camp> CampMap);
}