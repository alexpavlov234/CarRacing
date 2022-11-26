package hristov.mihail.carracing.controllers;

import hristov.mihail.carracing.HelloApplication;
import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.models.User;
import hristov.mihail.carracing.services.CarService;
import hristov.mihail.carracing.services.LoginService;
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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ProfileController {

    static PreparedStatement storeImage;
    static File file;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField agePersonField;

    @FXML
    private Button applyChangeButton;


    @FXML
    private ComboBox<String> carPersonCombobox;

    @FXML
    private TextField emailUserField;

    @FXML
    private TextField firstNamePersonField;

    @FXML
    private Label label;

    @FXML
    private Label label1;

    @FXML
    private Label label11;

    @FXML
    private Label label4;

    @FXML
    private Label label41;

    @FXML
    private Label label42;

    @FXML
    private Label label43;

    @FXML
    private Label label431;

    @FXML
    private Label label432;

    @FXML
    private Label label433;

    @FXML
    private Label labelUserName;

    @FXML
    private TextField lastNamePersonField;

    @FXML
    private TextField middleNamePersonField;

    @FXML
    private ComboBox<String> nationalityPersonCombobox;

    @FXML
    private TextField passwordUserField;

    @FXML
    private TextField pointsPersonField;

    @FXML
    private ComboBox<String> roleCombobox;

    @FXML
    private Button uploadImageButton;

    @FXML
    private ImageView userImageView;
    private User user;
    private Person person;


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

    public boolean isValidName(String name) {
        String regexPattern = "[А-ЯЁ][-А-яЁё]+|[A-Z][a-z]+";

        if (!name.matches(regexPattern)) {
            WarningController.openMessageModal("Въведете коректни имена!", "Невалдини данни", MessageType.WARNING);
        }
        return name.matches(regexPattern);
    }

    // Какво да се случва като цъкнеш бутона приложи.
    @FXML
    void applyChanges(ActionEvent event) {

        // Програма да не би нашия обект да е празен. Ако е празен, значи е избран прозорец за добавяне на кола.
        // Ако не е празен, значи ще променяме кола

        // Ако обектът съдържащ колата, не е празен, това значи, че искаме да бъде актуализиран в базата данни.
        // Извършваме нужните проверки на въведените данни.
if (!Objects.isNull(user)) {
    if (!(Objects.isNull(emailUserField.getText()) || Objects.isNull(passwordUserField.getText()) || Objects.isNull(firstNamePersonField.getText()) || Objects.isNull(middleNamePersonField.getText()) || Objects.isNull(lastNamePersonField.getText()) || Objects.isNull(agePersonField.getText()))) {
        if (!(emailUserField.getText().equals("") || passwordUserField.getText().equals("") || firstNamePersonField.getText().equals("") || middleNamePersonField.getText().equals("") || lastNamePersonField.getText().equals("") || agePersonField.getText().equals(""))) {
            // Проверяваме дали имейлът е валиден
            if (isValidEmail(emailUserField.getText())) {
                if (isValidPassword(passwordUserField.getText())) {
                    if (isValidName(firstNamePersonField.getText()) && isValidName(middleNamePersonField.getText()) && isValidName(lastNamePersonField.getText())) {
                        if (isValidAge(agePersonField.getText())) {
                            if (isNumeric(pointsPersonField.getText())) {
                                if (!(Objects.isNull(carPersonCombobox.getValue()) || Objects.isNull(nationalityPersonCombobox.getValue()))) {
                                    // Актуализираме данните на нашата кола.
                                    user.setEmailUser(emailUserField.getText());
                                    user.setPassUser(passwordUserField.getText());
                                    person.setAgePerson(Integer.parseInt(agePersonField.getText()));
                                    person.setFirstNamePerson(firstNamePersonField.getText());
                                    person.setMiddleNamePerson(middleNamePersonField.getText());
                                    person.setLastNamePerson(lastNamePersonField.getText());
                                    person.setNationalityPerson(nationalityPersonCombobox.getValue());
                                    person.setPointsPerson(Integer.parseInt(pointsPersonField.getText()));
                                    person.setCarPerson(CarService.getCar(carPersonCombobox.getValue()).getIdCar());
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
                                    PersonService.updatePerson(person);
                                    UserService.updateUser(user);
                                    Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile.fxml"));


                                    Scene scene = null;
                                    try {
                                        scene = new Scene(fxmlLoader.load());
                                    } catch (IOException e) {
                                        WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                                    }
                                    // Обновяваме нашето прозорче за всеки случай.
                                    //dialogController.setLoggedUser(user.getIdCar());
                                    ProfileController dialogController = fxmlLoader.getController();
                                    dialogController.setUser(user);
                                    stage.setScene(scene);
                                    stage.show();
                                    //Stage stage = (Stage) applyChangeButton.getScene().getWindow();

                                } else {
                                    WarningController.openMessageModal("Попълнете всички данни за потребителя!", "Празни данни", MessageType.WARNING);
                                }
                            }
                        }
                    }

                }

            }

        } else {
            WarningController.openMessageModal("Попълнете всички данни за потребителя!", "Празни данни", MessageType.WARNING);
        }
    } else {
        WarningController.openMessageModal("Попълнете всички данни за потребителя!", "Празни данни", MessageType.WARNING);
    }
} else {
    if (!(Objects.isNull(emailUserField.getText()) || Objects.isNull(passwordUserField.getText()) || Objects.isNull(firstNamePersonField.getText()) || Objects.isNull(middleNamePersonField.getText()) || Objects.isNull(lastNamePersonField.getText()) || Objects.isNull(agePersonField.getText()))) {
        if (!(emailUserField.getText().equals("") || passwordUserField.getText().equals("") || firstNamePersonField.getText().equals("") || middleNamePersonField.getText().equals("") || lastNamePersonField.getText().equals("") || agePersonField.getText().equals(""))) {
            // Проверяваме дали имейлът е валиден
            if (isValidEmail(emailUserField.getText())) {
                if (isValidPassword(passwordUserField.getText())) {
                    if (isValidName(firstNamePersonField.getText()) && isValidName(middleNamePersonField.getText()) && isValidName(lastNamePersonField.getText())) {
                        if (isValidAge(agePersonField.getText())) {
                            if (isNumeric(pointsPersonField.getText())) {
                                if (!(Objects.isNull(carPersonCombobox.getValue()) || Objects.isNull(nationalityPersonCombobox.getValue()))) {
                                    // Актуализираме данните на нашата кола.
                                    user = new User();
                                    person = new Person();
                                    user.setEmailUser(emailUserField.getText());
                                    user.setPassUser(passwordUserField.getText());
                                    person.setAgePerson(Integer.parseInt(agePersonField.getText()));
                                    person.setFirstNamePerson(firstNamePersonField.getText());
                                    person.setMiddleNamePerson(middleNamePersonField.getText());
                                    person.setLastNamePerson(lastNamePersonField.getText());
                                    person.setNationalityPerson(nationalityPersonCombobox.getValue());
                                    person.setPointsPerson(Integer.parseInt(pointsPersonField.getText()));
                                    person.setCarPerson(CarService.getCar(carPersonCombobox.getValue()).getIdCar());
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
                                    System.out.println(person.getCarPerson());
                                    PersonService.addPerson(person);
                                    User user = new User(this.user, PersonService.getLastPerson().getIdPerson());
                                    if (Objects.isNull(UserService.getUser(user.getEmailUser()))) {
                                        UserService.addUser(user);
                                        this.user = UserService.getLastUser();
                                        Stage stage = (Stage) applyChangeButton.getScene().getWindow();
                                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile.fxml"));


                                        Scene scene = null;
                                        try {
                                            scene = new Scene(fxmlLoader.load());
                                        } catch (IOException e) {
                                            WarningController.openMessageModal(e.getMessage(), "Системна грешка", MessageType.WARNING);
                                        }
                                        // Обновяваме нашето прозорче за всеки случай.
                                        //dialogController.setLoggedUser(user.getIdCar());
                                        ProfileController dialogController = fxmlLoader.getController();
                                        dialogController.setUser(this.user);
                                        stage.setScene(scene);
                                        stage.show();
                                    } else {
                                        WarningController.openMessageModal("Вече съществува потребител със същия имейл!", "Съществуващ потребител", MessageType.WARNING);
                                    }

                                    //Stage stage = (Stage) applyChangeButton.getScene().getWindow();

                                } else {
                                    WarningController.openMessageModal("Попълнете всички данни за потребителя!", "Празни данни", MessageType.WARNING);
                                }
                            }
                        }
                    }

                }

            }

        } else {
            WarningController.openMessageModal("Попълнете всички данни за потребителя!", "Празни данни", MessageType.WARNING);
        }
    } else {
        WarningController.openMessageModal("Попълнете всички данни за потребителя!", "Празни данни", MessageType.WARNING);
    }
}

    }

    public boolean isNumeric(String strNum) {
        String regexPattern = "-?\\d+(\\.\\d+)?";
        if (!strNum.matches(regexPattern)) {
            WarningController.openMessageModal("Въведено е невалидно число за възраст!", "Невалидни конски сили", MessageType.WARNING);
        }
        return strNum.matches(regexPattern);
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


    // Проверка чрез regex дали даден низ е само от числа.
    public boolean isValidAge(String strNum) {
        String regexPattern = "-?\\d+(\\.\\d+)?";
        if (!strNum.matches(regexPattern)) {
            WarningController.openMessageModal("Въведено е невалидно число за възраст!", "Невалидни данни", MessageType.WARNING);
            return false;
        } else {
            int age = Integer.parseInt(strNum);
            if (!(age >= 18 && age <= 150)) {

                WarningController.openMessageModal("Въведена е невалидна възраст!", "Невалидни данни", MessageType.WARNING);
            }
            return age >= 18 && age <= 150;
        }

    }

    // Кметът, който се изпълнява при отварянето на нашия модел.
    @FXML
    void initialize() {

        Platform.runLater(() -> {
            assert agePersonField != null : "fx:id=\"agePersonField\" was not injected: check your FXML file 'profile.fxml'.";
            assert applyChangeButton != null : "fx:id=\"applyChangeButton\" was not injected: check your FXML file 'profile.fxml'.";
            assert carPersonCombobox != null : "fx:id=\"carPersonField\" was not injected: check your FXML file 'profile.fxml'.";
            assert emailUserField != null : "fx:id=\"emailUserField\" was not injected: check your FXML file 'profile.fxml'.";
            assert firstNamePersonField != null : "fx:id=\"firstNamePersonField\" was not injected: check your FXML file 'profile.fxml'.";
            assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'profile.fxml'.";
            assert label1 != null : "fx:id=\"label1\" was not injected: check your FXML file 'profile.fxml'.";
            assert label11 != null : "fx:id=\"label11\" was not injected: check your FXML file 'profile.fxml'.";
            assert label4 != null : "fx:id=\"label4\" was not injected: check your FXML file 'profile.fxml'.";
            assert label41 != null : "fx:id=\"label41\" was not injected: check your FXML file 'profile.fxml'.";
            assert label42 != null : "fx:id=\"label42\" was not injected: check your FXML file 'profile.fxml'.";
            assert label43 != null : "fx:id=\"label43\" was not injected: check your FXML file 'profile.fxml'.";
            assert label431 != null : "fx:id=\"label431\" was not injected: check your FXML file 'profile.fxml'.";
            assert label432 != null : "fx:id=\"label432\" was not injected: check your FXML file 'profile.fxml'.";
            assert label433 != null : "fx:id=\"label433\" was not injected: check your FXML file 'profile.fxml'.";
            assert labelUserName != null : "fx:id=\"labelUserName\" was not injected: check your FXML file 'profile.fxml'.";
            assert lastNamePersonField != null : "fx:id=\"lastNamePersonField\" was not injected: check your FXML file 'profile.fxml'.";
            assert middleNamePersonField != null : "fx:id=\"middleNamePersonField\" was not injected: check your FXML file 'profile.fxml'.";
            assert nationalityPersonCombobox != null : "fx:id=\"nationalityPersonField\" was not injected: check your FXML file 'profile.fxml'.";
            assert passwordUserField != null : "fx:id=\"passwordUserField\" was not injected: check your FXML file 'profile.fxml'.";
            assert pointsPersonField != null : "fx:id=\"pointsPersonField\" was not injected: check your FXML file 'profile.fxml'.";
            assert roleCombobox != null : "fx:id=\"roleCombobox\" was not injected: check your FXML file 'profile.fxml'.";
            assert uploadImageButton != null : "fx:id=\"uploadImageButton\" was not injected: check your FXML file 'profile.fxml'.";
            assert userImageView != null : "fx:id=\"userImageView\" was not injected: check your FXML file 'profile.fxml'.";
            if (!Objects.isNull(user)) {
                person = PersonService.getPerson(user.getUserHasPerson());
            }
            // Добавяме елементите в нашия comboBox
            roleCombobox.getItems().addAll("Admin", "User");
            carPersonCombobox.getItems().addAll(CarService.getAllCarNames());
            nationalityPersonCombobox.getItems().addAll("Австралия", "Австрия", "Азербайджан", "Албания", "Алжир", "Ангола", "Андора", "Антигуа и Барбуда", "Аржентина", "Армения", "Афганистан", "Бангладеш", "Барбадос", "Бахамски острови", "Бахрейн", "Беларус", "Белгия", "Белиз", "Бенин", "Боливия", "Босна и Херцеговина", "Ботсвана", "Бразилия", "Бруней", "Буркина Фасо", "Бурунди", "Бутан", "България", "Вануату", "Великобритания", "Венецуела", "Виетнам", "Габон", "Гамбия", "Гана", "Гватемала", "Гвиана", "Гвинея", "Гвинея-Бисау", "Германия", "Гренада", "Грузия", "Гърция", "Дания", "Демократична република Конго", "Джибути", "Доминика", "Доминиканска република", "Египет", "Еквадор", "Екваториална Гвинея", "Еритрея", "Есватини", "Естония", "Етиопия", "Замбия", "Зимбабве", "Израел", "Източен Тимор", "Индия", "Индонезия", "Ирак", "Иран", "Ирландия", "Исландия", "Испания", "Италия", "Йемен", "Йордания", "Кабо Верде", "Казахстан", "Камбоджа", "Камерун", "Канада", "Катар", "Кения", "Кипър", "Киргизстан", "Кирибати", "Китай", "Колумбия", "Коморски острови", "Коста Рика", "Кот д'Ивоар", "Куба", "Кувейт", "Лаос", "Латвия", "Лесото", "Либерия", "Либия", "Ливан", "Литва", "Лихтенщайн", "Люксембург", "Мавритания", "Мавриций", "Мадагаскар", "Малави", "Малайзия", "Малдиви", "Мали", "Малта", "Мароко", "Маршалови острови", "Мексико", "Мианмар", "Микронезия", "Мозамбик", "Молдова", "Монако", "Монголия", "Намибия", "Науру", "Непал", "Нигер", "Нигерия", "Нидерландия", "Никарагуа", "Нова Зеландия", "Норвегия", "ОАЕ", "Оман", "Пакистан", "Палау", "Панама", "Папуа Нова Гвинея", "Парагвай", "Перу", "Полша", "Португалия", "Република Конго", "Руанда", "Румъния", "Русия", "Салвадор", "Самоа", "Сан Марино", "Сао Томе и Принсипи", "Саудитска Арабия", "САЩ", "Северна Корея", "Северна Македония", "Сейнт Винсент и Гренадини", "Сейнт Китс и Невис", "Сейнт Лусия", "Сейшелски острови", "Сенегал", "Сиера Леоне", "Сингапур", "Сирия", "Словакия", "Словения", "Соломонови острови", "Сомалия", "Судан", "Суринам", "Сърбия", "Таджикистан", "Тайланд", "Танзания", "Того", "Тонга", "Тринидад и Тобаго", "Тувалу", "Тунис", "Туркменистан", "Турция", "Уганда", "Узбекистан", "Украйна", "Унгария", "Уругвай", "Фиджи", "Филипини", "Финландия", "Франция", "Хаити", "Хондурас", "Хърватия", "Централноафриканска република", "Чад", "Черна гора", "Чехия", "Чили", "Швейцария", "Швеция", "Шри Ланка", "ЮАР", "Южен Судан", "Южна Корея", "Ямайка", "Япония");
            if (!(Objects.isNull(person) || person.getCarPerson() == 0)) {
                carPersonCombobox.setValue(CarService.getCarName(CarService.getCar(person.getCarPerson())));
            }
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
            firstNamePersonField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            middleNamePersonField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            lastNamePersonField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            agePersonField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            nationalityPersonCombobox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        applyChanges(new ActionEvent());
                    }
                }
            });
            if (!Objects.isNull(user)) {
                // Ако колата не е празна, прозорецът ще се зададе като такъв за редактиране на данни и ако е - за добавяне на кола.
                if (LoginService.isLoggedUserAdmin()) {
                    person = PersonService.getPerson(user.getUserHasPerson());
                    emailUserField.setText(user.getEmailUser());
                    passwordUserField.setText(user.getPassUser());
                    agePersonField.setText(Integer.toString(person.getAgePerson()));
                    firstNamePersonField.setText(person.getFirstNamePerson());
                    middleNamePersonField.setText(person.getMiddleNamePerson());
                    lastNamePersonField.setText(person.getLastNamePerson());
                    nationalityPersonCombobox.setValue(person.getNationalityPerson());
                    pointsPersonField.setText(Integer.toString(person.getPointsPerson()));

                        roleCombobox.setValue(user.getTypeUser());
                    if (!(Objects.isNull(person) || person.getCarPerson() == 0)) {
                        carPersonCombobox.setValue(CarService.getCarName(CarService.getCar(person.getCarPerson())));
                    }

                    userImageView.setImage(PersonService.getImagePerson(PersonService.getPerson(user.getUserHasPerson())));

                } else {
                    emailUserField.setText(user.getEmailUser());
                    passwordUserField.setText(user.getPassUser());
                    agePersonField.setText(Integer.toString(person.getAgePerson()));
                    firstNamePersonField.setText(person.getFirstNamePerson());
                    middleNamePersonField.setText(person.getMiddleNamePerson());
                    lastNamePersonField.setText(person.getLastNamePerson());
                    nationalityPersonCombobox.setValue(person.getNationalityPerson());
                    pointsPersonField.setText(Integer.toString(person.getPointsPerson()));
                    roleCombobox.setValue(user.getTypeUser());


                    if (!(Objects.isNull(person) || person.getCarPerson() == 0)) {
                        carPersonCombobox.setValue(CarService.getCarName(CarService.getCar(person.getCarPerson())));
                    }

                        userImageView.setImage(PersonService.getImagePerson(PersonService.getPerson(user.getUserHasPerson())));

                    pointsPersonField.setEditable(false);
                    roleCombobox.setDisable(true);

                }
            } else {
                roleCombobox.setValue("User");
                pointsPersonField.setText("0");
                agePersonField.setText("18");
                applyChangeButton.setText("Добави");
                labelUserName.setText("Добавяне на състезател");
                            }
        });

    }

}
