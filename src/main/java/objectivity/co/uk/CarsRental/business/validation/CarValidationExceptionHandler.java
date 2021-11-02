package objectivity.co.uk.CarsRental.business.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class CarValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    ResponseEntity<Set<CarValidationResponse>> handleException(ConstraintViolationException exception) {
        return ResponseEntity.badRequest().body(exception.getConstraintViolations().stream().map(CarValidationResponse::new).collect(Collectors.toSet()));
    }

}
