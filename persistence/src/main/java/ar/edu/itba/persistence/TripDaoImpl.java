package ar.edu.itba.persistence;

import ar.edu.itba.paw.interfacesPersistence.TripDao;
import ar.edu.itba.paw.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Repository
public class TripDaoImpl implements TripDao {

    private final static RowMapper<Trip> ROW_MAPPER = new RowMapper<Trip>() {
        @Override
        public Trip mapRow(ResultSet rs, int rowNum) throws SQLException {
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
                    rs.getInt("acceptuserid"));
        }
    };


    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
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
                        "  acceptuserid INT REFERENCES users(userid)\n" +
                        ");"
        );
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("trips").usingGeneratedKeyColumns("tripid");
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
                       final String type) {

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

        int tripId = jdbcInsert.executeAndReturnKey(data).intValue();
        return new Trip(tripId, userid, licensePlate, availableWeight, availableVolume, departureDate, arrivalDate, origin, destination, type, -1);
    }

    @Override
    public List<Trip> getAllActiveTrips(){
        return jdbcTemplate.query("SELECT * FROM trips WHERE acceptuserid IS NULL", ROW_MAPPER);
    }

    @Override
    public Trip getTripById(int tripid){
        List<Trip> trips= jdbcTemplate.query("SELECT * FROM trips WHERE tripid = ? AND destination LIKE ?", ROW_MAPPER, tripid);
        if(trips.isEmpty()){
            return null;
        }
        return trips.get(0);
    }

    @Override
    public Trip acceptTrip(Trip trip, int acceptUserId){
        int rowsAffected = jdbcTemplate.update("UPDATE trips SET acceptuserid = ? WHERE tripid = ?", acceptUserId, trip.getTripId());
        if(rowsAffected > 0){
            trip.setAcceptUserId(acceptUserId);
            return trip;
        } else {
            return null;
        }
    }
}
