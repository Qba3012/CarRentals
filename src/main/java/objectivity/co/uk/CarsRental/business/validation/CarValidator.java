package objectivity.co.uk.CarsRental.business.validation;

import objectivity.co.uk.CarsRental.business.error.CarValidationException;
import objectivity.co.uk.CarsRental.model.Car;

public interface CarValidator {

    void validate(Car car) throws CarValidationException;
}
