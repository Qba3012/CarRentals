package objectivity.co.uk.CarsRental.repository;

import objectivity.co.uk.CarsRental.business.CarRepository;
import objectivity.co.uk.CarsRental.model.Car;
import objectivity.co.uk.CarsRental.model.Status;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class CarRepositoryImpl implements CarRepository {

    private List<Car> cars;

    @PostConstruct
    private void initMockData() {
        Car car1 = new Car(1L, "BMW", "E60", "black", new BigDecimal(30000), Status.AVAILABLE, Stream.of("Pasy " +
                "bezpieczeństwa", "Kierunkowskazy", "Podświetlane logo").collect(Collectors.toSet()), LocalDateTime.now(), LocalDateTime.now());
        Car car2 = new Car(2L, "BMW", "F10", "black", new BigDecimal(200000), Status.AVAILABLE, Stream.of("Pasy " +
                "bezpieczeństwa", "Kierunkowskazy", "Podświetlane logo").collect(Collectors.toSet()), LocalDateTime.now(), LocalDateTime.now());
        this.cars = new ArrayList<>();
        this.cars.add(car1);
        this.cars.add(car2);
    }

    private Long getNextId() {
        return this.cars.stream().map(Car::getId).max(Long::compare).map(id -> id + 1).orElse(1L);
    }

    @Override
    public Car save(Car car) {
        car.setId(getNextId());
        car.setCreateDate(LocalDateTime.now());
        car.setUpdateDate(LocalDateTime.now());
        this.cars.add(car);
        return car;
    }

    @Override
    public List<Car> findAll() {
        return this.cars;
    }

    @Override
    public Optional<Car> getById(Long id) {
        return this.cars.stream().filter(car -> id.equals(car.getId())).findFirst();
    }

    @Override
    public boolean deleteById(Long id) {
        return this.cars.removeIf(car -> id.equals(car.getId()));
    }

    @Override
    public Car update(Long id, Car car) {
        Optional<Car> existingCarOptional = this.cars.stream().filter(c -> id.equals(c.getId())).findFirst();
        if (existingCarOptional.isPresent()) {
            Car existingCar = existingCarOptional.get();
            existingCar.setMaker(car.getMaker());
            existingCar.setModel(car.getModel());
            existingCar.setStatus(car.getStatus());
            existingCar.setColor(car.getColor());
            existingCar.setFeatures(car.getFeatures());
            existingCar.setPrice(car.getPrice());
            existingCar.setUpdateDate(LocalDateTime.now());
            return existingCar;
        }
        return save(car);
    }
}
