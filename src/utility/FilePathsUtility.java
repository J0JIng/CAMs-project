package utility;

import java.util.HashMap;
import java.util.Map;

/**
 * The FilePathsUtils class provides utility methods for managing file
 * paths within the application. It contains a method to return a mapping of CSV
 * file paths for various data types.
 */
public class FilePathsUtility {
	/**
	 * A Map object that contains the file paths for various data types used
	 * in the application.
     */ 
	private static Map<String, String> filePathsMap = new HashMap<String, String>();

    private FilePathsUtility(){}

	/**
	 * Returns a mapping of CSV file paths for various data types used in the
	 * application. The returned map contains keys such as "user", "student",
	 * "Staff", "Camp", "request",
	 * "transferStudentRequest", and "changeCampTitleRequest",
	 * each associated with their respective file paths.
	 *
	 * @return a Map containing the CSV file paths for various data types
	 */
	public static Map<String, String> csvFilePaths() {
		filePathsMap.clear();

		// Initialize filePathsMap
		filePathsMap.put("user", "data/user.csv");
		filePathsMap.put("student", "data/student.csv");
		filePathsMap.put("Staff", "data/Staff.csv");
		filePathsMap.put("Camp", "data/Camp.csv");
		return filePathsMap;
	}
}