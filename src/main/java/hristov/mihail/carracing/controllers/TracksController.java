package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Track;
import hristov.mihail.carracing.services.CarService;
import hristov.mihail.carracing.services.LoginService;
import hristov.mihail.carracing.services.TrackService;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TracksController {
    // Нашата таблица с данни, която се визуализира.
    // Получаваме данните за нея от service, който взима всички записи от базата данни като ArrayList.
    // Този лист се преобразува в ObservableList - изгъзен лист, който само той е свъместим с TableView.
    ObservableList<Track> tracksObservableList = FXCollections.observableList(TrackService.getAllTrack());
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button addTrack;
    @FXML
    private TableColumn<Track, String> actions;
    @FXML
    private TableColumn<Track, String> nameTrack;
    @FXML
    private TableColumn<Track, String> lengthTrack;
    @FXML
    private TableColumn<Track, String> locationTrack;
    @FXML
    private TableView<Track> table;

    // Метод, който се изпълнява при инициализиране на нашия fxml прозорец.
    @FXML
    void initialize() {
        // Задаване как всяка колона да бъде запълвана с данни
        nameTrack.setCellValueFactory(new PropertyValueFactory<Track, String>("nameTrack"));
        lengthTrack.setCellValueFactory(new PropertyValueFactory<Track, String>("lengthTrack"));
        locationTrack.setCellValueFactory(new PropertyValueFactory<Track, String>("locationTrack"));


        // Проверка дали логнатия потребител е администратор
        // Ако е администратор прозорецът ще се визуализира по един начин, ако не е по друг.
        if (LoginService.isLoggedUserAdmin()) {
            nameTrack.maxWidthProperty().bind(table.widthProperty().divide(4));
            lengthTrack.maxWidthProperty().bind(table.widthProperty().divide(4));
            actions.maxWidthProperty().bind(table.widthProperty().divide(4));
            locationTrack.maxWidthProperty().bind(table.widthProperty().divide(4));
            nameTrack.minWidthProperty().bind(table.widthProperty().divide(4));
            lengthTrack.minWidthProperty().bind(table.widthProperty().divide(4));
            actions.minWidthProperty().bind(table.widthProperty().divide(4));
            locationTrack.minWidthProperty().bind(table.widthProperty().divide(4));
        } else {
            nameTrack.maxWidthProperty().bind(table.widthProperty().divide(4));
            lengthTrack.maxWidthProperty().bind(table.widthProperty().divide(4));
            actions.maxWidthProperty().bind(table.widthProperty().divide(4));
            locationTrack.maxWidthProperty().bind(table.widthProperty().divide(4));
            nameTrack.minWidthProperty().bind(table.widthProperty().divide(4));
            lengthTrack.minWidthProperty().bind(table.widthProperty().divide(4));
            actions.minWidthProperty().bind(table.widthProperty().divide(4));
            locationTrack.minWidthProperty().bind(table.widthProperty().divide(4));
            addTrack.setVisible(false);
            mainPane.setBottomAnchor(table, 14d);
        }
        // Създаване на така наречения CellFactory - как ще се пълни с данни всяка клетка.
        // nameTrack.setCellValueFactory(new PropertyValueFactory<Track, String>("nameTrack"));
        Callback<TableColumn<Track, String>, TableCell<Track, String>> cellFactory = //
                new Callback<TableColumn<Track, String>, TableCell<Track, String>>() {

                    @Override
                    public TableCell call(final TableColumn<Track, String> param) {
                        //Ако потребителя е админ, ще се покажат едни бутони, ако не е - други.
                        if (LoginService.isLoggedUserAdmin()) {
                            //Деклариране на нашата клетка от таблицата - тя е нещо като pane.
                            //Този обект казва как всяка клетка ще се създава в тази колона.
                            final TableCell<Track, String> cell = new TableCell<Track, String>() {

                                //В клетката ще има два бутона
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
                                            Track track = getTableView().getItems().get(getIndex());
                                            TrackService.deleteTrack(track.getIdTrack());
                                            tracksObservableList = FXCollections.observableList(TrackService.getAllTrack());
                                            table.setItems(tracksObservableList);
                                        });

                                        editButton.setOnAction(event -> {
                                            Track track = getTableView().getItems().get(getIndex());

                                            try {
                                                Stage stage = new Stage();
                                                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("track-modal.fxml"));


                                                Scene scene = new Scene(fxmlLoader.load());
                                                //dialogController.setLoggedUser(track.getIdCar());
                                                TrackModalController dialogController = fxmlLoader.getController();
                                              dialogController.setTrack(track);
                                                stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
                                              stage.setTitle("Редакция на " + track.getNameTrack());
                                                stage.setScene(scene);
                                                stage.show();
                                                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                                                    @Override
                                                    public void handle(WindowEvent paramT) {
                                                        tracksObservableList = FXCollections.observableList(TrackService.getAllTrack());
                                                        table.setItems(tracksObservableList);
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
                                                //messageController.setLoggedUser(track.getIdCar());
                                                WarningController messageController = fxmlLoader.getController();
                                                messageController.setErrorMessage(e.getMessage());
                                                stage1.setTitle("Системна грешка");
                                                stage1.setScene(scene);
                                                stage1.show();
                                                e.printStackTrace();
                                                //TODO: Екран за грешка
                                            }


                                        });
                                        HBox pane = new HBox(deleteButton, editButton);
                                        pane.setSpacing(5);
                                        pane.setAlignment(Pos.CENTER);
                                        setGraphic(pane);
                                        setText(null);
                                    }
                                }
                            };
                            cell.setAlignment(Pos.CENTER);

                            return cell;
                        } else {
                            final TableCell<Track, String> cell = new TableCell<Track, String>() {

                                final Button chooseButton = new Button("Преглед");


                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        chooseButton.setStyle("-fx-background-color: #e5aa00; -fx-text-fill: white; ");
                                        chooseButton.setOnAction(event -> {
                                            Track track = getTableView().getItems().get(getIndex());

                                            try {
                                                Stage stage = new Stage();
                                                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("track-modal.fxml"));


                                                Scene scene = new Scene(fxmlLoader.load());
                                                //dialogController.setLoggedUser(track.getIdCar());
                                                TrackModalController dialogController = fxmlLoader.getController();
                                                dialogController.setTrack(track);
                                                stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
                                                stage.setTitle("Преглед на " + track.getNameTrack());
                                                stage.setScene(scene);
                                                stage.show();
                                                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                                                    @Override
                                                    public void handle(WindowEvent paramT) {
                                                        tracksObservableList = FXCollections.observableList(TrackService.getAllTrack());
                                                        table.setItems(tracksObservableList);
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
                                                //messageController.setLoggedUser(track.getIdCar());
                                                WarningController messageController = fxmlLoader.getController();
                                                messageController.setErrorMessage(e.getMessage());
                                                stage1.setTitle("Системна грешка");
                                                stage1.setScene(scene);
                                                stage1.show();
                                                e.printStackTrace();
                                                //TODO: Екран за грешка
                                            }
                                        });


                                        HBox pane = new HBox(chooseButton);
                                        pane.setSpacing(5);
                                        pane.setAlignment(Pos.CENTER);
                                        setGraphic(pane);
                                        setText(null);
                                    }
                                }
                            };
                            cell.setAlignment(Pos.CENTER);
                            return cell;
                        }
                    }
                };
        actions.setCellFactory(cellFactory);
        table.setItems(tracksObservableList);
    }


    @FXML
    void addTrack(ActionEvent event) {


        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("track-modal.fxml"));


            Scene scene = new Scene(fxmlLoader.load());
            //dialogController.setLoggedUser(car.getIdCar());
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/hristov/mihail/carracing/icon.png")));
            stage.setTitle("Добавяне на кола");
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent paramT) {
                    tracksObservableList = FXCollections.observableList(TrackService.getAllTrack());
                    table.setItems(tracksObservableList);
                }
            });
        } catch (Exception e) {

            //TODO: Екран за грешка
        }
    }
}
