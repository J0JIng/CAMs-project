package models;

import enums.UserRole;
import enums.FacultyGroups;

/**
 * Represents a {@link Staff} member in the system, inheriting from the {@link User} class.
 * Stores fields such as the {@link Staff}'s ID and number of camps managed
 * Contains methods for the {@link Staff} to use, such as retrieving Staff ID.
 */
public class Staff extends User {
	
	/**
     * The unique identifier for the staff member.
     */
	private String staffID;
	
	/**
     * The number of camps managed by the staff member.
     */
	private int numOfCampsManaged;
	
	/**
     * Creates a new Staff object with the specified attributes.
     *
     * @param name               The name of the staff member.
     * @param staffID            The unique identifier for the staff member.
     * @param email              The email address of the staff member.
     * @param faculty            The faculty group to which the staff member belongs to.
     * @param password           The password of the staff member.
     * @param numOfCampsManaged  The number of camps managed by the staff member.
     * @param isPasswordChanged  A boolean indicating whether the staff member has changed their password.
     */
	public Staff (String name, String staffID, String email, FacultyGroups faculty, String password, int numOfCampsManaged, boolean isPasswordChanged) {
		super(name, staffID, email, faculty, password, isPasswordChanged);
		this.staffID = staffID;
		this.numOfCampsManaged = numOfCampsManaged;
		super.setRole(UserRole.STAFF);
	}
	
	 /**
     * Gets the staff member's unique identifier.
     *
     * @return The staff member's unique identifier.
     */
	public String getStaffID() {
		return this.staffID;
	}
	  
	/**
     * Gets the number of camps managed by the staff member.
     *
     * @return The number of camps managed by the staff member.
     */
	public int getNumOfCampsManaged() {
		return this.numOfCampsManaged;
	}
  
}