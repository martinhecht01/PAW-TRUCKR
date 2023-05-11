package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Image;

import java.io.IOException;

public interface ImageService {
    byte[] getImage(int imageid) throws IOException;

    void uploadImage(byte[] image, int userid);
}
