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
import enums.UserRole;

import models.Camp;
import models.Student;
import models.Staff;

//import models.Enquiry;

/**
 * The {@link CsvDataService} class implements the {@link IFileDataService}
 * interface and provides
 * methods for reading and writing data from/to CSV files.
 */
public class CsvFileDataService implements IFileDataService {
	/**
	 * The list of headers for the CSV file that stores user data.
	 */
	private static List<String> userCsvHeaders = new ArrayList<String>();

	/**
	 * The list of headers for the CSV file that stores student data.
	 */
	private static List<String> studentCsvHeaders = new ArrayList<String>();

	/**
	 * The list of headers for the CSV file that stores Staff data.
	 */
	private static List<String> staffCsvHeaders = new ArrayList<String>();

	/**
	 * The list of headers for the CSV file that stores camp data.
	 */
	private static List<String> campCsvHeaders = new ArrayList<String>();

	/**
	 * Constructs an instance of the {@link CsvFileDataService} class.
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
			String line = "";
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
		String userID = userRow[0];
		String password = userRow[1];
		String email = userRow[2];
		String role = userRow[3];
		String name = userRow[4];

		// to handle names with comma
		for (int i = 5; i < userRow.length; i++) {
			if (i != 4)
				name += ",";
			name += userRow[i];
		}

		// Return
		Map<String, String> userInfoMap = new HashMap<String, String>();
		userInfoMap.put("userID", userID);
		userInfoMap.put("password", password);
		userInfoMap.put("email", email);
		userInfoMap.put("role", role);
		userInfoMap.put("name", name);

		return userInfoMap;
	}

	// ---------- Interface method implementation ---------- //
	// Student
	/* 
	@Override
	public Map<String, Student> importStudentData(String usersFilePath, String studentsFilePath) {
		Map<String, Student> studentsMap = new HashMap<String, Student>();

		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		List<String[]> studentsRows = this.readCsvFile(studentsFilePath, studentCsvHeaders);

		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);

			String role = userInfoMap.get("role");
			if (!role.equals("student"))
				continue;

			String userID = userInfoMap.get("userID");
			String password = userInfoMap.get("password");
			String name = userInfoMap.get("name");
			String email = userInfoMap.get("email");

			// get the associated student data
			boolean isDeregistered = false;
			for (String[] studentRow : studentsRows) {
				if (!studentRow[0].equals(userID))
					continue;

				isDeregistered = Boolean.parseBoolean(studentRow[1]);
			}

			Student student = new Student(userID, name, email, password, isDeregistered);

			studentsMap.put(userID, student);
		}

		return studentsMap;
	}
	*/
	/* 
	@Override
	public boolean exportStudentData(String usersFilePath, String studentsFilePath, Map<String, Student> studentMap) {
		List<String> studentLines = new ArrayList<String>();
		List<String> userLines = new ArrayList<String>();

		// User
		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);
			String userLine = String.format("%s,%s,%s,%s,%s",
					userInfoMap.get("userID"),
					userInfoMap.get("password"),
					userInfoMap.get("email"),
					userInfoMap.get("role"),
					userInfoMap.get("name"));

			if (userInfoMap.get("role").equals("student")) {
				Student student = studentMap.get(userInfoMap.get("userID"));

				userLine = String.format("%s,%s,%s,%s,%s",
						student.getUserID(),
						student.getPassword(),
						student.getEmail(),
						"student", // role
						student.getName());
			}

			userLines.add(userLine);
		}

		// Student
		for (Student student : studentMap.values()) {
			String studentLine = String.format("%s,%b",
					student.getStudentID(),
					student.getIsDeregistered());

			studentLines.add(studentLine);
		}

		// Write to CSV
		boolean success1 = this.writeCsvFile(usersFilePath, userCsvHeaders, userLines);
		boolean success2 = this.writeCsvFile(studentsFilePath, studentCsvHeaders, studentLines);
		return success1 && success2;
	}
	*/

	// Staff
	
