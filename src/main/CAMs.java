package main;


import controllers.AuthController;
import controllers.StudentController;
import controllers.StaffController;
import models.User;
import services.CsvFileDataService;
import stores.AuthStore;
import stores.DataStore;
import utility.FilePathsUtility;
import views.Boundary;

/**
 * The main class responsible for running the FYPMS application.
 * 
 * <p>
 * This class handles initializing of the data store, authentication for users
 * to log in, and starting the appropriate session based on the role of the
 * logged-in user.
 * </p>
 */
public class CAMs {
	/**
	 * Private constructor to prevent instantiation of the class.
	 */
	private CAMs() {
	};

	/**
	 * The entry point for the FYPMS application. This method is responsible for
	 * running an infinite loop to allow multiple users to operate the application.
	 * 
	 * @param args an array of String arguments passed to this method
	 */
	public static void main(String[] args) {
		try {
			do {
				// Initialize DataStore
				DataStore.initDataStore(new CsvFileDataService(), FilePathsUtility.csvFilePaths());

				// Display Splash Screen
				// Boundary.printSplashScreen(); //TODO

				// Authentication - Log In
				AuthController.startSession();
				if (!AuthStore.isLoggedIn())
					return;

				// Start session
				User user = AuthStore.getCurrentUser();
				switch (user.getUserRole()) {
					case Student:
						new StudentController().start();
						break;
					case Staff:
						new StaffController().start();
						break;
				}
			} while (true);
		} catch (Exception e) {
			// Save Data and Logout user
			DataStore.saveData();
			AuthController.endSession();

			// Print message
			System.out.println("CAMS crashed. Please restart the system.");
			System.out.println("Error: " + e.getMessage());
		}
	}
}













































/* 
import java.util.Scanner;
import enums.AccessLevel;
import models.*;
import controllers.StaffController;
import controllers.StudentController;
import stores.AuthStore;
import views.AuthView;

import java.util.Map;
import java.util.Map.Entry;

public class CAMs {
	
	public static User currentUser;
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	
        User studentBob = new Student("Bob","studentBob9999", "studentBob@gmail.com", "SCSE", "alpine", AccessLevel.NORMAL);
        
        StaffController staffC = new StaffController();
        StudentController studentC = new StudentController();
        
        AuthStore.initStaffUsers();
        AuthView authView = new AuthView();
        
        while (true) {
            
            authView.displayMenuView();

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                	scanner.nextLine();
                	System.out.print("Enter UserID: ");
                	String inputUserID = scanner.nextLine();
                	System.out.print("Enter Password: ");
                	String pwd = scanner.nextLine();
                	
                	boolean found = false;
                	for (Entry<String, Map<String, String>> entry : AuthStore.staffMap.entrySet()) {
                        Map<String, String> detailsMap = entry.getValue();
                        
                        // Check if input is a staff
                        if (detailsMap.containsValue(inputUserID.concat("@ntu.edu.sg")) && pwd.equals("password")) {
                        	found = true;
                            String key = entry.getKey();

                            // Access details
                            String name = key;
                            String faculty = detailsMap.get("Faculty");
                            String email = detailsMap.get("Email");

                            //currentUser = new Staff(name, inputUserID, email, faculty, pwd, AccessLevel.PRIVILEDGED);
                            staffC.start();
                            break;
                        }
                    }
                	if (!found) System.out.println("Invalid Staff Email or Password"); break;
                case 2:
                	//currentUser = studentBob;
                    studentC.start();
                    break;
                default:
                	System.out.println("-----------------------------------");
                    System.out.println("|     Thank you for using CAMs    |"); 
                    System.out.println("-----------------------------------");
                    return;
            }
            
        }
    }
}
*/
