package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.SecureTokenDao;
import ar.edu.itba.paw.models.SecureToken;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
public class SecureTokenDaoJPA implements SecureTokenDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoJPA.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Integer createSecureToken(User user, int token) {
        if (findTokenByUser(user).isPresent()){
            delete(findTokenByUser(user).get().getToken());
        }
        SecureToken secureToken = new SecureToken(user,token, LocalDateTime.now().plusHours(1));
        LOGGER.info("Creating secure token for user: {}", user.getUserId());
        entityManager.persist(secureToken);
        return token;
    }

    @Override
    public Optional<SecureToken> getSecureTokenByValue(Integer tokenValue) {
        SecureToken token = entityManager.find(SecureToken.class, tokenValue);
        if(token == null){
            LOGGER.info("Token not found. Token: {}", tokenValue);
            return Optional.empty();
        }
        return Optional.of(token);
    }

    @Override
    public boolean delete(Integer tokenValue){
        SecureToken token = entityManager.find(SecureToken.class, tokenValue);
        if(token == null){
            LOGGER.info("Token not found. Token: {}", tokenValue);
            return false;
        }
        entityManager.remove(token);
        return true;
    }

    @Override
    public Optional<SecureToken> findTokenByUser(User user){
        TypedQuery<SecureToken> query = entityManager.createQuery("select t from SecureToken t where t.user = :user", SecureToken.class);
        query.setParameter("user", user);
        return query.getResultList().stream().findFirst();
    }

}
