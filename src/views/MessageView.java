package views;

import java.util.Scanner;

import utility.ViewUtility;

/**
 * The {@link MessageView} class provides methods for displaying messages in a boxed format 
 * and clearing the scanner buffer.
 */
public class MessageView {
	
	/**
     * Displays an end message in a boxed format if required and waits for the user to press Enter.
     *
     * @param sc     The Scanner object to read user input.
     * @param s      The optional message to be displayed.
     * @param large  A flag indicating whether to use a large menu format.
     */
    public static void endMessage(Scanner sc, String s, boolean large) {
    	clearBuffer(sc);
        sc.nextLine();
        if (s != null) {
            if (large == false) {
                System.out.println("╔══════════════════════════════════════════════════════════╗");
                ViewUtility.displayInMenuCentered(s);
                ViewUtility.displayInMenuCentered("(Press Enter to Return)");
                System.out.println("╚══════════════════════════════════════════════════════════╝");
            } else {
            	System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════╗");
                ViewUtility.displayInLargeMenuCentered(s);
                ViewUtility.displayInLargeMenuCentered("(Press Enter to Return)");
                System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");
            }            
        } else {
        	System.out.println("(Press Enter to Return)");
        }
		sc.nextLine();
        ViewUtility.clearScreen();
    }
    
    /**
     * Clears the input buffer of the provided Scanner object.
     *
     * @param scanner The Scanner object's buffer to be cleared.
     */
    public static void clearBuffer(Scanner scanner) {
    	try {
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}