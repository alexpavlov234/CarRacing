package hristov.mihail.carracing.services;

import hristov.mihail.carracing.controllers.MessageType;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Race;
import hristov.mihail.carracing.models.RaceHasCarAndDriver;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

            return raceHasCarAndDriver != null && raceHasCarAndDriver.getIdRace() > 0;
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
        try (Connection conn = Database.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM race_has_car_and_driver WHERE id = ?");
             PreparedStatement updateStmt = conn.prepareStatement("UPDATE person SET pointsPerson = ? WHERE idPerson = ?")) {

            conn.setAutoCommit(false);

            int driverId = 0;
            int pointsPerson = 0;
            String selectSql = "SELECT idDriver, SUM(points) AS pointsPerson FROM race_has_car_and_driver WHERE id = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setInt(1, idRaceHasCarAndDriver);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    driverId = rs.getInt("idDriver");
                    pointsPerson = rs.getInt("pointsPerson");
                }
            }

            deleteStmt.setInt(1, idRaceHasCarAndDriver);
            deleteStmt.executeUpdate();

            pointsPerson = getTotalPointsForDriver(conn, driverId);
            updateStmt.setInt(1, pointsPerson);
            updateStmt.setInt(2, driverId);
            updateStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при изтриването на участие от базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }


    public static ArrayList<RaceHasCarAndDriver> getAllRaceHasCarAndDriver() {
        ArrayList<RaceHasCarAndDriver> allRaceHasCarAndDriver = new ArrayList<>();
        try (PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM race_has_car_and_driver")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getString("id") != null ? Integer.parseInt(resultSet.getString("id")) : 0;
                int idRace = resultSet.getString("idRace") != null ? Integer.parseInt(resultSet.getString("idRace")) : 0;
                int idCar = resultSet.getString("idCar") != null ? Integer.parseInt(resultSet.getString("idCar")) : 0;
                int idDriver = resultSet.getString("idDriver") != null ? Integer.parseInt(resultSet.getString("idDriver")) : 0;
                int points = resultSet.getString("points") != null ? Integer.parseInt(resultSet.getString("points")) : 0;

                RaceHasCarAndDriver raceHasCarAndDriver = new RaceHasCarAndDriver(id, idRace, idCar, idDriver, points);

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

    public static ArrayList<RaceHasCarAndDriver> getAllRaceHasCarAndDriverFromPerson(int idPerson) {
        ArrayList<RaceHasCarAndDriver> allRaceHasCarAndDriver = new ArrayList<>();
        String sql = "SELECT * FROM race_has_car_and_driver WHERE idDriver = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idPerson);
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
                arePlacesAvailable = number + 1 <= race.getParticipantsRace();
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при търсенето на свободни места в състезанието!", "Грешка", MessageType.WARNING);
        }
        return arePlacesAvailable;
    }

    public static void updateRaceHasCarAndDriverList(ObservableList<RaceHasCarAndDriver> raceHasCarAndDriversObservableList) {

        String updateSql = "UPDATE race_has_car_and_driver SET points = ? WHERE idRace = ? AND idCar = ? AND idDriver = ?";
        String insertSql = "INSERT INTO race_has_car_and_driver (idRace, idCar, idDriver, points) VALUES (?, ?, ?, ?)";
        String updatePersonSql = "UPDATE person SET pointsPerson = ? WHERE idPerson = ?";

        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                 PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                 PreparedStatement updatePersonStmt = conn.prepareStatement(updatePersonSql)) {

                for (RaceHasCarAndDriver r : raceHasCarAndDriversObservableList) {
                    updateStmt.setInt(1, r.getPoints());
                    updateStmt.setInt(2, r.getIdRace());
                    updateStmt.setInt(3, r.getIdCar());
                    updateStmt.setInt(4, r.getIdDriver());

                    int updatedRows = updateStmt.executeUpdate();

                    if (updatedRows == 0) {
                        insertStmt.setInt(1, r.getIdRace());
                        insertStmt.setInt(2, r.getIdCar());
                        insertStmt.setInt(3, r.getIdDriver());
                        insertStmt.setInt(4, r.getPoints());
                        insertStmt.addBatch();
                    }

                    updatePersonStmt.setInt(1, getTotalPointsForDriver(conn, r.getIdDriver()));
                    updatePersonStmt.setInt(2, r.getIdDriver());
                    updatePersonStmt.executeUpdate();
                }

                insertStmt.executeBatch();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                WarningController.openMessageModal("Грешка при актуализирането на точките!", "Грешка", MessageType.WARNING);
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при актуализирането на точките!", "Грешка", MessageType.WARNING);
        }
    }

    private static int getTotalPointsForDriver(Connection conn, int driverId) throws SQLException {
        String sql = "SELECT SUM(points) FROM race_has_car_and_driver WHERE idDriver = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        }
    }

}

