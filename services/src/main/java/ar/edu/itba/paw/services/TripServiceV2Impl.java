package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.ImageDao;
import ar.edu.itba.paw.interfacesPersistence.TripDaoV2;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfacesServices.exceptions.ProposalNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TripServiceV2Impl implements TripServiceV2 {

    Logger LOGGER = LoggerFactory.getLogger(TripServiceV2Impl.class);

    private final TripDaoV2 tripDaoV2;

    private final UserDao userDao;

    private final ImageDao imageDao;

    private final MailService ms;

    @Autowired
    public TripServiceV2Impl(TripDaoV2 tripDaoV2, UserDao userDao, ImageDao imageDao, MailService ms) {
        this.tripDaoV2 = tripDaoV2;
        this.userDao = userDao;
        this.imageDao = imageDao;
        this.ms = ms;
    }

    @Transactional
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

    @Transactional
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

    @Transactional
    @Override
    public void confirmTrip(int tripId, int userId) {
        tripDaoV2.confirmTrip(tripId, userId);
        Trip trip = tripDaoV2.getTripOrRequestById(tripId).orElseThrow(NoSuchElementException::new);
        User trucker = userDao.getUserById(trip.getTruckerId()).orElseThrow(NoSuchElementException::new);
        User provider = userDao.getUserById(trip.getProviderId()).orElseThrow(NoSuchElementException::new);
        if(trip.getProvider_confirmation()){
            ms.sendCompletionEmail(trucker, trip);
            ms.sendCompletionEmail(provider,trip);
        }else{
            ms.sendStatusEmail(trucker, trip);
            ms.sendStatusEmail(provider,trip);
        }
    }

    @Transactional
    @Override
    public Proposal createProposal(int tripId, int userId, String description) {
        Proposal proposal = tripDaoV2.createProposal(tripId, userId, description);
        Trip trip = tripDaoV2.getTripOrRequestById(tripId).orElseThrow(NoSuchElementException::new);
        LOGGER.debug("Trip: " + trip.toString() +", Proposal: " + proposal.toString());

        Integer uid;
        System.out.println(trip.getTruckerId());
        if(trip.getTruckerId() > 0)
            uid = trip.getTruckerId();
        else
            uid = trip.getProviderId();

        ms.sendProposalEmail(userDao.getUserById(uid).orElseThrow(NoSuchElementException::new), proposal);
        return proposal;
    }

    @Transactional
    @Override
    public void acceptProposal(int proposalId) {
        //TODO: AGARRAR EXCEPTION EN EL CONTROLLER.
        Proposal proposal = tripDaoV2.getProposalById(proposalId).orElseThrow(ProposalNotFoundException::new);
        tripDaoV2.acceptProposal(proposal);

        Trip trip = tripDaoV2.getTripOrRequestById(proposal.getTripId()).orElseThrow(ProposalNotFoundException::new);

        User trucker = userDao.getUserById(trip.getTruckerId()).orElseThrow(ProposalNotFoundException::new);
        User provider = userDao.getUserById(trip.getProviderId()).orElseThrow(ProposalNotFoundException::new);

        ms.sendTripEmail(trucker, provider,trip);
        ms.sendTripEmail(provider, trucker,trip);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Proposal> getAllProposalsForTripId(int tripId) {
        return tripDaoV2.getAllProposalsForTripId(tripId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Proposal> getProposalById(int proposalId) {
        return tripDaoV2.getProposalById(proposalId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {
        return tripDaoV2.getAllActiveTrips(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, pag);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag) {
        return tripDaoV2.getAllActiveRequests(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, pag);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getActiveTripsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate) {
        return tripDaoV2.getActiveTripsTotalPages(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, departureDate, arrivalDate);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getActiveRequestsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate) {
        return tripDaoV2.getActiveRequestsTotalPages(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, departureDate, arrivalDate);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Trip> getTripOrRequestById(int tripId) {
        return tripDaoV2.getTripOrRequestById(tripId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllActiveTripsOrRequestsAndProposalsCount(Integer userId, Integer pag){
        return tripDaoV2.getAllActiveTripsOrRequestAndProposalsCount(userId, pag);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllAcceptedTripsAndRequestsByUserId(Integer userId, Integer pag){
        return tripDaoV2.getAllAcceptedTripsAndRequestsByUserId(userId, pag);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getTotalPagesActiveTripsOrRequests(Integer userid) {
        return tripDaoV2.getTotalPagesActiveTripsOrRequests(userid);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getTotalPagesAcceptedTripsAndRequests(Integer userid) {
        return tripDaoV2.getTotalPagesAcceptedTripsAndRequests(userid);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Trip> getTripOrRequestByIdAndUserId(int id, int userid){
        return tripDaoV2.getTripOrRequestByIdAndUserId(id, userid);
    }

    @Override
    public void updateTripPicture(Integer userId, Integer imageId) {
        tripDaoV2.setImageId(userId, imageId);
    }

    @Override
    public byte[] getTripPicture(Integer userId) {
        return imageDao.getImage(tripDaoV2.getImageId(userId)).get().getImage();
    }

//    @Async
//    @Scheduled(cron = "0 0 0 * * ?") // runs every day
//    protected void cleanExpiredTrips(){
//        System.out.println("CLEANING EXPIRED TRIPS");
//        tripDaoV2.cleanExpiredTripsAndItsProposals();
//        System.out.println("CLEANING FINISHED");
//    }
//
//
//    @Async
//    @Scheduled(cron = "0 0 0 * * ?") // runs every day
//    protected void confirmTripsWithoutProviderConfirmation(){
//        System.out.println("CONFIRMING TRIPS WITHOUT PROVIDER CONFIRMATION");
//
//        List<Trip> trips = tripDaoV2.getTripsWithPendingProviderConfirmation();
//
//        for(Trip trip : trips)
//            if(Duration.between(trip.getConfirmation_date(), LocalDateTime.now()).toDays() >= 10)
//                tripDaoV2.confirmTrip(trip.getTripId(), trip.getProviderId());
//
//        System.out.println("CONFIRMATION FINISHED");
//    }

}
