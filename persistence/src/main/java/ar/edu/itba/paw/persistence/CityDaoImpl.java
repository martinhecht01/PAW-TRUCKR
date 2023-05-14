package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.CityDao;
import ar.edu.itba.paw.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CityDaoImpl implements CityDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CityDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<String> getAllCities() {
        List<String> cities= jdbcTemplate.query("SELECT name FROM cities", (rs, rowNum) -> rs.getString("name"));
        return cities;
    }

}
