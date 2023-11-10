package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import interfaces.ICampStaffService;

import enums.UserRole;
import enums.FacultyGroups;
import models.Camp;
import models.CampInformation;
import models.Staff;
import models.Student;
import stores.AuthStore;
import stores.DataStore;

public class CampStaffService implements ICampStaffService {
	Scanner scanner = new Scanner(System.in);

	// ---------- Helper Function ---------- //

	/**
     * Toggles the visibility of the specified camp to "on" or "off".
     *
     * @param camp      the {@link Camp} object to update
     * @param visibility the new visibility status ("on" or "off")
     * @return true if the camp visibility was updated successfully, false otherwise
     */
	public boolean toggleCampVisibility(Camp camp, boolean visibility){
		/* 		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
    	System.out.print("Enter Visibility: ");
    	boolean b = scanner.nextBoolean();
        for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
                c.setVisibility(b);
                System.out.println(c.getCampInformation().getCampName() +" visibility set to " + b);
                return;
            }
        }
        System.out.println("Cannot find camp " + campName);
		*/
	}

	// ---------- interface method implementation ---------- //

	/**
     * Retrieves a list of all camps.
     *
     * @return an {@link List} of {@link Camp} objects representing all camps
     */
	@Override
	public List<Camp> getAllCamps() {
    	Map<String, Camp> campsData = DataStore.getCampData();
    	List<Camp> allCamps = new ArrayList<>(campsData.values());
    	return allCamps;
	}
	
	/**
     * Retrieves a list of camps created by the staff with the specified ID.
     *
     * @param staffID the ID of the {@link Staff}
     * @return an {@link List} of {@link Camp} objects representing the created camps
     */
	@Override
	public List<Camp> getStaffCreatedCamps() {
		Staff staff = (Staff) AuthStore.getCurrentUser();
		Map<String, Camp> campsData = DataStore.getCampData();

		List<Camp> staffCreatedCamps = campsData.values().stream()
				.filter(camp -> camp.getCampInformation().getCampStaffInCharge().equals(staff.getName()))
				.collect(Collectors.toList());

		return staffCreatedCamps;
	}
	
	/**
     * Retrieves a list of students attending a specific camp.
     *
     * @param camp the {@link Camp} object for which to retrieve the attendee list
     * @return an {@link List} of {@link Student} objects representing attendees
     */
	@Override
	public List<Student> getCampAttendeeList(Camp camp){
		
	}

	// ---------- Service method implementation ---------- //

	/**
     * Creates new camps from the provided list of camps.
     *
     * @param staffID the ID of the {@link Staff} creating the camp
     * @return true if the camps were created successfully, false otherwise
     */
    public boolean createCamp(Staff staffID){
		/* 
		boolean isUniqueName = false;
    	String campName = "";
		
		while (!isUniqueName) {
        	System.out.print("Enter camp name: ");
        	String campNameCheck = scanner.nextLine();
        	campName = campNameCheck; // Created for checking if camp name is already chosen
        	// Check if a camp with the same name already exists
        	boolean nameExists = CampServiceController.camps.stream().anyMatch(existingCamp -> existingCamp.getCampInformation().getCampName().equalsIgnoreCase(campNameCheck));

        	if (!nameExists) {
            	isUniqueName = true;
        	} else {
            	System.out.println("Camp with the same name already exists. Please choose a different name.");
        	}
    	}

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	Date campStartDate = null;
    	while (campStartDate == null) {
    		System.out.print("Enter camp start date (dd/MM/yyyy): ");
    		String StartDateOfCamp = scanner.nextLine();
    		try {
        		campStartDate = dateFormat.parse(StartDateOfCamp);
    		} catch (Exception e) {
        		System.out.println("Invalid date format for camp start date. Please try again.");
    		}
		}

    	Date campEndDate = null;
    	while (campEndDate == null) {
    		System.out.print("Enter camp end date (dd/MM/yyyy): ");
    		String EndDateOfCamp = scanner.nextLine();
    		try {
        		campEndDate = dateFormat.parse(EndDateOfCamp);
    		} catch (Exception e) {
        		System.out.println("Invalid date format for camp end date. Please try again.");
    		}
		}

    	Date campRegistrationClosingDate = null;
    	while (campRegistrationClosingDate == null) {
    		System.out.print("Enter registration closing date (dd/MM/yyyy): ");
    		String closingDate = scanner.nextLine();
    		try {
        		campRegistrationClosingDate = dateFormat.parse(closingDate);
    		} catch (Exception e) {
        		System.out.println("Invalid date format for camp closing date. Please try again.");
    		}
		}

        System.out.print("Enter user group: ");
        String campUserGroup = scanner.nextLine();

        System.out.print("Enter camp location: ");
        String campLocation = scanner.nextLine();

        System.out.print("Enter camp description: ");
        String campDescription = scanner.nextLine();

        System.out.print("Enter total slots: ");
        int campTotalSlots = scanner.nextInt();

        System.out.print("Enter committee slots: ");
        int campCommitteeSlots = scanner.nextInt();
        
		Camp c = new Camp(new CampInformation(campName, campStartDate, campEndDate, campRegistrationClosingDate, campUserGroup, campLocation, campTotalSlots, campCommitteeSlots, campDescription, AuthStore.getCurrentUser().getName()));
		CampServiceController.camps.add(c);
        System.out.println("Created " + c.getCampInformation().getCampName());
		*/
	}
	
