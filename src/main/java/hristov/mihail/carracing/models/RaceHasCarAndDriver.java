package hristov.mihail.carracing.models;

public class RaceHasCarAndDriver
{
    private int _id;
    public int getid()
    {
        return this._id;
    }
    public void setid(int value)
    {
        this._id = value;
    }

    private int _idRace;
    public int getidRace()
    {
        return this._idRace;
    }
    public void setidRace(int value)
    {
        this._idRace = value;
    }

    private int _idCar;
    public int getidCar()
    {
        return this._idCar;
    }
    public void setidCar(int value)
    {
        this._idCar = value;
    }

    private int _idDriver;
    public int getidDriver()
    {
        return this._idDriver;
    }
    public void setidDriver(int value)
    {
        this._idDriver = value;
    }

    private int _mileage;
    public int getmileage()
    {
        return this._mileage;
    }
    public void setmileage(int value)
    {
        this._mileage = value;
    }

    private int _length;
    public int getlength()
    {
        return this._length;
    }
    public void setlength(int value)
    {
        this._length = value;
    }

    private int _points;
    public int getpoints()
    {
        return this._points;
    }
    public void setpoints(int value)
    {
        this._points = value;
    }


    public RaceHasCarAndDriver(int id_, int idRace_, int idCar_, int idDriver_, int mileage_, int length_, int points_)
    {
        this._id = id_;
        this._idRace = idRace_;
        this._idCar = idCar_;
        this._idDriver = idDriver_;
        this._mileage = mileage_;
        this._length = length_;
        this._points = points_;
    }
}