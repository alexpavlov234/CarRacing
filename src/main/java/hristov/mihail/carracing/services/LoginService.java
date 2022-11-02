package hristov.mihail.carracing.services;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginService {
    private static User user = null;

    public static void loginUser(User loggedUser, String password, Stage stage) {
        try {
            if (loggedUser.getPassUser().equals(password)) {
                user = loggedUser;


                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-screen.fxml"));

                Scene scene = new Scene(fxmlLoader.load());


                stage.setTitle("Управление на автомобилни състезания");
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LoginService.user = user;
    }

    public static void logoutUser() {
        user = null;
    }

}
