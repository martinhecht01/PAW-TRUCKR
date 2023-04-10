package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.TripDao;
import ar.edu.itba.paw.interfacesServices.TripService;
import ar.edu.itba.paw.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TripServiceImpl implements TripService {

    private final TripDao tripDao;

    @Autowired
    public TripServiceImpl(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    @Override
    public Trip createTrip( int userId, String licensePlate, int availableWeight, int availableVolume, Date departureDate, Date arrivalDate, String origin, String destination, String type) {
        return tripDao.create(userId, licensePlate, availableWeight, availableVolume, departureDate, arrivalDate, origin, destination, type);
    }
}
