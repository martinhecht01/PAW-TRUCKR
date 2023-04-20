package ar.edu.itba.paw.webapp.annotations;

import javax.validation.ConstraintValidator;
import java.time.LocalDateTime;

public class PastPreventer implements ConstraintValidator<PreventPast, String>{
    @Override
    public void initialize(PreventPast constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String date, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return !dateTime.isBefore(LocalDateTime.now());
    }

}
