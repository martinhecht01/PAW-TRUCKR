package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.CargoType;

import java.util.List;
import java.util.Optional;

public interface CargoTypeService {
    List<CargoType> getAllCargoTypes();
    Optional<CargoType> getCargoTypeById(Integer id);
}
