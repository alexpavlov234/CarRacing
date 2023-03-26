package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.services.CarService;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class CarModalController {

    static File file;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button applyChangeButton;
    @FXML
    private TextField brandCarField;
    @FXML
    private ImageView carImageView;
    @FXML
    private TextField engineCarField;
    @FXML
    private TextField fuelCarField;
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
    private Label labelCarName;
    @FXML
    private TextField modelCarField;
    @FXML
    private Button uploadImageButton;
    private Car car;
    private int carId;

    public void setCar(Car car) {
        this.car = car;
    }

    @FXML
    void applyChanges(ActionEvent event) {
        if (Objects.isNull(car)) {
            if (!(Objects.isNull(modelCarField.getText()) || Objects.isNull(brandCarField.getText()) || Objects.isNull(engineCarField.getText()) || Objects.isNull(fuelCarField.getText()) || Objects.isNull(horsepowerCarField.getText()))) {
                if (!(modelCarField.getText().equals("") || brandCarField.getText().equals("") || engineCarField.getText().equals("") || fuelCarField.getText().equals("") || horsepowerCarField.getText().equals(""))) {
                    if (isValidEngine(engineCarField.getText())) {
                        if (isNumeric(horsepowerCarField.getText())) {
                            car = new Car(modelCarField.getText().trim().replace(" ", "-"), brandCarField.getText().trim(), engineCarField.getText().trim(), fuelCarField.getText().trim(), Integer.parseInt(horsepowerCarField.getText().trim()));
                            CarService.addCar(car);
                            car = CarService.getLastCar();
                            try {
                                if (!Objects.isNull(file)) {
                                    CarService.setImageCar(file, this.car);
                                    file = null;
                                }
                                modelCarField.setText(car.getModelCar());
                                brandCarField.setText(car.getBrandCar());
                                fuelCarField.setText(car.getFuelCar());
                                engineCarField.setText(car.getEngineCar());
                                horsepowerCarField.setText(Integer.toString(car.getHorsepowerCar()));
                                labelCarName.setText(car.getBrandCar() + " " + car.getModelCar());
                                carImageView.setImage(CarService.getImageCar(car));
                                Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("car-modal.fxml"));
                                stage.setResizable(false);

                                Scene scene = null;
                                try {
                                    scene = new Scene(fxmlLoader.load());
                                } catch (IOException e) {
                                    WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                                }

                                CarModalController dialogController = fxmlLoader.getController();
                                dialogController.setCar(car);
                                stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
                                stage.setTitle("Редакция на " + car.getBrandCar() + " " + car.getModelCar());
                                stage.setScene(scene);
                                stage.setResizable(false);
                                stage.show();
                                applyChangeButton.setText("Приложи");
                            } catch (Exception e) {
                                WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                            }
                        }
                    }
                } else {
                    WarningController.openMessageModal("Попълнете всички данни за колата!", "Празни данни", MessageType.WARNING);
                }

            } else {
                WarningController.openMessageModal("Попълнете всички данни за колата!", "Празни данни", MessageType.WARNING);
            }
        } else {
            if (!(Objects.isNull(modelCarField.getText()) || Objects.isNull(brandCarField.getText()) || Objects.isNull(engineCarField.getText()) || Objects.isNull(fuelCarField.getText()) || Objects.isNull(horsepowerCarField.getText()))) {
                if (!(modelCarField.getText().equals("") || brandCarField.getText().equals("") || engineCarField.getText().equals("") || fuelCarField.getText().equals("") || horsepowerCarField.getText().equals(""))) {
                    if (isValidEngine(engineCarField.getText())) {
                        if (isNumeric(horsepowerCarField.getText())) {
                            car.setModelCar(modelCarField.getText());
                            car.setBrandCar(brandCarField.getText());
                            car.setHorsepowerCar(Integer.parseInt(horsepowerCarField.getText()));
                            car.setFuelCar(fuelCarField.getText());
                            car.setEngineCar(engineCarField.getText());
                            try {
                                if (!Objects.isNull(file)) {
                                    CarService.setImageCar(file, this.car);
                                    file = null;
                                }
                            } catch (Exception e) {
                                WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                            }
                            CarService.updateCar(car);
                            Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("car-modal.fxml"));
                            stage.setResizable(false);

                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                            }
                            CarModalController dialogController = fxmlLoader.getController();
                            dialogController.setCar(car);
                            stage.setScene(scene);
                            stage.show();
                        }
                    }

                } else {
                    WarningController.openMessageModal("Попълнете всички данни за колата!", "Празни данни", MessageType.WARNING);
                }
            } else {
                WarningController.openMessageModal("Попълнете всички данни за колата!", "Празни данни", MessageType.WARNING);
            }
        }
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Файлове с изображения", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));
        file = fileChooser.showOpenDialog(uploadImageButton.getScene().getWindow());
        if (!Objects.isNull(file)) {
            try {

                FileInputStream fileInputStream = new FileInputStream(file);
                carImageView.setImage(new Image(fileInputStream));
            } catch (Exception e) {
                WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
            }
        } else {
            carImageView.setImage(CarService.getImageCar(car));
        }
    }

    public boolean isValidEngine(String name) {
        String regexPattern = "^[0-9].[0-9].*$";

        if (!name.matches(regexPattern)) {
            WarningController.openMessageModal("Въведено е невалидно име за двигател!", "Невалиден двигател", MessageType.WARNING);
        }
        return name.matches(regexPattern);
    }

    public boolean isNumeric(String strNum) {
        String regexPattern = "^[1-9]\\d*$";
        if (!strNum.matches(regexPattern)) {
            WarningController.openMessageModal("Въведено е невалидно число за конски сили!", "Невалидни конски сили", MessageType.WARNING);
        }
        return strNum.matches(regexPattern);
    }

    @FXML
    void initialize() {

        Platform.runLater(() -> {
            assert applyChangeButton != null : "fx:id=\"applyChangeButton\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert brandCarField != null : "fx:id=\"brandCarField\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert carImageView != null : "fx:id=\"carImageView\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert engineCarField != null : "fx:id=\"engineCarField\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert fuelCarField != null : "fx:id=\"fuelCarField\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert horsepowerCarField != null : "fx:id=\"horsepowerCarField\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert label1 != null : "fx:id=\"label1\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert label2 != null : "fx:id=\"label2\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert label3 != null : "fx:id=\"label3\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert labelCarName != null : "fx:id=\"labelCarName\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert modelCarField != null : "fx:id=\"modelCarField\" was not injected: check your FXML file 'car-modal.fxml'.";
            file = null;
            modelCarField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            brandCarField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            engineCarField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            fuelCarField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            horsepowerCarField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            if (!Objects.isNull(car)) {
                modelCarField.setText(car.getModelCar());
                brandCarField.setText(car.getBrandCar());
                fuelCarField.setText(car.getFuelCar());
                engineCarField.setText(car.getEngineCar());
                horsepowerCarField.setText(Integer.toString(car.getHorsepowerCar()));
                labelCarName.setText(car.getBrandCar() + " " + car.getModelCar());

                carImageView.setImage(CarService.getImageCar(car));
            } else {
                applyChangeButton.setText("Добави");
                ((Stage) applyChangeButton.getScene().getWindow()).setTitle("Добавяне на кола");
                try {
                    ((Stage) applyChangeButton.getScene().getWindow()).getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
                } catch (FileNotFoundException e) {
                    WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                }
                labelCarName.setText("Добавяне на кола");
            }

        });

    }

}
