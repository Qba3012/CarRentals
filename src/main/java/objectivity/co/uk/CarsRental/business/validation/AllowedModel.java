package objectivity.co.uk.CarsRental.business.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AllowedModelValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedModel {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Model: '${validatedValue}' is not allowed. Posh cars only!";
}
