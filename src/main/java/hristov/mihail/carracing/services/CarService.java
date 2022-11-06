package hristov.mihail.carracing.services;

import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Car;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarService {
    public static void addCar(Car car) {
        Database.execute("INSERT INTO Car (modelCar, brandCar, engineCar, fuelCar, horsepowerCar) VALUES ('" + car.getModelCar() + "','" + car.getBrandCar() + "','" + car.getEngineCar() + "','" + car.getFuelCar() + "'," + car.getHorsepowerCar() + ");");
        //INSERT INTO Car (nameCar, lengthCar, locationCar) VALUES ('Monte Carlo',456,'Dupnica');
    }

    public static Car getCar(int idCar) {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM car WHERE (idCar = " + idCar + ");");
        //INSERT INTO Car (nameCar, lengthCar, locationCar) VALUES ('Monte Carlo',456,'Dupnica');
        Car car = null;
        try {
//            byte[] byteData = resultSet.getString("imageCar").getBytes(StandardCharsets.UTF_8);//Better to specify encoding
//            Blob blobData = Database.createBlob();
//            blobData.setBytes(1, byteData);
            //  car = new Car(Integer.parseInt(resultSet.getString("idCar")), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), Integer.parseInt(resultSet.getString("horsepowerCar")), blobData);
            car = new Car(Integer.parseInt(resultSet.getString("idCar")), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), Integer.parseInt(resultSet.getString("horsepowerCar")));
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return car;
    }

    public static Car getLastCar() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM car ORDER BY idCar DESC LIMIT 1;");

        Car car = null;
        try {
            resultSet.next();
            //TODO: Проверка за null стойности
            car = new Car(Integer.parseInt(resultSet.getString("idCar")), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), Integer.parseInt(resultSet.getString("horsepowerCar")));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

    public static Image getImageCar(Car car) {
        try {
            PreparedStatement retrieve = Database.getConnection().prepareStatement("SELECT imageCar FROM car WHERE (idCar = " + car.getIdCar() + ");");
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

    public static PreparedStatement setImageCar() {
        try {
            PreparedStatement store = Database.getConnection().prepareStatement("UPDATE car SET imageCar = ?  WHERE idCar = ?;");
            return store;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static ArrayList<Car> getAllCar() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM car;");

        ArrayList<Car> allCars = new ArrayList<>();
        try {
            while ((resultSet.next())) {
//                byte[] byteData = resultSet.getString("imageCar").getBytes(StandardCharsets.UTF_8);//Better to specify encoding
//                Blob blobData = Database.createBlob();
//                blobData.setBytes(1, byteData);
                //  Car car = new Car(Integer.parseInt(resultSet.getString("idCar")), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), Integer.parseInt(resultSet.getString("horsepowerCar")), blobData);
                Car car = new Car(Integer.parseInt(resultSet.getString("idCar")), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), Integer.parseInt(resultSet.getString("horsepowerCar")));
                allCars.add(car);
            }
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return allCars;
    }
}
