package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.services.PersonService;
import hristov.mihail.carracing.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class RankingController {
    // Нашата таблица с данни, която се визуализира.
    // Получаваме данните за нея от service, който взима всички записи от базата данни като ArrayList.
    // Този лист се преобразува в ObservableList - лист, който е свъместим с TableView.
    ObservableList<Person> userObservableList = new ObservableListBase<Person>() {
        @Override
        public Person get(int index) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    };
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button addUser;

    @FXML
    private TableColumn<Person, String> nationality;
    @FXML
    private TableColumn<Person, String> firstName;
    @FXML
    private TableColumn<Person, String> lastName;
    @FXML
    private TableColumn<Person, String> points;
    @FXML
    private TableView<Person> table;

    // Метод, който се изпълнява при зареждането на нашия прозорец.
    @FXML
    void initialize() {
        firstName.setCellValueFactory(new PropertyValueFactory<Person, String>("firstNamePerson"));
        lastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastNamePerson"));
        nationality.setCellValueFactory(new PropertyValueFactory<Person, String>("nationalityPerson"));
        points.setCellValueFactory(new PropertyValueFactory<Person, String>("pointsPerson"));

        ArrayList<Person> people = PersonService.getAllPerson();
        ArrayList<Person> racers = new ArrayList<>();
        for (Person person : people) {
            //  if(!Objects.isNull(UserService.getUser(person))) {
            //     if (!UserService.getUser(person).isAdmin()) {
            racers.add(person);
            //     }
            // }
        }

        userObservableList = FXCollections.observableList(racers);
        // Проверяваме дали логнат потребител е администратор.

        firstName.maxWidthProperty().bind(table.widthProperty().divide(4));
        lastName.maxWidthProperty().bind(table.widthProperty().divide(4));
        nationality.maxWidthProperty().bind(table.widthProperty().divide(4));
        points.maxWidthProperty().bind(table.widthProperty().divide(4));
        points.minWidthProperty().bind(table.widthProperty().divide(4));
        firstName.minWidthProperty().bind(table.widthProperty().divide(4));
        lastName.minWidthProperty().bind(table.widthProperty().divide(4));
        nationality.minWidthProperty().bind(table.widthProperty().divide(4));

        // Задаваме елементите на таблицата
        table.setItems(userObservableList);
        points.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().add(points);
        table.sort();
    }

    // Метод, който ще се изпълнява при натискане на бутона добавяне на нов обект.

}
