package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.User;
import hristov.mihail.carracing.services.CarService;
import hristov.mihail.carracing.services.PersonService;
import hristov.mihail.carracing.services.UserService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UserModalController {

    static PreparedStatement storeImage;
    static File file;

    @FXML
    private ComboBox<String> roleCombobox;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button applyChangeButton;
    @FXML
    private TextField emailUserField;
    @FXML
    private ImageView userImageView;

    @FXML
    private TextField personUserField;

    @FXML
    private Label label;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private TextField passwordUserField;
    @FXML
    private Label labelUserName;

    @FXML
    private Button uploadImageButton;
    private User user;
    private int carId;

    // Нашият метод, който приема обект, който ще се зареди в нашия модал. Тоест като цъкнеш редактирай този метод получава от таблицата обекта, който ще редактираме и го задава за нашия модал.
    public void setUser(User user) {
        this.user = user;
    }

    public boolean isValidEmail(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (!Pattern.compile(regexPattern).matcher(emailAddress).matches()) {
            WarningController.openMessageModal("Въведете валиден имейл!", "Невалидни данни", MessageType.WARNING);
        }
        return Pattern.compile(regexPattern).matcher(emailAddress).matches();
    }

    public boolean isValidPassword(String password) {
        String regexPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        if (!password.matches(regexPattern)) {
            WarningController.openMessageModal("Въведете валидна парола!", "Невалидни данни", MessageType.WARNING);
        }
        return password.matches(regexPattern);
    }

    // Какво да се случва като цъкнеш бутона приложи.
    @FXML
    void applyChanges(ActionEvent event) {
        // Програма да не би нашия обект да е празен. Ако е празен, значи е избран прозорец за добавяне на кола.
        // Ако не е празен, значи ще променяме кола
        if (Objects.isNull(user)) {

            String[] name = personUserField.getText().split(" ");
            // Проверяваме дали въведените полета са с коректни данни.
            if (!(emailUserField.getText().equals(null) || passwordUserField.getText().equals(null) || personUserField.getText().equals(null) || emailUserField.getText().equals("") || passwordUserField.getText().equals("") || personUserField.getText().equals(""))) {
                // Проверяваме дали имейлът е валиден
                if (isValidEmail(emailUserField.getText())) {
                    if (isValidPassword(passwordUserField.getText())) {
                        if (name.length == 2) {

                            // Ако всичко е наред с данните, значи може да запишем нашия нов обект в базата данни.
                            // Запазваме информацията от нашите полета в нов обект и този обект го добавяме в базата данни.
                            PersonService.addPerson(name[0], name[1]);
                            User newUser = new User(emailUserField.getText(), passwordUserField.getText(), roleCombobox.getValue(), PersonService.getLastPerson().getIdPerson());
                            if (Objects.isNull(UserService.getUser(newUser.getEmailUser()))) {
                                UserService.addUser(newUser);
                                user = UserService.getLastUser();
                                try {
                                    // Проверяваме дали има качено изображение.

                                    if (!Objects.isNull(file)) {
                                        FileInputStream fileInputStream = new FileInputStream(file);
                                        // Подготвяме нашата команда за добавяне на изображение на нашата кола.
                                        storeImage = PersonService.setImagePerson();
                                        // Задаваме със стойности, двете въпросителни в нашата заявка.
                                        storeImage.setBinaryStream(1, fileInputStream, fileInputStream.available());
                                        // Изпълняваме нашата заявка.
                                        storeImage.execute();
                                    }
                                    // Задаваме нашите полета да бъдат равни на полетата от нашия обект. Нали след като записахме колата, изтеглихме отново от базата данни за всеки случай.
                                    roleCombobox.setValue(user.getTypeUser());
                                    emailUserField.setText(user.getEmailUser());
                                    personUserField.setText(PersonService.getPerson(user.getUserHasPerson()).getFirstNamePerson() + " " + PersonService.getPerson(user.getUserHasPerson()).getLastNamePerson());
                                    // Задаваме изображение на ImageView-то
                                    userImageView.setImage(PersonService.getImagePerson(PersonService.getPerson(user.getUserHasPerson())));
                                    // Обновяваме нашия модел и го отваряме като модал за редакция на вече създадената кола.
                                    Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-modal.fxml"));


                                    Scene scene = null;
                                    try {
                                        scene = new Scene(fxmlLoader.load());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                    UserModalController dialogController = fxmlLoader.getController();
                                    dialogController.setUser(user);
                                    stage.setTitle("Редакция на потребител");
                                    stage.setScene(scene);
                                    stage.show();
                                    //Stage stage = (Stage) applyChangeButton.getScene().getWindow();

                                    applyChangeButton.setText("Приложи");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                                }
                            } else {
                                WarningController.openMessageModal("Вече съществува потребител със същия имейл!", "Съществуващ потребител", MessageType.WARNING);
                            }

                            // Извличаме от базата данни новосъздадения обект, защото така е редно, ако има някакво форматиране на данните от нашата база данни.

                        } else {
                            WarningController.openMessageModal("Въведете валидни имена за потребителя!", "Невалидни данни", MessageType.WARNING);
                        }
                    }
                }
            } else {
                WarningController.openMessageModal("Попълнете всички данни за потребителя!", "Празни данни", MessageType.WARNING);
            }
        } else {
            // Ако обектът съдържащ колата, не е празен, това значи, че искаме да бъде актуализиран в базата данни.
            // Извършваме нужните проверки на въведените данни.
            if (!(emailUserField.getText().equals(null) || passwordUserField.getText().equals(null) || personUserField.getText().equals(null) || emailUserField.getText().equals("") || passwordUserField.getText().equals("") || personUserField.getText().equals(""))) {
                // Проверяваме дали имейлът е валиден
                if (isValidEmail(emailUserField.getText())) {
                    if (isValidPassword(passwordUserField.getText())) {

                        // Актуализираме данните на нашата кола.
                        user.setEmailUser(emailUserField.getText());
                        user.setPassUser(passwordUserField.getText());
                        user.setTypeUser(roleCombobox.getValue());


                        // Зареждаме каченото изображение и го задаваме на нашия обект.
                        try {
                            if (!Objects.isNull(file)) {
                                FileInputStream fileInputStream = new FileInputStream(file);
                                storeImage = PersonService.setImagePerson();
                                storeImage.setBinaryStream(1, fileInputStream, fileInputStream.available());
                                storeImage.setInt(2, this.user.getUserHasPerson());
                                storeImage.execute();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        UserService.updateUser(user);
                        Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-modal.fxml"));


                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                        }
                        // Обновяваме нашето прозорче за всеки случай.
                        //dialogController.setLoggedUser(user.getIdCar());
                        UserModalController dialogController = fxmlLoader.getController();
                        dialogController.setUser(user);
                        stage.setScene(scene);
                        stage.show();
                        //Stage stage = (Stage) applyChangeButton.getScene().getWindow();


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
                userImageView.setImage(new Image(fileInputStream));
            } catch (Exception e) {
                WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
            }
        } else {
            userImageView.setImage(PersonService.getImagePerson(PersonService.getPerson(user.getUserHasPerson())));

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
            assert applyChangeButton != null : "fx:id=\"applyChangeButton\" was not injected: check your FXML file 'user-modal.fxml'.";
            assert emailUserField != null : "fx:id=\"emailUserField\" was not injected: check your FXML file 'user-modal.fxml'.";
            assert userImageView != null : "fx:id=\"userImageView\" was not injected: check your FXML file 'user-modal.fxml'.";
            assert personUserField != null : "fx:id=\"personUserField\" was not injected: check your FXML file 'user-modal.fxml'.";
            assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'user-modal.fxml'.";
            assert label1 != null : "fx:id=\"label1\" was not injected: check your FXML file 'user-modal.fxml'.";
            assert label2 != null : "fx:id=\"label2\" was not injected: check your FXML file 'user-modal.fxml'.";
            assert label3 != null : "fx:id=\"label3\" was not injected: check your FXML file 'user-modal.fxml'.";
            assert labelUserName != null : "fx:id=\"labelUserName\" was not injected: check your FXML file 'user-modal.fxml'.";
            // Добавяме елементите в нашия comboBox
            roleCombobox.getItems().addAll("Admin", "User");
            emailUserField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            passwordUserField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            personUserField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });

            // Ако колата не е празна, прозорецът ще се зададе като такъв за редактиране на данни и ако е - за добавяне на кола.
            if (!Objects.isNull(user)) {
                //do stuff
                roleCombobox.setValue(user.getTypeUser());

                emailUserField.setText(user.getEmailUser());
                personUserField.setText(PersonService.getPerson(user.getUserHasPerson()).getFirstNamePerson() + " " + PersonService.getPerson(user.getUserHasPerson()).getLastNamePerson());
                passwordUserField.setText(user.getPassUser());


                userImageView.setImage(PersonService.getImagePerson(PersonService.getPerson(user.getUserHasPerson())));

            } else {
                applyChangeButton.setText("Добави");
                labelUserName.setText("Потребител");
                ((Stage) applyChangeButton.getScene().getWindow()).setTitle("Добавяне на потребител");
                roleCombobox.setValue("User");
                personUserField.setEditable(true);
            }
        });

    }

}
