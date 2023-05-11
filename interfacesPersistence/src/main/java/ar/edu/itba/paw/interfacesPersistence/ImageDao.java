package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageDao {
    void uploadImage(byte[] image, int userid);

    Optional<Image> getImage(int imageid);

    void updateImage(byte[] image, int imageid);
}
