package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.CargoTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CargoTypeDaoImpl implements CargoTypeDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CargoTypeDaoImpl(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<String> getAllCargoTypes() {
        return jdbcTemplate.query("SELECT name FROM cargotypes", (rs, rowNum) -> rs.getString("name"));
    }

}
