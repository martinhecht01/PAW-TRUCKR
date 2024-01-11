package ar.edu.itba.paw.webapp.form.constraints.validators;


import ar.edu.itba.paw.webapp.form.constraints.annotations.RequireImage;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequireImageValidator implements ConstraintValidator<RequireImage, FormDataBodyPart> {

    @Override
    public void initialize(RequireImage requireImage) {
        ConstraintValidator.super.initialize(requireImage);
    }

    @Override
    public boolean isValid(FormDataBodyPart image, ConstraintValidatorContext constraintValidatorContext) {
        return image != null && image.getMediaType().toString().contains("image/");
    }
}