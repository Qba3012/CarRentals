package objectivity.co.uk.CarsRental.business.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CarValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CarValidationException.class)
    ResponseEntity<CarExceptionResponse> handleException(CarValidationException exception) {
        return ResponseEntity.badRequest().body(new CarExceptionResponse(exception));
    }
}
