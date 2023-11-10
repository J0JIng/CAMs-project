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

public class DataStore {
	
	/**
     * The file data service used for importing and exporting data. It should implement {@link IFileDataService}.
     */
    private static IFileDataService fileDataService;

    /**
     * The map containing file paths for different data types. It should include paths for "user", "student", "Staff", and "Camp".
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
	 * Private constructor to prevent instantiation of the class.
	 */
	private DataStore() {}

	public static boolean initDataStore(IFileDataService fileDataService, Map<String, String> filePathsMap) {
		// work in progress

		// Initialize fileDataService and filePathsMap
		DataStore.filePathsMap = filePathsMap;
		DataStore.fileDataService = fileDataService;

		// Import data
		DataStore.studentsData = fileDataService.importStudentData(
            filePathsMap.get("user"),
            filePathsMap.get("student"));

		DataStore.staffData = fileDataService.importStaffData(
            filePathsMap.get("user"),
			filePathsMap.get("Staff"));

		DataStore.campData = fileDataService.importCampData(
            filePathsMap.get("Camp"),
            filePathsMap.get("user"), 
            filePathsMap.get("student"), 
            filePathsMap.get("Staff"));

		return true;
	}

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

	public static Map<String, Student> getStudentData() {
		return DataStore.studentsData;
	}

	public static void setStudentsData(Map<String, Student> studentsData) {
		DataStore.studentsData = studentsData;
		fileDataService.exportStudentData(filePathsMap.get("user"), filePathsMap.get("student"), studentsData);
	}

	public static Map<String, Staff> getStaffData() {
		return DataStore.staffData;
	}

	public static void setStaffData(Map<String, Staff> StaffData) {
		DataStore.staffData = StaffData;
		fileDataService.exportStaffData(filePathsMap.get("user"), filePathsMap.get("Staff"), StaffData);
	}

	public static Map<String, Camp> getCampData() {
		return DataStore.campData;
	}

	public static void setCampData(Map<String, Camp> campData) {
		DataStore.campData = campData;
		fileDataService.exportCampData(filePathsMap.get("Camp"), campData);
	}

	public static Map<Integer, Enquiry> getEnquiryData() {
        return enquiryData;
    }

   	public static void setEnquiryData(Map<Integer, Enquiry> enquiryData) {
        DataStore.enquiryData = enquiryData;
    }

  	public static Map<Integer, Suggestion> getSuggestionData() {
        return suggestionData;
    }
  	public static void setSuggestionData(Map<Integer, Suggestion> suggestionData) {
        DataStore.suggestionData = suggestionData; 
    }    

  	public static Map<String, List<String>> getStudentToCampsWithdrawnData() {
        return studentToCampsWithdrawnData;
    }

  	public static void setStudentToCampsWithdrawnData(Map<String, List<String>> studentToCampsWithdrawnData) {
        DataStore.studentToCampsWithdrawnData = studentToCampsWithdrawnData; 
    }    

	public static Map<String, List<String>> getStudentsToCampsRegisteredData() {
        return studentsToCampsRegisteredData;
    }

  	public static void setStudentsToCampsRegisteredData(Map<String, List<String>> studentsToCampsRegisteredData) {
        DataStore.studentsToCampsRegisteredData = studentsToCampsRegisteredData; 
    }    
	
	public static Map<String, List<String>> getCampToRegisteredStudentData() {
        return campToRegisteredStudentData;
    }

  	public static void setCampToRegisteredStudentData(Map<String, List<String>> campToRegisteredStudentData) {
        DataStore.campToRegisteredStudentData = campToRegisteredStudentData; 
    }    

	public static Map<String, String> getCampCommitteeToCampRegisteredData() {
        return campCommitteeToCampRegisteredData;
    }

  	public static void setCampCommitteeToCampRegisteredData(Map<String, String> campCommitteeToCampRegisteredData) {
        DataStore.campCommitteeToCampRegisteredData = campCommitteeToCampRegisteredData; 
    }    


	public static Map<String, List<String>> getCampToRegisteredCampCommitteeData() {
        return campToRegisteredCampCommitteeData;
    }

  	public static void setCampToRegisteredCampCommitteeData(Map<String, List<String>> campToRegisteredCampCommitteeData) {
        DataStore.campToRegisteredCampCommitteeData = campToRegisteredCampCommitteeData; 
    }    

}
