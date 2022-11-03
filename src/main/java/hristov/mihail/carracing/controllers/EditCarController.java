package hristov.mihail.carracing.controllers;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.services.CarService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EditCarController {

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

    private Car car;
    private int carId;



    public void setUser(Car car){
        this.car = car;
    }
    @FXML
    void applyChanges(ActionEvent event) {
        car.setModelCar(modelCarField.getText());
        car.setBrandCar(brandCarField.getText());
        car.setFuelCar(fuelCarField.getText());
        car.setEngineCar(engineCarField.getText());
        car.setHorsepowerCar(Integer.parseInt(horsepowerCarField.getText()));
        CarService.updateCar(car);
    }

    @FXML
    void initialize() {

          Platform.runLater(() -> {
              assert applyChangeButton != null : "fx:id=\"applyChangeButton\" was not injected: check your FXML file 'edit-car.fxml'.";
            assert brandCarField != null : "fx:id=\"brandCarField\" was not injected: check your FXML file 'edit-car.fxml'.";
            assert carImageView != null : "fx:id=\"carImageView\" was not injected: check your FXML file 'edit-car.fxml'.";
            assert engineCarField != null : "fx:id=\"engineCarField\" was not injected: check your FXML file 'edit-car.fxml'.";
            assert fuelCarField != null : "fx:id=\"fuelCarField\" was not injected: check your FXML file 'edit-car.fxml'.";
            assert horsepowerCarField != null : "fx:id=\"horsepowerCarField\" was not injected: check your FXML file 'edit-car.fxml'.";
            assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'edit-car.fxml'.";
            assert label1 != null : "fx:id=\"label1\" was not injected: check your FXML file 'edit-car.fxml'.";
            assert label2 != null : "fx:id=\"label2\" was not injected: check your FXML file 'edit-car.fxml'.";
            assert label3 != null : "fx:id=\"label3\" was not injected: check your FXML file 'edit-car.fxml'.";
            assert labelCarName != null : "fx:id=\"labelCarName\" was not injected: check your FXML file 'edit-car.fxml'.";
            assert modelCarField != null : "fx:id=\"modelCarField\" was not injected: check your FXML file 'edit-car.fxml'.";


            //do stuff
            modelCarField.setText(car.getModelCar());
            brandCarField.setText(car.getBrandCar());
            fuelCarField.setText(car.getFuelCar());
            engineCarField.setText(car.getEngineCar());
            horsepowerCarField.setText(Integer.toString(car.getHorsepowerCar()));
            labelCarName.setText(car.getBrandCar() + " "+ car.getModelCar());
              File file2 = null;
              try {
                  file2 = new File(getClass().getClassLoader().getResource("img.png").toURI());
              } catch (URISyntaxException e) {
                  e.printStackTrace();
              }
              Image image2 = new Image(file2.toURI().toString());
              carImageView.setImage(image2);
        });

    }

}
