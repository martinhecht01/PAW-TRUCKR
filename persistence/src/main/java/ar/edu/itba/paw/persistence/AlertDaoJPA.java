package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfacesPersistence.AlertDao;
import ar.edu.itba.paw.models.Alert;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AlertDaoJPA implements AlertDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Alert> createAlert(User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime from, LocalDateTime to) {
        if(user == null || getAlert(user).isPresent())
            return Optional.empty();

        Alert alert = new Alert(user, city, maxWeight, maxVolume, from, to);
        entityManager.persist(alert);
        return Optional.of(alert);
    }

    @Override
    public Optional<Alert> getAlert(User user) {
        Alert alert = entityManager.find(Alert.class, user);

        if(alert == null)
            return Optional.empty();

        return Optional.of(alert);
    }

    @Override
    public void deleteAlert(Alert alert) {
        entityManager.remove(alert);
    }

    @Override
    public Optional<Alert> updateAlert(User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime from, LocalDateTime to) {
        Alert alert = entityManager.find(Alert.class, user);

        if(alert == null)
            return Optional.empty();

        alert.setCity(city);
        alert.setMaxWeight(maxWeight);
        alert.setMaxVolume(maxVolume);
        alert.setFromDate(from);
        alert.setToDate(to);

        entityManager.persist(alert);

        return Optional.of(alert);
    }

    @Override
    public List<Alert> getAlertsThatMatch(Trip trip) {
        String jpql = "SELECT a FROM Alert a WHERE :city IN a.cities AND DATE(a.from) <= CAST( :departureDate AS DATE) AND (a.to = NULL OR a.to >= CAST( :departureDate AS DATE)) AND a.maxWeight >= :weight AND a.maxVolume >= :volume AND a.cargoType = :type";

        return entityManager.createQuery(jpql, Alert.class)
                .setParameter("city", trip.getOrigin())
                .setParameter("departureDate", trip.getDepartureDate())
                .setParameter("weight", trip.getWeight() == null ? 0 : trip.getWeight())
                .setParameter("volume", trip.getVolume() == null ? 0 : trip.getVolume())
                .setParameter("type", trip.getType())
                .getResultList();
    }
}
