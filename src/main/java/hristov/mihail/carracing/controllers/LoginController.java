/**
 * Sample Skeleton for 'login.fxml' Controller Class
 */

package hristov.mihail.carracing.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import hristov.mihail.carracing.models.User;
import hristov.mihail.carracing.services.TrackService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LoginController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnForgot"
    private Label btnForgot; // Value injected by FXMLLoader

    @FXML // fx:id="btnSignin"
    private Button btnSignin; // Value injected by FXMLLoader

    @FXML // fx:id="btnSignup"
    private Button btnSignup; // Value injected by FXMLLoader

    @FXML // fx:id="lblErrors"
    private Label lblErrors; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassword"
    private PasswordField txtPassword; // Value injected by FXMLLoader

    @FXML // fx:id="backgroundImageView"
    private ImageView backgroundImageView; // Value injected by FXMLLoader

    @FXML // fx:id="txtUsername"
    private TextField txtUsername; // Value injected by FXMLLoader
    @FXML // fx:id="imageView"
    private ImageView imageView; // Value injected by FXMLLoader
    @FXML
    void handleButtonAction(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnForgot != null : "fx:id=\"btnForgot\" was not injected: check your FXML file 'login.fxml'.";
        assert btnSignin != null : "fx:id=\"btnSignin\" was not injected: check your FXML file 'login.fxml'.";
        assert btnSignup != null : "fx:id=\"btnSignup\" was not injected: check your FXML file 'login.fxml'.";
        assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'login.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'login.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'login.fxml'.";

        File file = new File("src/main/java/hristov/mihail/carracing/images/car-icon.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        File file2 = new File("src/main/java/hristov/mihail/carracing/images/dodge-challenger-srt-hellcat.jpg");
        Image image2 = new Image(file2.toURI().toString());
        backgroundImageView.setImage(image2);
    }

}
