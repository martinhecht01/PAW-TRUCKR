package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageDao {
    int uploadImage(byte[] newImage);

    Optional<Image> getImage(int imageid);

    void updateImage(byte[] newImage, Image image );
}
