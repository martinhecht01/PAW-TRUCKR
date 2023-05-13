package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.SecureToken;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void resetPassword(Integer hash, String newPassword);

    public User create(final String email, final String name, final String userId, final String password, final String role);
    public Optional<User> getUserByCuit(String userCuit);
    public Optional<User> getUserById(int id);
    Optional<Integer> createReset(Integer userId, Integer hash);

    Optional<Reset> getResetByHash(Integer hash);
    void completeReset(Integer hash);
    Optional<User> findById(String id);
    public boolean existsUser(String cuit);

    Integer createSecureToken(Integer userId, int token);

    Optional<SecureToken> getSecureTokenByValue(Integer tokenValue);

    void verifyAccount(Integer tokenValue);
}
