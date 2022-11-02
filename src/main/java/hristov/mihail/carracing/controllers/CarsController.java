package hristov.mihail.carracing.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.services.CarService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class CarsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Car, String> actions;

    @FXML
    private TableColumn<Car, String> brand;

    @FXML
    private TableColumn<Car, String> model;
    ObservableList<Car> carObservableList = FXCollections.observableList(CarService.getAllCar());
    @FXML
    private TableView<Car> table;

    @FXML
    void initialize() {
        assert actions != null : "fx:id=\"actions\" was not injected: check your FXML file 'cars.fxml'.";
        assert brand != null : "fx:id=\"brand\" was not injected: check your FXML file 'cars.fxml'.";
        assert model != null : "fx:id=\"model\" was not injected: check your FXML file 'cars.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'cars.fxml'.";
        brand.setCellValueFactory(new PropertyValueFactory<Car, String>("brandCar"));
        model.setCellValueFactory(new PropertyValueFactory<Car, String>("modelCar"));
        actions.setCellValueFactory(new PropertyValueFactory<Car, String>("fuelCar"));


        table.setItems(carObservableList);
    }

}