	/**
     * Updates the details of the specified camp if the staff ID matches the
     * camp's creator.
     *
     * @param camp    the {@link Camp} object to update
     * @param details the new details for the camp
     * @param staffID the ID of the {@link Staff} making the update
     * @return true if the camp details were updated successfully, false otherwise
     */
    public boolean updateCampDetails(Camp camp, CampInformation details, Staff staffID){
		/*
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
				String newCampName = null;
            	boolean isUniqueName = false;

            	while (!isUniqueName) {
                	System.out.print("Enter new camp name: ");
                	String newCampNameCheck = scanner.nextLine(); // Created for checking if camp name is already chosen
                	newCampName = newCampNameCheck;
                	isUniqueName = CampServiceController.camps.stream().noneMatch(existingCamp -> existingCamp.getCampInformation().getCampName().equalsIgnoreCase(newCampNameCheck));

                	if (!isUniqueName) {
                    	System.out.println("Camp name is not unique. Please choose a different name.");
                	}
            	}

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    			Date campStartDate = null;
    			while (campStartDate == null) {
    				System.out.print("Enter camp start date (dd/MM/yyyy): ");
    				String StartDateOfCamp = scanner.nextLine();
    				try {
        				campStartDate = dateFormat.parse(StartDateOfCamp);
    				} catch (Exception e) {
        				System.out.println("Invalid date format for camp start date. Please try again.");
    				}
				}

    			Date campEndDate = null;
    			while (campEndDate == null) {
    				System.out.print("Enter camp end date (dd/MM/yyyy): ");
    				String EndDateOfCamp = scanner.nextLine();
    				try {
        				campEndDate = dateFormat.parse(EndDateOfCamp);
    				} catch (Exception e) {
        				System.out.println("Invalid date format for camp end date. Please try again.");
    				}
				}

    			Date campRegistrationClosingDate = null;
    			while (campRegistrationClosingDate == null) {
    				System.out.print("Enter registration closing date (dd/MM/yyyy): ");
    				String closingDate = scanner.nextLine();
    				try {
        				campRegistrationClosingDate = dateFormat.parse(closingDate);
    				} catch (Exception e) {
        				System.out.println("Invalid date format for camp closing date. Please try again.");
    				}
				}

		        System.out.print("Enter user group: ");
		        String campUserGroup = scanner.nextLine();

		        System.out.print("Enter camp location: ");
		        String campLocation = scanner.nextLine();

		        System.out.print("Enter camp description: ");
		        String campDescription = scanner.nextLine();

		        System.out.print("Enter total slots: ");
		        int campTotalSlots = scanner.nextInt();

		        System.out.print("Enter committee slots: ");
		        int campCommitteeSlots = scanner.nextInt();
		        
				c.setCampInformation(new CampInformation(newCampName, campStartDate, campEndDate, campRegistrationClosingDate, campUserGroup, campLocation, campTotalSlots, campCommitteeSlots, campDescription, AuthStore.getCurrentUser().getName()));
				System.out.println("Edited " + c.getCampInformation().getCampName());
                return;
            }
        }
		System.out.println("Cannot find " + campName);
		*/
	}
	
	/**
     * Deletes the specified camp if the staff ID matches the camp's creator.
     *
     * @param camp    the {@link Camp} object to delete
     * @param staffID the ID of the {@link Staff} making the delete request
     * @return true if the camp was deleted successfully, false otherwise
     */
    public boolean deleteCamp(Camp camp, Staff staffID){
		/* 
		scanner.nextLine();
    	System.out.print("Enter Camp Name: ");
    	String campName = scanner.nextLine();
		for (Camp c : CampServiceController.camps) {
			if (c.getCampInformation().getCampName().equalsIgnoreCase(campName)) {
                CampServiceController.camps.remove(c);
                System.out.println("Deleted " + campName);
                return;
            }
        }
        System.out.println("Error deleting " + campName);
		*/
	}

}

