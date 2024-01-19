package ar.edu.itba.paw.webapp.form.constraints.annotations;

import ar.edu.itba.paw.webapp.form.constraints.validators.ConfirmPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ConfirmPasswordValidator.class)
public @interface ConfirmPasswordValidation {

    String message() default "validation.ConfirmPassword";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String passwordFieldName();

    String confirmPasswordFieldName();
}
