package services;

import java.util.Map;

import models.Student;
import stores.AuthStore;
import stores.DataStore;
import views.AuthView;
import enums.UserRole;

/**
 * The {@code AuthStudentService} class extends {@link AuthService} and
 * provides the login functionality for students.
 */
public class AuthStudentService extends AuthService {
    /**
     * Constructs an instance of the {@code AuthStudentService} class.
     */
    public AuthStudentService() {
        AuthView.studentLoginView();
    };

    /**
     * Attempts to log in a student with the provided credentials.
     * If successful, the currentUser in AuthStore is set to that student.
     *
     * @param userID   a {@link String} representing the user ID
     * @param password a {@link String} representing the user's password
     * @return true if the login is successful, false otherwise
     */
    @Override
    public boolean login(String userID, String password) {
        Map<String, Student> studentData = DataStore.getStudentData();
        Student student = studentData.get(userID);
        
        if (student == null) {
            return false;
        }

        if (authenticate(student, password) == false)
            return false;

        if(student.getUserRole()!=UserRole.STUDENT){
            return false;
        }

        AuthStore.setCurrentUser(student);
        return true;
    }

}