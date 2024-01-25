package ar.edu.itba.paw.webapp.form.constraints.validators;

import ar.edu.itba.paw.interfacesServices.CargoTypeService;
import ar.edu.itba.paw.webapp.form.constraints.annotations.CargoType;
import ar.edu.itba.paw.webapp.form.constraints.annotations.PreventPast;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import java.time.LocalDateTime;

public class CargoTypeValidator implements ConstraintValidator<CargoType, String>{

    @Autowired
    CargoTypeService cargoTypeService;

    @Override
    public void initialize(CargoType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cargoType, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        return cargoType == null || cargoTypeService.getAllCargoTypes().contains(cargoType);
    }

}
