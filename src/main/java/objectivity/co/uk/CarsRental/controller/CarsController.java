package objectivity.co.uk.CarsRental.controller;

import lombok.RequiredArgsConstructor;
import objectivity.co.uk.CarsRental.business.CarService;
import objectivity.co.uk.CarsRental.model.Car;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarsController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<List<Car>> addCars(@RequestBody List<Car> cars) {
        List<Car> newCars = carService.addCars(cars);
        return ResponseEntity.ok().body(newCars);
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> carOptional = carService.getCarById(id);
        return carOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        Car updatedCar = carService.updateCar(id, car);
        return ResponseEntity.ok(updatedCar);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Long id) {
        if (carService.deleteCar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
