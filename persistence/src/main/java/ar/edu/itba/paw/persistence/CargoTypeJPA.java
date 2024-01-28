package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfacesPersistence.CargoTypeDao;
import ar.edu.itba.paw.models.CargoType;
import ar.edu.itba.paw.models.City;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CargoTypeJPA implements CargoTypeDao {


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<CargoType> getAllCargoTypes() {
        TypedQuery<CargoType> query= entityManager.createQuery("SELECT c FROM CargoType c", CargoType.class);
        return query.getResultList();
    }

    @Override
    public Optional<CargoType> getCargoTypeById(Integer id) {
        CargoType cargoType = entityManager.find(CargoType.class, id);
        if (cargoType == null) {
            return Optional.empty();
        }
        return Optional.of(cargoType);
    }
}
