package ar.edu.itba.paw.webapp.dto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

public class ValidationErrorDto {

    private String message;
    private String path;

    public static ValidationErrorDto fromValidationException(ConstraintViolation<?> violation){
        ValidationErrorDto dto = new ValidationErrorDto();
        dto.message = violation.getMessage();
        dto.path = getViolationPropertyName(violation);
        return dto;
    }
//TODO revisar como se comporta con la validacion de query params
    private static String getViolationPropertyName(ConstraintViolation<?> violation) {
        final String propertyPath = violation.getPropertyPath().toString();
        return propertyPath.substring(propertyPath.lastIndexOf(".") + 1);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
