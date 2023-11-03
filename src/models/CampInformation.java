package models;
import java.util.Date;

public class CampInformation {
    private String campName, campUserGroup, campLocation, campDescription, campStaffInCharge;
    private Date campDate, campRegistrationClosingDate;
    private int campTotalSlots, campCommitteeSlots;
    public CampInformation( String campName, Date campDate, Date campRegistrationClosingDate, 
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
    public Date getCampDate() { return this.campDate; }
    public Date getCampRegistrationClosingDate() { return this.campRegistrationClosingDate; }
    public String getCampUserGroup() { return this.campUserGroup; }
    public String getCampLocation() { return this.campLocation; }
    public int getCampTotalSlots() { return this.campTotalSlots; }
    public int getCampCommitteeSlots() { return this.campCommitteeSlots; }
    public String getCampDescription() { return this.campDescription; }
    public String getCampStaffInCharge() { return this.campStaffInCharge; }
}
