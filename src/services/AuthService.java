package services;

import interfaces.IAuthService;
import models.User;
import stores.AuthStore;

/**  
 * The {@link AuthService}  class is an abstract class that implements the
 * {@link IAuthService} interface. It provides basic authentication functionalities 
 * for user login and logout.
 */
public abstract class AuthService implements IAuthService {
    
	/**
	 * Abstract method that attempts to log in the user with the provided user ID and password.
	 *
	 * @param userID   The user ID of the user trying to log in.
	 * @param password The password of the user trying to log in.
	 * @return True if the login is successful, false otherwise.
	 */
    public abstract boolean login(String userID, String password);

    /**
     * Constructs an instance of the {@link AuthService}  class.
     */
    public AuthService() {
    };

    /**
     * Logs out the current user by setting the current user in the authentication store to null.
     *
     * @return True if the logout is successful, false otherwise.
     */
    @Override
    public boolean logout() {
        AuthStore.setCurrentUser(null);
        return true;
    };

    /**
     * Authenticates the given user with the given password.
     * 
     * @param user     the user to be authenticated
     * @param password the password to be used for authentication
     * @return true if the user is authenticated successfully, false otherwise
     */
    protected boolean authenticate(User user, String password) {
        if (user == null){
            return false;
        }
        if (!user.getPassword().equals(password)){
            return false;
        }
        return true;
    }
}