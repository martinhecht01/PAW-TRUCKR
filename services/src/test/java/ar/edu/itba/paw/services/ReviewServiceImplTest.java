package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.AlertDao;
import ar.edu.itba.paw.interfacesPersistence.ReviewDao;
import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.exceptions.ReviewAlreadyExistsException;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import ar.edu.itba.paw.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ar.edu.itba.paw.interfacesPersistence.SecureTokenDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.exceptions.CuitAlreadyExistsException;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceImplTest {
    private static final int TRIPID_NOT_EXISTENT = 1;
    private static final int TRUCKERID_NOT_EXISTENT = 1;

    private static final String TRUCKERNAME_NOT_EXISTENT = "Julian Marenco";

    private static final int PROVIDERID_NOT_EXISTENT = 2;

    private static final String LICENSEPLATE_NOT_EXISTENT = "AHU123";
    private static final int WEIGHT_NOT_EXISTENT = 100;

    private static final int VOLUME_NOT_EXISTENT = 100;
    private static final LocalDateTime DEPARTUREDATE_NOT_EXISTENT = LocalDateTime.now();
    private static final LocalDateTime ARRIVALDATE_NOT_EXISTENT = LocalDateTime.of(2024, 12, 12, 12, 12);

    private static final String ORIGIN_NOT_EXISTENT = "Buenos Aires";

    private static final String DESTINATION_NOT_EXISTENT = "Chivilcoy";

    private static final String TYPE_NOT_EXISTENT = "REFRIGERATED";

    private static final int PRICE_NOT_EXISTENT = 100;

    private static final int PROPOSALID_NOT_EXISTENT = 1;
    private static final String PROPOSAL_DESCRIPTION = "Proposal description.";

    private static final String EMAIL = "testing@gmail.com";
    private static final String NAME = "Testing Testington";
    private static final String ROLE_PROVIDER = "PROVIDER";

    private static final String ROLE_TRUCKER="TRUCKER";
    private static final String PASSWORD = "password";
    private static final String CUIT = "20-12345678-9";

    @Mock
    private TripDaoV2 tripDao;
    @InjectMocks
    private TripServiceV2Impl tripService;

    @Mock
    private ReviewDao reviewDao;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private MailServiceImpl ms;

    @Mock
    private UserDao userDao;

    @Mock
    private AlertDao alertDao;

    User user1;
    User user2;
    Trip trip1;
    Proposal offer1;

    @Before
    public void setup()
    {
        user1 = new User(TRUCKERID_NOT_EXISTENT,EMAIL,NAME,CUIT,ROLE_TRUCKER,PASSWORD,true, null, Locale.ENGLISH);
        user2 = new User(PROVIDERID_NOT_EXISTENT,EMAIL,NAME,CUIT,ROLE_PROVIDER,PASSWORD,true, null, Locale.ENGLISH);
        trip1 = new Trip(TRIPID_NOT_EXISTENT,user1, null, LICENSEPLATE_NOT_EXISTENT, WEIGHT_NOT_EXISTENT, VOLUME_NOT_EXISTENT, DEPARTUREDATE_NOT_EXISTENT, ARRIVALDATE_NOT_EXISTENT, ORIGIN_NOT_EXISTENT, DESTINATION_NOT_EXISTENT, TYPE_NOT_EXISTENT, PRICE_NOT_EXISTENT, true, true,null,null,0, false, false);
        offer1 = new Proposal(trip1, user1, PROPOSAL_DESCRIPTION, PRICE_NOT_EXISTENT);
    }

    @Test
    public void testCreateReview() {
        // Arrange
        int tripId = 1;
        int userId = 2;
        float rating = 4.5f;
        String comment = "Great trip!";




        when(userDao.getUserById(userId)).thenReturn(Optional.of(user1));
        when(tripDao.getTripOrRequestById(tripId)).thenReturn(Optional.of(trip1));
        when(reviewDao.getReviewByTripAndUserId(trip1, user1)).thenReturn(Optional.empty());
        when(reviewDao.createReview(trip1, user1, rating, comment)).thenReturn(new Review(trip1, user1, rating, comment));

        // Act
        Review createdReview = reviewService.createReview(tripId, userId, rating, comment);

        // Assert
        Assert.assertNotNull(createdReview);
        verify(reviewDao, times(1)).createReview(eq(trip1), eq(user1), eq(rating), eq(comment));
    }

    @Test(expected = UserNotFoundException.class)
    public void testCreateReviewUserNotFound() {
        // Arrange
        int tripId = 1;
        int userId = 2;
        float rating = 4.5f;
        String comment = "Great trip!";

        when(userDao.getUserById(userId)).thenReturn(Optional.empty());

        // Act
        reviewService.createReview(tripId, userId, rating, comment);

        // Assert: Expects UserNotFoundException to be thrown
    }

    @Test(expected = TripOrRequestNotFoundException.class)
    public void testCreateReviewTripNotFound() {
        // Arrange
        int tripId = 1;
        int userId = 2;
        float rating = 4.5f;
        String comment = "Great trip!";
        // Initialize with appropriate data

        when(userDao.getUserById(userId)).thenReturn(Optional.of(user1));
        when(tripDao.getTripOrRequestById(tripId)).thenReturn(Optional.empty());
        when(reviewDao.getReviewByTripAndUserId(trip1, user1)).thenReturn(Optional.of(new Review()));

        // Act
        reviewService.createReview(tripId, userId, rating, comment);

    }

    @Test
    public void testGetUserRating() {
        // Arrange
        int userId = 1;


        when(userDao.getUserById(userId)).thenReturn(Optional.of(user1));

        Double expectedRating = 4.2;
        when(reviewDao.getUserRating(user1)).thenReturn(expectedRating);

        // Act
        Double actualRating = reviewService.getUserRating(userId);

        // Assert
        Assert.assertEquals(expectedRating, actualRating);
    }

    @Test(expected = ReviewAlreadyExistsException.class)
    public void testCreateReviewReviewAlreadyExists() {
        // Arrange
        int tripId = 1;
        int userId = 2;
        float rating = 4.5f;
        String comment = "Great trip!";



        when(userDao.getUserById(userId)).thenReturn(Optional.of(user1));
        when(tripDao.getTripOrRequestById(tripId)).thenReturn(Optional.of(trip1));
        when(reviewDao.getReviewByTripAndUserId(trip1, user1)).thenReturn(Optional.of(new Review()));

        // Act
        reviewService.createReview(tripId, userId, rating, comment);

        // Assert: Expects ReviewAlreadyExistsException to be thrown
    }

    @Test
    public void testGetUserReviewCount() {
        // Arrange
        int userId = 1;

        when(userDao.getUserById(userId)).thenReturn(Optional.of(user1));

        int expectedReviewCount = 10;
        when(reviewDao.getUserReviewCount(user1)).thenReturn(expectedReviewCount);

        // Act
        int actualReviewCount = reviewService.getUserReviewCount(userId);

        // Assert
        Assert.assertEquals(expectedReviewCount, actualReviewCount);
    }

    @Test
    public void testGetUserReviews() {
        // Arrange
        int userId = 1;
        int page = 1;
        int pageSize = 10;

        when(userDao.getUserById(userId)).thenReturn(Optional.of(user1));

        List<Review> expectedReviews = Arrays.asList(
                new Review(new Trip(), user1, 4.0f, "Good experience"),
                new Review(new Trip(), user1, 3.5f, "Average service")
        );
        when(reviewDao.getUserReviews(user1, page, pageSize)).thenReturn(expectedReviews);

        // Act
        List<Review> actualReviews = reviewService.getUserReviews(userId, page, pageSize);

        // Assert
        Assert.assertEquals(expectedReviews, actualReviews);
    }


}



