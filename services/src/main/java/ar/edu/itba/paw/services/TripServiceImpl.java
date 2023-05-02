package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.TripDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.TripService;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
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
    public Trip createTrip(String cuit,
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
        User user = userDao.getUserByCuit(cuit).get();
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
    public void acceptTrip(int proposalid){
        tripDao.acceptTrip(proposalid);
        Proposal proposal = tripDao.getProposalById(proposalid).get();
        Trip trip = tripDao.getTripById(proposal.getTripid()).get();


         User tripOwner = userDao.getUserById(trip.getUserId()).get();
         User user = userDao.getUserById(proposal.getUserid()).get();
        try{ms.sendTripEmail(tripOwner,trip);}
        catch(MessagingException e){
            throw new RuntimeException();
        }
        try{ms.sendTripEmail(user,trip);}
        catch(MessagingException e){
            throw new RuntimeException();
        }

    }

    @Override
    public Proposal sendProposal(int tripId, int userid, String description) {
        Proposal prop = tripDao.createProposal(tripId, userid, description);
        Trip trip = tripDao.getTripById(tripId).get();
        try{
            ms.sendProposalEmail(userDao.getUserById(trip.getUserId()).get(),prop);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return prop;
    }

    @Override
    public Integer getTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate) {
        return tripDao.getTotalPages(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate);
    }

    @Override
    public List<Proposal> getProposalsForTripId(int tripId){
        return tripDao.getProposalsForTripId(tripId);
    }

    @Override
    public List<Trip> getAllActiveTripsByUserId(Integer userid){
        return tripDao.getAllActiveTripsByUserId(userid);
    }
    @Override
    public List<Trip> getAllProposedTripsByUserId(Integer userid){
        return tripDao.getAllProposedTripsByUserId(userid);
    }
    @Override
    public List<Trip> getAllAcceptedTripsByUserId(Integer userid){
        return tripDao.getAllAcceptedTripsByUserId(userid);
    }
    @Override
    public List<Trip> getAllUnproposedTripsByUserId(Integer userid){
        return tripDao.getAllUnproposedTripsByUserId(userid);
    }

    @Override
    public Optional<Trip> getTripByIdAndUserId(int tripid, int userid){
        return tripDao.getTripByIdAndUserId(tripid, userid);
    }


}
