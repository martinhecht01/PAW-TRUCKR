package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.CityDao;
import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CityDaoJPA implements CityDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<City> getAllCities() {
        TypedQuery<City> query= entityManager.createQuery("SELECT c FROM City c", City.class);
        return query.getResultList();
    }

    @Override
    public Optional<City> getCityById(int id) {
        City city = entityManager.find(City.class, id);
        if (city == null) {
            return Optional.empty();
        }
        return Optional.of(city);
    }
}
