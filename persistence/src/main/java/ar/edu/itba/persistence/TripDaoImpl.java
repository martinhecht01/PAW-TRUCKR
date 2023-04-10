package ar.edu.itba.persistence;

import ar.edu.itba.paw.interfacesPersistence.TripDao;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class TripDaoImpl implements TripDao {

    private final static RowMapper<Trip> ROW_MAPPER = new RowMapper<Trip>() {
        @Override
        public Trip mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Trip(
                    rs.getInt("tripid"),
                    rs.getInt("userid"),
                    rs.getString("licenseplate"),
                    rs.getInt("availableweight"),
                    rs.getInt("availablevolume"),
                    rs.getDate("departuredate"),
                    rs.getDate("arrivaldate"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getString("type"));
        }
    };


    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    @Autowired
    public TripDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                "  userid SERIAL PRIMARY KEY,\n" +
                "  cuit VARCHAR(255) UNIQUE,\n" +
                "  email VARCHAR(255),\n" +
                "  name VARCHAR(255)\n" +
                ");");
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS trips (\n" +
                "  tripid SERIAL PRIMARY KEY,\n" +
                "  userid INT REFERENCES users(userid),\n" +
                "  licenseplate VARCHAR(255),\n" +
                "  availableweight INT,\n" +
                "  availablevolume INT,\n" +
                "  departuredate DATE,\n" +
                "  arrivaldate DATE,\n" +
                "  origin VARCHAR(255),\n" +
                "  destination VARCHAR(255),\n" +
                "  type VARCHAR(255)\n" +
                ");");
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("trips").usingGeneratedKeyColumns("tripid");
    }

    @Override
    public Trip create(final int userid,
                       final String licensePlate,
                       final int availableWeight,
                       final int availableVolume,
                       final Date departureDate,
                       final Date arrivalDate,
                       final String origin,
                       final String destination,
                       final String type) {

        HashMap<String, Object> data = new HashMap<>();

        //put all the data in the hashmap casting to string
        data.put("userid", userid);
        data.put("licensePlate", licensePlate);
        data.put("availableWeight", availableWeight);
        data.put("availableVolume", availableVolume);
        data.put("departureDate", departureDate);
        data.put("arrivalDate", arrivalDate);
        data.put("origin", origin);
        data.put("destination", destination);
        data.put("type", type);

        int tripId = jdbcInsert.executeAndReturnKey(data).intValue();
        return new Trip(tripId, userid, licensePlate, availableWeight, availableVolume, departureDate, arrivalDate, origin, destination, type);
    }

    @Override
    public List<Trip> getAllTrips(){
        return jdbcTemplate.query("SELECT * FROM trips", ROW_MAPPER);
    }

}
