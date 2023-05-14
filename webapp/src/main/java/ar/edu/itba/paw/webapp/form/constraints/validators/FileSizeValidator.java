package ar.edu.itba.paw.webapp.form.constraints.validators;

import ar.edu.itba.paw.webapp.form.constraints.annotations.MaxFileSize;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<MaxFileSize, CommonsMultipartFile> {

    private long size;

    @Override
    public void initialize(MaxFileSize size) {
        this.size = size.value();
    }

    @Override
    public boolean isValid(CommonsMultipartFile file, ConstraintValidatorContext constraintContext) {
        if (file == null)
            return true;
        return file.getSize() <= size*1024*1024;
    }

}
