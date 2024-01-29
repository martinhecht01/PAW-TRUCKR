package ar.edu.itba.paw.webapp.form.constraints.validators;

import ar.edu.itba.paw.interfacesServices.CargoTypeService;
import ar.edu.itba.paw.interfacesServices.CityService;
import ar.edu.itba.paw.webapp.form.constraints.annotations.CargoType;
import ar.edu.itba.paw.webapp.form.constraints.annotations.City;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import java.util.HashSet;

public class CityValidator implements ConstraintValidator<City, String>{

    @Autowired
    CityService cityService;

    @Override
    public void initialize(City constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cityName, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        HashSet<String> list = new HashSet<>();
        for (ar.edu.itba.paw.models.City city : cityService.getAllCities()) {
            list.add(city.getCityName());
        }
        HashSet<String> cities = new HashSet<>(list);
        return cityName == null || cities.contains(cityName);
    }

}
