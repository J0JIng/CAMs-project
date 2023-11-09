package models;

import java.util.ArrayList;
import java.util.List;

public class Camp {
    
    private CampInformation campInformation;
    private List<Student> campRegisteredStudents = new ArrayList<>();
    private List<Student> campCommitteeMembers = new ArrayList<>();
    private boolean campVisibility;
    
    public Camp(CampInformation info) {
        this.campInformation = info;
    }
    public boolean getVisibility() {
        return this.campVisibility;
    }
    public void setVisibility(boolean bool) {
        this.campVisibility = bool;
    }
    public CampInformation getCampInformation() {
        return this.campInformation;
    }
    public void setCampInformation(CampInformation info) {
        this.campInformation = info;
    }
    
    public List<Student> getRegisteredStudents() {
        return this.campRegisteredStudents;
    }
    public List<Student> getCommitteeMembers() {
        return this.campCommitteeMembers;
    }
    
}