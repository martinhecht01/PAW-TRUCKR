package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final MailService ms;
    String email;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, MailService ms, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.ms = ms;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(String email, String name, String id, String role, String password){
        this.email = email;

        User us= userDao.create(email,name,id, role, passwordEncoder.encode(password));
        try{
            ms.sendConfirmationEmail(us);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return us;
    }

    @Override
    public Optional<User> getUserByCuit(String cuit){
        return userDao.getUserByCuit(cuit);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public String toString(){
        return this.email;
    }

}
