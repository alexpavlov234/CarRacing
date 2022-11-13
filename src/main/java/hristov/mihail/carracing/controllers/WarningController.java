package hristov.mihail.carracing.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hristov.mihail.carracing.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WarningController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button okButton;

    @FXML
    private Label errorMessage;

    @FXML
    private Label labelCarName;

    public void setErrorMessage(String message) {
        this.errorMessage.setText(message);
    }

    @FXML
    void setOkButton(ActionEvent event) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        stage.close();
    }

    @FXML
    void initialize() {
        assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'warning-modal.fxml'.";
        assert errorMessage != null : "fx:id=\"errorMessage\" was not injected: check your FXML file 'warning-modal.fxml'.";
        assert labelCarName != null : "fx:id=\"labelCarName\" was not injected: check your FXML file 'warning-modal.fxml'.";

    }

}
