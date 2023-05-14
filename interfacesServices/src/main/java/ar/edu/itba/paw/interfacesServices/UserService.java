package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.interfacesServices.exceptions.ResetErrorException;
import ar.edu.itba.paw.interfacesServices.exceptions.UserExistsException;
import ar.edu.itba.paw.interfacesServices.exceptions.VerifyErrorException;
import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.SecureToken;
import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserService {

    User createUser(String email, String name, String id, String role, String password) throws UserExistsException;

    void resetPassword(Integer hash, String newPassword);

    Optional<Reset> getResetByHash(Integer hash) throws ResetErrorException;

    void createReset(Integer userId);

   void createSecureToken(Integer userId);


    void verifyAccount(Integer tokenValue) throws VerifyErrorException;

    Optional<User> getUserByCuit(String cuit);

    Optional<User> getUserById(int id);
    boolean existsUser(String cuit);

    void updateProfilePicture(Integer userId, Integer imageId);

    byte[] getProfilePicture(Integer userId);

    void updateProfileName(Integer userId, String name);
}
