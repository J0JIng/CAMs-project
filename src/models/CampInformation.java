package models;
import java.util.Date;
import enums.FacultyGroups;

public class CampInformation {

    private static final int MAXCAMPCOMMITTEESLOTS = 10;
    
    private String campName;
    private String campUserGroup;
    private String campLocation;
    private String campDescription;
    private String campStaffInCharge;
    private FacultyGroups facultyGroups;

    private Date campStartDate;
    private Date campEndDate;
    private Date campRegistrationClosingDate;
    
    private int campTotalSlots;
    private int campCommitteeSlots;
    
    public CampInformation( String campName, Date campStartDate, Date campEndDate, Date campRegistrationClosingDate, 
                            String campUserGroup, String campLocation, int campTotalSlots, int campCommitteeSlots, 
                            String campDescription, String campStaffInCharge ,FacultyGroups facultyGroup) {

        this.campName = campName;
        this.campStartDate = campStartDate;
        this.campEndDate = campEndDate;
        this.campRegistrationClosingDate = campRegistrationClosingDate;
        this.campUserGroup = campUserGroup;
        this.campLocation = campLocation;
        this.campTotalSlots = campTotalSlots;
        this.campDescription = campDescription;
        this.campStaffInCharge = campStaffInCharge;
        this.facultyGroups = facultyGroup;

        if(campCommitteeSlots>MAXCAMPCOMMITTEESLOTS) this.campCommitteeSlots = MAXCAMPCOMMITTEESLOTS; 
        else this.campCommitteeSlots = campCommitteeSlots;
    }

    public String getCampName() { 
        return this.campName; 
    }
    public Date getCampStartDate() { 
        return this.campStartDate; 
    }
    public Date getCampEndDate() { 
        return this.campEndDate; 
    }
    public Date getCampRegistrationClosingDate() { 
        return this.campRegistrationClosingDate; 
    }
    public String getCampUserGroup() { 
        return this.campUserGroup; 
    }
    public String getCampLocation() { 
        return this.campLocation; 
    }
    public int getCampTotalSlots() { 
        return this.campTotalSlots; 
    }
    public int getCampCommitteeSlots() { 
        return this.campCommitteeSlots; 
    }
    public String getCampDescription() { 
        return this.campDescription; 
    }
    public String getCampStaffInCharge() { 
        return this.campStaffInCharge; 
    }
    public FacultyGroups getFacultyGroup() { 
        return this.facultyGroups; 
    }

}
