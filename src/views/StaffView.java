package views;

import interfaces.IMenuView;
import stores.AuthStore;

public class StaffView implements IMenuView{

	@Override
	public void displayMenuView() {
		System.out.println("-----------------------------------");
		String welcomeMessage = "Welcome " + AuthStore.getCurrentUser().getName();
		int totalLength = welcomeMessage.length();
		int spaces = (33 - totalLength) / 2;

		System.out.println("|" + " ".repeat(spaces) + welcomeMessage + " ".repeat(33 - totalLength - spaces) + "|");
        System.out.println("|           Staff Menu            |");
        System.out.println("|---------------------------------|");
        System.out.println("|  1. Create Camp                 |");
        System.out.println("|  2. View All Camps              |");
        System.out.println("|  3. View Created Camps          |");
        System.out.println("|  4. Set Visibility              |");
        System.out.println("|  5. Delete Camp                 |");
        System.out.println("|  6. Edit Camp                   |");
        System.out.println("|  7. View Student List           |");
        System.out.println("|  8. Logout                      |");
        System.out.println("-----------------------------------");
        System.out.print("Select an option: ");
	}
	
}
