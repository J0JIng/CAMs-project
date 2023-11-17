package services;

import java.util.Map;

import enums.UserRole;
import models.Staff;
import stores.AuthStore;
import stores.DataStore;
import views.AuthView;

/**
 * The AuthSupervisorService class extends AuthService and
 * provides the login functionality for supervisors.
 */
public class AuthStaffService extends AuthService {
    /**
     * Constructs an instance of the AuthSupervisorService class.
     */
    public AuthStaffService() {
        AuthView.staffLoginView();
    };

    @Override
    public boolean login(String userID, String password) {
        Map<String, Staff> staffData = DataStore.getStaffData();

        Staff staff = staffData.get(userID);

        if (authenticate(staff, password) == false){
            return false;
        }

        if(staff.getUserRole()!=UserRole.STAFF){
            return false;
        }

        AuthStore.setCurrentUser(staff);
        return true;
    }

}
