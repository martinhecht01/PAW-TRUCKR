package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;


@Service
public class ImageServiceImpl implements ImageService {
    private final ImageDao imageDao;
    @Autowired
    public ImageServiceImpl(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public byte[] getImage(int imageid) throws IOException {
        Optional<Image> image = imageDao.getImage(imageid);
        if(!image.isPresent()){
            throw new IOException("Image not found");
        }
        else {
            return image.get().getImage();
        }
    }

    @Override
    public void uploadImage(byte[] image, int userid) {
        imageDao.uploadImage(image, userid);
    }



}
