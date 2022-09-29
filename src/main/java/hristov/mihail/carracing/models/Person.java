package hristov.mihail.carracing.models;

public class Person
{
    private int _idPerson;
    public int getidPerson()
    {
        return this._idPerson;
    }
    public void setidPerson(int value)
    {
        this._idPerson = value;
    }

    private String _firstNamePerson;
    public String getfirstNamePerson()
    {
        return this._firstNamePerson;
    }
    public void setfirstNamePerson(String value)
    {
        this._firstNamePerson = value;
    }

    private String _middleNamePerson;
    public String getmiddleNamePerson()
    {
        return this._middleNamePerson;
    }
    public void setmiddleNamePerson(String value)
    {
        this._middleNamePerson = value;
    }

    private String _lastNamePerson;
    public String getlastNamePerson()
    {
        return this._lastNamePerson;
    }
    public void setlastNamePerson(String value)
    {
        this._lastNamePerson = value;
    }

    private int _agePerson;
    public int getagePerson()
    {
        return this._agePerson;
    }
    public void setagePerson(int value)
    {
        this._agePerson = value;
    }

    private String _nationalityPerson;
    public String getnationalityPerson()
    {
        return this._nationalityPerson;
    }
    public void setnationalityPerson(String value)
    {
        this._nationalityPerson = value;
    }

    private int _pointsPerson;
    public int getpointsPerson()
    {
        return this._pointsPerson;
    }
    public void setpointsPerson(int value)
    {
        this._pointsPerson = value;
    }

    private String _imagePathPerson;
    public String getimagePathPerson()
    {
        return this._imagePathPerson;
    }
    public void setimagePathPerson(String value)
    {
        this._imagePathPerson = value;
    }


    public Person(int idPerson_,String firstNamePerson_,String middleNamePerson_,String lastNamePerson_,int agePerson_,String nationalityPerson_,int pointsPerson_,String imagePathPerson_)
    {
        this._idPerson = idPerson_;
        this._firstNamePerson = firstNamePerson_;
        this._middleNamePerson = middleNamePerson_;
        this._lastNamePerson = lastNamePerson_;
        this._agePerson = agePerson_;
        this._nationalityPerson = nationalityPerson_;
        this._pointsPerson = pointsPerson_;
        this._imagePathPerson = imagePathPerson_;
    }
}