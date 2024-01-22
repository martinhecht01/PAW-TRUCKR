package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.SecureToken;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoJPA implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoJPA.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public boolean verifyAccount(User user) {
        LOGGER.info("Verifying account for user: {}", user.getUserId());
        boolean wasVerified = user.getAccountVerified();
        user.setAccountVerified(true);
        return wasVerified;
    }


    @Override
    public void resetPassword(Integer userId, String newPassword) {
        User user = entityManager.find(User.class, userId);
        user.setPassword(newPassword);
    }

    @Override
    public User create(String email, String name, String cuit, String role, String password, Locale locale) {
        User user = new User(email, name, cuit, role, password, false, new Image(1,null), locale);
        LOGGER.info("Creating user. CUIT: {}, EMAIL: {}, NAME: {}", cuit, email, name);
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<User> getUserByCuit(String userCuit) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.cuit = :cuit", User.class);
        query.setParameter("cuit", userCuit);
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            LOGGER.warn("User not found. CUIT: {}", userCuit);
            return Optional.empty();
        }
        return users.stream().findFirst();
    }

    @Override
    public Optional<User> getUserById(int id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            LOGGER.warn("User not found. UserID: {}", id);
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public boolean existsUser(String cuit) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.cuit = :cuit", Long.class);
        query.setParameter("cuit", cuit);
        Long count = query.getSingleResult();
        return count > 0;
    }

//cambio imageId a Image
    //me suena a que esta funcion es inutil ahora, hago get de user y set de image
    @Override
    public void setImage(int userId, Image image) {
        User user = entityManager.find(User.class, userId);
        LOGGER.info("Updating user image. UserID: {}, ImageID: {}", userId, user.getImage().getImageid());
        user.setImage(image);
    }

//    cambio de getImageId a getImage
    //esta tambien inutil
    @Override
    public Image getImage(int userId) {
        User user = entityManager.find(User.class, userId);
        return user.getImage();
    }

    //y esta tambien inutil
    @Override
    public void setUserName(int userId, String name) {
        LOGGER.info("Updating user name. UserID: {}, Name: {}", userId, name);
        User user = entityManager.find(User.class, userId);
        user.setName(name);
    }

    @Override
    public void setLocale(int userId, Locale locale){
        LOGGER.info("Updating user locale. UserID: {}, Locale: {}", userId, locale);
        User user = entityManager.find(User.class, userId);
        user.setLocale(locale);
    }
}
