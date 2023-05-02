package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.interfacesServices.exceptions.ResetErrorException;
import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserService {

    User createUser(String email, String name, String id, String role, String password);

    void resetPassword(Integer hash, String newPassword);

    Optional<Reset> getResetByHash(Integer hash) throws ResetErrorException;

    void createReset(Integer userId);

    Optional<User> getUserByCuit(String cuit);

    Optional<User> getUserById(int id);
    boolean existsUser(String cuit);
}
