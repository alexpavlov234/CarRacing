package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.services.CarService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CarModalController {

    static PreparedStatement storeImage;
    static File file;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button applyChangeButton;
    @FXML
    private TextField brandCarField;
    @FXML
    private ImageView carImageView;
    @FXML
    private TextField engineCarField;
    @FXML
    private TextField fuelCarField;
    @FXML
    private TextField horsepowerCarField;
    @FXML
    private Label label;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label labelCarName;
    @FXML
    private TextField modelCarField;
    @FXML
    private Button uploadImageButton;
    private Car car;
    private int carId;

    // Нашият метод, който приема обект, който ще се зареди в нашия модал. Тоест като цъкнеш редактирай този метод получава от таблицата обекта, който ще редактираме и го задава за нашия модал.
    public void setCar(Car car) {
        this.car = car;
    }

    // Какво да се случва като цъкнеш бутона приложи.
    @FXML
    void applyChanges(ActionEvent event) {
        // Програма да не би нашия обект да е празен. Ако е празен, значи е избран прозорец за добавяне на кола.
        // Ако не е празен, значи ще променяме кола
        if (Objects.isNull(car)) {
            // Проверяваме дали въведените полета са с коректни данни.
            if (!(modelCarField.getText().equals(null) || brandCarField.getText().equals(null) || engineCarField.getText().equals(null) || fuelCarField.getText().equals(null) || horsepowerCarField.getText().equals(null) || modelCarField.getText().equals("") || brandCarField.getText().equals("") || engineCarField.getText().equals("") || fuelCarField.getText().equals("") || horsepowerCarField.getText().equals(""))) {
                // Проверяваме дали нашият двигател е с валидно число.
                if (isValidEngine(engineCarField.getText())) {
                    // Проверяваме дали е въведено число за конски сили.
                    if (isNumeric(horsepowerCarField.getText())) {
                        // Ако всичко е наред с данните, значи може да запишем нашия нов обект в базата данни.
                        // Запазваме информацията от нашите полета в нов обект и този обект го добавяме в базата данни.
                        car = new Car(modelCarField.getText(), brandCarField.getText(), engineCarField.getText(), fuelCarField.getText(), Integer.parseInt(horsepowerCarField.getText()));
                        CarService.addCar(car);
                        // Извличаме от базата данни новосъздадения обект, защото така е редно, ако има някакво форматиране на данните от нашата база данни.
                        car = CarService.getLastCar();
                        try {
                            // Проверяваме дали има качено изображение.
                            if (!Objects.isNull(file)) {
                                FileInputStream fileInputStream = new FileInputStream(file);
                                // Подготвяме нашата команда за добавяне на изображение на нашата кола.
                                storeImage = CarService.setImageCar();
                                // Задаваме със стойности, двете въпросителни в нашата заявка.
                                storeImage.setBinaryStream(1, fileInputStream, fileInputStream.available());
                                storeImage.setInt(2, this.car.getIdCar());
                                // Изпълняваме нашата заявка.
                                storeImage.execute();
                            }
                            // Задаваме нашите полета да бъдат равни на полетата от нашия обект. Нали след като записахме колата, изтеглихме отново от базата данни за всеки случай.
                            modelCarField.setText(car.getModelCar());
                            brandCarField.setText(car.getBrandCar());
                            fuelCarField.setText(car.getFuelCar());
                            engineCarField.setText(car.getEngineCar());
                            horsepowerCarField.setText(Integer.toString(car.getHorsepowerCar()));
                            labelCarName.setText(car.getBrandCar() + " " + car.getModelCar());
                            // Задаваме изображение на ImageView-то
                            carImageView.setImage(CarService.getImageCar(car));
                            // Обновяваме нашия модел и го отваряме като модал за редакция на вече създадената кола.
                            Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("car-modal.fxml"));


                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            CarModalController dialogController = fxmlLoader.getController();
                            dialogController.setCar(car);
                            stage.setTitle("Редакция на " + car.getBrandCar() + " " + car.getModelCar());
                            stage.setScene(scene);
                            stage.show();
                            //Stage stage = (Stage) applyChangeButton.getScene().getWindow();

                            applyChangeButton.setText("Приложи");
                        } catch (Exception e) {
                            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                        }
                    }
                }
            } else {
                WarningController.openMessageModal("Попълнете всички данни за колата!", "Празни данни", MessageType.WARNING);
            }
        } else {
            // Ако обектът съдържащ колата, не е празен, това значи, че искаме да бъде актуализиран в базата данни.
            // Извършваме нужните проверки на въведените данни.
            if (!(modelCarField.getText().equals(null) || brandCarField.getText().equals(null) || engineCarField.getText().equals(null) || fuelCarField.getText().equals(null) || horsepowerCarField.getText().equals(null) || modelCarField.getText().equals("") || brandCarField.getText().equals("") || engineCarField.getText().equals("") || fuelCarField.getText().equals("") || horsepowerCarField.getText().equals(""))) {
                {
                    if (!(modelCarField.getText().equals(null) || brandCarField.getText().equals(null) || engineCarField.getText().equals(null) || fuelCarField.getText().equals(null) || horsepowerCarField.getText().equals(null) || modelCarField.getText().equals("") || brandCarField.getText().equals("") || engineCarField.getText().equals("") || fuelCarField.getText().equals("") || horsepowerCarField.getText().equals(""))) {
                        // Проверяваме дали нашият двигател е с валидно число.
                        if (isValidEngine(engineCarField.getText())) {
                            // Проверяваме дали е въведено число за конски сили.
                            if (isNumeric(horsepowerCarField.getText())) {
                                // Актуализираме данните на нашата кола.
                                car.setModelCar(modelCarField.getText());
                                car.setBrandCar(brandCarField.getText());
                                car.setFuelCar(fuelCarField.getText());
                                car.setEngineCar(engineCarField.getText());
                                // Зареждаме каченото изображение и го задаваме на нашия обект.
                                try {
                                    if (!Objects.isNull(file)) {
                                        FileInputStream fileInputStream = new FileInputStream(file);
                                        storeImage = CarService.setImageCar();
                                        storeImage.setBinaryStream(1, fileInputStream, fileInputStream.available());
                                        storeImage.setInt(2, this.car.getIdCar());
                                        storeImage.execute();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                CarService.updateCar(car);
                                Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("car-modal.fxml"));


                                Scene scene = null;
                                try {
                                    scene = new Scene(fxmlLoader.load());
                                } catch (IOException e) {
                                    WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                                }
                                // Обновяваме нашето прозорче за всеки случай.
                                //dialogController.setLoggedUser(car.getIdCar());
                                CarModalController dialogController = fxmlLoader.getController();
                                dialogController.setCar(car);
                                stage.setScene(scene);
                                stage.show();
                                //Stage stage = (Stage) applyChangeButton.getScene().getWindow();


                            }
                        }
                    }
                }
            } else {
                WarningController.openMessageModal("Попълнете всички данни за колата!", "Празни данни", MessageType.WARNING);
            }
        }
    }
    // Метод, който се извиква при натискане на бутона за качване на изображение.
    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        // Отваря диалог за избор на файл от системата.
        file = fileChooser.showOpenDialog(uploadImageButton.getScene().getWindow());
        // Ако файла не празен.
        if (!Objects.isNull(file)) {
            try {

                FileInputStream fileInputStream = new FileInputStream(file);
                // Задаваме изображение на нашия ImageView.
                carImageView.setImage(new Image(fileInputStream));
            } catch (Exception e) {
                WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
            }
        }
    }
    // Проверка чрез regex дали двигателят е валиден.
    public boolean isValidEngine(String name) {
        String regexPattern = "^[0-9].[0-9].*$";

        if (!name.matches(regexPattern)) {
            WarningController.openMessageModal("Въведено е невалидно име за двигател!", "Невалиден двигател", MessageType.WARNING);
        }
        return name.matches(regexPattern);
    }

    // Проверка чрез regex дали даден низ е само от числа.
    public boolean isNumeric(String strNum) {
        String regexPattern = "-?\\d+(\\.\\d+)?";
        if (!strNum.matches(regexPattern)) {
            WarningController.openMessageModal("Въведено е невалидно число за конски сили!", "Невалидни конски сили", MessageType.WARNING);
        }
        return strNum.matches(regexPattern);
    }
    // Кметът, който се изпълнява при отварянето на нашия модел.
    @FXML
    void initialize() {

        Platform.runLater(() -> {
            assert applyChangeButton != null : "fx:id=\"applyChangeButton\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert brandCarField != null : "fx:id=\"brandCarField\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert carImageView != null : "fx:id=\"carImageView\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert engineCarField != null : "fx:id=\"engineCarField\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert fuelCarField != null : "fx:id=\"fuelCarField\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert horsepowerCarField != null : "fx:id=\"horsepowerCarField\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert label1 != null : "fx:id=\"label1\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert label2 != null : "fx:id=\"label2\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert label3 != null : "fx:id=\"label3\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert labelCarName != null : "fx:id=\"labelCarName\" was not injected: check your FXML file 'car-modal.fxml'.";
            assert modelCarField != null : "fx:id=\"modelCarField\" was not injected: check your FXML file 'car-modal.fxml'.";
            modelCarField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            brandCarField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            engineCarField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            fuelCarField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            horsepowerCarField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            // Ако колата не е празна, прозорецът ще се зададе като такъв за редактиране на данни и ако е - за добавяне на кола.
            if (car != null) {
                //do stuff
                modelCarField.setText(car.getModelCar());
                brandCarField.setText(car.getBrandCar());
                fuelCarField.setText(car.getFuelCar());
                engineCarField.setText(car.getEngineCar());
                horsepowerCarField.setText(Integer.toString(car.getHorsepowerCar()));
                labelCarName.setText(car.getBrandCar() + " " + car.getModelCar());

                carImageView.setImage(CarService.getImageCar(car));
            } else {
                applyChangeButton.setText("Добави");
                labelCarName.setText("Добавяне на кола");
            }
        });

    }

}
