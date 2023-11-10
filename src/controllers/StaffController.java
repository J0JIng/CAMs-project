package controllers;

import controllers.UserController;
import java.util.Scanner;
import views.StaffView;

public class StaffController extends UserController {
    
    public void start() {
    	Scanner scanner = new Scanner(System.in);

	    
	    StaffView view = new StaffView();
	    while (true) {
        /*     
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
            	    controller.campStaffService.createCamp(); 
            	    break;
                case 2:
                	controller.campStaffService.deleteCamp(); 
                	break;
                case 3:
                	controller.campStaffService.editCamp(); 
                	break;
                case 4:
                	controller.campStaffService.setVisibility(); 
                	break;
                case 5:
                    controller.campStaffService.viewAllCamps(null, null, null); 
                    break;
                case 6: 
                	controller.campStaffService.viewAllCampsWithFilters();
                	break;
                case 7:
                	controller.campStaffService.viewCreatedCamps(); 
                	break;
                case 8:
                	controller.campStaffService.viewStudentList(); 
                	break;
                case 9:
                	controller.campStaffService.viewEnquiriesForCamp(); 
                	break;
                case 10:
                	controller.campStaffService.respondToEnquiry(); 
                	break;
                default: return;
                
            }
            */
        }
    }
}
