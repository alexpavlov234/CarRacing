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

    public static void openMessageModal(String text, String windowTitle, MessageType messageType){
        if(messageType == MessageType.WARNING) {
            Stage stage1 = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("warning-modal.fxml"));


            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            //messageController.setLoggedUser(car.getIdCar());
            WarningController messageController = fxmlLoader.getController();
            messageController.setErrorMessage(text);
            stage1.setTitle(windowTitle);
            stage1.setScene(scene);
            stage1.setResizable(false);
            stage1.show();
        } else if (messageType == MessageType.SUCCESS){
            Stage stage1 = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("success-modal.fxml"));


            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            //messageController.setLoggedUser(car.getIdCar());
            WarningController messageController = fxmlLoader.getController();
            messageController.setErrorMessage(text);
            stage1.setTitle(windowTitle);
            stage1.setScene(scene);
            stage1.setResizable(false);
            stage1.show();
        }
    }
    @FXML
    void initialize() {
        assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'warning-modal.fxml'.";
        assert errorMessage != null : "fx:id=\"errorMessage\" was not injected: check your FXML file 'warning-modal.fxml'.";
        assert labelCarName != null : "fx:id=\"labelCarName\" was not injected: check your FXML file 'warning-modal.fxml'.";

    }

}
