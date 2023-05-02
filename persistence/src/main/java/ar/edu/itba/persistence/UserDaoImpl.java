package ar.edu.itba.persistence;

import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private final static RowMapper<User> ROW_MAPPER_USER = new RowMapper<User>() {
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

    private final static RowMapper<Reset> ROW_MAPPER_RESET = new RowMapper<Reset>() {
        @Override
        public Reset mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Reset(rs.getInt("userid"),
                    rs.getString("hash"),
                    rs.getTimestamp("createDate").toLocalDateTime(),
                    rs.getBoolean("completed"));
        }
    };


    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertUsers;
    private final SimpleJdbcInsert jdbcInsertPasswordResets;
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
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS passwordresets(\n" +
                "   userid int REFERENCES users(userid),\n" +
                "   hash int PRIMARY KEY,\n" +
                "   createdate TIMESTAMP,\n" +
                "   completed VARCHAR(20)\n" +
                ");");
        this.jdbcInsertUsers = new SimpleJdbcInsert(ds).withTableName("users").usingGeneratedKeyColumns("userid");
        this.jdbcInsertPasswordResets = new SimpleJdbcInsert(ds).withTableName("passwordresets");
    }

    @Override
    public void createReset(Integer userId, Integer hash ){
        HashMap<String, Object> data = new HashMap<>();
        data.put("userid", userId);
        data.put("hash", hash);
        data.put("createdate", LocalDateTime.now());
        data.put("completed", null);
        jdbcInsertPasswordResets.execute(data);
    }

    @Override
    public Optional<Reset> getResetByHash(Integer hash){
        List<Reset> resets = jdbcTemplate.query("SELECT * FROM passwordresets WHERE hash = ?", ROW_MAPPER_RESET, hash);
        if(resets.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(resets.get(0));
    }

    @Override
    public void completeReset(Integer hash){
        String sql = "UPDATE passwordresets SET completed = ? WHERE hash = ?";
        jdbcTemplate.update(sql, "true", hash);
    }

    @Override
    public Optional<User> findById(final String id) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", ROW_MAPPER_USER, id);
        if (list.isEmpty()) {
            return null;
        }
        return Optional.of(list.get(0));
    }

    @Override
    public void resetPassword(Integer hash, String newPassword){
        String sql = "UPDATE users SET password = ? WHERE userid = (SELECT userid FROM passwordresets WHERE hash = ?)";
        jdbcTemplate.update(sql, newPassword, hash);
    }


    @Override
    public User create(final String email, final String name, final String cuit, final String role, final String password) {
        HashMap<String, String> data = new HashMap<>();
        data.put("cuit", cuit);
        data.put("email", email);
        data.put("name", name);
        data.put("role", role);
        data.put("password", password); 
        int userId = jdbcInsertUsers.executeAndReturnKey(data).intValue();
        return new User( userId, email, name, cuit, role, password);
    }

    @Override
    public Optional<User> getUserByCuit(String userCuit) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE cuit = ?", ROW_MAPPER_USER, userCuit);
        if(users.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> getUserById(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", ROW_MAPPER_USER, id);
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
