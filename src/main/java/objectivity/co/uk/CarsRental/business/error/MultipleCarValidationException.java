package objectivity.co.uk.CarsRental.business.error;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class MultipleCarValidationException extends RuntimeException {

    private final Map<Integer, List<CarValidationResult>> validationResults;

    public MultipleCarValidationException(Map<Integer, List<CarValidationResult>> validationResults) {
        super("At least one car has validation errors");
        this.validationResults = validationResults;
    }
}
