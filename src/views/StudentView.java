package views;

import interfaces.IMenuView;
import stores.AuthStore;

public class StudentView implements IMenuView{

	@Override
	public void displayMenuView() {
		System.out.println("-----------------------------------");
		String welcomeMessage = "Welcome " + AuthStore.getCurrentUser().getName();
		int totalLength = welcomeMessage.length();
		int spaces = (33 - totalLength) / 2;

		System.out.println("|" + " ".repeat(spaces) + welcomeMessage + " ".repeat(33 - totalLength - spaces) + "|");
        System.out.println("|           Student Menu          |");
        System.out.println("|---------------------------------|");
        System.out.println("| 1. View All Camps               |");
        System.out.println("| 2. Register for Camp            |");
        System.out.println("| 3. Withdraw from Camp           |");
        System.out.println("| 4. View Remaining Slots of Camp |");
        System.out.println("| 5. Logout                       |");
        System.out.println("-----------------------------------");
        System.out.print("Select an option: ");
	}

}
