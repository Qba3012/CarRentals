package objectivity.co.uk.CarsRental.business.validation;

import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.util.Objects;

@Getter
public class CarValidationResponse {
    private final String message;

    public CarValidationResponse(ConstraintViolation<?> violation) {
        this.message = violation.getMessage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarValidationResponse that = (CarValidationResponse) o;
        return message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

}
