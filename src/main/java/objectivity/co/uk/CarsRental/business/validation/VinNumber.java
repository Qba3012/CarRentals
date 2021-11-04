package objectivity.co.uk.CarsRental.business.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = VinNumberValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VinNumber {
    String message() default "VIN: ${validatedValue.vin} is invalid format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String purchaseDateField();

    String vinField();

}
