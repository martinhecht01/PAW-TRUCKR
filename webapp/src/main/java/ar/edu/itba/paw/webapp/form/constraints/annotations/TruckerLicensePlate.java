package ar.edu.itba.paw.webapp.form.constraints.annotations;

import ar.edu.itba.paw.webapp.form.constraints.validators.TruckerLicensePlateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = TruckerLicensePlateValidator.class)
public @interface TruckerLicensePlate {

    String message() default "validation.TruckerLicensePlate";

    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};

}