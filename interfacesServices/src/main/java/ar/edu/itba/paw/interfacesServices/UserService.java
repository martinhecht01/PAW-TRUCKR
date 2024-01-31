package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.SecureToken;
import ar.edu.itba.paw.models.User;
import java.util.Locale;
import java.util.Optional;

public interface UserService {

    User createUser(String email, String name, String id, String role, String password, Locale locale);

    void resetPassword(Integer userId, String newPassword);

    void sendPasswordToken(User user, Locale locale);

    void createSecureToken(User user, Locale locale);

    Optional<SecureToken> getSecureToken(String tokenValue);

    boolean deleteToken(String token);

    boolean validateToken(Integer userId, Locale locale);

    void resendToken(String cuit);

    Optional<User> getUserByCuit(String cuit);

    Optional<User> getUserById(int id);

    void updateProfilePicture(Integer userId, Integer imageId);

    byte[] getProfilePicture(Integer userId);

    void updateProfileName(Integer userId, String name);

    void updateProfile(Integer userId, Integer imageId, String name);

    void updateLocale(Integer userId, Locale locale);

    Optional<User> getCurrentUser();

}
