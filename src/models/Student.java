package models;

import java.util.ArrayList;
import java.util.List;
import enums.UserRole;
import enums.FacultyGroups;

public class Student extends User {

    private String studentID;
    private String committeeStatus;
    // fix this @ ojing KIV having enquiry list here
    private List<Enquiry> enquiries= new ArrayList<>();

    public Student (String name, String userID, String email, FacultyGroups faculty, String password) {
        super(name, userID, email, faculty, password);
        this.studentID = userID;
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

    public List<Enquiry> getEnquiries() {
        return this.enquiries;
    }

}
