package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    String email;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(String email, String name, String id, String password){
        this.email = email;
        return userDao.create(email,name,id, passwordEncoder.encode(password));
    }

    @Override
    public Optional<User> getUserByCuit(String cuit){
        return userDao.getUserByCuit(cuit);
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public String toString(){
        return this.email;
    }
}
