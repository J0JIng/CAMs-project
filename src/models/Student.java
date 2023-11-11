package models;

import enums.UserRole;
import enums.FacultyGroups;

public class Student extends User {

    private String studentID;
    private String committeeStatus;
    private int studentPoints;

    public Student (String name, String userID, String email, FacultyGroups faculty, String password, int studentPoints, boolean isPasswordChanged) {
        super(name, userID, email, faculty, password, isPasswordChanged);
        this.studentID = userID;
        this.studentPoints = studentPoints;
        super.setRole(UserRole.STUDENT);
    }
    
    public String getCommitteeStatus() {
        return this.committeeStatus;
    }

    public void setCommitteeStatus(Camp camp) {
    	if (camp == null) {
    		this.committeeStatus = null;
    	} else {
    		this.committeeStatus = camp.getCampInformation().getCampName();
    
    	}
    }

    public String getStudentID() {
        return this.studentID;
    }

    public int getStudentPoints() {
        return this.studentPoints;
    }

    public void incrementStudentPoints() {
         this.studentPoints++;
    }

}
