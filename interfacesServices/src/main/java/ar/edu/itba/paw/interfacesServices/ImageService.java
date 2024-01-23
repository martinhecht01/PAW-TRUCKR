package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Image;

import java.io.IOException;
import java.util.Optional;

public interface ImageService {
    Optional<Image> getImage(int imageid);

    int uploadImage(byte[] image);

    void updateImage(byte[] newImage, Image image);
}
