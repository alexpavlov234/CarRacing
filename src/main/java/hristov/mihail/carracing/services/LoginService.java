package hristov.mihail.carracing.services;

import hristov.mihail.carracing.models.User;

public class LoginService {
    private static User user = null;

    public static void loginUser(User loggedUser) {
        if (loggedUser.getPassUser().equals(UserService.getUser(loggedUser.getIdUser()).getPassUser())) {
            user = loggedUser;
        }
    }

    public static void logoutUser(User loggedUser) {
        user = null;
    }

}
