package enums;

/**
 * {@link Enum} representing different Message Status for {@link Enquiry} and {@link Suggestion}s.
 * Each constant represents a state of the message.
 */
public enum MessageStatus{
	
	/**
	 * Denotes that message is currently saved as a draft.
	 */
	DRAFT, 
	
	/**
	 * Denotes that message is submitted and awaiting for review.
	 */
	PENDING, 
	
	/**
	 * Denotes that message has been reviewed and accepted.
	 */
	ACCEPTED, 
	
	/**
	 * Denotes that message has been reviewed and rejected.
	 */
	REJECTED}

