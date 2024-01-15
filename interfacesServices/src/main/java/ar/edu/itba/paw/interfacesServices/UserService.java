package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.User;

import java.util.Locale;
import java.util.Optional;

public interface UserService {

    User createUser(String email, String name, String id, String role, String password, Locale locale);

    void resetPassword(Integer hash, String newPassword);

    Optional<Reset> getResetByHash(Integer hash);

    void createReset(Integer userId, Locale locale);

    void createSecureToken(User user, Locale locale);

    public boolean verifyAccount(Integer tokenValue, Locale locale);

    Optional<User> getUserByCuit(String cuit);

    Optional<User> getUserById(int id);

    void updateProfilePicture(Integer userId, Integer imageId);

    byte[] getProfilePicture(Integer userId);

    void updateProfileName(Integer userId, String name);

    void updateProfile(Integer userId, byte[] image, String name);

    void updateLocale(Integer userId, Locale locale);

    Optional<User> getCurrentUser();
}
