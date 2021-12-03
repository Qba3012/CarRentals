package objectivity.co.uk.CarsRental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String maker;
    private String model;
    private String vin;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createDate;
    private LocalDateTime purchaseDate;
    private LocalDateTime updateDate;

}
