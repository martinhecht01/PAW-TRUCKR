package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    List<City> getAllCities();
    Optional<City> getCityById(int id);
}
