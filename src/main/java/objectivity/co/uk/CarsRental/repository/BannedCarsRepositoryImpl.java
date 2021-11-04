package objectivity.co.uk.CarsRental.repository;

import objectivity.co.uk.CarsRental.business.validation.BannedCarsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Arrays.asList;

@Repository
public class BannedCarsRepositoryImpl implements BannedCarsRepository {
    @Override
    public List<String> getBannedModels() {
        return asList("Multipla", "Panda");
    }
}
