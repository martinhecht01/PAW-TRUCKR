package ar.edu.itba.paw.webapp.form.constraints.validators;

import ar.edu.itba.paw.webapp.form.constraints.annotations.PreventPast;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;

public class PastPreventer implements ConstraintValidator<PreventPast, String>{
    @Override
    public void initialize(PreventPast constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String date, javax.validation.ConstraintValidatorContext constraintValidatorContext) {

        if(date == null || date.isEmpty())
            return true;

        LocalDateTime dateTime = null;
        try {
            dateTime = LocalDateTime.parse(date);
        } catch (Exception e) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("validation.DateFormat")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return dateTime != null && !dateTime.isBefore(LocalDateTime.now());
    }

}
