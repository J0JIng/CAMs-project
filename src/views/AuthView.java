package views;

import interfaces.IMenuView;
import utility.ViewUtility;

/**
 * The {@link AuthView} class implements the {@link IMenuView} interface and is responsible
 * for displaying the authentication menu with options for staff login, student login,
 * camp committee login, and quitting the application.
 */
public class AuthView implements IMenuView {

<<<<<<< HEAD
	/**
     * Displays the authentication menu with options for staff login, student login,
     * camp committee login, and quitting the application. Users can choose an option
     * by entering the corresponding option number.
     */
	@Override
	public void displayMenuView() {
	   //ViewUtility.clearScreen(); // comment out to debug
		System.out.println("╔══════════════════════════════════════════════════════════╗");
		System.out.println("║                                                          ║");   
=======
	@Override
	public void displayMenuView() {
		ViewUtility.clearScreen();
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");   
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
		System.out.println("║             ██████╗ █████╗ ███╗   ███╗███████╗           ║");
		System.out.println("║            ██╔════╝██╔══██╗████╗ ████║██╔════╝           ║");
		System.out.println("║            ██║     ███████║██╔████╔██║███████╗           ║");
		System.out.println("║            ██║     ██╔══██║██║╚██╔╝██║╚════██║           ║");
		System.out.println("║            ╚██████╗██║  ██║██║ ╚═╝ ██║███████║           ║");
		System.out.println("║             ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝           ║");
<<<<<<< HEAD
		System.out.println("║                                                          ║");                                      
		System.out.println("╠══════════════════════════════════════════════════════════╣");
		System.out.println("║                    - User Selection -                    ║");
		System.out.println("╠══════════════════════════════════════════════════════════╣");
		ViewUtility.displayInMenuNumbered("Staff Login", 1);
		ViewUtility.displayInMenuNumbered("Student Login", 2);
		ViewUtility.displayInMenuNumbered("Camp Committee Login", 3);
		ViewUtility.displayInMenuNumbered("Quit", 4);
		System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
	
	/**
     * Displays a message thanking the user for using CAMS when quitting the application.
     */
=======
        System.out.println("║                                                          ║");                                      
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║                    - User Selection -                    ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║[1] Staff Login                                           ║");
        System.out.println("║[2] Student Login                                         ║");
		System.out.println("║[3] Camp Committee Login                                  ║");
        System.out.println("║[4] Quit                                                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
	
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
	public static void quitApp() {
		System.out.println();
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                Thank you for using CAMS!!                ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
    
<<<<<<< HEAD
	/**
     * Displays the view for staff login.
     */
=======
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
	public static void staffLoginView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                      - Staff Login -                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
	
<<<<<<< HEAD
	/**
     * Displays the view for student login.
     */
=======
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
	public static void studentLoginView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                     - Student Login -                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

<<<<<<< HEAD
	/**
     * Displays the view for camp committee login.
     */
	public static void campCommitteeLoginView() {
=======
	public static void CampCommitteeLoginView() {
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                 - Camp Committee Login -                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

<<<<<<< HEAD
	/**
     * Displays the view for staff logout.
     */
	public static void staffLogoutView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                     - Staff Logout -                     ║");
        System.out.println("║                       (Press Enter)                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
	
	/**
     * Displays the view for student logout.
     */
	public static void studentLogoutView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                    - Student Logout -                    ║");
        System.out.println("║                       (Press Enter)                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	/**
     * Displays the view for camp committee logout.
     */
	public static void CampCommitteeLogoutView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                 - Camp Committee Logout -                ║");
        System.out.println("║                       (Press Enter)                      ║");
=======
	public static void staffLogoutView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                      - Staff Logout -                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
	
	public static void studentLogoutView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                     - Student Logout -                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	public static void CampCommitteeLogoutView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                 - Camp Committee Logout -                ║");
>>>>>>> eefd642a51ec84d47395937eac077761227004ce
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
}
