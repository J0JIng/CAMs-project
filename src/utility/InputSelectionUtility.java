package utility;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import models.Camp;

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
    public static Camp campSelector(ArrayList<Camp> camps) {  
        while (true) {
            System.out.println("Camp Name");
            for (Camp camp : camps) {
                System.out.printf("%-20s\n", camp.getCampInformation().getCampName());
            }
    
            System.out.print("\nEnter the name of the camp you want to register for (or press Enter to return): ");
            String input = sc.nextLine();
    
            if (input.isEmpty()) { // If the input is empty (user pressed Enter), return
                return null;
            }
    
            Optional<Camp> optionalSelectedCamp = camps.stream()
                    .filter(camp -> camp.getCampInformation().getCampName().equalsIgnoreCase(input))
                    .findFirst();
    
            if (optionalSelectedCamp.isPresent()) {
                return optionalSelectedCamp.get();
            } else {
                System.out.println("Invalid camp name!");
            }
        }
    }      
}