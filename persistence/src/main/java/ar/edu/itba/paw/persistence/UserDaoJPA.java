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
import java.util.Optional;

@Repository
@Transactional
public class UserDaoJPA implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoJPA.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Integer> createReset(Integer userId, Integer hash) {
        //hash.toString()
        // completed = false
        User user = entityManager.find(User.class, userId);
        Reset reset = new Reset(user, hash , new Timestamp(System.currentTimeMillis()), false);
        LOGGER.info("Creating reset for user: {}", userId);
        entityManager.persist(reset);
        return Optional.of(hash);
    }

    //Decision: userid o user?
    @Override
    public Integer createSecureToken(User user, int token) {
        SecureToken secureToken = new SecureToken(user,String.valueOf(token), LocalDateTime.now().plusHours(1));
        LOGGER.info("Creating secure token for user: {}", user.getUserId());
        entityManager.persist(secureToken);
        return token;
    }

    @Override
    public Optional<SecureToken> getSecureTokenByValue(Integer tokenValue) {
        TypedQuery<SecureToken> query = entityManager.createQuery(
                "SELECT t FROM SecureToken t WHERE t.token = :token", SecureToken.class);
        query.setParameter("token", tokenValue);
        List<SecureToken> tokens = query.getResultList();
        if (tokens.isEmpty()) {
            LOGGER.info("Token not found. Token: {}", tokenValue);
            return Optional.empty();
        }
        return Optional.of(tokens.get(0));
    }

    @Override
    public void verifyAccount(Integer userId) {
        LOGGER.info("Verifying account for user: {}", userId);
        User user = entityManager.find(User.class, userId);
        user.setAccountVerified(true);
    }

    @Override
    public Optional<Reset> getResetByHash(Integer hash) {
        TypedQuery<Reset> query = entityManager.createQuery(
                "SELECT r FROM Reset r WHERE r.hash = :hash", Reset.class);
        query.setParameter("hash", hash);
        List<Reset> resets = query.getResultList();
        if (resets.isEmpty()) {
            LOGGER.warn("Reset not found. Hash: {}", hash);
            return Optional.empty();
        }
        return Optional.of(resets.get(0));
    }

    @Override
    public void completeReset(Integer hash) {
        LOGGER.info("Completing reset. Hash: {}", hash);
        Reset reset = entityManager.find(Reset.class, hash);
        reset.setCompleted(true);
    }

    @Override
    public void resetPassword(Integer hash, String newPassword) {
        LOGGER.info("Resetting password for hash: {}", hash);
        Reset reset = entityManager.find(Reset.class, hash);
        //User user = entityManager.find(User.class, reset.getUserId());
        User user = reset.getUser();
        user.setPassword(newPassword);
    }

    @Override
    public User create(String email, String name, String cuit, String role, String password) {
        User user = new User(email, name, cuit, role, password, false, null);
        LOGGER.info("Creating user. CUIT: {}, EMAIL: {}, NAME: {}", cuit, email, name);
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<User> getUserByCuit(String userCuit) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.cuit = :cuit", User.class);
        query.setParameter("cuit", userCuit);
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            LOGGER.warn("User not found. CUIT: {}", userCuit);
            return Optional.empty();
        }
        return Optional.of(users.get(0));
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
    @Override
    public void setImage(int userId, Image image) {
        User user = entityManager.find(User.class, userId);
        LOGGER.info("Updating user image. UserID: {}, ImageID: {}", userId, user.getImage().getImageid());
        user.setImage(image);
    }

//    cambio de getImageId a getImage
    @Override
    public Image getImage(int userId) {
        User user = entityManager.find(User.class, userId);
        return user.getImage();
    }

    @Override
    public void setUserName(int userId, String name) {
        LOGGER.info("Updating user name. UserID: {}, Name: {}", userId, name);
        User user = entityManager.find(User.class, userId);
        user.setName(name);
    }
}
