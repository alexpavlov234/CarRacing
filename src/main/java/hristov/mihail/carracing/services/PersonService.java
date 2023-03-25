package hristov.mihail.carracing.services;

import hristov.mihail.carracing.controllers.MessageType;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.models.User;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonService {
    public static void addPerson(Person person) {
        String query = "INSERT INTO carracers.person (firstNamePerson, middleNamePerson, lastNamePerson, agePerson, nationalityPerson, pointsPerson, carPerson, imagePerson) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setString(1, person.getFirstNamePerson());
            statement.setString(2, person.getMiddleNamePerson());
            statement.setString(3, person.getLastNamePerson());
            statement.setInt(4, person.getAgePerson());
            statement.setString(5, person.getNationalityPerson());
            statement.setInt(6, person.getPointsPerson());
            statement.setInt(7, person.getCarPerson());
            statement.setString(8, person.getImagePathPerson());
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при добавяне на човек!", "Грешка", MessageType.WARNING);
        }
    }

    public static void addPerson(String firstNamePerson, String lastNamePerson) {
        String query = "INSERT INTO carracers.person (firstNamePerson, lastNamePerson) VALUES (?, ?)";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setString(1, firstNamePerson);
            statement.setString(2, lastNamePerson);
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при добавяне на човек!", "Грешка", MessageType.WARNING);
        }
    }

    public static Person getPerson(int idPerson) {
        String sql = "SELECT * FROM carracers.person WHERE idPerson=?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idPerson);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Person(
                        resultSet.getInt("idPerson"),
                        resultSet.getString("firstNamePerson"),
                        resultSet.getString("middleNamePerson"),
                        resultSet.getString("lastNamePerson"),
                        resultSet.getInt("agePerson"),
                        resultSet.getString("nationalityPerson"),
                        resultSet.getInt("pointsPerson"),
                        resultSet.getInt("carPerson"),
                        resultSet.getString("imagePerson")
                );
            } else {
                WarningController.openMessageModal("Не е намерен такъв човек!", "Липсващ човек", MessageType.WARNING);
                return null; // or throw an exception if you prefer
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Не е намерен такъв човек!", "Липсващ човек", MessageType.WARNING);
            return null;
        }
    }


    public static Image getImagePerson(Person person) {
        try (PreparedStatement retrieve = Database.getConnection().prepareStatement("SELECT imagePerson FROM carracers.person WHERE (idPerson = " + person.getIdPerson() + ");")) {
            //retrieve.setInt(1, 1);
            ResultSet resultSet = retrieve.executeQuery();
            resultSet.next();
            Blob blob = resultSet.getBlob(1);
            if (blob != null) {
                InputStream inputStream = blob.getBinaryStream();
                Image image = new Image(inputStream);
                return image;
            } else {
                return null;
            }

        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при зареждане на снимката на човека!", "Грешка", MessageType.WARNING);
            return null;
        }

    }

    public static void setImagePerson(File file, Person person) {
        try (PreparedStatement store = Database.getConnection().prepareStatement("UPDATE carracers.person SET imagePerson = ?  WHERE idPerson = ?;")) {
            // Задаване на снимката на колата
            FileInputStream fileInputStream = new FileInputStream(file);
            store.setBinaryStream(1, fileInputStream, fileInputStream.available());
            store.setInt(2, person.getIdPerson());
            store.execute();
        } catch (SQLException e) {
            // Показване на предупреждение при грешка в базата данни
            WarningController.openMessageModal("Грешка при задаване на снимката на човека! Грешка в базата данни!", "Грешка", MessageType.WARNING);
        } catch (FileNotFoundException e) {
            // Показване на предупреждение при грешка при намирането на файла
            WarningController.openMessageModal("Грешка при задаване на снимката на човека! Файлът не е намерен!", "Грешка", MessageType.WARNING);
        } catch (Exception e) {
            // Показване на предупреждение при друг вид грешка
            WarningController.openMessageModal("Грешка при задаване на снимката на човека!", "Грешка", MessageType.WARNING);
        }
    }

    public static Person getLastPerson() {
        String sql = "SELECT * FROM carracers.person ORDER BY idPerson DESC LIMIT 1;";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Person(resultSet.getInt("idPerson"), resultSet.getString("firstNamePerson"), resultSet.getString("middleNamePerson"), resultSet.getString("lastNamePerson"), resultSet.getInt("agePerson"), resultSet.getString("nationalityPerson"), resultSet.getInt("pointsPerson"), resultSet.getInt("carPerson"), resultSet.getString("imagePerson"));
            } else {
                WarningController.openMessageModal("Няма намерени резултати!", "Липсваща информация", MessageType.WARNING);
                return null; // or throw an exception if you prefer
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Възникна грешка при извличане на информацията!", "Грешка", MessageType.WARNING);
            return null;
        }
    }

    public static void updatePerson(Person person) {
        String sql = "UPDATE carracers.person SET firstNamePerson=?, middleNamePerson=?, lastNamePerson=?, agePerson=?, nationalityPerson=?, pointsPerson=?, carPerson=? WHERE idPerson=?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setString(1, person.getFirstNamePerson());
            statement.setString(2, person.getMiddleNamePerson());
            statement.setString(3, person.getLastNamePerson());
            statement.setInt(4, person.getAgePerson());
            statement.setString(5, person.getNationalityPerson());
            statement.setInt(6, person.getPointsPerson());
            statement.setInt(7, person.getCarPerson());
            statement.setInt(8, person.getIdPerson());
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при обновяване на човек!", "Грешка", MessageType.WARNING);
        }
    }

    public static void deletePerson(int idPerson) {
        String sql1 = "DELETE FROM carracers.person WHERE idPerson=?";
        String sql2 = "DELETE FROM carracers.race_has_car_and_driver WHERE carracers.race_has_car_and_driver.idDriver=?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql1)) {
            statement.setInt(1, idPerson);
            statement.executeUpdate();

        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при изтриване на човек!", "Грешка", MessageType.WARNING);
        }
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql2)) {
            statement.setInt(1, idPerson);
            statement.executeUpdate();

        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при изтриване на човек!", "Грешка", MessageType.WARNING);
        }
    }

    public static void setCarPerson(Car car, Person person) {
        String sql = "UPDATE carracers.person SET carPerson=? WHERE idPerson=?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, car.getIdCar());
            statement.setInt(2, person.getIdPerson());
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при добавяне на кола на човек!", "Грешка", MessageType.WARNING);
        }
    }


    public static ArrayList<Person> getAllPerson() {
        ArrayList<Person> allPeople = new ArrayList<>();
        String sql = "SELECT * FROM carracers.person";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Person person = new Person(resultSet.getInt("idPerson"), resultSet.getString("firstNamePerson"), resultSet.getString("middleNamePerson"), resultSet.getString("lastNamePerson"), resultSet.getInt("agePerson"), resultSet.getString("nationalityPerson"), resultSet.getInt("pointsPerson"), resultSet.getInt("carPerson"), resultSet.getString("imagePerson"));
                User user = UserService.getUser(person);
                if (user != null && !user.getTypeUser().equals("Admin")) {
                    allPeople.add(person);
                }

            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Възникна грешка при извличането на всички хора!", "Грешка", MessageType.WARNING);
        }
        return allPeople;
    }


    public static ArrayList<String> getAllPersonNames() {
        ArrayList<String> allPeople = new ArrayList<>();
        try (PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM carracers.person;")) {
            ResultSet resultSet = statement.executeQuery();

            while ((resultSet.next())) {
                Person person = new Person(
                        resultSet.getString("idPerson") == null ? 0 : Integer.parseInt(resultSet.getString("idPerson")),
                        resultSet.getString("firstNamePerson"),
                        resultSet.getString("middleNamePerson"),
                        resultSet.getString("lastNamePerson"),
                        resultSet.getString("agePerson") == null ? 0 : Integer.parseInt(resultSet.getString("agePerson")),
                        resultSet.getString("nationalityPerson"),
                        resultSet.getString("pointsPerson") == null ? 0 : Integer.parseInt(resultSet.getString("pointsPerson")),
                        resultSet.getString("carPerson") == null ? 0 : Integer.parseInt(resultSet.getString("carPerson")),
                        resultSet.getString("imagePerson")
                );

                User user = UserService.getUser(person);
                if (user != null && !user.getTypeUser().equals("Admin")) {
                    allPeople.add(resultSet.getString("firstNamePerson") + " " + resultSet.getString("lastNamePerson"));
                }
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Възникна грешка при извличането на всички хора!", "Грешка", MessageType.WARNING);
        }
        return allPeople;
    }


    public static Person getPerson(String name) {
        String[] names = name.split(" ");
        if (names.length == 2) {
            String sql = "SELECT * FROM carracers.person WHERE (firstNamePerson = ? AND lastNamePerson = ?)";
            try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
                statement.setString(1, names[0]);
                statement.setString(2, names[1]);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return new Person(resultSet.getInt("idPerson"), resultSet.getString("firstNamePerson"), resultSet.getString("middleNamePerson"), resultSet.getString("lastNamePerson"), resultSet.getInt("agePerson"), resultSet.getString("nationalityPerson"), resultSet.getInt("pointsPerson"), resultSet.getInt("carPerson"), resultSet.getString("imagePerson"));
                } else {
                    WarningController.openMessageModal("Не е намерен такъв състезател!", "Лиспващ състезател", MessageType.WARNING);
                    return null;
                }
            } catch (SQLException e) {
                WarningController.openMessageModal("Не е намерен такъв състезател!", "Лиспващ състезател", MessageType.WARNING);
                return null;
            }
        } else {
            WarningController.openMessageModal("Не е намерен такъв състезател!", "Лиспващ състезател", MessageType.WARNING);
            return null;
        }

    }

}
