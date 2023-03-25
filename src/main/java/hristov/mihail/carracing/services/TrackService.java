package hristov.mihail.carracing.services;

import hristov.mihail.carracing.controllers.MessageType;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Track;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackService {
    public static void addTrack(Track track) {
        try (PreparedStatement statement = Database.getConnection().prepareStatement("INSERT INTO track (nameTrack, lengthTrack, locationTrack) VALUES (?, ?, ?)")) {
            statement.setString(1, track.getNameTrack());
            statement.setInt(2, track.getLengthTrack());
            statement.setString(3, track.getLocationTrack());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при добавянето на писта в базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }

    public static void setImageTrack(File file, Track track) {
        try (PreparedStatement store = Database.getConnection().prepareStatement("UPDATE carracers.track SET imageTrack = ?  WHERE idTrack = ?;")) {
            FileInputStream fileInputStream = new FileInputStream(file);
            store.setBinaryStream(1, fileInputStream, fileInputStream.available());
            store.setInt(2, track.getIdTrack());
            store.execute();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при задаване на снимката на пистата! Грешка в базата данни!", "Грешка", MessageType.WARNING);
        } catch (FileNotFoundException e) {
            WarningController.openMessageModal("Грешка при задаване на снимката на пистата! Файлът не е намерен!", "Грешка", MessageType.WARNING);
        } catch (Exception e) {
            WarningController.openMessageModal("Грешка при задаване на снимката на пистата!", "Грешка", MessageType.WARNING);
        }
    }

    public static Image getImageTrack(Track track) {
        try (PreparedStatement retrieve = Database.getConnection().prepareStatement("SELECT imageTrack FROM track WHERE (idTrack = " + track.getIdTrack() + ");")) {
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
            WarningController.openMessageModal("Грешка при зареждане на снимката на пистата!", "Грешка", MessageType.WARNING);
            return null;
        }
    }

    public static Track getTrack(int idTrack) {
        Track track = null;
        try (PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM track WHERE idTrack = ?")) {
            statement.setInt(1, idTrack);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                track = new Track(Integer.parseInt(resultSet.getString("idTrack")), resultSet.getString("nameTrack"), Integer.parseInt(resultSet.getString("lengthTrack")), resultSet.getString("locationTrack"));
            } else {
                WarningController.openMessageModal("Не е намерена такава писта!", "Лиспваща писта", MessageType.WARNING);
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Не е намерена такава писта!", "Лиспваща писта", MessageType.WARNING);
        }
        return track;
    }

    public static Track getTrack(String name) {
        String sql = "SELECT * FROM track WHERE nameTrack = ?";
        Track track = null;

        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                track = new Track(resultSet.getInt("idTrack"), resultSet.getString("nameTrack"), resultSet.getInt("lengthTrack"), resultSet.getString("locationTrack"));
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Не е намерена такава писта!", "Лиспваща писта", MessageType.WARNING);
        }

        return track;
    }

    public static void updateTrack(Track track) {
        String sql = "UPDATE track SET nameTrack = ?, lengthTrack = ?, locationTrack = ? WHERE idTrack = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setString(1, track.getNameTrack());
            statement.setInt(2, track.getLengthTrack());
            statement.setString(3, track.getLocationTrack());
            statement.setInt(4, track.getIdTrack());
            statement.executeUpdate();
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при обновяването на пистата в базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }

    public static void deleteTrack(int idTrack) {
        boolean isTrackUsed = false;
        String sql = "DELETE FROM track WHERE idTrack = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            String trackSql = "SELECT * FROM race WHERE trackRace = ?";
            try (PreparedStatement driverPstmt = Database.getConnection().prepareStatement(trackSql)) {
                driverPstmt.setInt(1, idTrack);
                ResultSet driverResultSet = driverPstmt.executeQuery();
                if (driverResultSet.next()) {
                    isTrackUsed = true;
                    WarningController.openMessageModal("Пистата се използва от състезание и не може да бъде изтрита!", "Неуспешно изтриване", MessageType.WARNING);
                }
            }
            if (!isTrackUsed) {
                statement.setInt(1, idTrack);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при изтриването на пистата от базата данни!", "Неуспешна операция", MessageType.WARNING);
        }
    }

    public static ArrayList<Track> getAllTrack() {
        String sql = "SELECT * FROM track;";
        ArrayList<Track> allTracks = new ArrayList<>();
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Track track = new Track(Integer.parseInt(resultSet.getString("idTrack")), resultSet.getString("nameTrack"), Integer.parseInt(resultSet.getString("lengthTrack")), resultSet.getString("locationTrack"));
                allTracks.add(track);
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Възникна грешка при извличането на всички писти!", "Грешка", MessageType.WARNING);
        }
        return allTracks;
    }

    public static Track getLastTrack() {
        String sql = "SELECT * FROM track ORDER BY idTrack DESC LIMIT 1;";
        Track track = null;

        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                track = new Track(Integer.parseInt(resultSet.getString("idTrack")), resultSet.getString("nameTrack"), Integer.parseInt(resultSet.getString("lengthTrack")), resultSet.getString("locationTrack"));
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при взимане на последната писта от базата данни!", "Неуспешна операция", MessageType.WARNING);
        }

        return track;
    }

    public static ArrayList<String> getAllTrackNames() {
        ArrayList<String> allTracks = new ArrayList<>();
        try (PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM track")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allTracks.add(resultSet.getString("nameTrack"));
            }
        } catch (SQLException e) {
            WarningController.openMessageModal("Грешка при извличане на имената на пистите!", "Грешка", MessageType.WARNING);
        }
        return allTracks;
    }

}

