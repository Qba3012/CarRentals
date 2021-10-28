package objectivity.co.uk.CarsRental.dummies;

import objectivity.co.uk.CarsRental.model.Car;
import objectivity.co.uk.CarsRental.model.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class CarDummies {
    private final static String CORRECT_VIN_1 = "BMW57S2021";
    private final static String CORRECT_VIN_2 = "BMW56S2021";
    private final static String CORRECT_MODEL = "E60";

    public static Car car1 = new Car(1L, "BMW", CORRECT_MODEL, CORRECT_VIN_1, new BigDecimal(30000), Status.AVAILABLE, Stream.of(
            "Pasy bezpieczeństwa", "Kierunkowskazy", "Podświetlane logo").collect(Collectors.toSet()),
            LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
    public static Car car2 = new Car(2L, "BMW", CORRECT_MODEL, CORRECT_VIN_2, new BigDecimal(200000), Status.AVAILABLE,
            Stream.of("Pasy bezpieczeństwa", "Kierunkowskazy", "Podświetlane logo").collect(Collectors.toSet()),
            LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
    public static List<Car> carList = asList(car1, car2);

    public static void resetDummnyData() {
        car1.setModel(CORRECT_MODEL);
        car1.setVin(CORRECT_VIN_1);
        car1.setPurchaseDate(LocalDateTime.now());
        car2.setModel(CORRECT_MODEL);
        car2.setVin(CORRECT_VIN_2);
        car2.setPurchaseDate(LocalDateTime.now());
    }
}
