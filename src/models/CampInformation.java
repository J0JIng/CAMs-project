package models;

import java.util.Date;

import enums.FacultyGroups;

public class CampInformation {

    private static final int MAX_CAMPCOMMITTEE_SLOTS = 10;

    private String campName;
    private String campLocation;
    private String campDescription;
    private String campStaffInCharge;
    private FacultyGroups facultyGroups;

    private Date campStartDate;
    private Date campEndDate;
    private Date campRegistrationClosingDate;

    private int campTotalSlots;
    private int campCommitteeSlots;

    public CampInformation(String campName, Date campStartDate, Date campEndDate, Date campRegistrationClosingDate,
            String campLocation, int campTotalSlots, int campCommitteeSlots, String campDescription,
            String campStaffInCharge, FacultyGroups facultyGroup) {

        this.campName = campName;
        this.campStartDate = campStartDate;
        this.campEndDate = campEndDate;
        this.campRegistrationClosingDate = campRegistrationClosingDate;
        this.campLocation = campLocation;
        this.campTotalSlots = campTotalSlots;
        this.campDescription = campDescription;
        this.campStaffInCharge = campStaffInCharge;
        this.facultyGroups = facultyGroup;

        setCampCommitteeSlots(campCommitteeSlots);
    }

    public String getCampName() {
        return this.campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public Date getCampStartDate() {
        return this.campStartDate;
    }

    public void setCampStartDate(Date campStartDate) {
        this.campStartDate = campStartDate;
    }

    public Date getCampEndDate() {
        return this.campEndDate;
    }

    public void setCampEndDate(Date campEndDate) {
        this.campEndDate = campEndDate;
    }

    public Date getCampRegistrationClosingDate() {
        return this.campRegistrationClosingDate;
    }

    public void setCampRegistrationClosingDate(Date campRegistrationClosingDate) {
        this.campRegistrationClosingDate = campRegistrationClosingDate;
    }

    public String getCampLocation() {
        return this.campLocation;
    }

    public void setCampLocation(String campLocation) {
        this.campLocation = campLocation;
    }

    public int getCampTotalSlots() {
        return this.campTotalSlots;
    }

    public void setCampTotalSlots(int campTotalSlots) {
        this.campTotalSlots = campTotalSlots;
    }

    public int getCampCommitteeSlots() {
        return this.campCommitteeSlots;
    }

    public void setCampCommitteeSlots(int campCommitteeSlots) {
        if (campCommitteeSlots > MAX_CAMPCOMMITTEE_SLOTS)
            this.campCommitteeSlots = MAX_CAMPCOMMITTEE_SLOTS;
        else
            this.campCommitteeSlots = campCommitteeSlots;
    }

    public String getCampDescription() {
        return this.campDescription;
    }

    public void setCampDescription(String campDescription) {
        this.campDescription = campDescription;
    }

    public String getCampStaffInCharge() {
        return this.campStaffInCharge;
    }

    public void setCampStaffInCharge(String campStaffInCharge) {
        this.campStaffInCharge = campStaffInCharge;
    }

    public FacultyGroups getFacultyGroup() {
        return this.facultyGroups;
    }

    public void setFacultyGroup(FacultyGroups facultyGroup) {
        this.facultyGroups = facultyGroup;
    }

}
