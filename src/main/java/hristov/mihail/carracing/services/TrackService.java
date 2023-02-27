package hristov.mihail.carracing.services;

import hristov.mihail.carracing.controllers.MessageType;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Track;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackService {
    public static void addTrack(Track track) {
        Database.execute("INSERT INTO track (nameTrack, lengthTrack, locationTrack) VALUES ('" + track.getNameTrack() + "'," + track.getLengthTrack() + ",'" + track.getLocationTrack() + "');");

    }
    public static PreparedStatement setImageTrack() {
        try {
            PreparedStatement store = Database.getConnection().prepareStatement("UPDATE track SET imageTrack = ?  WHERE idTrack = ?;");
            return store;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Image getImageTrack(Track track) {
        try {
            PreparedStatement retrieve = Database.getConnection().prepareStatement("SELECT imageTrack FROM track WHERE (idTrack = " + track.getIdTrack() + ");");
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
    public static Track getTrack(int idTrack) {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM track WHERE (idTrack = " + idTrack + ");");
         Track track = null;
        try {
            resultSet.next();
            track = new Track(Integer.parseInt(resultSet.getString("idTrack")), resultSet.getString("nameTrack"), Integer.parseInt(resultSet.getString("lengthTrack")), resultSet.getString("locationTrack"));
        } catch (SQLException e) {

        }

        return track;
    }

    public static Track getTrack(String name) {

        ResultSet resultSet = Database.executeQuery("SELECT * FROM track WHERE (nameTrack = '" + name + "');");

        Track track = null;
        try {
            resultSet.next();
        track = new Track(Integer.parseInt(resultSet.getString("idTrack")), resultSet.getString("nameTrack"), Integer.parseInt(resultSet.getString("lengthTrack")), resultSet.getString("locationTrack"));
        } catch (SQLException e) {
            //TODO: Екран за грешка
             WarningController.openMessageModal("Не е намерена такава кола!", "Лиспваща кола", MessageType.WARNING);
        }
        return track;

    }
    public static void updateTrack(Track track) {
        Database.execute("UPDATE track SET nameTrack = '" + track.getNameTrack() + "', lengthTrack = " + track.getLengthTrack() + ", locationTrack ='" + track.getLocationTrack() + "'  WHERE idTrack =" + track.getIdTrack() + ";");
    }

    public static void deleteTrack(int idTrack) {
        Database.execute("DELETE FROM track WHERE idTrack = " + idTrack + ";");
    }

    public static ArrayList<Track> getAllTrack() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM track;");

        ArrayList<Track> allTracks = new ArrayList<>();
        try {
            while ((resultSet.next())) {
                Track track = new Track(Integer.parseInt(resultSet.getString("idTrack")), resultSet.getString("nameTrack"), Integer.parseInt(resultSet.getString("lengthTrack")), resultSet.getString("locationTrack"));
                allTracks.add(track);
            }
        } catch (SQLException e) {

        }
        return allTracks;
    }

    public static Track getLastTrack() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM track ORDER BY idTrack DESC LIMIT 1;");

        Track track = null;
        try {
            resultSet.next();
            track = new Track(Integer.parseInt(resultSet.getString("idTrack")), resultSet.getString("nameTrack"), Integer.parseInt(resultSet.getString("lengthTrack")), resultSet.getString("locationTrack"));
        } catch (SQLException e) {

        }

        return track;
    }


    public static ArrayList<String> getAllTrackNames() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM track;");

        ArrayList<String> allTracks = new ArrayList<>();
        try {
            while ((resultSet.next())) {

                allTracks.add(resultSet.getString("nameTrack"));

            }
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return allTracks;
    }
}
