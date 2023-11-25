/**
 * 
 */
/**
 * CAMs is a Java-based application for staff and students to manage, view and register for camps within NTU. 
 * The application will act as a centralized hub for all staff and students.
 * 
 * @author O Jing
 * @author Phua Zai Qin
 * @author Kenneth Yew Zhen Yuan
 * @author Cai Hongqi
 *
 */
module Assignment {
	
	/**
     * Contains files for the controllers that mediate between user and related operations.
     */
    exports controllers;
    
    /**
     * Contains enums for defining statuses and user roles.
     */
    exports enums;
    
    /**
     * Contains interfaces to be implemented by other classes.
     */
    exports interfaces;
    
    /**
     * Contains main java file, the entry point of the application.
     */
    exports main;
    
    /**
     * Contains the files of the respective models used in the application.
     */
    exports models;
    
    /**
     * Contains files for executing services to support operations requested by user.
     */
    exports services;
    
    /**
     * Contains files that handle storage and data for the application.
     */
    exports stores;
    
    /**
     * Contains utility files that perform utility functions.
     */
    exports utility;
    
    /**
     * Contains views to display respective information.
     */
    exports views;
}