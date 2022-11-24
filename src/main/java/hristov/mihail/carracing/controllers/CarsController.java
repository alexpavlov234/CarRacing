package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.services.CarService;
import hristov.mihail.carracing.services.LoginService;
import hristov.mihail.carracing.services.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CarsController {
    // Нашата таблица с данни, която се визуализира.
    // Получаваме данните за нея от service, който взима всички записи от базата данни като ArrayList.
    // Този лист се преобразува в ObservableList - изгъзен лист, който само той е свъместим с TableView.
    ObservableList<Car> carObservableList = FXCollections.observableList(CarService.getAllCar());
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;
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
    // Метод, който се изпълнява при зареждането на нашия прозорец.
    @FXML
    void initialize() {
        assert actions != null : "fx:id=\"actions\" was not injected: check your FXML file 'cars.fxml'.";
        assert brand != null : "fx:id=\"brand\" was not injected: check your FXML file 'cars.fxml'.";
        assert model != null : "fx:id=\"model\" was not injected: check your FXML file 'cars.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'cars.fxml'.";
        brand.setCellValueFactory(new PropertyValueFactory<Car, String>("brandCar"));
        model.setCellValueFactory(new PropertyValueFactory<Car, String>("modelCar"));
        actions.setCellValueFactory(new PropertyValueFactory<Car, String>("fuelCar"));
        // Проверяваме дали логнат потребител е администратор.
        if (LoginService.isLoggedUserAdmin()) {
            brand.maxWidthProperty().bind(table.widthProperty().divide(3));
            model.maxWidthProperty().bind(table.widthProperty().divide(3));
            actions.maxWidthProperty().bind(table.widthProperty().divide(3));
            brand.minWidthProperty().bind(table.widthProperty().divide(3));
            model.minWidthProperty().bind(table.widthProperty().divide(3));
            actions.minWidthProperty().bind(table.widthProperty().divide(3));
        } else {
            brand.maxWidthProperty().bind(table.widthProperty().divide(3));
            model.maxWidthProperty().bind(table.widthProperty().divide(3));
            actions.maxWidthProperty().bind(table.widthProperty().divide(3));
            brand.minWidthProperty().bind(table.widthProperty().divide(3));
            model.minWidthProperty().bind(table.widthProperty().divide(3));
            actions.minWidthProperty().bind(table.widthProperty().divide(3));
            addCar.setVisible(false);
            mainPane.setBottomAnchor(table, 14d);
        }
        Callback<TableColumn<Car, String>, TableCell<Car, String>> cellFactory = //
                new Callback<TableColumn<Car, String>, TableCell<Car, String>>() {

                    @Override
                    public TableCell call(final TableColumn<Car, String> param) {
                        if (LoginService.isLoggedUserAdmin()) {
                            final TableCell<Car, String> cell = new TableCell<Car, String>() {

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
                                            Car car = getTableView().getItems().get(getIndex());
                                            // Изтриваме колата
                                            //TODO: Колата принадлежи на

                                            CarService.deleteCar(car.getIdCar());
                                            // Обновяваме таблицата
                                            carObservableList = FXCollections.observableList(CarService.getAllCar());
                                            table.setItems(carObservableList);
                                        });
                                        // Задаваме какво да се прави при натискантето на бутона за редакиране .
                                        editButton.setOnAction(event -> {
                                            // Взимаме нашата кола от таблицата с обекти по индекса ѝ. Например, ако колата е BMW M5, ще вземе нейния индекс и по този индекс от нашата заредена вече от базата данни таблица ще вземе обекта и ще го запамети.
                                            Car car = getTableView().getItems().get(getIndex());

                                            try {
                                                // Създаваме нов stage (нов прозорец)
                                                Stage stage = new Stage();
                                                // Зареждане на прозореца от fxml-a.
                                                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("car-modal.fxml"));
                                                // Зареждане на сцената.
                                                Scene scene = new Scene(fxmlLoader.load());
                                                // Подаваме на контролера на модала нашата кола за да може да я отвори
                                                CarModalController dialogController = fxmlLoader.getController();
                                                dialogController.setCar(car);
                                                // Промяна на прозореца да изглежда като такъв за редакция.
                                                stage.setTitle("Редакция на " + car.getBrandCar() + " " + car.getModelCar());
                                                stage.setScene(scene);
                                                stage.show();
                                                // Какво да се случва когато затворим нашия прозорец, който е отворил модал за редактиране на кола.
                                                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                                                    @Override
                                                    public void handle(WindowEvent paramT) {
                                                        // Обновяване на елементите в нашата таблица.
                                                        carObservableList = FXCollections.observableList(CarService.getAllCar());
                                                        table.setItems(carObservableList);
                                                    }
                                                });
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
                        } else {
                            // Ако потребителят не е администратор, бутонът ще бъде друг.
                            final TableCell<Car, String> cell = new TableCell<Car, String>() {

                                final Button chooseButton = new Button("Избери за текуща кола");


                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        // Задаваме стил на бутона.
                                        chooseButton.setStyle("-fx-background-color: #e5aa00; -fx-text-fill: white; ");
                                        // Задаваме какво да се прави при натискане на бутона.
                                        chooseButton.setOnAction(event -> {
                                            // Взимаме нашата кола от таблицата с обекти по индекса ѝ. Например, ако колата е BMW M5, ще вземе нейния индекс и по този индекс от нашата заредена вече от базата данни таблица ще вземе обекта и ще го запамети.
                                            Car car = getTableView().getItems().get(getIndex());

                                            try {
                                                // Задаваме избраната кола за дадения състезател.
                                                PersonService.setCarPerson(car, PersonService.getPerson(LoginService.getLoggedUser().getUserHasPerson()));
                                                WarningController.openMessageModal("Вие успешно избрахте своята кола за състезания!", "Успшено избрана кола", MessageType.SUCCESS);
                                            } catch (Exception e) {
                                                WarningController.openMessageModal("Неуспешно избрана кола за състезания!", "Неуспшено избрана кола", MessageType.WARNING);

                                            }
                                            // Обновяваме нашата таблица.
                                            carObservableList = FXCollections.observableList(CarService.getAllCar());
                                            table.setItems(carObservableList);
                                        });

                                        // Настройване на бутоните да бъдат в центъра на колоната и да са един до друг

                                        HBox pane = new HBox(chooseButton);
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
                    }
                };
        // Задаваме как нашата колона ще генерира своите клетки
        actions.setCellFactory(cellFactory);
        // Задаваме елементите на таблицата
        table.setItems(carObservableList);
    }

    // Метод, който ще се изпълнява при натискане на бутона добавяне на нов обект.
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
            // Когато се затвори нашия отворен прозорец да се обнови таблица.
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent paramT) {
                    carObservableList = FXCollections.observableList(CarService.getAllCar());
                    table.setItems(carObservableList);
                }
            });
        } catch (Exception e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);

        }
    }
}
