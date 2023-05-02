package ar.edu.itba.persistence;

import ar.edu.itba.paw.interfacesPersistence.TripDao;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.*;

@Repository
public class TripDaoImpl implements TripDao {

    private final static RowMapper<Trip> TRIP_ROW_MAPPER = (rs, rowNum) -> {
        LocalDateTime departure = rs.getTimestamp("departuredate").toLocalDateTime();
        LocalDateTime arrival = rs.getTimestamp("arrivaldate").toLocalDateTime();
        return new Trip(
                rs.getInt("tripid"),
                rs.getInt("userid"),
                rs.getString("licenseplate"),
                rs.getInt("availableweight"),
                rs.getInt("availablevolume"),
                departure,
                arrival,
                rs.getString("origin"),
                rs.getString("destination"),
                rs.getString("type"),
                rs.getInt("price"),
                rs.getInt("acceptuserid")
        );
    };

    private final static RowMapper<Proposal> PROPOSAL_ROW_MAPPER = (rs, rowNum) -> new Proposal(
            rs.getInt("proposalid"),
            rs.getInt("tripid"),
            rs.getInt("userid"),
            rs.getString("description"),
            rs.getString("name")
    );


    private static Integer ITEMS_PER_PAGE = 10;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcTripInsert;

    private final SimpleJdbcInsert jdbcProposalInsert;

