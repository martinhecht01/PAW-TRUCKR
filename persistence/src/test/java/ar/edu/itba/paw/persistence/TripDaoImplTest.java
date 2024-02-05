package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.models.Proposal;
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
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class TripDaoImplTest {

    private static final String LICENSE_PLATE_NEW= "ZZZ999";
    private static final int WEIGHT = 100;
    private static final int VOLUME = 100;
    private static final String ORIGIN = "Buenos Aires";
    private static final String DESTINATION = "Cordoba";
    private static final String TYPE = "Refrigerated";

    private static final int TRIP_ID_EXISTENT = 1;
    private static final int TRIP4_ID_EXISTENT = 4;
    private static final int TRIP_ID_NOT_EXISTENT = 100;
    private static final String MESSAGE = "I would like to accept your trip";

    private static final int PROPOSAL_ID_EXISTENT = 1;
    private static final int PROPOSAL_ID_NOT_EXISTENT = 2;

    public static final String PROPOSALDESC_EXISTENT = "Quiero que lleves mi carga.";

    @Autowired
    private DataSource ds;

    @Autowired
    private TripDaoV2 tripDao;

    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager em;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Rollback
    @Test
    public void testCreateTrip(){

        //1 Precondiciones
        LocalDateTime depDate = LocalDateTime.now();
        LocalDateTime arrDate = depDate.plusDays(7);

        //2 Ejercitar
        User user = em.find(User.class, 1);
        Trip trip = tripDao.createTrip(user,  LICENSE_PLATE_NEW, WEIGHT, VOLUME, depDate,arrDate,  ORIGIN, DESTINATION, "Refrigerated", 200);

        //3 Postcondiciones
        Assert.assertEquals(LICENSE_PLATE_NEW, trip.getLicensePlate());
        Assert.assertEquals(ORIGIN, trip.getOrigin());
        Assert.assertEquals(DESTINATION, trip.getDestination());
        Assert.assertEquals(TYPE, trip.getType());
        Assert.assertEquals(depDate, trip.getDepartureDate());
        Assert.assertEquals(arrDate, trip.getArrivalDate());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "trips", "trip_id =" + trip.getTripId()));
    }


    @Rollback
    @Test
    public void testCreateRequest(){

        //1 Precondiciones
        LocalDateTime depDate = LocalDateTime.now();
        LocalDateTime arrDate = depDate.plusDays(7);

        //2 Ejercitar
        User user = em.find(User.class, 1);
        Trip trip = tripDao.createRequest(user, WEIGHT, VOLUME, depDate,arrDate, ORIGIN, DESTINATION, TYPE, 200);

        //3 Postcondiciones
        Assert.assertNull(trip.getLicensePlate());
        Assert.assertEquals(ORIGIN, trip.getOrigin());
        Assert.assertEquals(DESTINATION, trip.getDestination());
        Assert.assertEquals(TYPE, trip.getType());
        Assert.assertEquals(depDate, trip.getDepartureDate());
        Assert.assertEquals(arrDate, trip.getArrivalDate());
    }

    @Rollback
    @Test
    public void testGetExistentTrip(){
        // 2 Ejercitar
        Optional<Trip> trip = tripDao.getTripOrRequestById(TRIP_ID_EXISTENT);

        // 3 Postcondiciones
        Assert.assertTrue(trip.isPresent());
        Assert.assertEquals(TRIP_ID_EXISTENT, trip.get().getTripId());
    }

    @Rollback
    @Test
    public void testGetNonExistentTrip(){
        // 2 Ejercitar
        Optional<Trip> trip = tripDao.getTripOrRequestById(TRIP_ID_NOT_EXISTENT);

        // 3 Postcondiciones
        Assert.assertFalse(trip.isPresent());
    }

    @Rollback
    @Test
    public void testConfirmTrip(){

        User trucker = em.find(User.class, 2);
        Trip trip = em.find(Trip.class, 1);

        // 2 Ejercitar
        tripDao.confirmTrip(trip, trucker);
        em.flush();


        // 3 Postcondiciones
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "trips", "provider_confirmation = true" ));
    }

    @Rollback
    @Test
    public void testCreateProposal(){
        //1 Precondiciones
        User trucker = em.find(User.class, 1);
        Trip trip = em.find(Trip.class, 2);

        //2 Ejercitar
        tripDao.createProposal(trip, trucker, MESSAGE, 200);

        //3 Postcondiciones
        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "proposals"));
    }

    @Rollback
    @Test
    public void testAcceptProposal(){
        //precondiciones
        Proposal proposal = em.find(Proposal.class, 1);

        //2 Ejercitar
        tripDao.acceptProposal(proposal);

        //3 Postcondiciones
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "trips", "provider_id IS NOT NULL AND trucker_id IS NOT NULL AND trip_id = " + TRIP_ID_EXISTENT));

    }

}
