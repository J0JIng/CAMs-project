package utility;

public class ViewUtility {
	/**
     *  Clears the console
     */
    public static void clearScreen(){  
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    } 
}
