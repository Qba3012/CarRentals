package objectivity.co.uk.CarsRental.business;

import objectivity.co.uk.CarsRental.business.error.CarError;
import objectivity.co.uk.CarsRental.business.error.CarValidationResult;
import objectivity.co.uk.CarsRental.business.error.MultipleCarValidationException;
import objectivity.co.uk.CarsRental.business.error.SingleCarValidationException;
import objectivity.co.uk.CarsRental.business.validation.CarValidator;
import objectivity.co.uk.CarsRental.model.Car;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static objectivity.co.uk.CarsRental.dummies.CarDummies.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carServiceImpl;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarValidator carValidator;

    @Test
    void shouldAddCarsIfNoValidationErrors() {
        // given
        BDDMockito.given(carRepository.save(carList)).willReturn(carList);

        // when
        List<Car> actualCars = carServiceImpl.addCars(carList);

        // then
        InOrder inOrder = inOrder(carValidator, carRepository);
        inOrder.verify(carValidator).validate(carList);
        inOrder.verify(carRepository).save(carList);
        assertThat(actualCars).usingRecursiveComparison().isEqualTo(carList);
    }

    @Test
    void shouldNotAddCarsAndThrowExceptionIfValidationFails() {
        // given
        List<CarValidationResult> validationResults = singletonList(new CarValidationResult(CarError.WRONG_VIN_FORMAT));
        Map<Integer, List<CarValidationResult>> validationResponse = Map.of(0, validationResults);
        BDDMockito.willThrow(new MultipleCarValidationException(validationResponse)).given(carValidator).validate(carList);

        // when // then
        assertThatThrownBy(() -> carServiceImpl.addCars(carList))
                .isInstanceOf(MultipleCarValidationException.class)
                .extracting("validationResults")
                .asInstanceOf(InstanceOfAssertFactories.map(Integer.class, List.class))
                .extractingByKey(0)
                .asList()
                .first()
                .extracting("errorCode", "message")
                .containsExactly(CarError.WRONG_VIN_FORMAT.name(), CarError.WRONG_VIN_FORMAT.getDescription());
        InOrder inOrder = inOrder(carValidator, carRepository);
        inOrder.verify(carValidator).validate(carList);
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
        List<CarValidationResult> validationResults = singletonList(new CarValidationResult(CarError.WRONG_VIN_FORMAT));
        BDDMockito.willThrow(new SingleCarValidationException(validationResults)).given(carValidator).validate(car1);

        // when // then
        assertThatThrownBy(() -> carServiceImpl.updateCar(1L, car1))
                .isInstanceOf(SingleCarValidationException.class)
                .extracting("validationResults")
                .asList()
                .first()
                .extracting("errorCode", "message")
                .containsExactly(CarError.WRONG_VIN_FORMAT.name(), CarError.WRONG_VIN_FORMAT.getDescription());
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