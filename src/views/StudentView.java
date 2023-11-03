package views;

import interfaces.IMenuView;

public class StudentView implements IMenuView{

	@Override
	public void displayMenuView() {
		System.out.println("----------------------------");
        System.out.println("|       Student Menu       |");
        System.out.println("----------------------------");
        System.out.println("1. View All Camps");
        System.out.println("2. Register for Camp");
        System.out.println("3. Withdraw from Camp");
        System.out.println("4. View Remaining Slots");
        System.out.println("5. Logout");
        System.out.println("----------------------------");
        System.out.print("Select an option: ");
	}

}
