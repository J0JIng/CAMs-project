package stores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.IFileDataService;
import models.Camp;
import models.Student;
import models.Staff;
import models.Enquiry; 
import models.Suggestion; 

/**
 * The {@code DataStore} class is responsible for storing and managing various types of data related to the application.
 * Data range from student, staff objects, camp registration/withdrawal data, enquiry and suggestion data
 * It provides methods to initialize, save, and retrieve data.
 */
public class DataStore {
	
	/**
     * The file data service used for importing and exporting data. It should implement {@link IFileDataService}.
     */
    private static IFileDataService fileDataService;

    /**
     * The map containing file paths for different data types. It should include paths for "student", "Staff".
     */
    private static Map<String, String> filePathsMap;

    /**
     * Map containing student data.
     */
    private static Map<String, Student> studentsData = new HashMap<>();

    /**
     * Map containing staff data.
     */
    private static Map<String, Staff> staffData = new HashMap<>();

    /**
     * Map containing camp data.
     */
    private static Map<String, Camp> campData = new HashMap<>();

    /**
     * Map containing enquiry data. Key: enquiry ID, Value: Enquiry object.
     */
    private static Map<Integer, Enquiry> enquiryData = new HashMap<>();

    /**
     * Map containing suggestion data. Key: suggestion ID, Value: Suggestion object.
     */
    private static Map<Integer, Suggestion> suggestionData = new HashMap<>();

    /**
     * Map representing the relationship between a student and the camps they have withdrawn or registered for.
     * Key: studentName, Value: List of camps withdrawn
     */
    private static Map<String, List<String>> studentToCampsWithdrawnData = new HashMap<>();

    /**
     * Map representing the relationship between a student and the camps they have registered for.
     * Key: studentName, Value: List of camps registered.
     */
    private static Map<String, List<String>> studentsToCampsRegisteredData = new HashMap<>();

    /**
     * Map representing the relationship between a Camp Committee member and the camp they have registered for.
     * Key: CampCommitteeName(CampCommittee), Value: Camp that the CampCommittee member has registered.
     */
    private static Map<String, String> campCommitteeToCampRegisteredData = new HashMap<>();

    /**
     * Map representing the relationship between a camp and the students registered for it.
     * Key: CampName, Value: List of Student registered.
     */
    private static Map<String, List<String>> campToRegisteredStudentData = new HashMap<>();

    /**
     * Map representing the relationship between a camp and the Camp Committee members registered for it.
     * Key: CampName, Value: List of CampCommitteeName(CampCommittee) registered.
     */
    private static Map<String, List<String>> campToRegisteredCampCommitteeData = new HashMap<>();

	/**
	 * Private constructor  {@code DataStore} to prevent instantiation of the class.
	 */
	private DataStore() {}

