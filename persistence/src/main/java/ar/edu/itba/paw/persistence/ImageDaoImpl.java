package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;

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
    public int uploadImage(final byte[] image){
        final HashMap<String, Object> data = new HashMap<>();
        data.put("image", image);

        return jdbcInsert.executeAndReturnKey(data).intValue();
    }

    @Override
    public Optional<Image> getImage(final int imageid){
        List<Image> maybeList = jdbcTemplate.query("SELECT * FROM images WHERE imageid = ?", IMAGE_ROW_MAPPER, imageid);
        if (!maybeList.isEmpty())
            return Optional.of(maybeList.get(0));
        return Optional.empty();
    }

    @Override
    public void updateImage(final byte[] image, final int imageid){
        jdbcTemplate.update("UPDATE images SET image = ? WHERE imageid = ?", image, imageid);
    }



}
