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

    //ejemplo de 1+1 query
    @Override
    public List<Proposal> getAllProposalsForTripId(int tripId, int pag){
        String query = "SELECT proposal_id FROM proposals WHERE trip_id = :tripId";
        Query q = entityManager.createNativeQuery(query);
        q.setFirstResult(pag);
        q.setMaxResults(ITEMS_PER_PAGE);
        q.setParameter("tripId", tripId);
        final List<Long> idList = (List<Long>) q.getResultList()
                .stream().map(n -> (Long)((Number)n).longValue()).collect(Collectors.toList());
        final TypedQuery<Proposal> typedQuery = entityManager.createQuery("SELECT p FROM Proposal p WHERE p.proposalId IN :idList", Proposal.class);
        typedQuery.setParameter("idList", idList);
        return typedQuery.getResultList();

    }


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

    private Query buildQuery(String baseQuery, String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag){

        LOGGER.debug("Building query for trips");
        StringBuilder query = new StringBuilder();
        final Map<String, Object> params = new HashMap<>();
        query.append(baseQuery);


        if (origin != null && !origin.equals("")){
            LOGGER.debug("Adding origin: {} to query", origin);
            query.append(" AND origin = :origin");
            params.put("origin", origin);
        }

        if (destination != null && !destination.equals("")){
            LOGGER.debug("Adding destination: {} to query", destination);
            query.append(" AND destination = destination");
            params.put("destination",destination);
        }

        if (minAvailableVolume != null){
            LOGGER.debug("Adding minAvailableVolume: {} to query", minAvailableVolume);
            query.append(" AND volume >= :minAvailableVolume");
            params.put("minAvailableVolume", minAvailableVolume);
        }

        if (minAvailableWeight != null){
            LOGGER.debug("Adding minAvailableWeight: {} to query", minAvailableWeight);
            query.append(" AND weight >= :minAvailableWeight");
            params.put("minAvailableWeight", minAvailableWeight);
        }

        if (minPrice != null){
            LOGGER.debug("Adding minPrice: {} to query", minPrice);
            query.append(" AND price >= :minPrice");
            params.put("minPrice",minPrice);
        }

        if (maxPrice != null){
            LOGGER.debug("Adding maxPrice: {} to query", maxPrice);
            query.append(" AND price <= :maxPrice");
            params.put("maxPrice",maxPrice);
        }

        //ESTAS DOS SON RARAS, REVISAR
        if (departureDate != null && !departureDate.equals("")){
            LOGGER.debug("Adding departureDate: {} to query", departureDate);
            query.append(" AND DATE(departure_date) = CAST(:departureDate AS DATE)");
            params.put("departureDate", "'" + departureDate + "'");

        }

        if (arrivalDate != null && !arrivalDate.equals("")){
            LOGGER.debug("Adding arrivalDate: {} to query", arrivalDate);
            query.append(" AND DATE(arrival_date) = CAST(:arrivalDate AS DATE)");
            params.put(arrivalDate, "'" + arrivalDate + "'");
        }

        if(sortOrder != null && !sortOrder.isEmpty()) {
            LOGGER.debug("Adding sort order: {} to query", sortOrder);
            //sort order asc and desc
            if (sortOrder.equals("departureDate ASC")) {
                query.append(" ORDER BY departure_date ASC");
            } else if (sortOrder.equals("departureDate DESC")) {
                query.append(" ORDER BY departure_date DESC");
            } else if(sortOrder.equals("arrivalDate ASC")) {
                query.append(" ORDER BY arrival_date ASC");
            } else if(sortOrder.equals("arrivalDate DESC")) {
                query.append(" ORDER BY arrival_date DESC");
            } else if(sortOrder.equals("price ASC")) {
                query.append(" ORDER BY price ASC");
            } else if(sortOrder.equals("price DESC")) {
                query.append(" ORDER BY price DESC");
            }
        }

        final Query nativeQuery = entityManager.createNativeQuery(query.toString());
        params.forEach(nativeQuery::setParameter);
        return nativeQuery;

    }

    @Override
    public List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {

        String query= "SELECT trip_id FROM trips WHERE provider_id IS NULL AND departure_date >= now()";
        Query nativeQuery = buildQuery(query, origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, pag);
        //query += builder2.getKey();
//        List<Object> params2 = builder2.getValue();
//        Query nativeQuery = entityManager.createNativeQuery(query);
//        for (int i = 0; i < params2.size(); i++) {
//            nativeQuery.setParameter(i + 1, params2.get(i));
//        }


        nativeQuery.setMaxResults(ITEMS_PER_PAGE);
        nativeQuery.setFirstResult((pag - 1) * ITEMS_PER_PAGE);

        final List<Long> idList = (List<Long>) nativeQuery.getResultList()
                .stream().map(n -> (Long)((Number)n).longValue()).collect(Collectors.toList());

        final TypedQuery<Trip> query3 = entityManager.createQuery("SELECT  t FROM Trip t WHERE t.tripId IN :ids", Trip.class);
        query3.setParameter("ids", idList);

        return query3.getResultList();
    }

    @Override
    public List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {
        String query = "SELECT trip_id FROM trips WHERE provider_id IS NULL AND departure_date >= now()";
        Query nativeQuery = buildQuery(query, origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, pag);

        nativeQuery.setMaxResults(ITEMS_PER_PAGE);
        nativeQuery.setFirstResult((pag - 1) * ITEMS_PER_PAGE);

        final List<Long> idList = (List<Long>) nativeQuery.getResultList()
                .stream().map(n -> (Long)((Number)n).longValue()).collect(Collectors.toList());

        final TypedQuery<Trip> query3 = entityManager.createQuery("SELECT t FROM Trip t WHERE t.tripId IN :ids", Trip.class);
        query3.setParameter("ids", idList);

        return query3.getResultList();
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
//        String query = "SELECT trips.*, COUNT(proposals.proposal_id) AS proposalcount FROM trips LEFT JOIN proposals ON trips.trip_id = proposals.trip_id WHERE (trips.trucker_id = ? AND provider_id IS NULL) OR (trips.provider_id = ? AND trucker_id IS NULL) GROUP BY trips.trip_id LIMIT ? OFFSET ?";
//        return jdbcTemplate.query(query, ACTIVE_TRIP_COUNT_MAPPER, userid, userid, ITEMS_PER_PAGE, (pag - 1) * ITEMS_PER_PAGE);
    return null;
    }


    //PAGINACION
    @Override
    public List<Trip> getAllAcceptedTripsAndRequestsByUserId(Integer userid, Integer pag) {
//        String query = "SELECT * FROM trips WHERE (trucker_id = ? AND provider_id IS NOT NULL) OR (provider_id = ? AND trucker_id IS NOT NULL) LIMIT ? OFFSET ?";
//        return jdbcTemplate.query(query, TRIP_ROW_MAPPER, userid, userid, ITEMS_PER_PAGE, (pag - 1) * ITEMS_PER_PAGE);
        return null;
    }

    //PAGINACION
    @Override
    public Integer getTotalPagesActiveTripsOrRequests(User user) {
//        String query = "SELECT count(*) as total FROM trips WHERE (trucker_id = ? AND provider_id IS NULL) OR (provider_id = ? AND trucker_id IS NULL)";
//        Integer total = jdbcTemplate.query(query, (rs, row) -> rs.getInt("total"), userid, userid).get(0);
//        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
        long total = user.getTruckerTrips().stream().filter(trip -> trip.getProvider() == null).count();
        total += user.getProviderTrips().stream().filter(trip -> trip.getTrucker() == null).count();
        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);

    }


    //PAGINACION
    @Override
    public Integer getTotalPagesAcceptedTripsAndRequests(User user) {
//        String query = "SELECT COUNT(*) as total FROM Trip t WHERE (t.trucker.userId = :userid AND t.provider.useriD IS NOT NULL) OR (t.provider.userId = :userid AND t.trucker.userId IS NOT NULL)";
//        TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
//        typedQuery.setParameter("userid", userid);
//
//        Long total = typedQuery.getSingleResult();
//        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
        //User user = userDao.getUserById(userid).orElseThrow(NoSuchElementException::new);

        long total = user.getTruckerTrips().stream().filter(trip -> trip.getProvider() != null).count();
        total += user.getProviderTrips().stream().filter(trip -> trip.getTrucker() != null).count();
        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
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
        entityManager.persist(trip);
        //revisar si tengo que persistir o no aca
    }

    @Override
    public Image getImage(int tripId) {
        Trip trip = entityManager.find(Trip.class,tripId);
        return trip.getImage();
    }
}
