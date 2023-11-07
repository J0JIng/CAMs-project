package models;

import java.util.ArrayList;
import java.util.List;

import enums.AccessLevel;
import enums.StudentRole;
import enums.UserRole;

public class Student extends User {

    private String studentID;
    private Camp committeeStatus;
    private List<Camp> registeredCamps = new ArrayList<>();
    private List<Camp> withdrawnCamps = new ArrayList<>();
    private List<Enquiry> enquiries= new ArrayList<>();

    public Student (String name, String userID, String email, String faculty, String password, StudentRole studentRole) {
        super(name, userID, email, faculty, password);
        this.studentID = userID;
        super.setRole(UserRole.STUDENT);
    }
    
    public Camp getCommitteeStatus() {
        return this.committeeStatus;
    }

    public void setCommitteeStatus(Camp camp) {
        this.committeeStatus = camp;
    }

    public String getStudentID() {
        return this.studentID;
    }

    public List<Camp> getRegisteredCamps() {
        return this.registeredCamps;
    }

    public List<Camp> getWithdrawnCamps() {
        return this.withdrawnCamps;
    }

    public List<Enquiry> getEnquiries() {
        return this.enquiries;
    }

}
