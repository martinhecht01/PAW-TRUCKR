package ar.edu.itba.paw.webapp.form.constraints.annotations;

import ar.edu.itba.paw.webapp.form.constraints.validators.CargoTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CargoTypeValidator.class)
public @interface CargoType {

    String message() default "validation.CargoType";

    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};

}