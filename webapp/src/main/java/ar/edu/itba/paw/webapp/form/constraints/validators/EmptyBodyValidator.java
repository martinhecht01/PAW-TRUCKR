package ar.edu.itba.paw.webapp.form.constraints.validators;


import ar.edu.itba.paw.webapp.form.constraints.annotations.EmptyBody;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmptyBodyValidator implements ConstraintValidator<EmptyBody, Object> {

    public void initialize(EmptyBody constraint) {
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {
        return object != null;
    }

}