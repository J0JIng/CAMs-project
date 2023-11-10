package utility;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import models.Camp;
import models.CampInformation;

public class InputSelectionUtility {

    private static final Scanner sc = new Scanner(System.in);

    public InputSelectionUtility() {
    };

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

    //public static CampInformation campSelector(){

    //}
    
    
    
}