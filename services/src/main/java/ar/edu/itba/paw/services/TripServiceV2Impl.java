package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.models.Pair;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfacesServices.exceptions.ProposalNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TripServiceV2Impl implements TripServiceV2 {
    private final TripDaoV2 tripDaoV2;

    private final UserDao userDao;

    private final MailService ms;

    @Autowired
    public TripServiceV2Impl(TripDaoV2 tripDaoV2, UserDao userDao, MailService ms){
        this.tripDaoV2 = tripDaoV2;
        this.userDao = userDao;
        this.ms = ms;
    }

    @Override
    public Trip createTrip(int truckerId,
                           String licensePlate,
                           int weight,
                           int volume,
                           LocalDateTime departureDate,
                           LocalDateTime arrivalDate,
                           String origin,
                           String destination,
                           String type,
                           int price) {
        return tripDaoV2.createTrip(truckerId, licensePlate, weight, volume, departureDate, arrivalDate, origin, destination, type, price);
    }

    @Override
    public Trip createRequest(int providerId,
                              int weight,
                              int volume,
                              LocalDateTime departureDate,
                              LocalDateTime arrivalDate,
                              String origin,
                              String destination,
                              String type,
                              int price) {
        return tripDaoV2.createRequest(providerId, weight, volume, departureDate, arrivalDate, origin, destination, type, price);
    }

    @Override
    public void confirmTrip(int tripId, int userId) {
        tripDaoV2.confirmTrip(tripId, userId);
        //TODO: ENVIAR EMAIL A AMBOS CON EL NUEVO STATUS DEL VIAJE
    }

    @Override
    public Proposal createProposal(int tripId, int userId, String description) {
        Proposal proposal = tripDaoV2.createProposal(tripId, userId, description);
        Trip trip = tripDaoV2.getTripOrRequestById(tripId).orElseThrow(NoSuchElementException::new);
        System.out.println("LLEGUE ACA 1");

        Integer uid;
        if(trip.getTruckerId() > 0)
            uid = trip.getTruckerId();
        else
            uid = trip.getProviderId();

        try{
            ms.sendProposalEmail(userDao.getUserById(uid).orElseThrow(NoSuchElementException::new), proposal);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return proposal;
    }

    @Override
    public void acceptProposal(int proposalId) {
        //TODO: AGARRAR EXCEPTION EN EL CONTROLLER.
        Proposal proposal = tripDaoV2.getProposalById(proposalId).orElseThrow(ProposalNotFoundException::new);
        tripDaoV2.acceptProposal(proposal);

        Trip trip = tripDaoV2.getTripOrRequestById(proposal.getTripId()).orElseThrow(ProposalNotFoundException::new);

        User trucker = userDao.getUserById(trip.getTruckerId()).orElseThrow(ProposalNotFoundException::new);
        User provider = userDao.getUserById(trip.getProviderId()).orElseThrow(ProposalNotFoundException::new);

        try{ms.sendTripEmail(trucker, provider,trip);}
        catch(MessagingException e){
            throw new RuntimeException();
        }
        try{ms.sendTripEmail(provider, trucker,trip);}
        catch(MessagingException e){
            throw new RuntimeException();
        }

    }

    @Override
    public List<Proposal> getAllProposalsForTripId(int tripId) {
        return tripDaoV2.getAllProposalsForTripId(tripId);
    }

    @Override
    public Optional<Proposal> getProposalById(int proposalId) {
        return tripDaoV2.getProposalById(proposalId);
    }

    @Override
    public List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {
        return tripDaoV2.getAllActiveTrips(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, pag);
    }

    @Override
    public List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {
        return tripDaoV2.getAllActiveRequests(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, pag);
    }

    @Override
    public List<Trip> getAllActiveTripsAndRequestsByUserId(Integer userId) {
        return tripDaoV2.getAllActiveTripsAndRequestsByUserId(userId);
    }

    @Override
    public Optional<Trip> getTripOrRequestById(int tripId) {
        return tripDaoV2.getTripOrRequestById(tripId);
    }

    @Override
    public List<Pair<Trip, Integer>> getAllActiveTripsOrRequestsAndProposalsCount(Integer userId){
        return tripDaoV2.getAllActiveTripsOrRequestAndProposalsCount(userId);
    }

    @Override
    public List<Trip> getAllAcceptedTripsAndRequestsByUserId(Integer userId){
        return tripDaoV2.getAllAcceptedTripsAndRequestsByUserId(userId);
    }

    @Override
    public Optional<Trip> getTripOrRequestByIdAndUserId(int id, int userid){
        return tripDaoV2.getTripOrRequestByIdAndUserId(id, userid);
    }

//    @Async
//    @Scheduled(cron = "0 0 0 * * ?") // runs every day
//    protected void cleanTrips(){
//        System.out.println("Cleaning Trips");
//        tripDaoV2.cleanExpiredTripsAndItsProposals();
//        System.out.println("Cleaning Finished");
//    }

}
