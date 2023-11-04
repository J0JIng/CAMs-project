package models;
import java.util.Date;

public class CampInformation {
    private String campName, campUserGroup, campLocation, campDescription, campStaffInCharge;
    private Date campStartDate, campEndDate, campRegistrationClosingDate;
    private int campTotalSlots, campCommitteeSlots;
    public CampInformation( String campName, Date campStartDate, Date campEndDate, Date campRegistrationClosingDate, 
                            String campUserGroup, String campLocation, int campTotalSlots, int campCommitteeSlots, 
                            String campDescription, String campStaffInCharge) {
        this.campName = campName;
        this.campStartDate = campStartDate;
        this.campEndDate = campEndDate;
        this.campRegistrationClosingDate = campRegistrationClosingDate;
        this.campUserGroup = campUserGroup;
        this.campLocation = campLocation;
        this.campTotalSlots = campTotalSlots;
        this.campCommitteeSlots = campCommitteeSlots;
        this.campDescription = campDescription;
        this.campStaffInCharge = campStaffInCharge;
    }
    public String getCampName() { return this.campName; }
    public Date getCampStartDate() { return this.campStartDate; }
    public Date getCampEndDate() { return this.campEndDate; }
    public Date getCampRegistrationClosingDate() { return this.campRegistrationClosingDate; }
    public String getCampUserGroup() { return this.campUserGroup; }
    public String getCampLocation() { return this.campLocation; }
    public int getCampTotalSlots() { return this.campTotalSlots; }
    public int getCampCommitteeSlots() { return this.campCommitteeSlots; }
    public String getCampDescription() { return this.campDescription; }
    public String getCampStaffInCharge() { return this.campStaffInCharge; }
}
