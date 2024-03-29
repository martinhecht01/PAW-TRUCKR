package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfacesPersistence.SecureTokenDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.exceptions.CuitAlreadyExistsException;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String EMAIL = "testing@gmail.com";
    private static final String NAME = "Testing Testington";
    private static final String ROLE = "PROVIDER";
    private static final String PASSWORD = "password";
    private static final String CUIT = "20-12345678-9";
    private static final int USERID = 1;


    @Mock
    private SecureTokenDao secureTokenDao;

    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private MailServiceImpl mailService;
    @Mock
    private ImageServiceImpl imageService;

    @Test
    public void testCreateUser() {
        // 1 Precondiciones
        when(userDao.existsUser(anyString()))
                .thenReturn(false);

        when(userDao.create(eq(EMAIL), eq(NAME), eq(CUIT), eq(ROLE), eq(PASSWORD), eq(Locale.ENGLISH)))
                .thenReturn( new User(USERID, EMAIL, NAME, CUIT, ROLE, PASSWORD, false, null,Locale.ENGLISH));

        when(passwordEncoder.encode(anyString())).thenReturn(PASSWORD);
        when(secureTokenDao.createSecureToken(any(User.class),anyInt())).thenReturn(1);

        //2 Ejercitar
        User user = userService.createUser(EMAIL, NAME, CUIT, ROLE, PASSWORD, Locale.ENGLISH);

        //3 Postcondiciones
        Assert.assertNotNull(user);
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(NAME, user.getName());
        Assert.assertEquals(CUIT, user.getCuit());
        Assert.assertEquals(ROLE, user.getRole());
        Assert.assertEquals(PASSWORD, user.getPassword());
    }

    @Test(expected = CuitAlreadyExistsException.class)
    public void testCreateUserWithExistingId() {
        // 1 Precondiciones
        when(userDao.existsUser(anyString()))
                .thenReturn(true);

        //2 Ejercitar
        User user = userService.createUser(EMAIL, NAME, CUIT, ROLE, PASSWORD, Locale.ENGLISH);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserWithNullId() {

        User user = userService.createUser(EMAIL, NAME, null, ROLE, PASSWORD, Locale.ENGLISH);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByIdWithNonExistentId() {
        // 1 Precondiciones
        when(userDao.getUserById(anyInt()))
                .thenReturn(Optional.empty());

        //2 Ejercitar
        userService.getUserById(USERID);
    }


    @Test
    public void testGetUserByIdWithExistentId() {
        // 1 Precondiciones
        when(userDao.getUserById(anyInt()))
                .thenReturn(Optional.of(new User(USERID, EMAIL, NAME, CUIT, ROLE, PASSWORD, false, null,Locale.ENGLISH)));

        //2 Ejercitar
        User user = userService.getUserById(USERID).get();

        //3 Postcondiciones
        Assert.assertNotNull(user);
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(NAME, user.getName());
        Assert.assertEquals(CUIT, user.getCuit());
        Assert.assertEquals(ROLE, user.getRole());
        Assert.assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    public void testResetUserPassword() {
        // 1 Precondiciones
        User existingUser = new User(USERID, EMAIL, NAME, CUIT, ROLE, PASSWORD, false, null, Locale.ENGLISH);

        String newPassword = "newPassword";
        when(passwordEncoder.encode(eq(newPassword))).thenReturn(newPassword);

        // 2 Ejercitar
        userService.resetPassword(USERID, newPassword);

        // 3 Postcondiciones
        verify(userDao, times(1)).resetPassword(eq(USERID), eq(newPassword));
    }


}
