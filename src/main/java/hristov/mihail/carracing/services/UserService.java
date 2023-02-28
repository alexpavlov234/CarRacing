package hristov.mihail.carracing.services;

import hristov.mihail.carracing.controllers.MessageType;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.models.User;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    public static void addUser(User user) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("INSERT INTO user (emailUser, passUser, typeUser, userHasPerson) VALUES (?, ?, ?, ?)");
            statement.setString(1, user.getEmailUser());
            statement.setString(2, user.getPassUser());
            statement.setString(3, user.getTypeUser());
            statement.setInt(4, user.getUserHasPerson());
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при добавяне на потребител!", "Грешка", MessageType.WARNING);
        }
    }

    public static User getUser(int idUser) {
        User user = null;
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM user WHERE idUser = ?");
            statement.setInt(1, idUser);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt("idUser"), resultSet.getString("emailUser"), resultSet.getString("passUser"), resultSet.getString("typeUser"), resultSet.getInt("userHasPerson"));
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Не е намерен такъв потребител!", "Липсващ потребител", MessageType.WARNING);
        }
        return user;
    }

    public static User getLastUser() {
        User user = null;
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM user ORDER BY idUser DESC LIMIT 1");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt("idUser"), resultSet.getString("emailUser"), resultSet.getString("passUser"), resultSet.getString("typeUser"), resultSet.getInt("userHasPerson"));
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Не е намерен такъв потребител!", "Липсващ потребител", MessageType.WARNING);
        }
        return user;
    }

    public static User getUser(String emailUser) {
        User user = null;
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM user WHERE emailUser = ?");
            statement.setString(1, emailUser);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt("idUser"), resultSet.getString("emailUser"), resultSet.getString("passUser"), resultSet.getString("typeUser"), resultSet.getInt("userHasPerson"));
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Не е намерен такъв потребител!", "Липсващ потребител", MessageType.WARNING);
        }
        return user;
    }

    public static User getUser(Person person) {
        User user = null;
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM user WHERE userHasPerson = ? LIMIT 1");
            statement.setInt(1, person.getIdPerson());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt("idUser"), resultSet.getString("emailUser"), resultSet.getString("passUser"), resultSet.getString("typeUser"), resultSet.getInt("userHasPerson"));
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Не е намерен такъв потребител!", "Липсващ потребител", MessageType.WARNING);
        }
        return user;
    }

    public static void updateUser(User user) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("UPDATE user SET emailUser = ?, passUser = ?, typeUser = ?, userHasPerson = ? WHERE idUser = ?");
            statement.setString(1, user.getEmailUser());
            statement.setString(2, user.getPassUser());
            statement.setString(3, user.getTypeUser());
            statement.setInt(4, user.getUserHasPerson());
            statement.setInt(5, user.getIdUser());
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при обновяване на потребител!", "Грешка", MessageType.WARNING);
        }
    }

    public static void deleteUser(int idUser) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("DELETE FROM user WHERE idUser = ?");
            statement.setInt(1, idUser);
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при изтриване на потребител!", "Грешка", MessageType.WARNING);
        }
    }
    public static ArrayList<User> getAllUser() {
        ArrayList<User> allUsers = new ArrayList<>();

        try (PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM user");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("idUser");
                String email = resultSet.getString("emailUser");
                String password = resultSet.getString("passUser");
                String type = resultSet.getString("typeUser");
                int personId = resultSet.getInt("userHasPerson");

                User user = new User(id, email, password, type, personId);
                allUsers.add(user);
            }

        } catch (SQLException e) {
            WarningController.openMessageModal("Възникна грешка при извличането на всички потребители!", "Грешка", MessageType.WARNING);
        }

        return allUsers;
    }

}
