package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.CargoType;
import java.util.List;
import java.util.Optional;

public interface CargoTypeDao {
    List<CargoType> getAllCargoTypes();
    Optional<CargoType> getCargoTypeById(Integer id);
}
