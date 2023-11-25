package interfaces;

import java.util.Map;

import models.Student;
import models.Staff;

/**
 * The {@link IFileDataService} interface defines methods for importing data related to students and staff 
 * from specified file paths to the login detail .csv files.
 */
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
	 * @param StaffFilePath the file path of the Staffs file
	 * @return a {@link Map} of {@link Staff} objects with their IDs as keys
	 */
	Map<String, Staff> importStaffData(String StaffFilePath);
	
}