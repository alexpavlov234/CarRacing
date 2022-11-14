package hristov.mihail.carracing.services;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
            } else {
                Stage stage1 = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("warning-modal.fxml"));


                Scene scene = new Scene(fxmlLoader.load());
                //messageController.setLoggedUser(car.getIdCar());
                WarningController messageController = fxmlLoader.getController();
                messageController.setErrorMessage("Въведената парола е невалидна!");
                stage1.setTitle("Грешна парола");
                stage1.setScene(scene);
                stage1.show();
            }
        } catch (Exception e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка");
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
