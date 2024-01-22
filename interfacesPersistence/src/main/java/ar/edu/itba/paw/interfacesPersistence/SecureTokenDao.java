package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.SecureToken;
import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface SecureTokenDao {

    Integer createSecureToken(User user, int token);

    Optional<SecureToken> getSecureTokenByValue(Integer tokenValue);

    boolean delete(Integer tokenValue);

    Optional<SecureToken> findTokenByUser(User user);

}
