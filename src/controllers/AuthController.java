package controllers;

import java.util.Scanner;

import interfaces.IAuthService;
import models.User;
import services.AuthStudentService;
import services.AuthCampCommitteeService;
import services.AuthStaffService;
import stores.AuthStore;
import utility.InputSelectionUtility;
import views.AuthView;


/**
 * {@link AuthController} class provides utility methods for managing user authentication within the application. 
 * It offers methods to start and end user sessions, as well as handle user login and logout. This
 * class utilizes the {@link IAuthService} interface for handling the
 * authentication process.
 */
public class AuthController {
    
	/**
	 * Scanner object to allow input from user
	 */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * authService object that implements the {@link IAuthService} interface,
     * allows the AuthController to perform authentication services.
     */
    private static IAuthService authService;
    
    /**
	 * Private constructor to prevent instantiation of the class.
	 */
    private AuthController() {};

    /**
     * Starts a user session by prompting the user to select their role and
     * enter their credentials. The method loops until valid credentials are
     * provided or the system is shut down.
     */
    public static void startSession() {
        int choice;
        boolean authenticated = false;
        AuthView view = new AuthView();
        do {
            view.displayMenuView();
        	choice = InputSelectionUtility.getIntInput("Enter your user type: ");

            switch (choice) {
                case 1:
                    authService = new AuthStaffService();
                    break;
                case 2:
                    authService = new AuthStudentService();
                    break;
                case 3:
                    authService = new AuthCampCommitteeService();
                    break;
                case 4:
                    System.out.println("Shutting down CAMs...");
                    AuthView.quitApp();
                    authService = null; // Set authService to null    
                    return;
        	}

            String userID, password;
            System.out.print("UserID: ");
            userID = sc.nextLine();

            System.out.print("Password: ");
            password = sc.nextLine();

            authenticated = authService.login(userID, password);
            if (!authenticated) {
                // We do not specify the source of error to make it more secure
                System.out.println("Credentials invalid! Note that UserID and Password are case-sensitive.\n");
                System.out.println("(Please press enter to return)");
                sc.nextLine();
            }

            if(authenticated){
                User user = (User) AuthStore.getCurrentUser();
                if(!user.getIsPasswordChanged()){
                    // // Change default password once
                    System.out.println("Please Change the Default Password.\n");
                    if(!UserController.changePassword()){
                       System.out.println("Password change cancelled. Returning to main menu");
                       authService = null;
                       authenticated = false;
                    }
                }
            }
            
        } while (!authenticated);
    }

    /**
     * Ends the current user session by logging the user out and displaying a
     * logout message.
     */
    public static void endSession() {
        // Check if authService is not null before calling methods on it
        if (authService != null) {
            authService.logout();
            switch (authService.getClass().getName()) {
            case "services.AuthStaffService":
            	AuthView.staffLogoutView();
            	break;
            case "services.AuthStudentService":
            	AuthView.studentLogoutView();
            	break;
            case "services.AuthCampCommitteeService":
            	AuthView.CampCommitteeLogoutView();
            	break;
			default:
				break;
            }
            //System.out.println("User logged out successfully. (Press Enter)");
        } else {
            System.out.println("Error: AuthService is null.");
        }

        // Reset the input stream (System.in) to avoid potential issues
        sc.nextLine();
    }
}