package hristov.mihail.carracing.models;

public class Track
{
    private int _idTrack;
    public int getidTrack()
    {
        return this._idTrack;
    }
    public void setidTrack(int value)
    {
        this._idTrack = value;
    }

    private String _trackName;
    public String gettrackName()
    {
        return this._trackName;
    }
    public void settrackName(String value)
    {
        this._trackName = value;
    }

    private int _trackLength;
    public int gettrackLength()
    {
        return this._trackLength;
    }
    public void settrackLength(int value)
    {
        this._trackLength = value;
    }

    private String _trackLocation;
    public String gettrackLocation()
    {
        return this._trackLocation;
    }
    public void settrackLocation(String value)
    {
        this._trackLocation = value;
    }


    public Track(int idTrack_, String trackName_, int trackLength_, String trackLocation_)
    {
        this._idTrack = idTrack_;
        this._trackName = trackName_;
        this._trackLength = trackLength_;
        this._trackLocation = trackLocation_;
    }
}