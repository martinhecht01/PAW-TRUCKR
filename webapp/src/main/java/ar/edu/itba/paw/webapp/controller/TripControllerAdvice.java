package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.CargoTypeService;
import ar.edu.itba.paw.interfacesServices.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class TripControllerAdvice {
    private final CityService cityService;
    private final CargoTypeService cargoTypeService;

    @Autowired
    public TripControllerAdvice(CityService cityService, CargoTypeService cargoTypeService) {
        this.cityService = cityService;
        this.cargoTypeService = cargoTypeService;
    }


//    @ModelAttribute("cities")
//    public List<String> getCities() {
//        return cityService.getAllCities();
//    }

//    @ModelAttribute("cargoTypes")
//    public List<String> getCargoTypes() {
//        return cargoTypeService.getAllCargoTypes();
//    }
}
