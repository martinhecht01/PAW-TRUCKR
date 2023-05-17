package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserService {

    User createUser(String email, String name, String id, String role, String password);

    void resetPassword(Integer hash, String newPassword);

    Optional<Reset> getResetByHash(Integer hash);

    void createReset(Integer userId);

   void createSecureToken(Integer userId);

    boolean verifyAccount(Integer tokenValue);

    Optional<User> getUserByCuit(String cuit);

    Optional<User> getUserById(int id);

    void updateProfilePicture(Integer userId, Integer imageId);

    byte[] getProfilePicture(Integer userId);

    void updateProfileName(Integer userId, String name);
}
