package services;

import java.util.Map;

import enums.UserRole;
import models.Staff;
import stores.AuthStore;
import stores.DataStore;
import views.AuthView;

/**
 * The {@code AuthStaffService}  class extends {@link AuthService} and
 * provides the login functionality for staffs.
 */
public class AuthStaffService extends AuthService {
    /**
     * Constructs an instance of the {@code AuthStaffService}  class.
     */
    public AuthStaffService() {
        AuthView.staffLoginView();
    };

    /**
     * Attempts to log in a staff member with the provided credentials.
     * If successful, the currentUser in AuthStore is set to that staff member.
     *
     * @param userID   a {@link String} representing the user ID
     * @param password a {@link String} representing the user's password
     * @return true if the login is successful, false otherwise
     */
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
