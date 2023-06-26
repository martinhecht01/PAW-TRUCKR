package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.CityDao;
import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.models.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CityDaoJPA implements CityDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<String> getAllCities() {
        return entityManager.createQuery("SELECT c.cityName FROM City c", String.class).getResultList();
    }
}
