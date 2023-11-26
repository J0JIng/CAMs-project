package utility;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link FilePathsUtils} class provides utility methods for managing file
 * paths within the application. It contains a method to return a mapping of CSV
 * file paths for various data types.
 */
public class FilePathsUtility {
	/**
	 * A Map object that contains the file paths for various data types used
	 * in the application.
     */ 
	private static Map<String, String> filePathsMap = new HashMap<String, String>();

	/**
	 * Private constructor {@link FilePathsUtils} to prevent instantiation of the class.
	 */
    private FilePathsUtility(){
	}

	/**
	 * Returns a mapping of CSV file paths for various data types used in the
	 * application. The returned map contains keys such as "student", "staff"
	 * each associated with their respective file paths.
	 *
	 * @return a Map containing the CSV file paths for various data types
	 */
	public static Map<String, String> csvFilePaths() {
		filePathsMap.clear();

		// Initialize filePathsMap
		filePathsMap.put("student", "data/student_list.csv");
		filePathsMap.put("staff", "data/staff_list.csv");
		filePathsMap.put("reportCommittee", "report/committee_report.csv");
		filePathsMap.put("reportStaff", "report/staff_report.csv");
		return filePathsMap;
	}
}