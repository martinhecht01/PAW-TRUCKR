package ar.edu.itba.persistence;

import ar.edu.itba.paw.interfacesPersistence.RequestDao;
import ar.edu.itba.paw.models.Request;
import ar.edu.itba.paw.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class RequestDaoImpl implements RequestDao {

    private final static RowMapper<Request> ROW_MAPPER = (rs, rowNum) -> {
        LocalDateTime departure = rs.getTimestamp("mindeparturedate").toLocalDateTime();
        LocalDateTime arrival = rs.getTimestamp("maxarrivaldate").toLocalDateTime();
        return new Request(
                rs.getInt("requestid"),
                rs.getInt("userid"),
                rs.getInt("requestedweight"),
                rs.getInt("requestevolume"),
                rs.getInt("maxprice"),
                departure,
                arrival,
                rs.getString("type"),
                rs.getString("origin"),
                rs.getString("destination"),
                rs.getInt("acceptuserid")
        );
    };


    private static Integer ITEMS_PER_PAGE = 10;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    @Autowired
    public RequestDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                "  userid SERIAL PRIMARY KEY,\n" +
                "  cuit VARCHAR(255) UNIQUE NOT NULL,\n" +
                "  email VARCHAR(255) NOT NULL,\n" +
                "  name VARCHAR(255) NOT NULL\n" +
                ");");
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS requests (\n" +
                        "  requestid SERIAL PRIMARY KEY,\n" +
                        "  userid INT NOT NULL REFERENCES users(userid),\n" +
                        "  availableweight INT,\n" +
                        "  availablevolume INT,\n" +
                        "  mindeparturedate TIMESTAMP,\n" +
                        "  maxarrivaldate TIMESTAMP,\n" +
                        "  origin VARCHAR(255),\n" +
                        "  destination VARCHAR(255),\n" +
                        "  type VARCHAR(255),\n" +
                        "  maxprice INT,\n" +
                        "  acceptuserid INT REFERENCES users(userid)\n" +
                        ");"
        );
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("requests").usingGeneratedKeyColumns("requestid");
    }

    @Override
    public Request create(final int userid,
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
        data.put("availableWeight", availableWeight);
        data.put("availableVolume", availableVolume);
        data.put("departureDate", Timestamp.valueOf(departureDate));
        data.put("arrivalDate", Timestamp.valueOf(arrivalDate));
        data.put("origin", origin);
        data.put("destination", destination);
        data.put("type", type);
        data.put("price", price);

        int tripId = jdbcInsert.executeAndReturnKey(data).intValue();
        return new Request(tripId, userid, availableWeight, availableVolume, departureDate, arrivalDate, origin, destination, type, price,-1);
    }

    @Override
    public List<Request> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {
        if (pag < 1)
            pag = 1;
        Integer offset = (pag - 1) * 10;
        String query = "SELECT * FROM requests WHERE acceptuserid IS NULL ";
        List<Object> params = new ArrayList<>();


        if (origin != null && !origin.equals("")) {
            query = query + " AND origin = ?";
            params.add(origin);
        }

        if (destination != null && !destination.equals("")) {
            query = query + " AND destination = ?";
            params.add(destination);
        }

        if (minAvailableVolume != null) {
            query = query + " AND availableVolume >= ?";
            params.add(minAvailableVolume);
        }

        if (minAvailableWeight != null) {
            query = query + " AND availableWeight >= ?";
            params.add(minAvailableWeight);
        }

        if (minPrice != null) {
            query = query + " AND price >= ?";
            params.add(minPrice);
        }

        if (maxPrice != null) {
            query = query + " AND price <= ?";
            params.add(maxPrice);
        }

        if (departureDate != null && !departureDate.equals("")) {
            query = query + " AND DATE(departuredate) = CAST(? AS DATE)";
            params.add("'" + departureDate + "'");
        }

        if (arrivalDate != null && !arrivalDate.equals("")) {
            query = query + " AND DATE(arrivaldate) = CAST(? AS DATE)";
            params.add("'" + arrivalDate + "'");
        }

        if (sortOrder != null && !sortOrder.isEmpty()) {
            //sort order asc and desc
            if (sortOrder.equals("departureDate ASC")) {
                query = query + " ORDER BY departuredate ASC";
            } else if (sortOrder.equals("departureDate DESC")) {
                query = query + " ORDER BY departuredate DESC";
            } else if (sortOrder.equals("arrivalDate ASC")) {
                query = query + " ORDER BY arrivaldate ASC";
            } else if (sortOrder.equals("arrivalDate DESC")) {
                query = query + " ORDER BY arrivaldate DESC";
            } else if (sortOrder.equals("price ASC")) {
                query = query + " ORDER BY price ASC";
            } else if (sortOrder.equals("price DESC")) {
                query = query + " ORDER BY price DESC";
            }
        }
        query = query + " LIMIT ? OFFSET ?";
        params.add(ITEMS_PER_PAGE);
        params.add(offset);

        return jdbcTemplate.query(query, params.toArray(), ROW_MAPPER);
        }

//
//    @Override
//    public List<Request> getAllActiveTripsByUserId(Integer userId) {
//        String query = "SELECT * FROM trips WHERE userid = ? AND acceptuserid IS NULL";
//        return jdbcTemplate.query(query, ROW_MAPPER, userId);
//    }
//
//    @Override
//    public Integer getTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate) {
//        String query = "SELECT COUNT(*) FROM trips WHERE acceptuserid IS NULL ";
//        List<Object> params = new ArrayList<>();
//
//        if (origin != null && !origin.equals("")){
//            query = query + " AND origin = ?";
//            params.add(origin);
//        }
//
//        if (destination != null && !destination.equals("")){
//            query = query + " AND destination = ?";
//            params.add(destination);
//        }
//
//        if (minAvailableVolume != null){
//            query = query + " AND availableVolume >= ?";
//            params.add(minAvailableVolume);
//        }
//
//        if (minAvailableWeight != null){
//            query = query + " AND availableWeight >= ?";
//            params.add(minAvailableWeight);
//        }
//
//        if (minPrice != null){
//            query = query + " AND price >= ?";
//            params.add(minPrice);
//        }
//
//        if (maxPrice != null){
//            query = query + " AND price <= ?";
//            params.add(maxPrice);
//        }
//
//        if (departureDate != null && !departureDate.equals("")){
//            query = query + " AND DATE(departuredate) = CAST(? AS DATE)";
//            params.add("'" + departureDate + "'");
//        }
//
//        if (arrivalDate != null && !arrivalDate.equals("")){
//            query = query + " AND DATE(arrivaldate) = CAST(? AS DATE)";
//            params.add("'" + arrivalDate + "'");
//        }
//        return (int) Math.ceil((double) jdbcTemplate.queryForObject(query, params.toArray(), Integer.class) /ITEMS_PER_PAGE);
//    }
//    @Override
//    public Optional<Trip> getRequestById(int tripid){
//        List<Request> trips= jdbcTemplate.query("SELECT * FROM requests WHERE tripid = ?", ROW_MAPPER, tripid);
//        return trips.isEmpty() ? Optional.empty() : Optional.of(trips.get(0));
//    }
//
//    @Override
//    public Trip acceptTrip(Trip trip, int acceptUserId){
//        System.out.println(acceptUserId);
//        int rowsAffected = jdbcTemplate.update("UPDATE trips SET acceptuserid = ? WHERE tripid = ?", acceptUserId, trip.getTripId());
//        if(rowsAffected > 0){
//            trip.setAcceptUserId(acceptUserId);
//            return trip;
//        } else {
//            return null;
//        }
//    }
}
