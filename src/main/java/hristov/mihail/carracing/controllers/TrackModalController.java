package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.models.Track;
import hristov.mihail.carracing.services.CarService;
import hristov.mihail.carracing.services.TrackService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class TrackModalController {

    static PreparedStatement storeImage;
    static File file;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button applyChangeButton;
    @FXML
    private TextField nameTrackField;
    @FXML
    private ImageView trackImageView;
    @FXML
    private TextField lengthTrackField;
    @FXML
    private TextField locationTrackField;
    @FXML
    private TextField horsepowerCarField;
    @FXML
    private Label label;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label labelTrackName;
    @FXML
    private TextField modelCarField;
    @FXML
    private Button uploadImageButton;
    private Track track;
    private int trackId;

    public void setTrack(Track track) {
        this.track = track;
    }

    @FXML
    void applyChanges(ActionEvent event) {

        if (Objects.isNull(track)) {
            if (!(nameTrackField.getText().equals(null) || nameTrackField.getText().equals(null) || lengthTrackField.getText().equals(null) || locationTrackField.getText().equals(null) || nameTrackField.getText().equals("") || lengthTrackField.getText().equals("") || locationTrackField.getText().equals("") )) {

                    if (isNumeric(lengthTrackField.getText())) {
                        track = new Track( nameTrackField.getText(), Integer.parseInt(lengthTrackField.getText()), locationTrackField.getText());
                        TrackService.addTrack(track);
                        try {
                            if (!Objects.isNull(file)) {
                                FileInputStream fileInputStream = new FileInputStream(file);
                                storeImage = TrackService.setImageTrack();
                                storeImage.setBinaryStream(1, fileInputStream, fileInputStream.available());
                                storeImage.setInt(2, this.track.getIdTrack());
                                storeImage.execute();
                            }

                            nameTrackField.setText(track.getNameTrack());
                            locationTrackField.setText(track.getLocationTrack());
                            lengthTrackField.setText(Integer.toString(track.getLengthTrack()));

                            labelTrackName.setText(track.getNameTrack());

                            trackImageView.setImage(TrackService.getImageTrack(track));
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("track-modal.fxml"));


                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }


                            Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                            stage.setTitle("Редакция на " + track.getNameTrack());
                            // applyChangeButton.getScene().getWindow().setWidth(applyChangeButton.getScene().getWidth());

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                }
            } else {
                WarningController.openMessageModal("Попълнете всички данни за пистата!", "Празни данни", MessageType.WARNING);
            }
        } else {
            if (!(nameTrackField.getText().equals(null) || nameTrackField.getText().equals(null) || lengthTrackField.getText().equals(null) || locationTrackField.getText().equals(null) || nameTrackField.getText().equals("") || lengthTrackField.getText().equals("") || locationTrackField.getText().equals("") )) {
                {
                    if (isNumeric(lengthTrackField.getText())) {
                            track.setNameTrack(nameTrackField.getText());
                            track.setLocationTrack(locationTrackField.getText());
                            track.setLengthTrack(Integer.parseInt(lengthTrackField.getText()));
                            try {

                                try {
                                    if (!Objects.isNull(file)) {
                                        FileInputStream fileInputStream = new FileInputStream(file);
                                        storeImage = TrackService.setImageTrack();
                                        storeImage.setBinaryStream(1, fileInputStream, fileInputStream.available());
                                        storeImage.setInt(2, this.track.getIdTrack());
                                        storeImage.execute();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                TrackService.updateTrack(track);
                                Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("track-modal.fxml"));


                                Scene scene = null;
                                try {
                                    scene = new Scene(fxmlLoader.load());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                //dialogController.setLoggedUser(track.getIdTrack());
                                TrackModalController dialogController = fxmlLoader.getController();
                                dialogController.setTrack(track);
                                stage.setTitle("Редакция на " + track.getNameTrack());
                                stage.setScene(scene);
                                stage.show();
                                applyChangeButton.setText("Приложи");
                            } catch (Exception e) {
                                WarningController.openMessageModal("Въведете валидна дължина на писта!", "Невалидни данни", MessageType.WARNING);
                            }
                        }

                }
            } else {
                WarningController.openMessageModal("Попълнете всички данни за пистата!", "Празни данни", MessageType.WARNING);
            }
        }
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(uploadImageButton.getScene().getWindow());
        if (!Objects.isNull(file)) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);

                //storeImage.execute();
                trackImageView.setImage(new Image(fileInputStream));
            } catch (FileNotFoundException e) {
                Stage stage1 = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("warning-modal.fxml"));


                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                //messageController.setLoggedUser(track.getIdTrack());
                WarningController messageController = fxmlLoader.getController();
                messageController.setErrorMessage(e.getMessage());
                stage1.setTitle("Системна грешка");
                stage1.setScene(scene);
                stage1.show();
            } catch (IOException e) {
                WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
            }
        }
    }




    public boolean isNumeric(String strNum) {
        String regexPattern = "-?\\d+(\\.\\d+)?";
        if (!strNum.matches(regexPattern)) {
            WarningController.openMessageModal("Въведено е невалидно число за дължина на пистата!", "Невалидна дължина на пистата", MessageType.WARNING);
        }
        return strNum.matches(regexPattern);
    }

    @FXML
    void initialize() {

        Platform.runLater(() -> {
            assert applyChangeButton != null : "fx:id=\"applyChangeButton\" was not injected: check your FXML file 'track-modal.fxml'.";
            assert nameTrackField != null : "fx:id=\"nameTrackField\" was not injected: check your FXML file 'track-modal.fxml'.";
            assert trackImageView != null : "fx:id=\"trackImageView\" was not injected: check your FXML file 'track-modal.fxml'.";
            assert lengthTrackField != null : "fx:id=\"lengthTrackField\" was not injected: check your FXML file 'track-modal.fxml'.";
            assert locationTrackField != null : "fx:id=\"locationTrackField\" was not injected: check your FXML file 'track-modal.fxml'.";
            assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'track-modal.fxml'.";
            assert label1 != null : "fx:id=\"label1\" was not injected: check your FXML file 'track-modal.fxml'.";
            assert label2 != null : "fx:id=\"label2\" was not injected: check your FXML file 'track-modal.fxml'.";
            assert label3 != null : "fx:id=\"label3\" was not injected: check your FXML file 'track-modal.fxml'.";
            assert labelTrackName != null : "fx:id=\"labelTrackName\" was not injected: check your FXML file 'track-modal.fxml'.";


            nameTrackField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            lengthTrackField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            locationTrackField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });

            if (track != null) {
                //do stuff

                nameTrackField.setText(track.getNameTrack());
                locationTrackField.setText(track.getLocationTrack());
                lengthTrackField.setText(Integer.toString(track.getLengthTrack()));

                labelTrackName.setText(track.getNameTrack());

                trackImageView.setImage(TrackService.getImageTrack(track));
            } else {
                applyChangeButton.setText("Добави");
                labelTrackName.setText("Добавяне на писта");
            }
        });

    }

}
