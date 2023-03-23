package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.models.Person;
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

        // Проверка дали размерът на списъка с коли и състезатели за състезанието е по-голям от 0
        if (raceHasCarAndDriversObservableList.size() > 0) {

            // Проверка дали общият брой точки е по-малък или равен на точките за състезанието
            if (totalPoints <= race.getPointsRace()) {
                // Актуализиране на списъка с коли и състезатели за състезанието
                RaceHasCarAndDriverService.updateRaceHasCarAndDriverList(raceHasCarAndDriversObservableList);
                // Обхождане на всеки елемент от списъка с коли и състезатели за състезанието
                for (RaceHasCarAndDriver raceHasCarAndDriver : raceHasCarAndDriversObservableList) {
                    if(raceHasCarAndDriver.getPoints() >= 0) {
                        // Вземане на човека, асоцииран с ID-то на състезателя
                        Person person = PersonService.getPerson(raceHasCarAndDriver.getIdDriver());
                        // Вземане на оригиналните точки от оригиналния списък
                        int originalPoints = originalRaceHasCarAndDriversObservableList.get(raceHasCarAndDriversObservableList.indexOf(raceHasCarAndDriver)).getPoints();
                        // Актуализация на точките на човека като добавяме новите му точки и изваждаме точките му преди редакция
                        person.setPointsPerson(person.getPointsPerson() + raceHasCarAndDriver.getPoints() - originalPoints);
                        // Актуализация на човека в базата данни
                        PersonService.updatePerson(person);
                    } else {
                        WarningController.openMessageModal("Въведете валидни точки!", "Невалидни данни", MessageType.WARNING);
                    }
                }
                // Показване на успешно съобщение при въвеждането на всички точки за всички участници в състезанието
                WarningController.openMessageModal("Вие успешно въведохте точките на всички състезатели!", "Успешно въведени точки", MessageType.SUCCESS);
                // Актуализация на оригиналния списък с всички записи за коли и състезатели за това конкретно ID на състезание
                originalRaceHasCarAndDriversObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver(idRace));
            } else {
                // Показване на предупреждение, че общата стойност от точките не трябва да надвишава определена стойност, определена от максималния брой позволени точки за това конкретно състезание.
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

                            // Add listener in constructor
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
                                    // Get the value for this cell from the RaceHasCarAndDriver object
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
            racesCombobox.getItems().addAll(RaceService.getAllFreeRacesNames());
        }
    }

    @FXML
    private void onSelectComboBox(ActionEvent event) {
        idRace = RaceService.getRace(racesCombobox.getValue()).getIdRace();
        //Не бях добавил idRace където трябва.
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
