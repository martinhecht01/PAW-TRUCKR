package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    public User create(final String email, final String name, final String userId, final String password, final String role);
    public Optional<User> getUserByCuit(String userCuit);
    public Optional<User> getUserById(int id);
    Optional<User> findById(String id);
    public boolean existsUser(String cuit);
}
