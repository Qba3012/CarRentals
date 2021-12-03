package objectivity.co.uk.CarsRental.repository.jdbc;

import lombok.RequiredArgsConstructor;
import objectivity.co.uk.CarsRental.business.CarRepository;
import objectivity.co.uk.CarsRental.model.Car;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CarJDBCRepositoryImpl implements CarRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CarQueries queries;

    @Override
    public Car save(Car car) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        car.setUpdateDate(LocalDateTime.now());
        car.setCreateDate(LocalDateTime.now());
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("maker", car.getMaker())
                .addValue("model", car.getModel())
                .addValue("vin", car.getVin())
                .addValue("status", car.getStatus().name())
                .addValue("price", car.getPrice())
                .addValue("purchaseDate", car.getPurchaseDate())
                .addValue("updateDate", car.getUpdateDate())
                .addValue("createDate", car.getCreateDate());

        jdbcTemplate.update(queries.getInsert(), parameterSource, keyHolder);
        car.setId(keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null);
        return car;
    }

    @Override
    public List<Car> save(List<Car> cars) {
        return cars.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public List<Car> findAll() {
        return jdbcTemplate.query(queries.getFindAll(), new BeanPropertyRowMapper<Car>(Car.class));
    }

    @Override
    public Optional<Car> getById(Long id) {
        List<Car> cars = jdbcTemplate.query(queries.getFindById(), new MapSqlParameterSource().addValue("id",
                id), new BeanPropertyRowMapper<Car>(Car.class));
        if (cars.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(cars.get(0));
        }
    }

    @Override
    public boolean deleteById(Long id) {
        int rows = jdbcTemplate.update(queries.getDeleteById(), new MapSqlParameterSource().addValue("id", id));
        return rows != 0;
    }

    @Override
    public Car update(Long id, Car car) {
        car.setUpdateDate(LocalDateTime.now());
        car.setId(id);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("maker", car.getMaker())
                .addValue("model", car.getModel())
                .addValue("vin", car.getVin())
                .addValue("status", car.getStatus().name())
                .addValue("price", car.getPrice())
                .addValue("purchaseDate", car.getPurchaseDate())
                .addValue("updateDate", car.getUpdateDate())
                .addValue("id", id);

        int affectedRows = jdbcTemplate.update(queries.getUpdate(), parameterSource);

        if (affectedRows == 0) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            car.setCreateDate(LocalDateTime.now());
            parameterSource.addValue("createDate", car.getCreateDate());
            jdbcTemplate.update(queries.getInsert(), parameterSource, keyHolder);
            car.setId(keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null);
        }
        return car;
    }
}
