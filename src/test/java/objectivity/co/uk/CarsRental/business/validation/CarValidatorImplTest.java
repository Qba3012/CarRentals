package objectivity.co.uk.CarsRental.business.validation;

import objectivity.co.uk.CarsRental.business.error.CarValidationException;
import objectivity.co.uk.CarsRental.model.Car;
import objectivity.co.uk.CarsRental.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static objectivity.co.uk.CarsRental.business.error.CarError.*;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class CarValidatorImplTest {

    private final Car testCar = new Car(1L, "BMW", "E60", "BMW57S2021", new BigDecimal(30000), Status.AVAILABLE, Stream.of(
            "Pasy bezpieczeństwa", "Kierunkowskazy", "Podświetlane logo").collect(Collectors.toSet()),
            LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());

    @InjectMocks
    CarValidatorImpl carValidator;

    @Mock
    BannedCarsRepository bannedCarsRepository;

    @BeforeEach
    public void setMockRepository() {
        // given
        testCar.setModel("E60");
        testCar.setPurchaseDate(LocalDateTime.now());
        testCar.setVin("BMW57S2021");
        BDDMockito.given(bannedCarsRepository.getBannedModels()).willReturn(asList("Multipla", "Panda"));
    }

    @Test
    public void shouldNotThrowExceptionIfNoValidationErrors() {
        // when // then
        assertThatNoException().isThrownBy(() -> carValidator.validate(testCar));
    }

    @Test
    public void shouldThrowExceptionIfCarModelIsBanned() {
        // given
        testCar.setModel("Panda");

        // when // then
        assertThatThrownBy(() -> carValidator.validate(testCar))
                .isInstanceOf(CarValidationException.class)
                .hasMessage(BANNED_MODEL.getDescription());
    }

    @Test
    public void shouldThrowExceptionIfPurchaseDateIsInFuture() {
        // given
        testCar.setPurchaseDate(LocalDateTime.now().plusDays(1));

        // when // then
        assertThatThrownBy(() -> carValidator.validate(testCar))
                .isInstanceOf(CarValidationException.class)
                .hasMessage(WRONG_PURCHASE_DATE.getDescription());
    }


    @Test
    public void shouldThrowExceptionIfWrongVin() {
        // given
        testCar.setVin("Definitely wrong vin");

        // when // then
        assertThatThrownBy(() -> carValidator.validate(testCar))
                .isInstanceOf(CarValidationException.class)
                .hasMessage(WRONG_VIN_FORMAT.getDescription());
    }

}