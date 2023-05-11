package ar.edu.itba.persistence;

import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class ImageDaoImpl implements ImageDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    @Autowired
    public ImageDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("images").usingGeneratedKeyColumns("imageid");
    }
    private final static RowMapper<Image> IMAGE_ROW_MAPPER = (rs, rowNum) -> new Image(
            rs.getInt("imageid"),
            rs.getBytes("image"));

    @Override
    public void uploadImage(final byte[] image, final int userid){
        final int imageid = jdbcInsert.executeAndReturnKey(
                new java.util.HashMap<String, Object>() {{
                    put("image", image);
                    put("userid", userid);
                    put("tripid", null);
                }}).intValue();
    }

    @Override
    public Optional<Image> getImage(final int imageid){
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM images WHERE imageid = ?", IMAGE_ROW_MAPPER, imageid));
    }

    @Override
    public void updateImage(final byte[] image, final int imageid){
        jdbcTemplate.update("UPDATE images SET image = ? WHERE imageid = ?", image, imageid);
    }



}
