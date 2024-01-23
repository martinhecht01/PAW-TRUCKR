package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Image;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ImageDto {

    Integer imageId;
    byte[] image;
    URI self;

    public ImageDto() {
        // Empty constructor needed by JAX-RS
    }

    public static ImageDto fromImage(final UriInfo uri, final Image image){
        ImageDto dto = new ImageDto();
        dto.self = uri.getBaseUriBuilder().path("/images/").path(String.valueOf(image.getImageid())).build();
        dto.imageId = image.getImageid();
        dto.image = image.getImage();
        return dto;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}
