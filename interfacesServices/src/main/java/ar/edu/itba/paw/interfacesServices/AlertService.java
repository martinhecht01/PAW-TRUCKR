package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Alert;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AlertService {
    Optional<Alert> createAlert(User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime from, LocalDateTime to, String type);

//    Optional<Alert> getAlert(User user);

    void deleteAlert(User user);

    Optional<Alert> updateAlert(User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime from, LocalDateTime to);

    List<Alert> getAlertsThatMatch(Integer tripId);

    Optional<Alert> getAlertById(Integer alertId);
}
