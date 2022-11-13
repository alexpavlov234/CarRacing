package hristov.mihail.carracing.services;

import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonService {
    public static void addPerson(Person person) {
        Database.execute("INSERT INTO person (firstNamePerson, middleNamePerson, lastNamePerson, agePerson, nationalityPerson, pointsPerson, imagePerson) VALUES ('" + person.getFirstNamePerson() + "','" + person.getMiddleNamePerson() + "','" + person.getLastNamePerson() + "'," + person.getAgePerson() + ",'" + person.getNationalityPerson() + "'," + person.getPointsPerson() + ",'" + person.getImagePathPerson() + "');");
        //INSERT INTO Person (namePerson, lengthPerson, locationPerson) VALUES ('Monte Personlo',456,'Dupnica');
    }

    public static void addPerson(String firstNamePerson, String lastNamePerson) {
        Database.execute("INSERT INTO person (firstNamePerson, lastNamePerson) VALUES ('" + firstNamePerson + "','" + lastNamePerson + "');");
    }

    public static Person getPerson(int idPerson) {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM person WHERE (idPerson = " + idPerson + ");");
        //INSERT INTO Person (namePerson, lengthPerson, locationPerson) VALUES ('Monte Personlo',456,'Dupnica');
        Person person = null;

        try {
            resultSet.next();
            person = new Person(resultSet.getString("idPerson") == null ? 0 : Integer.parseInt(resultSet.getString("idPerson")), resultSet.getString("firstNamePerson"), resultSet.getString("middleNamePerson"), resultSet.getString("lastNamePerson"), resultSet.getString("agePerson") == null ? 0 : Integer.parseInt(resultSet.getString("agePerson")), resultSet.getString("nationalityPerson"), resultSet.getString("pointsPerson") == null ? 0 : Integer.parseInt(resultSet.getString("pointsPerson")), resultSet.getString("imagePerson"));
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO: Екран за грешка
        }
        return person;
    }

    public static Person getLastPerson() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM person ORDER BY idPerson DESC LIMIT 1;");

        Person person = null;
        try {
            resultSet.next();
            //TODO: Проверка за null стойности
            person = new Person(resultSet.getString("idPerson") == null ? 0 : Integer.parseInt(resultSet.getString("idPerson")), resultSet.getString("firstNamePerson"), resultSet.getString("middleNamePerson"), resultSet.getString("lastNamePerson"), resultSet.getString("agePerson") == null ? 0 : Integer.parseInt(resultSet.getString("agePerson")), resultSet.getString("nationalityPerson"), resultSet.getString("pointsPerson") == null ? 0 : Integer.parseInt(resultSet.getString("pointsPerson")), resultSet.getString("imagePerson"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //TODO: Екран за грешка
        }
        return person;
    }

    public static void updatePerson(Person person) {
        //'
        Database.execute("UPDATE person SET firstNamePerson = '" + person.getFirstNamePerson() + "', middleNamePerson = '" + person.getMiddleNamePerson() + "', lastNamePerson = '" + person.getLastNamePerson() + "', agePerson = " + person.getAgePerson() + ", nationalityPerson = '" + person.getNationalityPerson() + "', pointsPerson = " + person.getPointsPerson() + "  WHERE idPerson =" + person.getIdPerson() + ";");
        //INSERT INTO Person (namePerson, lengthPerson, locationPerson) VALUES ('Monte Personlo',456,'Dupnica');
    }

    public static void deletePerson(int idPerson) {
        Database.execute("DELETE FROM person WHERE idPerson = " + idPerson + ";");
        // DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
    }

    public static ArrayList<Person> getAllPerson() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM person;");

        ArrayList<Person> allPersons = new ArrayList<>();
        try {
            while ((resultSet.next())) {
                Person person = new Person(Integer.parseInt(resultSet.getString("idPerson")), resultSet.getString("firstNamePerson"), resultSet.getString("middleNamePerson"), resultSet.getString("lastNamePerson"), Integer.parseInt(resultSet.getString("agePerson")), resultSet.getString("nationalityPerson"), Integer.parseInt(resultSet.getString("pointsPerson")), resultSet.getString("imagePerson"));
                allPersons.add(person);
            }
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return allPersons;
    }
}
