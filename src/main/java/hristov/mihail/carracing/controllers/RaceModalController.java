package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Race;
import hristov.mihail.carracing.services.RaceService;
import hristov.mihail.carracing.services.TrackService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;

public class RaceModalController {

    static PreparedStatement storeImage;
    static File file;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button applyChangeButton;
    @FXML
    private ComboBox<String> trackRaceCombobox;

    @FXML
    private TextField lapsRaceField;
    @FXML
    private TextField participantsRaceField;
    @FXML
    private TextField pointsRaceField;
    @FXML
    private Label label;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label labelRaceName;
    @FXML
    private TextField dateRaceField;

    private Race race;

    public boolean isValidDate(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    // Нашият метод, който приема обект, който ще се зареди в нашия модал. Тоест като цъкнеш редактирай този метод получава от таблицата обекта, който ще редактираме и го задава за нашия модал.
    public void setRace(Race race) {
        this.race = race;
    }

    // Какво да се случва като цъкнеш бутона приложи.
    @FXML
    void applyChanges(ActionEvent event) {
        // Програма да не би нашия обект да е празен. Ако е празен, значи е избран прозорец за добавяне на кола.
        // Ако не е празен, значи ще променяме кола
        if (Objects.isNull(race)) {
            // Проверяваме дали въведените полета са с коректни данни.
            if (!(Objects.isNull(dateRaceField.getText()) || Objects.isNull(lapsRaceField.getText()) || Objects.isNull(participantsRaceField.getText()) || Objects.isNull(pointsRaceField.getText()))) {
                if (!(dateRaceField.getText().equals("") || lapsRaceField.getText().equals("") || participantsRaceField.getText().equals("") || pointsRaceField.getText().equals(""))) {
                    // Проверяваме дали нашият двигател е с валидно число.
                    if (isNumeric(lapsRaceField.getText())) {
                        // Проверяваме дали е въведено число за конски сили.
                        if (isNumeric(pointsRaceField.getText())) {
                            if (isNumeric(participantsRaceField.getText())) {
                                if (isValidDate(dateRaceField.getText())) {


                                    try {
                                        // Ако всичко е наред с данните, значи може да запишем нашия нов обект в базата данни.
                                        // Запазваме информацията от нашите полета в нов обект и този обект го добавяме в базата данни.
                                        race = new Race(TrackService.getTrack(trackRaceCombobox.getValue()).getIdTrack(), dateRaceField.getText(), Integer.parseInt(lapsRaceField.getText()), Integer.parseInt(pointsRaceField.getText()), Integer.parseInt(participantsRaceField.getText()));
                                        RaceService.addRace(race);
                                        // Извличаме от базата данни новосъздадения обект, защото така е редно, ако има някакво форматиране на данните от нашата база данни.
                                        race = RaceService.getLastRace();

                                        // Задаваме нашите полета да бъдат равни на полетата от нашия обект. Нали след като записахме колата, изтеглихме отново от базата данни за всеки случай.
                                        dateRaceField.setText(race.getDateRace());
                                        System.out.println(race.getTrackRace());
                                        trackRaceCombobox.setValue(TrackService.getTrack(race.getTrackRace()).getNameTrack());
                                        participantsRaceField.setText(Integer.toString(race.getParticipantsRace()));
                                        lapsRaceField.setText(Integer.toString(race.getLapsRace()));
                                        pointsRaceField.setText(Integer.toString(race.getPointsRace()));
                                        labelRaceName.setText(race.getNameRace());
                                        // Задаваме изображение на ImageView-то

                                        // Обновяваме нашия модел и го отваряме като модал за редакция на вече създадената кола.
                                        Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("race-modal.fxml"));


                                        Scene scene = null;
                                        try {
                                            scene = new Scene(fxmlLoader.load());
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }

                                        RaceModalController dialogController = fxmlLoader.getController();
                                        dialogController.setRace(race);
                                        stage.setTitle("Редакция на " + race.getNameRace());
                                        stage.setScene(scene);
                                        stage.show();
                                        //Stage stage = (Stage) applyChangeButton.getScene().getWindow();

                                        applyChangeButton.setText("Приложи");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                                    }
                                } else {
                                    WarningController.openMessageModal("Въведено е невалидна дата!", "Невалидна дата", MessageType.WARNING);

                                }
                            } else {
                                WarningController.openMessageModal("Въведено е невалидно число за брой участници!", "Невалиден брой участници", MessageType.WARNING);
                            }
                        } else {
                            WarningController.openMessageModal("Въведено е невалидно число за точки!", "Невалидни точки", MessageType.WARNING);

                        }
                    } else {
                        WarningController.openMessageModal("Въведено е невалидно число за обиколки!", "Невалидни обиколки", MessageType.WARNING);

                    }
                } else {
                    WarningController.openMessageModal("Попълнете всички данни за състезанието!", "Празни данни", MessageType.WARNING);
                }

            } else {
                WarningController.openMessageModal("Попълнете всички данни за състезанието!", "Празни данни", MessageType.WARNING);
            }
        } else {
            // Ако обектът съдържащ колата, не е празен, това значи, че искаме да бъде актуализиран в базата данни.
            // Извършваме нужните проверки на въведените данни.
            if (!(Objects.isNull(dateRaceField.getText()) || Objects.isNull(lapsRaceField.getText()) || Objects.isNull(participantsRaceField.getText()) || Objects.isNull(pointsRaceField.getText()))) {
                if (!(dateRaceField.getText().equals("") || lapsRaceField.getText().equals("") || participantsRaceField.getText().equals("") || pointsRaceField.getText().equals(""))) {
                    // Проверяваме дали нашият двигател е с валидно число.
                    if (isNumeric(lapsRaceField.getText())) {
                        // Проверяваме дали е въведено число за конски сили.
                        if (isNumeric(pointsRaceField.getText())) {
                            if (isNumeric(participantsRaceField.getText())) {
                                if (isValidDate(dateRaceField.getText())) {
                                    // Актуализираме данните на нашата кола.

                                    race.setDateRace(dateRaceField.getText());
                                    race.setLapsRace(Integer.parseInt(lapsRaceField.getText()));
                                    race.setPointsRace(Integer.parseInt(pointsRaceField.getText()));
                                    race.setParticipantsRace(Integer.parseInt(participantsRaceField.getText()));
                                    race.setTrackRace(TrackService.getTrack(trackRaceCombobox.getValue()).getIdTrack());
                                    // Зареждаме каченото изображение и го задаваме на нашия обект.

                                    RaceService.updateRace(race);
                                    Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("race-modal.fxml"));


                                    Scene scene = null;
                                    try {
                                        scene = new Scene(fxmlLoader.load());
                                    } catch (IOException e) {
                                        WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                                    }
                                    // Обновяваме нашето прозорче за всеки случай.
                                    //dialogController.setLoggedUser(car.getIdCar());
                                    RaceModalController dialogController = fxmlLoader.getController();
                                    dialogController.setRace(race);
                                    stage.setScene(scene);
                                    stage.show();
                                    //Stage stage = (Stage) applyChangeButton.getScene().getWindow();

                                } else {
                                    WarningController.openMessageModal("Въведено е невалидна дата!", "Невалидна дата", MessageType.WARNING);
                                }
                            } else {
                                WarningController.openMessageModal("Въведено е невалидно число за брой участници!", "Невалиден брой участници", MessageType.WARNING);
                            }
                        } else {
                            WarningController.openMessageModal("Въведено е невалидно число за точки!", "Невалидни точки", MessageType.WARNING);

                        }
                    } else {
                        WarningController.openMessageModal("Въведено е невалидно число за обиколки!", "Невалидни обиколки", MessageType.WARNING);

                    }

                } else {
                    WarningController.openMessageModal("Попълнете всички данни за състезанието!", "Празни данни", MessageType.WARNING);
                }


            } else {
                WarningController.openMessageModal("Попълнете всички данни за състезанието!", "Празни данни", MessageType.WARNING);
            }
        }
    }


    // Проверка чрез regex дали даден низ е само от числа.
    public boolean isNumeric(String strNum) {
        String regexPattern = "^[1-9]\\d*$";
        return strNum.matches(regexPattern);
    }

    // Кметът, който се изпълнява при отварянето на нашия модел.
    @FXML
    void initialize() {

        Platform.runLater(() -> {
            assert applyChangeButton != null : "fx:id=\"applyChangeButton\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert trackRaceCombobox != null : "fx:id=\"trackRaceField\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert lapsRaceField != null : "fx:id=\"lapsRaceField\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert participantsRaceField != null : "fx:id=\"participantsRaceField\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert pointsRaceField != null : "fx:id=\"pointsRaceField\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert label1 != null : "fx:id=\"label1\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert label2 != null : "fx:id=\"label2\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert label3 != null : "fx:id=\"label3\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert labelRaceName != null : "fx:id=\"labelCarName\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert dateRaceField != null : "fx:id=\"dateRaceField\" was not injected: check your FXML file 'car-modal.fxml'.";
            dateRaceField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            trackRaceCombobox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            lapsRaceField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            participantsRaceField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            pointsRaceField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            trackRaceCombobox.getItems().addAll(TrackService.getAllTrackNames());
            // Ако колата не е празна, прозорецът ще се зададе като такъв за редактиране на данни и ако е - за добавяне на кола.
            if (!Objects.isNull(race)) {
                //do stuff
                dateRaceField.setText(race.getDateRace());
                trackRaceCombobox.setValue(TrackService.getTrack(race.getTrackRace()).getNameTrack());
                participantsRaceField.setText(Integer.toString(race.getParticipantsRace()));
                lapsRaceField.setText(Integer.toString(race.getLapsRace()));
                pointsRaceField.setText(Integer.toString(race.getPointsRace()));
                labelRaceName.setText(race.getNameRace());


            } else {
                applyChangeButton.setText("Добави");
                ((Stage) applyChangeButton.getScene().getWindow()).setTitle("Добавяне на състезание");

                labelRaceName.setText("Добавяне на състезание");
            }
        });

    }

}
