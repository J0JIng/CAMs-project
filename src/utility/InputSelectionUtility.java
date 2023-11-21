package utility;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Scanner;
import enums.FacultyGroups;

import java.util.ArrayList;
import java.util.Date;

import models.Staff;
import models.Suggestion;
import models.Camp;
import models.CampInformation;
import models.Enquiry;

import services.CampStaffService;
import views.MessageView;


public class InputSelectionUtility {

    private static final Scanner sc = new Scanner(System.in);
    private static final int MAX_COMMITTEE_SLOTS = 10;  
    private static final CampStaffService campStaffService = new CampStaffService();

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

     private static FacultyGroups validateFacultyGroup(String userInput) {
        try {
            return FacultyGroups.valueOf(userInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
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
    
    public static boolean toggleSelector(Camp selectedCamp) {
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
 
    /**
    * Takes in Inputs for creation of camp
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
        FacultyGroups faculty;
        do {
            String campUserGroup = getStringInput("Enter user group: ");
            faculty = validateFacultyGroup(campUserGroup);
    
            if (faculty == null) {
                System.out.println("Invalid user group. Please enter a valid user group.");
            }
        } while (faculty == null);
    
        return new CampInformation(campName, campStartDate, campEndDate, campRegistrationClosingDate,
                campLocation, campTotalSlots, campCommitteeSlots, campDescription, staff.getName(), faculty);
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
        	
    
            int choice = InputSelectionUtility.getIntInput("Enter your choice: ");
    
            switch (choice) {
                case 1:
                    // Update Camp Name
                    try {
                        String newCampName = getStringInput("Enter new camp name: ");
                        campStaffService.updateCampName(camp, newCampName);
                        System.out.println("Camp name updated successfully!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error updating camp name: " + e.getMessage());
                    }
                    break;
                case 2:
                    // Update Camp Start Date
                    try {
                        Date newStartDate = getDateInput("Enter new camp start date (dd/MM/yyyy): ", dateFormat);
                        campStaffService.updateCampStartDate(camp, newStartDate);
                        System.out.println("Camp start date updated successfully!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error updating camp start date: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Update Camp End Date
                    try {
                        Date newEndDate = getDateInput("Enter new camp end date (dd/MM/yyyy): ", dateFormat);
                        campStaffService.updateCampEndDate(camp, newEndDate);
                        System.out.println("Camp End date updated successfully!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error updating camp End date: " + e.getMessage());
                    }
                    break;
    
                case 4:
                    // Update registration End Date
                    try {
                        Date newRegistrationClosingDate = getDateInput("Enter new camp registration closing date (dd/MM/yyyy): ", dateFormat);
                        campStaffService.updateRegistrationEndDate(camp, newRegistrationClosingDate);
                        System.out.println("Camp Registration End date updated successfully!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error updating camp End date: " + e.getMessage());
                    }
                    break;
    
                case 5:
                    // Update Camp Location
                    String newLocation = getStringInput("Enter new camp location: ");

                    // Validate the new location
                    try {
                        campStaffService.updateCampLocation(camp, newLocation);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());  
                    }
                    break;
    
                case 6:
                    // Update Camp Total Slots
                    int newTotalSlots = getIntInput("Enter new camp total slots: ");
                
                    try {
                        campStaffService.updateCampTotalSlot(camp, newTotalSlots);
                        System.out.println("Camp total slots updated successfully.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage()); 
                    }
                    break;
                
                
                case 7:
                    // Update Camp Committee Total Slots
                    int newCommitteeSlots = getIntInput("Enter new camp committee slots: ");
                
                    try {
                        campStaffService.updateCampCommitteeSlots(camp, newCommitteeSlots);
                        System.out.println("Camp committee slots updated successfully.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage()); 
                    }
                    break;

                case 8:
                    // Update Camp Description
                    String newDescription = getStringInput("Enter new camp description: ");
                    try {
                        campStaffService.updateCampDescription(camp, newDescription);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());  
                    }
                    break;
    
                case 9:
                    // Validate and update the faculty User Group
                    String campUserGroup = getStringInput("Enter user group:");
                    try {
                        campStaffService.updateCampFacultyGroup(camp, campUserGroup);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());  
                    }

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

    public static Enquiry enquirySelector(Map<Integer, Enquiry> enquiries) {
        while (true) {
//
//            for (Map.Entry<Integer, Enquiry> entry : enquiries.entrySet()) {
//                System.out.println(entry.getKey() + ". " + entry.getValue().getEnquiryMessage());
//            }

            int enquiryID = getIntInput("Enter the ID of the enquiry (Enter 0 to return): ");
            
            if (enquiryID != 0) {
	            Enquiry selectedEnquiry = enquiries.get(enquiryID);
	
	            if (selectedEnquiry != null) {
	                return selectedEnquiry;
	            } else {
	                System.out.println("Invalid ID. Please enter a valid ID or Enter 0 to return.");
	            }
            } else {
            	//System.out.println("No enquiry selected");
            	return null;
            }
        }
    }

    public static Suggestion suggestionSelector(Map<Integer, Suggestion> suggestions) {
        while (true) {
//            System.out.println("Select a Suggestion:");
//
//            for (Map.Entry<Integer, Suggestion> entry : suggestions.entrySet()) {
//                System.out.println(entry.getKey() + ". " + entry.getValue().getSuggestionMessage());
//            }

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