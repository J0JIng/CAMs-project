package controllers;

import java.util.Scanner;
import views.StudentView;

public class StudentController {
	public void start() {
    	Scanner scanner = new Scanner(System.in);
	    CampServiceController controller = new CampServiceController(CampServiceController.camps);
	    
	    StudentView view = new StudentView();
	    while (true) {
            view.displayMenuView();

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    controller.campStudentService.viewAllCamps();
                    break;
                case 2:
                	controller.campStudentService.registerCamp();
                    break;
                case 3:
                	controller.campStudentService.withdrawCamp();
                    break;
                case 4:
                	controller.campStudentService.viewRemainingSlots();
                    break;
                default: return;
            }
        }
    }
}
