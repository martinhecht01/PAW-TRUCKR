package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserService {

    User createUser(String email, String name, String id, String role, String password);
    Optional<User> getUserByCuit(String cuit);
    User getUserById(int id);
}
