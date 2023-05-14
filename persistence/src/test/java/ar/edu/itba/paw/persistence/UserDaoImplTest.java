package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
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
    private UserDaoImpl userDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

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

    @Test
    public void testFindByIdDoesNotExist() throws SQLException{
        // 2. Ejercitar
        Optional<User> maybeUser = userDao.getUserById(USERID_NOT_EXISTENT);

        // 3. Postcondiciones
        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    public void testCreate(){
        // 2. Ejercitar
        User user = userDao.create(EMAIL_NEW, NAME_NEW, CUIT_NEW, ROLE, PASSWORD);

        // 3. Postcondiciones
        Assert.assertNotNull(user);
        Assert.assertEquals(EMAIL_NEW, user.getEmail());
        Assert.assertEquals(PASSWORD, user.getPassword());
        Assert.assertEquals(CUIT_NEW, user.getCuit());
        Assert.assertEquals(NAME_NEW, user.getName());
        Assert.assertEquals(ROLE, user.getRole());
        Assert.assertEquals(2, user.getUserId());
        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

}