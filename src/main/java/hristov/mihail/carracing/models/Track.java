package hristov.mihail.carracing.models;

public class Track {
    private int idTrack;
    private String nameTrack;
    private int lengthTrack;
    private String locationTrack;
    private String imagePathTrack;

    public Track(String trackName, int trackLength, String trackLocation) {
        this.nameTrack = trackName;
        this.lengthTrack = trackLength;
        this.locationTrack = trackLocation;
    }


    public Track(int idTrack, String nameTrack, int lengthTrack, String locationTrack, String imagePathTrack) {
        this.idTrack = idTrack;
        this.nameTrack = nameTrack;
        this.lengthTrack = lengthTrack;
        this.locationTrack = locationTrack;
        this.imagePathTrack = imagePathTrack;
    }

    public Track(int idTrack, String nameTrack, int lengthTrack, String locationTrack) {
        this.idTrack = idTrack;
        this.nameTrack = nameTrack;
        this.lengthTrack = lengthTrack;
        this.locationTrack = locationTrack;
    }


    public int getIdTrack() {
        return this.idTrack;
    }

    public void setIdTrack(int value) {
        this.idTrack = value;
    }

    public String getTrackName() {
        return this.nameTrack;
    }

    public void setTrackName(String value) {
        this.nameTrack = value;
    }

    public int getTrackLength() {
        return this.lengthTrack;
    }

    public void setTrackLength(int value) {
        this.lengthTrack = value;
    }

    public String getTrackLocation() {
        return this.locationTrack;
    }

    public void setTrackLocation(String value) {
        this.locationTrack = value;
    }

    public String getTrackImagePath() {
        return this.imagePathTrack;
    }

    public void setTrackImagePath(String value) {
        this.imagePathTrack = value;
    }
}