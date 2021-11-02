package objectivity.co.uk.CarsRental.business;

import lombok.RequiredArgsConstructor;
import objectivity.co.uk.CarsRental.business.validation.CarValidator;
import objectivity.co.uk.CarsRental.model.Car;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarValidator carValidator;

    @Override
    public List<Car> addCars(List<Car> cars) {
        carValidator.validate(cars);
        return carRepository.save(cars);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        return carRepository.getById(id);
    }

    @Override
    public Car updateCar(Long id, Car car) {
        carValidator.validate(car);
        return carRepository.update(id, car);
    }

    @Override
    public boolean deleteCar(Long id) {
        return carRepository.deleteById(id);
    }
}
