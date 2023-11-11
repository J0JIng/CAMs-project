package controllers;
import java.util.Scanner;

import models.Staff;
import services.CampStaffService;
import stores.AuthStore;
import views.StaffView;

public class StaffController extends UserController {
    
    public void start() {
    	Scanner scanner = new Scanner(System.in);
        CampStaffService service = new CampStaffService();
        Staff staff = (Staff) AuthStore.getCurrentUser();
	
	    StaffView view = new StaffView();

	    while (true) {
        
            view.displayMenuView();
            
            // Checks for invalid inputs
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input!! ");
                scanner.next();
                System.out.print("Select an option: ");
            }
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // Create Camp
            	    service.createCamp(staff);
            	    break;
                case 2: // Delete Camp
                	service.deleteCamp(staff); 
                	break;
                case 3: // Update camp details
                	service.updateCampDetails(staff); 
                	break;
                case 4: // Set camp visibility
                	service.toggleCampVisibility(staff);
                	break;
                case 5: // View all camps
                    service.viewAllCamps();
                    break;
                case 6: 
                	//controller.campStaffService.viewAllCampsWithFilters();
                	break;
                case 7: // View Staff created Camps
                	service.viewCreatedCamps(); 
                	break;
                case 8: // View List of students in a Camp
                	service.viewStudentList(); 
                	break;
                case 9:
                	//controller.campStaffService.viewEnquiriesForCamp(); 
                	break;
                case 10:
                	//controller.campStaffService.respondToEnquiry(); 
                	break;
                case 16:
                	// Change password
                	break;
                default:
                    System.out.println("Exiting Staff menu");
                    AuthController.endSession();
                    return;   
            }
        }
    }
}
