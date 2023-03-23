package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.RaceHasCarAndDriver;
import hristov.mihail.carracing.services.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class RaceHasCarAndDriverModalController {

    @FXML
    private Button applyChangeButton;

    @FXML
    private ComboBox<String> carCombobox;

    @FXML
    private ComboBox<String> driverCombobox;

    @FXML
    private Label label;

    @FXML
    private Label label1;

    @FXML
    private Label label11;

    @FXML
    private Label label4;

    @FXML
    private Label labelUserName;

    private RaceHasCarAndDriver raceHasCarAndDriver;
    @FXML
    private ComboBox<String> raceCombobox;
    private boolean isNotEditModal;

    public void setRaceHasCarAndDriver(RaceHasCarAndDriver raceHasCarAndDriver) {
        this.raceHasCarAndDriver = raceHasCarAndDriver;
    }

    public boolean isNumeric(String strNum) {
        String regexPattern = "^[1-9]\\d*$";

        return strNum.matches(regexPattern);
    }

    @FXML
    void applyChanges(ActionEvent event) {
        if (!Objects.isNull(raceHasCarAndDriver)) {
            if (carCombobox.getValue() != null && raceCombobox.getValue() != null && driverCombobox.getValue() != null) {
                raceHasCarAndDriver.setIdCar(CarService.getCar(carCombobox.getValue()).getIdCar());
                raceHasCarAndDriver.setIdDriver(PersonService.getPerson(driverCombobox.getValue()).getIdPerson());
                raceHasCarAndDriver.setIdRace(RaceService.getRace(raceCombobox.getValue()).getIdRace());
                raceHasCarAndDriver.setPoints(0);
                if ((raceHasCarAndDriver.getIdCar() == CarService.getCar(carCombobox.getValue()).getIdCar() && raceHasCarAndDriver.getIdDriver() == PersonService.getPerson(driverCombobox.getValue()).getIdPerson())) {


                    RaceHasCarAndDriverService.updateRaceHasCarAndDriver(raceHasCarAndDriver);
                    Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("race-has-car-and-driver-modal.fxml"));

                    Scene scene = null;
                    try {
                        scene = new Scene(fxmlLoader.load());
                    } catch (IOException e) {
                        WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                    }
                    // Обновяваме нашето прозорче за всеки случай.
                    //dialogController.setLoggedUser(car.getIdCar());
                    RaceHasCarAndDriverModalController dialogController = fxmlLoader.getController();
                    dialogController.setRaceHasCarAndDriver(raceHasCarAndDriver);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } else {
                    if (!(RaceHasCarAndDriverService.isParticipatingInRace(raceHasCarAndDriver.getIdRace(), raceHasCarAndDriver.getIdDriver()))) {
                        RaceHasCarAndDriverService.updateRaceHasCarAndDriver(raceHasCarAndDriver);
                        Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("race-has-car-and-driver-modal.fxml"));

                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                        }
                        // Обновяваме нашето прозорче за всеки случай.
                        //dialogController.setLoggedUser(car.getIdCar());
                        RaceHasCarAndDriverModalController dialogController = fxmlLoader.getController();
                        dialogController.setRaceHasCarAndDriver(raceHasCarAndDriver);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                    } else {
                        WarningController.openMessageModal("Този състезател вече участва в това състезание!", "Повторно участие в състезание", MessageType.WARNING);
                    }
                }
            } else {
                WarningController.openMessageModal("Попълнете всички данни за участието!", "Празни данни", MessageType.WARNING);

            }
        } else {
            if (carCombobox.getValue() != null && raceCombobox.getValue() != null && driverCombobox.getValue() != null) {
                raceHasCarAndDriver = new RaceHasCarAndDriver();
                raceHasCarAndDriver.setIdCar(CarService.getCar(carCombobox.getValue()).getIdCar());
                raceHasCarAndDriver.setIdDriver(PersonService.getPerson(driverCombobox.getValue()).getIdPerson());
                raceHasCarAndDriver.setIdRace(RaceService.getRace(raceCombobox.getValue()).getIdRace());
                raceHasCarAndDriver.setPoints(0);
                if (!RaceHasCarAndDriverService.isParticipatingInRace(raceHasCarAndDriver.getIdRace(), raceHasCarAndDriver.getIdDriver())) {

                    RaceHasCarAndDriverService.addRaceHasCarAndDriver(raceHasCarAndDriver);
                    Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("race-has-car-and-driver-modal.fxml"));


                    Scene scene = null;
                    try {
                        scene = new Scene(fxmlLoader.load());
                    } catch (IOException e) {
                        WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                    }
                    // Обновяваме нашето прозорче за всеки случай.
                    //dialogController.setLoggedUser(car.getIdCar());
                    RaceHasCarAndDriverModalController dialogController = fxmlLoader.getController();
                    dialogController.setRaceHasCarAndDriver(raceHasCarAndDriver);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } else {
                    WarningController.openMessageModal("Този състезател вече участва в това състезание!", "Повторно участие в състезание", MessageType.WARNING);
                }
            } else {
                WarningController.openMessageModal("Попълнете всички данни за участието!", "Празни данни", MessageType.WARNING);

            }
        }

    }

    @FXML
    private void onSelectComboBox(ActionEvent event) {
        int idRace = RaceService.getRace(raceCombobox.getValue()).getIdRace();
        ArrayList<String> personNames = PersonService.getAllPersonNames();
        ArrayList<RaceHasCarAndDriver> raceHasCarAndDriverArrayList = RaceHasCarAndDriverService.getAllRaceHasCarAndDriver(idRace);
        for (RaceHasCarAndDriver raceHasCarAndDriver : raceHasCarAndDriverArrayList) {
            for (int i = 0; i < personNames.size(); i++) {
                if (!Objects.isNull(PersonService.getPerson(personNames.get(i)))) {
                    if (PersonService.getPerson(personNames.get(i)).getIdPerson() == raceHasCarAndDriver.getIdDriver()) {
                        personNames.remove(i);
                    }
                }
            }

        }
        driverCombobox.getItems().clear();
        driverCombobox.getItems().addAll(personNames);
        if (isNotEditModal) {
            driverCombobox.setDisable(false);
        }
        carCombobox.setDisable(false);

    }

    @FXML
    void initialize() {
        Platform.runLater(() -> {
            assert applyChangeButton != null : "fx:id=\"applyChangeButton\" was not injected: check your FXML file 'race-has-car-and-driver-modal.fxml'.";
            assert carCombobox != null : "fx:id=\"carCombobox\" was not injected: check your FXML file 'race-has-car-and-driver-modal.fxml'.";
            assert driverCombobox != null : "fx:id=\"driverCombobox\" was not injected: check your FXML file 'race-has-car-and-driver-modal.fxml'.";
            assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'race-has-car-and-driver-modal.fxml'.";
            assert label1 != null : "fx:id=\"label1\" was not injected: check your FXML file 'race-has-car-and-driver-modal.fxml'.";
            assert label11 != null : "fx:id=\"label11\" was not injected: check your FXML file 'race-has-car-and-driver-modal.fxml'.";
            assert label4 != null : "fx:id=\"label4\" was not injected: check your FXML file 'race-has-car-and-driver-modal.fxml'.";
            assert labelUserName != null : "fx:id=\"labelUserName\" was not injected: check your FXML file 'race-has-car-and-driver-modal.fxml'.";
            assert raceCombobox != null : "fx:id=\"raceCombobox\" was not injected: check your FXML file 'race-has-car-and-driver-modal.fxml'.";


            isNotEditModal = Objects.isNull(raceHasCarAndDriver);
            if (!Objects.isNull(raceHasCarAndDriver)) {
                carCombobox.getItems().addAll(CarService.getAllCarNames());
                raceCombobox.getItems().addAll(RaceService.getAllFreeRacesNames());
                raceCombobox.setDisable(true);
                driverCombobox.getItems().addAll(PersonService.getAllPersonNames());
                driverCombobox.setDisable(true);
                if (LoginService.isLoggedUserAdmin()) {
                    carCombobox.setValue(CarService.getCar(raceHasCarAndDriver.getIdCar()).getNameCar());
                    raceCombobox.setValue(RaceService.getRace(raceHasCarAndDriver.getIdRace()).getNameRace());
                    driverCombobox.setValue(PersonService.getPerson(raceHasCarAndDriver.getIdDriver()).getNamePerson());
                } else {
                    carCombobox.setValue(CarService.getCar(raceHasCarAndDriver.getIdCar()).getNameCar());
                    raceCombobox.setValue(RaceService.getRace(raceHasCarAndDriver.getIdRace()).getNameRace());
                    driverCombobox.setValue(PersonService.getPerson(raceHasCarAndDriver.getIdDriver()).getNamePerson());
                    driverCombobox.setDisable(true);
                    raceCombobox.setDisable(true);
                }
            } else {
                carCombobox.setDisable(true);
                driverCombobox.setDisable(true);
                carCombobox.getItems().addAll(CarService.getAllCarNames());
                raceCombobox.getItems().addAll(RaceService.getAllFreeRacesNames());
                driverCombobox.getItems().addAll(PersonService.getAllPersonNames());
                if (LoginService.isLoggedUserAdmin() && Objects.isNull(raceHasCarAndDriver)) {
                    applyChangeButton.setText("Добави");
                }
            }
        });
    }

}
