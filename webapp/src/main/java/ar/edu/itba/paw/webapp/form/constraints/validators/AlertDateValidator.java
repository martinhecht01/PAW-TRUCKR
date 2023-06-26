package ar.edu.itba.paw.webapp.form.constraints.validators;

import ar.edu.itba.paw.webapp.form.constraints.annotations.AlertDateValidation;
import ar.edu.itba.paw.webapp.form.constraints.annotations.DateValidation;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AlertDateValidator implements ConstraintValidator<AlertDateValidation, Object> {


    private String start;
    private String end;

    @Override
    public void initialize(AlertDateValidation constraintAnnotation) {
        start = constraintAnnotation.start();
        end = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (start == null || end == null)
            return true;

        BeanWrapper wrapper = new BeanWrapperImpl(value);
        Object startValue = wrapper.getPropertyValue(start);
        Object endValue = wrapper.getPropertyValue(end);

        if (startValue == null || endValue == null) {
            return true; // Skip validation if either value is null
        }

        String startStr = startValue.toString();
        String endStr = endValue.toString();

        if (startStr == null || endStr == null) {
            return true; // Skip validation if either string representation is null
        }

        try {
            LocalDateTime startDateTime = LocalDateTime.parse(startStr);
            LocalDateTime endDateTime = LocalDateTime.parse(endStr);
            return !startDateTime.isAfter(endDateTime);
        } catch (DateTimeParseException e) {
            return true; // Return false if either value cannot be parsed as LocalDateTime
        }
    }
}
