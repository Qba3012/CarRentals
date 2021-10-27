package objectivity.co.uk.CarsRental.business.error;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class CarException extends RuntimeException {
    private final CarError errorCode;

    public CarException(CarError errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
