package services;

import java.util.Map;

import models.Student;
import stores.AuthStore;
import stores.DataStore;

/**
 * The AuthStudentService class extends AuthService and
 * provides the login functionality for students.
 */
public class AuthStudentService extends AuthService {
    /**
     * Constructs an instance of the AuthStudentService class.
     */
    public AuthStudentService() {
    };

    @Override
    public boolean login(String userID, String password) {
        Map<String, Student> studentData = DataStore.getStudentData();

        Student student = studentData.get(userID);
        
        //debugging
        if (student == null) {
            System.out.println("User with userID " + userID + " not found.");
            System.out.println("Contents of studentData: " + studentData);
            return false;
        }

        if (authenticate(student, password) == false)
            return false;

        AuthStore.setCurrentUser(student);
        return true;
    }

}