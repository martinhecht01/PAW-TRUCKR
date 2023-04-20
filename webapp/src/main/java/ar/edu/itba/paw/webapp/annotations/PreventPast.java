package ar.edu.itba.paw.webapp.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PastPreventer.class)
public @interface PreventPast {

    String message() default "{javax.validation.constraints.PreventPast.message}";

    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};

}