package hristov.mihail.carracing.services;

import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonService {
    //    public static void addPerson(Person person) {
//        Database.execute("INSERT INTO Person (modelPerson, brandPerson, enginePerson, fuelPerson, horsepowerPerson, imagePathPerson) VALUES ('" + person.getModelPerson() + "','" + person.getBrandPerson() + "','" + person.getEnginePerson() + "'," + person.getFuelPerson() + "," + person.getHorsepowerPerson() + "'," + person.getImagePathPerson() +");");
//        //INSERT INTO Person (namePerson, lengthPerson, locationPerson) VALUES ('Monte Personlo',456,'Dupnica');
//    }
    public static void addPerson(String firstNamePerson, String lastNamePerson) {
        Database.execute("INSERT INTO Person (firstNamePerson, lastNamePerson) VALUES ('" + firstNamePerson + "','" + lastNamePerson + "');");
    }
//
//    public static Person getPerson(int idPerson) {
//        ResultSet resultSet = Database.executeQuery("SELECT * FROM person WHERE (idPerson == " + idPerson + ");");
//        //INSERT INTO Person (namePerson, lengthPerson, locationPerson) VALUES ('Monte Personlo',456,'Dupnica');
//        Person person = null;
//        try {
//            person = new Person(Integer.parseInt(resultSet.getString("idPerson")), resultSet.getString("modelPerson"), resultSet.getString("brandPerson"), resultSet.getString("enginePerson"),resultSet.getString("fuelPerson"), Integer.parseInt(resultSet.getString("horsepowerPerson")), resultSet.getString("imagePathPerson"));
//        } catch (SQLException e) {
//            //TODO: Екран за грешка
//        }
//        return person;
//    }
//
//    public static void updatePerson(Person person) {
//        //'
//        Database.execute("UPDATE person SET modelPerson = '" + person.getModelPerson() + "', brandPerson = '" + person.getBrandPerson() + "', enginePerson = '" + person.getEnginePerson() + "', fuelPerson = '" + person.getFuelPerson() + "', horsepowerPerson = " + person.getHorsepowerPerson() +"  WHERE idPerson =" + person.getIdPerson() + ";");
//        //INSERT INTO Person (namePerson, lengthPerson, locationPerson) VALUES ('Monte Personlo',456,'Dupnica');
//    }
//
//    public static void deletePerson(int idPerson) {
//        Database.execute("DELETE FROM person WHERE idPerson = " + idPerson + ";");
//        // DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
//    }
//
//    public static ArrayList<Person> getAllPerson() {
//        ResultSet resultSet = Database.executeQuery("SELECT * FROM person;");
//
//        ArrayList<Person> allPersons = new ArrayList<>();
//        try {
//            while ((resultSet.next())) {
//                Person person = new Person(Integer.parseInt(resultSet.getString("idPerson")), resultSet.getString("modelPerson"), resultSet.getString("brandPerson"), resultSet.getString("enginePerson"), resultSet.getString("fuelPerson"), Integer.parseInt(resultSet.getString("horsepowerPerson")),resultSet.getString("imagePathPerson"));
//                allPersons.add(person);
//            }
//        } catch (SQLException e) {
//            //TODO: Екран за грешка
//        }
//        return allPersons;
//    }
}
