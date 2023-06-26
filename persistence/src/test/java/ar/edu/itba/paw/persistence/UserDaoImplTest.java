package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class UserDaoImplTest {

    private static final String EMAIL_EXISTENT = "martinh563@email.com";
    private static final String NAME_EXISTENT = "Testing Testalez";
    private static final String CUIT_EXISTENT = "20-12345678-9";
    private static final String PASSWORD = "1234567890";
    private static final String ROLE = "PROVIDER";
    private static final int USERID_EXISTENT = 1;
    private static final int USERID_NOT_EXISTENT = 2;

    private static final String EMAIL_NEW = "testingnew@email.com";
    private static final String NAME_NEW = "Testing Testez";
    private static final String CUIT_NEW = "20-12345678-0";

    @Autowired
    private DataSource ds;

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager em;


    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Rollback
    @Test
    public void testFindById() throws SQLException{

        // 2. Ejercitar
        Optional<User> maybeUser = userDao.getUserById(USERID_EXISTENT);

        // 3. Postcondiciones
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(CUIT_EXISTENT, maybeUser.get().getCuit());
        Assert.assertEquals(EMAIL_EXISTENT, maybeUser.get().getEmail());
        Assert.assertEquals(NAME_EXISTENT, maybeUser.get().getName());
        Assert.assertEquals(PASSWORD, maybeUser.get().getPassword());
    }

    @Rollback
    @Test
    public void testFindByCuit(){
        //2. Ejercitar
        Optional<User> maybeUser = userDao.getUserByCuit(CUIT_EXISTENT);

        //3. Postcondiciones
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(CUIT_EXISTENT, maybeUser.get().getCuit());
        Assert.assertEquals(EMAIL_EXISTENT, maybeUser.get().getEmail());
        Assert.assertEquals(NAME_EXISTENT, maybeUser.get().getName());
        Assert.assertEquals(PASSWORD, maybeUser.get().getPassword());
    }

    @Rollback
    @Test
    public void testCreate(){
        // 2. Ejercitar
        User user = userDao.create(EMAIL_NEW, NAME_NEW, CUIT_NEW, ROLE, PASSWORD, Locale.ENGLISH);

        em.flush();

        // 3. Postcondiciones
        Assert.assertNotNull(user);
        Assert.assertEquals(EMAIL_NEW, user.getEmail());
        Assert.assertEquals(PASSWORD, user.getPassword());
        Assert.assertEquals(CUIT_NEW, user.getCuit());
        Assert.assertEquals(NAME_NEW, user.getName());
        Assert.assertEquals(ROLE, user.getRole());
        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Rollback
    @Test
    public void testFindByIdDoesNotExist() throws SQLException{
        // 2. Ejercitar
        Optional<User> maybeUser = userDao.getUserById(USERID_NOT_EXISTENT);

        // 3. Postcondiciones
        Assert.assertFalse(maybeUser.isPresent());
    }

    @Rollback
    @Test
    public void testCuitAlreadyExists() throws SQLException {
        // 2. Ejercitar
        User user = userDao.create(EMAIL_NEW, NAME_NEW, CUIT_EXISTENT, ROLE, PASSWORD, Locale.ENGLISH);

        // 3. Postcondiciones
        //Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "cuit = '" + CUIT_EXISTENT + "'"));
    }

    @Rollback
    @Test
    public void testCreateResetHash(){
        //1 - Precondiciones
        final String newPassword = "newPassword";

        //2 - Ejercitar
        Optional<Integer> hash = userDao.createReset(USERID_EXISTENT, 234234242);

        //3 - Postcondiciones
        Assert.assertTrue(hash.isPresent());
    }

    @Rollback
    @Test
    public void testResetPassword(){
        //1 - Precondiciones
        final String newPassword = "newPassword";
        final Integer hash = 1234567890;

        //2 - Ejercitar
        userDao.resetPassword(hash, newPassword);

        //3 - Postcondiciones
        Optional<User> maybeUser = userDao.getUserById(USERID_EXISTENT);
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(newPassword, maybeUser.get().getPassword());
    }

    @Rollback
    @Test
    public void testExistsUser(){
        //2 - Ejercitar
        boolean exists = userDao.existsUser(CUIT_EXISTENT);

        //3 - Postcondiciones
        Assert.assertTrue(exists);
    }

//    @Rollback
//    @Test
//    public void testVerifyAccount(){
//        //2 - Ejercitar
//        userDao.verifyAccount(USERID_EXISTENT);
//
//        //3 - Postcondiciones
//        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "accountverified = true AND userid = 1"));
//    }

}


