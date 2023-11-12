package views;

import interfaces.IMenuView;
import utility.ViewUtility;

public class AuthView implements IMenuView {


	@Override
	public void displayMenuView() {
		ViewUtility.clearScreen();
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
        System.out.println("║[1] Staff Login                                           ║");
        System.out.println("║[2] Student Login                                         ║");
        System.out.println("║[3] Quit                                                  ║");
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
}
