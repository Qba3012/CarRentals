package objectivity.co.uk.CarsRental.business;

import objectivity.co.uk.CarsRental.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Car save(Car car);

    List<Car> save(List<Car> car);

    List<Car> findAll();

    Optional<Car> getById(Long id);

    boolean deleteById(Long id);

    Car update(Long id, Car car);
}
