package objectivity.co.uk.CarsRental.repository.jdbc;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:jdbc/jdbcQueries.properties")
public class CarQueries {

    @Value("${car.query.insert}")
    private String insert;
    @Value("${car.query.update}")
    private String update;
    @Value("${car.query.find.all}")
    private String findAll;
    @Value("${car.query.find.by.id}")
    private String findById;
    @Value("${car.query.delete.by.id}")
    private String deleteById;
}