    @Autowired
    public TripDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                "  userid SERIAL PRIMARY KEY,\n" +
                "  cuit VARCHAR(255) UNIQUE NOT NULL,\n" +
                "  email VARCHAR(255) NOT NULL,\n" +
                "  name VARCHAR(255) NOT NULL\n" +
                ");");
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS trips (\n" +
                        "  tripid SERIAL PRIMARY KEY,\n" +
                        "  userid INT NOT NULL REFERENCES users(userid),\n" +
                        "  licenseplate VARCHAR(255),\n" +
                        "  availableweight INT,\n" +
                        "  availablevolume INT,\n" +
                        "  departuredate TIMESTAMP,\n" +
                        "  arrivaldate TIMESTAMP,\n" +
                        "  origin VARCHAR(255),\n" +
                        "  destination VARCHAR(255),\n" +
                        "  type VARCHAR(255),\n" +
                        "  price INT,\n" +
                        "  acceptuserid INT REFERENCES users(userid)\n" +
                        ");"
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS proposals (\n" +
                        "  proposalid SERIAL PRIMARY KEY,\n" +
                        "  tripid INT NOT NULL REFERENCES trips(tripid)," +
                        "  userid INT NOT NULL REFERENCES users(userid),\n" +
                        "  description VARCHAR(300)\n" +
                        ");"
        );
        this.jdbcTripInsert = new SimpleJdbcInsert(ds).withTableName("trips").usingGeneratedKeyColumns("tripid");
        this.jdbcProposalInsert = new SimpleJdbcInsert(ds).withTableName("proposals").usingGeneratedKeyColumns("proposalid");
    }

    @Override
    public Trip create(final int userid,
                       final String licensePlate,
                       final int availableWeight,
                       final int availableVolume,
                       final LocalDateTime departureDate,
                       final LocalDateTime arrivalDate,
                       final String origin,
                       final String destination,
                       final String type,
                       final int price)
    {

        HashMap<String, Object> data = new HashMap<>();

        //put all the data in the hashmap casting to string
        data.put("userid", userid);
        data.put("licensePlate", licensePlate);
        data.put("availableWeight", availableWeight);
        data.put("availableVolume", availableVolume);
        data.put("departureDate", Timestamp.valueOf(departureDate));
        data.put("arrivalDate", Timestamp.valueOf(arrivalDate));
        data.put("origin", origin);
        data.put("destination", destination);
        data.put("type", type);
        data.put("price", price);

        int tripId = jdbcTripInsert.executeAndReturnKey(data).intValue();
        return new Trip(tripId, userid, licensePlate, availableWeight, availableVolume, departureDate, arrivalDate, origin, destination, type, price,-1);
    }

    @Override
    public Proposal createProposal(int tripid, int userid, String description){
        HashMap<String, Object> data = new HashMap<>();
        data.put("tripid", tripid);
        data.put("userid", userid);
        data.put("description", description);
        int key = jdbcProposalInsert.executeAndReturnKey(data).intValue();
        return new Proposal(key, tripid, userid, description,"");
    }

    @Override
    public List<Proposal> getProposalsForTripId(int tripid){
        String query = "SELECT * FROM proposals NATURAL JOIN users WHERE tripid =  ?";
        return jdbcTemplate.query(query, PROPOSAL_ROW_MAPPER, tripid);
    }

    @Override
    public Optional<Proposal> getProposalById(int proposalId){
        String query = "SELECT * FROM proposals NATURAL JOIN users WHERE proposalid = ?";
        List<Proposal> proposals = jdbcTemplate.query(query, PROPOSAL_ROW_MAPPER, proposalId);
        return proposals.isEmpty() ? Optional.empty() : Optional.of(proposals.get(0));
    }

    @Override
    public List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag){
        if(pag < 1)
            pag = 1;
        Integer offset = (pag-1)*10;
        String query = "SELECT * FROM trips WHERE acceptuserid IS NULL ";
        List<Object> params = new ArrayList<>();


        if (origin != null && !origin.equals("")){
            query = query + " AND origin = ?";
            params.add(origin);
        }

        if (destination != null && !destination.equals("")){
            query = query + " AND destination = ?";
            params.add(destination);
        }

        if (minAvailableVolume != null){
            query = query + " AND availableVolume >= ?";
            params.add(minAvailableVolume);
        }

        if (minAvailableWeight != null){
            query = query + " AND availableWeight >= ?";
            params.add(minAvailableWeight);
        }

        if (minPrice != null){
            query = query + " AND price >= ?";
            params.add(minPrice);
        }

        if (maxPrice != null){
            query = query + " AND price <= ?";
            params.add(maxPrice);
        }

        if (departureDate != null && !departureDate.equals("")){
            query = query + " AND DATE(departuredate) = CAST(? AS DATE)";
            params.add("'" + departureDate + "'");
        }

        if (arrivalDate != null && !arrivalDate.equals("")){
            query = query + " AND DATE(arrivaldate) = CAST(? AS DATE)";
            params.add("'" + arrivalDate + "'");
        }

        if(sortOrder != null && !sortOrder.isEmpty()) {
            //sort order asc and desc
            if (sortOrder.equals("departureDate ASC")) {
                query = query + " ORDER BY departuredate ASC";
            } else if (sortOrder.equals("departureDate DESC")) {
                query = query + " ORDER BY departuredate DESC";
            } else if(sortOrder.equals("arrivalDate ASC")) {
                query = query + " ORDER BY arrivaldate ASC";
            } else if(sortOrder.equals("arrivalDate DESC")) {
                query = query + " ORDER BY arrivaldate DESC";
            } else if(sortOrder.equals("price ASC")) {
                query = query + " ORDER BY price ASC";
            } else if(sortOrder.equals("price DESC")) {
                query = query + " ORDER BY price DESC";
            }
        }
        query = query + " LIMIT ? OFFSET ?";
        params.add(ITEMS_PER_PAGE);
        params.add(offset);

        return jdbcTemplate.query(query, params.toArray(), TRIP_ROW_MAPPER);

    }

    @Override
    public List<Trip> getAllActiveTripsByUserId(Integer userId) {
        String query = "SELECT * FROM trips WHERE userid = ? AND acceptuserid IS NULL";
        return jdbcTemplate.query(query, TRIP_ROW_MAPPER, userId);
    }

    @Override
    public Integer getTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate) {
        String query = "SELECT COUNT(*) FROM trips WHERE acceptuserid IS NULL ";
        List<Object> params = new ArrayList<>();

        if (origin != null && !origin.equals("")){
            query = query + " AND origin = ?";
            params.add(origin);
        }

        if (destination != null && !destination.equals("")){
            query = query + " AND destination = ?";
            params.add(destination);
        }

        if (minAvailableVolume != null){
            query = query + " AND availableVolume >= ?";
            params.add(minAvailableVolume);
        }

        if (minAvailableWeight != null){
            query = query + " AND availableWeight >= ?";
            params.add(minAvailableWeight);
        }

        if (minPrice != null){
            query = query + " AND price >= ?";
            params.add(minPrice);
        }

        if (maxPrice != null){
            query = query + " AND price <= ?";
            params.add(maxPrice);
        }

        if (departureDate != null && !departureDate.equals("")){
            query = query + " AND DATE(departuredate) = CAST(? AS DATE)";
            params.add("'" + departureDate + "'");
        }

        if (arrivalDate != null && !arrivalDate.equals("")){
            query = query + " AND DATE(arrivaldate) = CAST(? AS DATE)";
            params.add("'" + arrivalDate + "'");
        }
        return (int) Math.ceil((double) jdbcTemplate.queryForObject(query, params.toArray(), Integer.class) /ITEMS_PER_PAGE);
    }
    @Override
    public Optional<Trip> getTripById(int tripid){
        List<Trip> trips = jdbcTemplate.query("SELECT * FROM trips WHERE tripid = ?", TRIP_ROW_MAPPER, tripid);
        return trips.isEmpty() ? Optional.empty() : Optional.of(trips.get(0));
    }

    @Override
    public void acceptTrip(int proposalId){
        Proposal proposal = getProposalById(proposalId).get();
        System.out.println("PROPOSAL DESCRIPTION = " + proposal.getDescription());
        jdbcTemplate.update("UPDATE trips SET acceptuserid = ? WHERE tripid = ?", proposal.getUserid() , proposal.getTripid());
    }

    @Override
    public List<Trip> getAllUnproposedTripsByUserId(Integer userid) {
        String query = "SELECT trips.* FROM trips LEFT JOIN proposals ON trips.tripid = proposals.tripid WHERE proposals.tripid IS NULL AND trips.userid = ? AND acceptuserid IS NULL;";
        return jdbcTemplate.query(query, TRIP_ROW_MAPPER, userid);
    }

    @Override
    public List<Trip> getAllAcceptedTripsByUserId(Integer userid) {
        String query = "SELECT * FROM trips WHERE userid = ? AND acceptuserid IS NOT NULL";
        return jdbcTemplate.query(query, TRIP_ROW_MAPPER, userid);
    }

    @Override
    public List<Trip> getAllProposedTripsByUserId(Integer userid) {
        String query = "SELECT trips.* FROM trips INNER JOIN proposals ON trips.tripid = proposals.tripid WHERE trips.userid = ? AND acceptuserid IS NULL;";
        return jdbcTemplate.query(query, TRIP_ROW_MAPPER, userid);
    }

    @Override
    public Optional<Trip> getTripByIdAndUserId(int tripid, int userid){
        return getTripById(tripid).filter(trip -> trip.getUserId() == userid);
    }

}
