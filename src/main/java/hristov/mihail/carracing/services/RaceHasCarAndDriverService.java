package hristov.mihail.carracing.services;

import hristov.mihail.carracing.controllers.MessageType;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.models.Race;
import hristov.mihail.carracing.models.RaceHasCarAndDriver;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class RaceHasCarAndDriverService {
    public static void addRaceHasCarAndDriver(RaceHasCarAndDriver raceHasCarAndDriver) {
        String sql = "INSERT INTO race_has_car_and_driver (idRace, idCar, idDriver, points) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, raceHasCarAndDriver.getIdRace());
            statement.setInt(2, raceHasCarAndDriver.getIdCar());
            statement.setInt(3, raceHasCarAndDriver.getIdDriver());
            statement.setInt(4, raceHasCarAndDriver.getPoints());
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при добавянето на участие в базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }
    public static RaceHasCarAndDriver getRaceHasCarAndDriver(int idRaceHasCarAndDriver) {
        String sql = "SELECT * FROM race_has_car_and_driver WHERE id = ?";
        RaceHasCarAndDriver raceHasCarAndDriver = null;
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idRaceHasCarAndDriver);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                raceHasCarAndDriver = new RaceHasCarAndDriver(
                        Integer.parseInt(resultSet.getString("id")),
                        Integer.parseInt(resultSet.getString("idRace")),
                        Integer.parseInt(resultSet.getString("idCar")),
                        Integer.parseInt(resultSet.getString("idDriver")),
                        Integer.parseInt(resultSet.getString("points"))
                );
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Не е намерено такова участие!", "Несъществуващо участие", MessageType.WARNING);
        }
        return raceHasCarAndDriver;
    }
    public static boolean isDriverParticipatingInRace(int idRace, int idDriver) {
        String sql = "SELECT * FROM race_has_car_and_driver WHERE idRace = ? AND idDriver = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idRace);
            statement.setInt(2, idDriver);
            ResultSet resultSet = statement.executeQuery();

            RaceHasCarAndDriver raceHasCarAndDriver = null;
            if (resultSet.next()) {
                raceHasCarAndDriver = new RaceHasCarAndDriver(
                        Integer.parseInt(resultSet.getString("id")),
                        Integer.parseInt(resultSet.getString("idRace")),
                        Integer.parseInt(resultSet.getString("idCar")),
                        Integer.parseInt(resultSet.getString("idDriver")),
                        Integer.parseInt(resultSet.getString("points")));
            }

            if (raceHasCarAndDriver != null && raceHasCarAndDriver.getIdRace() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при търсенето на участник в участие!", "Грешка", MessageType.WARNING);
            return false;
        }
    }
    public static void updateRaceHasCarAndDriver(RaceHasCarAndDriver raceHasCarAndDriver) {
        try (PreparedStatement statement = Database.getConnection().prepareStatement("UPDATE race_has_car_and_driver SET idRace = ?, idCar = ?, idDriver = ?, points = ? WHERE id = ?")) {
            statement.setInt(1, raceHasCarAndDriver.getIdRace());
            statement.setInt(2, raceHasCarAndDriver.getIdCar());
            statement.setInt(3, raceHasCarAndDriver.getIdDriver());
            statement.setInt(4, raceHasCarAndDriver.getPoints());
            statement.setInt(5, raceHasCarAndDriver.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при обновяването на участие в базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }

    public static void deleteRaceHasCarAndDriver(int idRaceHasCarAndDriver) {
        try (PreparedStatement statement = Database.getConnection().prepareStatement("DELETE FROM race_has_car_and_driver WHERE id = ?")) {
            statement.setInt(1, idRaceHasCarAndDriver);
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при изтриването на участие от базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }

    public static ArrayList<RaceHasCarAndDriver> getAllRaceHasCarAndDriver() {
        ArrayList<RaceHasCarAndDriver> allRaceHasCarAndDriver = new ArrayList<>();
        try (PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM race_has_car_and_driver")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                RaceHasCarAndDriver raceHasCarAndDriver = new RaceHasCarAndDriver(
                        Integer.parseInt(resultSet.getString("id")),
                        Integer.parseInt(resultSet.getString("idRace")),
                        Integer.parseInt(resultSet.getString("idCar")),
                        Integer.parseInt(resultSet.getString("idDriver")),
                        Integer.parseInt(resultSet.getString("points"))
                );
                allRaceHasCarAndDriver.add(raceHasCarAndDriver);
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Възникна грешка при извличането на всички участия!", "Грешка", MessageType.WARNING);
        }
        return allRaceHasCarAndDriver;
    }
    public static ArrayList<RaceHasCarAndDriver> getAllRaceHasCarAndDriver(int idRace) {
        ArrayList<RaceHasCarAndDriver> allRaceHasCarAndDriver = new ArrayList<>();
        String sql = "SELECT * FROM race_has_car_and_driver WHERE idRace = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idRace);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                RaceHasCarAndDriver raceHasCarAndDriver = new RaceHasCarAndDriver(
                        Integer.parseInt(resultSet.getString("id")),
                        Integer.parseInt(resultSet.getString("idRace")),
                        Integer.parseInt(resultSet.getString("idCar")),
                        Integer.parseInt(resultSet.getString("idDriver")),
                        Integer.parseInt(resultSet.getString("points"))
                );
                allRaceHasCarAndDriver.add(raceHasCarAndDriver);
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Възникна грешка при извличането на всички участия!", "Грешка", MessageType.WARNING);
        }
        return allRaceHasCarAndDriver;
    }
    public static int getNumberParticipants(Race race) {
        int number = 0;
        try (PreparedStatement statement = Database.getConnection().prepareStatement(
                "SELECT COUNT(IF(idRace = ?, 1, NULL)) AS 'Number' FROM race_has_car_and_driver")) {
            statement.setInt(1, race.getIdRace());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                number = resultSet.getInt("Number");
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Възникна грешка при извличането на броя на участниците!", "Грешка", MessageType.WARNING);
        }
        return number;
    }

    public static boolean isParticipatingInRace(int idRace, int idPerson) {
        boolean isParticipating = false;
        try (PreparedStatement statement = Database.getConnection().prepareStatement(
                "SELECT COUNT(IF(idRace = ? AND idDriver = ?, 1, NULL)) AS 'Number' FROM race_has_car_and_driver")) {
            statement.setInt(1, idRace);
            statement.setInt(2, idPerson);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int number = resultSet.getInt("Number");
                isParticipating = number == 1;
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при търсенето на участник в състезание!", "Грешка", MessageType.WARNING);
        }
        return isParticipating;
    }

    public static boolean areTherePlacesAvailable(Race race) {
        boolean arePlacesAvailable = false;
        try (PreparedStatement statement = Database.getConnection().prepareStatement(
                "SELECT COUNT(IF(idRace = ?, 1, NULL)) AS 'Number' FROM race_has_car_and_driver")) {
            statement.setInt(1, race.getIdRace());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int number = resultSet.getInt("Number");
                arePlacesAvailable = number < race.getParticipantsRace();
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при търсенето на свободни места в състезанието!", "Грешка", MessageType.WARNING);
        }
        return arePlacesAvailable;
    }
    public static void updateRaceHasCarAndDriverList(ObservableList<RaceHasCarAndDriver> raceHasCarAndDriversObservableList) {
        String insertSql = "INSERT INTO race_has_car_and_driver (idRace, idCar, idDriver, points) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {
            conn.setAutoCommit(false); // Disable auto-commit to improve performance
            for (RaceHasCarAndDriver r : raceHasCarAndDriversObservableList) {
                stmt.setInt(1, r.getIdRace());
                stmt.setInt(2, r.getIdCar());
                stmt.setInt(3, r.getIdDriver());
                stmt.setInt(4, r.getPoints());
                stmt.addBatch(); // Add the prepared statement to a batch
            }
            stmt.executeBatch(); // Execute the batch of prepared statements
            conn.commit(); // Commit the changes to the database
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при актуализирането на точките!", "Грешка", MessageType.WARNING);
        }
    }

}

