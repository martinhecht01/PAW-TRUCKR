package ar.edu.itba.paw.webapp.form.constraints.annotations;

import ar.edu.itba.paw.webapp.form.constraints.validators.CityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CityValidator.class)
public @interface City {

    String message() default "validation.City";

    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};

}