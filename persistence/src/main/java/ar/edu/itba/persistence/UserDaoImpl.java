package ar.edu.itba.persistence;

import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.swing.text.html.Option;
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
            return new User(rs.getInt("userid"),
                    rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("cuit"),
                    rs.getString("role"),
                    rs.getString("password"));
        }
    };


    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                "  userid SERIAL PRIMARY KEY,\n" +
                "  cuit VARCHAR(255) UNIQUE,\n" +
                "  email VARCHAR(255),\n" +
                "  name VARCHAR(255),\n" +
                "  role VARCHAR(255),\n" +
                "  password VARCHAR(255)\n" +
                ");");
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("users").usingGeneratedKeyColumns("userid");
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
    public User create(final String email, final String name, final String cuit, final String role, final String password) {
        HashMap<String, String> data = new HashMap<>();
        data.put("cuit", cuit);
        data.put("email", email);
        data.put("name", name);
        data.put("role", role);
        data.put("password", password);
        int userId = jdbcInsert.executeAndReturnKey(data).intValue();
        return new User( userId, email, name, cuit, role, password);
    }

    @Override
    public Optional<User> getUserByCuit(String userCuit) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE cuit = ?", ROW_MAPPER, userCuit);
        if(users.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> getUserById(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", ROW_MAPPER, id);
        if(users.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public boolean existsUser(String cuit) {
        String sql = "SELECT COUNT(*) FROM users WHERE cuit = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, cuit);
        return count > 0;
    }
}
