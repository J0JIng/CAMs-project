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
            System.out.println("2. Logout");
            System.out.println("----------------------------");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                	// View All Camps
                    controller.campStudentService.viewCamps();
                    break;
                default: return;
            }
        }
    }
}
