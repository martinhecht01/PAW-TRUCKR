package ar.edu.itba.paw.webapp.annotations;

import ar.edu.itba.paw.webapp.form.TripForm;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;


public class DateManager implements ConstraintValidator<DateValidator, TripForm>  {


    @Override
    public void initialize(DateValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    //Checks if its future or present
    @Override
    public boolean isValid(TripForm form, ConstraintValidatorContext constraintValidatorContext) {
        if(form.getDepartureDate() == null || form.getArrivalDate() == null || form.getDepartureDate().isEmpty() || form.getArrivalDate().isEmpty())
            return false;
        String start = form.getDepartureDate();
        String end = form.getArrivalDate();

        LocalDateTime startDateTime = LocalDateTime.parse(start);
        LocalDateTime endDateTime = LocalDateTime.parse(end);
        LocalDateTime now = LocalDateTime.now();
        return !startDateTime.isAfter(endDateTime);

    }
}