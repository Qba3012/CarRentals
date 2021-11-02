package objectivity.co.uk.CarsRental.business.validation;

import lombok.RequiredArgsConstructor;
import objectivity.co.uk.CarsRental.business.error.CarValidationResult;
import objectivity.co.uk.CarsRental.business.error.MultipleCarValidationException;
import objectivity.co.uk.CarsRental.business.error.SingleCarValidationException;
import objectivity.co.uk.CarsRental.model.Car;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static objectivity.co.uk.CarsRental.business.error.CarError.*;

@Service
@RequiredArgsConstructor
public class CarValidatorImpl implements CarValidator {

    private final BannedCarsRepository bannedCarsRepository;

    @Override
    public void validate(List<Car> cars) throws MultipleCarValidationException {
        Map<Integer, List<CarValidationResult>> validationResults = new HashMap<>();
        for (int i = 0; i < cars.size(); i++) {
            List<CarValidationResult> results = new ArrayList<>();
            validateModel(cars.get(i), results);
            validatePurchaseDate(cars.get(i), results);
            validateVinNumber(cars.get(i), results);
            if (!results.isEmpty()) {
                validationResults.put(i, results);
            }
        }
        if (!validationResults.isEmpty()) {
            throw new MultipleCarValidationException(validationResults);
        }
    }

    @Override
    public void validate(Car car) throws SingleCarValidationException {
        List<CarValidationResult> results = new ArrayList<>();
        validateModel(car, results);
        validatePurchaseDate(car, results);
        validateVinNumber(car, results);
        if (!results.isEmpty()) {
            throw new SingleCarValidationException(results);
        }
    }

    private void validateModel(Car car, List<CarValidationResult> results) {
        List<String> bannedModels = bannedCarsRepository.getBannedModels();
        if (null != bannedModels && bannedModels.contains(car.getModel())) {
            results.add(new CarValidationResult(BANNED_MODEL));
        }
    }

    private void validatePurchaseDate(Car car, List<CarValidationResult> results) {
        if (null == car.getPurchaseDate() || car.getPurchaseDate().isAfter(LocalDateTime.now())) {
            results.add(new CarValidationResult(WRONG_PURCHASE_DATE));
        }
    }

    private void validateVinNumber(Car car, List<CarValidationResult> results) {
        if (null == car.getVin()) {
            results.add(new CarValidationResult(WRONG_VIN_FORMAT));
        }
        String regex = "^[a-zA-Z]{3}\\w\\d\\w" + car.getPurchaseDate().getYear() + "$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(car.getVin());
        if (!matcher.matches()) {
            results.add(new CarValidationResult(WRONG_VIN_FORMAT));
        }
    }

}
