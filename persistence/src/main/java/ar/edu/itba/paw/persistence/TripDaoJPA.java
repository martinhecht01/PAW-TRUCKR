package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TripDaoJPA implements TripDaoV2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripDaoJPA.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Trip createTrip(final User trucker,
                           final String licensePlate,
                           final int weight,
                           final int volume,
                           final Timestamp departureDate,
                           final Timestamp arrivalDate,
                           final String origin,
                           final String destination,
                           final String type,
                           final int price) {

        Trip trip = new Trip(trucker, null, licensePlate, weight, volume, departureDate, arrivalDate, origin, destination, type, price);
        entityManager.persist(trip);
    LOGGER.info("Creating trip with id {} for user {}", trip.getTripId(), trucker.getUserId());
        return trip;

    }


    @Override
    public Trip createRequest(final User provider,
                              final int weight,
                              final int volume,
                              final Timestamp departureDate,
                              final Timestamp arrivalDate,
                              final String origin,
                              final String destination,
                              final String type,
                              final int price) {
        Trip trip = new Trip(null, provider, null, weight, volume, departureDate, arrivalDate, origin, destination, type, price);
        entityManager.persist(trip);
        LOGGER.info("Creating request with id {} for user {}", trip.getTripId(), provider.getUserId());
        return trip;
    }

    @Override
    public void confirmTrip(int tripId, int userId) {


    }

    @Override
    public Proposal createProposal(Trip trip, User user, String description) {
        Proposal proposal = new Proposal(trip, user, description);
        entityManager.persist(proposal);
        LOGGER.info("Creating proposal with id {} for user {}", proposal.getProposalId(), user.getUserId());
        return proposal;
    }

    @Override
    public List<Proposal> getAllProposalsForTripId(int tripId) {
        return null;
    }

    @Override
    public Integer getActiveTripsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate) {
        return null;
    }

    @Override
    public Integer getActiveRequestsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate) {
        return null;
    }

    @Override
    public Optional<Proposal> getProposalById(int proposalId) {
        return Optional.empty();
    }

    @Override
    public List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {
        return null;
    }

    @Override
    public List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {
        return null;
    }

    @Override
    public Optional<Trip> getTripOrRequestById(int tripid) {
        return Optional.empty();
    }

    @Override
    public void acceptProposal(Proposal proposal) {

    }

    @Override
    public List<Trip> getAllActiveTripsOrRequestAndProposalsCount(Integer userid, Integer pag) {
        return null;
    }

    @Override
    public List<Trip> getAllAcceptedTripsAndRequestsByUserId(Integer userid, Integer pag) {
        return null;
    }

    @Override
    public Integer getTotalPagesActiveTripsOrRequests(Integer userid) {
        return null;
    }

    @Override
    public Integer getTotalPagesAcceptedTripsAndRequests(Integer userid) {
        return null;
    }

    @Override
    public Optional<Trip> getTripOrRequestByIdAndUserId(int id, int userid) {
        return Optional.empty();
    }

    @Override
    public void setImageId(int tripId, int imageId) {

    }

    @Override
    public int getImageId(int tripId) {
        return 0;
    }
}
