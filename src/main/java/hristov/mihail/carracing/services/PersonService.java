package hristov.mihail.carracing.services;

import hristov.mihail.carracing.controllers.MessageType;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.models.User;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonService {
    public static void addPerson(Person person) {
        Database.execute("INSERT INTO person (firstNamePerson, middleNamePerson, lastNamePerson, agePerson, nationalityPerson, pointsPerson, carPerson, imagePerson) VALUES ('" + person.getFirstNamePerson() + "','" + person.getMiddleNamePerson() + "','" + person.getLastNamePerson() + "'," + person.getAgePerson() + ",'" + person.getNationalityPerson() + "'," + person.getPointsPerson() + "," + person.getCarPerson() + ",'" + person.getImagePathPerson() + "');");
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
            person = new Person(resultSet.getString("idPerson") == null ? 0 : Integer.parseInt(resultSet.getString("idPerson")), resultSet.getString("firstNamePerson"), resultSet.getString("middleNamePerson"), resultSet.getString("lastNamePerson"), resultSet.getString("agePerson") == null ? 0 : Integer.parseInt(resultSet.getString("agePerson")), resultSet.getString("nationalityPerson"), resultSet.getString("pointsPerson") == null ? 0 : Integer.parseInt(resultSet.getString("pointsPerson")), resultSet.getString("carPerson") == null ? 0 : Integer.parseInt(resultSet.getString("carPerson")),resultSet.getString("imagePerson"));
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO: Екран за грешка
        }
        return person;
    }

    public static Image getImagePerson(Person person) {
        try {
            PreparedStatement retrieve = Database.getConnection().prepareStatement("SELECT imagePerson FROM person WHERE (idPerson = " + person.getIdPerson() + ");");
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
            throw new RuntimeException(e);
        }

    }

    public static PreparedStatement setImagePerson() {
        try {
            PreparedStatement store = Database.getConnection().prepareStatement("UPDATE person SET imagePerson = ?  WHERE idPerson = ?;");
            return store;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Person getLastPerson() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM person ORDER BY idPerson DESC LIMIT 1;");

        Person person = null;
        try {
            resultSet.next();
            //TODO: Проверка за null стойности
            person = new Person(resultSet.getString("idPerson") == null ? 0 : Integer.parseInt(resultSet.getString("idPerson")), resultSet.getString("firstNamePerson"), resultSet.getString("middleNamePerson"), resultSet.getString("lastNamePerson"), resultSet.getString("agePerson") == null ? 0 : Integer.parseInt(resultSet.getString("agePerson")), resultSet.getString("nationalityPerson"), resultSet.getString("pointsPerson") == null ? 0 : Integer.parseInt(resultSet.getString("pointsPerson")),  resultSet.getString("carPerson") == null ? 0 : Integer.parseInt(resultSet.getString("carPerson")),resultSet.getString("imagePerson"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //TODO: Екран за грешка
        }
        return person;
    }

    public static void updatePerson(Person person) {
        //'
        Database.execute("UPDATE person SET firstNamePerson = '" + person.getFirstNamePerson() + "', middleNamePerson = '" + person.getMiddleNamePerson() + "', lastNamePerson = '" + person.getLastNamePerson() + "', agePerson = " + person.getAgePerson() + ", nationalityPerson = '" + person.getNationalityPerson() + "', pointsPerson = " + person.getPointsPerson() + ", carPerson = " + person.getCarPerson() + "  WHERE idPerson =" + person.getIdPerson() + ";");
        //INSERT INTO Person (namePerson, lengthPerson, locationPerson) VALUES ('Monte Personlo',456,'Dupnica');
    }

    public static void deletePerson(int idPerson) {
        Database.execute("DELETE FROM person WHERE idPerson = " + idPerson + ";");
        // DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
    }
    public static void setCarPerson(Car car, Person person){
        Database.execute("UPDATE person SET  carPerson = " + car.getIdCar() + "  WHERE idPerson =" + person.getIdPerson() + ";");
        //INSERT INTO Person (namePerson, lengthPerson, locationPerson) VALUES ('Monte Personlo',456,'Dupnica');
    }

    public static ArrayList<Person> getAllPerson() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM person;");

        ArrayList<Person> allPersons = new ArrayList<>();
        try {
            while ((resultSet.next())) {
                Person person = new Person(resultSet.getString("idPerson") == null ? 0 : Integer.parseInt(resultSet.getString("idPerson")), resultSet.getString("firstNamePerson"), resultSet.getString("middleNamePerson"), resultSet.getString("lastNamePerson"), resultSet.getString("agePerson") == null ? 0 : Integer.parseInt(resultSet.getString("agePerson")), resultSet.getString("nationalityPerson"), resultSet.getString("pointsPerson") == null ? 0 : Integer.parseInt(resultSet.getString("pointsPerson")),  resultSet.getString("carPerson") == null ? 0 : Integer.parseInt(resultSet.getString("carPerson")),resultSet.getString("imagePerson"));
                allPersons.add(person);
            }
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return allPersons;
    }

    public static ArrayList<String> getAllPersonNames() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM person;");

        ArrayList<String> allPeople = new ArrayList<>();
        try {
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
            //TODO: Handle the error
        }
        return allPeople;
    }


    public static Person getPerson(String name) {
        String[] names = name.split(" ");
        if (names.length == 2) {
            ResultSet resultSet = Database.executeQuery("SELECT * FROM person WHERE (firstNamePerson = '" + names[0] + "' AND lastNamePerson = '" + names[1] + "');");
           //INSERT INTO Car (nameCar, lengthCar, locationCar) VALUES ('Monte Carlo',456,'Dupnica');
            Person person = null;
            try {
                resultSet.next();
//            byte[] byteData = resultSet.getString("imageCar").getBytes(StandardCharsets.UTF_8);//Better to specify encoding
//            Blob blobData = Database.createBlob();
//            blobData.setBytes(1, byteData);
                //  car = new Car(Integer.parseInt(resultSet.getString("idCar")), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), Integer.parseInt(resultSet.getString("horsepowerCar")), blobData);
                person = new Person(resultSet.getString("idPerson") == null ? 0 : Integer.parseInt(resultSet.getString("idPerson")), resultSet.getString("firstNamePerson"), resultSet.getString("middleNamePerson"), resultSet.getString("lastNamePerson"), resultSet.getString("agePerson") == null ? 0 : Integer.parseInt(resultSet.getString("agePerson")), resultSet.getString("nationalityPerson"), resultSet.getString("pointsPerson") == null ? 0 : Integer.parseInt(resultSet.getString("pointsPerson")), resultSet.getString("carPerson") == null ? 0 : Integer.parseInt(resultSet.getString("carPerson")),resultSet.getString("imagePerson"));
            } catch (SQLException e) {
                //TODO: Екран за грешка
            }
            return person;
        } else {
            WarningController.openMessageModal("Не е намерен такъв състезател!", "Лиспващ състезател", MessageType.WARNING);
            return null;
        }

    }
}
