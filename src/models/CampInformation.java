package models;

import java.time.LocalDate;

import enums.FacultyGroups;

/**
 * Represents {@link CampInformation} about a camp.
 * Contains details such as its name, dates, location, slots, and other details.
 */
public class CampInformation {

	/**
     * The maximum number of camp committee slots allowed.
     */
    private static final int MAX_CAMPCOMMITTEE_SLOTS = 10;

    /**
     * The name of the camp.
     */
    private String campName;
    
    /**
     * The location of the camp.
     */
    private String campLocation;
    
    /**
     * A description of the camp.
     */
    private String campDescription;
    
    /**
     * The staff in charge of the camp.
     */
    private String campStaffInCharge;
    
    /**
     * The faculty group associated with the camp.
     */
    private FacultyGroups facultyGroups;

    /**
     * The start date of the camp.
     */
    private LocalDate campStartDate;
    
    /**
     * The end date of the camp.
     */
    private LocalDate campEndDate;
    
    /**
     * The closing date for camp registration.
     */
    private LocalDate campRegistrationClosingDate;

    /**
     * The total number of slots available in the camp.
     */
    private int campTotalSlots;
    
    /**
     * The number of camp committee slots.
     */
    private int campCommitteeSlots;

    /**
     * Constructs a new CampInformation object with the specified attributes.
     *
     * @param campName                     The name of the camp.
     * @param campStartDate                The start date of the camp.
     * @param campEndDate                  The end date of the camp.
     * @param campRegistrationClosingDate  The closing date for camp registration.
     * @param campLocation                 The location of the camp.
     * @param campTotalSlots               The total number of slots available in the camp.
     * @param campCommitteeSlots           The number of camp committee slots.
     * @param campDescription              A description of the camp.
     * @param campStaffInCharge            The staff in charge of the camp.
     * @param facultyGroup                 The faculty groups associated with the camp.
     */
    public CampInformation(String campName, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationClosingDate,
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

    /**
     * Gets the name of the camp.
     *
     * @return The name of the camp.
     */
    public String getCampName() {
        return this.campName;
    }

    /**
     * Sets the name of the camp.
     *
     * @param campName The new name of the camp.
     */
    public void setCampName(String campName) {
        this.campName = campName;
    }

    /**
     * Gets the start date of the camp.
     *
     * @return The start date of the camp.
     */
    public LocalDate getCampStartDate() {
        return this.campStartDate;
    }

    /**
     * Sets the start date of the camp.
     *
     * @param campStartDate The new start date of the camp.
     */
    public void setCampStartDate(LocalDate campStartDate) {
        this.campStartDate = campStartDate;
    }

    /**
     * Gets the end date of the camp.
     *
     * @return The end date of the camp.
     */
    public LocalDate getCampEndDate() {
        return this.campEndDate;
    }

    /**
     * Sets the end date of the camp.
     *
     * @param campEndDate The new end date of the camp.
     */
    public void setCampEndDate(LocalDate campEndDate) {
        this.campEndDate = campEndDate;
    }

    /**
     * Gets the closing date for camp registration.
     *
     * @return The closing date for camp registration.
     */
    public LocalDate getCampRegistrationClosingDate() {
        return this.campRegistrationClosingDate;
    }

    /**
     * Sets the closing date for camp registration.
     *
     * @param campRegistrationClosingDate The new closing date for camp registration.
     */
    public void setCampRegistrationClosingDate(LocalDate campRegistrationClosingDate) {
        this.campRegistrationClosingDate = campRegistrationClosingDate;
    }

    /**
     * Gets the location of the camp.
     *
     * @return The location of the camp.
     */
    public String getCampLocation() {
        return this.campLocation;
    }

    /**
     * Sets the location of the camp.
     *
     * @param campLocation The new location of the camp.
     */
    public void setCampLocation(String campLocation) {
        this.campLocation = campLocation;
    }

    /**
     * Gets the total number of slots available in the camp.
     *
     * @return The total number of slots available in the camp.
     */
    public int getCampTotalSlots() {
        return this.campTotalSlots;
    }

    /**
     * Sets the total number of slots available in the camp.
     *
     * @param campTotalSlots The new total number of slots available in the camp.
     */
    public void setCampTotalSlots(int campTotalSlots) {
        this.campTotalSlots = campTotalSlots;
    }

    /**
     * Gets the number of camp committee slots.
     *
     * @return The number of camp committee slots.
     */
    public int getCampCommitteeSlots() {
        return this.campCommitteeSlots;
    }

    /**
     * Sets the number of camp committee slots. If the specified value exceeds the maximum allowed, 
     * it is set to the maximum value specified in the class attribute.
     *
     * @param campCommitteeSlots The new number of camp committee slots.
     */
    public void setCampCommitteeSlots(int campCommitteeSlots) {
        if (campCommitteeSlots > MAX_CAMPCOMMITTEE_SLOTS)
            this.campCommitteeSlots = MAX_CAMPCOMMITTEE_SLOTS;
        else
            this.campCommitteeSlots = campCommitteeSlots;
    }

    /**
     * Gets the description of the camp.
     *
     * @return The description of the camp.
     */
    public String getCampDescription() {
        return this.campDescription;
    }

    /**
     * Sets the description of the camp.
     *
     * @param campDescription The new description of the camp.
     */
    public void setCampDescription(String campDescription) {
        this.campDescription = campDescription;
    }

    /**
     * Gets the staff in charge of the camp.
     *
     * @return The staff in charge of the camp.
     */
    public String getCampStaffInCharge() {
        return this.campStaffInCharge;
    }

    /**
     * Sets the staff in charge of the camp.
     *
     * @param campStaffInCharge The new staff in charge of the camp.
     */
    public void setCampStaffInCharge(String campStaffInCharge) {
        this.campStaffInCharge = campStaffInCharge;
    }

    /**
     * Gets the faculty groups associated with the camp.
     *
     * @return The faculty groups associated with the camp.
     */
    public FacultyGroups getFacultyGroup() {
        return this.facultyGroups;
    }

    /**
     * Sets the faculty groups associated with the camp.
     *
     * @param facultyGroup The new faculty groups associated with the camp.
     */
    public void setFacultyGroup(FacultyGroups facultyGroup) {
        this.facultyGroups = facultyGroup;
    }

}