	// Staff
	@Override
	public Map<String, Staff> importStaffData(String usersFilePath, String staffsFilePath) {
		Map<String, Staff> StaffsMap = new HashMap<String, Staff>();
		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		List<String[]> StaffsRows = this.readCsvFile(staffsFilePath, staffCsvHeaders);

		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);
			String role = userInfoMap.get("role");
			if (!role.equals("Staff"))
				continue;
			
			String name = userInfoMap.get("name");
			String userID = userInfoMap.get("userID");
			String email = userInfoMap.get("email");
			String faculty = userInfoMap.get("faculty");
			String password = userInfoMap.get("password");
			
			// get the associated Staff data
			int numOfCampsManaged = 0;
			for (String[] StaffRow : StaffsRows) {
				if (!StaffRow[0].equals(userID))
					continue;
				numOfCampsManaged = Integer.parseInt(StaffRow[1]);
			}
			Staff Staff = new Staff(name, userID, email, faculty, password, numOfCampsManaged);
			StaffsMap.put(userID, Staff);
		}
		return StaffsMap;
	}

	@Override
	public boolean exportStaffData(String usersFilePath, String staffsFilePath, Map<String, Staff> StaffMap) {
		List<String> StaffLines = new ArrayList<String>();
		List<String> userLines = new ArrayList<String>();
		// User
		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);
			String userLine = String.format("%s,%s,%s,%s,%s",
					userInfoMap.get("name"),
					userInfoMap.get("userID"),
					userInfoMap.get("email"),
					userInfoMap.get("password"),
					userInfoMap.get("faculty"),
					userInfoMap.get("role"));
			if (userInfoMap.get("role").equals("Staff")) {
				Staff Staff = StaffMap.get(userInfoMap.get("userID"));
				userLine = String.format("%s,%s,%s,%s,%s",
						Staff.getName(),
						Staff.getUserID(),
						Staff.getEmail(),
						Staff.getPassword(),
						Staff.getFaculty(),
						"Staff" // role
						);
			}
			userLines.add(userLine);
		}
		// Staff
		for (Staff Staff : StaffMap.values()) {
			String StaffLine = String.format("%s,%d",
					Staff.getStaffID(),
					Staff.getNumOfCampsManaged());
			StaffLines.add(StaffLine);
		}
		// Write to CSV
		boolean success1 = this.writeCsvFile(usersFilePath, userCsvHeaders, userLines);
		boolean success2 = this.writeCsvFile(staffsFilePath, staffCsvHeaders, StaffLines);
		return success1 && success2;
	}

	/* 
	// Camp
	@Override
	public Map<Integer, Camp> importCampData(String campFilePath, String usersFilePath,
			String studentsFilePath, String StaffsFilePath) {
		Map<Integer, Camp> campsMap = new HashMap<Integer, Camp>();

		List<String[]> campsRows = this.readCsvFile(campFilePath, campCsvHeaders);

		for (String[] campRow : campsRows) {
			int campID = Integer.parseInt(campRow[0]);
			String title = campRow[1];
			campStatus status = campStatus.valueOf(campRow[2]);
			String StaffID = campRow[3];
			String studentID = campRow.length > 4 ? campRow[4] : null;

			Camp camp = new Camp(campID, title, StaffID, studentID, status);

			campsMap.put(campID, camp);
		}

		return campsMap;
	}
	*/

	/* 
	@Override
	public boolean exportCampData(String campsFilePath, Map<Integer, Camp> campMap) {
		List<String> campLines = new ArrayList<String>();

		// Camp
		for (Camp camp : campMap.values()) {
			String campLine = String.format("%d,%s,%s,%s,%s",
					camp.getCampID(),
					camp.getTitle(),
					camp.getStatus(),
					camp.getStaff().getStaffID(),
					camp.getStudent() != null ? camp.getStudent().getStudentID() : "");

			campLines.add(campLine);
		}

		// Write to CSV
		return this.writeCsvFile(campsFilePath, campCsvHeaders, campLines);
		
	}
	*/
	
}