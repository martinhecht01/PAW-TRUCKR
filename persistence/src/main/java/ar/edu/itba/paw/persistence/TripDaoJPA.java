package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
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
    public void confirmTrip(Trip trip, User user) {
            entityManager.createQuery(
                            "UPDATE Trip t " +
                                    "SET t.truckerConfirmation = CASE " +
                                    "    WHEN t.truckerConfirmation = FALSE AND t.trucker = :user THEN TRUE " +
                                    "    WHEN t.truckerConfirmation = FALSE AND t.provider = :user THEN TRUE " +
                                    "    ELSE t.truckerConfirmation " +
                                    "END, " +
                                    "t.providerConfirmation = CASE " +
                                    "    WHEN t.providerConfirmation = FALSE AND t.provider = :user THEN TRUE " +
                                    "    ELSE t.providerConfirmation " +
                                    "END, " +
                                    "t.confirmationDate = :confirmationDate " +
                                    "WHERE t = :trip AND (t.truckerConfirmation = FALSE OR t.providerConfirmation = FALSE)")
                    .setParameter("user", user)
                    .setParameter("confirmationDate", Timestamp.valueOf(LocalDateTime.now()))
                    .setParameter("trip", trip)
                    .executeUpdate();
            LOGGER.info("Confirming trip with id: {} for user with id: {}", trip.getTripId(), user.getUserId());
    }


    @Override
    public Proposal createProposal(Trip trip, User user, String description) {
        Proposal proposal = new Proposal(trip, user, description);
        entityManager.persist(proposal);
        LOGGER.info("Creating proposal with id {} for user {}", proposal.getProposalId(), user.getUserId());
        return proposal;
    }

    //hay que hacer Trip.getProposals()
//    @Override
//    public List<Proposal> getAllProposalsForTripId(int tripId) {
//        return null;
//    }


    //PAGINACION
    @Override
    public Integer getActiveTripsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate) {
        return null;
    }

    //PAGINACION
    @Override
    public Integer getActiveRequestsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate) {
        return null;
    }

    @Override
    public Optional<Proposal> getProposalById(int proposalId) {
        String query = "SELECT p FROM Proposal p JOIN FETCH p.user WHERE p.proposalId = :proposalId";
        TypedQuery<Proposal> typedQuery = entityManager.createQuery(query, Proposal.class);
        typedQuery.setParameter("proposalId", proposalId);
        List<Proposal> proposals = typedQuery.getResultList();
        return proposals.isEmpty() ? Optional.empty() : Optional.of(proposals.get(0));
    }

    @Override
    public Optional<Trip> getTripOrRequestById(int tripId) {
        String query = "SELECT t FROM Trip t WHERE t.tripId = :tripId";
        TypedQuery<Trip> typedQuery = entityManager.createQuery(query, Trip.class);
        typedQuery.setParameter("tripId", tripId);
        List<Trip> trips = typedQuery.getResultList();
        return trips.isEmpty() ? Optional.empty() : Optional.of(trips.get(0));
    }


    //FILTROS
    @Override
    public List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {
        return null;
    }

    //FILTROS
    @Override
    public List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {
        return null;
    }


    @Override
    public void acceptProposal(Proposal proposal) {
        LOGGER.info("Accepting proposal: {}, for trip: {}", proposal.getProposalId(), proposal.getTrip().getTripId());
        //Trip trip = getTripOrRequestById(proposal.getTripId()).orElseThrow(NoSuchElementException::new);
        Trip trip = proposal.getTrip();

        String sql;
        if (trip.getTrucker().getUserId() <= 0) {
            sql = "UPDATE Trip t SET t.trucker = :user WHERE t.tripId = :tripId";
        } else {
            sql = "UPDATE Trip t SET t.provider = :user WHERE t.tripId = :tripId";
        }
        entityManager.createQuery(sql)
                .setParameter("user", proposal.getUser())
                .setParameter("tripId", proposal.getTrip().getTripId())
                .executeUpdate();

        String deleteSql = "DELETE FROM Proposal p WHERE p.proposalId <> :proposalId AND p.trip = :trip";
        entityManager.createQuery(deleteSql)
                .setParameter("proposalId", proposal.getProposalId())
                .setParameter("trip", proposal.getTrip())
                .executeUpdate();
    }

//PAGINACION
    @Override
    public List<Trip> getAllActiveTripsOrRequestAndProposalsCount(Integer userid, Integer pag) {
        return null;
    }

    //PAGINACION
    @Override
    public List<Trip> getAllAcceptedTripsAndRequestsByUserId(Integer userid, Integer pag) {
        return null;
    }

    //PAGINACION
    @Override
    public Integer getTotalPagesActiveTripsOrRequests(Integer userid) {
        return null;
    }


    //PAGINACION
    @Override
    public Integer getTotalPagesAcceptedTripsAndRequests(Integer userid) {
        return null;
    }

    @Override
    public Optional<Trip> getTripOrRequestByIdAndUserId(int id, int userid) {
        return getTripOrRequestById(id)
                .filter(trip -> trip.getTrucker().getUserId() == userid || trip.getProvider().getUserId() == userid);
    }


    @Override
    public void setImage(Trip trip, Image image) {
        LOGGER.info("Setting image id: {} for trip: {}", image.getImageid(), trip.getTripId());
        trip.setImage(image);
    }

    @Override
    public Image getImage(int tripId) {
        Trip trip = entityManager.find(Trip.class,tripId);
        return trip.getImage();
    }
}
