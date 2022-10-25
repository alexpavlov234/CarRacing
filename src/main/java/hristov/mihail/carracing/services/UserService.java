package hristov.mihail.carracing.services;

import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    public static void addUser(User user) {
        Database.execute("INSERT INTO User (emailUser, passUser, typeUser, userHasPerson) VALUES ('" + user.getEmailUser() + "'," + user.getPassUser() + ",'" + user.getTypeUser() + "', " + user.getUserHasPerson() + ");");
    }

    public static User getUser(int idUser) {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM user WHERE (idUser == " + idUser + ");");
        //INSERT INTO User (nameUser, lengthUser, locationUser) VALUES ('Monte Carlo',456,'Dupnica');
        User user = null;
        try {
            user = new User(Integer.parseInt(resultSet.getString("idUser")), resultSet.getString("emailUser"), resultSet.getString("passUser"), resultSet.getString("typeUser"), Integer.parseInt(resultSet.getString("userHasPerson")));
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return user;
    }

    public static void updateUser(User user) {
        //'
        Database.execute("UPDATE user SET emailUser = '" + user.getEmailUser() + "', passUser = '" + user.getPassUser() + "', typeUser =' " + user.getTypeUser() + "', userHasPerson = " + user.getUserHasPerson() + "  WHERE idUser =" + user.getIdUser() + ";");
        //INSERT INTO User (nameUser, lengthUser, locationUser) VALUES ('Monte Carlo',456,'Dupnica');
    }

    public static void deleteUser(int idUser) {
        Database.execute("DELETE FROM user WHERE idUser = " + idUser + ";");
        // DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
    }

    public static ArrayList<User> getAllUser() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM user;");

        ArrayList<User> allUsers = new ArrayList<>();
        try {
            while ((resultSet.next())) {
                User user = new User(Integer.parseInt(resultSet.getString("idUser")), resultSet.getString("emailUser"), resultSet.getString("passUser"), resultSet.getString("typeUser"), Integer.parseInt(resultSet.getString("userHasPerson")));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return allUsers;
    }
}