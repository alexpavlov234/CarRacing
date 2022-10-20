package hristov.mihail.carracing.services;

import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Track;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackService {
    public static void addTrack(Track track) {
        Database.executeQuery("INSERT INTO Track (trackName, trackLength, trackLocation) VALUES (\'" + track.gettrackName() + "\'," + track.gettrackLength() + ",\'" + track.gettrackLocation() + "\');");
        //INSERT INTO Track (trackName, trackLength, trackLocation) VALUES ('Monte Carlo',456,'Dupnica');
    }

    public static Track getTrack(int idTrack) throws SQLException {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM track WHERE (idTrack == " + idTrack + ");");
        //INSERT INTO Track (trackName, trackLength, trackLocation) VALUES ('Monte Carlo',456,'Dupnica');
        Track track = new Track(resultSet.getString("trackName"), Integer.parseInt(resultSet.getString("trackLength")), resultSet.getString("trackLocation"));
        return track;
    }

    public static void updateTrack(Track track) {
        Database.executeQuery("UPDATE track SET trackName = " + track.gettrackName() + ", trackLength = " + track.gettrackLength() + ", trackLocation = " + track.gettrackLocation() + "  WHERE idTrack =" + track.getidTrack() + ";");
        //INSERT INTO Track (trackName, trackLength, trackLocation) VALUES ('Monte Carlo',456,'Dupnica');
    }

    public static void deleteTrack(int idTrack) {
        Database.executeQuery("DELETE FROM track WHERE idTrack = " + idTrack + ";");
        // DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
    }

    public static ArrayList<Track> getAllTrack() throws SQLException {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM track;");

        ArrayList<Track> allTracks = new ArrayList<>();
        while ((resultSet.next())){
            //Не е готово
        Track track = new Track(resultSet.getString("trackName"), Integer.parseInt(resultSet.getString("trackLength")), resultSet.getString("trackLocation"));
            allTracks.add(track);
        }
        return allTracks;
    }
}
