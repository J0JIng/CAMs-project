package utility;

<<<<<<< HEAD
/**
 * The {@link ViewUtility} class provides utility methods for displaying onto view menus.
 * It also allows the screen to be cleared.
 */
public class ViewUtility {
	
	/**
     *  Clears the console screen.
=======
import stores.AuthStore;

public class ViewUtility {
	/**
     *  Clears the console
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
     */
    public static void clearScreen(){  
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    } 
    
    /**
<<<<<<< HEAD
     * Displays a string as a numbered entry in the menu.
     *
     * @param s     The string in the menu entry.
     * @param index The numbered index.
     */
    public static void displayInMenuNumbered(String s, int index) {
    	if (53- s.length() < 0) {
            if (index > 9) {
                System.out.println("║[" + (index) + "] " + s + "║");
            } else {
                System.out.println("║[" + (index) + "] " + s + "║");
            }
            return;
        }
    	if (index > 9) {
    		System.out.println("║[" + (index) + "] " + s + " ".repeat(53- s.length()) + "║");
    	} else {
    		System.out.println("║[" + (index) + "] " + s + " ".repeat(54- s.length()) + "║");
    	}
    }
    
    /**
     * Displays a string as a bullet-pointed entry in the menu.
     *
     * @param s The string in the entry.
     */
    public static void displayInMenuBullet(String s) {
    	if (56- s.length() < 0) {
    		System.out.println("║- " + s + "║");
            return;
        }
=======
     * Displays string as a numbered entry in the menu
     * @param s the string in the entry
     * @param index the numbered index
     */
    public static void displayInMenuNumbered(String s, int index) {
    	System.out.println("║[" + (index) + "] " + s + " ".repeat(54- s.length()) + "║");
    }
    
    /**
     * Displays string as a numbered entry in the menu
     * @param s the string in the entry
     * @param index the numbered index
     */
    public static void displayInMenuBullet(String s) {
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
    	System.out.println("║- " + s + " ".repeat(56- s.length()) + "║");
    }
    
    /**
<<<<<<< HEAD
     * Displays a string as a bullet-pointed entry in the large menu.
     *
     * @param s The string in the entry.
     */
    public static void displayInMenuBulletLarge(String s) {
    	if (90- s.length() < 0) {
    		System.out.println("║- " + s + "║");
            return;
        }
    	System.out.println("║- " + s + " ".repeat(90- s.length()) + "║");
    }
    
    /**
     * Displays a string at the center of the menu.
     *
     * @param s The string to be centered.
=======
     * Displays string at the center of the menu
     * @param s the string to be centered
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
     */
    public static void displayInMenuCentered(String s) {
		int totalLength = s.length();
		int spaces = (58 - totalLength) / 2;
<<<<<<< HEAD
		if ((58 - totalLength - spaces) < 0) {
			System.out.println("║" + s + "║");
			return;
		}
=======
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
    	System.out.println("║" + " ".repeat(spaces) + s + " ".repeat(58 - totalLength - spaces) + "║");
    }
    
    /**
<<<<<<< HEAD
     * Displays a string at the center of the large menu.
     *
     * @param s The string to be centered.
     */
    public static void displayInLargeMenuCentered(String s) {
		int totalLength = s.length();
		int spaces = (92 - totalLength) / 2;
		if ((92 - totalLength - spaces) < 0) {
			System.out.println("║" + s + "║");
			return;
		}
    	System.out.println("║" + " ".repeat(spaces) + s + " ".repeat(92 - totalLength - spaces) + "║");
    }
    
    /**
     * Displays a string as a numbered entry in the two column menu with numerical entry on the right column.
     *
     * @param s         The string in the entry.
     * @param index     The numbered index.
     * @param colValue1 The first integer value for the right column.
     * @param colValue2 The second integer value for the right column.
     */
    public static void displayInMenuNumberedTwoColumns(String s, int index, int colValue1 , int colValue2) {
    	System.out.println("╠════════════════════════════════════════════════╬═════════╣");
    	String val = colValue1 + "/" + colValue2;
    	if ((40- s.length() < 0) || (8-val.length() < 0)) {
    		System.out.println("║[" + (index) + "] " + s + "║ " + val + "║");
    		return;
    	}
    	System.out.println("║[" + (index) + "] " + s + " ".repeat(44- s.length()) + "║ " + val + " ".repeat(8-val.length()) + "║");
=======
     * Displays string as a numbered entry in the menu
     * @param s the string in the entry
     * @param index the numbered index
     */
    public static void displayInMenuNumberedTwoColumns(String s, int index, int colValue1 , int colValue2) {
    	System.out.println("╠══════════════════════════════════════════════════╬═══════╣");
    	System.out.println("║[" + (index) + "] " + s + " ".repeat(46- s.length()) + "║ " + colValue1 + " / " + colValue2 + " ║");
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
    }
}
