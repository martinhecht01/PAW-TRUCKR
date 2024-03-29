package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfacesPersistence.AlertDao;
import ar.edu.itba.paw.models.Alert;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AlertDaoJPA implements AlertDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Alert> createAlert(User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime from, LocalDateTime to, String cargoType) {
        if(user == null || user.getAlert() != null)
            return Optional.empty();

        Alert alert = new Alert(user, city, maxWeight, maxVolume, from, to, cargoType);
        entityManager.persist(alert);
        return Optional.of(alert);
    }


    @Override
    public void deleteAlert(Alert alert) {
        if(alert != null)
            entityManager.remove(alert);
    }

    @Override
    public Optional<Alert> getAlertById(Integer alertId) {
        Alert alert = entityManager.find(Alert.class, alertId);
        return alert != null ? Optional.of(alert) : Optional.empty();
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
        String jpql = "SELECT a FROM Alert a WHERE (a.cargoType IS NULL OR :type = a.cargoType) AND :city = a.city AND a.fromDate <= :departureDate AND (a.toDate IS NULL OR a.toDate >= :departureDate) AND (a.maxWeight IS NULL OR a.maxWeight >= :weight) AND (a.maxVolume IS NULL OR a.maxVolume >= :volume)";

        return entityManager.createQuery(jpql, Alert.class)
                .setParameter("city", trip.getOrigin())
                .setParameter("departureDate", Timestamp.valueOf(trip.getDepartureDate()))
                .setParameter("weight", trip.getWeight() == null ? 0 : trip.getWeight())
                .setParameter("volume", trip.getVolume() == null ? 0 : trip.getVolume())
                .setParameter("type", trip.getType())
                .getResultList();
    }

}
