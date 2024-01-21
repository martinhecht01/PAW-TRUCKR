package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.CityDao;
import ar.edu.itba.paw.interfacesServices.CityService;
import ar.edu.itba.paw.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityDao cityDao;

    @Autowired
    public CityServiceImpl(CityDao cityDao){
        this.cityDao= cityDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<City> getAllCities() {
        return cityDao.getAllCities();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<City> getCityById(int id) {
        return cityDao.getCityById(id);
    }

}
