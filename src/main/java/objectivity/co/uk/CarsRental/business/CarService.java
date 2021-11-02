package objectivity.co.uk.CarsRental.business;

import objectivity.co.uk.CarsRental.model.Car;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
public interface CarService {

    List<Car> addCars(List<@Valid Car> car);

    List<Car> getAllCars();

    Optional<Car> getCarById(Long id);

    Car updateCar(Long id, @Valid Car car);

    boolean deleteCar(Long id);
}
