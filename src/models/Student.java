package models;

import enums.UserRole;
import enums.FacultyGroups;

/**
 * Represents a {@link Student} in the system, inheriting from the {@link User} class.
 * Stores fields such as the student's ID and committee status to any specific camp
 * Contains methods for the student to use, such as retrieving Student ID.
 */
public class Student extends User {

    /**
     * The unique identifier of the student.
     */
    private String studentID;

    /**
     * The committee status of the student, indicating the camp they are a committee member of.
     * A null value indicates that the student is not part of any camp's committee.
     */
    private String committeeStatus;

    /**
     * The number of points earned by the student.
     */
    private int studentPoints;

    /**
     * Constructs a new Student object with the specified attributes.
     *
     * @param name               The name of the student.
     * @param userID             The unique identifier for the student.
     * @param email              The email address of the student.
     * @param faculty            The faculty group to which the student belongs.
     * @param password           The password of the student.
     * @param studentPoints      The number of points earned by the student.
     * @param isPasswordChanged  A boolean indicating whether the password has been changed.
     */
    public Student (String name, String userID, String email, FacultyGroups faculty, String password, int studentPoints, boolean isPasswordChanged) {
        super(name, userID, email, faculty, password, isPasswordChanged);
        this.studentID = userID;
        this.studentPoints = studentPoints;
        super.setRole(UserRole.STUDENT);
    }
    
    /**
     * Gets the committee status of the student, indicating the camp they are a committee member of.
     *
     * @return The committee status of the student.
     */
    public String getCommitteeStatus() {
        return this.committeeStatus;
    }

    /**
     * Sets the committee status of the student based on the provided camp.
     * If argument is null, the student will not be a part of any camp's committee
     *
     * @param camp The camp to associate with the student.
     */
    public void setCommitteeStatus(Camp camp) {
    	if (camp == null) {
    		this.committeeStatus = null;
    	} else {
    		this.committeeStatus = camp.getCampInformation().getCampName();
    
    	}
    }

    /**
     * Gets the unique identifier of the student.
     *
     * @return The student's unique identifier.
     */
    public String getStudentID() {
        return this.studentID;
    }

    /**
     * Gets the number of points earned by the student.
     *
     * @return The number of points earned by the student.
     */
    public int getStudentPoints() {
        return this.studentPoints;
    }

    /**
     * Increments the number of points earned by the student.
     */
    public void incrementStudentPoints() {
         this.studentPoints++;
    }

}
