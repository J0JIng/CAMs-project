package interfaces;

import java.util.Date;
import java.util.List;

import models.Camp;
import models.Student;

public interface ICampValidationService {

    public boolean hasDateClash(Student student, Camp newCamp);
	
	public boolean dateClashExists(Camp camp1, Camp camp2);

	public boolean isCampOver(Date currentDate, Camp camp);
	
	public boolean isCampFull(Camp camp);

	public boolean isCampCommitteeFull(Camp camp);
	
	public boolean isUserCampCommitteeForCamp(Student student, Camp camp);

	public boolean isUserCampCommittee(Student student);
	
	public boolean isUserWithdrawnFromCamp(Student student, Camp camp);
	
	public boolean isUserRegisteredWithCamp(Student student, Camp camp);

    public boolean isCampRegistered(Camp camp);

    public boolean isValidName(List<Camp> existingCamps, String newCampName);
    
}
