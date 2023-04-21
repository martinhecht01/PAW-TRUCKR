package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.TripDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.TripService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {

    private final TripDao tripDao;
    private final UserDao userDao;
    private final MailService ms;

    @Autowired
    public TripServiceImpl(TripDao tripDao, UserDao userDao, MailService ms) {
        this.tripDao = tripDao;
        this.userDao = userDao;
        this.ms = ms;
    }

    @Override
    public Trip createTrip(String email,
                           String name,
                           String cuit,
                           String licensePlate,
                           int availableWeight,
                           int availableVolume,
                           LocalDateTime departureDate,
                           LocalDateTime arrivalDate,
                           String origin,
                           String destination,
                           String type,
                           int price)
    {
        User user = userDao.getUserByCuit(cuit);
        if(user == null)
            user = userDao.create(email,name,cuit);
        int userId = user.getUserId();
        return tripDao.create(userId, licensePlate.toUpperCase(), availableWeight, availableVolume, departureDate, arrivalDate, origin, destination, type, price);
    }

    @Override
    public List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag){
        return tripDao.getAllActiveTrips(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate,pag);
    }

    @Override
    public Optional<Trip> getTripById(int tripid){
        return tripDao.getTripById(tripid);
    }

    @Override
    public Trip acceptTrip(int tripId,String email, String name, String cuit ){
        User user = userDao.getUserByCuit(cuit);
        if(user == null)
            user = userDao.create(email,name,cuit);
        int acceptUserId = user.getUserId();

        Trip trip = tripDao.getTripById(tripId).get();
        Trip acceptedTrip = tripDao.acceptTrip(trip, acceptUserId);
        User tripOwner = userDao.getUserById(acceptedTrip.getUserId());
        ms.sendEmailTrip(tripOwner, user, acceptedTrip);
        return acceptedTrip;
    }


}
