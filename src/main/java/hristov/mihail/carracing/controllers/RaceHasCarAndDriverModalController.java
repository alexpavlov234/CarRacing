package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Race;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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

    @FXML
    private TextField points;
    private RaceHasCarAndDriver raceHasCarAndDriver;
    @FXML
    private ComboBox<String> raceCombobox;

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
            if (isNumeric(points.getText()) || Integer.parseInt(points.getText()) == 0) {
                raceHasCarAndDriver.setIdCar(CarService.getCar(carCombobox.getValue()).getIdCar());
                raceHasCarAndDriver.setIdDriver(PersonService.getPerson(driverCombobox.getValue()).getIdPerson());
                raceHasCarAndDriver.setIdRace(RaceService.getRace(raceCombobox.getValue()).getIdRace());
                raceHasCarAndDriver.setPoints(Integer.parseInt(points.getText()));
                if (RaceHasCarAndDriverService.areTherePlacesAvailable(RaceService.getRace(raceHasCarAndDriver.getIdRace()))) {
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
                            stage.show();
                        } else {
                            WarningController.openMessageModal("Този състезател вече участва в това състезание!", "Повторно участие в състезание", MessageType.WARNING);
                        }
                    }
                } else {
                    WarningController.openMessageModal("Няма повече свободни места в избраното състезание!", "Няма свободни места", MessageType.WARNING);
                }
            } else {
                WarningController.openMessageModal("Въведено е невалидно число за точки!", "Невалидни точки", MessageType.WARNING);
            }

        } else {
            if (isNumeric(points.getText()) || Integer.parseInt(points.getText()) == 0) {
                raceHasCarAndDriver = new RaceHasCarAndDriver();
                raceHasCarAndDriver.setIdCar(CarService.getCar(carCombobox.getValue()).getIdCar());
                raceHasCarAndDriver.setIdDriver(PersonService.getPerson(driverCombobox.getValue()).getIdPerson());
                raceHasCarAndDriver.setIdRace(RaceService.getRace(raceCombobox.getValue()).getIdRace());
                raceHasCarAndDriver.setPoints(Integer.parseInt(points.getText()));
                if (RaceHasCarAndDriverService.areTherePlacesAvailable(RaceService.getRace(raceHasCarAndDriver.getIdRace()))) {
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
                        stage.show();
                    } else {
                        WarningController.openMessageModal("Този състезател вече участва в това състезание!", "Повторно участие в състезание", MessageType.WARNING);
                    }
                } else {
                    WarningController.openMessageModal("Няма повече свободни места в избраното състезание!", "Няма свободни места", MessageType.WARNING);
                }
            } else {
                WarningController.openMessageModal("Въведено е невалидно число за точки!", "Невалидни точки", MessageType.WARNING);

            }
        }

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
            assert points != null : "fx:id=\"points\" was not injected: check your FXML file 'race-has-car-and-driver-modal.fxml'.";
            assert raceCombobox != null : "fx:id=\"raceCombobox\" was not injected: check your FXML file 'race-has-car-and-driver-modal.fxml'.";

            carCombobox.getItems().addAll(CarService.getAllCarNames());
            raceCombobox.getItems().addAll(RaceService.getAllFreeRacesNames());
            driverCombobox.getItems().addAll(PersonService.getAllPersonNames());

            if (!Objects.isNull(raceHasCarAndDriver)) {
                if (LoginService.isLoggedUserAdmin()) {
                    carCombobox.setValue(CarService.getCar(raceHasCarAndDriver.getIdCar()).getNameCar());
                    raceCombobox.setValue(RaceService.getRace(raceHasCarAndDriver.getIdRace()).getNameRace());
                    driverCombobox.setValue(PersonService.getPerson(raceHasCarAndDriver.getIdDriver()).getNamePerson());
                    points.setText(Integer.toString(raceHasCarAndDriver.getPoints()));
                } else {
                    carCombobox.setValue(CarService.getCar(raceHasCarAndDriver.getIdCar()).getNameCar());
                    raceCombobox.setValue(RaceService.getRace(raceHasCarAndDriver.getIdRace()).getNameRace());
                    driverCombobox.setValue(PersonService.getPerson(raceHasCarAndDriver.getIdDriver()).getNamePerson());
                    points.setText(Integer.toString(raceHasCarAndDriver.getPoints()));
                    driverCombobox.setDisable(true);
                    raceCombobox.setDisable(true);
                    points.setEditable(false);
                }
            } else {
                if (LoginService.isLoggedUserAdmin() && Objects.isNull(raceHasCarAndDriver)) {
                    applyChangeButton.setText("Добави");
                }
            }
        });
    }

}
