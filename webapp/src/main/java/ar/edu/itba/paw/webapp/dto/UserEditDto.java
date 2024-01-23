package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.webapp.form.constraints.annotations.ConfirmPasswordValidation;
import ar.edu.itba.paw.webapp.form.constraints.annotations.ImageType;
import ar.edu.itba.paw.webapp.form.constraints.annotations.MaxFileSize;
import ar.edu.itba.paw.webapp.form.constraints.annotations.RequireImage;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserEditDto {

    @Size(min = 6, max = 100, message="validation.Password.Size")
    private String password;


    @Size(min = 6, max = 100, message="validation.Name.Size")
    @Pattern(regexp = "^[A-Za-z]+(\\s[A-Za-z]*)+$", message= "validation.Name.Pattern")
    private String name;

    private Integer imageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
}
