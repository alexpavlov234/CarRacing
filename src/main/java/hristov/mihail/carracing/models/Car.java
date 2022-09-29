package hristov.mihail.carracing.models;

public class Car
{
    private int _idCar;
    public int getidCar()
    {
        return this._idCar;
    }
    public void setidCar(int value)
    {
        this._idCar = value;
    }

    private String _modelCar;
    public String getmodelCar()
    {
        return this._modelCar;
    }
    public void setmodelCar(String value)
    {
        this._modelCar = value;
    }

    private String _brandCar;
    public String getbrandCar()
    {
        return this._brandCar;
    }
    public void setbrandCar(String value)
    {
        this._brandCar = value;
    }

    private int _mileageCar;
    public int getmileageCar()
    {
        return this._mileageCar;
    }
    public void setmileageCar(int value)
    {
        this._mileageCar = value;
    }

    private java.sql.Date _serviceDate;
    public java.sql.Date getserviceDate()
    {
        return this._serviceDate;
    }
    public void setserviceDate(java.sql.Date value)
    {
        this._serviceDate = value;
    }

    private String _imagePathCar;
    public String getimagePathCar()
    {
        return this._imagePathCar;
    }
    public void setimagePathCar(String value)
    {
        this._imagePathCar = value;
    }


    public Car(int idCar_,String modelCar_,String brandCar_,int mileageCar_,java.sql.Date serviceDate_,String imagePathCar_,Object CHARACTER_)
    {
        this._idCar = idCar_;
        this._modelCar = modelCar_;
        this._brandCar = brandCar_;
        this._mileageCar = mileageCar_;
        this._serviceDate = serviceDate_;
        this._imagePathCar = imagePathCar_;
    }
}