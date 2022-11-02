package hristov.mihail.carracing.services;

import hristov.mihail.carracing.models.User;

public class LoginService {
    private static User user = null;

    public static void loginUser(User loggedUser, String password) {
        if (loggedUser.getPassUser().equals(password)) {
            user = loggedUser;
        }
    }

    public static void logoutUser(User loggedUser) {
        user = null;
    }

}
