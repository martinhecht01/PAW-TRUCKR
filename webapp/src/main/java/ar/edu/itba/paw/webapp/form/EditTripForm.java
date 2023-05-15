package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.ImageType;
import ar.edu.itba.paw.webapp.form.constraints.annotations.MaxFileSize;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EditTripForm {

    @MaxFileSize(8)
    @ImageType(types = {"image/jpeg", "image/png"})
    @NotNull
    private CommonsMultipartFile tripImage;

    public CommonsMultipartFile getTripImage() {
        return tripImage;
    }

    public void setTripImage(CommonsMultipartFile tripImage) {
        this.tripImage = tripImage;
    }

}
