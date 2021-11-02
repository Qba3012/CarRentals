package objectivity.co.uk.CarsRental.business.error;

import lombok.Getter;

@Getter
public class CarValidationResult {
    private final String errorCode;
    private final String message;

    public CarValidationResult(CarError carError) {
        this.errorCode = carError.name();
        this.message = carError.getDescription();
    }
}
