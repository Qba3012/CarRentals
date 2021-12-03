package objectivity.co.uk.CarsRental.business.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CarValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = MultipleCarValidationException.class)
    ResponseEntity<Map<Integer, List<CarValidationResult>>> handleException(MultipleCarValidationException exception) {
        return ResponseEntity.badRequest().body(exception.getValidationResults());
    }

    @ExceptionHandler(value = SingleCarValidationException.class)
    ResponseEntity<List<CarValidationResult>> handleException(SingleCarValidationException exception) {
        return ResponseEntity.badRequest().body(exception.getValidationResults());
    }
}
