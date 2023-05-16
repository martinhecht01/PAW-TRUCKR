package ar.edu.itba.paw.webapp.form.constraints.validators;


import ar.edu.itba.paw.webapp.form.constraints.annotations.RequireImage;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class RequireImageValidator implements ConstraintValidator<RequireImage, CommonsMultipartFile> {

    @Override
    public void initialize(RequireImage requireImage) {
        ConstraintValidator.super.initialize(requireImage);
    }

    @Override
    public boolean isValid(CommonsMultipartFile image, ConstraintValidatorContext constraintValidatorContext) {
        if(image==null)
            return false;
        else
            return !image.isEmpty();
    }
}