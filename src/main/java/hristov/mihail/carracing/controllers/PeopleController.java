package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.models.User;
import hristov.mihail.carracing.services.PersonService;
import hristov.mihail.carracing.services.UserService;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class PeopleController {
    // Нашата таблица с данни, която се визуализира.
    // Получаваме данните за нея от service, който взима всички записи от базата данни като ArrayList.
    // Този лист се преобразува в ObservableList - изгъзен лист, който само той е свъместим с TableView.
    ObservableList<Person> peopleObservableList = FXCollections.observableList(PersonService.getAllPerson());
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button addPerson;
    @FXML
    private TableColumn<Person, String> actions;
    @FXML
    private TableColumn<Person, String> firstName;
    @FXML
    private TableColumn<Person, String> lastName;
    @FXML
    private TableColumn<Person, String> nationality;
    @FXML
    private TableColumn<Person, String> points;
    @FXML
    private TableView<Person> table;

    // Метод, който се изпълнява при зареждането на нашия прозорец.
    @FXML
    void initialize() {
        assert actions != null : "fx:id=\"actions\" was not injected: check your FXML file 'cars.fxml'.";
        assert firstName != null : "fx:id=\"firstName\" was not injected: check your FXML file 'cars.fxml'.";
        assert lastName != null : "fx:id=\"lastName\" was not injected: check your FXML file 'cars.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'cars.fxml'.";
        firstName.setCellValueFactory(new PropertyValueFactory<Person, String>("firstNamePerson"));
        lastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastNamePerson"));
        nationality.setCellValueFactory(new PropertyValueFactory<Person, String>("nationalityPerson"));
        points.setCellValueFactory(new PropertyValueFactory<Person, String>("pointsPerson"));
        table.setPlaceholder(new Label("Няма записи в таблицата"));
        // Проверяваме дали логнат потребител е администратор.

        firstName.maxWidthProperty().bind(table.widthProperty().divide(5));
        lastName.maxWidthProperty().bind(table.widthProperty().divide(5));
        actions.maxWidthProperty().bind(table.widthProperty().divide(10/2.99));
        nationality.maxWidthProperty().bind(table.widthProperty().divide(5));
        points.maxWidthProperty().bind(table.widthProperty().divide(10));


        firstName.minWidthProperty().bind(table.widthProperty().divide(5));
        lastName.minWidthProperty().bind(table.widthProperty().divide(5));
        actions.minWidthProperty().bind(table.widthProperty().divide(10/2.99));
        nationality.minWidthProperty().bind(table.widthProperty().divide(5));
        points.minWidthProperty().bind(table.widthProperty().divide(10));
        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory = //
                new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {

                    @Override
                    public TableCell call(final TableColumn<Person, String> param) {

                        final TableCell<Person, String> cell = new TableCell<Person, String>() {

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
                                        Person person = getTableView().getItems().get(getIndex());
                                        // Изтриваме колата
                                        //TODO: Колата принадлежи на
                                        User user = UserService.getUser(person);
                                        UserService.deleteUser(user.getIdUser());
                                        PersonService.deletePerson(person.getIdPerson());
                                        // Обновяваме таблицата
                                        peopleObservableList = FXCollections.observableList(PersonService.getAllPerson());
                                        table.setItems(peopleObservableList);
                                    });
                                    // Задаваме какво да се прави при натискантето на бутона за редакиране .
                                    editButton.setOnAction(event -> {
                                        // Взимаме нашата кола от таблицата с обекти по индекса ѝ. Например, ако колата е BMW M5, ще вземе нейния индекс и по този индекс от нашата заредена вече от базата данни таблица ще вземе обекта и ще го запамети.
                                        Person person = getTableView().getItems().get(getIndex());

                                        try {
                                            // Създаваме нов stage (нов прозорец)
                                            Stage stage = new Stage();
                                            // Зареждане на прозореца от fxml-a.
                                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile.fxml"));
                                            // Зареждане на сцената.
                                            Scene scene = new Scene(fxmlLoader.load());
                                            // Подаваме на контролера на модала нашата кола за да може да я отвори
                                            ProfileController dialogController = fxmlLoader.getController();
                                            dialogController.setUser(UserService.getUser(person));
                                            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
                                            // Промяна на прозореца да изглежда като такъв за редакция.
                                            stage.setTitle("Редакция на " + person.getFirstNamePerson() + " " + person.getLastNamePerson());
                                            stage.setScene(scene);
                                            stage.show();
                                            // Какво да се случва когато затворим нашия прозорец, който е отворил модал за редактиране на кола.
                                            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                                                @Override
                                                public void handle(WindowEvent paramT) {
                                                    // Обновяване на елементите в нашата таблица.
                                                    peopleObservableList = FXCollections.observableList(PersonService.getAllPerson());
                                                    table.setItems(peopleObservableList);
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
                    }
                };
        // Задаваме как нашата колона ще генерира своите клетки
        actions.setCellFactory(cellFactory);
        // Задаваме елементите на таблицата
        table.setItems(peopleObservableList);
    }

    // Метод, който ще се изпълнява при натискане на бутона добавяне на нов обект.
    @FXML
    void addPerson(ActionEvent event) {


        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile.fxml"));


            Scene scene = new Scene(fxmlLoader.load());
            //dialogController.setLoggedUser(car.getIdPerson());
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
            stage.setTitle("Добавяне на състезател");
            stage.setScene(scene);
            stage.show();
            // Когато се затвори нашия отворен прозорец да се обнови таблица.

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent paramT) {
                    peopleObservableList = FXCollections.observableList(PersonService.getAllPerson());
                    table.setItems(peopleObservableList);
                }
            });
        } catch (Exception e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);

        }
    }
}
