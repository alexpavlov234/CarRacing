package hristov.mihail.carracing.models;

public class Race {
    private int idRace;
    private int trackRace;
    private java.sql.Date dateRace;
    private int lapsRace;
    private int pointsRace;
    private int participantsRace;

    public Race(int IdRace, int TrackRace, java.sql.Date DateRace, int LapsRace, int PointsRace, int ParticipantsRace) {
        this.idRace = IdRace;
        this.trackRace = TrackRace;
        this.dateRace = DateRace;
        this.lapsRace = LapsRace;
        this.pointsRace = PointsRace;
        this.participantsRace = ParticipantsRace;
    }

    public int getIdRace() {
        return this.idRace;
    }

    public void setIdRace(int value) {
        this.idRace = value;
    }

    public int getTrackRace() {
        return this.trackRace;
    }

    public void setTrackRace(int value) {
        this.trackRace = value;
    }

    public java.sql.Date getDateRace() {
        return this.dateRace;
    }

    public void setDateRace(java.sql.Date value) {
        this.dateRace = value;
    }

    public int getLapsRace() {
        return this.lapsRace;
    }

    public void setLapsRace(int value) {
        this.lapsRace = value;
    }

    public int getPointsRace() {
        return this.pointsRace;
    }

    public void setPointsRace(int value) {
        this.pointsRace = value;
    }

    public int getParticipantsRace() {
        return this.participantsRace;
    }

    public void setParticipantsRace(int value) {
        this.participantsRace = value;
    }
}
