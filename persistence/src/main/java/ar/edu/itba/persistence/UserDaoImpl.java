package ar.edu.itba.persistence;

import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private final static RowMapper<User> ROW_MAPPER = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getString("email"), rs.getString("username"),  rs.getString("userid"));
        }
    };


    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<User> findById(final String id) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }
        return Optional.of(list.get(0));
    }


    @Override
    public User create(final String email, final String password) {
        return new User(email,password,"0");
    }
}
