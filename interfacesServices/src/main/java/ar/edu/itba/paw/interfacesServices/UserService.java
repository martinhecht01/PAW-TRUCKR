package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.SecureToken;
import ar.edu.itba.paw.models.User;

import javax.swing.text.html.Option;
import java.util.Locale;
import java.util.Optional;

public interface UserService {

    User createUser(String email, String name, String id, String role, String password, Locale locale);

    void resetPassword(Integer userId, String newPassword);

//    Optional<Reset> getResetByHash(Integer hash);

    void sendPasswordToken(User user, Locale locale);

    void createSecureToken(User user, Locale locale);

    Optional<SecureToken> getSecureToken(String tokenValue);

    boolean deleteToken(String token);

    public boolean validateToken(Integer userId, Locale locale);

    Optional<User> getUserByCuit(String cuit);

    Optional<User> getUserById(int id);

    void updateProfilePicture(Integer userId, Integer imageId);

    byte[] getProfilePicture(Integer userId);

    void updateProfileName(Integer userId, String name);

    void updateProfile(Integer userId, byte[] image, String name);

    void updateLocale(Integer userId, Locale locale);

    Optional<User> getCurrentUser();

//    void completeReset(Integer hash);
}
