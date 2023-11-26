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

/**
 * The {@link CsvFileDataService} class implements the {@link IFileDataService}
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
	 * Writes the given data to a CSV file located at the given file path.
	 *
	 * @param filePath the file path of the CSV file to write
	 * @param headers  the list of headers for the CSV file
	 * @param lines    the list of lines to write to the CSV file
	 * @return true if the data is written successfully, false otherwise
	 */
	public boolean writeCsvFile(String filePath, List<String> headers, List<String> lines) {
		try (FileWriter writer = new FileWriter(filePath)) {
			// Write Headers
			String headerLine = String.join(",", headers);
			writer.write(headerLine + "\n");

			// Write Content
			for (String line : lines) {
				writer.write(line + "\n");
			}
		} catch (IOException e) {
			System.out.println("Cannot export data!");
			return false;
		}
		return true;
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
	
	/**
	 * Imports staff data from a CSV file, creates Staff objects, and stores them in a map.
	 *
	 * @param staffsFilePath The file path of the CSV file containing staff data.
	 * @return A map containing staff objects with user ID as the key.
	 * @throws IllegalArgumentException If the CSV file contains invalid or missing data.
	 */
	@Override
	public Map<String, Staff> importStaffData(String staffsFilePath) {
		// Initialize the map to store imported staff data
		Map<String, Staff> staffsMap = new HashMap<>();
		try {
			// Read rows from the CSV file
			List<String[]> staffsRows = readCsvFile(staffsFilePath, staffCsvHeaders);

			// Iterate through each row in the CSV file
			for (String[] staffRow : staffsRows) {
				// Parse user information from the row
				Map<String, String> userInfoMap = parseUserRow(staffRow);

				// Extract necessary information
				String name = userInfoMap.get("name");
				String email = userInfoMap.get("email");
				String facultyString = userInfoMap.get("faculty");

				// Validate and handle potential errors in data
				if (name == null || email == null || facultyString == null) {
					// Log an error or throw an exception, depending on your requirements
					System.err.println("Invalid data in CSV row: " + Arrays.toString(staffRow));
					continue; // Skip this row and move to the next one
				}

				// Convert faculty string to FacultyGroups enum
				FacultyGroups faculty = FacultyGroups.valueOf(facultyString.toUpperCase());

				// Extract user ID from the email
				int indexOfAtSymbol = email.indexOf('@');
				String userID = (indexOfAtSymbol != -1) ? email.substring(0, indexOfAtSymbol) : email;

				// Default values for optional attributes
				String password = "password";
				int numOfCampsManaged = 0;
				boolean isPasswordChanged = false;

				// Create a new Staff object
				Staff staff = new Staff(name, userID, email, faculty, password, numOfCampsManaged, isPasswordChanged);

				// Add the staff to the map using user ID as the key
				staffsMap.put(userID, staff);
			}

			// Log success message
			System.out.println("Imported " + staffsMap.size() + " staff members from " + staffsFilePath);

		} catch (Exception e) {
				// Handle exceptions (e.g., file not found, format issues)
				System.err.println("Error importing Staff data: " + e.getMessage());
				e.printStackTrace();
			}

		// Return the map containing imported staff data
		return staffsMap;
	}

	/**
	 * Imports student data from a CSV file, creates Student objects, and stores them in a map.
	 *
	 * @param studentsFilePath The file path of the CSV file containing student data.
	 * @return A map containing student objects with user ID as the key.
	 * @throws IllegalArgumentException If the CSV file contains invalid or missing data.
	 */
	@Override
	public Map<String, Student> importStudentData(String studentsFilePath) {
		Map<String, Student> studentsMap = new HashMap<>();

		try {
			List<String[]> studentRows = readCsvFile(studentsFilePath, studentCsvHeaders);

			for (String[] studentRow : studentRows) {
				Map<String, String> userInfoMap = parseUserRow(studentRow);

				String name = userInfoMap.get("name");
				String email = userInfoMap.get("email");
				String facultyString = userInfoMap.get("faculty");

				// Validate and handle potential errors in data
				if (name == null || email == null || facultyString == null) {
					// Log an error or throw an exception, depending on your requirements
					System.err.println("Invalid data in CSV row: " + Arrays.toString(studentRow));
					continue; // Skip this row and move to the next one
				}

				FacultyGroups faculty = FacultyGroups.valueOf(facultyString.toUpperCase());

				// parse userID from the email 
				int indexOfAtSymbol = email.indexOf('@');
				String userID = (indexOfAtSymbol != -1) ? email.substring(0, indexOfAtSymbol) : email;

				// Default values
				String password = "password";
				int studentPoints = 0;
				boolean isPasswordChanged = false;

				Student student = new Student(name, userID, email, faculty, password, studentPoints, isPasswordChanged);
				studentsMap.put(userID, student);
			}

			System.out.println("Imported " + studentsMap.size() + " students from " + studentsFilePath);
		} catch (Exception e) {
			// Handle exceptions (e.g., file not found, format issues)
			System.err.println("Error importing student data: " + e.getMessage());
			e.printStackTrace();
		}

		return studentsMap;
	}
}