package controllers;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import main.CAMs;
import models.*;

public class StaffController {
    
    public void start() {
    	Scanner scanner = new Scanner(System.in);
	    CampServiceController controller = new CampServiceController(CampServiceController.camps);
	    
	    while (true) {
            System.out.println("----------------------------");
            System.out.println(String.format("|      Welcome %-11s |", CAMs.currentUser.getName()));
            //System.out.println("      Welcome "+ CAMs.currentUser.getName());
            System.out.println("----------------------------");
            System.out.println("|        Staff Menu        |");
            System.out.println("----------------------------");
            System.out.println("|  1. Create Camp          |");
            System.out.println("|  2. View All Camps       |");
            System.out.println("|  3. View Created Camps   |");
            System.out.println("|  4. Set Visibility       |");
            System.out.println("|  5. Delete Camp          |");
            System.out.println("|  6. Edit Camp            |");
            System.out.println("|  7. View Student List    |");
            System.out.println("|  8. Logout               |");
            System.out.println("----------------------------");
            System.out.print("Select an option: ");
            
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
