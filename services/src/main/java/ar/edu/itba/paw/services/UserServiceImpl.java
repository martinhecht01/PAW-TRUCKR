package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.SecureToken;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
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

        if(id == null || userDao.existsUser(id))
            return null;

        User us= userDao.create(email,name,id, role, passwordEncoder.encode(password));
        try{
            ms.sendConfirmationEmail(us);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        try{
            createSecureToken(us.getUserId());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return us;
    }

    @Override
    public void resetPassword(Integer hash, String newPassword){
        userDao.resetPassword(hash, passwordEncoder.encode(newPassword));
        userDao.completeReset(hash);
    }

    @Override
    public Optional<Reset> getResetByHash(Integer hash){
        Optional<Reset> reset = userDao.getResetByHash(hash);

        if(!reset.isPresent())
            return reset;

        Duration interval = Duration.between(reset.get().getCreateDate(), LocalDateTime.now());
        if(reset.get().isCompleted() || interval.toHours() > 24)
            return Optional.empty();

        return reset;
    }

    @Override
    public void createReset(Integer userId){
        Integer hash = userDao.createReset(userId, Objects.hash(LocalDateTime.now() + userId.toString()) ).get();
        try{ms.sendResetEmail(userDao.getUserById(userId).get(),hash);
        } catch(MessagingException e){
            throw new RuntimeException();
        }
    }
    private static int hashTo6Digits(Object obj1, Object obj2) {
        int hash = Objects.hash(obj1, obj2);
        // Truncate the hash to 6 digits
        int truncatedHash = Math.abs(hash) % 1000000;
        return truncatedHash;
    }

    @Override
    public void createSecureToken(Integer userId) {
        Integer tokenValue = userDao.createSecureToken(userId,hashTo6Digits(LocalDateTime.now(),userId.toString()));

        try{ms.sendSecureTokenEmail(userDao.getUserById(userId).get(),tokenValue);
        } catch(MessagingException e){
            throw new RuntimeException();
        }

    }

    @Override
    public boolean verifyAccount(Integer tokenValue){
        Optional<SecureToken> token = userDao.getSecureTokenByValue(tokenValue);
        if(!token.isPresent() || token.get().isExpired())
            return false;
        else
            userDao.verifyAccount(token.get().getUserId());
        return true;
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

    @Override
    public boolean existsUser(String cuit) {
        return userDao.existsUser(cuit);
    }

}
