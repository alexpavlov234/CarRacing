package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Race;
import hristov.mihail.carracing.models.RaceHasCarAndDriver;
import hristov.mihail.carracing.services.CarService;
import hristov.mihail.carracing.services.PersonService;
import hristov.mihail.carracing.services.RaceHasCarAndDriverService;
import hristov.mihail.carracing.services.RaceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RacesController {

    ObservableList<Race> raceObservableList = FXCollections.observableList(RaceService.getAllRace());
    ObservableList<RaceHasCarAndDriver> raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver());

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
    private Button addParticipation;
    @FXML
    private Button addPoints;
    @FXML
    private Button addRace;

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
                                        RaceHasCarAndDriver raceHasCarAndDriver = getTableView().getItems().get(getIndex());
                                        RaceHasCarAndDriverService.deleteRaceHasCarAndDriver(raceHasCarAndDriver.getId());
                                        // Обновяваме таблицата
                                        raceObservableList = FXCollections.observableList(RaceService.getAllRace());
                                        table.setItems(raceObservableList);
                                        raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver());
                                        table1.setItems(raceHasCarAndDriverObservableList);
                                    });

                                    // Задаваме какво да се прави при натискантето на бутона за редакиране.
                                    editButton.setOnAction(event -> {
                                        // Взимаме нашата кола от таблицата с обекти по индекса ѝ. Например, ако колата е BMW M5, ще вземе нейния индекс и по този индекс от нашата заредена вече от базата данни таблица ще вземе обекта и ще го запамети.
                                        RaceHasCarAndDriver raceHasCarAndDriver = getTableView().getItems().get(getIndex());
                                        try {
                                            // Създаваме нов stage (нов прозорец)
                                            Stage stage = new Stage();
                                            // Зареждане на прозореца от fxml-a.
                                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("race-has-car-and-driver-modal.fxml"));
                                            // Зареждане на сцената.
                                            Scene scene = new Scene(fxmlLoader.load());
                                            // Подаваме на контролера на модала нашата кола, за да може да я отвори
                                            RaceHasCarAndDriverModalController dialogController = fxmlLoader.getController();
                                            dialogController.setRaceHasCarAndDriver(RaceHasCarAndDriverService.getRaceHasCarAndDriver(raceHasCarAndDriver.getId()));
                                            // Промяна на прозореца да изглежда като такъв за редакция.
                                            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
                                            stage.setTitle("Редакция на участие в състезание");
                                            stage.setScene(scene);
                                            stage.setResizable(false);
                                            stage.initModality(Modality.APPLICATION_MODAL);

                                            // Какво да се случва когато затворим нашия прозорец, който е отворил модал за редактиране на кола.
                                            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                                @Override
                                                public void handle(WindowEvent paramT) {
                                                    // Обновяване на елементите в нашата таблица.
                                                    raceObservableList = FXCollections.observableList(RaceService.getAllRace());
                                                    table.setItems(raceObservableList);
                                                    raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver());
                                                    table1.setItems(raceHasCarAndDriverObservableList);
                                                }
                                            });
                                            stage.showAndWait();
                                        } catch (Exception e) {
                                            // Ако нещо гръмне да се покаже грешка.
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

                            final Button deleteButton = new Button("Изтрий");

                            final Button editButton = new Button("Редактирай");

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
                                        Race race = getTableView().getItems().get(getIndex());
                                        ArrayList<RaceHasCarAndDriver> raceHasCarAndDriverArrayList = RaceHasCarAndDriverService.getAllRaceHasCarAndDriver(race.getIdRace());
                                        for (RaceHasCarAndDriver raceHasCarAndDriver : raceHasCarAndDriverArrayList) {
                                            RaceHasCarAndDriverService.deleteRaceHasCarAndDriver(raceHasCarAndDriver.getId());
                                        }
                                        RaceService.deleteRace(race.getIdRace());
                                        // Обновяваме таблицата
                                        raceObservableList = FXCollections.observableList(RaceService.getAllRace());
                                        table.setItems(raceObservableList);
                                        raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver());
                                        table1.setItems(raceHasCarAndDriverObservableList);
                                    });

                                    // Задаваме какво да се прави при натискането на бутона за редакиране.
                                    editButton.setOnAction(event -> {
                                        Race race = getTableView().getItems().get(getIndex());
                                        try {
                                            // Създаваме нов stage (нов прозорец)
                                            Stage stage = new Stage();
                                            // Зареждане на прозореца от fxml-a.
                                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("race-modal.fxml"));
                                            // Зареждане на сцената.
                                            Scene scene = new Scene(fxmlLoader.load());
                                            // Подаваме на контролера на модала нашата кола за да може да я отвори
                                            RaceModalController dialogController = fxmlLoader.getController();
                                            dialogController.setRace(race);
                                            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
                                            // Промяна на прозореца да изглежда като такъв за редакция.
                                            stage.setTitle("Редакция на състезание");
                                            stage.setScene(scene);
                                            stage.setResizable(false);
                                            stage.initModality(Modality.APPLICATION_MODAL);

                                            // Какво да се случва когато затворим нашия прозорец, който е отворил модал за редактиране.
                                            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                                @Override
                                                public void handle(WindowEvent paramT) {
                                                    // Обновяване на елементите в нашата таблица.
                                                    raceObservableList = FXCollections.observableList(RaceService.getAllRace());
                                                    table.setItems(raceObservableList);
                                                    raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver());
                                                    table1.setItems(raceHasCarAndDriverObservableList);
                                                }
                                            });
                                            stage.showAndWait();
                                        } catch (Exception e) {
                                            // Ако нещо гръмне да се покаже грешка.
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
        actions.setCellFactory(cellFactory1);
        track.setCellFactory(cellFactoryTrack);
        participants.setCellFactory(cellFactoryParticipants);
        // Задаваме елементите на таблицата
        table.setItems(raceObservableList);
    }

    @FXML
    void addParticipation(ActionEvent event) {
        try {
            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("race-has-car-and-driver-modal.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            //dialogController.setLoggedUser(car.getIdCar());
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
            stage.setTitle("Добавяне на участие");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            // Когато се затвори нашия отворен прозорец да се обнови таблица.
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent paramT) {
                    // Обновяване на елементите в таблицата.
                    raceObservableList = FXCollections.observableList(RaceService.getAllRace());
                    table.setItems(raceObservableList);
                    raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver());
                    table1.setItems(raceHasCarAndDriverObservableList);
                }
            });
            stage.showAndWait();
        } catch (Exception e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
        }
    }

    @FXML
    void addRacePoints(ActionEvent event) {
        try {
            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("race-points-modal.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
            stage.setTitle("Добавяне на точки от състезания");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            // Когато се затвори нашия отворен прозорец да се обнови таблица.
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent paramT) {
                    // Обновяване на елементите в нашата таблица.
                    raceObservableList = FXCollections.observableList(RaceService.getAllRace());
                    table.setItems(raceObservableList);
                    raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver());
                    table1.setItems(raceHasCarAndDriverObservableList);
                }
            });
            stage.showAndWait();
        } catch (Exception e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
        }
    }

    @FXML
    void addRace(ActionEvent event) {
        try {
            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("race-modal.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
            stage.setTitle("Добавяне на състезание");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            // Когато се затвори нашия отворен прозорец да се обнови таблица.
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent paramT) {
                    // Обновяване на елементите в нашата таблица.
                    raceObservableList = FXCollections.observableList(RaceService.getAllRace());
                    table.setItems(raceObservableList);
                    raceHasCarAndDriverObservableList = FXCollections.observableList(RaceHasCarAndDriverService.getAllRaceHasCarAndDriver());
                    table1.setItems(raceHasCarAndDriverObservableList);
                }
            });
            stage.showAndWait();
        } catch (Exception e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
        }
    }
}
