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
	 * @param studentsFilePath the file path of the students file
	 * @return a {@link Map} of {@link Student} objects with their IDs as keys
	 */
	Map<String, Student> importStudentData(String studentsFilePath);
	
	// ---------- Staff ---------- //
	/**
	 * Imports Staff data from the specified file paths.
	 *
	 * @param usersFilePath      the file path of the users file
	 * @param StaffFilePath the file path of the Staffs file
	 * @return a {@link Map} of {@link Staff} objects with their IDs as keys
	 */
	Map<String, Staff> importStaffData(String StaffFilePath);
	
}