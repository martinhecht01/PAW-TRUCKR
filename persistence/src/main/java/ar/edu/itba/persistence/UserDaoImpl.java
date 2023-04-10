package ar.edu.itba.persistence;

import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private final static RowMapper<User> ROW_MAPPER = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getString("email"), rs.getString("username"),  rs.getString("userid"));
        }
    };


    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                "  userId VARCHAR(255) PRIMARY KEY,\n" +
                "  email VARCHAR(255),\n" +
                "  name VARCHAR(255)\n" +
                ");");
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("users");
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
    public User create(final String email, final String name, final String userId) {
        System.out.println(email);
        System.out.println(name);
        System.out.println(userId);
        HashMap<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("name", name);
        data.put("userid", userId);
        jdbcInsert.execute(data);
        return new User(email, name, userId);
    }
}
