package objectivity.co.uk.CarsRental.business.validation;

import java.util.List;

public interface BannedCarsRepository {

    List<String> getBannedModels();

}
