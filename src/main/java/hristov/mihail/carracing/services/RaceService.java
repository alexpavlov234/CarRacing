package hristov.mihail.carracing.services;

import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Race;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RaceService {
//    public static void addRace(Race race) {
//        Database.execute("INSERT INTO Race (trackRace, dataRace, lapsRace, pointsRace, participantsRace) VALUES ('" + race.getTrackRace() + "','" + race.getDataRace() + "','" + race.getEngineRace() + "'," + race.getFuelRace() + "," + race.getHorsepowerRace() + "'," + race.getImagePathRace() + ");");
//        //INSERT INTO Race (nameRace, lengthRace, locationRace) VALUES ('Monte Racelo',456,'Dupnica');
//    }
//
//    public static Race getRace(int idRace) {
//        ResultSet resultSet = Database.executeQuery("SELECT * FROM race WHERE (idRace == " + idRace + ");");
//        //INSERT INTO Race (nameRace, lengthRace, locationRace) VALUES ('Monte Racelo',456,'Dupnica');
//        Race race = null;
//        try {
//            race = new Race(Integer.parseInt(resultSet.getString("idRace")), resultSet.getString("modelRace"), resultSet.getString("brandRace"), resultSet.getString("engineRace"), resultSet.getString("fuelRace"), Integer.parseInt(resultSet.getString("horsepowerRace")), resultSet.getString("imagePathRace"));
//        } catch (SQLException e) {
//            //TODO: Екран за грешка
//        }
//        return race;
//    }
//
//    public static void updateRace(Race race) {
//        //'
//        Database.execute("UPDATE race SET modelRace = '" + race.getModelRace() + "', brandRace = '" + race.getBrandRace() + "', engineRace = '" + race.getEngineRace() + "', fuelRace = '" + race.getFuelRace() + "', horsepowerRace = " + race.getHorsepowerRace() + "  WHERE idRace =" + race.getIdRace() + ";");
//        //INSERT INTO Race (nameRace, lengthRace, locationRace) VALUES ('Monte Racelo',456,'Dupnica');
//    }
//
//    public static void deleteRace(int idRace) {
//        Database.execute("DELETE FROM race WHERE idRace = " + idRace + ";");
//        // DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
//    }
//
//    public static ArrayList<Race> getAllRace() {
//        ResultSet resultSet = Database.executeQuery("SELECT * FROM race;");
//
//        ArrayList<Race> allRaces = new ArrayList<>();
//        try {
//            while ((resultSet.next())) {
//                Race race = new Race(Integer.parseInt(resultSet.getString("idRace")), resultSet.getString("modelRace"), resultSet.getString("brandRace"), resultSet.getString("engineRace"), resultSet.getString("fuelRace"), Integer.parseInt(resultSet.getString("horsepowerRace")), resultSet.getString("imagePathRace"));
//                allRaces.add(race);
//            }
//        } catch (SQLException e) {
//            //TODO: Екран за грешка
//        }
//        return allRaces;
//    }
}
