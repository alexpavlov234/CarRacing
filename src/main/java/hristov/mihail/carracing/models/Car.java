package hristov.mihail.carracing.models;

public class Car {
    private int idCar;
    private String modelCar;
    private String brandCar;
    private String engineCar;
    private String fuelCar;
    private int horsepowerCar;
    private String imagePathCar;

    public Car(int IdCar, String ModelCar, String BrandCar, String EngineCar, String FuelCar, int HorsepowerCar, String ImagePathCar) {
        this.idCar = IdCar;
        this.modelCar = ModelCar;
        this.brandCar = BrandCar;
        this.engineCar = EngineCar;
        this.fuelCar = FuelCar;
        this.horsepowerCar = HorsepowerCar;
        this.imagePathCar = ImagePathCar;
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

    public String getImagePathCar() {
        return this.imagePathCar;
    }

    public void setImagePathCar(String value) {
        this.imagePathCar = value;
    }
}