package objectivity.co.uk.CarsRental.business.validation;

import lombok.RequiredArgsConstructor;
import objectivity.co.uk.CarsRental.business.error.CarError;
import objectivity.co.uk.CarsRental.business.error.CarValidationException;
import objectivity.co.uk.CarsRental.model.Car;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CarValidatorImpl implements CarValidator {

    private final BannedCarsRepository bannedCarsRepository;

    @Override
    public void validate(Car car) throws CarValidationException {
        validateModel(car);
        validatePurchaseDate(car);
        validateVinNumber(car);
    }

    private void validateModel(Car car) throws CarValidationException {
        List<String> bannedModels = bannedCarsRepository.getBannedModels();
        if (bannedModels.contains(car.getModel())) {
            throw new CarValidationException(CarError.BANNED_MODEL);
        }
    }

    private void validatePurchaseDate(Car car) throws CarValidationException {
        if (null == car.getPurchaseDate() || car.getPurchaseDate().isAfter(LocalDateTime.now())) {
            throw new CarValidationException(CarError.WRONG_PURCHASE_DATE);
        }
    }

    private void validateVinNumber(Car car) throws CarValidationException {
        if (null == car.getVin()) {
            throw new CarValidationException(CarError.WRONG_VIN_FORMAT);
        }
        String regex = "^[a-zA-Z]{3}\\w\\d\\w" + car.getPurchaseDate().getYear() + "$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(car.getVin());
        if (!matcher.matches()) {
            throw new CarValidationException(CarError.WRONG_VIN_FORMAT);
        }
    }

}
