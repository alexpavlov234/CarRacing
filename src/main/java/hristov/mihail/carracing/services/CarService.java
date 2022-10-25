package hristov.mihail.carracing.services;

import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarService {
    public static void addCar(Car car) {
        Database.execute("INSERT INTO Car (modelCar, brandCar, engineCar, fuelCar, horsepowerCar, imagePathCar) VALUES ('" + car.getModelCar() + "','" + car.getBrandCar() + "','" + car.getEngineCar() + "','" + car.getFuelCar() + "'," + car.getHorsepowerCar() + ",'" + car.getImagePathCar() + "');");
        //INSERT INTO Car (nameCar, lengthCar, locationCar) VALUES ('Monte Carlo',456,'Dupnica');
    }

    public static Car getCar(int idCar) {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM car WHERE (idCar == " + idCar + ");");
        //INSERT INTO Car (nameCar, lengthCar, locationCar) VALUES ('Monte Carlo',456,'Dupnica');
        Car car = null;
        try {
            car = new Car(Integer.parseInt(resultSet.getString("idCar")), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), Integer.parseInt(resultSet.getString("horsepowerCar")), resultSet.getString("imagePathCar"));
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return car;
    }

    public static void updateCar(Car car) {
        //'
        Database.execute("UPDATE car SET modelCar = '" + car.getModelCar() + "', brandCar = '" + car.getBrandCar() + "', engineCar = '" + car.getEngineCar() + "', fuelCar = '" + car.getFuelCar() + "', horsepowerCar = " + car.getHorsepowerCar() + "  WHERE idCar =" + car.getIdCar() + ";");
        //INSERT INTO Car (nameCar, lengthCar, locationCar) VALUES ('Monte Carlo',456,'Dupnica');
    }

    public static void deleteCar(int idCar) {
        Database.execute("DELETE FROM car WHERE idCar = " + idCar + ";");
        // DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
    }

    public static ArrayList<Car> getAllCar() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM car;");

        ArrayList<Car> allCars = new ArrayList<>();
        try {
            while ((resultSet.next())) {
                Car car = new Car(Integer.parseInt(resultSet.getString("idCar")), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), Integer.parseInt(resultSet.getString("horsepowerCar")), resultSet.getString("imagePathCar"));
                allCars.add(car);
            }
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return allCars;
    }
}
