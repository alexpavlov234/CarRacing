package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.User;
import hristov.mihail.carracing.services.PersonService;
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
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private ImageView imageView;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField lastNameField;
    @FXML
    private Label wrongConfirmPasswordLabel;

    @FXML
    private Label wrongEmailLabel;

    @FXML
    private Label wrongNameLabel;

    @FXML
    private Label wrongPasswordLabel;


    public boolean isValidPassword(String password) {
        //String regexPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        String regexPattern = "^(?=.*[0-9])(?=\\S+$).{8,}$";

        if (!password.matches(regexPattern)) {
            passwordField.setStyle("-fx-border-color: red");
            confirmPasswordField.setStyle("-fx-border-color: red");
            wrongPasswordLabel.setText("Въведете валидна парола!");
        }
        return password.matches(regexPattern);
    }

    public boolean isValidName(String name) {
        String regexPattern = "[А-ЯЁ][-А-яЁё]+|[A-Z][a-z]+";


        if (!name.matches(regexPattern)) {
            firstNameField.setStyle("-fx-border-color: red");
            lastNameField.setStyle("-fx-border-color: red");
            wrongNameLabel.setText("Въведете коректни имена!");
        }
        return name.matches(regexPattern);
    }

    public boolean isValidEmail(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (!Pattern.compile(regexPattern).matcher(emailAddress).matches()) {
            emailField.setStyle("-fx-border-color: red");
            wrongEmailLabel.setText("Въведете валиден имейл!");
        }
        return Pattern.compile(regexPattern).matcher(emailAddress).matches();
    }

    @FXML
    void openLoginScreen(ActionEvent event) {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
            stage.setTitle("Вход");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка",MessageType.WARNING);
        }
    }

    @FXML
    void registerUser(ActionEvent event) {
        try {
           if (isValidEmail(emailField.getText().trim()) && isValidPassword(passwordField.getText())) {
                if (isValidName(firstNameField.getText().trim()) && isValidName(lastNameField.getText().trim())) {
                    if (passwordField.getText().equals(confirmPasswordField.getText())) {
                        PersonService.addPerson(firstNameField.getText().trim(), lastNameField.getText().trim());
                        User user = new User(emailField.getText().trim(), passwordField.getText(), "User", PersonService.getLastPerson().getIdPerson());
                        if(Objects.isNull(UserService.getUser(user.getEmailUser()))) {
                            UserService.addUser(user);
                            openLoginScreen(event);
                        } else {
                            WarningController.openMessageModal("Вече съществува потребител със същия имейл!", "Съществуващ потребител",MessageType.WARNING);
                        }

                    } else {
                        passwordField.setStyle("-fx-border-color: red");
                        confirmPasswordField.setStyle("-fx-border-color: red");
                        wrongPasswordLabel.setText("Паролите не съвпадат!");
                    }
                }
            }
        } catch (Exception e) {

            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
        }

    }

    @FXML
    void initialize() {
        assert backgroundImageView != null : "fx:id=\"backgroundImageView\" was not injected: check your FXML file 'register.fxml'.";
        assert confirmPasswordField != null : "fx:id=\"confirmPasswordField\" was not injected: check your FXML file 'register.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'register.fxml'.";
        assert firstNameField != null : "fx:id=\"firstNameField\" was not injected: check your FXML file 'register.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'register.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'register.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'register.fxml'.";
        assert registerButton != null : "fx:id=\"registerButton\" was not injected: check your FXML file 'register.fxml'.";
        assert lastNameField != null : "fx:id=\"lastNameField\" was not injected: check your FXML file 'register.fxml'.";
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            firstNameField.setStyle("-fx-border-color: white");
            lastNameField.setStyle("-fx-border-color: white");
            confirmPasswordField.setStyle("-fx-border-color: white");
            passwordField.setStyle("-fx-border-color: white;");
            emailField.setStyle("-fx-border-color: white");
            wrongConfirmPasswordLabel.setText(null);
            wrongEmailLabel.setText(null);
            wrongNameLabel.setText(null);
            wrongPasswordLabel.setText(null);
        });
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            firstNameField.setStyle("-fx-border-color: white");
            lastNameField.setStyle("-fx-border-color: white");
            confirmPasswordField.setStyle("-fx-border-color: white");
            passwordField.setStyle("-fx-border-color: white;");
            emailField.setStyle("-fx-border-color: white");
            wrongConfirmPasswordLabel.setText(null);
            wrongEmailLabel.setText(null);
            wrongNameLabel.setText(null);
            wrongPasswordLabel.setText(null);
        });
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            firstNameField.setStyle("-fx-border-color: white");
            lastNameField.setStyle("-fx-border-color: white");
            confirmPasswordField.setStyle("-fx-border-color: white");
            passwordField.setStyle("-fx-border-color: white;");
            emailField.setStyle("-fx-border-color: white");
            wrongConfirmPasswordLabel.setText(null);
            wrongEmailLabel.setText(null);
            wrongNameLabel.setText(null);
            wrongPasswordLabel.setText(null);
        });
        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            firstNameField.setStyle("-fx-border-color: white");
            lastNameField.setStyle("-fx-border-color: white");
            confirmPasswordField.setStyle("-fx-border-color: white");
            passwordField.setStyle("-fx-border-color: white;");
            emailField.setStyle("-fx-border-color: white");
            wrongConfirmPasswordLabel.setText(null);
            wrongEmailLabel.setText(null);
            wrongNameLabel.setText(null);
            wrongPasswordLabel.setText(null);
        });
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            firstNameField.setStyle("-fx-border-color: white");
            lastNameField.setStyle("-fx-border-color: white");
            confirmPasswordField.setStyle("-fx-border-color: white");
            passwordField.setStyle("-fx-border-color: white;");
            emailField.setStyle("-fx-border-color: white");
            wrongConfirmPasswordLabel.setText(null);
            wrongEmailLabel.setText(null);
            wrongNameLabel.setText(null);
            wrongPasswordLabel.setText(null);
        });

        emailField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    registerUser(new ActionEvent());
                }
            }
        });

        passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    registerUser(new ActionEvent());
                }
            }
        });
        confirmPasswordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    registerUser(new ActionEvent());
                }
            }
        }); lastNameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    registerUser(new ActionEvent());
                }
            }
        }); firstNameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    registerUser(new ActionEvent());
                }
            }
        });


        File file = new File("src/main/java/hristov/mihail/carracing/images/car-icon.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        File file2 = new File("src/main/java/hristov/mihail/carracing/images/dodge-challenger-srt-hellcat.jpg");
        Image image2 = new Image(file2.toURI().toString());
        backgroundImageView.setImage(image2);
    }

}
