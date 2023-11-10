package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.IFileDataService;
import enums.FacultyGroups;
import models.Student;
import models.Staff;

//import models.Enquiry;

/**
 * The CsvDataService} class implements the IFileDataService
 * interface and provides
 * methods for reading and writing data from/to CSV files.
 */
public class CsvFileDataService implements IFileDataService {

	/**
	 * The list of headers for the CSV file that stores student data.
	 */
	private static List<String> studentCsvHeaders = new ArrayList<String>();

	/**
	 * The list of headers for the CSV file that stores Staff data.
	 */
	private static List<String> staffCsvHeaders = new ArrayList<String>();


	/**
	 * Constructs an instance of the CsvFileDataService class.
	 */
    public CsvFileDataService() {}

	// ---------- Helper Function ---------- //
	/**
	 * Reads data from the CSV file located at the given file path and returns it as
	 * a list of string arrays.
	 *
	 * @param filePath the file path of the CSV file to read
	 * @param headers  the list of headers for the CSV file
	 * @return a list of string arrays containing the CSV data
	 */
	public List<String[]> readCsvFile(String filePath, List<String> headers) {
		List<String[]> dataList = new ArrayList<String[]>();
		headers.clear();
	
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			// Headers
			String[] headerRow = br.readLine().split(",");
			for (String header : headerRow) {
				headers.add(header);
			}
	
			// Content
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				dataList.add(values);
			}
	
		} catch (IOException e) {
			System.out.println("Cannot import data!");
		}
	
		return dataList;
	}
	
	/**
	 * Parses a string array containing user data and returns a map of user
	 * information.
	 *
	 * @param userRow the string array containing the user data
	 * @return a map of user information, where the keys are "userID", "password",
	 *         "email", "role", and "name" and the values are the corresponding
	 *         values in the userRow array
	 */
	private Map<String, String> parseUserRow(String[] userRow) {
		Map<String, String> userInfoMap = new HashMap<>();
	
		// Check if userRow has at least three elements and they are not empty
		if (userRow.length >= 3 && !userRow[0].isEmpty() && !userRow[1].isEmpty() && !userRow[2].isEmpty()) {
			String Name = userRow[0];
			String Email = userRow[1];
			String Faculty = userRow[2];
	
			userInfoMap.put("name", Name);
			userInfoMap.put("email", Email);
			userInfoMap.put("faculty", Faculty);
		} else {
			// Handle the case where userRow does not have enough elements or contains empty values
			System.out.println("Invalid userRow: " + Arrays.toString(userRow));
		}
	
		return userInfoMap;
	}
	
	// ---------- Interface method implementation ---------- //
	
	// Staff
	@Override
	public Map<String, Staff> importStaffData(String staffsFilePath) {
		Map<String, Staff> staffsMap = new HashMap<>();
		List<String[]> staffsRows = readCsvFile(staffsFilePath, staffCsvHeaders);

		for (String[] staffRow : staffsRows) {
			Map<String, String> userInfoMap = parseUserRow(staffRow);

			String name = userInfoMap.get("name");
			String email = userInfoMap.get("email");
			String facultyString = userInfoMap.get("faculty");
			FacultyGroups faculty = FacultyGroups.valueOf(facultyString.toUpperCase());

			// Extract userID from the email (assuming it's the first few characters)
			int indexOfAtSymbol = email.indexOf('@');
			String userID = (indexOfAtSymbol != -1) ? email.substring(0, indexOfAtSymbol) : email;

			// Default
			String password = "password";
			int numOfCampsManaged = 0;

			Staff staff = new Staff(name, userID, email, faculty, password, numOfCampsManaged);
			staffsMap.put(userID, staff);
		}
		return staffsMap;
	}

	@Override
	public Map<String, Student> importStudentData(String studentsFilePath){
		Map<String, Student> studentsMap = new HashMap<String, Student>();
		List<String[]> staffsRows = readCsvFile(studentsFilePath, studentCsvHeaders);

		for (String[] staffRow : staffsRows) {
			Map<String, String> userInfoMap = parseUserRow(staffRow);

			String name = userInfoMap.get("name");
			String email = userInfoMap.get("email");
			String facultyString = userInfoMap.get("faculty");
			FacultyGroups faculty = FacultyGroups.valueOf(facultyString.toUpperCase());

			// Extract userID from the email (assuming it's the first few characters)
			int indexOfAtSymbol = email.indexOf('@');
			String userID = (indexOfAtSymbol != -1) ? email.substring(0, indexOfAtSymbol) : email;

			// Default
			String password = "password";
			
			Student student = new Student(name, userID, email, faculty, password);
			studentsMap.put(userID, student);
		}
		return studentsMap;
	}
	
}