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
	
	private static IFileDataService fileDataService;
	private static Map<String, String> filePathsMap;
	private static Map<String, Student> studentsData = new HashMap<String, Student>();
	private static Map<String, Staff> staffData = new HashMap<String, Staff>();
	private static Map<String, Camp> campData = new HashMap<String, Camp>();

	// Key: studentName | Value: List of camps withdrawn/registered
	private static Map<String, List<String>> withdrawnCampData = new HashMap<String, List<String>>();
	private static Map<String, List<String>> registeredCampData = new HashMap<String, List<String>>();
	// Key: CampName | Value: List of Student withdrawn/registered
	private static Map<String, List<String>> withdrawnStudentData = new HashMap<String, List<String>>();
	private static Map<String, List<String>> registeredStudentData = new HashMap<String, List<String>>();

	private static Map<Integer, Enquiry> enquiryData = new HashMap<Integer, Enquiry>(); 
    private static Map<Integer, Suggestion> suggestionData = new HashMap<Integer, Suggestion>(); 

	/**
	 * Private constructor to prevent instantiation of the class.
	 */
	private DataStore() {}

	public static boolean initDataStore(IFileDataService fileDataService, Map<String, String> filePathsMap) {
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

  	public static Map<String, List<String>> getWithdrawnCampData() {
        return withdrawnCampData;
    }

  	public static void setWithdrawnCampData(Map<String, List<String>> withdrawnCampData) {
        DataStore.withdrawnCampData = withdrawnCampData; 
    }    

	public static Map<String, List<String>> getRegisteredCampData() {
        return registeredCampData;
    }

  	public static void setRegisteredCampData(Map<String, List<String>> registeredCampData) {
        DataStore.registeredCampData = registeredCampData; 
    }    

	public static Map<String, List<String>> getWithdrawnStudentData() {
        return withdrawnStudentData;
    }

  	public static void setWithdrawnStudentData(Map<String, List<String>> withdrawnStudentData) {
        DataStore.withdrawnStudentData = withdrawnStudentData; 
    }    

	public static Map<String, List<String>> getRegisteredStudentData() {
        return registeredStudentData;
    }

  	public static void setRegisteredStudentData(Map<String, List<String>> registeredStudentData) {
        DataStore.registeredStudentData = registeredStudentData; 
    }    

}
