package services;

import java.util.List;
import java.time.LocalDate;

import enums.FacultyGroups;
import interfaces.ICampStaffService;
import interfaces.ICampUpdateService;
import interfaces.ICampValidationService;
import models.Camp;

/**
 * The {@link CampUpdateService} class implements the {@link ICampUpdateService} interface
 * and provides services for updating camp information such as its name, dates fields and availability slots.
 */
public class CampUpdateService implements ICampUpdateService {

	/**
     * The {@link campStaffService} field represents an instance of
     * {@link ICampStaffService} used for running camp staff related services.
     * It is kept private to ensure its use is exclusively in services that require it.
     */
    private final static ICampStaffService campStaffService = new CampStaffService();
    
    /**
     * The {@link campValidationService} field represents an instance of
     * {@link ICampValidationService} used for validating camp-related operations.
     * It is kept private to ensure its use is exclusively in services that require it.
     */
    private final static ICampValidationService campValidationService = new CampValidationService();
    
    /**
     * The {@link MAX_COMMITTEE_SLOTS} constant represents the maximum number of committee slots allowed which is set to 10.
     */
    private final static int MAX_COMMITTEE_SLOTS = 10; 

    /**
     * Constructs an instance of the {@link CampUpdateService} class.
     */
    public CampUpdateService() {
    }

    // ---------- interface Service method implementation ---------- //
	
