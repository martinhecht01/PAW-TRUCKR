package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.AlertDao;
import ar.edu.itba.paw.interfacesServices.AlertService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.models.Alert;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertServiceImpl implements AlertService{
    private final TripServiceV2 ts;

    private final AlertDao alertDao;

    @Autowired
    public AlertServiceImpl(TripServiceV2 ts, AlertDao alertDao) {
        this.ts = ts;
        this.alertDao = alertDao;
    }


    @Override
    public Optional<Alert> createAlert(User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime from, LocalDateTime to) {
        return alertDao.createAlert(user, city, maxWeight, maxVolume, from, to);
    }

//    @Override
//    public Optional<Alert> getAlert(User user) {
//        return alertDao.getAlert(user);
//    }

    @Override
    public void deleteAlert(User user) {
        alertDao.deleteAlert(user.getAlert());
    }

    @Override
    public Optional<Alert> updateAlert(User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime from, LocalDateTime to) {
        return alertDao.updateAlert(user, city, maxWeight, maxVolume, from, to);
    }

    @Override
    public List<Alert> getAlertsThatMatch(Integer tripId) {
        Optional<Trip> optionalTrip = ts.getTripOrRequestById(tripId);

        if(!optionalTrip.isPresent())
            return null;

        return alertDao.getAlertsThatMatch(optionalTrip.get());
    }
}
