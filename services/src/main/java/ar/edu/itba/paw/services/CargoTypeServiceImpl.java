package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.CargoTypeDao;
import ar.edu.itba.paw.interfacesServices.CargoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoTypeServiceImpl implements CargoTypeService {
    private final CargoTypeDao cargoTypeDao;

    @Autowired
    public CargoTypeServiceImpl(CargoTypeDao cargoTypeDao) {
        this.cargoTypeDao = cargoTypeDao;
    }


    @Override
    public List<String> getAllCargoTypes() {
        return cargoTypeDao.getAllCargoTypes();
    }
}
