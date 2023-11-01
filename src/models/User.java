package models;

import enums.AccessLevel;

public abstract class User {
    private String name, userID, email, faculty, password;
    private AccessLevel accessLevel;
    public User(String name, String userID, String email, String faculty, String password, AccessLevel accessLevel) {
        this.name = name;
        this.userID = userID;
        this.email = email;
        this.faculty = faculty;
        this.password = password;
        this.accessLevel = accessLevel;
    };
    public String getName() {return this.name;}
    public String getUserID() {return this.userID;}
    public String getEmail() {return this.email;}
    public String getFaculty() {return this.faculty;}
    public AccessLevel getAccessLevel() {return this.accessLevel;}
}