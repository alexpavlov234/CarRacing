package hristov.mihail.carracing.models;

public class Race
{
    private int _idRace;
    public int getidRace()
    {
        return this._idRace;
    }
    public void setidRace(int value)
    {
        this._idRace = value;
    }

    private int _trackRace;
    public int gettrackRace()
    {
        return this._trackRace;
    }
    public void settrackRace(int value)
    {
        this._trackRace = value;
    }

    private java.sql.Date _dateRace;
    public java.sql.Date getdateRace()
    {
        return this._dateRace;
    }
    public void setdateRace(java.sql.Date value)
    {
        this._dateRace = value;
    }

    private int _mileageRace;
    public int getmileageRace()
    {
        return this._mileageRace;
    }
    public void setmileageRace(int value)
    {
        this._mileageRace = value;
    }

    private int _lengthRace;
    public int getlengthRace()
    {
        return this._lengthRace;
    }
    public void setlengthRace(int value)
    {
        this._lengthRace = value;
    }

    private int _pointsRace;
    public int getpointsRace()
    {
        return this._pointsRace;
    }
    public void setpointsRace(int value)
    {
        this._pointsRace = value;
    }


    public Race(int idRace_, int trackRace_, java.sql.Date dateRace_, int mileageRace_, int lengthRace_, int pointsRace_)
    {
        this._idRace = idRace_;
        this._trackRace = trackRace_;
        this._dateRace = dateRace_;
        this._mileageRace = mileageRace_;
        this._lengthRace = lengthRace_;
        this._pointsRace = pointsRace_;
    }
}
