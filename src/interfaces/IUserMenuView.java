package interfaces;

import java.util.List;
import java.util.Map;

import models.Camp;
import models.Enquiry;

/**
 * The {@link IUserMenuView} interface defines a contract for the user main menu
 * options and relevant views it requires.
 */
public interface IUserMenuView extends IMenuView {
	
	/**
	 * Displays all camps without any filters
	 * @param list of camps, String of title for header
	 */
	public void viewCamps(List<Camp> campData, String title);
	
	/**
	 * Displays the camp information of the camp
	 * @param Camp c
	 */
	public void viewCampInformation(Camp c);
	

	public void viewFliterOption();

	public void displayEnquiries(Map<Integer, Enquiry> enquiries);
	
}
