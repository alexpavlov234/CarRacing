package hristov.mihail.carracing.services;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class LoginService {
    private static User user = null;

    public static boolean loginUser(User loggedUser, String password, Stage stage) {
        try {
            if (loggedUser.getPassUser().equals(password)) {
                user = loggedUser;
                if (isLoggedUserAdmin()) {

                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-screen-admin.fxml"));

                    Scene scene = new Scene(fxmlLoader.load());
                    stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
                    stage.setTitle("Управление на автомобилни състезания");
                    stage.setScene(scene);
                    stage.show();
                    return true;
                } else {

                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-screen-user.fxml"));

                    Scene scene = new Scene(fxmlLoader.load());
                    stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
                    stage.setTitle("Управление на автомобилни състезания");
                    stage.setScene(scene);
                    stage.show();
                    return true;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isLoggedUserAdmin() {
        return user.getTypeUser().equals("Admin");
    }

    public static User getLoggedUser() {
        return user;
    }

    public static void logoutUser() {
        user = null;
    }

}
