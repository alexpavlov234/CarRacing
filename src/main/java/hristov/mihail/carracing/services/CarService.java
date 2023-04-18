package hristov.mihail.carracing.services;

import hristov.mihail.carracing.controllers.MessageType;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Car;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class CarService {
    // Метод за добавяне на кола в базата данни
    public static void addCar(Car car) {
        // SQL заявка за добавяне на запис в таблицата car
        String sql = "INSERT INTO carracers.car (modelCar, brandCar, engineCar, fuelCar, horsepowerCar) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Задаване на стойности на параметрите в SQL заявката
            pstmt.setString(1, car.getModelCar());
            pstmt.setString(2, car.getBrandCar());
            pstmt.setString(3, car.getEngineCar());
            pstmt.setString(4, car.getFuelCar());
            pstmt.setInt(5, car.getHorsepowerCar());
            // Изпълнение на SQL заявката
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Показване на предупреждение при грешка при добавянето на кола в базата данни
            WarningController.openMessageModal("Грешка при добавянето на кола в базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }


    public static String getCarName(Car car) {
        return car.getBrandCar() + " " + car.getModelCar();
    }

    // Метод за извличане на кола от базата данни
    public static Car getCar(int idCar) {
        // SQL заявка за извличане на запис от таблицата car
        String sql = "SELECT * FROM carracers.car WHERE idCar=?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Задаване на стойността на параметъра в SQL заявката
            pstmt.setInt(1, idCar);
            // Изпълнение на SQL заявката и получаване на резултатите
            ResultSet resultSet = pstmt.executeQuery();
            // Проверка дали има резултати
            if (resultSet.next()) {
                // Създаване и връщане на обект от тип Car с информацията от резултатите
                return new Car(resultSet.getInt("idCar"), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), resultSet.getInt("horsepowerCar"));
            } else {
                // Показване на предупреждение при липсваща кола с посочения ID
                WarningController.openMessageModal("Не е намерена такава кола!", "Липсваща кола", MessageType.WARNING);
                return null;
            }
        } catch (SQLException e) {
            // Показване на предупреждение при грешка при извличането на колата от базата данни
            WarningController.openMessageModal("Не е намерена такава кола!", "Липсваща кола", MessageType.WARNING);
            return null;
        }
    }


    // Метод за извличане на кола от базата данни по име
    public static Car getCar(String name) {
        // Разделяне на името на две части - марка и модел
        int firstSpaceIndex = name.indexOf(" ");
        String[] names = {name.substring(0, firstSpaceIndex), name.substring(firstSpaceIndex + 1)};
        if (names.length == 2) {
            // SQL заявка за извличане на запис от таблицата car
            String sql = "SELECT * FROM carracers.car WHERE modelCar=? AND brandCar=?";
            try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Задаване на стойностите на параметрите в SQL заявката
                pstmt.setString(1, names[1]);
                pstmt.setString(2, names[0]);
                // Изпълнение на SQL заявката и получаване на резултатите
                ResultSet resultSet = pstmt.executeQuery();
                // Проверка дали има резултати
                if (resultSet.next()) {
                    // Създаване и връщане на обект от тип Car с информацията от резултатите
                    return new Car(resultSet.getInt("idCar"), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), resultSet.getInt("horsepowerCar"));
                } else {
                    // Показване на предупреждение при липсваща кола с посоченото име
                    WarningController.openMessageModal("Не е намерена такава кола!", "Липсваща кола", MessageType.WARNING);
                    return null;
                }
            } catch (SQLException e) {
                // Показване на предупреждение при грешка при търсенето на колата
                WarningController.openMessageModal("Грешка при търсенето на колата!", "Грешка", MessageType.WARNING);
                return null;
            }
        } else {
            // Показване на предупреждениe при ненамиране на кола
            WarningController.openMessageModal("Не е намерена такава кола!", "Липсваща кола", MessageType.WARNING);
            return null;
        }
    }


    // Метод за извличане на последната добавена кола в базата данни
    public static Car getLastCar() {
        // SQL заявка за извличане на последния запис от таблицата car
        String sql = "SELECT * FROM carracers.car ORDER BY idCar DESC LIMIT 1";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet resultSet = pstmt.executeQuery()) {
            // Проверка дали има резултати
            if (resultSet.next()) {
                // Създаване и връщане на обект от тип Car с информацията от резултатите
                return new Car(resultSet.getInt("idCar"), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), resultSet.getInt("horsepowerCar"));
            } else {
                // Показване на предупреждение при липсваща кола
                WarningController.openMessageModal("Не е намерена такава кола!", "Липсваща кола", MessageType.WARNING);
                return null;
            }
        } catch (SQLException e) {
            // Показване на предупреждение при грешка при извличането на последната кола
            WarningController.openMessageModal("Грешка при взимане на последната кола от базата данни!", "Неуспешна операция", MessageType.WARNING);
            return null;
        }
    }


    // Метод за актуализиране на кола в базата данни
    public static void updateCar(Car car) {
        // SQL заявка за актуализиране на запис в таблицата car
        String sql = "UPDATE carracers.car SET modelCar=?, brandCar=?, engineCar=?, fuelCar=?, horsepowerCar=? WHERE idCar=?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Задаване на стойности на параметрите в SQL заявката
            pstmt.setString(1, car.getModelCar());
            pstmt.setString(2, car.getBrandCar());
            pstmt.setString(3, car.getEngineCar());
            pstmt.setString(4, car.getFuelCar());
            pstmt.setInt(5, car.getHorsepowerCar());
            pstmt.setInt(6, car.getIdCar());
            // Изпълнение на SQL заявката
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Показване на предупреждение при грешка при актуализирането на колата в базата данни
            WarningController.openMessageModal("Грешка при обновяването на колата в базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }


    // Метод за изтриване на кола от базата данни
    public static void deleteCar(int idCar) {
        boolean isCarUsed = false;
        // SQL заявка за изтриване на запис от таблицата car
        String sql = "DELETE FROM carracers.car WHERE idCar=?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // SQL заявки за проверка дали колата е използвана някъде
            String raceSql = "SELECT * FROM carracers.race_has_car_and_driver WHERE idCar=?";
            String driverSql = "SELECT * FROM carracers.person WHERE carPerson=?";
            try (PreparedStatement racePstmt = conn.prepareStatement(raceSql); PreparedStatement driverPstmt = conn.prepareStatement(driverSql)) {
                // Проверка дали колата се използва в състезания
                racePstmt.setInt(1, idCar);
                ResultSet raceResultSet = racePstmt.executeQuery();
                if (raceResultSet.next()) {
                    isCarUsed = true;
                    WarningController.openMessageModal("Колата се използва в състезание и не може да бъде изтрита!", "Неуспешно изтриване", MessageType.WARNING);
                } else {
                    // Проверка дали колата се използва от други състезатели
                    driverPstmt.setInt(1, idCar);
                    ResultSet driverResultSet = driverPstmt.executeQuery();
                    if (driverResultSet.next()) {
                        isCarUsed = true;
                        // Показване на предупреждение при грешка при изтриването на колата от базата данни
                        WarningController.openMessageModal("Колата се използва от състезател и не може да бъде изтрита!", "Неуспешно изтриване", MessageType.WARNING);
                    }
                }
            } catch (SQLException e) {
                // Показване на предупреждение при грешка при изтриването на колата от базата данни
                WarningController.openMessageModal("Грешка при изтриването на колата от базата данни!", "Неуспешна операция", MessageType.WARNING);
            }
            // Ако колата не се използва се изтрива
            if (!isCarUsed) {
                // Задаване на стойността на параметъра в SQL заявката
                pstmt.setInt(1, idCar);
                // Изпълнение на SQL заявката
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            // Показване на предупреждение при грешка при изтриването на колата от базата данни
            WarningController.openMessageModal("Грешка при изтриването на колата от базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }

    // Метод за извличане на снимка на кола от базата данни
    public static Image getImageCar(Car car) {
        try (PreparedStatement retrieve = Database.getConnection().prepareStatement("SELECT imageCar FROM carracers.car WHERE (idCar = " + car.getIdCar() + ");")) {
            // Изпълнение на SQL заявката и получаване на резултатите
            ResultSet resultSet = retrieve.executeQuery();
            resultSet.next();
            // Извличане на снимката от резултатите
            Blob blob = resultSet.getBlob(1);
            if (blob != null) {
                // Превръщане на снимката в обект от тип Image и връщането му
                InputStream inputStream = blob.getBinaryStream();
                Image image = new Image(inputStream);
                return image;
            } else {
                return null;
            }
        } catch (SQLException e) {
            // Показване на предупреждение при грешка при зареждането на снимката на колата
            WarningController.openMessageModal("Грешка при зареждане на снимката на колата!", "Грешка", MessageType.WARNING);
            return null;
        }
    }

    // Метод за задаване на снимка на кола в базата данни
    public static void setImageCar(File file, Car car) {
        try (PreparedStatement store = Database.getConnection().prepareStatement("UPDATE carracers.car SET imageCar = ?  WHERE idCar = ?;")) {
            // Задаване на снимката на колата
            FileInputStream fileInputStream = new FileInputStream(file);
            store.setBinaryStream(1, fileInputStream, fileInputStream.available());
            store.setInt(2, car.getIdCar());
            store.execute();
        } catch (SQLException e) {
            // Показване на предупреждение при грешка в базата данни
            WarningController.openMessageModal("Грешка при задаване на снимката на колата! Грешка в базата данни!", "Грешка", MessageType.WARNING);
        } catch (FileNotFoundException e) {
            // Показване на предупреждение при грешка при намирането на файла
            WarningController.openMessageModal("Грешка при задаване на снимката на колата! Файлът не е намерен!", "Грешка", MessageType.WARNING);
        } catch (Exception e) {
            // Показване на предупреждение при друг вид грешка
            WarningController.openMessageModal("Грешка при задаване на снимката на колата!", "Грешка", MessageType.WARNING);
        }
    }

    // Метод за извличане на всички коли от базата данни
    public static ArrayList<Car> getAllCar() {
        // SQL заявка за извличане на всички коли от базата данни
        String sql = "SELECT * FROM carracers.car";
        ArrayList<Car> allCars = new ArrayList<>();
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql); ResultSet resultSet = pstmt.executeQuery()) {
            // Обхождане на резултатите от заявката
            while (resultSet.next()) {
                // Извличане на данните за всяка кола
                int id = resultSet.getInt("idCar");
                String model = resultSet.getString("modelCar");
                String brand = resultSet.getString("brandCar");
                String engine = resultSet.getString("engineCar");
                String fuel = resultSet.getString("fuelCar");
                int horsepower = resultSet.getInt("horsepowerCar");
                // Създаване на обект от тип Car с извлечените данни
                Car car = new Car(id, model, brand, engine, fuel, horsepower);
                // Добавяне на обекта към списъка с всички коли
                allCars.add(car);
            }
        } catch (SQLException e) {
            // Показване на съобщение за грешка при възникване на проблем при извличането на данните
            WarningController.openMessageModal("Възникна грешка при извличането на всички коли!", "Грешка", MessageType.WARNING);
        }
        // Връщане на списъка с всички коли
        return allCars;
    }


    // Метод за извличане на имената (марка и модел) на всички коли от базата данни
    public static ArrayList<String> getAllCarNames() {
        // Заявка към базата данни за извличане на марката и модела на всички коли
        String sql = "SELECT CONCAT(brandCar, ' ', modelCar) AS carName FROM carracers.car;";
        // Списък за съхранение на имената (марка и модел) на всички коли
        ArrayList<String> allCars = new ArrayList<>();
        // Изпълнение на заявка към базата данни
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                // Добавяне на името към списъка с всички имена на коли
                allCars.add(resultSet.getString("carName"));
            }
        } catch (SQLException e) {
            // Показване на съобщение за грешка при възникване на проблем при извличането на данните
            WarningController.openMessageModal("Грешка при извличане на имената на колите!", "Грешка", MessageType.WARNING);
        }
        // Връщане на списъка с имената (марка и модел) на всички коли
        return allCars;
    }

}
