package hristov.mihail.carracing.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.services.CarService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CarModalController {

    static PreparedStatement storeImage;
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
        if (car == null) {
            car = new Car(modelCarField.getText(), brandCarField.getText(), engineCarField.getText(), fuelCarField.getText(), Integer.parseInt(horsepowerCarField.getText()));
            CarService.addCar(car);
            car = CarService.getLastCar();
            try {

                FileInputStream fileInputStream = new FileInputStream(file);
                storeImage = CarService.setImageCar();
                storeImage.setBinaryStream(1, fileInputStream, fileInputStream.available());
                storeImage.setInt(2, this.car.getIdCar());
                storeImage.execute();

                modelCarField.setText(car.getModelCar());
                brandCarField.setText(car.getBrandCar());
                fuelCarField.setText(car.getFuelCar());
                engineCarField.setText(car.getEngineCar());
                horsepowerCarField.setText(Integer.toString(car.getHorsepowerCar()));
                labelCarName.setText(car.getBrandCar() + " " + car.getModelCar());

                carImageView.setImage(CarService.getImageCar(car));
                Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                stage.setTitle("Редакция на " + car.getBrandCar() + " " + car.getModelCar());
                applyChangeButton.setText("Приложи");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            car.setModelCar(modelCarField.getText());
            car.setBrandCar(brandCarField.getText());
            car.setFuelCar(fuelCarField.getText());
            car.setEngineCar(engineCarField.getText());
            car.setHorsepowerCar(Integer.parseInt(horsepowerCarField.getText()));


            try {

                    FileInputStream fileInputStream = new FileInputStream(file);
                    storeImage = CarService.setImageCar();
                    storeImage.setBinaryStream(1, fileInputStream, fileInputStream.available());
                    storeImage.setInt(2, this.car.getIdCar());
                    storeImage.execute();

            } catch (SQLException e) {
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
            } catch (IOException e) {
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
            CarService.updateCar(car);
        }
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(uploadImageButton.getScene().getWindow());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            //storeImage.execute();
            carImageView.setImage(new Image(fileInputStream));
        } catch (FileNotFoundException e) {
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
        } catch (IOException e) {
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

            if (car != null) {
                //do stuff
                modelCarField.setText(car.getModelCar());
                brandCarField.setText(car.getBrandCar());
                fuelCarField.setText(car.getFuelCar());
                engineCarField.setText(car.getEngineCar());
                horsepowerCarField.setText(Integer.toString(car.getHorsepowerCar()));
                labelCarName.setText(car.getBrandCar() + " " + car.getModelCar());

                carImageView.setImage(CarService.getImageCar(car));
            } else {
                applyChangeButton.setText("Добави");
                labelCarName.setText("Добавяне на кола");
            }
        });

    }

}
