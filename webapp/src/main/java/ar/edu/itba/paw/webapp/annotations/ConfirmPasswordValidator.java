package ar.edu.itba.paw.webapp.annotations;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPasswordValidation, Object> {

    private String passwordFieldName;
    private String confirmPasswordFieldName;

    @Override
    public void initialize(ConfirmPasswordValidation constraintAnnotation) {
        passwordFieldName = constraintAnnotation.passwordFieldName();
        confirmPasswordFieldName = constraintAnnotation.confirmPasswordFieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper wrapper = new BeanWrapperImpl(value);
        Object password = wrapper.getPropertyValue(passwordFieldName);
        Object confirmPassword = wrapper.getPropertyValue(confirmPasswordFieldName);
        if (password == null || password.equals("") || confirmPassword == null || confirmPassword.equals("")) {
            return false;
        }
        return password.equals(confirmPassword);
    }
}