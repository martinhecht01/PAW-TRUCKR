package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.ReviewDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
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
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ReviewDaoImplTest {

    private static final String EMAIL_EXISTENT = "martinh563@email.com";
    private static final String NAME_EXISTENT = "Testing Testalez";
    private static final String CUIT_EXISTENT = "20-12345678-9";
    private static final int TRIPID_EXISTENT = 1;
    private static final int TRIPID_EXISTENT2 = 2;
    private static final int TRIPID_EXISTENT3 = 3;
    private static final int TRIPID_EXISTENT4 = 4;
    private static final int TRIPID_EXISTENT5 = 5;
    private static final float RATING_EXISTENT = 5;
    private static final float RATING_EXISTENT2 = 3;
    private static final float RATING_EXISTENT3 = 1;
    private static final float RATING_EXISTENT4 = 2;
    private static final float RATING_NOT_EXISTENT = 2;
    private static final String PASSWORD = "1234567890";
    private static final String REVIEW_EXISTENT = "Excelente viaje, muy recomendable";
    private static final String REVIEW_EXISTENT2 = "Bien. Cumplio con lo acordado.";
    private static final String REVIEW_EXISTENT3 = "No cumplio con su palabra.";
    private static final String REVIEW_EXISTENT4 = "Mediocre trabajo. No llego a tiempo.";
    private static final String REVIEW_NOT_EXISTENT = "No me gusto el viaje. Tardo mucho en contestar.";
    private static final String ROLE_EXISTENT = "PROVIDER";
    private static final int USERID_EXISTENT = 1;
    private static final int USERID_NOT_EXISTENT = 2;

    @Autowired
    private DataSource ds;

    @Autowired
    private ReviewDao reviewDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

//    @Rollback
//    @Test
//    public void testGetReviewByTripAndUserId() {
//        // 2. Ejercitar
//        String sql1 = "SELECT * FROM trips WHERE tripid = ?";
//        String sql2 = "SELECT * FROM users WHERE userid = ?";
//        Optional<Review> maybeReview = reviewDao.getReviewByTripAndUserId(jdbcTemplate.queryForObject(sql1, Trip.class, TRIPID_EXISTENT), jdbcTemplate.queryForObject(sql2, User.class, USERID_EXISTENT));
//
//        // 3. Postcondiciones
//        Assert.assertTrue(maybeReview.isPresent());
//        Assert.assertEquals(RATING_EXISTENT, maybeReview.get().getRating(),0);
//        Assert.assertEquals(REVIEW_EXISTENT, maybeReview.get().getReview());
//    }
//
//    @Rollback
//    @Test
//    public void testGetReviewByTripAndUserIdNotExistent() {
//        // 2. Ejercitar
//        Optional<Review> maybeReview = reviewDao.getReviewByTripAndUserId(TRIPID_EXISTENT, USERID_NOT_EXISTENT);
//
//        // 3. Postcondiciones
//        Assert.assertFalse(maybeReview.isPresent());
//    }
//
//    @Rollback
//    @Test
//    public void testCreateReview(){
//        // 2. Ejercitar
//        reviewDao.createReview(TRIPID_EXISTENT5,USERID_EXISTENT, RATING_NOT_EXISTENT, REVIEW_NOT_EXISTENT);
//
//        // 3. Postcondiciones
//        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviews", "tripid = 5 AND userid = 1 AND rating = 2 AND review = 'No me gusto el viaje. Tardo mucho en contestar.'"));
//    }
//
//    @Rollback
//    @Test
//    public void testGetUserRating(){
//        // 2. Ejercitar
//        float rating = reviewDao.getUserRating(USERID_EXISTENT);
//
//        // 3. Postcondiciones
//        float correctRating = (RATING_EXISTENT+RATING_EXISTENT2+RATING_EXISTENT3+RATING_EXISTENT4)/4;
//        Assert.assertEquals(correctRating, rating, 0);
//    }
//
//    @Rollback
//    @Test
//    public void testGetUserRatingNotExistent(){
//        // 2. Ejercitar
//        float rating = reviewDao.getUserRating(USERID_NOT_EXISTENT);
//
//        // 3. Postcondiciones
//        Assert.assertEquals(0, rating, 0);
//    }
//
//    @Rollback
//    @Test
//    public void testGetUserReviews(){
//        // 2. Ejercitar
//        List<Review> reviews = reviewDao.getUserReviews(USERID_EXISTENT);
//
//        // 3. Postcondiciones
//        Assert.assertEquals(4, reviews.size());
//        Assert.assertNotNull(reviews.get(0));
//        Assert.assertEquals(RATING_EXISTENT, reviews.get(0).getRating(), 0);
//        Assert.assertNotNull(reviews.get(1));
//        Assert.assertEquals(RATING_EXISTENT2, reviews.get(1).getRating(), 0);
//        Assert.assertNotNull(reviews.get(2));
//        Assert.assertEquals(RATING_EXISTENT3, reviews.get(2).getRating(), 0);
//        Assert.assertNotNull(reviews.get(3));
//        Assert.assertEquals(RATING_EXISTENT4, reviews.get(3).getRating(), 0);
//    }

}
