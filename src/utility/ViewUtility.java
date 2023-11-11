package utility;

import stores.AuthStore;

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
    	System.out.println("║[" + (index) + "] " + s + " ".repeat(54- s.length()) + "║");
    }
    
    /**
     * Displays string as a numbered entry in the menu
     * @param s the string in the entry
     * @param index the numbered index
     */
    public static void displayInMenuBullet(String s) {
    	System.out.println("║- " + s + " ".repeat(56- s.length()) + "║");
    }
    
    public static void displayInMenuCentered(String s) {
		int totalLength = s.length();
		int spaces = (58 - totalLength) / 2;
    	System.out.println("║" + " ".repeat(spaces) + s + " ".repeat(58 - totalLength - spaces) + "║");
    }
}
