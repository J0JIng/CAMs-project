package utility;

public class ViewUtility {
	/**
     *  Clears the console
     */
    public static void clearScreen(){  
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    } 
    
    /**
     * Displays string as a numbered entry in the menu
     * @param s the string in the entry
     * @param index the numbered index
     */
    public static void displayInMenuNumbered(String s, int index) {
    	if (index > 9) {
    		System.out.println("║[" + (index) + "] " + s + " ".repeat(53- s.length()) + "║");
    	} else {
    		System.out.println("║[" + (index) + "] " + s + " ".repeat(54- s.length()) + "║");
    	}
    }
    
    /**
     * Displays string as a numbered entry in the menu
     * @param s the string in the entry
     * @param index the numbered index
     */
    public static void displayInMenuBullet(String s) {
    	System.out.println("║- " + s + " ".repeat(56- s.length()) + "║");
    }
    
    /**
     * Displays string as a numbered entry in the menu
     * @param s the string in the entry
     * @param index the numbered index
     */
    public static void displayInMenuBulletLarge(String s) {
    	System.out.println("║- " + s + " ".repeat(90- s.length()) + "║");
    }
    
    /**
     * Displays string at the center of the menu
     * @param s the string to be centered
     */
    public static void displayInMenuCentered(String s) {
		int totalLength = s.length();
		int spaces = (58 - totalLength) / 2;
    	System.out.println("║" + " ".repeat(spaces) + s + " ".repeat(58 - totalLength - spaces) + "║");
    }
    
    /**
     * Displays string as a numbered entry in the menu
     * @param s the string in the entry
     * @param index the numbered index
     */
    public static void displayInMenuNumberedTwoColumns(String s, int index, int colValue1 , int colValue2) {
    	System.out.println("╠════════════════════════════════════════════════╬═════════╣");
    	String val = colValue1 + "/" + colValue2;
    	System.out.println("║[" + (index) + "] " + s + " ".repeat(44- s.length()) + "║ " + val + " ".repeat(8-val.length()) + "║");
    	//System.out.println("║[" + (index) + "] " + s + " ".repeat(46- s.length()) + "║ " + colValue1 + " / " + colValue2 + " ║");
    }
}
