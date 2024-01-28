package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.CargoTypeDao;
import ar.edu.itba.paw.interfacesServices.CargoTypeService;
import ar.edu.itba.paw.models.CargoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoTypeServiceImpl implements CargoTypeService {
    private final CargoTypeDao cargoTypeDao;

    @Autowired
    public CargoTypeServiceImpl(CargoTypeDao cargoTypeDao) {
        this.cargoTypeDao = cargoTypeDao;
    }


    @Override
    public List<CargoType> getAllCargoTypes() {
        return cargoTypeDao.getAllCargoTypes();
    }

    @Override
    public Optional<CargoType> getCargoTypeById(Integer id) {
        return cargoTypeDao.getCargoTypeById(id);
    }
}
