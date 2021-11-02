package objectivity.co.uk.CarsRental.business.error;

import lombok.Getter;

import java.util.List;

@Getter
public class SingleCarValidationException extends RuntimeException {
    private final List<CarValidationResult> validationResults;

    public SingleCarValidationException(List<CarValidationResult> validationResults) {
        super("Car validation resulted with errors");
        this.validationResults = validationResults;
    }

}
