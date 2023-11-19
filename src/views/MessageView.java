package views;

import java.util.Scanner;

import utility.ViewUtility;

public class MessageView {
    public static void endMessage(Scanner sc, String s, boolean large) {
    	if (sc.hasNextLine()) { 
			sc.nextLine();
		}
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
    }
}