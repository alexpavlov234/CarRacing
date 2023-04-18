package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.User;
import hristov.mihail.carracing.services.LoginService;
import hristov.mihail.carracing.services.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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
    private Label wrongPasswordLabel;
    @FXML
    private Label wrongEmailLabel;

    @FXML
    void handleButtonAction(MouseEvent event) {

    }

    @FXML
    void login(ActionEvent event) {
        if (passwordField.getText().equals(null) || passwordField.getText().equals("") || emailField.getText().equals(null) || emailField.getText().equals("")) {
            passwordField.setStyle("-fx-border-color: red;");
            emailField.setStyle("-fx-border-color: red");
            wrongPasswordLabel.setText("Моля въведете данни за вход!");
        } else {
            User user = UserService.getUser(emailField.getText().trim());
            if (Objects.isNull(user)) {
                emailField.setStyle("-fx-border-color: red;");

                wrongEmailLabel.setText("Грешен имейл!");
            } else {
                if (!LoginService.loginUser(user, passwordField.getText(), (Stage) registerButton.getScene().getWindow())) {
                    passwordField.setStyle("-fx-border-color: red;");

                    wrongPasswordLabel.setText("Грешна парола!");
                }
            }
        }
    }

    @FXML
    void openRegisterScreen(ActionEvent event) {
        try {
            Stage stage = (Stage) registerButton.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
            stage.setTitle("Регистрация");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
        }
    }


    @FXML
    void initialize() {
        assert backgroundImageView != null : "fx:id=\"backgroundImageView\" was not injected: check your FXML file 'login.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'login.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'login.fxml'.";
        assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'login.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'login.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'login.fxml'.";
        assert registerButton != null : "fx:id=\"registerButton\" was not injected: check your FXML file 'login.fxml'.";
        emailField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    login(new ActionEvent());
                }
            }
        });
        passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    login(new ActionEvent());
                }
            }
        });
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordField.setStyle("-fx-border-color: white;");
            emailField.setStyle("-fx-border-color: white");
            wrongEmailLabel.setText(null);
            wrongPasswordLabel.setText(null);
        });
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordField.setStyle("-fx-border-color: white;");
            emailField.setStyle("-fx-border-color: white");
            wrongPasswordLabel.setText(null);
            wrongEmailLabel.setText(null);
        });
        File file = new File("src/main/java/hristov/mihail/carracing/images/car-icon.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        File file2 = new File("src/main/java/hristov/mihail/carracing/images/dodge-challenger-srt-hellcat.jpg");
        Image image2 = new Image(file2.toURI().toString());
        backgroundImageView.setImage(image2);
    }

}
