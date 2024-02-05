package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.AlertDao;
import ar.edu.itba.paw.interfacesServices.AlertService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.exceptions.AlertAlreadyExistsException;
import ar.edu.itba.paw.interfacesServices.exceptions.AlertNotFoundException;
import ar.edu.itba.paw.models.Alert;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertServiceImpl implements AlertService{
    private final TripServiceV2 ts;

    private final AlertDao alertDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertServiceImpl.class);


    @Autowired
    public AlertServiceImpl(TripServiceV2 ts, AlertDao alertDao) {
        this.ts = ts;
        this.alertDao = alertDao;
    }

    @Override
    public Optional<Alert> createAlert(User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime from, LocalDateTime to, String type) {
        LOGGER.info("Creating alert for user {} in city {}", user.getUserId(), city);
        if(user.getAlert() != null)
            throw new AlertAlreadyExistsException();
        return alertDao.createAlert(user, city, maxWeight, maxVolume, from, to, type);
    }

//    @Override
//    public Optional<Alert> getAlert(User user) {
//        return alertDao.getAlert(user);
//    }

    @Transactional
    @Override
    public void deleteAlert(Integer alertId) {
        LOGGER.info("Deleting alert {}", alertId);
        Alert alert= alertDao.getAlertById(alertId).orElseThrow(AlertNotFoundException::new);
        alertDao.deleteAlert(alert);
    }

    @Transactional
    @Override
    public Optional<Alert> updateAlert(User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime from, LocalDateTime to) {
        LOGGER.info("Updating alert for user {}", user.getUserId());
        return alertDao.updateAlert(user, city, maxWeight, maxVolume, from, to);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Alert> getAlertsThatMatch(Integer tripId) {
        Optional<Trip> optionalTrip = ts.getTripOrRequestById(tripId);

        if(!optionalTrip.isPresent())
            return null;

        return alertDao.getAlertsThatMatch(optionalTrip.get());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Alert> getAlertById(Integer alertId) {
        Alert toRet = alertDao.getAlertById(alertId).orElseThrow(AlertNotFoundException::new);
        return Optional.of(toRet);
    }
}
