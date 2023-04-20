package ar.edu.itba.paw.webapp.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateManager.class)
public @interface DateValidator {

    String message() default "{javax.validation.constraints.DateValidator.message}";

    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};

}