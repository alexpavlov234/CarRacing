import hristov.mihail.carracing.models.Car;
import hristov.mihail.carracing.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    private Car car;

    @BeforeEach
    void setUp() throws Exception {
        car = new Car(1, "Model X", "Tesla", "Electric", "Battery", 500);
    }

    @Test
    void testAddCar() {
        // Ensure that the car is not in the database yet
        assertNull(CarService.getLastCar());

        // Add the car
        CarService.addCar(car);

        // Ensure that the car is now in the database and all of its attributes match
        Car carFromDb = CarService.getLastCar();
        
        assertEquals(car.getModelCar(), carFromDb.getModelCar());
        assertEquals(car.getBrandCar(), carFromDb.getBrandCar());
        assertEquals(car.getFuelCar(), carFromDb.getFuelCar());
        assertEquals(car.getEngineCar(), carFromDb.getEngineCar());
        assertEquals(car.getHorsepowerCar(), carFromDb.getHorsepowerCar());
    }


    @Test
    void testGetCarName() {
        assertEquals("Tesla Model X", CarService.getCarName(car));
    }

    @Test
    void testGetCarById() {
        // Ensure that the car is not in the database yet
        assertNull(CarService.getLastCar());

        // Add the car
        CarService.addCar(car);

        // Ensure that the car can be retrieved by ID
        Car carFromDb = CarService.getLastCar();
        assertNotNull(carFromDb);
        
        assertEquals(car.getModelCar(), carFromDb.getModelCar());
        assertEquals(car.getBrandCar(), carFromDb.getBrandCar());
        assertEquals(car.getFuelCar(), carFromDb.getFuelCar());
        assertEquals(car.getEngineCar(), carFromDb.getEngineCar());
        assertEquals(car.getHorsepowerCar(), carFromDb.getHorsepowerCar());
    }

    @Test
    void testGetCarByName() {
        // Ensure that the car is not in the database yet
        assertNull(CarService.getLastCar());

        // Add the car
        CarService.addCar(car);

        // Ensure that the car can be retrieved by name
        Car carFromDb = CarService.getCar("Tesla Model X");
        assertNotNull(carFromDb);
        
        assertEquals(car.getModelCar(), carFromDb.getModelCar());
        assertEquals(car.getBrandCar(), carFromDb.getBrandCar());
        assertEquals(car.getFuelCar(), carFromDb.getFuelCar());
        assertEquals(car.getEngineCar(), carFromDb.getEngineCar());
        assertEquals(car.getHorsepowerCar(), carFromDb.getHorsepowerCar());
    }

    @Test
    void testGetLastCar() {
        // Ensure that the database is empty
        assertNull(CarService.getLastCar());

        // Add the car
        CarService.addCar(car);

        // Ensure that the car is the last car in the database
        Car lastCar = CarService.getLastCar();
        assertNotNull(lastCar);
        assertEquals(car.getIdCar(), lastCar.getIdCar());
        assertEquals(car.getModelCar(), lastCar.getModelCar());
        assertEquals(car.getBrandCar(), lastCar.getBrandCar());
        assertEquals(car.getFuelCar(), lastCar.getFuelCar());
        assertEquals(car.getEngineCar(), lastCar.getEngineCar());
        assertEquals(car.getHorsepowerCar(), lastCar.getHorsepowerCar());
    }

    @Test
    void testUpdateCar() {
        // Add the car
        CarService.addCar(car);

        // Update the car
        car.setHorsepowerCar(600);
        CarService.updateCar(car);

        // Ensure that the car was updated
        Car carFromDb = CarService.getCar(car.getModelCar());
        assertNotNull(carFromDb);
        
        assertEquals(car.getModelCar(), carFromDb.getModelCar());
        assertEquals(car.getBrandCar(), carFromDb.getBrandCar());
        assertEquals(car.getFuelCar(), carFromDb.getFuelCar());
        assertEquals(car.getEngineCar(), carFromDb.getEngineCar());
        assertEquals(car.getHorsepowerCar(), carFromDb.getHorsepowerCar());
    }

    @Test
    void testDeleteCar() {
        // Add the car
        CarService.addCar(car);

        // Delete the car
        CarService.deleteCar(car.getIdCar());

        // Ensure that the car is no longer in the database
        assertNull(CarService.getLastCar());
    }
}
