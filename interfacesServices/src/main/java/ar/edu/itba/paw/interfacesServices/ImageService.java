package ar.edu.itba.paw.interfacesServices;

import java.io.IOException;

public interface ImageService {
    byte[] getImage(int imageid) throws IOException;

    int uploadImage(byte[] image);
}
