package objectivity.co.uk.CarsRental.business.validation;

import objectivity.co.uk.CarsRental.business.error.MultipleCarValidationException;
import objectivity.co.uk.CarsRental.business.error.SingleCarValidationException;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static objectivity.co.uk.CarsRental.business.error.CarError.*;
import static objectivity.co.uk.CarsRental.dummies.CarDummies.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class CarValidatorImplTest {

    @InjectMocks
    CarValidatorImpl carValidator;

    @Mock
    BannedCarsRepository bannedCarsRepository;

    @BeforeEach
    public void setUpMockRepository() {
        BDDMockito.given(bannedCarsRepository.getBannedModels()).willReturn(asList("Multipla", "Panda"));
    }

    @AfterEach
    public void resetDummyData() {
        resetDummnyData();
    }

    @Nested
    @DisplayName("Single car validation")
    class CarValidatorImplTestForSingleCar {

        @Test
        public void shouldNotThrowExceptionIfNoValidationErrors() {
            // when // then
            assertThatNoException().isThrownBy(() -> carValidator.validate(car1));
        }

        @Test
        public void shouldThrowExceptionIfCarModelIsBanned() {
            // given
            car1.setModel("Panda");

            // when // then
            assertThatThrownBy(() -> carValidator.validate(car1))
                    .isInstanceOf(SingleCarValidationException.class)
                    .extracting("validationResults")
                    .asList()
                    .first()
                    .extracting("errorCode", "message")
                    .containsExactly(BANNED_MODEL.name(), BANNED_MODEL.getDescription());
        }

        @Test
        public void shouldThrowExceptionIfPurchaseDateIsInFuture() {
            // given
            car1.setPurchaseDate(LocalDateTime.now().plusDays(1));

            // when // then
            assertThatThrownBy(() -> carValidator.validate(car1))
                    .isInstanceOf(SingleCarValidationException.class)
                    .extracting("validationResults")
                    .asList()
                    .first()
                    .extracting("errorCode", "message")
                    .containsExactly(WRONG_PURCHASE_DATE.name(), WRONG_PURCHASE_DATE.getDescription());
        }

        @Test
        public void shouldThrowExceptionIfWrongVin() {
            // given
            car1.setVin("Definitely wrong vin");

            // when // then
            assertThatThrownBy(() -> carValidator.validate(car1))
                    .isInstanceOf(SingleCarValidationException.class)
                    .extracting("validationResults")
                    .asList()
                    .first()
                    .extracting("errorCode", "message")
                    .containsExactly(WRONG_VIN_FORMAT.name(), WRONG_VIN_FORMAT.getDescription());
        }
    }

    @Nested
    @DisplayName("Multiple cars validation")
    class CarValidatorImplTestForMultipleCars {

        @Test
        public void shouldNotThrowExceptionIfNoValidationErrors() {
            // when // then
            assertThatNoException().isThrownBy(() -> carValidator.validate(carList));
        }

        @Test
        public void shouldThrowExceptionIfCarModelIsBanned() {
            // given
            car1.setModel("Panda");

            // when // then
            assertThatThrownBy(() -> carValidator.validate(carList))
                    .isInstanceOf(MultipleCarValidationException.class)
                    .extracting("validationResults")
                    .asInstanceOf(InstanceOfAssertFactories.map(Integer.class, List.class))
                    .extractingByKey(0)
                    .asList()
                    .first()
                    .extracting("errorCode", "message")
                    .containsExactly(BANNED_MODEL.name(), BANNED_MODEL.getDescription());
        }

        @Test
        public void shouldThrowExceptionIfPurchaseDateIsInFuture() {
            // given
            car1.setPurchaseDate(LocalDateTime.now().plusDays(1));

            // when // then
            assertThatThrownBy(() -> carValidator.validate(carList))
                    .isInstanceOf(MultipleCarValidationException.class)
                    .extracting("validationResults")
                    .asInstanceOf(InstanceOfAssertFactories.map(Integer.class, List.class))
                    .extractingByKey(0)
                    .asList()
                    .first()
                    .extracting("errorCode", "message")
                    .containsExactly(WRONG_PURCHASE_DATE.name(), WRONG_PURCHASE_DATE.getDescription());
        }

        @Test
        public void shouldThrowExceptionIfWrongVin() {
            // given
            car1.setVin("Definitely wrong vin");

            // when // then
            assertThatThrownBy(() -> carValidator.validate(carList))
                    .isInstanceOf(MultipleCarValidationException.class)
                    .extracting("validationResults")
                    .asInstanceOf(InstanceOfAssertFactories.map(Integer.class, List.class))
                    .extractingByKey(0)
                    .asList()
                    .first()
                    .extracting("errorCode", "message")
                    .containsExactly(WRONG_VIN_FORMAT.name(), WRONG_VIN_FORMAT.getDescription());
        }
    }

}