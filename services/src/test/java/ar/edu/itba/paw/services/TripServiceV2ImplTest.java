package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.AlertDao;
import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import net.bytebuddy.asm.Advice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceV2ImplTest {

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
        trip1 = new Trip(TRIPID_NOT_EXISTENT,user1, null, LICENSEPLATE_NOT_EXISTENT, WEIGHT_NOT_EXISTENT, VOLUME_NOT_EXISTENT, DEPARTUREDATE_NOT_EXISTENT, ARRIVALDATE_NOT_EXISTENT, ORIGIN_NOT_EXISTENT, DESTINATION_NOT_EXISTENT, TYPE_NOT_EXISTENT, PRICE_NOT_EXISTENT, false, false,null,null,0, false, false);
        offer1 = new Proposal(trip1, user1, PROPOSAL_DESCRIPTION, PRICE_NOT_EXISTENT);
    }

    @Test
    public void testCreateTrip() {
        // 1 Precondiciones

        when(tripDao.createTrip(eq(user1), eq(LICENSEPLATE_NOT_EXISTENT), eq(WEIGHT_NOT_EXISTENT), eq(VOLUME_NOT_EXISTENT), eq(DEPARTUREDATE_NOT_EXISTENT), eq(ARRIVALDATE_NOT_EXISTENT), eq(ORIGIN_NOT_EXISTENT), eq(DESTINATION_NOT_EXISTENT), eq(TYPE_NOT_EXISTENT), eq(PRICE_NOT_EXISTENT))).
                thenReturn( new Trip(user1,null, LICENSEPLATE_NOT_EXISTENT, WEIGHT_NOT_EXISTENT, VOLUME_NOT_EXISTENT, DEPARTUREDATE_NOT_EXISTENT, ARRIVALDATE_NOT_EXISTENT, ORIGIN_NOT_EXISTENT, DESTINATION_NOT_EXISTENT, TYPE_NOT_EXISTENT, PRICE_NOT_EXISTENT ));

        when(userDao.getUserById(eq(TRUCKERID_NOT_EXISTENT)))
                .thenReturn(Optional.of(user1));
//
//        //2 Ejercitar
        Trip trip = tripService.createTrip(user1,LICENSEPLATE_NOT_EXISTENT,WEIGHT_NOT_EXISTENT,VOLUME_NOT_EXISTENT,DEPARTUREDATE_NOT_EXISTENT,ARRIVALDATE_NOT_EXISTENT,ORIGIN_NOT_EXISTENT,DESTINATION_NOT_EXISTENT,TYPE_NOT_EXISTENT,PRICE_NOT_EXISTENT);
//
//        //3 Postcondiciones
        Assert.assertNotNull(trip);
        Assert.assertNotNull(trip.getTrucker());
        Assert.assertEquals((Integer)TRUCKERID_NOT_EXISTENT, trip.getTrucker().getUserId());
        Assert.assertEquals(LICENSEPLATE_NOT_EXISTENT, trip.getLicensePlate());
        Assert.assertEquals(WEIGHT_NOT_EXISTENT, trip.getWeight().intValue());
        Assert.assertEquals(VOLUME_NOT_EXISTENT, trip.getVolume().intValue());
        Assert.assertEquals(DEPARTUREDATE_NOT_EXISTENT, trip.getDepartureDate());
        Assert.assertEquals(ARRIVALDATE_NOT_EXISTENT, trip.getArrivalDate());
        Assert.assertEquals(ORIGIN_NOT_EXISTENT, trip.getOrigin());
        Assert.assertEquals(DESTINATION_NOT_EXISTENT, trip.getDestination());
        Assert.assertEquals(TYPE_NOT_EXISTENT, trip.getType());
        Assert.assertEquals(PRICE_NOT_EXISTENT, trip.getPrice().intValue());

    }

    @Test
    public void testCreateRequest() {
//         1 Precondiciones

        when(tripDao.createRequest(eq(user2), eq(WEIGHT_NOT_EXISTENT), eq(VOLUME_NOT_EXISTENT), eq(DEPARTUREDATE_NOT_EXISTENT), eq(ARRIVALDATE_NOT_EXISTENT), eq(ORIGIN_NOT_EXISTENT), eq(DESTINATION_NOT_EXISTENT), eq(TYPE_NOT_EXISTENT), eq(PRICE_NOT_EXISTENT))).
                thenReturn( new Trip(null,user2, LICENSEPLATE_NOT_EXISTENT, WEIGHT_NOT_EXISTENT, VOLUME_NOT_EXISTENT, DEPARTUREDATE_NOT_EXISTENT, ARRIVALDATE_NOT_EXISTENT, ORIGIN_NOT_EXISTENT, DESTINATION_NOT_EXISTENT, TYPE_NOT_EXISTENT, PRICE_NOT_EXISTENT ));

        when(userDao.getUserById(eq(PROVIDERID_NOT_EXISTENT)))
                .thenReturn(Optional.of(user2));

        when(alertDao.getAlertsThatMatch(any()))
                .thenReturn(new ArrayList<>());

//        2 Ejercitar
        Trip trip = tripService.createRequest(user2,WEIGHT_NOT_EXISTENT,VOLUME_NOT_EXISTENT,DEPARTUREDATE_NOT_EXISTENT,ARRIVALDATE_NOT_EXISTENT,ORIGIN_NOT_EXISTENT,DESTINATION_NOT_EXISTENT,TYPE_NOT_EXISTENT,PRICE_NOT_EXISTENT, Locale.ENGLISH);

//        3 Postcondiciones
        Assert.assertNotNull(trip);
        Assert.assertNotNull(trip.getProvider().getUserId());
        Assert.assertEquals((Integer)PROVIDERID_NOT_EXISTENT, trip.getProvider().getUserId());
        Assert.assertEquals(LICENSEPLATE_NOT_EXISTENT, trip.getLicensePlate());
        Assert.assertEquals(WEIGHT_NOT_EXISTENT, (long)trip.getWeight());
        Assert.assertEquals(VOLUME_NOT_EXISTENT, (long)trip.getVolume());
        Assert.assertEquals(DEPARTUREDATE_NOT_EXISTENT, trip.getDepartureDate());
        Assert.assertEquals(ARRIVALDATE_NOT_EXISTENT, trip.getArrivalDate());
        Assert.assertEquals(ORIGIN_NOT_EXISTENT, trip.getOrigin());
        Assert.assertEquals(DESTINATION_NOT_EXISTENT, trip.getDestination());
        Assert.assertEquals(TYPE_NOT_EXISTENT, trip.getType());
        Assert.assertEquals(PRICE_NOT_EXISTENT, (long)trip.getPrice());

    }


    @Test
    public void testCreateProposal(){
//         1 Precondiciones

        when(tripDao.createProposal(eq(trip1), eq(user1), eq(PROPOSAL_DESCRIPTION), anyInt())).
                thenReturn( new Proposal(PROPOSALID_NOT_EXISTENT, trip1, user1, PROPOSAL_DESCRIPTION, TRUCKERNAME_NOT_EXISTENT, PRICE_NOT_EXISTENT, null));

        when(tripDao.getTripOrRequestById(TRIPID_NOT_EXISTENT))
                .thenReturn(Optional.of(trip1));

        when(userDao.getUserById(anyInt()))
                .thenReturn(Optional.of(user1));


        //2 Ejercitar
        Proposal proposal = tripService.createProposal(TRIPID_NOT_EXISTENT, user1, PROPOSAL_DESCRIPTION, PRICE_NOT_EXISTENT, 0,Locale.ENGLISH);

        //3 Postcondiciones
        Assert.assertNotNull(proposal);
        Assert.assertEquals(TRIPID_NOT_EXISTENT, proposal.getTrip().getTripId());
        Assert.assertEquals(TRUCKERID_NOT_EXISTENT, (long)proposal.getUser().getUserId());
        Assert.assertEquals(PROPOSAL_DESCRIPTION, proposal.getDescription());
        Assert.assertEquals(TRUCKERNAME_NOT_EXISTENT, proposal.getUserName());
    }

}
