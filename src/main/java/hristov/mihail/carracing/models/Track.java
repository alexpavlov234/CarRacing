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
        return idTrack;
    }

    public void setIdTrack(int idTrack) {
        this.idTrack = idTrack;
    }

    public String getNameTrack() {
        return nameTrack;
    }

    public void setNameTrack(String nameTrack) {
        this.nameTrack = nameTrack;
    }

    public int getLengthTrack() {
        return lengthTrack;
    }

    public void setLengthTrack(int lengthTrack) {
        this.lengthTrack = lengthTrack;
    }

    public String getLocationTrack() {
        return locationTrack;
    }

    public void setLocationTrack(String locationTrack) {
        this.locationTrack = locationTrack;
    }

    public String getImagePathTrack() {
        return imagePathTrack;
    }

    public void setImagePathTrack(String imagePathTrack) {
        this.imagePathTrack = imagePathTrack;
    }
}