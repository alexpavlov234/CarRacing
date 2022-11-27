package hristov.mihail.carracing.services;

import hristov.mihail.carracing.controllers.MessageType;
import hristov.mihail.carracing.controllers.WarningController;
import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.models.Race;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class RaceService {
    public static void addRace(Race race) {
        Database.execute("INSERT INTO race (trackRace, dateRace, lapsRace, pointsRace, participantsRace) VALUES (" + race.getTrackRace() + ",'" + race.getDateRace() + "'," + race.getLapsRace() + "," + race.getPointsRace() + "," + race.getParticipantsRace() + ");");
        //INSERT INTO Race (nameRace, lengthRace, locationRace) VALUES ('Monte Racelo',456,'Dupnica');
    }
    public static Race getLastRace() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM race ORDER BY idRace DESC LIMIT 1;");

        Race race = null;
        try {
            resultSet.next();
            //TODO: Проверка за null стойности
            race = new Race(Integer.parseInt(resultSet.getString("idRace")), Integer.parseInt(resultSet.getString("trackRace")), resultSet.getString("dateRace"), Integer.parseInt(resultSet.getString("lapsRace")), Integer.parseInt(resultSet.getString("pointsRace")), Integer.parseInt(resultSet.getString("participantsRace")));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //TODO: Екран за грешка
        }
        return race;
    }
    public static Race getRace(int idRace) {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM race WHERE (idRace = " + idRace + ");");
        //INSERT INTO Race (nameRace, lengthRace, locationRace) VALUES ('Monte Racelo',456,'Dupnica');
        Race race = null;


        try {
            resultSet.next();
            race = new Race(Integer.parseInt(resultSet.getString("idRace")), Integer.parseInt(resultSet.getString("trackRace")), resultSet.getString("dateRace"), Integer.parseInt(resultSet.getString("lapsRace")), Integer.parseInt(resultSet.getString("pointsRace")), Integer.parseInt(resultSet.getString("participantsRace")));
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return race;
    }

    public static void updateRace(Race race) {
        //'
        Database.execute("UPDATE race SET trackRace = " + race.getTrackRace() + ", dateRace = '" + race.getDateRace() + "', lapsRace = " + race.getLapsRace() + ", pointsRace = " + race.getPointsRace() + ", participantsRace = " + race.getParticipantsRace() + "  WHERE idRace =  " + race.getIdRace() + ";");
        //INSERT INTO Race (nameRace, lengthRace, locationRace) VALUES ('Monte Racelo',456,'Dupnica');
    }

    public static void deleteRace(int idRace) {
        Database.execute("DELETE FROM race WHERE idRace = " + idRace + ";");
        // DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
    }

    public static ArrayList<Race> getAllRace() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM race;");

        ArrayList<Race> allRaces = new ArrayList<>();
        try {
            while ((resultSet.next())) {
                Race race = new Race(Integer.parseInt(resultSet.getString("idRace")), Integer.parseInt(resultSet.getString("trackRace")), resultSet.getString("dateRace"), Integer.parseInt(resultSet.getString("lapsRace")), Integer.parseInt(resultSet.getString("pointsRace")), Integer.parseInt(resultSet.getString("participantsRace")));
                allRaces.add(race);
            }
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return allRaces;
    }

    public static ArrayList<String> getAllRaceNames() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM race;");

        ArrayList<String> allRaces = new ArrayList<>();
        try {
            while ((resultSet.next())) {
//                byte[] byteData = resultSet.getString("imageCar").getBytes(StandardCharsets.UTF_8);//Better to specify encoding
//                Blob blobData = Database.createBlob();
//                blobData.setBytes(1, byteData);
                allRaces.add(TrackService.getTrack(Integer.parseInt(resultSet.getString("trackRace"))).getNameTrack() + " / " + resultSet.getString("dateRace"));

            }
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return allRaces;
    }

    public static Race getRace(String name) {

        String[] names = name.split(" / ");

        if (names.length == 2) {

            int trackId = TrackService.getTrack(names[0]).getIdTrack();
            ResultSet resultSet = Database.executeQuery("SELECT * FROM race WHERE (trackRace = " + TrackService.getTrack(names[0]).getIdTrack() + " AND dateRace = '" + names[1] + "');");
            //INSERT INTO Car (nameCar, lengthCar, locationCar) VALUES ('Monte Carlo',456,'Dupnica');

            Race race = null;
            try {
                resultSet.next();
//            byte[] byteData = resultSet.getString("imageCar").getBytes(StandardCharsets.UTF_8);//Better to specify encoding
//            Blob blobData = Database.createBlob();
//            blobData.setBytes(1, byteData);
                //  race = new Car(Integer.parseInt(resultSet.getString("idCar")), resultSet.getString("modelCar"), resultSet.getString("brandCar"), resultSet.getString("engineCar"), resultSet.getString("fuelCar"), Integer.parseInt(resultSet.getString("horsepowerCar")), blobData);
                race = new Race(Integer.parseInt(resultSet.getString("idRace")), Integer.parseInt(resultSet.getString("trackRace")), resultSet.getString("dateRace"), Integer.parseInt(resultSet.getString("lapsRace")), Integer.parseInt(resultSet.getString("pointsRace")), Integer.parseInt(resultSet.getString("participantsRace")));
            } catch (SQLException e) {
                //TODO: Екран за грешка
            }
            return race;
        } else {
            WarningController.openMessageModal("Не е намерено такова състезание!", "Лиспващо състезание", MessageType.WARNING);
            return null;
        }

    }
}
