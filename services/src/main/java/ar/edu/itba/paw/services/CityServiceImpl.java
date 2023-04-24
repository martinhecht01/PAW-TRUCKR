package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.CityDao;
import ar.edu.itba.paw.interfacesServices.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityDao cityDao;

    @Autowired
    public CityServiceImpl(CityDao cityDao){
        this.cityDao= cityDao;
    }

    @Override
    public List<String> getAllCities() {
        return cityDao.getAllCities();
    }

}
