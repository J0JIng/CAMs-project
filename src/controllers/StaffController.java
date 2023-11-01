package controllers;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import main.CAMs;
import models.*;

public class StaffController {
    //Camp uocCamp = new Camp(new CampInformation("UOC CAMP", "12-DEC-2023", "12-NOV-23", "NTU", "THE WAVE", 50, 10, "Ultimate UOC Camp 2023", CAMs.currentUser.getName()));
    
    public void start() {
    	Scanner scanner = new Scanner(System.in);
	    CampServiceController controller = new CampServiceController(CampServiceController.camps);
	    
	    while (true) {
            System.out.println("----------------------------");
            System.out.println("|       Welcome "+ CAMs.currentUser.getName() + "    |");
            System.out.println("|        Staff Menu        |");
            System.out.println("----------------------------");
            System.out.println("1. Create Camp");
            System.out.println("2. View All Camps");
            System.out.println("3. View Created Camps");
            System.out.println("4. Set Visibility");
            System.out.println("5. Logout");
            System.out.println("----------------------------");
            System.out.print("Select an option: ");

            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Create Camp
                	Camp scseCamp = new Camp(new CampInformation("SCSE CAMP", "1-DEC-2023", "21-NOV-23", "NTU", "SCSE", 50, 10, "SCSE Orientation Camp 2023", CAMs.currentUser.getName()));
            	    controller.campStaffService.createCamp(scseCamp);
                    break;
                case 2:
                    // View All Camps
                    controller.campStaffService.viewAllCamps();
                    break;
                case 3:
                	controller.campStaffService.viewCreatedCamps();
                	break;
                case 4:
                	scanner.nextLine();
                	System.out.print("Enter Camp Name: ");
                	String s = scanner.nextLine();
                	System.out.print("Enter Visibility: ");
                	boolean b = scanner.nextBoolean();
                	controller.campStaffService.setVisibility(s, b);
                	break;
                default: return;
            }
        }
    }
}
