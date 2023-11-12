package controllers;

import java.util.Scanner;
import views.StudentView;
import interfaces.ICampStudentService;
import models.Student;
import services.CampStudentService;
import stores.AuthStore;

public class StudentController extends UserController {
	public void start() {
    	Scanner scanner = new Scanner(System.in);
        Student student = (Student) AuthStore.getCurrentUser();
        CampStudentService service = new CampStudentService();

	    StudentView view = new StudentView();
	    while (true) {
            view.displayMenuView();

            
            // Checks for invalid inputs
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input!! ");
                scanner.next();
                System.out.print("Select an option: ");
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            // choice  // fix this @ ojing
        
            switch (choice) {
                case 1:
                    //controller.campStudentService.viewAllCamps(null, null, null);
                    break;
                case 2: 
                	//controller.campStudentService.viewAllCampsWithFilters();
                	break;
                case 3:
                	service.registerCamp();
                    break;
                case 4:
                	service.withdrawCamp();
                    break;
                case 5:
                	//controller.campStudentService.viewRemainingSlots();
                    break;
                case 6:
                	service.registerAsCommittee();
                    break;
                case 7:
                	//controller.campStudentService.withdrawFromCommittee();
                    break;
                case 8:
                	//controller.campStudentService.viewRegisteredCamps();
                    break;
                case 9: 
                	service.submitEnquiry();
                	break;
                case 10: 
                	service.viewEnquiries();
                	break;
                case 11: 
                	service.editEnquiry();
                	break;
                case 12: 
                	service.deleteEnquiry();
                	break;
                case 13: 
                	service.viewEnquiriesForCamp();
                	break;
                case 14: 
                	service.respondEnquiry();
                	break;
                case 15:
                    service.submitSuggestion();
                    break;
                case 16:
                    service.viewSuggestions();
                    break;
                case 17:
                    service.editSuggestion();
                    break;
                case 18:
                    service.deleteSuggestion();
                    break;
                default: 
                    System.out.println("Exiting student menu");
                    AuthController.endSession();
                    return;
               
            }
             
        }
    }
}
