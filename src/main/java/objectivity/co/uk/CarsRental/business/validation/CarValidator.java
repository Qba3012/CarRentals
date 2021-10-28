package objectivity.co.uk.CarsRental.business.validation;

import objectivity.co.uk.CarsRental.business.error.MultipleCarValidationException;
import objectivity.co.uk.CarsRental.business.error.SingleCarValidationException;
import objectivity.co.uk.CarsRental.model.Car;

import java.util.List;

public interface CarValidator {

    void validate(List<Car> car) throws MultipleCarValidationException;

    void validate(Car car) throws SingleCarValidationException;
}
