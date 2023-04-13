package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.TripDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.TripService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    private final TripDao tripDao;
    private final UserDao userDao;

    @Autowired
    public TripServiceImpl(TripDao tripDao, UserDao userDao) {
        this.tripDao = tripDao;
        this.userDao = userDao;
    }

    @Override
    public Trip createTrip(String email, String name, String cuit, String licensePlate, int availableWeight, int availableVolume, LocalDateTime departureDate, LocalDateTime arrivalDate, String origin, String destination, String type) {
        User user = userDao.getUserByCuit(cuit);
        if(user == null)
            user = userDao.create(email,name,cuit);
        int userId = user.getUserId();
        return tripDao.create(userId, licensePlate, availableWeight, availableVolume, departureDate, arrivalDate, origin, destination, type);
    }

    @Override
    public List<Trip> getAllTrips(){
        return tripDao.getAllTrips();
    }
}
