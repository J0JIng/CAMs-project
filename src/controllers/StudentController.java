package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.CAMs;
import models.Camp;
import models.CampInformation;

public class StudentController {
	public void start() {
    	Scanner scanner = new Scanner(System.in);
	    CampServiceController controller = new CampServiceController(CampServiceController.camps);
	    
	    while (true) {
            System.out.println("----------------------------");
            System.out.println("|       Student Menu       |");
            System.out.println("----------------------------");
            System.out.println("1. View All Camps");
            System.out.println("2. Register for Camp");
            System.out.println("3. Withdraw from Camp");
            System.out.println("4. View Remaining slots of camp");
            System.out.println("5. Logout");
            System.out.println("----------------------------");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    controller.campStudentService.viewCamps();
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
