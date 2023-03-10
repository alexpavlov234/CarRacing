package hristov.mihail.carracing.services;

import hristov.mihail.carracing.controllers.MessageType;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Race;
import hristov.mihail.carracing.models.Track;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RaceService {
    public static void addRace(Race race) {
        String sql = "INSERT INTO race (trackRace, dateRace, lapsRace, pointsRace, participantsRace) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, race.getTrackRace());
            statement.setString(2, race.getDateRace());
            statement.setInt(3, race.getLapsRace());
            statement.setInt(4, race.getPointsRace());
            statement.setInt(5, race.getParticipantsRace());
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при добавянето на състезание в базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }

    public static Race getLastRace() {
        String sql = "SELECT * FROM race ORDER BY idRace DESC LIMIT 1;";
        Race race = null;
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("idRace");
                int track = resultSet.getInt("trackRace");
                String date = resultSet.getString("dateRace");
                int laps = resultSet.getInt("lapsRace");
                int points = resultSet.getInt("pointsRace");
                int participants = resultSet.getInt("participantsRace");
                race = new Race(id, track, date, laps, points, participants);
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при взимане на последното състезание от базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
        return race;
    }

    public static Race getRace(int idRace) {
        String sql = "SELECT * FROM race WHERE idRace=?";
        Race race = null;

        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idRace);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                race = new Race(
                        resultSet.getInt("idRace"),
                        resultSet.getInt("trackRace"),
                        resultSet.getString("dateRace"),
                        resultSet.getInt("lapsRace"),
                        resultSet.getInt("pointsRace"),
                        resultSet.getInt("participantsRace")
                );
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Не е намерена такова състезание!", "Лиспващо състезание", MessageType.WARNING);
        }

        return race;
    }

    public static void updateRace(Race race) {
        String sql = "UPDATE race SET trackRace = ?, dateRace = ?, lapsRace = ?, pointsRace = ?, participantsRace = ? WHERE idRace = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, race.getTrackRace());
            statement.setString(2, race.getDateRace());
            statement.setInt(3, race.getLapsRace());
            statement.setInt(4, race.getPointsRace());
            statement.setInt(5, race.getParticipantsRace());
            statement.setInt(6, race.getIdRace());
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при обновяването на състезанието в базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }

    public static void deleteRace(int idRace) {
        String sql = "DELETE FROM race WHERE idRace = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idRace);
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при изтриването на състезанието от базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }

    public static ArrayList<Race> getAllRace() {
        ArrayList<Race> allRaces = new ArrayList<>();

        try (PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM race;")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Race race = new Race(resultSet.getInt("idRace"), resultSet.getInt("trackRace"), resultSet.getString("dateRace"), resultSet.getInt("lapsRace"), resultSet.getInt("pointsRace"), resultSet.getInt("participantsRace"));
                allRaces.add(race);
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Възникна грешка при извличането на всички състезания!", "Грешка", MessageType.WARNING);
        }

        return allRaces;
    }

    public static ArrayList<String> getAllRaceNames() {
        ArrayList<String> allRaces = new ArrayList<>();
        try (PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM race");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                allRaces.add(TrackService.getTrack(resultSet.getInt("trackRace")).getNameTrack() + " / " + resultSet.getString("dateRace"));
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при извличане на имената на състезанията!", "Грешка", MessageType.WARNING);
        }
        return allRaces;
    }

    public static ArrayList<String> getAllFreeRacesNames() {
        ArrayList<String> allRaces = new ArrayList<>();
        try (PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM race");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                if (RaceHasCarAndDriverService.areTherePlacesAvailable(getRace(resultSet.getInt("idRace")))) {
                    allRaces.add(TrackService.getTrack(resultSet.getInt("trackRace")).getNameTrack() + " / " + resultSet.getString("dateRace"));
                }
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при извличане на имената на състезанията!", "Грешка", MessageType.WARNING);
        }
        return allRaces;
    }

    public static Race getRace(String name) {

        String[] names = name.split(" / ");

        if (names.length == 2) {

            Track track = TrackService.getTrack(names[0]);
            int trackId = track.getIdTrack();
            String date = names[1];

            String sql = "SELECT * FROM race WHERE trackRace = ? AND dateRace = ?";
            try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {

                statement.setInt(1, trackId);
                statement.setString(2, date);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Race race = new Race(
                            resultSet.getInt("idRace"),
                            resultSet.getInt("trackRace"),
                            resultSet.getString("dateRace"),
                            resultSet.getInt("lapsRace"),
                            resultSet.getInt("pointsRace"),
                            resultSet.getInt("participantsRace")
                    );
                    return race;
                } else {
                    WarningController.openMessageModal("Не е намерено такова състезание!", "Лиспващо състезание", MessageType.WARNING);
                    return null;
                }

            } catch (SQLException e) {
                WarningController.openMessageModal("Възникна грешка при заявката към базата данни!", "Грешка", MessageType.WARNING);
                return null;
            }

        } else {
            WarningController.openMessageModal("Не е намерено такова състезание!", "Лиспващо състезание", MessageType.WARNING);
            return null;
        }

    }

}

