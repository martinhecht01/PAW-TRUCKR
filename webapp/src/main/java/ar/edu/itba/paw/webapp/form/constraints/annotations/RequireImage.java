package ar.edu.itba.paw.webapp.form.constraints.annotations;
import ar.edu.itba.paw.webapp.form.constraints.validators.RequireImageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {RequireImageValidator.class})
@Target({TYPE, FIELD, PARAMETER, ANNOTATION_TYPE} )
@Retention(RUNTIME)
@Documented
public @interface RequireImage {

    String message() default "validation.RequireImage";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}