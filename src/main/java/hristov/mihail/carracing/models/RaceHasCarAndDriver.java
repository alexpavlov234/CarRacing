package hristov.mihail.carracing.models;

public class RaceHasCarAndDriver
{
    private int id;
    private int idRace;
    private int idCar;
    private int idDriver;

    public RaceHasCarAndDriver() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRace() {
        return idRace;
    }

    public void setIdRace(int idRace) {
        this.idRace = idRace;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public RaceHasCarAndDriver(int idRace, int idCar, int idDriver, int points) {
        this.idRace = idRace;
        this.idCar = idCar;
        this.idDriver = idDriver;
        this.points = points;
    }

    public RaceHasCarAndDriver(int id, int idRace, int idCar, int idDriver, int points) {
        this.id = id;
        this.idRace = idRace;
        this.idCar = idCar;
        this.idDriver = idDriver;
        this.points = points;
    }

    private int points;
}