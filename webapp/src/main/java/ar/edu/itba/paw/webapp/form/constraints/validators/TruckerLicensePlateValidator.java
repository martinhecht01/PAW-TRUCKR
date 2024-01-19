package ar.edu.itba.paw.webapp.form.constraints.validators;

import ar.edu.itba.paw.interfacesServices.CargoTypeService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.constraints.annotations.TruckerLicensePlate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import java.util.Objects;

public class TruckerLicensePlateValidator implements ConstraintValidator<TruckerLicensePlate, String>{

    @Autowired
    CargoTypeService cargoTypeService;

    @Autowired
    UserService us;


    @Override
    public void initialize(TruckerLicensePlate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String licensePlate, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        User user = us.getCurrentUser().orElseThrow(UserNotFoundException::new);
        if(Objects.equals(user.getRole(), "TRUCKER")){
            return licensePlate != null && !licensePlate.isEmpty();
        }
        return true;
    }

}
