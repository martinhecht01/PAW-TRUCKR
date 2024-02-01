package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.models.*;

import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
                           final LocalDateTime departureDate,
                           final LocalDateTime arrivalDate,
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
                              final LocalDateTime departureDate,
                              final LocalDateTime arrivalDate,
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
            trip.setConfirmationDate(LocalDateTime.now());
        }
        entityManager.persist(trip);

        LOGGER.info("Confirming trip with id: {} for user with id: {}", trip.getTripId(), user.getUserId());
    }


    @Override
    public Proposal createProposal(Trip trip, User user, String description, Integer price) {
        Proposal proposal = new Proposal(trip, user, description, price);
        entityManager.persist(proposal);
        LOGGER.info("Creating proposal with id {}", proposal.getProposalId());
        return proposal;
    }

    //ejemplo de 1+1 query
    @Override
    public List<Proposal> getAllProposalsForTripId(int tripId, int pag, int pagesize){
        String query = "SELECT proposal_id FROM proposals WHERE trip_id = :tripId";
        Query q = entityManager.createNativeQuery(query);
        q.setFirstResult((pag-1) * pagesize);
        q.setMaxResults(pagesize);
        q.setParameter("tripId", tripId);

        @SuppressWarnings("unchecked")
        final List<Integer> idList = (List<Integer>) q.getResultList()
                .stream().map(n -> ((Number)n).intValue()).collect(Collectors.toList());

        if(idList.isEmpty()){
            return Collections.emptyList();
        }

        final TypedQuery<Proposal> typedQuery = entityManager.createQuery("SELECT p FROM Proposal p WHERE p.proposalId IN :idList", Proposal.class);
        typedQuery.setParameter("idList", idList);

        return typedQuery.getResultList();
    }

    @Override
    public Integer getProposalsCountForTripId(int tripId){
        String query = "SELECT COUNT(proposal_id) FROM proposals WHERE trip_id = :tripId";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("tripId", tripId);
        return ((Number) q.getSingleResult()).intValue();
    }

    @Override
    public Integer getProposalsCountForUserId(int userId){
        String query = "SELECT COUNT(proposal_id) FROM proposals WHERE user_id = :userId";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("userId", userId);
        return ((Number) q.getSingleResult()).intValue();
    }


    //PAGINACION
    @Override
    public Integer getActiveTripsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate, String type) {
        String query= "SELECT COUNT(trip_id) FROM trips WHERE provider_id IS NULL AND departure_date >= now()";
        Query nativeQuery = buildQuery(query, origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, null, departureDate, arrivalDate, type);

        final int totalItems = ((Number) nativeQuery.getSingleResult()).intValue();
        return (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
    }

    //PAGINACION
    @Override
    public Integer getActiveRequestsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate, String type) {
        String query= "SELECT COUNT(trip_id) FROM trips WHERE trucker_id IS NULL AND departure_date >= now()";
        Query nativeQuery = buildQuery(query, origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, null, departureDate, arrivalDate, type);

        final int totalItems = ((Number) nativeQuery.getSingleResult()).intValue();
        return (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
    }

    @Override
    public Optional<Proposal> getProposalById(int proposalId) {
        Proposal proposal = entityManager.find(Proposal.class, proposalId);
        if(proposal == null) {
            LOGGER.info("Proposal with id {} not found", proposalId);
            return Optional.empty();
        }
        return Optional.of(proposal);
    }

    @Override
    public Optional<Trip> getTripOrRequestById(int tripId) {
        Trip trip = entityManager.find(Trip.class, tripId);
        if(trip == null) {
            LOGGER.info("Trip with id {} not found", tripId);
            return Optional.empty();
        }

        return Optional.of(trip);
    }

    private Query buildQuery(String baseQuery, String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String type){

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
            query.append(" AND destination = :destination");
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

        if (departureDate != null && !departureDate.equals("")){
            LOGGER.debug("Adding departureDate: {} to query", departureDate);
            query.append(" AND DATE(departure_date) >= CAST( :departureDate AS DATE)");
            params.put("departureDate", Timestamp.valueOf(LocalDateTime.parse(departureDate)));

        }

        if (arrivalDate != null && !arrivalDate.equals("")){
            LOGGER.debug("Adding arrivalDate: {} to query", arrivalDate);
            query.append(" AND DATE(arrival_date) <= CAST( :arrivalDate AS DATE)");
            params.put("arrivalDate", Timestamp.valueOf(LocalDateTime.parse(arrivalDate)));
        }

        if (type != null && !type.equals("")){
            LOGGER.debug("Adding type: {} to query", type);
            query.append(" AND type = :type");
            params.put("type", type);
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
    public List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String type, Integer pag) {

        String query= "SELECT trip_id FROM trips WHERE provider_id IS NULL AND departure_date >= now()";
        Query nativeQuery = buildQuery(query, origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, type);

        nativeQuery.setMaxResults(ITEMS_PER_PAGE);
        nativeQuery.setFirstResult((pag - 1) * ITEMS_PER_PAGE);

        @SuppressWarnings("unchecked")
        final List<Integer> idList = (List<Integer>) nativeQuery.getResultList()
                .stream().map(n -> ((Number)n).intValue()).collect(Collectors.toList());

        StringBuilder query2 = new StringBuilder("FROM Trip WHERE tripId IN (:ids)");

        if(sortOrder != null && !sortOrder.isEmpty())
            query2.append(" order by ").append(sortOrder);

        final TypedQuery<Trip> query3 = entityManager.createQuery(query2.toString(), Trip.class);
        query3.setParameter("ids", idList);

        return idList.isEmpty() ? Collections.emptyList() : query3.getResultList();
    }

    @Override
    public List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String type, Integer pag) {
        String query = "SELECT trip_id FROM trips WHERE trucker_id IS NULL AND departure_date >= now()";
        Query nativeQuery = buildQuery(query, origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, type);

        nativeQuery.setMaxResults(ITEMS_PER_PAGE);
        nativeQuery.setFirstResult((pag - 1) * ITEMS_PER_PAGE);

        @SuppressWarnings("unchecked")
        final List<Integer> idList = (List<Integer>) nativeQuery.getResultList()
                .stream().map(n -> ((Number) n).intValue()).collect(Collectors.toList());

        StringBuilder query2 = new StringBuilder("FROM Trip WHERE tripId IN (:ids)");

        if(sortOrder != null && !sortOrder.isEmpty())
            query2.append(" order by ").append(sortOrder);

        final TypedQuery<Trip> query3 = entityManager.createQuery(query2.toString(), Trip.class);
        query3.setParameter("ids", idList);

        return idList.isEmpty() ? Collections.emptyList() : query3.getResultList();
    }


    @Override
    public void acceptProposal(Proposal proposal) {
        LOGGER.info("Accepting proposal: {}, for trip: {}", proposal.getProposalId(), proposal.getTrip().getTripId());
        //Trip trip = getTripOrRequestById(proposal.getTripId()).orElseThrow(NoSuchElementException::new);
        Trip trip = proposal.getTrip();

        if(trip.getTrucker() != null) {
            trip.setProvider(proposal.getUser());
        } else {
            trip.setTrucker(proposal.getUser());
        }

        trip.setPrice(proposal.getPrice());
        entityManager.persist(trip);

        for (Proposal p : trip.getProposals()) {
            entityManager.remove(p);
        }

    }

//PAGINACION
@Override
public List<Trip> getAllActiveTripsOrRequestAndProposalsCount(Integer userId, Integer pag) {
    String tripIdQuery = "SELECT trips.trip_id " +
            "FROM trips LEFT JOIN proposals ON trips.trip_id = proposals.trip_id " +
            "WHERE (trips.trucker_id = :userId AND trips.provider_id IS NULL) OR " +
            "(trips.provider_id = :userId AND trips.trucker_id IS NULL) " +
            "GROUP BY trips.trip_id ";

    Query tripIdNativeQuery = entityManager.createNativeQuery(tripIdQuery);
    tripIdNativeQuery.setParameter("userId", userId);
    tripIdNativeQuery.setMaxResults(ITEMS_PER_PAGE);
    tripIdNativeQuery.setFirstResult((pag - 1) * ITEMS_PER_PAGE);

    final List<Integer> idList = (List<Integer>) tripIdNativeQuery.getResultList()
            .stream().map(n -> ((Number)n).intValue()).collect(Collectors.toList());

    final TypedQuery<Trip> tripQuery = entityManager.createQuery("FROM Trip  WHERE tripId IN (:ids)", Trip.class);
    tripQuery.setParameter("ids", idList);
    List<Trip> trips = idList.isEmpty() ? Collections.emptyList() : tripQuery.getResultList();

    // Fetch the count of proposals for each trip
    for (Trip trip : trips) {
        String proposalCountQuery = "SELECT COUNT(p) FROM Proposal p WHERE p.trip.tripId = :tripId";
        TypedQuery<Long> proposalCountTypedQuery = entityManager.createQuery(proposalCountQuery, Long.class);
        proposalCountTypedQuery.setParameter("tripId", trip.getTripId());
        Long proposalCount = proposalCountTypedQuery.getSingleResult();
        trip.setProposalCount(proposalCount.intValue());
    }

    return trips;
}
    @Override
    public List<Trip> getAllActivePublications(Integer userId, Integer pag) {
        String tripIdQuery = "SELECT trip_id " +
                "FROM trips " +
                "WHERE ((trucker_id = :userId AND provider_id IS NULL) OR " +
                "((provider_id = :userId) AND trucker_id IS NULL)) AND departure_date >= now() ";

        Query tripIdNativeQuery = entityManager.createNativeQuery(tripIdQuery);
        tripIdNativeQuery.setParameter("userId", userId);
        tripIdNativeQuery.setMaxResults(ITEMS_PER_PAGE);
        tripIdNativeQuery.setFirstResult((pag - 1) * ITEMS_PER_PAGE);

        final List<Integer> idList = (List<Integer>) tripIdNativeQuery.getResultList()
                .stream().map(n -> ((Number)n).intValue()).collect(Collectors.toList());

        final TypedQuery<Trip> tripQuery = entityManager.createQuery("FROM Trip  WHERE tripId IN (:ids)", Trip.class);
        tripQuery.setParameter("ids", idList);
        return idList.isEmpty() ? Collections.emptyList() : tripQuery.getResultList();

    }
    @Override
    public Integer getTotalPagesExpiredPublications(User user){
        String tripIdQuery = "SELECT trip_id " +
                "FROM trips " +
                "WHERE ((trucker_id = :userId AND provider_id IS NULL) OR " +
                "((provider_id = :userId) AND trucker_id IS NULL)) AND departure_date < now() ";

        Query tripIdNativeQuery = entityManager.createNativeQuery(tripIdQuery);
        tripIdNativeQuery.setParameter("userId", user.getUserId());

        final List<Integer> idList = (List<Integer>) tripIdNativeQuery.getResultList()
                .stream().map(n -> ((Number)n).intValue()).collect(Collectors.toList());

        return (int) Math.ceil((double) idList.size() / ITEMS_PER_PAGE);

    }

    @Override
    public Integer getTotalPagesActivePublications(User user){
        String tripIdQuery = "SELECT trip_id " +
                "FROM trips " +
                "WHERE ((trucker_id = :userId AND provider_id IS NULL) OR " +
                "((provider_id = :userId) AND trucker_id IS NULL)) AND departure_date >= now() ";

        Query tripIdNativeQuery = entityManager.createNativeQuery(tripIdQuery);
        tripIdNativeQuery.setParameter("userId", user.getUserId());

        final List<Integer> idList = (List<Integer>) tripIdNativeQuery.getResultList()
                .stream().map(n -> ((Number)n).intValue()).collect(Collectors.toList());

        return (int) Math.ceil((double) idList.size() / ITEMS_PER_PAGE);
    }

    @Override
    public List<Trip> getAllExpiredPublications(Integer userId, Integer pag) {
        String tripIdQuery = "SELECT trip_id " +
                "FROM trips " +
                "WHERE ((trucker_id = :userId AND provider_id IS NULL) OR " +
                "((provider_id = :userId) AND trucker_id IS NULL)) AND departure_date < now() ";

        Query tripIdNativeQuery = entityManager.createNativeQuery(tripIdQuery);
        tripIdNativeQuery.setParameter("userId", userId);
        tripIdNativeQuery.setMaxResults(ITEMS_PER_PAGE);
        tripIdNativeQuery.setFirstResult((pag - 1) * ITEMS_PER_PAGE);

        final List<Integer> idList = (List<Integer>) tripIdNativeQuery.getResultList()
                .stream().map(n -> ((Number)n).intValue()).collect(Collectors.toList());

        final TypedQuery<Trip> tripQuery = entityManager.createQuery("FROM Trip  WHERE tripId IN (:ids)", Trip.class);
        tripQuery.setParameter("ids", idList);
        return idList.isEmpty() ? Collections.emptyList() : tripQuery.getResultList();
    }

    @Override
    public List<Trip> getAllOngoingPublications(Integer userId){
        String tripIdQuery = "SELECT trip_id " +
                "FROM trips " +
                "WHERE ((trucker_id = :userId AND provider_id IS NOT NULL) OR " +
                "((provider_id = :userId) AND trucker_id IS NOT NULL)) AND departure_date < now() AND (trucker_confirmation = false OR provider_confirmation = false)";

        Query tripIdNativeQuery = entityManager.createNativeQuery(tripIdQuery);
        tripIdNativeQuery.setParameter("userId", userId);
//        tripIdNativeQuery.setMaxResults(ITEMS_PER_PAGE);
//        tripIdNativeQuery.setFirstResult((pag - 1) * ITEMS_PER_PAGE);

        final List<Integer> idList = (List<Integer>) tripIdNativeQuery.getResultList()
                .stream().map(n -> ((Number)n).intValue()).collect(Collectors.toList());

        final TypedQuery<Trip> tripQuery = entityManager.createQuery("FROM Trip  WHERE tripId IN (:ids)", Trip.class);
        tripQuery.setParameter("ids", idList);
        return idList.isEmpty() ? Collections.emptyList() : tripQuery.getResultList();

    }



    //PAGINACION
    @Override
    public List<Trip> getAllAcceptedTripsAndRequestsByUserId(Integer userid, Integer pag) {
        String query = "SELECT trip_id FROM trips WHERE (trucker_id = :userId AND provider_id IS NOT NULL) OR (provider_id = :userId AND trucker_id IS NOT NULL)";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("userId", userid);

        nativeQuery.setMaxResults(ITEMS_PER_PAGE);
        nativeQuery.setFirstResult((pag - 1) * ITEMS_PER_PAGE);

        @SuppressWarnings("unchecked")
        final List<Integer> idList = (List<Integer>) nativeQuery.getResultList()
                .stream().map(n -> ((Number)n).intValue()).collect(Collectors.toList());

        final TypedQuery<Trip> query3 = entityManager.createQuery("FROM Trip WHERE tripId IN (:ids)", Trip.class);
        query3.setParameter("ids", idList);

        return idList.isEmpty() ? Collections.emptyList() : query3.getResultList();

    }

    //PAGINACION
    @Override
    public Integer getTotalPagesActiveTripsOrRequests(User user) {
        long total = user.getTruckerTrips().stream().filter(trip -> trip.getProvider() == null).count();
        total += user.getProviderTrips().stream().filter(trip -> trip.getTrucker() == null).count();
        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);

    }


    //PAGINACION
    @Override
    public Integer getTotalPagesAcceptedTripsAndRequests(User user) {


        long total = user.getTruckerTrips().stream().filter(trip -> trip.getProvider() != null).count();
        total += user.getProviderTrips().stream().filter(trip -> trip.getTrucker() != null).count();
        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
    }


    @Override
    public Optional<Trip> getTripOrRequestByIdAndUserId(Trip trip, User user) {
        Optional<Trip> optionalTrip = getTripOrRequestById(trip.getTripId());
        if(!optionalTrip.isPresent() || (optionalTrip.get().getProvider() != null && optionalTrip.get().getTrucker() != null && user == null))
            return Optional.empty();

        return optionalTrip.filter(auxTrip -> (auxTrip.getTrucker() != null && Objects.equals(auxTrip.getTrucker().getUserId(), user.getUserId())) || (auxTrip.getProvider() != null && Objects.equals(auxTrip.getProvider().getUserId(), user.getUserId())));
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

    @Override
    public void deleteOffer(Proposal proposal) {
        Proposal offer = entityManager.find(Proposal.class, proposal.getProposalId());
        entityManager.remove(offer);
    }
    @Override
    public Integer getTotalPagesAllOngoingTrips(User user) {
        String query = "SELECT COUNT(*) as total FROM Trip t WHERE (( t.provider = :user AND t.trucker IS NOT NULL ) OR (t.trucker = :user AND t.provider IS NOT NULL)) AND t.departureDate < now() AND (t.truckerConfirmation = false OR t.providerConfirmation = false )";
        TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
        typedQuery.setParameter("user", user);

        Long total = typedQuery.getSingleResult();
        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
    }
    @Override
    public List<Trip> getAllOngoingTrips(User user, Integer page) {
        String jpql = "SELECT r FROM Trip r WHERE (( r.provider = :user AND r.trucker IS NOT NULL ) OR (r.trucker = :user AND r.provider IS NOT NULL)) AND r.departureDate < now() AND (r.truckerConfirmation = false OR r.providerConfirmation = false )";
        return entityManager.createQuery(jpql, Trip.class)
                .setParameter("user", user)
                .setFirstResult((page - 1) * ITEMS_PER_PAGE)
                .setMaxResults(ITEMS_PER_PAGE)
                .getResultList();
    }

    @Override
    public List<Trip> getAllPastTrips(User user) {
        String jpql = "SELECT r FROM Trip r WHERE (r.provider = :user OR r.trucker = :user) AND r.truckerConfirmation = true AND r.providerConfirmation = true";
        return entityManager.createQuery(jpql, Trip.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public Integer getTotalPagesAllPastTrips(User user){
        String query = "SELECT COUNT(*) as total FROM Trip t WHERE (t.provider = :user OR t.trucker = :user) AND t.truckerConfirmation = true AND t.providerConfirmation = true";
        TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
        typedQuery.setParameter("user", user);

        Long total = typedQuery.getSingleResult();
        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
    }


    @Override
    public Integer getTotalPagesAllFutureTrips(User user){
        String query = "SELECT COUNT(*) as total FROM Trip t WHERE (( t.provider = :user AND t.trucker IS NOT NULL ) OR (t.trucker = :user AND t.provider IS NOT NULL)) AND t.departureDate > now()";
        TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
        typedQuery.setParameter("user", user);

        Long total = typedQuery.getSingleResult();
        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
    }

    @Override
    public List<Trip> getAllFutureTrips(User user, Integer page) {
        String jpql = "SELECT r FROM Trip r WHERE ((r.provider = :user AND r.trucker IS NOT NULL ) OR (r.trucker = :user AND r.provider IS NOT NULL)) AND r.departureDate > now()";
        return entityManager.createQuery(jpql, Trip.class)
                .setParameter("user", user)
                .setFirstResult((page - 1) * ITEMS_PER_PAGE)
                .setMaxResults(ITEMS_PER_PAGE)
                .getResultList();
    }

    @Override
    public Integer getCompletedTripsCount(User user){
        String jpql = "SELECT r FROM Trip r WHERE ((r.provider = :user AND r.trucker IS NOT NULL ) OR (r.trucker = :user AND r.provider IS NOT NULL)) AND r.providerConfirmation = true AND r.truckerConfirmation = true";
        return entityManager.createQuery(jpql, Trip.class)
                .setParameter("user", user)
                .getResultList().size();
    }

    @Override
    public Optional<Proposal> getOffer(User user, Trip trip){
        String jpql = "SELECT p FROM Proposal p WHERE p.user = :user AND p.trip = :trip";
        List<Proposal> results = entityManager.createQuery(jpql, Proposal.class)
                .setParameter("user", user)
                .setParameter("trip", trip)
                .getResultList();

        if(results.isEmpty())
            return Optional.empty();

        return Optional.of(results.get(0));
    }

    @Override
    public Optional<Proposal> sendCounterOffer(Proposal original, User user,String description, Integer price){
        if(original == null || entityManager.find(Proposal.class, original.getProposalId()) == null)
            return Optional.empty();

        Proposal counterProposal = createCounterProposal(original.getTrip(), user, description, price, original);
        original.setCounterProposal(counterProposal);
        entityManager.persist(original);
        return Optional.of(counterProposal);
    }

    @Override
    public Proposal createCounterProposal(Trip trip, User user, String description, Integer price, Proposal parentProposal) {
        Proposal proposal = new Proposal(trip, user, description, price, parentProposal);
        entityManager.persist(proposal);
        LOGGER.info("Creating proposal with id {}", proposal.getProposalId());
        return proposal;
    }

    @Override
    public void acceptCounterOffer(Proposal counterOffer){
        String jpql = "SELECT p FROM Proposal p WHERE p.counterProposal = :counterProposal";
        Proposal originalProposal = entityManager.createQuery(jpql, Proposal.class)
                .setParameter("counterProposal", counterOffer)
                .getSingleResult();

        originalProposal.setPrice(counterOffer.getPrice());
        acceptProposal(originalProposal);
    }

    //TODO ver si funciona.
    @Override
    public void rejectCounterOffer(Proposal counterOffer){
        String jpql = "SELECT p FROM Proposal p WHERE p.counterProposal = :counterProposal";
        List<Proposal> originalProposals = entityManager.createQuery(jpql, Proposal.class)
                .setParameter("counterProposal", counterOffer)
                .getResultList();

        entityManager.remove(counterOffer);

        for(Proposal offer : originalProposals)
            entityManager.remove(offer);

    }

    @Override
    public void deleteCounterOffer(Proposal counterOffer){
        String jpql = "SELECT p FROM Proposal p WHERE p.counterProposal = :counterProposal";
        List<Proposal> originalProposals = entityManager.createQuery(jpql, Proposal.class)
                .setParameter("counterProposal", counterOffer)
                .getResultList();

        for(Proposal offer : originalProposals) {
            offer.setCounterProposal(null);
            entityManager.persist(offer);
        }

        entityManager.remove(counterOffer);
    }

    @Override
    public void deletePublication(Trip publication){
        entityManager.remove(publication);
    }

}
