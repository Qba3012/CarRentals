package objectivity.co.uk.CarsRental.business.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

import static java.lang.Character.*;

public class VinNumberValidator implements ConstraintValidator<VinNumber, Object> {
    private String vinField;
    private String purchaseDateField;

    @Override
    public void initialize(VinNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.vinField = constraintAnnotation.vinField();
        this.purchaseDateField = constraintAnnotation.purchaseDateField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object vinObject = new BeanWrapperImpl(value).getPropertyValue(vinField);
        Object purchaseDateObject = new BeanWrapperImpl(value).getPropertyValue(purchaseDateField);

        if(!(vinObject instanceof String) || !(purchaseDateObject instanceof LocalDateTime)) {
            return false;
        }
        String vin = (String) vinObject;
        LocalDateTime purchaseDate = (LocalDateTime) purchaseDateObject;
        String purchaseYear = String.valueOf(purchaseDate.getYear());

        if (vin.length() != 10 || !vin.endsWith(purchaseYear)) {
            return false;
        }
        char[] chars = vin.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i < 3 && !isLetter(chars[i])) {
                return false;
            }
            if (isWhitespace(chars[i])) {
                return false;
            }
            if (i == 4 && !isDigit(chars[i])) {
                return false;
            }
        }
        return true;
    }
}
