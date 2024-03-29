package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.models.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;


@Transactional
@Repository
public class ImageDaoJPA implements ImageDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageDaoJPA.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int uploadImage(byte[] image) {
        Image im = new Image(image);
        LOGGER.info("Uploading image with imageId {}", im.getImageid());
        entityManager.persist(im);
        return im.getImageid();
    }

    @Override
    public Optional<Image> getImage(int imageid) {
        Image image = entityManager.find(Image.class, imageid);
        LOGGER.info("Getting image with imageId {}", imageid);
        return Optional.ofNullable(image);
    }

    @Override
    public void updateImage(byte[] newImage, Image image) {
        image.setImage(newImage);
        LOGGER.info("Updating image with imageId {}", image.getImageid());
    }
}
