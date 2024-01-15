package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.ImageType;
import ar.edu.itba.paw.webapp.form.constraints.annotations.MaxFileSize;
import ar.edu.itba.paw.webapp.form.constraints.annotations.RequireImage;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditUserForm {

    @Size(min = 6, max = 100)
    @Pattern(regexp = "^[A-Za-z]+(\\s[A-Za-z]*)+$")
    @FormDataParam("name")
    private String name;

//    @MaxFileSize(8)
    @ImageType(types = {"image/jpeg", "image/png"})
    @FormDataParam("profileImage")
    private FormDataBodyPart profileImage;

    @FormDataParam("profileImage")
    private byte[] bytes;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FormDataBodyPart getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(FormDataBodyPart profileImage) {
        this.profileImage = profileImage;
    }

}
