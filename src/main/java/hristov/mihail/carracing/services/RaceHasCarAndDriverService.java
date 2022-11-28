package hristov.mihail.carracing.services;

import hristov.mihail.carracing.data.Database;
import hristov.mihail.carracing.models.Race;
import hristov.mihail.carracing.models.RaceHasCarAndDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RaceHasCarAndDriverService {
    public static void addRaceHasCarAndDriver(RaceHasCarAndDriver raceHasCarAndDriver) {
        Database.execute("INSERT INTO race_has_car_and_driver (idRace, idCar, idDriver, points) VALUES (" + raceHasCarAndDriver.getIdRace() + "," + raceHasCarAndDriver.getIdCar() + "," + raceHasCarAndDriver.getIdDriver() + "," +raceHasCarAndDriver.getPoints()+");");
        //INSERT INTO RaceHasCarAndDriver (nameRace, lengthRace, locationRace) VALUES ('Monte Racelo',456,'Dupnica');
    }

    public static RaceHasCarAndDriver getRaceHasCarAndDriver(int idRaceHasCarAndDriver) {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM race_has_car_and_driver WHERE (id = " + idRaceHasCarAndDriver + ");");

        //INSERT INTO RaceHasCarAndDriver (nameRace, lengthRace, locationRace) VALUES ('Monte Racelo',456,'Dupnica');
        RaceHasCarAndDriver raceHasCarAndDriver = null;
        try {
            resultSet.next();
            raceHasCarAndDriver = new RaceHasCarAndDriver(Integer.parseInt(resultSet.getString("id")),Integer.parseInt(resultSet.getString("idRace")),Integer.parseInt(resultSet.getString("idCar")),Integer.parseInt(resultSet.getString("idDriver")),Integer.parseInt(resultSet.getString("points")));
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return raceHasCarAndDriver;
    }

    public static boolean isDriverParticipatingInRace(int idRace, int idDriver) {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM race_has_car_and_driver WHERE (idRace = " + idRace + " AND idDriver ="+ idDriver +");");

        //INSERT INTO RaceHasCarAndDriver (nameRace, lengthRace, locationRace) VALUES ('Monte Racelo',456,'Dupnica');
        RaceHasCarAndDriver raceHasCarAndDriver = null;
        try {
            resultSet.next();
            raceHasCarAndDriver = new RaceHasCarAndDriver(Integer.parseInt(resultSet.getString("id")),Integer.parseInt(resultSet.getString("idRace")),Integer.parseInt(resultSet.getString("idCar")),Integer.parseInt(resultSet.getString("idDriver")),Integer.parseInt(resultSet.getString("points")));

      if(raceHasCarAndDriver.getIdRace() > 0){
          return true;
      } else {
          return false;
      }
        } catch (SQLException e) {
        //TODO: Екран за грешка
    }
        return false;
    }

    public static void updateRaceHasCarAndDriver(RaceHasCarAndDriver raceHasCarAndDriver) {
        //'
        Database.execute("UPDATE race_has_car_and_driver SET idRace = " + raceHasCarAndDriver.getIdRace() + ", idCar = " + raceHasCarAndDriver.getIdCar() + ", idDriver = " + raceHasCarAndDriver.getIdDriver() + ", points = " + raceHasCarAndDriver.getPoints() + " WHERE id =  " + raceHasCarAndDriver.getId() + ";");
        //INSERT INTO RaceHasCarAndDriver (nameRace, lengthRace, locationRace) VALUES ('Monte Racelo',456,'Dupnica');
    }

    public static void deleteRaceHasCarAndDriver(int idRaceHasCarAndDriver) {
        Database.execute("DELETE FROM race_has_car_and_driver WHERE id = " + idRaceHasCarAndDriver + ";");
        // DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
    }

    public static ArrayList<RaceHasCarAndDriver> getAllRaceHasCarAndDriver() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM race_has_car_and_driver;");

        ArrayList<RaceHasCarAndDriver> allRaceHasCarAndDriver = new ArrayList<>();
        try {
            while ((resultSet.next())) {
                RaceHasCarAndDriver raceHasCarAndDriver = new RaceHasCarAndDriver(Integer.parseInt(resultSet.getString("id")),Integer.parseInt(resultSet.getString("idRace")),Integer.parseInt(resultSet.getString("idCar")),Integer.parseInt(resultSet.getString("idDriver")),Integer.parseInt(resultSet.getString("points")));
                allRaceHasCarAndDriver.add(raceHasCarAndDriver);
            }
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return allRaceHasCarAndDriver;
    }
    public static ArrayList<RaceHasCarAndDriver> getAllRaceHasCarAndDriver(int idRace) {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM race_has_car_and_driver;");

        ArrayList<RaceHasCarAndDriver> allRaceHasCarAndDriver = new ArrayList<>();
        try {
            while ((resultSet.next())) {

                RaceHasCarAndDriver raceHasCarAndDriver = new RaceHasCarAndDriver(Integer.parseInt(resultSet.getString("id")),Integer.parseInt(resultSet.getString("idRace")),Integer.parseInt(resultSet.getString("idCar")),Integer.parseInt(resultSet.getString("idDriver")),Integer.parseInt(resultSet.getString("points")));
                if(raceHasCarAndDriver.getIdRace() == idRace) {
                    allRaceHasCarAndDriver.add(raceHasCarAndDriver);
                }
            }
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return allRaceHasCarAndDriver;
    }

    public static int getNumberParticipants(Race race) {
        ResultSet resultSet = Database.executeQuery("SELECT COUNT(IF(idRace = "+race.getIdRace()+", 1, NULL)) AS 'Number' FROM race_has_car_and_driver;");
        int number = 0;
        try {
            resultSet.next();
            number =Integer.parseInt(resultSet.getString("Number"));
        } catch (SQLException e) {
            //TODO: Екран за грешка
        }
        return number;
    }
    public static boolean areTherePlacesAvailable(Race race) {
        ResultSet resultSet = Database.executeQuery("SELECT COUNT(IF(idRace = "+race.getIdRace()+", 1, NULL)) AS 'Number' FROM race_has_car_and_driver;");
        int number;
        try {
            resultSet.next();
            number =Integer.parseInt(resultSet.getString("Number"));

            return number < race.getParticipantsRace();
        } catch (SQLException e) {
            //TODO: Екран за грешка
            return false;
        }
    }
}