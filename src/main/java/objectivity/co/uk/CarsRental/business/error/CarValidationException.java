package objectivity.co.uk.CarsRental.business.error;

public class CarValidationException extends CarException {

    public CarValidationException(CarError errorCode) {
        super(errorCode);
    }
}
