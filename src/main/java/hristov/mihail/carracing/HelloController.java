package hristov.mihail.carracing;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        Parent root = null;
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("login.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root));
        stage.show();
    }
}