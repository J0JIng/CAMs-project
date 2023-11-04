package controllers;

import java.util.Scanner;
import views.StaffView;

public class StaffController {
    
    public void start() {
    	Scanner scanner = new Scanner(System.in);
	    CampServiceController controller = new CampServiceController(CampServiceController.camps);
	    
	    StaffView view = new StaffView();
	    while (true) {
            
            view.displayMenuView();
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
            	    controller.campStaffService.createCamp(); break;
                case 2:
                    controller.campStaffService.viewAllCamps(); break;
                case 3:
                	controller.campStaffService.viewCreatedCamps(); break;
                case 4:
                	controller.campStaffService.setVisibility(); break;
                case 5:
                	controller.campStaffService.deleteCamp(); break;
                case 6:
                	controller.campStaffService.editCamp(); break;
                case 7:
                	controller.campStaffService.viewStudentList(); break;
                default: return;
            }
        }
    }
}
