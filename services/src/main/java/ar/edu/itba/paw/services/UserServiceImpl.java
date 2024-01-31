package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.interfacesPersistence.SecureTokenDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.CuitAlreadyExistsException;
import ar.edu.itba.paw.interfacesServices.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.SecureToken;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

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
    private final SecureTokenDao secureTokenDao;


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           MailService ms,
                           ImageDao imageDao,
                           PasswordEncoder passwordEncoder,
                           SecureTokenDao secureTokenDao){
        this.userDao = userDao;
        this.ms = ms;
        this.imageDao = imageDao;
        this.passwordEncoder = passwordEncoder;
        this.secureTokenDao = secureTokenDao;
    }

    @Transactional
    @Override
    public User createUser(String email, String name, String cuit, String role, String password, Locale locale){

        if( userDao.existsUser(cuit)){
            LOGGER.warn("User could not be created: id already exists, CUIT: {}", cuit);
            throw new CuitAlreadyExistsException();
        }

        User us= userDao.create(email,name,cuit, role, passwordEncoder.encode(password),locale);
        createSecureToken(us, locale);
        return us;
    }

    @Transactional
    @Override
    public void resetPassword(Integer userId, String newPassword){
        if (newPassword != null && !newPassword.isEmpty())
            userDao.resetPassword(userId, passwordEncoder.encode(newPassword));
    }

    @Transactional
    @Override
    public void sendPasswordToken(User user, Locale locale){
        Integer tokenValue = secureTokenDao.createSecureToken(user,hashTo6Digits(LocalDateTime.now(),user.getUserId().toString()));
        ms.sendResetEmail(user, tokenValue, locale);
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
        Integer tokenValue = secureTokenDao.createSecureToken(user,hashTo6Digits(LocalDateTime.now(),user.getUserId().toString()));
        ms.sendSecureTokenEmail(user, tokenValue,locale);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<SecureToken> getSecureToken(String tokenValue){
        //return empty if string does not contain a number
        if(!tokenValue.matches("\\d+"))
            return Optional.empty();
        return secureTokenDao.getSecureTokenByValue(Integer.valueOf(tokenValue));
    }

    @Transactional
    @Override
    public boolean deleteToken(String token){
        if(!token.matches("\\d+"))
            return false;
        return secureTokenDao.delete(Integer.valueOf(token));
    }

    @Transactional
    @Override
    public boolean validateToken(Integer tokenValue, Locale locale){
        Optional<SecureToken> token = secureTokenDao.getSecureTokenByValue(tokenValue);
        if(!token.isPresent()) {
            LOGGER.warn("Account not verified. Token missing.");
            return false;
        }

        if(token.get().isExpired()){
            deleteToken(tokenValue.toString());
            LOGGER.warn("Account not verified. Token expired. Token: {}", tokenValue);
            return false;
        } else {
            boolean wasVerified = userDao.verifyAccount(token.get().getUser());
            if(!wasVerified){
                ms.sendConfirmationEmail(token.get().getUser(), locale);
            }
        }
        return true;
    }

    @Transactional
    @Override
    public void resendToken(String cuit){
        User user = getUserByCuit(cuit).orElseThrow(UserNotFoundException::new);
        Optional<SecureToken> token = secureTokenDao.findTokenByUser(user);
        token.ifPresent(secureToken -> deleteToken(secureToken.getToken().toString()));
        createSecureToken(user, user.getLocale());
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
    public void updateProfile(Integer userId, Integer imageId, String name) {
        if (imageId != null && imageId != 0){
            imageDao.getImage(imageId).orElseThrow(ImageNotFoundException::new);
            updateProfilePicture(userId, imageId);
        }

        if (name != null && !name.isEmpty())
            updateProfileName(userId, name);
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
