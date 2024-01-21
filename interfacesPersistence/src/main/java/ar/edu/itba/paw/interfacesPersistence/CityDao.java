package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.City;

import java.util.List;
import java.util.Optional;

public interface CityDao {

    List<City> getAllCities();

    Optional<City> getCityById(int id);

}
