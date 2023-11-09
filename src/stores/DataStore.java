package stores;

import java.util.HashMap;
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
	private static Map<String, Staff> StaffData = new HashMap<String, Staff>();
	private static Map<Integer, Camp> CampData = new HashMap<Integer, Camp>();
	private static Map<Integer, Enquiry> EnquiryData = new HashMap<Integer, Enquiry>(); 
    private static Map<Integer, Suggestion> SuggestionData = new HashMap<Integer, Suggestion>(); 

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

		DataStore.StaffData = fileDataService.importStaffData(
            filePathsMap.get("user"),
			filePathsMap.get("Staff"));

		DataStore.CampData = fileDataService.importCampData(
            filePathsMap.get("Camp"),
            filePathsMap.get("user"), 
            filePathsMap.get("student"), 
            filePathsMap.get("Staff"));

		return true;
	}

	public static boolean saveData() {
		DataStore.setStudentsData(studentsData);
		DataStore.setStaffData(StaffData);
		DataStore.setCampData(CampData);
		return true;
	}

	public static Map<String, Student> getStudentsData() {
		return DataStore.studentsData;
	}

	public static void setStudentsData(Map<String, Student> studentsData) {
		DataStore.studentsData = studentsData;
		fileDataService.exportStudentData(filePathsMap.get("user"), filePathsMap.get("student"), studentsData);
	}

	public static Map<String, Staff> getStaffData() {
		return DataStore.StaffData;
	}

	public static void setStaffData(Map<String, Staff> StaffData) {
		DataStore.StaffData = StaffData;
		fileDataService.exportStaffData(filePathsMap.get("user"), filePathsMap.get("Staff"), StaffData);
	}

	public static Map<Integer, Camp> getCampData() {
		return DataStore.CampData;
	}

	public static void setCampData(Map<Integer, Camp> CampData) {
		DataStore.CampData = CampData;
		fileDataService.exportCampData(filePathsMap.get("Camp"), CampData);
	}

	public static Map<Integer, Enquiry> getEnquiryData() {
        return EnquiryData;
    }

   	public static void setEnquiryData(Map<Integer, Enquiry> enquiryData) {
        DataStore.EnquiryData = enquiryData;
    }

  	public static Map<Integer, Suggestion> getSuggestionData() {
        return SuggestionData;
    }
  	public static void setSuggestionData(Map<Integer, Suggestion> suggestionData) {
        DataStore.SuggestionData = suggestionData; 
    }    
}
