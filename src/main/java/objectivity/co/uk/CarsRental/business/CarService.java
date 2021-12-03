package objectivity.co.uk.CarsRental.business;

import objectivity.co.uk.CarsRental.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> addCars(List<Car> car);

    List<Car> getAllCars();

    Optional<Car> getCarById(Long id);

    Car updateCar(Long id, Car car);

    boolean deleteCar(Long id);
}
