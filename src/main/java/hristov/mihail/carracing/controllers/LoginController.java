package hristov.mihail.carracing.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.User;
import hristov.mihail.carracing.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private TextField emailField;

    @FXML
    private ImageView imageView;

    @FXML
    private Label lblErrors;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    void handleButtonAction(MouseEvent event) {

    }

    @FXML
    void login(ActionEvent event) {
        User user = new User(emailField.getText().trim(),passwordField.getText() );
        LoginService.loginUser(user);
    }

    @FXML
    void openRegisterScreen(ActionEvent event) {
        try {
            Stage stage = (Stage) registerButton.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("Регистрация");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            //TODO: Екран за грешка
        }
    }



    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert backgroundImageView != null : "fx:id=\"backgroundImageView\" was not injected: check your FXML file 'login.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'login.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'login.fxml'.";
        assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'login.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'login.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'login.fxml'.";
        assert registerButton != null : "fx:id=\"registerButton\" was not injected: check your FXML file 'login.fxml'.";


        File file = new File("src/main/java/hristov/mihail/carracing/images/car-icon.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        File file2 = new File("src/main/java/hristov/mihail/carracing/images/dodge-challenger-srt-hellcat.jpg");
        Image image2 = new Image(file2.toURI().toString());
        backgroundImageView.setImage(image2);
    }

}
