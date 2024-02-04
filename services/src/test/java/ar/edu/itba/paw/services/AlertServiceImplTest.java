package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.AlertDao;
import ar.edu.itba.paw.interfacesPersistence.ReviewDao;
import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.exceptions.*;
import ar.edu.itba.paw.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ar.edu.itba.paw.interfacesPersistence.SecureTokenDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
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

import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class AlertServiceImplTest {
    private static final int USER1_TRUCKERID = 1;
    private static final String USER1_EMAIL = "testing@gmail.com";
    private static final String USER1_NAME = "Testing Trucker";
    private static final String USER1_CUIT = "20-12345678-9";
    private static final String USER1_ROLE_TRUCKER = "TRUCKER";
    private static final String USER1_PASSWORD = "password";

    private static final int TRIP1_ID = 1;
    private static final String TRIP1_LICENSE_PLATE = "AHU123";
    private static final int TRIP1_WEIGHT = 100;
    private static final int TRIP1_VOLUME = 100;
    private static final LocalDateTime TRIP1_DEPARTURE_DATE = LocalDateTime.now();
    private static final LocalDateTime TRIP1_ARRIVAL_DATE = LocalDateTime.of(2024, 12, 12, 12, 12);
    private static final String TRIP1_ORIGIN = "Buenos Aires";
    private static final String TRIP1_DESTINATION = "Chivilcoy";
    private static final String TRIP1_TYPE = "REFRIGERATED";
    private static final int TRIP1_PRICE = 100;

    private static final int USER2_PROVIDERID = 2;
    private static final String USER2_EMAIL = "provider@gmail.com";
    private static final String USER2_NAME = "Testing Provider";
    private static final String USER2_CUIT = "20-87654321-0";
    private static final String USER2_ROLE_PROVIDER = "PROVIDER";
    private static final String USER2_PASSWORD = "providerpass";

    private static final int ALERT1_MAX_WEIGHT = 150;
    private static final int ALERT1_MAX_VOLUME = 120;
    private static final LocalDateTime ALERT1_FROM = LocalDateTime.of(2024, 6, 1, 12, 0);
    private static final LocalDateTime ALERT1_TO = LocalDateTime.of(2024, 6, 15, 12, 0);

    @Mock
    private TripDaoV2 tripDao;

    @Mock
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

    @InjectMocks
    private AlertServiceImpl alertService;
    User user1;
    User user2;
    Trip trip1;
    Proposal offer1;
    Alert alert1;
    User useralerted;
    @Before
    public void setup() {
        user1 = new User(USER1_TRUCKERID, USER1_EMAIL, USER1_NAME, USER1_CUIT, USER1_ROLE_TRUCKER, USER1_PASSWORD, true, null, Locale.ENGLISH);
        useralerted = new User(USER1_TRUCKERID, USER1_EMAIL, USER1_NAME, USER1_CUIT, USER1_ROLE_TRUCKER, USER1_PASSWORD, true, null, Locale.ENGLISH);
        useralerted.setAlert(new Alert());
        user2 = new User(USER2_PROVIDERID, USER2_EMAIL, USER2_NAME, USER2_CUIT, USER2_ROLE_PROVIDER, USER2_PASSWORD, true, null, Locale.ENGLISH);
        trip1 = new Trip(TRIP1_ID, user1, null, TRIP1_LICENSE_PLATE, TRIP1_WEIGHT, TRIP1_VOLUME, TRIP1_DEPARTURE_DATE, TRIP1_ARRIVAL_DATE, TRIP1_ORIGIN, TRIP1_DESTINATION, TRIP1_TYPE, TRIP1_PRICE, false, false, null, null, 0, false, false);
        offer1 = new Proposal(trip1, user1, "Proposal description.", TRIP1_PRICE);
        alert1 = new Alert(user1, TRIP1_ORIGIN, ALERT1_MAX_WEIGHT, ALERT1_MAX_VOLUME, ALERT1_FROM, ALERT1_TO, TRIP1_TYPE);
    }

    @Test(expected = AlertAlreadyExistsException.class)
    public void testCreateAlert() {
        // Arrange
        // Mocking an existing alert for the user already done in user alerted

        // Act
        Optional<Alert> createdAlert = alertService.createAlert(useralerted, TRIP1_ORIGIN, ALERT1_MAX_WEIGHT, ALERT1_MAX_VOLUME, ALERT1_FROM, ALERT1_TO, TRIP1_TYPE);

       // Asert exception expected
    }

    @Test
    public void testDeleteAlert() {
        // Arrange
        int alertId = 1;
        Alert alert = new Alert(user1, TRIP1_ORIGIN, ALERT1_MAX_WEIGHT, ALERT1_MAX_VOLUME, ALERT1_FROM, ALERT1_TO, TRIP1_TYPE);
        when(alertDao.getAlertById(alertId)).thenReturn(Optional.of(alert));

        // Act
        alertService.deleteAlert(alertId);

        // Assert
        verify(alertDao, times(1)).deleteAlert(alert);
    }

    @Test
    public void testUpdateAlert() {
        // Arrange
        when(alertDao.updateAlert(any(User.class), eq(TRIP1_ORIGIN), eq(ALERT1_MAX_WEIGHT), eq(ALERT1_MAX_VOLUME), eq(ALERT1_FROM), eq(ALERT1_TO)))
                .thenReturn(Optional.of(new Alert()));

        // Act
        Optional<Alert> updatedAlert = alertService.updateAlert(user1, TRIP1_ORIGIN, ALERT1_MAX_WEIGHT, ALERT1_MAX_VOLUME, ALERT1_FROM, ALERT1_TO);

        // Assert
        Assert.assertTrue(updatedAlert.isPresent());
    }

    @Test
    public void testGetAlertsThatMatch() {
        // Arrange
        when(tripService.getTripOrRequestById(anyInt())).thenReturn(Optional.of(trip1));

        // Mock the list of alerts
        List<Alert> expectedAlerts = Arrays.asList(
                new Alert(),
                new Alert()
        );
        when(alertDao.getAlertsThatMatch(any(Trip.class))).thenReturn(expectedAlerts);

        // Act
        List<Alert> actualAlerts = alertService.getAlertsThatMatch(TRIP1_ID);

        // Assert
        Assert.assertEquals(expectedAlerts, actualAlerts);
    }

    @Test
    public void testGetAlertById() {
        // Arrange
        int alertId = 1;
        Alert expectedAlert = new Alert(user1, TRIP1_ORIGIN, ALERT1_MAX_WEIGHT, ALERT1_MAX_VOLUME, ALERT1_FROM, ALERT1_TO, TRIP1_TYPE);
        when(alertDao.getAlertById(alertId)).thenReturn(Optional.of(expectedAlert));

        // Act
        Optional<Alert> actualAlert = alertService.getAlertById(alertId);

        // Assert
        Assert.assertTrue(actualAlert.isPresent());
        Assert.assertEquals(expectedAlert, actualAlert.get());
    }

    @Test(expected = AlertNotFoundException.class)
    public void testGetAlertByIdNotExistent() {
        // Arrange
        when(alertDao.getAlertById(USER1_TRUCKERID)).thenReturn(Optional.empty());

        // Act
        Optional<Alert> alert = alertService.getAlertById(USER1_TRUCKERID);

        // Assert exception expected
    }


}
