package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageDao {
    int uploadImage(Image image);

    Optional<Image> getImage(int imageid);

    void updateImage(byte[] newImage, Image image );
}
