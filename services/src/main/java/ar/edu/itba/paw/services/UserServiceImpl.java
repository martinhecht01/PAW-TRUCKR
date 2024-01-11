package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.SecureToken;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final MailService ms;
    private final ImageDao imageDao;

    String email;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, MailService ms, ImageDao imageDao, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.ms = ms;
        this.imageDao = imageDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User createUser(String email, String name, String id, String role, String password, Locale locale){
        this.email = email;

        if(id == null) {
            LOGGER.warn("User could not be created: id is null");
            return null;
        }

        if( userDao.existsUser(id)){
            LOGGER.warn("User could not be created: id already exists, ID: {}", id);
            return null;
        }

        User us= userDao.create(email,name,id, role, passwordEncoder.encode(password),locale);

        createSecureToken(us,locale);

        return us;
    }

    @Transactional
    @Override
    public void resetPassword(Integer hash, String newPassword){
        userDao.resetPassword(hash, passwordEncoder.encode(newPassword));
        userDao.completeReset(hash);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Reset> getResetByHash(Integer hash){
        Optional<Reset> reset = userDao.getResetByHash(hash);


        //raro lo que devuelve esto. Revisar
        if(!reset.isPresent())
            return reset;

        Duration interval = Duration.between(reset.get().getCreateDate().toLocalDateTime(), LocalDateTime.now());
        if(reset.get().getCompleted() || interval.toHours() > 24)
            return Optional.empty();

        return reset;
    }

    @Transactional
    @Override
    public void createReset(Integer userId, Locale locale){

        Integer hash = userDao.createReset(userId, Objects.hash(LocalDateTime.now() + userId.toString()) ).get();
        ms.sendResetEmail(userDao.getUserById(userId).get(), hash,locale);
    }

    private static int hashTo6Digits(Object obj1, Object obj2) {
        int hash = Objects.hash(obj1, obj2);
        // Truncate the hash to 6 digits
        int truncatedHash = Math.abs(hash) % 1000000;
        return truncatedHash;
    }

    @Transactional
    @Override
    public void createSecureToken(User user, Locale locale) {
//        User user = userDao.getUserById(userId).orElseThrow(UserNotFoundException::new);
        Integer tokenValue = userDao.createSecureToken(user,hashTo6Digits(LocalDateTime.now(),user.getUserId().toString()));
        ms.sendSecureTokenEmail(user, tokenValue,locale);
    }

    @Transactional
    @Override
    public boolean verifyAccount(Integer tokenValue, Locale locale){
        Optional<SecureToken> token = userDao.getSecureTokenByValue(tokenValue);
        if(!token.isPresent()) {
            LOGGER.warn("Account not verified. Token missing.");
            return false;
        }

        if(token.get().isExpired()){
            LOGGER.warn("Account not verified. Token expired. Token: {}", tokenValue);
            return false;
        } else {
            userDao.verifyAccount(token.get().getUser());
            ms.sendConfirmationEmail(token.get().getUser(), locale);
        }
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserByCuit(String cuit){
        return userDao.getUserByCuit(cuit);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public String toString(){
        return this.email;
    }

    @Transactional
    @Override
    public void updateProfilePicture(Integer userId, Integer imageId) {
        User user = userDao.getUserById(userId).orElseThrow(UserNotFoundException::new);
        Image image = imageDao.getImage(imageId).orElseThrow(UserNotFoundException::new);
        user.setImage(image);
    	//userDao.setImageId(userId, imageId);
    }

    @Transactional
    @Override
    public byte[] getProfilePicture(Integer userId) {
        User user = userDao.getUserById(userId).orElseThrow(UserNotFoundException::new);
        return user.getImage().getImage();
        //return imageDao.getImage(userDao.getImageId(userId)).get().getImage();
    }

    @Transactional
    @Override
    public void updateProfileName(Integer userId, String name) {
    	userDao.setUserName(userId, name);
    }

    @Transactional
    @Override
    public void updateLocale(Integer userId, Locale locale) {
    	userDao.setLocale(userId, locale);
    }

    @Transactional
    @Override
    public Optional<User> getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            return Optional.empty();
        return getUserByCuit(auth.getName());
    }
}
