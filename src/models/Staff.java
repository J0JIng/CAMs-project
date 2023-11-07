package models;

import enums.AccessLevel;
import enums.UserRole;

public class Staff extends User {

  private String staffID;
  private int numOfCampsManaged;

  public Staff (String name, String staffID, String email, String faculty, String password, int numOfCampsManaged) {
    super(name, staffID, email, faculty, password);
    this.staffID = staffID;
    this.numOfCampsManaged = numOfCampsManaged;
    super.setRole(UserRole.STAFF);
    super.setAccessLevel(AccessLevel.TOP);
  }

  public String getStaffID() {
    return this.staffID;
  }
  
  public int getNumOfCampsManaged() {
    return this.numOfCampsManaged;
  }
  
}