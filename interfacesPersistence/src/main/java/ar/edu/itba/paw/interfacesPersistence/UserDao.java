package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserDao {
    public User create(final String email, final String name, final String userId);
    Optional<User> findById(String id);

}
