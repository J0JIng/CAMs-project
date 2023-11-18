package models;

import enums.UserRole;
import enums.FacultyGroups;

public class Staff extends User {

  private String staffID;
  private int numOfCampsManaged;

  public Staff (String name, String staffID, String email, FacultyGroups faculty, String password, int numOfCampsManaged,  boolean isPasswordChanged) {
    super(name, staffID, email, faculty, password, isPasswordChanged);
    this.staffID = staffID;
    this.numOfCampsManaged = numOfCampsManaged;
    super.setRole(UserRole.STAFF);
  }

  public String getStaffID() {
    return this.staffID;
  }
  
  public int getNumOfCampsManaged() {
    return this.numOfCampsManaged;
  }
  
}