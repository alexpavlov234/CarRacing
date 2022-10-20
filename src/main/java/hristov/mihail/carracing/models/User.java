package hristov.mihail.carracing.models;

public class User {
    private int _idUser;
    public int getidUser()
    {
        return this._idUser;
    }
    public void setidUser(int value)
    {
        this._idUser = value;
    }

    private String _emailUser;
    public String getemailUser()
    {
        return this._emailUser;
    }
    public void setemailUser(String value)
    {
        this._emailUser = value;
    }

    private String _passUser;
    public String getpassUser()
    {
        return this._passUser;
    }
    public void setpassUser(String value)
    {
        this._passUser = value;
    }

    private String _typeUser;
    public String gettypeUser()
    {
        return this._typeUser;
    }
    public void settypeUser(String value)
    {
        this._typeUser = value;
    }

    private int _userHasDriver;
    public int getuserHasDriver()
    {
        return this._userHasDriver;
    }
    public void setuserHasDriver(int value)
    {
        this._userHasDriver = value;
    }


    public User(int idUser_, String emailUser_, String passUser_, String typeUser_, int userHasDriver_)
    {
        this._idUser = idUser_;
        this._emailUser = emailUser_;
        this._passUser = passUser_;
        this._typeUser = typeUser_;
        this._userHasDriver = userHasDriver_;
    }
}