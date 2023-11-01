package main;

import java.util.Scanner;
import enums.AccessLevel;
import models.*;
import controllers.StaffController;
import controllers.StudentController;
import stores.AuthStore;
import java.util.Map;
import java.util.Map.Entry;

public class CAMs {
	
	public static User currentUser;
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	
        User studentBob = new Student("Bob","studentBob9999", "studentBob@gmail.com", "SCSE", "alpine", AccessLevel.NORMAL);
        
        StaffController staffC = new StaffController();
        StudentController studentC = new StudentController();
        
        AuthStore.initStaffUsers();
        
        while (true) {
            System.out.println("----------------------------");
            System.out.println("|        Login Screen      |");
            System.out.println("----------------------------");
            System.out.println("| 1. Login as Staff        |");
            System.out.println("| 2. Login as Student Bob  |");
            System.out.println("| 3. Quit                  |");
            System.out.println("----------------------------");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                	scanner.nextLine();
                	System.out.print("Enter UserID: ");
                	String inputUserID = scanner.nextLine();
                	System.out.print("Enter Password: ");
                	String pwd = scanner.nextLine();
                	
                	for (Entry<String, Map<String, String>> entry : AuthStore.staffMap.entrySet()) {
                        Map<String, String> detailsMap = entry.getValue();

                        // Check if input is a staff
                        if (detailsMap.containsValue(inputUserID.concat("@ntu.edu.sg")) && pwd.equals("password")) {
                            String key = entry.getKey();

                            // Access details
                            String name = key;
                            String faculty = detailsMap.get("Faculty");
                            String email = detailsMap.get("Email");

                            currentUser = new Staff(name, inputUserID, email, faculty, pwd, AccessLevel.PRIVILEDGED);
                            staffC.start();
                            break;
                        }
                    }
                	System.out.println("Invalid Staff Email or Password");
                    break;
                case 2:
                	currentUser = studentBob;
                    studentC.start();
                    break;
                default:
                    System.out.println("---- Thank you for using CAMs ----"); return;
            }
            
        }
    }
}
