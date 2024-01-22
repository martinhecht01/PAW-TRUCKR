package ar.edu.itba.paw.webapp.form.constraints.annotations;
import ar.edu.itba.paw.webapp.form.constraints.validators.EmptyBodyValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = EmptyBodyValidator.class)
@Target({ TYPE, FIELD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface EmptyBody {

    String message() default "validation.EmptyBody";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}