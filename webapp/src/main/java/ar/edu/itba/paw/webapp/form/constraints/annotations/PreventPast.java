package ar.edu.itba.paw.webapp.form.constraints.annotations;

import ar.edu.itba.paw.webapp.form.constraints.validators.PastPreventer;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PastPreventer.class)
public @interface PreventPast {

    String message() default "validation.PreventPast";

    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};

}