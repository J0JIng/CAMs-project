package interfaces;

import java.util.Date;

import models.Camp;

public interface ICampUpdateService {

    public boolean updateCampName(Camp camp, String newCampName);

    public boolean updateCampStartDate(Camp camp, Date newStartDate);

    public boolean updateCampEndDate(Camp camp, Date newEndDate);

    public boolean updateRegistrationEndDate(Camp camp, Date newRegistrationClosingDate);

    public boolean updateCampLocation(Camp camp, String newLocation);

    public boolean updateCampTotalSlot(Camp camp, int newCampTotalSlots);

    public boolean updateCampCommitteeSlots(Camp camp, int newCommitteeSlots);

    public boolean updateCampDescription(Camp camp, String newDescription);

    public boolean updateCampFacultyGroup(Camp camp, String userInput);
    
} 