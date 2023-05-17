package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.CityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CityDaoImpl implements CityDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CityDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<String> getAllCities() {
        return jdbcTemplate.query("SELECT name FROM cities", (rs, rowNum) -> rs.getString("name"));
    }

}
