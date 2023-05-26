package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.models.*;
import org.hibernate.Criteria;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional
public class TripDaoJPA implements TripDaoV2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripDaoJPA.class);
    private static final int ITEMS_PER_PAGE = 12;

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

        if(!trip.getTruckerConfirmation()){
            if(Objects.equals(trip.getTrucker().getUserId(), user.getUserId())){
                trip.setTruckerConfirmation(true);
            }
            if(Objects.equals(trip.getProvider().getUserId(), user.getUserId())){
                trip.setTruckerConfirmation(true);
            }
        }
        if(!trip.getProviderConfirmation()){
            if(Objects.equals(trip.getProvider().getUserId(), user.getUserId())){
                trip.setProviderConfirmation(true);
            }
        }
        if(!trip.getTruckerConfirmation() || !trip.getProviderConfirmation()){
            trip.setConfirmationDate(Timestamp.valueOf(LocalDateTime.now()));
        }
        entityManager.persist(trip);
//
//            entityManager.createQuery(
//                            "UPDATE Trip t " +
//                                    "SET t.truckerConfirmation = CASE " +
//                                    "    WHEN t.truckerConfirmation = FALSE AND t.trucker = :user THEN TRUE " +
//                                    "    WHEN t.truckerConfirmation = FALSE AND t.provider = :user THEN TRUE " +
//                                    "    ELSE t.truckerConfirmation " +
//                                    "END, " +
//                                    "t.providerConfirmation = CASE " +
//                                    "    WHEN t.providerConfirmation = FALSE AND t.provider = :user THEN TRUE " +
//                                    "    ELSE t.providerConfirmation " +
//                                    "END, " +
//                                    "t.confirmationDate = :confirmationDate " +
//                                    "WHERE t = :trip AND (t.truckerConfirmation = FALSE OR t.providerConfirmation = FALSE)")
//                    .setParameter("user", user)
//                    .setParameter("confirmationDate", Timestamp.valueOf(LocalDateTime.now()))
//                    .setParameter("trip", trip)
//                    .executeUpdate();
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
//        String query = "SELECT p FROM Proposal p JOIN FETCH p.user WHERE p.proposalId = :proposalId";
//        TypedQuery<Proposal> typedQuery = entityManager.createQuery(query, Proposal.class);
//        typedQuery.setParameter("proposalId", proposalId);
//        List<Proposal> proposals = typedQuery.getResultList();
//        return proposals.isEmpty() ? Optional.empty() : Optional.of(proposals.get(0));
        Proposal proposal = entityManager.find(Proposal.class, proposalId);
        if(proposal == null) {
            LOGGER.info("Proposal with id {} not found", proposalId);
            return Optional.empty();
        }
        return Optional.of(proposal);
    }

    @Override
    public Optional<Trip> getTripOrRequestById(int tripId) {
//        String query = "SELECT t FROM Trip t WHERE t.tripId = :tripId";
//        TypedQuery<Trip> typedQuery = entityManager.createQuery(query, Trip.class);
//        typedQuery.setParameter("tripId", tripId);
//        List<Trip> trips = typedQuery.getResultList();
//        return trips.isEmpty() ? Optional.empty() : Optional.of(trips.get(0));
        Trip trip = entityManager.find(Trip.class, tripId);
        if(trip == null) {
            LOGGER.info("Trip with id {} not found", tripId);
            return Optional.empty();
        }
        return Optional.of(trip);
    }

    private Pair<String, List<Object>> buildQuery(Boolean pagination, String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag){
        LOGGER.debug("Building query for trips");
        String query = "";

        if(pag < 1)
            pag = 1;

        List<Object> params = new ArrayList<>();
        Integer offset = (pag-1)*ITEMS_PER_PAGE;

        if (origin != null && !origin.equals("")){
            LOGGER.debug("Adding origin: {} to query", origin);
            query = query + " AND origin = ?";
            params.add(origin);
        }

        if (destination != null && !destination.equals("")){
            LOGGER.debug("Adding destination: {} to query", destination);
            query = query + " AND destination = ?";
            params.add(destination);
        }

        if (minAvailableVolume != null){
            LOGGER.debug("Adding minAvailableVolume: {} to query", minAvailableVolume);
            query = query + " AND volume >= ?";
            params.add(minAvailableVolume);
        }

        if (minAvailableWeight != null){
            LOGGER.debug("Adding minAvailableWeight: {} to query", minAvailableWeight);
            query = query + " AND weight >= ?";
            params.add(minAvailableWeight);
        }

        if (minPrice != null){
            LOGGER.debug("Adding minPrice: {} to query", minPrice);
            query = query + " AND price >= ?";
            params.add(minPrice);
        }

        if (maxPrice != null){
            LOGGER.debug("Adding maxPrice: {} to query", maxPrice);
            query = query + " AND price <= ?";
            params.add(maxPrice);
        }

        if (departureDate != null && !departureDate.equals("")){
            LOGGER.debug("Adding departureDate: {} to query", departureDate);
            query = query + " AND DATE(departure_date) = CAST(? AS DATE)";
            params.add("'" + departureDate + "'");
        }

        if (arrivalDate != null && !arrivalDate.equals("")){
            LOGGER.debug("Adding arrivalDate: {} to query", arrivalDate);
            query = query + " AND DATE(arrival_date) = CAST(? AS DATE)";
            params.add("'" + arrivalDate + "'");
        }

        if(sortOrder != null && !sortOrder.isEmpty()) {
            LOGGER.debug("Adding sort order: {} to query", sortOrder);
            //sort order asc and desc
            if (sortOrder.equals("departureDate ASC")) {
                query = query + " ORDER BY departure_date ASC";
            } else if (sortOrder.equals("departureDate DESC")) {
                query = query + " ORDER BY departure_date DESC";
            } else if(sortOrder.equals("arrivalDate ASC")) {
                query = query + " ORDER BY arrival_date ASC";
            } else if(sortOrder.equals("arrivalDate DESC")) {
                query = query + " ORDER BY arrival_date DESC";
            } else if(sortOrder.equals("price ASC")) {
                query = query + " ORDER BY price ASC";
            } else if(sortOrder.equals("price DESC")) {
                query = query + " ORDER BY price DESC";
            }
        }
        if(pagination){
            query = query + " LIMIT ? OFFSET ?";
            params.add(ITEMS_PER_PAGE);
            params.add(offset);
        }
        return new Pair<>(query, params);

    }

    @Override
    public List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {


        String query= "SELECT trip_id FROM trips WHERE provider_id IS NULL AND departure_date >= now()";
        Pair<String, List<Object>> builder2 = buildQuery(false, origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, pag);
        Query nativeQuery = entityManager.createNativeQuery(query);
        List<Object> params2 = builder2.getValue();
        for (int i = 0; i < params2.size(); i++) {
            nativeQuery.setParameter(i + 1, params2.get(i));
        }
        nativeQuery.setMaxResults(ITEMS_PER_PAGE);
        nativeQuery.setFirstResult((pag - 1) * ITEMS_PER_PAGE); //el 12 es el tamano de la pagina

        final List<Long> idList = (List<Long>) nativeQuery.getResultList()
                .stream().map(n -> (Long)((Number)n).longValue()).collect(Collectors.toList());

        final TypedQuery<Trip> query3 = entityManager.createQuery("SELECT  t FROM Trip t WHERE t.tripId IN :ids", Trip.class);
        query3.setParameter("ids", idList);

        return query3.getResultList();
    }

    @Override
    public List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {
        Pair<String, List<Object>> builder = buildQuery(true, origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, pag);
        //t.trucker is null porque no me deja hacer t.trucker.userId
        String query = "SELECT t FROM Trip t WHERE t.trucker IS NULL AND t.departureDate >= CURRENT_TIMESTAMP";
        query += builder.getKey();
        List<Object> params = builder.getValue();

        LOGGER.debug("Getting all active requests");
        TypedQuery<Trip> typedQuery = entityManager.createQuery(query, Trip.class);

        // Set query parameters
        for (int i = 0; i < params.size(); i++) {
            typedQuery.setParameter(i + 1, params.get(i));
        }

        return typedQuery.getResultList();
    }


    @Override
    public void acceptProposal(Proposal proposal) {
        LOGGER.info("Accepting proposal: {}, for trip: {}", proposal.getProposalId(), proposal.getTrip().getTripId());
        //Trip trip = getTripOrRequestById(proposal.getTripId()).orElseThrow(NoSuchElementException::new);
        Trip trip = proposal.getTrip();

        if(trip.getTrucker().getUserId() <= 0) {
            trip.setTrucker(proposal.getUser());
        } else {
            trip.setProvider(proposal.getUser());
        }
        entityManager.persist(trip);

        entityManager.remove(proposal);


//        String sql;
//        if (trip.getTrucker().getUserId() <= 0) {
//            sql = "UPDATE Trip t SET t.trucker = :user WHERE t.tripId = :tripId";
//        } else {
//            sql = "UPDATE Trip t SET t.provider = :user WHERE t.tripId = :tripId";
//        }
//        entityManager.createQuery(sql)
//                .setParameter("user", proposal.getUser())
//                .setParameter("tripId", proposal.getTrip().getTripId())
//                .executeUpdate();
//
//
//        String deleteSql = "DELETE FROM Proposal p WHERE p.proposalId <> :proposalId AND p.trip = :trip";
//        entityManager.createQuery(deleteSql)
//                .setParameter("proposalId", proposal.getProposalId())
//                .setParameter("trip", proposal.getTrip())
//                .executeUpdate();
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
        //revisar si tengo que persistir aca
    }

    @Override
    public Image getImage(int tripId) {
        Trip trip = entityManager.find(Trip.class,tripId);
        return trip.getImage();
    }
}
