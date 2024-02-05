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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    private static final float RATING_EXISTENT5 = 4;
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

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;
    private User user;
    private Trip trip;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
        user = em.find(User.class, USERID_EXISTENT);
        trip = em.find(Trip.class, TRIPID_EXISTENT5);
    }

    @Rollback
    @Test
    public void testGetReviewByTripAndUserId() {
        Optional<Review> maybeReview = reviewDao.getReviewByTripAndUserId(trip, user);

        // 3. Postcondiciones
        Assert.assertTrue(maybeReview.isPresent());
        Assert.assertEquals(RATING_EXISTENT5, maybeReview.get().getRating(), 0);
        Assert.assertEquals(REVIEW_EXISTENT, maybeReview.get().getReview());
    }

    @Rollback
    @Test
    public void testGetReviewByTripAndUserIdNotExistent() {

        // 2. Ejercitar
        Optional<Review> maybeReview = reviewDao.getReviewByTripAndUserId(em.createQuery("SELECT t from Trip t WHERE t.tripId = :tripId", Trip.class).setParameter("tripId", TRIPID_EXISTENT).getSingleResult(), user);

        // 3. Postcondiciones
        Assert.assertFalse(maybeReview.isPresent());
    }

    @Rollback
    @Test
    public void testCreateReview() {
        // 1. Preconditions - Set up necessary data
        float rating = RATING_NOT_EXISTENT;
        String reviewText = REVIEW_NOT_EXISTENT;

        // 2. Exercise
        Review createdReview = reviewDao.createReview(trip, user, rating, reviewText);

        // Flush changes to synchronize with the database
        em.flush();

        // 3. Postconditions
        // Assert the properties of the retrieved review
        Assert.assertNotNull(createdReview);
        Assert.assertEquals(trip.getTripId(), createdReview.getTripId());
        Assert.assertEquals(rating, createdReview.getRating(), 0.01); // Add a delta for floating-point precision
        Assert.assertEquals(reviewText, createdReview.getReview());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviews", "reviewId = " + createdReview.getReviewId()));
    }


    @Rollback
    @Test
    public void testGetUserRating() {
        // 2. Ejercitar
        Double rating = reviewDao.getUserRating(em.find(User.class, USERID_EXISTENT));

        // 3. Postcondiciones
        double correctRating = ( RATING_EXISTENT2 + RATING_EXISTENT3 + RATING_EXISTENT4 + RATING_EXISTENT5 ) / 4.0;
        Assert.assertEquals(correctRating, rating, 0);
    }

    @Rollback
    @Test
    public void testGetUserRatingNotExistent(){
        // 2. Ejercitar
        double rating = reviewDao.getUserRating(em.find(User.class, USERID_NOT_EXISTENT));

        // 3. Postcondiciones
        Assert.assertEquals(0, rating, 0);
    }

    @Rollback
    @Test
    public void testGetUserReviews() {
        // Insert reviews into the database before running the test

        // 2. Ejercitar
        List<Review> reviews = reviewDao.getUserReviews(em.find(User.class, USERID_EXISTENT), 1, 12);

        // 3. Postcondiciones
        Assert.assertEquals(4, reviews.size());

        // Check the properties of each review
        Assert.assertNotNull(reviews.get(0));
        Assert.assertEquals(3.0, reviews.get(0).getRating(), 0);
        Assert.assertEquals("Good. Met the agreed terms.", reviews.get(0).getReview());

        Assert.assertNotNull(reviews.get(1));
        Assert.assertEquals(1.0, reviews.get(1).getRating(), 0);
        Assert.assertEquals("Did not keep their word.", reviews.get(1).getReview());

        Assert.assertNotNull(reviews.get(2));
        Assert.assertEquals(2.0, reviews.get(2).getRating(), 0);
        Assert.assertEquals("Average job. Did not arrive on time.", reviews.get(2).getReview());

        Assert.assertNotNull(reviews.get(3));
        Assert.assertEquals(4.0, reviews.get(3).getRating(), 0);
        Assert.assertEquals("Excelente viaje, muy recomendable", reviews.get(3).getReview());
    }


}
