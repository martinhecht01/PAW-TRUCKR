package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ImageDaoImplTest {
    private static final byte[] IMAGE_EXISTENT = new byte[0];
    private static final byte[] IMAGE_NOT_EXISTENT = new byte[10];


    private static final int IMAGEID_NOT_EXISTENT = 2;

    private static final int IMAGEID_EXISTENT = 1;

    @Autowired
    private DataSource ds;

    @Autowired
    private ImageDao imageDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
    }


    @Rollback
    @Test
    public void testUploadImage(){
        int newImageId = imageDao.uploadImage(IMAGE_NOT_EXISTENT);

        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "images"));

        byte[] maybeImage = jdbcTemplate.queryForObject("SELECT image FROM images WHERE imageid = ?", byte[].class, IMAGEID_NOT_EXISTENT);

        Assert.assertArrayEquals(IMAGE_NOT_EXISTENT, maybeImage);
        Assert.assertEquals(IMAGEID_NOT_EXISTENT, newImageId);
    }

//    @Rollback
//    @Test
//    public void testGetImage(){
//        Optional<Image> image = imageDao.getImage(IMAGEID_EXISTENT);
//
//        Assert.assertTrue(image.isPresent());
//
//        Assert.assertArrayEquals(IMAGE_EXISTENT, image);
//    }
}
