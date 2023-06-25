package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Alert;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AlertDao {

    Optional<Alert> createAlert(User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime from, LocalDateTime to);

//    Optional<Alert> getAlert(User user);

    void deleteAlert(Alert alert);
    Optional<Alert> updateAlert(User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime from, LocalDateTime to);

    List<Alert> getAlertsThatMatch(Trip trip);

}
