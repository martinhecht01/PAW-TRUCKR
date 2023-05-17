package ar.edu.itba.paw.webapp.form.constraints.validators;

import ar.edu.itba.paw.webapp.form.constraints.annotations.DateValidation;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<DateValidation, Object> {

    private String start;
    private String end;

    @Override
    public void initialize(DateValidation constraintAnnotation) {
        start = constraintAnnotation.start();
        end = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper wrapper = new BeanWrapperImpl(value);
        Object startValue = wrapper.getPropertyValue(start);
        Object endValue = wrapper.getPropertyValue(end);
        if (startValue == null || endValue == null) {
            return false; // Skip validation if either value is null
        }
        String start = startValue.toString();
        String end = endValue.toString();
        try {
            LocalDateTime startDateTime = LocalDateTime.parse(start);
            LocalDateTime endDateTime = LocalDateTime.parse(end);
            return !startDateTime.isAfter(endDateTime);
        } catch (DateTimeParseException e) {
            return false; // Return false if either value cannot be parsed as LocalDateTime
        }
    }
}