package hristov.mihail.carracing.models;

public class User {

    private int idUser;
    private String emailUser;
    private String passUser;
    private String typeUser;
    private int userHasPerson;

    public User(int idUser, String emailUser, String passUser, String typeUser, int userHasPerson) {
        this.idUser = idUser;
        this.emailUser = emailUser;
        this.passUser = passUser;
        this.typeUser = typeUser;
        this.userHasPerson = userHasPerson;
    }

    public User(String emailUser, String passUser, String typeUser, int userHasPerson) {
        this.emailUser = emailUser;
        this.passUser = passUser;
        this.typeUser = typeUser;
        this.userHasPerson = userHasPerson;
    }

    public User(String emailUser, String passUser) {
        this.emailUser = emailUser;
        this.passUser = passUser;
    }

    public User(User user, int userHasPerson) {
        this.emailUser = user.emailUser;
        this.passUser = user.passUser;
        this.typeUser = user.typeUser;
        this.userHasPerson = userHasPerson;
    }

    public User() {

    }

    public int getIdUser() {
        return this.idUser;
    }

    public void setIdUser(int value) {
        this.idUser = value;
    }

    public String getEmailUser() {
        return this.emailUser;
    }

    public void setEmailUser(String value) {
        this.emailUser = value;
    }

    public String getPassUser() {
        return this.passUser;
    }

    public void setPassUser(String value) {
        this.passUser = value;
    }

    public String getTypeUser() {
        return this.typeUser;
    }

    public void setTypeUser(String value) {
        this.typeUser = value;
    }

    public boolean isAdmin() {
        return this.typeUser.equals("Admin");
    }

    public int getUserHasPerson() {
        return this.userHasPerson;
    }

    public void setUserHasPerson(int value) {
        this.userHasPerson = value;
    }
}