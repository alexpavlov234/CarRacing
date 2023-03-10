package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.User;
import hristov.mihail.carracing.services.LoginService;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersController {
    // Нашата таблица с данни, която се визуализира.
    // Получаваме данните за нея от service, който взима всички записи от базата данни като ArrayList.
    // Този лист се преобразува в ObservableList - изгъзен лист, който само той е свъместим с TableView.
    ObservableList<User> userObservableList = FXCollections.observableList(UserService.getAllUser());
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button addUser;

    @FXML
    private TableColumn<User, String> actions;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, String> typeOfUser;
    @FXML
    private TableView<User> table;
    // Метод, който се изпълнява при зареждането на нашия прозорец.
    @FXML
    void initialize() {
        assert actions != null : "fx:id=\"actions\" was not injected: check your FXML file 'cars.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'cars.fxml'.";
        assert typeOfUser != null : "fx:id=\"typeOfUser\" was not injected: check your FXML file 'cars.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'cars.fxml'.";
        email.setCellValueFactory(new PropertyValueFactory<User, String>("emailUser"));
        typeOfUser.setCellValueFactory(new PropertyValueFactory<User, String>("typeUser"));
        table.setPlaceholder(new Label("Няма записи в таблицата"));
        // Проверяваме дали логнат потребител е администратор.
        if (LoginService.isLoggedUserAdmin()) {
            email.maxWidthProperty().bind(table.widthProperty().divide(3));
            typeOfUser.maxWidthProperty().bind(table.widthProperty().divide(3));
            actions.maxWidthProperty().bind(table.widthProperty().divide(3));
            email.minWidthProperty().bind(table.widthProperty().divide(3));
            typeOfUser.minWidthProperty().bind(table.widthProperty().divide(3));
            actions.minWidthProperty().bind(table.widthProperty().divide(3));
        }
        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory = //
                new Callback<TableColumn<User, String>, TableCell<User, String>>() {

                    @Override
                    public TableCell call(final TableColumn<User, String> param) {

                            final TableCell<User, String> cell = new TableCell<User, String>() {

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
                                            User user = getTableView().getItems().get(getIndex());
                                            // Изтриваме колата
                                            // Премахваме състезателя, след това потребителя
                                            UserService.deleteUser(user.getIdUser());
                                            PersonService.deletePerson(user.getUserHasPerson());
                                            // Обновяваме таблицата
                                            userObservableList = FXCollections.observableList(UserService.getAllUser());
                                            table.setItems(userObservableList);
                                        });
                                        // Задаваме какво да се прави при натискантето на бутона за редакиране .
                                        editButton.setOnAction(event -> {
                                            // Взимаме нашата кола от таблицата с обекти по индекса ѝ. Например, ако колата е BMW M5, ще вземе нейния индекс и по този индекс от нашата заредена вече от базата данни таблица ще вземе обекта и ще го запамети.
                                            User user = getTableView().getItems().get(getIndex());

                                            try {
                                                // Създаваме нов stage (нов прозорец)
                                                Stage stage = new Stage();
                                                // Зареждане на прозореца от fxml-a.
                                                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile.fxml"));
                                                // Зареждане на сцената.
                                                Scene scene = new Scene(fxmlLoader.load());
                                                // Подаваме на контролера на модала нашата кола за да може да я отвори
                                                ProfileController dialogController = fxmlLoader.getController();
                                                dialogController.setUser(user);
                                                // Промяна на прозореца да изглежда като такъв за редакция.
                                                stage.setTitle("Редакция на потребител");
                                                stage.setScene(scene);
                                                stage.show();
                                                // Какво да се случва когато затворим нашия прозорец, който е отворил модал за редактиране на кола.
                                                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                                                    @Override
                                                    public void handle(WindowEvent paramT) {
                                                        // Обновяване на елементите в нашата таблица.
                                                        userObservableList = FXCollections.observableList(UserService.getAllUser());
                                                        table.setItems(userObservableList);
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
        table.setItems(userObservableList);
    }

    // Метод, който ще се изпълнява при натискане на бутона добавяне на нов обект.
    @FXML
    void addUser(ActionEvent event) {


        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile.fxml"));


            Scene scene = new Scene(fxmlLoader.load());
            //dialogController.setLoggedUser(user.getIdUser());

            stage.setTitle("Добавяне на потребител");
            stage.setScene(scene);
            stage.show();
            // Когато се затвори нашия отворен прозорец да се обнови таблица.
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent paramT) {
                    userObservableList = FXCollections.observableList(UserService.getAllUser());
                    table.setItems(userObservableList);
                }
            });
        } catch (Exception e) {
            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);

        }
    }
}
