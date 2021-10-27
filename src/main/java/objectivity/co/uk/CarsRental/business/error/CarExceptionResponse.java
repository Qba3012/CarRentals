package objectivity.co.uk.CarsRental.business.error;

import lombok.Getter;

@Getter
public class CarExceptionResponse {
    private final String errorCode;
    private final String message;

    public CarExceptionResponse(CarException exception) {
        this.errorCode = exception.getErrorCode().name();
        this.message = exception.getMessage();
    }
}
