package utility;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

import enums.FacultyGroups;
import interfaces.ICampUpdateService;
import models.Staff;
import models.Suggestion;
import models.Camp;
import models.CampInformation;
import models.Enquiry;
import services.CampUpdateService;

/**
 * The {@link InputSelectionUtility} class provides utility and helper methods for handling input of various forms.
 * Helper functions include receiving inputs of integers, Strings and Dates.
 * It also provides utility methods such as selecting a camp, getting enquiries and filter types.
 */
public class InputSelectionUtility {


    /**
	 * Scanner object for receiving input from the camp committee member.
	 */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * The {@link MAX_COMMITTEE_SLOTS} constant represents the maximum number of committee slots allowed which is set to 10.
     */
    private static final int MAX_COMMITTEE_SLOTS = 10;  

    /**
	 * Service for validating camp-related operations for the camp committee.
	 */
    private static final ICampUpdateService campUpdateService = new CampUpdateService();

    /**
	 * Private constructor  {@link InputSelectionUtility} to prevent instantiation of the class.
	 */
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
        sc.nextLine(); // Consumes the nextline character
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
     * @param camps the list of available Camps
     * @return the selected camp or null if no camp is selected
     */
    public static Camp getSelectedCamp(List<Camp> camps) {
        while (true) {
            System.out.println("Select a Camp: (Press Enter to Return)");
    
            //System.out.print("Enter the index of the camp (or press Enter to return): ");
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
    
    /**
     * Prompts the user with a message, asking user to confirm their choice with a boolean yes or no.
     *
     * @param prompt The message to display as a prompt.
     * @return {@code true} if the user enters 'y' for yes, {@code false} if the user enters 'n' for no.
     */
    public static boolean getYesNoInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim().toLowerCase();

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
    
    /**
     * Obtains a boolean input for setting the visibility of a camp.
     *
     * @param selectedCamp The camp for which visibility is being set.
     * @return {@code true} if the user selects 1 for "On," {@code false} if the user selects 0 for "Off,"
     *         or prompts the user to enter again if an invalid choice is sent.
     */
    public static boolean getSelectedBoolean(Camp selectedCamp) {
        while (true) {
            
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
                    return false;
    
                case 1:
                    return true;
    
                default:
                    System.out.println("Invalid choice. Please enter 0 or 1.");
            }
        }
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
            String campNameCheck = getStringInput("Enter camp name: ");
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
     * Collects input to create a {@code CampInformation} object.
     *
     * @param allCamps   The list of all existing camps to ensure the uniqueness of the camp name.
     * @param staff      The staff in charge for the camp.
     * @param dateFormat The date format to be used for parsing date inputs.
     * @return A {@code CampInformation} object containing details of a new camp.
     */
    public static CampInformation getCampInput(List<Camp> allCamps, Staff staff, SimpleDateFormat dateFormat) {
        // get Unique Camp Name
        String campName = getUniqueCampName(allCamps);
    
        // get the registration closing date
        Date campRegistrationClosingDate;
        do {
            campRegistrationClosingDate = getDateInput("Enter camp registration closing date (dd/MM/yyyy) ", dateFormat);
            if (campRegistrationClosingDate.before(new Date())) {
                System.out.println("Camp registration closing date must be in the present. Please enter a valid date.");
            }
        } while (campRegistrationClosingDate.before(new Date()));
    
        // Validate and get the camp start date
        Date campStartDate;
        do {
            campStartDate = getDateInput("Enter camp start date (dd/MM/yyyy): ", dateFormat);
            if (campStartDate.before(campRegistrationClosingDate)) {
                System.out.println("Camp start date must be after the registration closing date. Please enter a valid date.");
            }
        } while (campStartDate.before(campRegistrationClosingDate));
    
        // Validate and get the camp end date
        Date campEndDate;
        do {
            campEndDate = getDateInput("Enter camp end date (dd/MM/yyyy): ", dateFormat);
            if (campEndDate.before(campStartDate)) {
                System.out.println("Camp end date must be after the start date. Please enter a valid date.");
            }
        } while (campEndDate.before(campStartDate));
    
        String campLocation = getStringInput("Enter camp location: ");
        String campDescription = getStringInput("Enter camp description: ");
    
        int campCommitteeSlots = getIntInput("Enter committee slots: ");
        if (campCommitteeSlots > MAX_COMMITTEE_SLOTS) {
            campCommitteeSlots = MAX_COMMITTEE_SLOTS;
            System.out.println("Input camp committee slots greater than allowed camp committee slots. Maximum default of 10 is set.");
        }
    
        // validate and get total camp slots
        int campTotalSlots;
        do {
            campTotalSlots = getIntInput("Enter total slots: ");
            // Check if total slots are greater than or equal to camp committee slots
            if (campTotalSlots < campCommitteeSlots) {
                System.out.println("Total slots must be greater than or equal to camp committee slots. Please enter the values again.");
                continue; // Continue to the next iteration of the loop
            } else {
                break;
            }
        } while (true);
    
        // Validate and get the faculty User Group
        FacultyGroups faculty = null;
        while (faculty == null) {
            String campUserGroup = getStringInput("Enter user group: ");
            
            try {
                faculty = FacultyGroups.valueOf(campUserGroup.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid user group. Please enter a valid user group.");
            }
        }
    
        return new CampInformation(campName, campStartDate, campEndDate, campRegistrationClosingDate,
                campLocation, campTotalSlots, campCommitteeSlots, campDescription, staff.getName(), faculty);
    }

    /**
     * Updates the details of the specified camp.
     *
     * @param camp   the {@link Camp} object to update
     * @param staff  the {@link Staff} making the update
     * @param dateFormat The date format to be used for parsing date inputs.
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
        	
    
            int choice = InputSelectionUtility.getIntInput("Enter your choice: ");
    
            switch (choice) {
                case 1:
                    // Update Camp Name
                    String newCampName = getStringInput("Enter new camp name: ");
                    campUpdateService.updateCampName(camp, newCampName);
                    break;

                case 2:
                    // Update Camp Start Date
                    Date newStartDate = getDateInput("Enter new camp start date (dd/MM/yyyy): ", dateFormat);
                    campUpdateService.updateCampStartDate(camp, newStartDate);
                    break;

                case 3:
                    // Update Camp End Date
                    Date newEndDate = getDateInput("Enter new camp end date (dd/MM/yyyy): ", dateFormat);
                    campUpdateService.updateCampEndDate(camp, newEndDate);
                    break;
    
                case 4:
                    // Update registration End Date
                    Date newRegistrationClosingDate = getDateInput("Enter new camp registration closing date (dd/MM/yyyy): ", dateFormat);
                    campUpdateService.updateRegistrationEndDate(camp, newRegistrationClosingDate);
                    break;
    
                case 5:
                    // Update Camp Location
                    String newLocation = getStringInput("Enter new camp location: ");
                    campUpdateService.updateCampLocation(camp, newLocation);
                    break;
    
                case 6:
                    // Update Camp Total Slots
                    int newTotalSlots = getIntInput("Enter new camp total slots: ");
                    campUpdateService.updateCampTotalSlot(camp, newTotalSlots);
                    break;
                
                
                case 7:
                    // Update Camp Committee Total Slots
                    int newCommitteeSlots = getIntInput("Enter new camp committee slots: ");
                    campUpdateService.updateCampCommitteeSlots(camp, newCommitteeSlots);
                    break;

                case 8:
                    // Update Camp Description
                    String newDescription = getStringInput("Enter new camp description: ");
                    campUpdateService.updateCampDescription(camp, newDescription);
                    break;
    
                case 9:
                    // Validate and update the faculty User Group
                    String campUserGroup = getStringInput("Enter user group:");
                    campUpdateService.updateCampFacultyGroup(camp, campUserGroup);
                    break;

                case 10:
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

    /**
     * Gets the selected enquiry based on user input.
     * User either enters a valid ID or return to exit enquiry input
     *
     * @param enquiries A map containing enquiries with their corresponding IDs.
     * @return The selected {@code Enquiry} object, or {@code null} if the user chooses to return.
     */
    public static Enquiry getSelectedEnquiry(Map<Integer, Enquiry> enquiries) {
        while (true) {
            int enquiryID = getIntInput("Enter the ID of the enquiry (Enter 0 to return): ");
            if (enquiryID != 0) {
	            Enquiry selectedEnquiry = enquiries.get(enquiryID);
	
	            if (selectedEnquiry != null) {
	                return selectedEnquiry;
	            } else {
	                System.out.println("Invalid ID. Please enter a valid ID or Enter 0 to return.");
	            }
            } else {
            	return null;
            }
        }
    }

    /**
     * Gets the selected suggestion based on user input.
     * User either enters a valid ID or return to exit Suggestion input
     *
     * @param suggestions A map containing suggestions with their corresponding IDs.
     * @return The selected {@code Suggestion} object, or {@code null} if the user chooses to return.
     */
    public static Suggestion getSelectedSuggestion(Map<Integer, Suggestion> suggestions) {
        while (true) {
            int suggestionID = getIntInput("Enter the ID of the suggestion (Enter 0 to return): ");
            if (suggestionID != 0) {
	            Suggestion selectedSuggestion = suggestions.get(suggestionID);
	
	            if (selectedSuggestion != null) {
	                return selectedSuggestion;
	            } else {
	                System.out.println("Invalid ID. Please enter a valid ID or Enter 0 to return.");
	            }
            } else {
            	return null;
            }
        }
    }

    /**
     * Gets the selected filters based on user input.
     * Some filters include No filter, Camp Information, Attendee and Location.
     *
     * @return A list of selected filters, including the filter option.
     */
    public static List<String> getFilterInput() {
        List<String> selectedFilters = new ArrayList<>();
        int option;

        do {
            option = InputSelectionUtility.getIntInput("Enter the filter option (1/2/3/4/5/6): ");
            selectedFilters.clear(); // Clear the list before processing new filters
            boolean filterByName = false;
            String selectedFilter = null;

            switch (option) {
                case 1:
                    selectedFilter = "No Filter";
                    break;
                case 2:
                    selectedFilter = "Camp Information";
                    break;
                case 3:
                    selectedFilter = "Attendee";
                    filterByName = getYesNoInput("Filter by Specific Name? (y/n): ");
                    break;
                case 4:
                    selectedFilter = "Camp Committee";
                    filterByName = getYesNoInput("Filter by Specific Name? (y/n): ");
                    break;
                case 5:
                    selectedFilter = "Location";
                    break;
                case 6:
                    selectedFilter = "Date";
                    break;
                default:
                    System.out.println("Invalid option. Please enter a valid filter option.");
                    continue;
            }

            // Add filter to the list
            selectedFilters.add(selectedFilter);

            // Add name if filtering by name
            if (filterByName) {
                String nameInput = getStringInput("Enter the Name: ");
                selectedFilters.add(nameInput);
            }

            // Print selected filters after each choice            
            System.out.print("Selected Filters: ");
            for (String filter : selectedFilters) {
                System.out.print(filter + " ");
            }
            System.out.println();

            // Ask for confirmation
            boolean confirmed = getYesNoInput("Confirm your selection? (y/n)? : ");
            if (confirmed) {
                break; 
            }

        } while (true);

        return selectedFilters;
    }

    /**
     * Gets the selected performance filters based on user input.
     * Filters include No filter, Committee ID, Committee Name and Committee Points.
     *
     * @return A list of selected performance filters, including the filter option.
     */
    public static List<String> getPerformanceFilterInput() {
        List<String> selectedFilters = new ArrayList<>();
        int option;

        do {
            option = getIntInput("Enter the filter option (1/2/3/4): ");
            selectedFilters.clear(); // Clear the list before processing new filters
            boolean filterByName = false;
            String selectedFilter = null;

            switch (option) {
                case 1:
                    selectedFilter = "No Filter";
                    break;
                case 2:
                    selectedFilter = "Committee ID";
                    break;
                case 3:
                    selectedFilter = "Committee Name";
                    filterByName = getYesNoInput("Filter by Specific Name? (y/n): ");
                    break;
                case 4:
                    selectedFilter = "Committee Points";
                    break;
                default:
                    System.out.println("Invalid option. Please enter a valid filter option.");
                    continue;
            }

            // Add filter to the list
            selectedFilters.add(selectedFilter);

            // Add name if filtering by name
            if (filterByName) {
                String nameInput = getStringInput("Enter the Name: ");
                selectedFilters.add(nameInput);
            }

            // Print selected filters after each choice         
            System.out.print("Selected Filters: ");
            for (String filter : selectedFilters) {
                System.out.print(filter + " ");
            }
            System.out.println();

            // Ask for confirmation
            boolean confirmed = getYesNoInput("Confirm your selection? (y/n)? : ");
            if (confirmed) {
                break; 
            }
    
        } while (true);
    
        return selectedFilters;
    }
    
    /**
     * Gets the selected enquiry filters based on user input.
     * Filters include No filter, Enquiry ID, Sender ID, Responder ID and Enquiry Status.
     *
     * @return A list of selected enquiry filters, including the filter option.
     */
    public static List<String> getEnquiryFilterInput() {
        List<String> selectedFilters = new ArrayList<>();
        int option;
        do {
            option = getIntInput("Enter the filter option (1/2/3/4/5/6/7): ");
            selectedFilters.clear(); // Clear the list before processing new filters
            boolean filterByName = false;
            String selectedFilter = null;

            switch (option) {
                case 1:
                    selectedFilter = "No Filter";
                    break;
                case 2:
                    selectedFilter = "Enquiry ID";
                    break;
                case 3:
                    selectedFilter = "Sender ID";
                    filterByName = getYesNoInput("Filter by Specific Name? (y/n): ");
                    break;
                case 4:
                    selectedFilter = "Responder ID";
                    filterByName = getYesNoInput("Filter by Specific Name? (y/n): ");
                    break;
                case 5:
                    selectedFilter = "Enquiry Status";
                    break;
                case 6:
                    selectedFilter = "Enquiry Message";
                    break;
                case 7:
                    selectedFilter = "Enquiry Response";
                    break;
                default:
                    System.out.println("Invalid option. Please enter a valid filter option.");
                    continue;
            }

            // Add filter to the list
            selectedFilters.add(selectedFilter);

            // Add name if filtering by name
            if (filterByName) {
                String nameInput = getStringInput("Enter the Name: ");
                selectedFilters.add(nameInput);
            }

            // Print selected filters after each choice            
            System.out.print("Selected Filters: ");
            for (String filter : selectedFilters) {
                System.out.print(filter + " ");
            }
            System.out.println();

            // Ask for confirmation
            boolean confirmed = getYesNoInput("Confirm your selection? (y/n)? : ");
            if (confirmed) {
                break; 
            }
    
        } while (true);
    
        return selectedFilters;
    }
    

}