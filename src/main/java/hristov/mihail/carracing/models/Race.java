package hristov.mihail.carracing.models;

import hristov.mihail.carracing.services.TrackService;

public class Race {

    private int idRace;
    private int trackRace;
    private String dateRace;
    private int lapsRace;
    private int pointsRace;
    private int participantsRace;

    public Race(int trackRace, String dateRace, int lapsRace, int pointsRace, int participantsRace) {
        this.trackRace = trackRace;
        this.dateRace = dateRace;
        this.lapsRace = lapsRace;
        this.pointsRace = pointsRace;
        this.participantsRace = participantsRace;
    }

    public Race(int IdRace, int TrackRace, String DateRace, int LapsRace, int PointsRace, int ParticipantsRace) {
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

    public String getDateRace() {
        return this.dateRace;
    }

    public void setDateRace(String value) {
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

    public String getNameRace() {
        return TrackService.getTrack(this.trackRace).getNameTrack() + " / " + this.dateRace;
    }
}
