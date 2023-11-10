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
                case 1:
                    System.out.println("create camp");
            	    service.createCamp(staff); 
            	    break;
                case 2:
                    System.out.println("delete camp");
                	service.deleteCamp(staff); 
                	break;
                case 3:
                    System.out.println("update camp");
                	service.updateCampDetails(staff); 
                	break;
                case 4:
                    System.out.println("Set visibility");
                	service.toggleCampVisibility(staff); 
                	break;
                case 5:
                    //controller.campStaffService.viewAllCamps(null, null, null); 
                    break;
                case 6: 
                	//controller.campStaffService.viewAllCampsWithFilters();
                	break;
                case 7:
                	//controller.campStaffService.viewCreatedCamps(); 
                	break;
                case 8:
                	//controller.campStaffService.viewStudentList(); 
                	break;
                case 9:
                	//controller.campStaffService.viewEnquiriesForCamp(); 
                	break;
                case 10:
                	//controller.campStaffService.respondToEnquiry(); 
                	break;
                default:
                    System.out.println("Exiting Staff menu");
                    AuthController.endSession();
                    return;   
            }
        }
    }
}
