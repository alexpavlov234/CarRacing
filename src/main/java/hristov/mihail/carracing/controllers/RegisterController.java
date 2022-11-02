package hristov.mihail.carracing.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.models.User;
import hristov.mihail.carracing.services.PersonService;
import hristov.mihail.carracing.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private TextField secondNameField;

    @FXML
    void handleButtonAction(MouseEvent event) {

    }

    @FXML
    void openLoginScreen(ActionEvent event) {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("Вход");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @FXML
    void registerUser(ActionEvent event) {
        try {
            if (firstNameField.getText().trim().contains(" ") || secondNameField.getText().trim().contains(" ")) {
                System.out.println("Invalid name!");
                throw new Exception();
            } else if (validateEmail(emailField.getText().trim()) || isValidPassword(passwordField.getText())) {
                if (passwordField.getText().equals(confirmPasswordField.getText())) {
                    PersonService.addPerson(firstNameField.getText().trim(), secondNameField.getText().trim());
                    User user = new User(emailField.getText().trim(), passwordField.getText(), "User", PersonService.getLastPerson().getIdPerson());
                    UserService.addUser(user);
                } else {
                    System.out.println("Invalid pass!");
                    throw new Exception();

                }
            } else {
                System.out.println("Invalid email!");
                throw new Exception();
            }
        } catch (Exception e) {

            //TODO: Екран за грешка
        }

    }

    public static boolean validateEmail(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
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
        assert secondNameField != null : "fx:id=\"secondNameField\" was not injected: check your FXML file 'register.fxml'.";
        File file = new File("src/main/java/hristov/mihail/carracing/images/car-icon.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        File file2 = new File("src/main/java/hristov/mihail/carracing/images/dodge-challenger-srt-hellcat.jpg");
        Image image2 = new Image(file2.toURI().toString());
        backgroundImageView.setImage(image2);
    }

}
