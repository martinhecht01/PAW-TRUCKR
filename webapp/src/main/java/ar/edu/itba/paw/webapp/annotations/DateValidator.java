package ar.edu.itba.paw.webapp.annotations;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

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

        try {
            BeanWrapper wrapper = new BeanWrapperImpl(value);
            Object firstObj = wrapper.getPropertyValue(start);
            Object secondObj = wrapper.getPropertyValue(end);
            if (firstObj == null || secondObj == null) {
                return false;
            }
            String start = (String) firstObj;
            String end = (String) secondObj;

            LocalDateTime startDateTime = LocalDateTime.parse(start);
            LocalDateTime endDateTime = LocalDateTime.parse(end);

            return !startDateTime.isAfter(endDateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}