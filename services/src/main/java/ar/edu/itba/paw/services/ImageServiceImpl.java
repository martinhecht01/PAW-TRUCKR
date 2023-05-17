package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.models.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;


@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageDao imageDao;


    @Autowired
    public ImageServiceImpl(ImageDao imageDao) {
        this.imageDao = imageDao;

    }

    @Transactional(readOnly = true)
    @Override
    public byte[] getImage(int imageid) throws IOException {
        Optional<Image> image = imageDao.getImage(imageid);
        if(!image.isPresent()){
            LOGGER.warn("Image not found: {}", imageid);
            throw new IOException("Image not found");
        }
        else {
            return image.get().getImage();
        }
    }

    @Transactional
    @Override
    public int uploadImage(byte[] image) {
        return imageDao.uploadImage(image);
    }





}
