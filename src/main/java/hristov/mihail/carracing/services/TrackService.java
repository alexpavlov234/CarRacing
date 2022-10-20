package hristov.mihail.carracing.services;

import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Track;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackService {
    public static void addTrack(Track track) throws SQLException {
        Database.execute("INSERT INTO Track (nameTrack, lengthTrack, locationTrack) VALUES (\'" + track.getTrackName() + "\'," + track.getTrackLength() + ",\'" + track.getTrackLocation() + "\');");
        //INSERT INTO Track (nameTrack, lengthTrack, locationTrack) VALUES ('Monte Carlo',456,'Dupnica');
    }

    public static Track getTrack(int idTrack) throws SQLException {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM track WHERE (idTrack == " + idTrack + ");");
        //INSERT INTO Track (nameTrack, lengthTrack, locationTrack) VALUES ('Monte Carlo',456,'Dupnica');
        Track track = new Track(resultSet.getString("nameTrack"), Integer.parseInt(resultSet.getString("lengthTrack")), resultSet.getString("locationTrack"));
        return track;
    }

    public static void updateTrack(Track track) throws SQLException {
        //'
        Database.execute("UPDATE track SET nameTrack = \'" + track.getTrackName() + "\', lengthTrack = " + track.getTrackLength() + ", locationTrack =\' " + track.getTrackLocation() + "\'  WHERE idTrack =" + track.getIdTrack() + ";");
        //INSERT INTO Track (nameTrack, lengthTrack, locationTrack) VALUES ('Monte Carlo',456,'Dupnica');
    }

    public static void deleteTrack(int idTrack) throws SQLException {
        Database.execute("DELETE FROM track WHERE idTrack = " + idTrack + ";");
        // DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
    }

    public static ArrayList<Track> getAllTrack() throws SQLException {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM track;");

        ArrayList<Track> allTracks = new ArrayList<>();
        while ((resultSet.next())){
            //Не е готово
        Track track = new Track(resultSet.getString("nameTrack"), Integer.parseInt(resultSet.getString("lengthTrack")), resultSet.getString("locationTrack"));
            allTracks.add(track);
        }
        return allTracks;
    }
}
