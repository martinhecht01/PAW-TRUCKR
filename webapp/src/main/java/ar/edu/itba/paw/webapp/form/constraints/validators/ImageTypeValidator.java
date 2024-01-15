package ar.edu.itba.paw.webapp.form.constraints.validators;

import ar.edu.itba.paw.webapp.form.constraints.annotations.ImageType;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ImageTypeValidator implements ConstraintValidator<ImageType, FormDataBodyPart> {

    private Collection<String> types;

    @Override
    public void initialize(ImageType imageType) {
        this.types = Collections.unmodifiableCollection(Arrays.asList(imageType.types()));
    }

    @Override
    public boolean isValid(FormDataBodyPart image, ConstraintValidatorContext constraintValidatorContext) {
        if(image == null) {
            return true;
        }
        return types.contains(image.getMediaType().toString());
    }
}