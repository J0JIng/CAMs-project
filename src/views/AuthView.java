package views;

import interfaces.IMenuView;
import utility.ViewUtility;

public class AuthView implements IMenuView {

	@Override
	public void displayMenuView() {
		ViewUtility.clearScreen(); // comment out to debug
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");   
		System.out.println("║             ██████╗ █████╗ ███╗   ███╗███████╗           ║");
		System.out.println("║            ██╔════╝██╔══██╗████╗ ████║██╔════╝           ║");
		System.out.println("║            ██║     ███████║██╔████╔██║███████╗           ║");
		System.out.println("║            ██║     ██╔══██║██║╚██╔╝██║╚════██║           ║");
		System.out.println("║            ╚██████╗██║  ██║██║ ╚═╝ ██║███████║           ║");
		System.out.println("║             ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝           ║");
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
	
	public static void quitApp() {
		System.out.println();
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                Thank you for using CAMS!!                ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
    
	public static void staffLoginView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                      - Staff Login -                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
	
	public static void studentLoginView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                     - Student Login -                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	public static void campCommitteeLoginView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                 - Camp Committee Login -                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	public static void staffLogoutView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                     - Staff Logout -                     ║");
        System.out.println("║                       (Press Enter)                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
	
	public static void studentLogoutView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                    - Student Logout -                    ║");
        System.out.println("║                       (Press Enter)                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}

	public static void CampCommitteeLogoutView() {
		System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                 - Camp Committee Logout -                ║");
        System.out.println("║                       (Press Enter)                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
	}
}
