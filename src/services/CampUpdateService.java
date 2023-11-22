package services;

import java.util.List;
import java.util.Date;

import enums.FacultyGroups;
import interfaces.ICampStaffService;
import interfaces.ICampUpdateService;
import interfaces.ICampValidationService;
import models.Camp;

public class CampUpdateService implements ICampUpdateService {

    private final static ICampStaffService campStaffService = new CampStaffService();
    private final static ICampValidationService campValidationService = new CampValidationService();

    private static final int MAX_COMMITTEE_SLOTS = 10; 

    public CampUpdateService() {
    }

    // ---------- interface Service method implementation ---------- //
	
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
	
    @Override
	public boolean updateCampStartDate(Camp camp, Date newStartDate) {
		if (newStartDate.after(camp.getCampInformation().getCampEndDate())) {
			System.out.println("Error updating start date: Invalid start date. The new start date must be before the current end date.");
			return false;
		} else if (newStartDate.before(camp.getCampInformation().getCampRegistrationClosingDate())) {
			System.out.println("Error updating start date: Invalid start date. The new start date must be after the registration end date.");
			return false;
		} else {
			camp.getCampInformation().setCampStartDate(newStartDate);
			System.out.println("Start date updated successfully!");
			return true;
		}
	}
	
    @Override
	public boolean updateCampEndDate(Camp camp, Date newEndDate) {
		if (newEndDate.before(camp.getCampInformation().getCampStartDate())) {
			System.out.println("Error updating end date: Invalid end date. The new end date must be after the current start date.");
			return false;
		} else if (newEndDate.before(camp.getCampInformation().getCampRegistrationClosingDate())) {
			System.out.println("Error updating end date: Invalid end date. The new end date must be after the registration end date.");
			return false;
		} else {
			camp.getCampInformation().setCampEndDate(newEndDate);
			System.out.println("End date updated successfully!");
			return true;
		}
	}
	
    @Override
	public boolean updateRegistrationEndDate(Camp camp, Date newRegistrationClosingDate) {
		if (newRegistrationClosingDate.after(camp.getCampInformation().getCampStartDate())) {
			System.out.println("Error updating registration closing date: Invalid date. The new date must be before the current start date.");
			return false;
		} else if (newRegistrationClosingDate.after(camp.getCampInformation().getCampEndDate())) {
			System.out.println("Error updating registration closing date: Invalid date. The new date must be before the current end date.");
			return false;
		} else {
			camp.getCampInformation().setCampRegistrationClosingDate(newRegistrationClosingDate);
			System.out.println("Registration closing date updated successfully!");
			return true;
		}
	}

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
