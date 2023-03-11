package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Race;
import hristov.mihail.carracing.models.RaceHasCarAndDriver;
import hristov.mihail.carracing.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class RacesUserController {

    ObservableList<Race> raceObservableList = FXCollections.observableList(RaceService.getAllRace());
    ObservableList<RaceHasCarAndDriver> raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriverFromPerson(LoginService.getLoggedUser().getIdUser()));

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Race, String> actions;

    @FXML
    private TableColumn<RaceHasCarAndDriver, String> actions1;

    @FXML
    private TableColumn<Race, String> date;

    @FXML
    private TableColumn<RaceHasCarAndDriver, String> car;

    @FXML
    private TableColumn<Race, String> participants;

    @FXML
    private TableColumn<RaceHasCarAndDriver, String> driver;

    @FXML
    private TableView<Race> table;

    @FXML
    private TableView<RaceHasCarAndDriver> table1;

    @FXML
    private TableColumn<Race, String> track;

    @FXML
    private TableColumn<RaceHasCarAndDriver, String> race;


    @FXML
    void initialize() {
        assert actions != null : "fx:id=\"actions\" was not injected: check your FXML file 'races.fxml'.";
        assert actions1 != null : "fx:id=\"actions1\" was not injected: check your FXML file 'races.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'races.fxml'.";
        assert car != null : "fx:id=\"date1\" was not injected: check your FXML file 'races.fxml'.";
        assert participants != null : "fx:id=\"participants\" was not injected: check your FXML file 'races.fxml'.";
        assert driver != null : "fx:id=\"participants1\" was not injected: check your FXML file 'races.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'races.fxml'.";
        assert table1 != null : "fx:id=\"table1\" was not injected: check your FXML file 'races.fxml'.";
        assert track != null : "fx:id=\"track\" was not injected: check your FXML file 'races.fxml'.";
        assert race != null : "fx:id=\"track1\" was not injected: check your FXML file 'races.fxml'.";
        track.setCellValueFactory(new PropertyValueFactory<Race, String>("trackRace"));
        date.setCellValueFactory(new PropertyValueFactory<Race, String>("dateRace"));
        participants.setCellValueFactory(new PropertyValueFactory<Race, String>("participantsRace"));
        race.setCellValueFactory(new PropertyValueFactory<RaceHasCarAndDriver, String>("idRace"));
        car.setCellValueFactory(new PropertyValueFactory<RaceHasCarAndDriver, String>("idCar"));
        driver.setCellValueFactory(new PropertyValueFactory<RaceHasCarAndDriver, String>("idDriver"));

        table.setPlaceholder(new Label("Няма записи в таблицата"));
        table1.setPlaceholder(new Label("Няма записи в таблицата"));
        // Проверяваме дали логнат потребител е администратор.

        track.maxWidthProperty().bind(table.widthProperty().divide(4));
        date.maxWidthProperty().bind(table.widthProperty().divide(4));
        actions.maxWidthProperty().bind(table.widthProperty().divide(4));
        participants.maxWidthProperty().bind(table.widthProperty().divide(4));
        race.maxWidthProperty().bind(table1.widthProperty().divide(4));
        car.maxWidthProperty().bind(table1.widthProperty().divide(4));
        driver.maxWidthProperty().bind(table1.widthProperty().divide(4));
        actions1.maxWidthProperty().bind(table1.widthProperty().divide(4));
        track.minWidthProperty().bind(table.widthProperty().divide(4));
        date.minWidthProperty().bind(table.widthProperty().divide(4));
        actions.minWidthProperty().bind(table.widthProperty().divide(4));
        participants.minWidthProperty().bind(table.widthProperty().divide(4));
        race.minWidthProperty().bind(table1.widthProperty().divide(4));
        car.minWidthProperty().bind(table1.widthProperty().divide(4));
        driver.minWidthProperty().bind(table1.widthProperty().divide(4));
        actions1.minWidthProperty().bind(table1.widthProperty().divide(4));
        Callback<TableColumn<Race, String>, TableCell<Race, String>> cellFactoryParticipants = //
                new Callback<TableColumn<Race, String>, TableCell<Race, String>>() {

                    @Override
                    public TableCell call(final TableColumn<Race, String> param) {

                        final TableCell<Race, Integer> cell = new TableCell<Race, Integer>() {


                            // Override-ваме някакъв метод бе не го мисли
                            @Override
                            public void updateItem(Integer item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Race race = getTableView().getItems().get(getIndex());
                                    RaceHasCarAndDriverService.getNumberParticipants(race);
                                    setText(RaceHasCarAndDriverService.getNumberParticipants(race) + " / " + race.getParticipantsRace());
                                }
                            }
                        };
                        // Задаване на подравняване на колоната.
                        cell.setAlignment(Pos.CENTER);
                        // Връшаме нашата клетка. Кодът по-горе указва как ще се генерира дадена клетка и какви методи ще имат бутоните ѝ.
                        return cell;
                    }
                };
        Callback<TableColumn<Race, String>, TableCell<Race, String>> cellFactoryTrack = //
                new Callback<TableColumn<Race, String>, TableCell<Race, String>>() {

                    @Override
                    public TableCell call(final TableColumn<Race, String> param) {

                        final TableCell<Race, Integer> cell = new TableCell<Race, Integer>() {


                            // Override-ваме някакъв метод бе не го мисли
                            @Override
                            public void updateItem(Integer item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Race race = getTableView().getItems().get(getIndex());
                                    setText(race.getNameRace());
                                }
                            }
                        };
                        // Задаване на подравняване на колоната.
                        cell.setAlignment(Pos.CENTER);
                        // Връшаме нашата клетка. Кодът по-горе указва как ще се генерира дадена клетка и какви методи ще имат бутоните ѝ.
                        return cell;
                    }
                };

        Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>> cellFactoryRace = //
                new Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>>() {

                    @Override
                    public TableCell call(final TableColumn<RaceHasCarAndDriver, String> param) {

                        final TableCell<RaceHasCarAndDriver, Integer> cell = new TableCell<RaceHasCarAndDriver, Integer>() {


                            // Override-ваме някакъв метод бе не го мисли
                            @Override
                            public void updateItem(Integer item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    RaceHasCarAndDriver raceHasCarAndDriver = getTableView().getItems().get(getIndex());
                                    setText(RaceService.getRace(raceHasCarAndDriver.getIdRace()).getNameRace());
                                }
                            }
                        };
                        // Задаване на подравняване на колоната.
                        cell.setAlignment(Pos.CENTER);
                        // Връшаме нашата клетка. Кодът по-горе указва как ще се генерира дадена клетка и какви методи ще имат бутоните ѝ.
                        return cell;
                    }
                };
        Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>> cellFactoryCar = //
                new Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>>() {

                    @Override
                    public TableCell call(final TableColumn<RaceHasCarAndDriver, String> param) {

                        final TableCell<RaceHasCarAndDriver, Integer> cell = new TableCell<RaceHasCarAndDriver, Integer>() {


                            // Override-ваме някакъв метод бе не го мисли
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
        Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>> cellFactoryDriver = //
                new Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>>() {

                    @Override
                    public TableCell call(final TableColumn<RaceHasCarAndDriver, String> param) {

                        final TableCell<RaceHasCarAndDriver, Integer> cell = new TableCell<RaceHasCarAndDriver, Integer>() {


                            // Override-ваме някакъв метод бе не го мисли
                            @Override
                            public void updateItem(Integer item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    RaceHasCarAndDriver raceHasCarAndDriver = getTableView().getItems().get(getIndex());
                                    setText(PersonService.getPerson(raceHasCarAndDriver.getIdDriver()).getNamePerson());
                                }
                            }
                        };
                        // Задаване на подравняване на колоната.
                        cell.setAlignment(Pos.CENTER);
                        // Връшаме нашата клетка. Кодът по-горе указва как ще се генерира дадена клетка и какви методи ще имат бутоните ѝ.
                        return cell;
                    }
                };
        Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>> cellFactory = //
                new Callback<TableColumn<RaceHasCarAndDriver, String>, TableCell<RaceHasCarAndDriver, String>>() {

                    @Override
                    public TableCell call(final TableColumn<RaceHasCarAndDriver, String> param) {

                        final TableCell<RaceHasCarAndDriver, String> cell = new TableCell<RaceHasCarAndDriver, String>() {

                            final Button deleteButton = new Button("Изтрий");

                            final Button editButton = new Button("Редактирай");

                            // Override-ваме някакъв метод бе не го мисли
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    // На нашия бутон за изтриване задаваме стил и какво да става като се цъкне.
                                    deleteButton.setStyle("-fx-background-color: #8b0000; -fx-text-fill: white; ");
                                    deleteButton.setOnAction(event -> {
                                        // Взимаме нашата кола от таблицата с обекти по индекса ѝ. Например, ако колата е BMW M5, ще вземе нейния индекс и по този индекс от нашата заредена вече от базата данни таблица ще вземе обекта и ще го запамети.
                                        RaceHasCarAndDriver raceHasCarAndDriver = getTableView().getItems().get(getIndex());
                                        // Изтриваме колата
                                        //TODO: Колата принадлежи на
                                        RaceHasCarAndDriverService.deleteRaceHasCarAndDriver(raceHasCarAndDriver.getId());

                                        // Обновяваме таблицата
                                        raceObservableList = FXCollections.observableList(RaceService.getAllRace());
                                        table.setItems(raceObservableList);
                                        raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriverFromPerson(LoginService.getLoggedUser().getIdUser()));
                                        table1.setItems(raceHasCarAndDriverObservableList);
                                    });
                                    // Задаваме какво да се прави при натискантето на бутона за редакиране .
                                    editButton.setOnAction(event -> {
                                        // Взимаме нашата кола от таблицата с обекти по индекса ѝ. Например, ако колата е BMW M5, ще вземе нейния индекс и по този индекс от нашата заредена вече от базата данни таблица ще вземе обекта и ще го запамети.
                                        RaceHasCarAndDriver raceHasCarAndDriver = getTableView().getItems().get(getIndex());
                                        try {
                                            if (RaceHasCarAndDriverService.areTherePlacesAvailable(RaceService.getRace(raceHasCarAndDriver.getIdRace()))) {
                                                // Създаваме нов stage (нов прозорец)
                                                Stage stage = new Stage();
                                                // Зареждане на прозореца от fxml-a.
                                                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("race-has-car-and-driver-modal.fxml"));
                                                // Зареждане на сцената.
                                                Scene scene = new Scene(fxmlLoader.load());
                                                // Подаваме на контролера на модала нашата кола за да може да я отвори
                                                RaceHasCarAndDriverModalController dialogController = fxmlLoader.getController();
                                                dialogController.setRaceHasCarAndDriver(RaceHasCarAndDriverService.getRaceHasCarAndDriver(raceHasCarAndDriver.getId()));
                                                // Промяна на прозореца да изглежда като такъв за редакция.
                                                stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
                                                stage.setTitle("Редакция на участие в състезание");
                                                stage.setScene(scene);
                                                stage.setResizable(false);
                                                stage.show();
                                                // Какво да се случва когато затворим нашия прозорец, който е отворил модал за редактиране на кола.
                                                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                                                    @Override
                                                    public void handle(WindowEvent paramT) {
                                                        // Обновяване на елементите в нашата таблица.
                                                        raceObservableList = FXCollections.observableList(RaceService.getAllRace());
                                                        table.setItems(raceObservableList);
                                                        raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriverFromPerson(LoginService.getLoggedUser().getIdUser()));
                                                        table1.setItems(raceHasCarAndDriverObservableList);
                                                    }
                                                });
                                            } else {
                                                WarningController.openMessageModal("Няма повече свободни места в избраното състезание!", "Няма свободни места", MessageType.WARNING);
                                            }

                                        } catch (Exception e) {

                                            // Ако нещо гръмне по трасето да се покаже грешка.
                                            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                                        }

                                    });
                                    // Настройване на бутоните да бъдат в центъра на колоната и да са един до друг
                                    HBox pane = new HBox(deleteButton, editButton);
                                    pane.setSpacing(5);
                                    pane.setAlignment(Pos.CENTER);
                                    setGraphic(pane);
                                    setText(null);

                                }
                            }
                        };
                        // Задаване на подравняване на колоната.
                        cell.setAlignment(Pos.CENTER);
                        // Връшаме нашата клетка. Кодът по-горе указва как ще се генерира дадена клетка и какви методи ще имат бутоните ѝ.
                        return cell;
                    }
                };
        // Задаваме как нашата колона ще генерира своите клетки
        actions1.setCellFactory(cellFactory);
        race.setCellFactory(cellFactoryRace);
        car.setCellFactory(cellFactoryCar);
        driver.setCellFactory(cellFactoryDriver);
        // Задаваме елементите на таблицата
        table1.setItems(raceHasCarAndDriverObservableList);

        Callback<TableColumn<Race, String>, TableCell<Race, String>> cellFactory1 = //
                new Callback<TableColumn<Race, String>, TableCell<Race, String>>() {

                    @Override
                    public TableCell call(final TableColumn<Race, String> param) {

                        final TableCell<Race, String> cell = new TableCell<Race, String>() {


                            final Button participateButton = new Button("Участвай");

                            // Override-ваме някакъв метод бе не го мисли
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Race race = getTableView().getItems().get(getIndex());
                                    participateButton.setDisable(RaceHasCarAndDriverService.isDriverParticipatingInRace(race.getIdRace(), PersonService.getPerson(LoginService.getLoggedUser().getUserHasPerson()).getIdPerson()));
                                    participateButton.setStyle("-fx-background-color: #e5aa00; -fx-text-fill: white; ");
                                    // Задаваме какво да се прави при натискантето на бутона за редакиране .
                                    participateButton.setOnAction(event -> {
                                        // Взимаме нашата кола от таблицата с обекти по индекса ѝ. Например, ако колата е BMW M5, ще вземе нейния индекс и по този индекс от нашата заредена вече от базата данни таблица ще вземе обекта и ще го запамети.

                                        try {
                                            if (RaceHasCarAndDriverService.areTherePlacesAvailable(race)) {
                                                if(PersonService.getPerson(LoginService.getLoggedUser().getUserHasPerson()).getCarPerson() != 0) {
                                                    RaceHasCarAndDriver raceHasCarAndDriver = new RaceHasCarAndDriver(race.getIdRace(), PersonService.getPerson(LoginService.getLoggedUser().getUserHasPerson()).getCarPerson(), PersonService.getPerson(LoginService.getLoggedUser().getUserHasPerson()).getIdPerson(), 0);
                                                    RaceHasCarAndDriverService.addRaceHasCarAndDriver(raceHasCarAndDriver);
                                                    raceObservableList = FXCollections.observableList(RaceService.getAllRace());
                                                    table.setItems(raceObservableList);
                                                    raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriverFromPerson(LoginService.getLoggedUser().getIdUser()));
                                                    table1.setItems(raceHasCarAndDriverObservableList);
                                                    WarningController.openMessageModal("Вие успешно участвате в състезанието!", "Успешно участие", MessageType.SUCCESS);
                                                } else {
                                                    WarningController.openMessageModal("Моля първо си изберете кола!", "Няма избрана кола", MessageType.WARNING);
                                                }
                                            } else {
                                                WarningController.openMessageModal("Няма повече свободни места в избраното състезание!", "Няма свободни места", MessageType.WARNING);
                                            }
                                            // Какво да се случва когато затворим нашия прозорец, който е отворил модал за редактиране на кола.

                                            // Обновяване на елементите в нашата таблица.
                                            raceObservableList = FXCollections.observableList(RaceService.getAllRace());
                                            table.setItems(raceObservableList);
                                            raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriverFromPerson(LoginService.getLoggedUser().getIdUser()));
                                            table1.setItems(raceHasCarAndDriverObservableList);

                                        } catch (Exception e) {

                                            // Ако нещо гръмне по трасето да се покаже грешка.
                                            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                                        }


                                    });
                                    // Настройване на бутоните да бъдат в центъра на колоната и да са един до друг
                                    HBox pane = new HBox(participateButton);
                                    pane.setSpacing(0);
                                    pane.setAlignment(Pos.CENTER);
                                    setGraphic(pane);
                                    setText(null);
                                }
                            }
                        };
                        // Задаване на подравняване на колоната.
                        cell.setAlignment(Pos.CENTER);
                        // Връшаме нашата клетка. Кодът по-горе указва как ще се генерира дадена клетка и какви методи ще имат бутоните ѝ.
                        return cell;
                    }
                };
        // Задаваме как нашата колона ще генерира своите клетки
        actions.setCellFactory(cellFactory1);
        track.setCellFactory(cellFactoryTrack);
        participants.setCellFactory(cellFactoryParticipants);
        // Задаваме елементите на таблицата
        table.setItems(raceObservableList);
    }

}
