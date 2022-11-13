package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.services.CarService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CarsController {

    ObservableList<Car> carObservableList = FXCollections.observableList(CarService.getAllCar());
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button addCar;
    @FXML
    private TableColumn<Car, String> actions;
    @FXML
    private TableColumn<Car, String> brand;
    @FXML
    private TableColumn<Car, String> model;
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

        Callback<TableColumn<Car, String>, TableCell<Car, String>> cellFactory = //
                new Callback<TableColumn<Car, String>, TableCell<Car, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Car, String> param) {
                        final TableCell<Car, String> cell = new TableCell<Car, String>() {

                            final Button deleteButton = new Button("Изтрий");

                            final Button editButton = new Button("Редактирай");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    deleteButton.setStyle("-fx-background-color: #8b0000; -fx-text-fill: white; ");
                                    deleteButton.setOnAction(event -> {
                                        Car car = getTableView().getItems().get(getIndex());
                                        CarService.deleteCar(car.getIdCar());
                                        carObservableList = FXCollections.observableList(CarService.getAllCar());
                                        table.setItems(carObservableList);
                                    });

                                    editButton.setOnAction(event -> {
                                        Car car = getTableView().getItems().get(getIndex());

                                        try {
                                            Stage stage = new Stage();
                                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("car-modal.fxml"));


                                            Scene scene = new Scene(fxmlLoader.load());
                                            //dialogController.setLoggedUser(car.getIdCar());
                                            CarModalController dialogController = fxmlLoader.getController();
                                            dialogController.setCar(car);
                                            stage.setTitle("Редакция на " + car.getBrandCar() + " " + car.getModelCar());
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
                                            //TODO: Екран за грешка
                                        }


                                    });
                                    HBox pane = new HBox(deleteButton, editButton);
                                    pane.setSpacing(5);
                                    setGraphic(pane);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        actions.setCellFactory(cellFactory);
        table.setItems(carObservableList);
    }

    @FXML
    void addCar(ActionEvent event) {


        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("car-modal.fxml"));


            Scene scene = new Scene(fxmlLoader.load());
            //dialogController.setLoggedUser(car.getIdCar());

            stage.setTitle("Добавяне на кола");
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent paramT) {
                    carObservableList = FXCollections.observableList(CarService.getAllCar());
                    table.setItems(carObservableList);
                }
            });
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
            //TODO: Екран за грешка
        }
    }
}
