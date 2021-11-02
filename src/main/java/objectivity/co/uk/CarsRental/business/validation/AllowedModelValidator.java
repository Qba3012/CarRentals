package objectivity.co.uk.CarsRental.business.validation;

import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@RequiredArgsConstructor
public class AllowedModelValidator implements ConstraintValidator<AllowedModel, String> {

    private final BannedCarsRepository bannedCarsRepository;

    @Override
    public void initialize(AllowedModel constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> bannedCars = bannedCarsRepository.getBannedModels();
        return !bannedCars.contains(value);
    }

}
