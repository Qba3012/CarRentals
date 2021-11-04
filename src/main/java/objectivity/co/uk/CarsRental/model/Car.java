package objectivity.co.uk.CarsRental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import objectivity.co.uk.CarsRental.business.validation.AllowedModel;
import objectivity.co.uk.CarsRental.business.validation.VinNumber;

import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@VinNumber(vinField = "vin", purchaseDateField = "purchaseDate")
public class Car {
    private Long id;
    private String maker;
    @AllowedModel
    private String model;
    private String vin;
    private BigDecimal price;
    private Status status;
    private Set<String> features;
    private LocalDateTime createDate;
    @PastOrPresent(message = "Purchase date: ${formatter.format('%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS',validatedValue)" +
            "} is in future!")
    private LocalDateTime purchaseDate;
    private LocalDateTime updateDate;
}
