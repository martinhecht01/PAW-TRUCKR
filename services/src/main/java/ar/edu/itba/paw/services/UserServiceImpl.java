package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    String email;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(String email, String name, String id, String password){
        this.email = email;
        return userDao.create(email,name,id, password);
    }

    @Override
    public User getUserByCuit(String cuit){
        return userDao.getUserByCuit(cuit);
    }

    @Override
    public User getUserById(int id){
        return userDao.getUserById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public String toString(){
        return this.email;
    }
}
