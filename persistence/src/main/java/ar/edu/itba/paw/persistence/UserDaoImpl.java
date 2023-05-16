package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.models.Reset;
import ar.edu.itba.paw.models.SecureToken;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private final static RowMapper<User> ROW_MAPPER_USER = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getInt("userid"),
                    rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("cuit"),
                    rs.getString("role"),
                    rs.getString("password"),
                    rs.getBoolean("accountverified"),
                    rs.getInt("imageid"));
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

    private final static RowMapper<SecureToken> ROW_MAPPER_TOKEN = new RowMapper<SecureToken>() {
        @Override
        public SecureToken mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SecureToken(rs.getInt("userid"),
                    rs.getString("token"),
                    rs.getTimestamp("expireDate").toLocalDateTime());
        }
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertUsers;
    private final SimpleJdbcInsert jdbcInsertPasswordResets;
    private final SimpleJdbcInsert jdbcInsertSecureTokens;

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsertUsers = new SimpleJdbcInsert(ds).withTableName("users").usingGeneratedKeyColumns("userid");
        this.jdbcInsertPasswordResets = new SimpleJdbcInsert(ds).withTableName("passwordresets");
        this.jdbcInsertSecureTokens = new SimpleJdbcInsert(ds).withTableName("securetokens");
    }

    @Override
    public Optional<Integer> createReset(Integer userId, Integer hash ){
        HashMap<String, Object> data = new HashMap<>();
        data.put("userid", userId);
        data.put("hash", hash);
        data.put("createdate", LocalDateTime.now());
        data.put("completed", null);
        LOGGER.info("Creating reset for user: {}", userId);
        jdbcInsertPasswordResets.execute(data);
        return Optional.of(hash);
    }

    @Override
    public Integer createSecureToken(Integer userId, int token){
        HashMap<String, Object> data = new HashMap<>();
        data.put("userid", userId);
        data.put("token", token);
        data.put("expiredate", LocalDateTime.now().plusHours(1));
        LOGGER.info("Creating secure token for user: {}", userId);
        jdbcInsertSecureTokens.execute(data);
        return token;
    }

    @Override
    public Optional<SecureToken> getSecureTokenByValue(Integer tokenValue) {
        List<SecureToken> tokens = jdbcTemplate.query("SELECT * FROM securetokens WHERE token = ?", ROW_MAPPER_TOKEN, tokenValue);
        if(tokens.isEmpty()){
            LOGGER.info("Token not found. Token: {}", tokenValue);
            return Optional.empty();
        }
        return Optional.of(tokens.get(0));
    }

    @Override
    public void verifyAccount(Integer userId){
        String sql = "UPDATE users SET accountverified = true WHERE userid = ?";
        LOGGER.info("Verifying account for user: {}", userId);
        jdbcTemplate.update(sql, userId);
    }


    @Override
    public Optional<Reset> getResetByHash(Integer hash){
        List<Reset> resets = jdbcTemplate.query("SELECT * FROM passwordresets WHERE hash = ?", ROW_MAPPER_RESET, hash);
        if(resets.isEmpty()){
            LOGGER.warn("Reset not found. Hash: {}", hash);
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
    public void resetPassword(Integer hash, String newPassword){
        String sql = "UPDATE users SET password = ? WHERE userid = (SELECT userid FROM passwordresets WHERE hash = ?)";
        LOGGER.info("Resetting password for hash: {}", hash);
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
        data.put("accountverified", "false");
        data.put("imageid", null);
        LOGGER.info("Creating user. CUIT: {}, EMAIL: {}, NAME: {}",cuit, email, name);
        int userId = jdbcInsertUsers.executeAndReturnKey(data).intValue();
        return new User( userId, email, name, cuit, role, password, false,null);
    }

    @Override
    public Optional<User> getUserByCuit(String userCuit) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE cuit = ?", ROW_MAPPER_USER, userCuit);
        if(users.isEmpty()){
            LOGGER.warn("User not found. CUIT: {}", userCuit);
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> getUserById(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", ROW_MAPPER_USER, id);
        if(users.isEmpty()){
            LOGGER.warn("User not found. UserID: {}", id);
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

    @Override
    public void setImageId(int userId, int imageId){
        LOGGER.info("Updating user image. UserID: {}, ImageID: {}", userId, imageId);
        String sql = "UPDATE users SET imageid = ? WHERE userid = ?";
        jdbcTemplate.update(sql, imageId, userId);
    }

    @Override
    public int getImageId(int userId){
        String sql = "SELECT imageid FROM users WHERE userid = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    @Override
    public void setUserName(int userId, String name){
        LOGGER.info("Updating user name. UserID: {}, Name: {}", userId, name);
        String sql = "UPDATE users SET name = ? WHERE userid = ?";
        jdbcTemplate.update(sql, name, userId);
    }
}
