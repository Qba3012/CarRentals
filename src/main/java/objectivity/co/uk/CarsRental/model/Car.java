package objectivity.co.uk.CarsRental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Car {

    private Long id;
    private String maker;
    private String model;
    private String vin;
    private BigDecimal price;
    private Status status;
    private Set<String> features;
    private LocalDateTime createDate;
    private LocalDateTime purchaseDate;
    private LocalDateTime updateDate;

}
