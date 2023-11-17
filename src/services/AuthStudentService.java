package services;

import java.util.Map;

import models.Student;
import stores.AuthStore;
import stores.DataStore;
import views.AuthView;
import enums.UserRole;

/**
 * The AuthStudentService class extends AuthService and
 * provides the login functionality for students.
 */
public class AuthStudentService extends AuthService {
    /**
     * Constructs an instance of the AuthStudentService class.
     */
    public AuthStudentService() {
        AuthView.studentLoginView();
    };

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