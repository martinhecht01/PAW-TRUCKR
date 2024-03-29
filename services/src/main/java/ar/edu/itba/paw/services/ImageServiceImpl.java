package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.interfacesServices.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;


@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageDao imageDao;


    @Autowired
    public ImageServiceImpl(@Qualifier("imageDaoJPA") ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Image> getImage(int imageid) {
        Optional<Image> image = imageDao.getImage(imageid);
        if(!image.isPresent()){
            LOGGER.warn("Image not found: {}", imageid);
            throw new ImageNotFoundException();
        }
        else {
            return image;
        }
    }

    @Transactional
    @Override
    public int uploadImage(byte[] image) {
        return imageDao.uploadImage(image);
    }

    @Transactional
    @Override
    public void updateImage(byte[] newImage, Image image) {
        imageDao.updateImage(newImage, image);
    }


}
