package hristov.mihail.carracing.services;

import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Track;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackService {
    public static void addTrack(Track track) {
        Database.execute("INSERT INTO Track (nameTrack, lengthTrack, locationTrack) VALUES ('" + track.getTrackName() + "'," + track.getTrackLength() + ",'" + track.getTrackLocation() + "');");
        //INSERT INTO Track (nameTrack, lengthTrack, locationTrack) VALUES ('Monte Carlo',456,'Dupnica');
    }

    public static Track getTrack(int idTrack) {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM track WHERE (idTrack == " + idTrack + ");");
        //INSERT INTO Track (nameTrack, lengthTrack, locationTrack) VALUES ('Monte Carlo',456,'Dupnica');
        Track track = null;
        try {
            track = new Track(Integer.parseInt(resultSet.getString("idTrack")), resultSet.getString("nameTrack"), Integer.parseInt(resultSet.getString("lengthTrack")), resultSet.getString("locationTrack"));
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return track;
    }

    public static void updateTrack(Track track) {
        //'
        Database.execute("UPDATE track SET nameTrack = '" + track.getTrackName() + "', lengthTrack = " + track.getTrackLength() + ", locationTrack =' " + track.getTrackLocation() + "'  WHERE idTrack =" + track.getIdTrack() + ";");
        //INSERT INTO Track (nameTrack, lengthTrack, locationTrack) VALUES ('Monte Carlo',456,'Dupnica');
    }

    public static void deleteTrack(int idTrack) {
        Database.execute("DELETE FROM track WHERE idTrack = " + idTrack + ";");
        // DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
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
            //TODO: Екран за грешка
        }
        return allTracks;
    }
}
