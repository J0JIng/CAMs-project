package models;

public class CampInformation {
    private String campName, campDate, campRegistrationClosingDate, campUserGroup, campLocation, campDescription, campStaffInCharge;
    private int campTotalSlots, campCommitteeSlots;
    public CampInformation( String campName, String campDate, String campRegistrationClosingDate, 
                            String campUserGroup, String campLocation, int campTotalSlots, int campCommitteeSlots, 
                            String campDescription, String campStaffInCharge) {
        this.campName = campName;
        this.campDate = campDate;
        this.campRegistrationClosingDate = campRegistrationClosingDate;
        this.campUserGroup = campUserGroup;
        this.campLocation = campLocation;
        this.campTotalSlots = campTotalSlots;
        this.campCommitteeSlots = campCommitteeSlots;
        this.campDescription = campDescription;
        this.campStaffInCharge = campStaffInCharge;
    }
    public String getCampName() { return this.campName; }
    public String getCampDate() { return this.campDate; }
    public String getCampRegistrationClosingDate() { return this.campRegistrationClosingDate; }
    public String getCampUserGroup() { return this.campUserGroup; }
    public String getCampLocation() { return this.campLocation; }
    public int getCampTotalSlots() { return this.campTotalSlots; }
    public int getCampCommitteeSlots() { return this.campCommitteeSlots; }
    public String getCampDescription() { return this.campDescription; }
    public String getCampStaffInCharge() { return this.campStaffInCharge; }
}
