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
 * The main class responsible for running the CAMS application.
 *
 *
 * This class handles initializing of the data store, authentication for users
 * to log in, and starting the appropriate session based on the role of the
 * logged-in user.
 *
 */
public class CAMs {
    /**
     * Private constructor to prevent instantiation of the class.
     */
    private CAMs() {
    };

    /**
     * The entry point for the CAMS application. This method is responsible for
     * running an infinite loop to allow multiple users to operate the application.
     *
     * @param args an array of String arguments passed to this method
     */
    public static void main(String[] args) {
        try {
            // Initialize DataStore
            DataStore.initDataStore(new CsvFileDataService(), FilePathsUtility.csvFilePaths());

            do {
                // Display Splash Screen
                // Boundary.printSplashScreen(); //@TODO

                // Authentication - Log In
                AuthController.startSession();
                if (!AuthStore.isLoggedIn())
                    return;

                // Start session
                User user = AuthStore.getCurrentUser();
                switch (user.getUserRole()) {
                    case STUDENT,COMMITTEE:
                        new StudentController().start();
                        break;

                    case STAFF:
                        new StaffController().start();
                        break;

                    default:
                        System.out.println("User Role Not Found");
                        return;
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
