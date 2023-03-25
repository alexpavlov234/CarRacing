package hristov.mihail.carracing.models;

import java.sql.Blob;

public class Car {
    private int idCar;
    private String modelCar;
    private String brandCar;
    private String engineCar;
    private String fuelCar;
    private int horsepowerCar;
    private java.sql.Blob imageCar;

    public Car(String modelCar, String brandCar, String engineCar, String fuelCar, int horsepowerCar) {
        this.modelCar = modelCar;
        this.brandCar = brandCar;
        this.engineCar = engineCar;
        this.fuelCar = fuelCar;
        this.horsepowerCar = horsepowerCar;
    }

    public Car(String modelCar, String brandCar, String engineCar, String fuelCar, int horsepowerCar, Blob imageCar) {
        this.modelCar = modelCar;
        this.brandCar = brandCar;
        this.engineCar = engineCar;
        this.fuelCar = fuelCar;
        this.horsepowerCar = horsepowerCar;
        this.imageCar = imageCar;
    }


    public Car(int IdCar, String ModelCar, String BrandCar, String EngineCar, String FuelCar, int HorsepowerCar, Blob imageCar) {
        this.idCar = IdCar;
        this.modelCar = ModelCar;
        this.brandCar = BrandCar;
        this.engineCar = EngineCar;
        this.fuelCar = FuelCar;
        this.horsepowerCar = HorsepowerCar;
        this.imageCar = imageCar;
    }

    public Car(int IdCar, String ModelCar, String BrandCar, String EngineCar, String FuelCar, int HorsepowerCar) {
        this.idCar = IdCar;
        this.modelCar = ModelCar;
        this.brandCar = BrandCar;
        this.engineCar = EngineCar;
        this.fuelCar = FuelCar;
        this.horsepowerCar = HorsepowerCar;
    }

    public int getIdCar() {
        return this.idCar;
    }

    public void setIdCar(int value) {
        this.idCar = value;
    }

    public String getModelCar() {
        return this.modelCar;
    }

    public void setModelCar(String value) {
        this.modelCar = value;
    }

    public String getBrandCar() {
        return this.brandCar;
    }

    public void setBrandCar(String value) {
        this.brandCar = value;
    }

    public String getEngineCar() {
        return this.engineCar;
    }

    public void setEngineCar(String value) {
        this.engineCar = value;
    }

    public String getFuelCar() {
        return this.fuelCar;
    }

    public void setFuelCar(String value) {
        this.fuelCar = value;
    }

    public int getHorsepowerCar() {
        return this.horsepowerCar;
    }

    public void setHorsepowerCar(int value) {
        this.horsepowerCar = value;
    }

    public Blob getImageCar() {
        return this.imageCar;
    }

    public void setImageCar(Blob value) {
        this.imageCar = value;
    }

    public String getNameCar() {
        return this.brandCar + " " + this.modelCar;
    }
}