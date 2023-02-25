package hristov.mihail.carracing.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.models.Race;
import hristov.mihail.carracing.models.RaceHasCarAndDriver;
import hristov.mihail.carracing.services.CarService;
import hristov.mihail.carracing.services.PersonService;
import hristov.mihail.carracing.services.RaceHasCarAndDriverService;
import hristov.mihail.carracing.services.RaceService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class RaceModalPointsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    ObservableList<RaceHasCarAndDriver> raceHasCarAndDriversObservableList;

//    ObservableList<Person> userObservableList = new ObservableListBase<Person>() {
//        @Override
//        public Person get(int index) {
//            return null;
//        }
//
//        @Override
//        public int size() {
//            return 0;
//        }
//    };

    @FXML
    private Button addPoints;

    private RaceHasCarAndDriver raceHasCarAndDriver;

    @FXML
    private TableColumn<RaceHasCarAndDriver, String> car;

    @FXML
    private TableColumn<RaceHasCarAndDriver, String> firstName;

    @FXML
    private TableColumn<RaceHasCarAndDriver, String> lastName;

    @FXML
    private TableColumn<RaceHasCarAndDriver, Integer> points;

    @FXML
    private ComboBox<String> racesCombobox;

    @FXML
    private TableView<RaceHasCarAndDriver> table;

    @FXML
    void initialize() {
        assert addPoints != null : "fx:id=\"addPoints\" was not injected: check your FXML file 'race-points-modal.fxml'.";
        assert car != null : "fx:id=\"car\" was not injected: check your FXML file 'race-points-modal.fxml'.";
        assert firstName != null : "fx:id=\"firstName\" was not injected: check your FXML file 'race-points-modal.fxml'.";
        assert lastName != null : "fx:id=\"lastName\" was not injected: check your FXML file 'race-points-modal.fxml'.";
        assert points != null : "fx:id=\"points\" was not injected: check your FXML file 'race-points-modal.fxml'.";
        assert racesCombobox != null : "fx:id=\"racesCombobox\" was not injected: check your FXML file 'race-points-modal.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'race-points-modal.fxml'.";
        car.setCellValueFactory(new PropertyValueFactory<RaceHasCarAndDriver, String>("idCar"));


        firstName.maxWidthProperty().bind(table.widthProperty().divide(4));
        firstName.minWidthProperty().bind(table.widthProperty().divide(4));
        lastName.maxWidthProperty().bind(table.widthProperty().divide(4));
        lastName.minWidthProperty().bind(table.widthProperty().divide(4));
        car.maxWidthProperty().bind(table.widthProperty().divide(4));
        car.minWidthProperty().bind(table.widthProperty().divide(4));
        points.maxWidthProperty().bind(table.widthProperty().divide(4));
        points.minWidthProperty().bind(table.widthProperty().divide(4));


        Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>> cellFactoryCar = //
                new Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>>() {

                    @Override
                    public TableCell call(final TableColumn<RaceHasCarAndDriver, String> param) {

                        final TableCell<RaceHasCarAndDriver, Integer> cell = new TableCell<RaceHasCarAndDriver, Integer>() {

                            @Override
                            public void updateItem(Integer item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    RaceHasCarAndDriver raceHasCarAndDriver = getTableView().getItems().get(getIndex());
                                    setText(CarService.getCar(raceHasCarAndDriver.getIdCar()).getNameCar());
                                }
                            }
                        };
                        // Задаване на подравняване на колоната.
                        cell.setAlignment(Pos.CENTER);
                        // Връшаме нашата клетка. Кодът по-горе указва как ще се генерира дадена клетка и какви методи ще имат бутоните ѝ.
                        return cell;
                    }
                };


        Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>> cellFactoryFirstName = //
                new Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>>() {

                    @Override
                    public TableCell call(final TableColumn<RaceHasCarAndDriver, String> param) {

                        final TableCell<RaceHasCarAndDriver, String> cell = new TableCell<RaceHasCarAndDriver, String>() {

                            // Override-ваме някакъв метод бе не го мисли
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    RaceHasCarAndDriver raceHasCarAndDriver1 = getTableView().getItems().get(getIndex());
                                    setText(PersonService.getPerson(raceHasCarAndDriver1.getIdDriver()).getFirstNamePerson());
                                }
                            }
                        };
                        // Задаване на подравняване на колоната.
                        cell.setAlignment(Pos.CENTER);
                        // Връшаме нашата клетка. Кодът по-горе указва как ще се генерира дадена клетка и какви методи ще имат бутоните ѝ.
                        return cell;
                    }
                };

        Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>> cellFactoryLastName = //
                new Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>>() {

                    @Override
                    public TableCell call(final TableColumn<RaceHasCarAndDriver, String> param) {

                        final TableCell<RaceHasCarAndDriver, String> cell = new TableCell<RaceHasCarAndDriver, String>() {

                            // Override-ваме някакъв метод бе не го мисли
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    RaceHasCarAndDriver raceHasCarAndDriver1 = getTableView().getItems().get(getIndex());
                                    setText(PersonService.getPerson(raceHasCarAndDriver1.getIdDriver()).getLastNamePerson());
                                }
                            }
                        };
                        // Задаване на подравняване на колоната.
                        cell.setAlignment(Pos.CENTER);
                        // Връшаме нашата клетка. Кодът по-горе указва как ще се генерира дадена клетка и какви методи ще имат бутоните ѝ.
                        return cell;
                    }
                };

        Callback<TableColumn<RaceHasCarAndDriver, Integer>, TableCell<RaceHasCarAndDriver, Integer>> cellFactoryPoints = new Callback<TableColumn<RaceHasCarAndDriver, Integer>, TableCell<RaceHasCarAndDriver, Integer>>() {

            @Override
            public TableCell<RaceHasCarAndDriver, Integer> call(TableColumn<RaceHasCarAndDriver, Integer> param) {
                TableCell<RaceHasCarAndDriver, Integer> cell = new TableCell<RaceHasCarAndDriver, Integer>() {
                    private TextField textField = new TextField();

                    @Override
                    public void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            RaceHasCarAndDriver raceHasCarAndDriver = getTableView().getItems().get(getIndex());
                            // Get the value for this cell from the RaceHasCarAndDriver object
                            Integer points = raceHasCarAndDriver.getPoints();
                            if (points != null) {
                                textField.setText(points.toString());
                            } else {
                                textField.setText("");
                            }
                            textField.textProperty().addListener((obs, oldText, newText) -> {
                                if (newText.isEmpty()) {
                                    raceHasCarAndDriver.setPoints(0);
                                } else {
                                    raceHasCarAndDriver.setPoints(Integer.parseInt(newText));
                                }
                            });
                            setGraphic(textField);
                        }
                        setText(null);
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        };





        car.setCellFactory(cellFactoryCar);
        firstName.setCellFactory(cellFactoryFirstName);
        lastName.setCellFactory(cellFactoryLastName);
        points.setCellFactory(cellFactoryPoints);


        racesCombobox.getItems().addAll(RaceService.getAllFreeRacesNames());

    }
    @FXML
    private void onSelectComboBox(ActionEvent event) {
        int idRace = RaceService.getRace(racesCombobox.getValue()).getIdRace();
        raceHasCarAndDriversObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver());
        for (int i = 0; i<table.getItems().size(); i++) {
            table.getItems().clear();
        }
        table.setItems(raceHasCarAndDriversObservableList);
    }

    // A helper method to retrieve the data to display in the TableView based on the selected option
//    private List<String> getDataForSelectedOption(String selectedOption) {
//        // Perform any necessary logic to retrieve the data to display in the TableView
//        // For this example, we'll just return a hardcoded list of data
//        if (selectedOption.equals(raceHasCarAndDriver.getIdRace(RaceService.getRace(racesCombobox.getValue()).getIdRace()))) {
//            return Arrays.asList("asdasdasdasdasdasda");
//        } else {
//            return Collections.emptyList();
//        }
//    }

}
