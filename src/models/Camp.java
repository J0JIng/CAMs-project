package models;

/**
 * Represents a camp with associated information and visibility status.
 * It holds a campInformation object that stores the information details of the camp
 * It stores a boolean variable denoting its visibility to students
 * 
 */
public class Camp {
    
	/**
    * The information related to the camp.
    */
    private CampInformation campInformation;
    
    /**
     * The boolean visibility status of the camp.
     */
    private boolean campVisibility;
    
    /**
     * Constructs a camp with the provided camp information.
     *
     * @param info The information pertaining to the camp.
     */
    public Camp(CampInformation info) {
        this.campInformation = info;
    }
    
    /**
     * Gets the visibility status of the camp.
     *
     * @return True if the camp is visible, false otherwise.
     */
    public boolean getVisibility() {
        return this.campVisibility;
    }
    
    /**
     * Sets the visibility status of the camp.
     *
     * @param bool The visibility status to set.
     */
    public void setVisibility(boolean bool) {
        this.campVisibility = bool;
    }
    
    /**
     * Gets the camp information of the camp.
     *
     * @return The camp information.
     */
    public CampInformation getCampInformation() {
        return this.campInformation;
    }
    
    /**
     * Sets the camp information of the camp.
     *
     * @param info The camp information to set.
     */
    public void setCampInformation(CampInformation info) {
        this.campInformation = info;
    }
    
}