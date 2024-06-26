package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.models.Race;
import hristov.mihail.carracing.models.RaceHasCarAndDriver;
import hristov.mihail.carracing.services.CarService;
import hristov.mihail.carracing.services.PersonService;
import hristov.mihail.carracing.services.RaceHasCarAndDriverService;
import hristov.mihail.carracing.services.RaceService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class RaceModalPointsController {

    ObservableList<RaceHasCarAndDriver> raceHasCarAndDriversObservableList;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
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
    private ObservableList<RaceHasCarAndDriver> originalRaceHasCarAndDriversObservableList;
    private int idRace;

    @FXML
    void applyPoints(ActionEvent event) {
        Race race = RaceService.getRace(racesCombobox.getValue());
        int totalPoints = 0;
        for (RaceHasCarAndDriver raceHasCarAndDriver : raceHasCarAndDriversObservableList) {
            totalPoints += raceHasCarAndDriver.getPoints();
        }
        if (raceHasCarAndDriversObservableList.size() > 0) {
            for (RaceHasCarAndDriver raceHasCarAndDriver : raceHasCarAndDriversObservableList) {
                if (raceHasCarAndDriver.getPoints() < 0) {
                    WarningController.openMessageModal("Въведени са отрицателни точки!", "Невалидни данни", MessageType.WARNING);
                    return;
                }
            }
            if (totalPoints <= race.getPointsRace()) {
                RaceHasCarAndDriverService.updateRaceHasCarAndDriverList(raceHasCarAndDriversObservableList);
                WarningController.openMessageModal("Вие успешно въведохте точките на всички състезатели!", "Успешно въведени точки", MessageType.SUCCESS);
                originalRaceHasCarAndDriversObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver(idRace));
            } else {
                WarningController.openMessageModal("Сумата от точките на всички участници не трябва да е по-голяма от " + race.getPointsRace() + "!", "Невалиден брой точки", MessageType.WARNING);
            }
        }
        this.initialize();
    }

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
        table.setPlaceholder(new Label("Няма записи в таблицата"));

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

        Callback<TableColumn<RaceHasCarAndDriver, Integer>, TableCell<RaceHasCarAndDriver, Integer>> cellFactoryPoints = //
                new Callback<TableColumn<RaceHasCarAndDriver, Integer>, TableCell<RaceHasCarAndDriver, Integer>>() {

                    @Override
                    public TableCell<RaceHasCarAndDriver, Integer> call(TableColumn<RaceHasCarAndDriver, Integer> param) {
                        TableCell<RaceHasCarAndDriver, Integer> cell = new TableCell<RaceHasCarAndDriver, Integer>() {
                            private final TextField textField = new TextField();

                            {
                                textField.textProperty().addListener((obs, oldText, newText) -> {
                                    if (newText.isEmpty()) {
                                        getTableView().getItems().get(getIndex()).setPoints(0);
                                    } else {
                                        try {
                                            getTableView().getItems().get(getIndex()).setPoints(Integer.parseInt(newText));
                                        } catch (Exception e) {
                                            getTableView().getItems().get(getIndex()).setPoints(0);
                                        }
                                    }
                                });
                            }

                            @Override
                            public void updateItem(Integer item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    RaceHasCarAndDriver raceHasCarAndDriver = getTableView().getItems().get(getIndex());
                                    Integer points = raceHasCarAndDriver.getPoints();
                                    if (points != null) {
                                        textField.setText(points.toString());
                                    } else {
                                        textField.setText("");
                                    }
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
        if (racesCombobox.getItems().size() == 0) {
            racesCombobox.getItems().addAll(RaceService.getAllRacesNames());
        }
    }

    @FXML
    private void onSelectComboBox(ActionEvent event) {
        idRace = RaceService.getRace(racesCombobox.getValue()).getIdRace();
        originalRaceHasCarAndDriversObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver(idRace));
        raceHasCarAndDriversObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver(idRace));
        for (int i = 0; i < table.getItems().size(); i++) {
            table.getItems().clear();
        }
        table.setItems(raceHasCarAndDriversObservableList);
        raceHasCarAndDriversObservableList.addListener((ListChangeListener<RaceHasCarAndDriver>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved() || change.wasUpdated()) {
                    table.refresh();
                }
            }
        });
    }
}
