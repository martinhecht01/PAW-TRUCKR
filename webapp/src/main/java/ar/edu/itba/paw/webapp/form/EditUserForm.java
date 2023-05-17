package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.ImageType;
import ar.edu.itba.paw.webapp.form.constraints.annotations.MaxFileSize;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditUserForm {

    @Size(min = 6, max = 100)
    @Pattern(regexp = "^[A-Za-z]+(\\s[A-Za-z]*)+$")
    private String name;

    @MaxFileSize(8)
    @ImageType(types = {"image/jpeg", "image/png"})
    @NotNull
    private CommonsMultipartFile profileImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommonsMultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(CommonsMultipartFile profileImage) {
        this.profileImage = profileImage;
    }

}
