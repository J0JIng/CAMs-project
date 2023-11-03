package views;

import interfaces.IMenuView;

public class AuthView implements IMenuView {

	@Override
	public void displayMenuView() {
        /* 
		System.out.println("-----------------------------------");
        System.out.println("|           Login Screen          |");
        System.out.println("|---------------------------------|");
        System.out.println("| 1. Login as Staff               |");
        System.out.println("| 2. Login as Student Bob         |");
        System.out.println("| 3. Quit                         |");
        System.out.println("-----------------------------------");
        System.out.print("Select an option: ");
        */
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");
        System.out.println("║          ░█████╗░░█████╗░███╗░░░███╗░██████╗             ║");
        System.out.println("║          ██╔══██╗██╔══██╗████╗░████║██╔════╝             ║");
        System.out.println("║          ██║░░╚═╝███████║██╔████╔██║╚█████╗░             ║");
        System.out.println("║          ██║░░██╗██╔══██║██║╚██╔╝██║░╚═══██╗             ║");
        System.out.println("║          ╚█████╔╝██║░░██║██║░╚═╝░██║██████╔╝             ║");
        System.out.println("║           ░╚════╝░╚═╝░░╚═╝╚═╝░░░░░╚═╝╚═════╝░            ║");                                      
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║                      -User Selection-                    ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║[1] Staff Login                                           ║");
        System.out.println("║[2] Student Login                                         ║");
        System.out.println("║[3] Quit                                                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");


	}
}
