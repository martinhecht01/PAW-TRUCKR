package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.models.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class ImageDaoJPA implements ImageDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageDaoJPA.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int uploadImage(Image image) {
        LOGGER.info("Uploading image with imageId {}", image.getImageid());
        entityManager.persist(image);
        return image.getImageid();
    }

    @Override
    public Optional<Image> getImage(int imageid) {
        Image image = entityManager.find(Image.class, imageid);
        LOGGER.info("Getting image with imageId {}", imageid);
        return Optional.of(image);
    }

    @Override
    public void updateImage(byte[] image, int imageid) {
        Image imageToUpdate = entityManager.find(Image.class, imageid);

        imageToUpdate.setImage(image);

        entityManager.getTransaction().begin();
        entityManager.merge(imageToUpdate);
        entityManager.getTransaction().commit();
    }
}
