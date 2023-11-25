package services;

import interfaces.IUserService;
import models.User;
import stores.AuthStore;
import stores.DataStore;

/**
 * The {@link UserService} class implements the {@link IUserService} interface
 * and provides functionality related to user management, such as changing
 * passwords.
 */
public class UserService implements IUserService {

    /**
     * Constructs an instance of the {@link UserService} class.
     */
    public UserService() {
    };

    /**
     * Changes the password of a user if the provided old password matches the
     * current password.
     *
     * @param oldPassword the current password of the user
     * @param newPassword the new password the user wants to set
     * @return true if the password was changed successfully, false otherwise
     */
    @Override
    public boolean changePassword(String oldPassword, String newPassword) {
        User user = AuthStore.getCurrentUser();
        if (!user.setPassword(oldPassword, newPassword))
            return false;

        user.setIsPasswordChanged(true);
        DataStore.saveData(); // save new password to database

        return true;
    }
}
