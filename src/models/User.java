package models;

import enums.UserRole;
import enums.FacultyGroups;

/**
 * Represents a user in the system, serving as a base class for specific user types.
 * Abstract class prevents creation of a User object that has insufficient meaning in the system.
 */
public abstract class User {
    
	/**
    * The name of the user.
    */
    private String name;

    /**
     * The unique identifier for the user.
     */
    private String userID;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * A boolean variable indicating whether the user's password has been changed.
     */
    private boolean isPasswordChanged;

    /**
     * The role of the user in the system, can be a Staff, Student or Camp Committee Member.
     */
    private UserRole userRole;

    /**
     * The faculty group the user belongs to.
     */
    private FacultyGroups faculty;

    /**
     * Creates a new User object with the specified attributes.
     *
     * @param name               The name of the user.
     * @param userID             The unique identifier for the user.
     * @param email              The email address of the user.
     * @param faculty            The faculty group to which the user belongs.
     * @param password           The password of the user.
     * @param isPasswordChanged  A boolean indicating whether the password has been changed.
     */
    public User(String name, String userID, String email, FacultyGroups faculty, String password, boolean isPasswordChanged) {
        this.name = name;
        this.userID = userID;
        this.email = email;
        this.faculty = faculty;
        this.password = password; 
        this.isPasswordChanged = isPasswordChanged;     
    }
  
    /**
     * Gets the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The unique identifier of the user.
     */
    public String getUserID() {
        return this.userID;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the faculty group to which the user belongs.
     *
     * @return The faculty group to which the user belongs.
     */
    public FacultyGroups getFaculty() {
        return this.faculty;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets a new password for the user.
     *
     * @param oldPassword The old password for verification.
     * @param newPassword The new password to set.
     * @return True if the password is successfully changed, false otherwise.
     */
    public Boolean setPassword(String oldPassword, String newPassword) {
        if (!oldPassword.equals(this.password))
            return false;
        this.password = newPassword;
        return true;
    }

    /**
     * Checks if the user's password has been changed.
     *
     * @return True if the password has been changed, false otherwise.
     */
    public boolean getIsPasswordChanged() {
        return this.isPasswordChanged;
    }

    /**
     * Sets the boolean variable indicating whether the user's password has been changed.
     *
     * @param isPasswordChanged The boolean variable indicating whether the password has been changed.
     */
    public void setIsPasswordChanged(boolean isPasswordChangedFlag) {
        this.isPasswordChanged = isPasswordChangedFlag;
    }

    /**
     * Gets the role of the user in the system.
     *
     * @return The role of the user.
     */
    public UserRole getUserRole() {
        return this.userRole;
    }

    /**
     * Sets the role of the user in the system.
     *
     * @param userRole The role to set for the user.
     * @return True if the role is successfully set, false otherwise.
     */
    public Boolean setRole(UserRole userRole) {
        this.userRole = userRole;
        return true;
    }
}