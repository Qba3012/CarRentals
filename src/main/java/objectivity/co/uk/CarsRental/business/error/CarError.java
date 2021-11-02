package objectivity.co.uk.CarsRental.business.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CarError {
    BANNED_MODEL("Car model is banned"),
    WRONG_PURCHASE_DATE("Purchase date is in future"),
    WRONG_VIN_FORMAT("VIN should have a valid format");

    private final String description;
}