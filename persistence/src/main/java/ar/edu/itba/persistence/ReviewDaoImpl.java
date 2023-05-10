package ar.edu.itba.persistence;

import ar.edu.itba.paw.interfacesPersistence.ReviewDao;
import ar.edu.itba.paw.models.Review;
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
import java.util.Optional;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    private final static RowMapper<Review> ROW_MAPPER_REVIEW = new RowMapper<Review>() {
        @Override
        public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Review(rs.getInt("tripid"),rs.getInt("userid"),rs.getFloat("rating"),rs.getString("review"));
        }
    };

    private final SimpleJdbcInsert jdbcInsertReviews;

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public ReviewDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS reviews (\n" +
                "  userid INT ,\n" +
                "  tripid INT ,\n" +
                "  rating FLOAT CHECK(rating<=5 AND rating>=0),\n" +
                "  review VARCHAR(400),\n" +
                "  PRIMARY KEY(userid,tripid),\n" +
                "  FOREIGN KEY (userid) REFERENCES users(userid),\n" +
                "  FOREIGN KEY (tripid) REFERENCES trips(tripid)\n" +
                ");");
        this.jdbcInsertReviews = new SimpleJdbcInsert(ds).withTableName("reviews").usingColumns("userid","tripid","rating","review");
    }

    @Override
    public Optional<Review> getReviewByTripAndUserId(int tripId, int userId) {
        String query = "SELECT * FROM reviews WHERE tripid = ? AND userid = ?";
        return Optional.of(jdbcTemplate.query(query, ROW_MAPPER_REVIEW,tripId,userId).get(0));
    }

    @Override
    public Optional<Review> getReviewByRequestAndUserId(int requestId, int userId) {
        String query = "SELECT * FROM reviews WHERE requestid = ? AND userid = ?";
        return Optional.of(jdbcTemplate.query(query, ROW_MAPPER_REVIEW,requestId,userId).get(0));
    }

    @Override
    public Review create(int tripId, int userId, float rating, String review) {
        HashMap<String,Object> data = new HashMap<>();

        data.put("tripid", tripId);
        data.put("userid",userId);
        data.put("rating",rating);
        data.put("review",review);

        jdbcInsertReviews.execute(data);

        return new Review(tripId,userId,rating,review);
    }
}
