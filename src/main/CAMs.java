package main;

import java.util.Scanner;
import enums.AccessLevel;
import models.*;
import controllers.StaffController;
import controllers.StudentController;

public class CAMs {
	
	public static User currentUser;
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	
        User profAaron = new Staff("Aaron","profAaron9999", "profAaron@gmail.com", "SCSE", "alpine", AccessLevel.PRIVILEDGED);
        User profBarry = new Staff("Barry","profBarry9999", "profBarry@gmail.com", "SCSE", "alpine", AccessLevel.PRIVILEDGED);
        User studentBob = new Student("Bob","studentBob9999", "studentBob@gmail.com", "SCSE", "alpine", AccessLevel.NORMAL);
        
        StaffController staffC = new StaffController();
        StudentController studentC = new StudentController();
        
        while (true) {
            System.out.println("----------------------------");
            System.out.println("|        Login Screen      |");
            System.out.println("----------------------------");
            System.out.println("| 1. Login as Prof Aaron   |");
            System.out.println("| 2. Login as Prof Barry   |");
            System.out.println("| 3. Login as Student Bob  |");
            System.out.println("| 4. Quit                  |");
            System.out.println("----------------------------");
            System.out.print("Select an option: ");
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentUser = profAaron;
                    staffC.start();
                    break;
                case 2:
                	currentUser = profBarry;
                    staffC.start();
                    break;
                case 3:
                	currentUser = studentBob;
                    studentC.start();
                    break;
                default:
                    System.out.println("---- Thank you for using CAMs ----"); return;
            }
        }
    }
}
