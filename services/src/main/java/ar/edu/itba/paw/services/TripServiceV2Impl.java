package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.*;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.interfacesServices.exceptions.ProposalNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TripServiceV2Impl implements TripServiceV2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripServiceV2Impl.class);

    private final TripDaoV2 tripDaoV2;

    private final UserDao userDao;

    private final ImageDao imageDao;

    private final ReviewDao reviewDao;

    private final MailService ms;

    private final AlertDao alertDao;

    @Autowired
    public TripServiceV2Impl(TripDaoV2 tripDaoV2, UserDao userDao, @Qualifier("imageDaoJPA") ImageDao imageDao, MailService ms, ReviewDao reviewDao, AlertDao alertDao) {
        this.tripDaoV2 = tripDaoV2;
        this.userDao = userDao;
        this.imageDao = imageDao;
        this.reviewDao = reviewDao;
        this.ms = ms;
        this.alertDao = alertDao;
    }


    @Transactional
    @Override
    public Trip createTrip(User user,
                           String licensePlate,
                           int weight,
                           int volume,
                           LocalDateTime departureDate,
                           LocalDateTime arrivalDate,
                           String origin,
                           String destination,
                           String type,
                           int price) {

        if(Objects.equals(user.getRole(), "TRUCKER")) {
            if(licensePlate == null || licensePlate.isEmpty())
                throw new IllegalArgumentException("exception.LicensePlate");
            return tripDaoV2.createTrip(user, licensePlate, weight, volume, departureDate, arrivalDate, origin, destination, type, price);
        }else
            return createRequest(user, weight, volume, departureDate, arrivalDate, origin, destination, type, price, user.getLocale());
    }

    @Transactional
    @Override
    public Trip createRequest(User user,
                              int weight,
                              int volume,
                              LocalDateTime departureDate,
                              LocalDateTime arrivalDate,
                              String origin,
                              String destination,
                              String type,
                              int price,
                              Locale locale) {
        Trip trip =  tripDaoV2.createRequest(user, weight, volume, departureDate, arrivalDate, origin, destination, type, price);

        List<Alert> alerts = alertDao.getAlertsThatMatch(trip);

        for(Alert alert : alerts){
            ms.sendAlertEmail(alert.getUser(), trip,alert.getUser().getLocale());
        }

        return trip;
    }

    @Transactional
    @Override
    public Trip confirmTrip(int tripId, int userId,Locale locale) {
        Trip trip = tripDaoV2.getTripOrRequestById(tripId).orElseThrow(NoSuchElementException::new);
        User user = userDao.getUserById(userId).orElseThrow(NoSuchElementException::new);
        tripDaoV2.confirmTrip(trip, user);
        //Trip trip = tripDaoV2.getTripOrRequestById(tripId).orElseThrow(NoSuchElementException::new);
        User trucker = trip.getTrucker();
        User provider = trip.getProvider();
        if(trip.getProviderConfirmation()){
            ms.sendCompletionEmail(trucker, trip,locale);
            ms.sendCompletionEmail(provider,trip,locale);
        }else{
            ms.sendStatusEmail(trucker, trip,locale);
            ms.sendStatusEmail(provider,trip,locale);
        }
        return trip;
    }

    @Transactional
    @Override
    public Proposal createProposal(int tripId, int userId, String description, int price, Locale locale) {
        Trip trip = tripDaoV2.getTripOrRequestById(tripId).orElseThrow(NoSuchElementException::new);
        User user = userDao.getUserById(userId).orElseThrow(NoSuchElementException::new);
        Proposal proposal = tripDaoV2.createProposal(trip, user, description, price);
        //Trip trip = tripDaoV2.getTripOrRequestById(tripId).orElseThrow(NoSuchElementException::new);
        LOGGER.debug("Trip: " + trip.toString() +", Proposal: " + proposal.toString());

        //User user;
        if(trip.getTrucker() != null)
            user = trip.getTrucker();
        else
            user = trip.getProvider();

        ms.sendProposalEmail(user, proposal, user.getLocale());
        return proposal;
    }

    @Transactional
    @Override
    public void acceptProposal(int proposalId, Locale locale) {

        Proposal proposal = tripDaoV2.getProposalById(proposalId).orElseThrow(ProposalNotFoundException::new);
        tripDaoV2.acceptProposal(proposal);

        Trip trip = proposal.getTrip();
        // tripDaoV2.getTripOrRequestById(proposal.getTripId()).orElseThrow(ProposalNotFoundException::new);

        User trucker = trip.getTrucker();
                //userDao.getUserById(trip.getTruckerId()).orElseThrow(ProposalNotFoundException::new);
        User provider = trip.getProvider();
                //userDao.getUserById(trip.getProviderId()).orElseThrow(ProposalNotFoundException::new);

        ms.sendTripEmail(trucker, provider,trip,locale);
        ms.sendTripEmail(provider, trucker,trip, locale);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Proposal> getAllProposalsForTripId(int tripId) {
        Trip trip = tripDaoV2.getTripOrRequestById(tripId).orElseThrow(NoSuchElementException::new);
        return trip.getProposals();
        //return tripDaoV2.getAllProposalsForTripId(tripId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Proposal> getProposalById(int proposalId) {
        return tripDaoV2.getProposalById(proposalId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllActiveTripsOrRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String cargoType, String tripType,  Integer page, Integer pageSize) {
        tripType = tripType.toLowerCase();
        if(tripType.equals("trip"))
            return tripDaoV2.getAllActiveTrips(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, cargoType, page);
        else if(tripType.equals("request"))
            return tripDaoV2.getAllActiveRequests(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, cargoType, page);
        else
            throw new IllegalArgumentException("The tripType must be either trip or request");
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getActiveTripsOrRequestsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate, String cargoType, String tripType) {
        tripType = tripType.toLowerCase();
        if (tripType.equals("trip"))
            return tripDaoV2.getActiveTripsTotalPages(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, departureDate, arrivalDate, cargoType);
        else if (tripType.equals("request"))
            return tripDaoV2.getActiveRequestsTotalPages(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, departureDate, arrivalDate, cargoType);
        else
            throw new IllegalArgumentException("The tripType must be either trip or request");
    }



//    @Transactional(readOnly = true)
//    @Override
//    public List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String type,  Integer page, Integer pageSize) {
//        return tripDaoV2.getAllActiveTrips(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, type, page);
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String type, Integer pag) {
//        return tripDaoV2.getAllActiveRequests(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, type, pag);
//    }

//    @Transactional(readOnly = true)
//    @Override
//    public Integer getActiveTripsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate, String type) {
//        return tripDaoV2.getActiveTripsTotalPages(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, departureDate, arrivalDate, type);
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public Integer getActiveRequestsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate, String type) {
//        return tripDaoV2.getActiveRequestsTotalPages(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, departureDate, arrivalDate, type);
//    }

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
        User user = userDao.getUserById(userid).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getTotalPagesActiveTripsOrRequests(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getTotalPagesAcceptedTripsAndRequests(Integer userid) {
        User user = userDao.getUserById(userid).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getTotalPagesAcceptedTripsAndRequests(user);
    }

//    @Transactional(readOnly = true)
//    @Override
//    public Optional<Trip> getTripOrRequestByIdAndUserId(int id, User user){
//        Optional<Trip> optionalTrip = tripDaoV2.getTripOrRequestById(id);
//
//        if(!optionalTrip.isPresent())
//            return Optional.empty();
//
//        Trip trip = optionalTrip.get();
//        //si esta aceptado y es usuario anonimo devuelvo empty
//        if(trip.getProvider() != null && trip.getTrucker() != null && user == null)
//            return Optional.empty();
//
//        //si es usuario anonimo y alguno de los dos es null (no aceptado) lo devuelvo
//        if(user == null && (trip.getProvider() == null || trip.getTrucker() == null))
//            return optionalTrip;
//
//        if(user != null && ((trip.getTrucker() != null && !Objects.equals(trip.getTrucker().getUserId(), user.getUserId())) && (trip.getProvider() != null && !Objects.equals(trip.getProvider().getUserId(), user.getUserId())))){
//            return Optional.empty();
//        }
//
//        optionalTrip.get().setReview(reviewDao.getReviewByTripAndUserId(trip, Objects.equals(user.getUserId(), trip.getTrucker() == null ? null : trip.getTrucker().getUserId()) ? trip.getProvider() : trip.getTrucker()).orElse(null));
//        optionalTrip.get().setOffer(tripDaoV2.getOffer(user, optionalTrip.get()).orElse(null));
//        return optionalTrip;
//    }

//    Get Publications

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getPublications(Integer userId, String status, Integer page){
        String st = status.toLowerCase();
        switch (st) {
            case "expired":
                return tripDaoV2.getAllExpiredPublications(userId, page);
            case "active":
                return tripDaoV2.getAllActivePublications(userId, page);
            default:
                return new ArrayList<>();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllActivePublications(Integer userId, Integer pag){
        return tripDaoV2.getAllActivePublications(userId,pag);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllExpiredPublications(Integer userId, Integer pag){
        return tripDaoV2.getAllExpiredPublications(userId, pag);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllOngoingPublications(Integer userId){
        return tripDaoV2.getAllOngoingPublications(userId);
    }

//    Get Pages:

    @Transactional(readOnly = true)
    @Override
    public Integer getTotalPagesPublications(User user, String status){
        String st = status.toLowerCase();
        switch (st) {
            case "expired":
                return tripDaoV2.getTotalPagesExpiredPublications(user);
            case "active":
                return tripDaoV2.getTotalPagesActivePublications(user);
            default:
                return 0;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getTotalPagesExpiredPublications(User user){
        return tripDaoV2.getTotalPagesExpiredPublications(user);
    }
    @Transactional
    @Override
    public Integer getTotalPagesActivePublications(User user){
        return tripDaoV2.getTotalPagesActivePublications(user);
    }

//   --------------


    @Transactional
    @Override
    public void updateTripPicture(Integer tripId, Integer imageId) {
        Trip trip = tripDaoV2.getTripOrRequestById(tripId).orElseThrow(NoSuchElementException::new);
        Image image = imageDao.getImage(imageId).orElseThrow(NoSuchElementException::new);
        tripDaoV2.setImage(trip, image);
    }
    
    @Transactional
    @Override
    public void deleteOffer(int offerId){
        Proposal offer = tripDaoV2.getProposalById(offerId).orElseThrow(ProposalNotFoundException::new);
        tripDaoV2.deleteOffer(offer);
    }

    @Transactional
    @Override
    public Integer getTotalPagesAllOngoingTrips(Integer userId){
        User user = userDao.getUserById(userId).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getTotalPagesAllOngoingTrips(user);
    }
    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllOngoingTrips(Integer userId,Integer pag) {
        User user = userDao.getUserById(userId).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getAllOngoingTrips(user,pag);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllPastTrips(Integer userId) {
        User user = userDao.getUserById(userId).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getAllPastTrips(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getTotalPagesAllFutureTrips(Integer userId){
        User user = userDao.getUserById(userId).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getTotalPagesAllFutureTrips(user);
    }


    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllFutureTrips(Integer userId, Integer page){
        User user = userDao.getUserById(userId).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getAllFutureTrips(user, page);
    }


    @Transactional(readOnly = true)
    @Override
    public byte[] getTripPicture(Integer tripId) {
        return imageDao.getImage(tripId).get().getImage();
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getCompletedTripsCount(Integer userId){
        User user = userDao.getUserById(userId).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getCompletedTripsCount(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Proposal> getOffer(User user, Trip trip){
        return tripDaoV2.getOffer(user, trip);
    }


    @Transactional
    @Override
    public Optional<Proposal> sendCounterOffer(Integer originalId, User user, String description, Integer price){
        Proposal original = tripDaoV2.getProposalById(originalId).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.sendCounterOffer(original, description, price);
    }

    @Transactional
    @Override
    public void rejectCounterOffer(Integer offerId){
        Proposal offer = tripDaoV2.getProposalById(offerId).orElseThrow(NoSuchElementException::new);
        tripDaoV2.rejectCounterOffer(offer);
    }

    @Transactional
    @Override
    public void acceptCounterOffer(Integer offerId){
        Proposal offer = tripDaoV2.getProposalById(offerId).orElseThrow(NoSuchElementException::new);
        tripDaoV2.acceptCounterOffer(offer);
    }

    @Transactional
    @Override
    public void deleteCounterOffer(Integer offerId){
        Proposal offer = tripDaoV2.getProposalById(offerId).orElseThrow(NoSuchElementException::new);
        tripDaoV2.deleteCounterOffer(offer);
    }

}
