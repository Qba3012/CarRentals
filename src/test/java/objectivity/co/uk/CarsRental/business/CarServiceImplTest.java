package objectivity.co.uk.CarsRental.business;

import objectivity.co.uk.CarsRental.business.error.CarError;
import objectivity.co.uk.CarsRental.business.error.CarValidationException;
import objectivity.co.uk.CarsRental.business.validation.CarValidator;
import objectivity.co.uk.CarsRental.model.Car;
import objectivity.co.uk.CarsRental.model.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    private final Car car1 = new Car(1L, "BMW", "E60", "BMW57S2021", new BigDecimal(30000), Status.AVAILABLE, Stream.of(
            "Pasy bezpieczeństwa", "Kierunkowskazy", "Podświetlane logo").collect(Collectors.toSet()),
            LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
    private final Car car2 = new Car(2L, "BMW", "F10", "BMW56S2021", new BigDecimal(200000), Status.AVAILABLE, Stream.of(
            "Pasy bezpieczeństwa", "Kierunkowskazy", "Podświetlane logo").collect(Collectors.toSet()),
            LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());

    @InjectMocks
    private CarServiceImpl carServiceImpl;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarValidator carValidator;

    @Test
    void shouldAddCarIfNoValidationErrors() {
        // given
        BDDMockito.given(carRepository.save(car1)).willReturn(car1);

        // when
        Car actualCar = carServiceImpl.addCar(car1);

        // then
        InOrder inOrder = inOrder(carValidator, carRepository);
        inOrder.verify(carValidator).validate(car1);
        inOrder.verify(carRepository).save(car1);
        assertThat(actualCar).usingRecursiveComparison().isEqualTo(car1);
    }

    @Test
    void shouldNotAddCarAndThrowExceptionIfValidationFails() {
        // given
        BDDMockito.willThrow(new CarValidationException(CarError.WRONG_VIN_FORMAT)).given(carValidator).validate(car1);

        // when // then
        assertThatThrownBy(() -> carServiceImpl.addCar(car1))
                .isInstanceOf(CarValidationException.class)
                .hasMessage(CarError.WRONG_VIN_FORMAT.getDescription());
        InOrder inOrder = inOrder(carValidator, carRepository);
        inOrder.verify(carValidator).validate(car1);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void getAllCars() {
        // given
        List<Car> carList = asList(car1, car2);
        BDDMockito.given(carRepository.findAll()).willReturn(carList);

        // when
        List<Car> allCars = carServiceImpl.getAllCars();

        // then
        assertThat(allCars).containsExactly(car1, car2);
    }

    @Test
    void getCarById() {
        // given
        BDDMockito.given(carRepository.getById(1L)).willReturn(Optional.of(car1));

        // when
        Optional<Car> actualCarOptional = carServiceImpl.getCarById(1L);

        // then
        assertThat(actualCarOptional).isPresent().get().isEqualTo(car1);
    }

    @Test
    void updateCarIfNoValidationErrors() {
        // given
        BDDMockito.given(carRepository.update(1L, car1)).willReturn(car1);

        // when
        Car actualCar = carServiceImpl.updateCar(1L, car1);

        // then
        InOrder inOrder = inOrder(carValidator, carRepository);
        inOrder.verify(carValidator).validate(car1);
        inOrder.verify(carRepository).update(1L, car1);
        assertThat(actualCar).isEqualTo(car1);
    }

    @Test
    void shouldNotUpdateCarAndThrowExceptionIfValidationFails() {
        // given
        BDDMockito.willThrow(new CarValidationException(CarError.WRONG_VIN_FORMAT)).given(carValidator).validate(car1);

        // when // then
        assertThatThrownBy(() -> carServiceImpl.updateCar(1L, car1))
                .isInstanceOf(CarValidationException.class)
                .hasMessage(CarError.WRONG_VIN_FORMAT.getDescription());
        InOrder inOrder = inOrder(carValidator, carRepository);
        inOrder.verify(carValidator).validate(car1);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void deleteCar() {
        // given
        BDDMockito.given(carServiceImpl.deleteCar(1L)).willReturn(true);

        // when
        boolean result = carServiceImpl.deleteCar(1L);

        // then
        assertThat(result).isTrue();
    }
}