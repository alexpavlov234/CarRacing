package hristov.mihail.carracing.services;

import hristov.mihail.carracing.controllers.MessageType;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.models.Person;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public class CarService {
    public static void addCar(Car car) {
        String sql = "INSERT INTO car (modelCar, brandCar, engineCar, fuelCar, horsepowerCar) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, car.getModelCar());
            pstmt.setString(2, car.getBrandCar());
            pstmt.setString(3, car.getEngineCar());
            pstmt.setString(4, car.getFuelCar());
            pstmt.setInt(5, car.getHorsepowerCar());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при добавянето на кола в базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }


    public static String getCarName(Car car) {
        return car.getBrandCar() + " " + car.getModelCar();
    }

    public static Car getCar(int idCar) {
        String sql = "SELECT * FROM car WHERE idCar=?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCar);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new Car(resultSet.getInt("idCar"), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), resultSet.getInt("horsepowerCar"));
            } else {
                WarningController.openMessageModal("Не е намерена такава кола!", "Лиспваща кола", MessageType.WARNING);
                return null; // or throw an exception if you prefer
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Не е намерена такава кола!", "Лиспваща кола", MessageType.WARNING);
            return null;
        }
    }


    public static Car getCar(String name) {
        int firstSpaceIndex = name.indexOf(" ");
        String[] names = {name.substring(0, firstSpaceIndex), name.substring(firstSpaceIndex + 1)};
        if (names.length == 2) {
            String sql = "SELECT * FROM car WHERE modelCar=? AND brandCar=?";
            try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, names[1]);
                pstmt.setString(2, names[0]);
                ResultSet resultSet = pstmt.executeQuery();
                if (resultSet.next()) {
                    return new Car(resultSet.getInt("idCar"), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), resultSet.getInt("horsepowerCar"));
                } else {
                    WarningController.openMessageModal("Не е намерена такава кола!", "Лиспваща кола", MessageType.WARNING);
                    return null;
                }
            } catch (SQLException e) {
                WarningController.openMessageModal("Грешка при търсенето на колата!", "Грешка", MessageType.WARNING);
                return null;
            }
        } else {
            WarningController.openMessageModal("Не е намерена такава кола!", "Лиспваща кола", MessageType.WARNING);
            return null;
        }
    }


    public static Car getLastCar() {
        String sql = "SELECT * FROM car ORDER BY idCar DESC LIMIT 1";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet resultSet = pstmt.executeQuery()) {
            if (resultSet.next()) {
                return new Car(resultSet.getInt("idCar"), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), resultSet.getInt("horsepowerCar"));
            } else {
                WarningController.openMessageModal("Не е намерена такава кола!", "Лиспваща кола", MessageType.WARNING);
                return null;
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при взимане на последната кола от базата данни!", "Неуспешна операция", MessageType.WARNING);
            return null;
        }
    }


    public static void updateCar(Car car) {
        String sql = "UPDATE car SET modelCar=?, brandCar=?, engineCar=?, fuelCar=?, horsepowerCar=? WHERE idCar=?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, car.getModelCar());
            pstmt.setString(2, car.getBrandCar());
            pstmt.setString(3, car.getEngineCar());
            pstmt.setString(4, car.getFuelCar());
            pstmt.setInt(5, car.getHorsepowerCar());
            pstmt.setInt(6, car.getIdCar());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при обновяването на колата в базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }

    public static void deleteCar(int idCar) {
        String sql = "DELETE FROM car WHERE idCar=?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCar);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при изтриването на колата от базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }

    public static Image getImageCar(Car car) {
        try {
            PreparedStatement retrieve = Database.getConnection().prepareStatement("SELECT imageCar FROM car WHERE (idCar = " + car.getIdCar() + ");");
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
            WarningController.openMessageModal("Грешка при зареждане на снимката на колата!", "Грешка", MessageType.WARNING);
            return null;
        }
    }

    public static PreparedStatement setImageCar() {
        try {
            PreparedStatement store = Database.getConnection().prepareStatement("UPDATE car SET imageCar = ?  WHERE idCar = ?;");
            return store;
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при задаване на снимката на колата!", "Грешка", MessageType.WARNING);
            return null;
        }

    }

    public static ArrayList<Car> getAllCar() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM car;");

        ArrayList<Car> allCars = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("idCar");
                String model = resultSet.getString("modelCar");
                String brand = resultSet.getString("brandCar");
                String engine = resultSet.getString("engineCar");
                String fuel = resultSet.getString("fuelCar");
                int horsepower = resultSet.getInt("horsepowerCar");
                Car car = new Car(id, model, brand, engine, fuel, horsepower);
                allCars.add(car);
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Възникна грешка при извличането на всички коли", "Грешка", MessageType.WARNING);
        }
        return allCars;
    }


    public static ArrayList<String> getAllCarNames() {
        String sql = "SELECT CONCAT(brandCar, ' ', modelCar) AS carName FROM car;";
        ArrayList<String> allCars = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                allCars.add(resultSet.getString("carName"));
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при извличане на имената на колите!", "Грешка", MessageType.WARNING);
        }
        return allCars;
    }

}
