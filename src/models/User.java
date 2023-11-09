package models;
import enums.UserRole;
import enums.FacultyGroups;

public abstract class User {
    private String name;
    private String userID;
    private String email;
    private FacultyGroups faculty;
    private String password;
    private UserRole userRole;

    public User(String name, String userID, String email, FacultyGroups faculty, String password) {
        this.name = name;
        this.userID = userID;
        this.email = email;
        this.faculty = faculty;
        this.password = password;        
    };
  
    public String getName() {
        return this.name;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getEmail() {
        return this.email;
    }

    public FacultyGroups getFaculty() {
        return this.faculty;
    }

    public String getPassword() {
        return this.password;
    }

    public Boolean setPassword(String oldPassword, String newPassword) {
		if (!oldPassword.equals(this.password))
			return false;
		this.password = newPassword;
		return true;
	}

    public UserRole getUserRole() {
        return this.userRole;
    }

    public Boolean setRole(UserRole userRole) {
		this.userRole = userRole;
		return true;
	}
}