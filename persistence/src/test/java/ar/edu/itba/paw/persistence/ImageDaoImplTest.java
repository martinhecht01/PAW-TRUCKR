package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.sql.DataSource;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ImageDaoImplTest {
    private static final byte[] IMAGEBYTE_EXISTENT = new byte[] {(byte) 0xa0,0x01, 0x02, 0x03, 0x04, 0x05, 0x06};
    private static final byte[] IMAGEBYTE_NOT_EXISTENT = new byte[] {(byte) 0xa0,0x01, (byte) 0xb7, 0x56, (byte) 0x90, (byte) 0xbd, 0x08,0x78, (byte) 0x93};

    private static final int IMAGEID_NOT_EXISTENT = 1;

    private static final int IMAGEID_EXISTENT = 2;


    @Autowired
    private DataSource ds;

    @Autowired
    private ImageDao imageDao;


    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
    }


    @Rollback
    @Test
    public void testUploadImage(){


        int newImageId = imageDao.uploadImage(IMAGEBYTE_NOT_EXISTENT);

        em.flush();

        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "images"));

        byte[] maybeImage = jdbcTemplate.queryForObject("SELECT image FROM images WHERE imageid = ?", byte[].class, IMAGEID_NOT_EXISTENT);

        Assert.assertArrayEquals(IMAGEBYTE_NOT_EXISTENT, maybeImage);
        Assert.assertEquals(IMAGEID_NOT_EXISTENT, newImageId);


    }

    @Rollback
    @Test
    public void testGetImage(){

        Optional<Image> image = imageDao.getImage(IMAGEID_EXISTENT);

        Assert.assertTrue(image.isPresent());

        Assert.assertArrayEquals(jdbcTemplate.queryForObject("SELECT image FROM images WHERE imageid = ?", byte[].class, IMAGEID_EXISTENT), image.get().getImage());
    }

    @Rollback
    @Test
    public void testGetImageFailed(){

        Optional<Image> image = imageDao.getImage(IMAGEID_NOT_EXISTENT);

        Assert.assertFalse(image.isPresent());
    }
}
