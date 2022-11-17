package hristov.mihail.carracing.services;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginService {
    private static User user = null;

    public static boolean loginUser(User loggedUser, String password, Stage stage) {
        try {
            if (loggedUser.getPassUser().equals(password)) {
                user = loggedUser;


                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-screen.fxml"));

                Scene scene = new Scene(fxmlLoader.load());


                stage.setTitle("Управление на автомобилни състезания");
                stage.setScene(scene);
                stage.show();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {

            return false;
        }

    }

    public static User getLoggedUser() {
        return user;
    }

    public static void setLoggedUser(User user) {
        LoginService.user = user;
    }

    public static void logoutUser() {
        user = null;
    }

}
