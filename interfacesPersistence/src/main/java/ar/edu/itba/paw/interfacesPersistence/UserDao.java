package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.SecureToken;
import ar.edu.itba.paw.models.User;

import java.util.Locale;
import java.util.Optional;

public interface UserDao {
    void resetPassword(Integer hash, String newPassword);

    User create(String email, String name, String cuit, String role, String password, Locale locale);

    Optional<User> getUserByCuit(String userCuit);

    Optional<User> getUserById(int id);

    Optional<Integer> createReset(Integer userId, Integer hash);

    Optional<Reset> getResetByHash(Integer hash);

    void completeReset(Integer hash);

    boolean existsUser(String cuit);

    Integer createSecureToken(User user, int token);

    Optional<SecureToken> getSecureTokenByValue(Integer tokenValue);

    void verifyAccount(User user);

    void setImage(int userId, Image image);

    Image getImage(int userId);

    void setUserName(int userId, String name);

    void setLocale(int userId, Locale locale);
}
