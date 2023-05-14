package ar.edu.itba.paw.webapp.form.constraints.validators;

import ar.edu.itba.paw.webapp.form.constraints.annotations.ImageType;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ImageTypeValidator implements ConstraintValidator<ImageType, CommonsMultipartFile> {

    private Collection<String> types;

    @Override
    public void initialize(ImageType imageType) {
        this.types = Collections.unmodifiableCollection(Arrays.asList(imageType.types()));
    }

    @Override
    public boolean isValid(CommonsMultipartFile image, ConstraintValidatorContext constraintValidatorContext) {
        if(image.getSize() == 0)
            return true;
        return types.contains(image.getContentType());
    }
}