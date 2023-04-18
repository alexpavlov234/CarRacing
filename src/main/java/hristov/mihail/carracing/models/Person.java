package hristov.mihail.carracing.models;

public class Person {

    private int idPerson;
    private String firstNamePerson;
    private String middleNamePerson;
    private String lastNamePerson;
    private int agePerson;
    private String nationalityPerson;
    private int pointsPerson;
    private String imagePathPerson;
    private int carPerson;

    public Person(int idPerson, String firstNamePerson, String middleNamePerson, String lastNamePerson, int agePerson, String nationalityPerson, int pointsPerson, int carPerson, String imagePathPerson) {
        this.idPerson = idPerson;
        this.firstNamePerson = firstNamePerson;
        this.middleNamePerson = middleNamePerson;
        this.lastNamePerson = lastNamePerson;
        this.agePerson = agePerson;
        this.nationalityPerson = nationalityPerson;
        this.pointsPerson = pointsPerson;
        this.carPerson = carPerson;
        this.imagePathPerson = imagePathPerson;
    }

    public Person() {

    }

    public int getCarPerson() {
        return carPerson;
    }

    public void setCarPerson(int carPerson) {
        this.carPerson = carPerson;
    }

    public int getIdPerson() {
        return this.idPerson;
    }

    public void setIdPerson(int value) {
        this.idPerson = value;
    }

    public String getFirstNamePerson() {
        return this.firstNamePerson;
    }

    public void setFirstNamePerson(String value) {
        this.firstNamePerson = value;
    }

    public String getMiddleNamePerson() {
        return this.middleNamePerson;
    }

    public void setMiddleNamePerson(String value) {
        this.middleNamePerson = value;
    }

    public String getLastNamePerson() {
        return this.lastNamePerson;
    }

    public void setLastNamePerson(String value) {
        this.lastNamePerson = value;
    }

    public int getAgePerson() {
        return this.agePerson;
    }

    public void setAgePerson(int value) {
        this.agePerson = value;
    }

    public String getNationalityPerson() {
        return this.nationalityPerson;
    }

    public void setNationalityPerson(String value) {
        this.nationalityPerson = value;
    }

    public int getPointsPerson() {
        return this.pointsPerson;
    }

    public void setPointsPerson(int value) {
        this.pointsPerson = value;
    }

    public String getImagePathPerson() {
        return this.imagePathPerson;
    }

    public void setImagePathPerson(String value) {
        this.imagePathPerson = value;
    }


    public String getNamePerson() {
        return this.firstNamePerson + " " + this.lastNamePerson;
    }
}