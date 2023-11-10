package utility;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import enums.FacultyGroups;

import java.util.Date;

import models.Staff;
import models.Camp;
import models.CampInformation;
import stores.AuthStore;

public class InputSelectionUtility {

    private static final Scanner sc = new Scanner(System.in);

    public InputSelectionUtility() {
    };
    
    // ---------- Helper Function ---------- //

    /**
     * Gets an integer input from the user.
     *
     * @param prompt the prompt message
     * @return the parsed integer
     */
    public static int getIntInput(String prompt) {
        int value = 0;
        boolean isValid = false;

        while (!isValid) {
            System.out.print(prompt);

            if (sc.hasNextInt()) {
                value = sc.nextInt();
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); // Consume the invalid input
            }
        }

        return value;
    }

    /**
     * Gets a string input from the user.
     *
     * @param prompt the prompt message
     * @return the user input as a string
     */
    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    /**
     * Gets a unique camp name from the user.
     *
     * @param existingCamps List of existing camps to check for name uniqueness
     * @return the unique camp name
     */
    public static String getUniqueCampName(List<Camp> existingCamps) {
        boolean isUniqueName = false;
        String campName = "";

        while (!isUniqueName) {
            System.out.print("Enter camp name: ");
            String campNameCheck = sc.nextLine();
            campName = campNameCheck;

            boolean nameExists = existingCamps.stream()
                    .anyMatch(existingCamp -> existingCamp.getCampInformation().getCampName().equalsIgnoreCase(campNameCheck));

            if (!nameExists) {
                isUniqueName = true;
            } else {
                System.out.println("Camp with the same name already exists. Please choose a different name.");
            }
        }

        return campName;
    }

    /**
     * Gets a date input from the user.
     *
     * @param prompt     the prompt message
     * @param dateFormat the date format
     * @return the parsed date
     */
    public static Date getDateInput(String prompt, SimpleDateFormat dateFormat) {
        Date date = null;

        while (date == null) {
            System.out.print(prompt);
            String input = sc.nextLine();
            try {
                date = dateFormat.parse(input);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        return date;
    }

    // ---------- Utility method implementation ---------- //

    /**
     * Selects a Camp from a list of Camps by getting user input.
     *
     * @param Camps the list of available Camps
     * @return the selected camp or null if no camp is selected
     */
    public static Camp campSelector(List<Camp> camps) {
        while (true) {
            System.out.println("Select a Camp:");
    
            for (int i = 0; i < camps.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, camps.get(i).getCampInformation().getCampName());
            }
    
            System.out.print("Enter the index of the camp (or press Enter to return): ");
            String campIndexInput = sc.nextLine();
    
            if (campIndexInput.isEmpty()) { // If the input is empty (user pressed Enter), return
                return null;
            }
    
            int campIndex;
            try {
                campIndex = Integer.parseInt(campIndexInput) - 1; // Adjust index to start from 0
                if (campIndex < 0 || campIndex >= camps.size()) {
                    throw new NumberFormatException(); // Out of bounds
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid index. Please enter a valid index or press Enter to return.\n");
                continue;
            }
    
            return camps.get(campIndex);
        }
    }
      
    
    public static boolean toggleSelector(List<Camp> camps) {
        while (true) {
            System.out.println("Select a Camp by entering its index [Exit press Enter]:");
    
            for (int i = 0; i < camps.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, camps.get(i).getCampInformation().getCampName());
            }
    
            System.out.print("Enter the index of the camp you want to update (or press Enter to return): ");
            String campIndexInput = sc.nextLine();
    
            if (campIndexInput.isEmpty()) { // If the input is empty (user pressed Enter), return
                return false;
            }
    
            int campIndex;
            try {
                campIndex = Integer.parseInt(campIndexInput) - 1; // Adjust index to start from 0
                if (campIndex < 0 || campIndex >= camps.size()) {
                    throw new NumberFormatException(); // Out of bounds
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid index. Please enter a valid index or press Enter to return.\n");
                continue;
            }
    
            Camp selectedCamp = camps.get(campIndex);
    
            // Print menu
            System.out.println("Select Camp Visibility [Exit press Enter]:");
            System.out.println("1. On");
            System.out.println("0. Off");
    
            // Get user input
            String visibilityInput = sc.nextLine();
            int choice;
    
            if (visibilityInput.isEmpty()) { // If the input is empty (user pressed Enter), return current visibility
                return selectedCamp.getVisibility();
            } else if (visibilityInput.matches("[0-1]?")) { // If the input is a binary integer, proceed with the code
                choice = Integer.parseInt(visibilityInput);
            } else { // If the input is not empty and not a binary integer, prompt the user to enter again
                System.out.println("Invalid input. Please enter your choice or press Enter to return.\n");
                continue;
            }
    
            switch (choice) {
                case 0:
                    System.out.println("Camp visibility set to Off.");
                    return false;
    
                case 1:
                    System.out.println("Camp visibility set to On.");
                    return true;
    
                default:
                    System.out.println("Invalid choice. Please enter 0 or 1.");
            }
        }
    }

    private static FacultyGroups validateFacultyGroup(String userInput) {
        try {
            return FacultyGroups.valueOf(userInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static Camp createCampInput(List<Camp> camps, Staff staff, SimpleDateFormat dateFormat) {
        System.out.println("Creating a new camp...");

        String campName = getUniqueCampName(camps);
        Date campStartDate = InputSelectionUtility.getDateInput("Enter camp start date (dd/MM/yyyy): ", dateFormat);
        Date campEndDate = InputSelectionUtility.getDateInput("Enter camp end date (dd/MM/yyyy): ", dateFormat);
        Date campRegistrationClosingDate = InputSelectionUtility.getDateInput("Enter registration closing date (dd/MM/yyyy): ", dateFormat);
        String campLocation = InputSelectionUtility.getStringInput("Enter camp location: ");
        String campDescription = InputSelectionUtility.getStringInput("Enter camp description: ");
        int campTotalSlots = InputSelectionUtility.getIntInput("Enter total slots: ");
        int campCommitteeSlots = InputSelectionUtility.getIntInput("Enter committee slots: ");
        String campUserGroup = InputSelectionUtility.getStringInput("Enter user group:");
        FacultyGroups faculty = validateFacultyGroup(campUserGroup);

        while (faculty == null) {
            System.out.println("Invalid user group. Please Enter a valid user group.");
            campUserGroup = InputSelectionUtility.getStringInput("Enter user group:");
            faculty = validateFacultyGroup(campUserGroup);
        }

        return new Camp(new CampInformation(campName, campStartDate, campEndDate, campRegistrationClosingDate, 
        campLocation, campTotalSlots, campCommitteeSlots, campDescription, staff.getName(), faculty));
    }

    /**
     * Updates the details of the specified camp.
     *
     * @param camp   the {@link Camp} object to update
     * @param staff  the {@link Staff} making the update
     * @return true if the camp details were updated successfully, false otherwise
     */
    public static boolean updateCampInput(Camp camp, Staff staff , SimpleDateFormat dateFormat) {
        // Check if the camp is null
        if (camp == null) {
            System.out.println("Invalid camp selection. No changes were made.");
            return false;
        }

        // Check if the staffID matches the camp's creator
        if (!camp.getCampInformation().getCampStaffInCharge().equals(staff.getName())) {
            System.out.println("You are not authorized to update details for this camp.");
            return false;
        }

        boolean exit = false;

        while (!exit) {
            // Print menu for updating camp details
            System.out.println("Select a field to update:");
            System.out.println("1. Camp Name");
            System.out.println("2. Camp Start Date");
            System.out.println("3. Camp End Date");
            System.out.println("4. Camp Registration Closing Date");
            System.out.println("5. Camp Location");
            System.out.println("6. Camp Total Slots");
            System.out.println("7. Camp Description");
            System.out.println("8. Camp User Group");
            System.out.println("9. Exit");
    
            int choice = InputSelectionUtility.getIntInput("Enter your choice: ");
    
            switch (choice) {
                case 1:
                    // Update Camp Name
                    String newCampName = getStringInput("Enter new camp name: ");
                    camp.getCampInformation().setCampName(newCampName);
                    break;
    
                case 2:
                    // Update Camp Start Date
                    Date newStartDate = getDateInput("Enter new camp start date (dd/MM/yyyy): ", dateFormat);
                    camp.getCampInformation().setCampStartDate(newStartDate);
                    break;
    
                case 3:
                    // Update Camp End Date
                    Date newEndDate = getDateInput("Enter new camp end date (dd/MM/yyyy): ", dateFormat);
                    camp.getCampInformation().setCampEndDate(newEndDate);
                    break;
    
                case 4:
                    // Update Camp Registration Closing Date
                    Date newClosingDate = getDateInput("Enter new camp registration closing date (dd/MM/yyyy): ", dateFormat);
                    camp.getCampInformation().setCampRegistrationClosingDate(newClosingDate);
                    break;
    
                case 5:
                    // Update Camp Location
                    String newLocation = getStringInput("Enter new camp location: ");
                    camp.getCampInformation().setCampLocation(newLocation);
                    break;
    
                case 6:
                    // Update Camp Total Slots
                    int newTotalSlots = getIntInput("Enter new camp total slots: ");
                    camp.getCampInformation().setCampTotalSlots(newTotalSlots);
                    break;
    
                case 7:
                    // Update Camp Description
                    String newDescription = getStringInput("Enter new camp description: ");
                    camp.getCampInformation().setCampDescription(newDescription);
                    break;
    
                case 8:
                    String campUserGroup = InputSelectionUtility.getStringInput("Enter user group:");
		            FacultyGroups faculty = FacultyGroups.valueOf(campUserGroup.toUpperCase());
                    camp.getCampInformation().setFacultyGroup(faculty);
                    break;

                case 9:
                    // Exit editing loop
                    exit = true;
                    break;
    
                default:
                    System.out.println("Invalid choice. No changes were made.");
                    break;
            }
        }

        System.out.println("Camp details updated successfully.");
        return true;
    }
}