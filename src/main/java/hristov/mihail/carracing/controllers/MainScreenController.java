package hristov.mihail.carracing.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class MainScreenController {

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
    void exitUser(ActionEvent event) {
        try {
            LoginService.logoutUser();
            Stage stage = (Stage) exitUserButton.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("Вход");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            //TODO: Екран за грешка
        }
    }

    @FXML
    void handleShowView(ActionEvent event) {
        loadFXML("cars.fxml");
    }
    @FXML
    private void handleShowView1(ActionEvent e) {
        loadFXML("races.fxml");
    }

    private void loadFXML(String url) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(url));
            mainBorderPane.setCenter(fxmlLoader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleShowView2(ActionEvent e) {
        loadFXML("/sample/view_2.fxml");
    }

    @FXML
    private void handleShowView3(ActionEvent e) {
        loadFXML("/sample/view_3.fxml");
    }

    @FXML
    void initialize() {
        assert carScreenButton != null : "fx:id=\"carScreenButton\" was not injected: check your FXML file 'main-screen.fxml'.";
        assert exitUserButton != null : "fx:id=\"exitUserButton\" was not injected: check your FXML file 'main-screen.fxml'.";
        assert profileUserButton != null : "fx:id=\"profileUserButton\" was not injected: check your FXML file 'main-screen.fxml'.";
        assert personScreenButton != null : "fx:id=\"personScreenButton\" was not injected: check your FXML file 'main-screen.fxml'.";
        assert profileNameLabel != null : "fx:id=\"profileNameLabel\" was not injected: check your FXML file 'main-screen.fxml'.";
        assert mainBorderPane != null : "fx:id=\"mainBorderPane\" was not injected: check your FXML file 'main-screen.fxml'.";
        assert raceScreenButton != null : "fx:id=\"raceScreenButton\" was not injected: check your FXML file 'main-screen.fxml'.";
        assert tracksScreenButton != null : "fx:id=\"tracksScreenButton\" was not injected: check your FXML file 'main-screen.fxml'.";
        Person loggedPerson = PersonService.getPerson(LoginService.getUser().getUserHasPerson());
        profileNameLabel.setText("Здравей, \n"  +  loggedPerson.getFirstNamePerson() + " " + loggedPerson.getLastNamePerson());
    }

}
