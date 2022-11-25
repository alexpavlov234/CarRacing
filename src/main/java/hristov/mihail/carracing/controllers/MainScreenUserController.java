package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.services.LoginService;
import hristov.mihail.carracing.services.PersonService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button carScreenButton;

    @FXML
    private Button exitUserButton;

    @FXML
    private Button profileUserButton;

    @FXML
    private Button personScreenButton;

    @FXML
    private Label profileNameLabel;

    @FXML
    private Button raceScreenButton;

    @FXML
    private Button tracksScreenButton;

    @FXML
    private Button rankingScreenButton;

    @FXML
    void exitUser(ActionEvent event) {
        try {
            LoginService.logoutUser();
            Stage stage = (Stage) exitUserButton.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("Вход");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
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
            messageController.setErrorMessage(e.getMessage());
            stage1.setTitle("Системна грешка");
            stage1.setScene(scene);
            stage1.show();
        }
    }

    @FXML
    void editProfile(ActionEvent event) {
        try {

            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("edit-user.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("Редакция на профил");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка",MessageType.WARNING);
        }
    }
    @FXML
    void handleShowView(ActionEvent event) {
        try {loadFXML("cars.fxml");} catch (Exception e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка",MessageType.WARNING);
        }

    }

    @FXML
    private void handleShowView1(ActionEvent event) {
        try {loadFXML("tracks.fxml");} catch (Exception e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка",MessageType.WARNING);
        }
    }

    private void loadFXML(String url) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(url));
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
           e.printStackTrace();
        WarningController.openMessageModal(e.getMessage(),"Системна грешка",MessageType.WARNING);
        }
    }

    @FXML
    private void handleShowView2(ActionEvent event) {
        try {loadFXML("tracks.fxml");} catch (Exception e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка",MessageType.WARNING);
        }
    }

    @FXML
    private void handleShowView3(ActionEvent event) {

        try {loadFXML("ranking.fxml");} catch (Exception e) {
             WarningController.openMessageModal(e.getMessage(), "Системна грешка",MessageType.WARNING);
        }
    }



    @FXML
    void initialize() {
        assert carScreenButton != null : "fx:id=\"carScreenButton\" was not injected: check your FXML file 'main-screen-admin.fxml'.";
        assert exitUserButton != null : "fx:id=\"exitUserButton\" was not injected: check your FXML file 'main-screen-admin.fxml'.";
        assert profileUserButton != null : "fx:id=\"profileUserButton\" was not injected: check your FXML file 'main-screen-admin.fxml'.";
        assert personScreenButton != null : "fx:id=\"personScreenButton\" was not injected: check your FXML file 'main-screen-admin.fxml'.";
        assert profileNameLabel != null : "fx:id=\"profileNameLabel\" was not injected: check your FXML file 'main-screen-admin.fxml'.";
        assert mainBorderPane != null : "fx:id=\"mainBorderPane\" was not injected: check your FXML file 'main-screen-admin.fxml'.";
        assert raceScreenButton != null : "fx:id=\"raceScreenButton\" was not injected: check your FXML file 'main-screen-admin.fxml'.";
        assert tracksScreenButton != null : "fx:id=\"tracksScreenButton\" was not injected: check your FXML file 'main-screen-admin.fxml'.";
        Person loggedPerson = PersonService.getPerson(LoginService.getLoggedUser().getUserHasPerson());
        profileNameLabel.setText("Здравей, \n" + loggedPerson.getFirstNamePerson() + " " + loggedPerson.getLastNamePerson());

    }

}