	/**
     * Initializes the DataStore with the provided file data service and file paths.
     *
     * @param fileDataService The file data service to be used for importing and exporting data.
     * @param filePathsMap    The map containing file paths for different data types.
     * @return {@code true} if the initialization is successful, {@code false} otherwise.
     */
	public static boolean initDataStore(IFileDataService fileDataService, Map<String, String> filePathsMap) {
        try {
            // Initialize fileDataService and filePathsMap
            DataStore.filePathsMap = filePathsMap;
            DataStore.fileDataService = fileDataService;
    
            // Import data
            DataStore.studentsData = fileDataService.importStudentData(filePathsMap.get("student"));
            DataStore.staffData = fileDataService.importStaffData(filePathsMap.get("staff"));
    
            System.out.println("DataStore initialized successfully.");
            return true; 
        } catch (Exception e) {
            System.out.println("Error initializing DataStore: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
      
	/**
     * Saves the current state of all data to the respective data files.
     *
     * @return {@code true} if the data is successfully saved, {@code false} otherwise.
     */
	public static boolean saveData() {
		DataStore.setStudentsData(studentsData);
		DataStore.setStaffData(staffData);
		DataStore.setCampData(campData);
		DataStore.setEnquiryData(enquiryData);
		DataStore.setSuggestionData(suggestionData);
		DataStore.setStudentToCampsWithdrawnData(studentToCampsWithdrawnData);
		DataStore.setStudentsToCampsRegisteredData(studentsToCampsRegisteredData);
		DataStore.setCampToRegisteredStudentData(campToRegisteredStudentData);
		DataStore.setCampToRegisteredCampCommitteeData(campToRegisteredCampCommitteeData);
		DataStore.setCampCommitteeToCampRegisteredData(campCommitteeToCampRegisteredData);
		return true;
	}

	/**
     * Gets the map containing student data.
     *
     * @return The map containing student data.
     */
	public static Map<String, Student> getStudentData() {
		return DataStore.studentsData;
	}

	 /**
     * Sets the map containing student data.
     *
     * @param studentsData The map containing student data to be set.
     */
	public static void setStudentsData(Map<String, Student> studentsData) {
		DataStore.studentsData = studentsData;
	}

	/**
     * Gets the map containing staff data.
     *
     * @return The map containing staff data.
     */
	public static Map<String, Staff> getStaffData() {
		return DataStore.staffData;
	}

	/**
     * Sets the map containing staff data.
     *
     * @param StaffData The map containing staff data to be set.
     */
	public static void setStaffData(Map<String, Staff> StaffData) {
		DataStore.staffData = StaffData;
	}

	/**
     * Gets the map containing camp data.
     *
     * @return The map containing camp data.
     */
	public static Map<String, Camp> getCampData() {
		return DataStore.campData;
	}

	/**
     * Sets the map containing camp data.
     *
     * @param campData The map containing camp data to be set.
     */
	public static void setCampData(Map<String, Camp> campData) {
		DataStore.campData = campData;
	}

	/**
     * Gets the map containing enquiry data.
     *
     * @return The map containing enquiry data.
     */
	public static Map<Integer, Enquiry> getEnquiryData() {
        return enquiryData;
    }

	/**
     * Sets the map containing enquiry data.
     *
     * @param enquiryData The map containing enquiry data to be set.
     */
   	public static void setEnquiryData(Map<Integer, Enquiry> enquiryData) {
        DataStore.enquiryData = enquiryData;
    }

   	/**
   	 * Gets the map containing suggestion data.
   	 *
   	 * @return The map containing suggestion data.
   	 */
  	public static Map<Integer, Suggestion> getSuggestionData() {
        return suggestionData;
    }
  	
  	/**
  	 * Sets the map containing suggestion data.
  	 *
  	 * @param suggestionData The map containing suggestion data to be set.
  	 */
  	public static void setSuggestionData(Map<Integer, Suggestion> suggestionData) {
        DataStore.suggestionData = suggestionData; 
    }    

  	/**
  	 * Gets the map representing the relationship between a student and the camps they have withdrawn or registered for.
  	 *
  	 * @return The map representing the relationship between a student and the camps they have withdrawn.
  	 */
  	public static Map<String, List<String>> getStudentToCampsWithdrawnData() {
        return studentToCampsWithdrawnData;
    }

  	/**
  	 * Sets the map representing the relationship between a student and the camps they have withdrawn or registered for.
  	 *
  	 * @param studentToCampsWithdrawnData The map to be set.
  	 */
  	public static void setStudentToCampsWithdrawnData(Map<String, List<String>> studentToCampsWithdrawnData) {
        DataStore.studentToCampsWithdrawnData = studentToCampsWithdrawnData; 
    }    

  	/**
  	 * Gets the map representing the relationship between a student and the camps they have registered for.
  	 *
  	 * @return The map representing the relationship between a student and the camps they have registered for.
  	 */
	public static Map<String, List<String>> getStudentsToCampsRegisteredData() {
        return studentsToCampsRegisteredData;
    }

	/**
	 * Sets the map representing the relationship between a student and the camps they have registered for.
	 *
	 * @param studentsToCampsRegisteredData The map to be set.
	 */
  	public static void setStudentsToCampsRegisteredData(Map<String, List<String>> studentsToCampsRegisteredData) {
        DataStore.studentsToCampsRegisteredData = studentsToCampsRegisteredData; 
    }    
	
  	/**
  	 * Gets the map representing the relationship between a camp and the students registered for it.
  	 *
  	 * @return The map representing the relationship between a camp and the students registered for it.
  	 */
	public static Map<String, List<String>> getCampToRegisteredStudentData() {
        return campToRegisteredStudentData;
    }

	/**
	 * Sets the map representing the relationship between a camp and the students registered for it.
	 *
	 * @param campToRegisteredStudentData The map to be set.
	 */
  	public static void setCampToRegisteredStudentData(Map<String, List<String>> campToRegisteredStudentData) {
        DataStore.campToRegisteredStudentData = campToRegisteredStudentData; 
    }    

  	/**
  	 * Gets the map representing the relationship between a Camp Committee member and the camp they have registered for.
  	 *
  	 * @return The map representing the relationship between a Camp Committee member and the camp they have registered for.
  	 */
	public static Map<String, String> getCampCommitteeToCampRegisteredData() {
        return campCommitteeToCampRegisteredData;
    }

	/**
	 * Sets the map representing the relationship between a Camp Committee member and the camp they have registered for.
	 *
	 * @param campCommitteeToCampRegisteredData The map to be set.
	 */
  	public static void setCampCommitteeToCampRegisteredData(Map<String, String> campCommitteeToCampRegisteredData) {
        DataStore.campCommitteeToCampRegisteredData = campCommitteeToCampRegisteredData; 
    }    

  	/**
  	 * Gets the map representing the relationship between a camp and the Camp Committee members registered for it.
  	 *
  	 * @return The map representing the relationship between a camp and the Camp Committee members registered for it.
  	 */
	public static Map<String, List<String>> getCampToRegisteredCampCommitteeData() {
        return campToRegisteredCampCommitteeData;
    }

	/**
	 * Sets the map representing the relationship between a camp and the Camp Committee members registered for it.
	 *
	 * @param campToRegisteredCampCommitteeData The map to be set.
	 */
  	public static void setCampToRegisteredCampCommitteeData(Map<String, List<String>> campToRegisteredCampCommitteeData) {
        DataStore.campToRegisteredCampCommitteeData = campToRegisteredCampCommitteeData; 
    }    

}
