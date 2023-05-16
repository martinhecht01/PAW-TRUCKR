package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.ReviewDao;
import ar.edu.itba.paw.models.Review;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    Logger LOGGER = LoggerFactory.getLogger(ReviewDaoImpl.class);

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
        this.jdbcInsertReviews = new SimpleJdbcInsert(ds).withTableName("reviews").usingColumns("userid","tripid","rating","review");
    }

    @Override
    public Optional<Review> getReviewByTripAndUserId(int tripId, int userId) {
        String query = "SELECT * FROM reviews WHERE tripid = ? AND userid = ?";
        List<Review> reviews= jdbcTemplate.query(query, ROW_MAPPER_REVIEW,tripId,userId);
        if(reviews.isEmpty()){
            LOGGER.info("No review found for trip {} and user {}",tripId,userId);
            return Optional.empty();
        }
        LOGGER.info("Returning reviews for tripId {} and userId {}",tripId,userId);
        return Optional.of(reviews.get(0));
    }


    @Override
    public void createReview(int tripid, int userid, float rating, String comment) {
        HashMap<String,Object> data = new HashMap<>();

        data.put("tripid", tripid);
        data.put("userid",userid);
        data.put("rating",rating);
        data.put("review",comment);
        LOGGER.info("Creating review for trip {} and user {}",tripid,userid);
        jdbcInsertReviews.execute(data);
    }

    @Override
    public float getUserRating(int userId) {
        String sql = "SELECT AVG(rating) FROM reviews WHERE userid = ?";
        Float result = jdbcTemplate.queryForObject(sql, Float.class,userId);
        if( result == null){
            LOGGER.info("No rating found for user {}", userId);
            return 0;
        }
        return result;
    }

    @Override
    public List<Review> getUserReviews(int userId) {
        String query = "SELECT * FROM reviews WHERE userid = ?";
        LOGGER.info("Returning reviews for user {}",userId);
        return jdbcTemplate.query(query, ROW_MAPPER_REVIEW,userId);
    }
}
