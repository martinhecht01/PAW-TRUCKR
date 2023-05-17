package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.models.Pair;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class TripDaoV2Impl implements TripDaoV2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripDaoV2Impl.class);

    private final static RowMapper<Trip> TRIP_ROW_MAPPER = (rs, rowNum) -> {
        LocalDateTime departure = rs.getTimestamp("departure_date").toLocalDateTime();
        LocalDateTime arrival = rs.getTimestamp("arrival_date").toLocalDateTime();
        LocalDateTime confirmation = rs.getTimestamp("confirmation_date") == null ? null : rs.getTimestamp("confirmation_date").toLocalDateTime();

        return new Trip(
                rs.getInt("trip_id"),
                rs.getInt("trucker_id"),
                rs.getInt("provider_id"),
                rs.getString("licenseplate"),
                rs.getInt("weight"),
                rs.getInt("volume"),
                departure,
                arrival,
                rs.getString("origin"),
                rs.getString("destination"),
                rs.getString("type"),
                rs.getInt("price"),
                rs.getBoolean("trucker_confirmation"),
                rs.getBoolean("provider_confirmation"),
                confirmation,
                0,
                rs.getInt("imageid"));
    };

    private final static RowMapper<Trip> ACTIVE_TRIP_COUNT_MAPPER = (resultSet, i) -> {
        Trip trip = TRIP_ROW_MAPPER.mapRow(resultSet, i);
        trip.setProposalCount(resultSet.getInt("proposalcount"));
        return trip;
    };

    private final static RowMapper<Proposal> PROPOSAL_ROW_MAPPER = (rs, rowNum) -> new Proposal(
            rs.getInt("proposal_id"),
            rs.getInt("trip_id"),
            rs.getInt("user_id"),
            rs.getString("description"),
            rs.getString("name")
    );


    private static final Integer ITEMS_PER_PAGE = 12;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcTripInsert;
    private final SimpleJdbcInsert jdbcProposalInsert;

    @Autowired
    public TripDaoV2Impl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcTripInsert = new SimpleJdbcInsert(ds).withTableName("trips").usingGeneratedKeyColumns("trip_id");
        this.jdbcProposalInsert = new SimpleJdbcInsert(ds).withTableName("proposals").usingGeneratedKeyColumns("proposal_id");
    }

    @Override
    public Trip createTrip(final int truckerId,
                       final String licensePlate,
                       final int weight,
                       final int volume,
                       final LocalDateTime departureDate,
                       final LocalDateTime arrivalDate,
                       final String origin,
                       final String destination,
                       final String type,
                       final int price)
    {
        HashMap<String, Object> data = new HashMap<>();

        //put all the data in the hashmap casting to string
        data.put("trucker_id", truckerId);
        data.put("licensePlate", licensePlate);
        data.put("weight", weight);
        data.put("volume", volume);
        data.put("departure_date", Timestamp.valueOf(departureDate));
        data.put("arrival_date", Timestamp.valueOf(arrivalDate));
        data.put("origin", origin);
        data.put("destination", destination);
        data.put("type", type);
        data.put("price", price);
        data.put("trucker_confirmation", false);
        data.put("provider_confirmation", false);
        data.put("imageid", null);

        LOGGER.info("Creating trip for truckerID: {}", truckerId);
        int tripId = jdbcTripInsert.executeAndReturnKey(data).intValue();
        return new Trip(tripId, truckerId, null, licensePlate, weight, volume, departureDate, arrivalDate, origin, destination, type, price, null, false,  null, 0, null);
    }

    @Override
    public Trip createRequest(final int providerId,
                           final int weight,
                           final int volume,
                           final LocalDateTime departureDate,
                           final LocalDateTime arrivalDate,
                           final String origin,
                           final String destination,
                           final String type,
                           final int price)
    {

        HashMap<String, Object> data = new HashMap<>();

        //put all the data in the hashmap casting to string
        data.put("provider_id", providerId);
        data.put("weight", weight);
        data.put("volume", volume);
        data.put("departure_date", Timestamp.valueOf(departureDate));
        data.put("arrival_date", Timestamp.valueOf(arrivalDate));
        data.put("origin", origin);
        data.put("destination", destination);
        data.put("type", type);
        data.put("price", price);
        data.put("trucker_confirmation", false);
        data.put("provider_confirmation", false);

        LOGGER.info("Creating request for providerID: {}", providerId);
        int tripId = jdbcTripInsert.executeAndReturnKey(data).intValue();
        return new Trip(tripId, null, providerId, null, weight, volume, departureDate, arrivalDate, origin, destination, type, price, null, false,  null, 0, null);
    }

    @Override
    public void confirmTrip(int tripId, int userId){
        LOGGER.info("Confirming trip with id: {} for user with id: {}", tripId, userId);
        jdbcTemplate.execute(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "UPDATE trips " +
                                    "SET trucker_confirmation = CASE " +
                                    "    WHEN trucker_confirmation = FALSE AND trucker_id = ? THEN TRUE " +
                                    "    WHEN trucker_confirmation = FALSE AND provider_id = ? THEN TRUE " +
                                    "    ELSE trucker_confirmation " +
                                    "END, " +
                                    "provider_confirmation = CASE " +
                                    "    WHEN provider_confirmation = FALSE AND provider_id = ? THEN TRUE " +
                                    "    ELSE provider_confirmation " +
                                    "END, " +
                                    "confirmation_date = ? "+
                                    "WHERE trip_id = ? AND" +
                                    "(trucker_confirmation = FALSE OR provider_confirmation = FALSE)");
                    ps.setInt(1, userId);
                    ps.setInt(2, userId);
                    ps.setInt(3, userId);
                    ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setInt(5, tripId);
                    return ps;
                },
                (PreparedStatement ps) -> ps.executeUpdate()
        );
    }


    @Override
    public Proposal createProposal(int tripId, int userId, String description){
        HashMap<String, Object> data = new HashMap<>();
        data.put("trip_id", tripId);
        data.put("user_id", userId);
        data.put("description", description);
        LOGGER.info("Creating proposal for trip with id: {} for user with id: {}", tripId, userId);
        int key = jdbcProposalInsert.executeAndReturnKey(data).intValue();
        return new Proposal(key, tripId, userId, description,"");
    }

    @Override
    public List<Proposal> getAllProposalsForTripId(int tripId){
        String query = "SELECT * FROM proposals JOIN users ON proposals.user_id = users.userid WHERE trip_id = ?";
        LOGGER.info("Getting all proposals for trip with id: {}", tripId);
        return jdbcTemplate.query(query, PROPOSAL_ROW_MAPPER, tripId);
    }

    @Override
    public Optional<Proposal> getProposalById(int proposalId){
        String query = "SELECT * FROM proposals NATURAL JOIN users WHERE proposal_id = ?";
        List<Proposal> proposals = jdbcTemplate.query(query, PROPOSAL_ROW_MAPPER, proposalId);
        return proposals.isEmpty() ? Optional.empty() : Optional.of(proposals.get(0));
    }

    private Pair<String, List<Object>> buildQuery(Boolean pagination,String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag){
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
    public List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag){
        Pair<String, List<Object>> builder = buildQuery(true, origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, pag);
        String query = "SELECT * FROM trips WHERE provider_id IS NULL AND departure_date >= now()";
        query += builder.getKey();
        List<Object> params = builder.getValue();
        LOGGER.debug("Getting all active trips");
        return jdbcTemplate.query(query, params.toArray(), TRIP_ROW_MAPPER);

    }

    @Override
    public List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag){
        Pair<String, List<Object>> builder = buildQuery(true, origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, pag);
        String query = "SELECT * FROM trips WHERE trucker_id IS NULL AND departure_date >= now()";
        query += builder.getKey();
        List<Object> params = builder.getValue();
        LOGGER.debug("Getting all active requests");
        return jdbcTemplate.query(query, params.toArray(), TRIP_ROW_MAPPER);
    }

    @Override
    public Integer getActiveTripsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate){
        Pair<String, List<Object>> builder = buildQuery(false, origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, "ASC", departureDate, arrivalDate, 1);
        String query = "SELECT count(*) as total FROM trips WHERE provider_id IS NULL AND departure_date >= now()";
        query += builder.getKey();
        Integer total = jdbcTemplate.query(query, (rs, row) -> rs.getInt("total"), builder.getValue().toArray()).get(0);
        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
    }

    @Override
    public Integer getActiveRequestsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate){
        Pair<String, List<Object>> builder = buildQuery(false, origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, "ASC", departureDate, arrivalDate, 1);
        String query = "SELECT count(*) as total FROM trips WHERE trucker_id IS NULL AND departure_date >= now()";
        query += builder.getKey();
        Integer total = jdbcTemplate.query(query, (rs, row) -> rs.getInt("total"), builder.getValue().toArray()).get(0);
        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
    }

    @Override
    public Optional<Trip> getTripOrRequestById(int tripid){
        List<Trip> trips = jdbcTemplate.query("SELECT * FROM trips WHERE trip_id = ?", TRIP_ROW_MAPPER, tripid);
        return trips.isEmpty() ? Optional.empty() : Optional.of(trips.get(0));
    }

    @Override
    public void acceptProposal(Proposal proposal){
        //TODO: CATCHEAR ESTA EXCEPCION EN EL CONTROLLER
        LOGGER.info("Accepting proposal: {}, for trip: {}", proposal.getProposalId(), proposal.getTripId());
        Trip trip = getTripOrRequestById(proposal.getTripId()).orElseThrow(NoSuchElementException::new);
        String sql;

        if(trip.getTruckerId() <= 0)
            sql = "UPDATE trips SET trucker_id = ? WHERE trip_id = ?";
        else
            sql = "UPDATE trips SET provider_id = ? WHERE trip_id = ?";
        jdbcTemplate.update(sql, proposal.getUserId(), proposal.getTripId());



        sql = "DELETE FROM proposals WHERE proposal_id != ? AND trip_id = ?";
        jdbcTemplate.update(sql, proposal.getProposalId(), proposal.getTripId());
    }

    @Override
    public List<Trip> getAllActiveTripsOrRequestAndProposalsCount(Integer userid, Integer pag) {
        String query = "SELECT trips.*, COUNT(proposals.proposal_id) AS proposalcount FROM trips LEFT JOIN proposals ON trips.trip_id = proposals.trip_id WHERE (trips.trucker_id = ? AND provider_id IS NULL) OR (trips.provider_id = ? AND trucker_id IS NULL) GROUP BY trips.trip_id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(query, ACTIVE_TRIP_COUNT_MAPPER, userid, userid, ITEMS_PER_PAGE, (pag - 1) * ITEMS_PER_PAGE);
    }

    @Override
    public List<Trip> getAllAcceptedTripsAndRequestsByUserId(Integer userid, Integer pag) {
        String query = "SELECT * FROM trips WHERE (trucker_id = ? AND provider_id IS NOT NULL) OR (provider_id = ? AND trucker_id IS NOT NULL) LIMIT ? OFFSET ?";
        return jdbcTemplate.query(query, TRIP_ROW_MAPPER, userid, userid, ITEMS_PER_PAGE, (pag - 1) * ITEMS_PER_PAGE);
    }

    @Override
    public Integer getTotalPagesActiveTripsOrRequests(Integer userid) {
        String query = "SELECT count(*) as total FROM trips WHERE (trucker_id = ? AND provider_id IS NULL) OR (provider_id = ? AND trucker_id IS NULL)";
        Integer total = jdbcTemplate.query(query, (rs, row) -> rs.getInt("total"), userid, userid).get(0);
        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
    }

    @Override
    public Integer getTotalPagesAcceptedTripsAndRequests(Integer userid) {
        String query = "SELECT count(*) as total FROM trips WHERE (trucker_id = ? AND provider_id IS NOT NULL) OR (provider_id = ? AND trucker_id IS NOT NULL)";
        Integer total = jdbcTemplate.query(query, (rs, row) -> rs.getInt("total"), userid, userid).get(0);
        return (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
    }

    @Override
    public Optional<Trip> getTripOrRequestByIdAndUserId(int id, int userid){
        return getTripOrRequestById(id).filter(trip -> trip.getTruckerId() == userid || trip.getProviderId() == userid);
    }

    @Override
    public void setImageId(int tripId, int imageId){
        LOGGER.info("Setting image id: {} for trip: {}", imageId, tripId);
        String sql = "UPDATE trips SET imageid = ? WHERE trip_id = ?";
        jdbcTemplate.update(sql, imageId, tripId);
    }

    @Override
    public int getImageId(int tripId){
        String sql = "SELECT imageid FROM trips WHERE trip_id = ?";
        return jdbcTemplate.query(sql, (rs, row) -> rs.getInt("imageid"), tripId).get(0);
    }


}
