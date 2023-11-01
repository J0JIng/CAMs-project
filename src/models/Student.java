package models;

import enums.AccessLevel;

public class Student extends User {
    public Student (String name, String userID, String email, String faculty, String password, AccessLevel accessLevel) {
        super(name, userID, email, faculty, password, accessLevel);
    }
}
