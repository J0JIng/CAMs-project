package enums;

import models.*;

/**
 * {@link Enum} representing different user role.
 * Each constant represents a user role, namely {@link Staff}, {@link Student} and Camp Committee.
 */
public enum UserRole {
	
	/**
     * Denotes that {@link User} is a {@link Student} in NTU.
     */
	STUDENT,
	
	/**
     * Denotes that {@link User} is a {@link Student} and a camp committee member of a camp in NTU.
     */
	COMMITTEE,
	
	/**
     * Denotes that {@link User} is a {@link Staff} in NTU.
     */
	STAFF}