    /**
     * Updates the name of the camp. Checks for uniqueness with all camps in the system.
     * @param newCampName the new camp name
     * @param camp the camp to update
     *
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    @Override
	public boolean updateCampName(Camp camp, String newCampName) {
		List<Camp> existingCamps = campStaffService.getAllCamps();
		boolean isUniqueName = campValidationService.isValidName(existingCamps, newCampName);
		if (isUniqueName) {
			System.out.println("Camp name updated successfully!");
			camp.getCampInformation().setCampName(newCampName);
			return true;
		}else{
			System.out.println("Error updating camp name: Camp with the same name already exists. Please choose a different name.");
			return false;
		}
	}
	
    /**
     * Updates the start date of the camp. Checks for date validity with start/end/registration date.
     * @param newStartDate the new start date of the camp.
     * @param camp the camp to update.
     *
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    @Override
	public boolean updateCampStartDate(Camp camp, LocalDate newStartDate) {
		if (newStartDate.isAfter(camp.getCampInformation().getCampEndDate())) {
			System.out.println("Error updating start date: Invalid start date. The new start date must be before the current end date.");
			return false;
		} else if (newStartDate.isBefore(camp.getCampInformation().getCampRegistrationClosingDate())) {
			System.out.println("Error updating start date: Invalid start date. The new start date must be after the registration end date.");
			return false;
		} else {
			camp.getCampInformation().setCampStartDate(newStartDate);
			System.out.println("Start date updated successfully!");
			return true;
		}
	}
	
    /**
     * Updates the end date of the camp. Checks for date validity with start/end/registration date.
     * @param newEndDate the new end date of the camp.
     * @param camp the camp to update.
     *
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    @Override
	public boolean updateCampEndDate(Camp camp, LocalDate newEndDate) {
		if (newEndDate.isBefore(camp.getCampInformation().getCampStartDate())) {
			System.out.println("Error updating end date: Invalid end date. The new end date must be after the current start date.");
			return false;
		} else if (newEndDate.isBefore(camp.getCampInformation().getCampRegistrationClosingDate())) {
			System.out.println("Error updating end date: Invalid end date. The new end date must be after the registration end date.");
			return false;
		} else {
			camp.getCampInformation().setCampEndDate(newEndDate);
			System.out.println("End date updated successfully!");
			return true;
		}
	}
	
    /**
     * Updates the registration closing date of the camp. Checks for date validity with start/end/registration date.
     * @param newRegistrationClosingDate the new registration closing date.
     * @param camp the camp to update.
     *
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    @Override
	public boolean updateRegistrationEndDate(Camp camp, LocalDate newRegistrationClosingDate) {
		if (newRegistrationClosingDate.isAfter(camp.getCampInformation().getCampStartDate())) {
			System.out.println("Error updating registration closing date: Invalid date. The new date must be before the current start date.");
			return false;
		} else if (newRegistrationClosingDate.isAfter(camp.getCampInformation().getCampEndDate())) {
			System.out.println("Error updating registration closing date: Invalid date. The new date must be before the current end date.");
			return false;
		} else {
			camp.getCampInformation().setCampRegistrationClosingDate(newRegistrationClosingDate);
			System.out.println("Registration closing date updated successfully!");
			return true;
		}
	}

    /**
     * Updates the location of the camp.
     * @param newLocation the new location of the camp.
     * @param camp the camp to update.
     *
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    @Override
	public boolean updateCampLocation(Camp camp, String newLocation) {
		// Add validation logic to check if newLocation is valid
		if (newLocation == null || newLocation.trim().isEmpty()) {
			System.out.println("Error updating location: Invalid location. Location cannot be empty or null.");
			return false;
		}
	
		// Update the camp location
		camp.getCampInformation().setCampLocation(newLocation);
		System.out.println("Location updated successfully!");
		return true;
	}
	
    /**
     * Updates the total slots of the camp. Checks whether total slots is more than committee slots.
     * @param newCampTotalSlots the total camp slots.
     * @param camp the camp to update.
     *
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    @Override
	public boolean updateCampTotalSlot(Camp camp, int newCampTotalSlots) {
		if (newCampTotalSlots <= camp.getCampInformation().getCampCommitteeSlots()) {
			System.out.println("Error updating total slots: Total slots must be greater than camp committee slots.");
			return false;
		}
	
		camp.getCampInformation().setCampTotalSlots(newCampTotalSlots);
		System.out.println("Total slots updated successfully!");
		return true;
	}
	
    /**
     * Updates the committee slots of the camp. Checks that commitee slots are less than total available slots.
     * @param newCommitteeSlots the new committee member slots.
     * @param camp the camp to update.
     *
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    @Override
	public boolean updateCampCommitteeSlots(Camp camp, int newCommitteeSlots) {
		if (newCommitteeSlots >= camp.getCampInformation().getCampTotalSlots()) {
			System.out.println("Error updating committee slots: Committee slots must be less than total slots.");
			return false;
		} else if (newCommitteeSlots > MAX_COMMITTEE_SLOTS) {
			newCommitteeSlots = MAX_COMMITTEE_SLOTS;
			camp.getCampInformation().setCampCommitteeSlots(newCommitteeSlots);
			System.out.println("Committee slots must be less than allowed maximum camp committee slots. Max default of 10 set.");
			return false;
		}
	
		camp.getCampInformation().setCampCommitteeSlots(newCommitteeSlots);
		System.out.println("Committee slots updated successfully!");
		return true;
	}
	
    /**
     * Updates the description of the camp. Include sanity checks for null descriptions.
     * @param newDescription the new description for the camp.
     * @param camp the camp to update.
     *
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
	@Override
	public boolean updateCampDescription(Camp camp, String newDescription) {
		// Add validation logic to check if newDescription is valid
		if (newDescription == null || newDescription.trim().isEmpty()) {
			System.out.println("Error updating description: Invalid description. Description cannot be empty or null.");
			return false;
		}
	
		// Update the camp description
		camp.getCampInformation().setCampDescription(newDescription);
		System.out.println("Description updated successfully!");
		return true;
	}

	/**
     * Updates the faculty group of the camp.
     * @param userInput the new faculty access for the camp.
     * @param camp the camp to update.
     *
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    @Override
	public boolean updateCampFacultyGroup(Camp camp, String userInput) {
		// Add validation logic to check if userInput is valid
		if (userInput == null || userInput.trim().isEmpty()) {
			System.out.println("Invalid Input. Input cannot be empty or null.");
			// Return false if input is invalid
			return false;
		}
	
		// Validate userInput against valid FacultyGroups
		try {
			FacultyGroups newFacultyGroup = FacultyGroups.valueOf(userInput.toUpperCase());
			// Update the camp faculty group
			camp.getCampInformation().setFacultyGroup(newFacultyGroup);
			// Return true if the update was successful
			System.out.println("Valid faculty group. User group updated successfully!");
			return true;

		} catch (IllegalArgumentException e) {
			// Return false if an invalid faculty group is provided
			System.out.println("Invalid faculty group. Please enter a valid faculty group.");
			return false;
		}
	}
}
