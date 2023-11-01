package models;

import enums.AccessLevel;

public class Staff extends User {
    public Staff (String name, String userID, String email, String faculty, String password, AccessLevel accessLevel) {
        super(name, userID, email, faculty, password, accessLevel);
    }
}