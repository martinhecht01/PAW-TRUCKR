package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.*;
import ar.edu.itba.paw.interfacesServices.AlertService;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.interfacesServices.exceptions.ProposalNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

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
        User user = userDao.getUserById(truckerId).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.createTrip(user, licensePlate, weight, volume, Timestamp.valueOf(departureDate), Timestamp.valueOf(arrivalDate), origin, destination, type, price);
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
        User user = userDao.getUserById(providerId).orElseThrow(NoSuchElementException::new);
        Trip trip =  tripDaoV2.createRequest(user, weight, volume, Timestamp.valueOf(departureDate), Timestamp.valueOf(arrivalDate), origin, destination, type, price);

        List<Alert> alerts = alertDao.getAlertsThatMatch(trip);

        System.out.println("ALERTS THAT MATCH = " + alerts.size());

        //TODO: enviar mail a los usuarios que tienen alertas que matchean con el trip

        return trip;
    }

    @Transactional
    @Override
    public void confirmTrip(int tripId, int userId) {
        Trip trip = tripDaoV2.getTripOrRequestById(tripId).orElseThrow(NoSuchElementException::new);
        User user = userDao.getUserById(userId).orElseThrow(NoSuchElementException::new);
        tripDaoV2.confirmTrip(trip, user);
        //Trip trip = tripDaoV2.getTripOrRequestById(tripId).orElseThrow(NoSuchElementException::new);
        User trucker = trip.getTrucker();
        User provider = trip.getProvider();
        if(trip.getProviderConfirmation()){
            ms.sendCompletionEmail(trucker, trip);
            ms.sendCompletionEmail(provider,trip);
        }else{
            ms.sendStatusEmail(trucker, trip);
            ms.sendStatusEmail(provider,trip);
        }
    }

    @Transactional
    @Override
    public Proposal createProposal(int tripId, int userId, String description, int price) {
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

        ms.sendProposalEmail(user, proposal);
        return proposal;
    }

    @Transactional
    @Override
    public void acceptProposal(int proposalId) {

        Proposal proposal = tripDaoV2.getProposalById(proposalId).orElseThrow(ProposalNotFoundException::new);
        tripDaoV2.acceptProposal(proposal);

        Trip trip = proposal.getTrip();
        // tripDaoV2.getTripOrRequestById(proposal.getTripId()).orElseThrow(ProposalNotFoundException::new);

        User trucker = trip.getTrucker();
                //userDao.getUserById(trip.getTruckerId()).orElseThrow(ProposalNotFoundException::new);
        User provider = trip.getProvider();
                //userDao.getUserById(trip.getProviderId()).orElseThrow(ProposalNotFoundException::new);

        ms.sendTripEmail(trucker, provider,trip);
        ms.sendTripEmail(provider, trucker,trip);
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
    public List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String type,  Integer pag) {
        return tripDaoV2.getAllActiveTrips(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, type, pag);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String type, Integer pag) {
        return tripDaoV2.getAllActiveRequests(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate, type, pag);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getActiveTripsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate, String type) {
        return tripDaoV2.getActiveTripsTotalPages(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, departureDate, arrivalDate, type);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getActiveRequestsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate, String type) {
        return tripDaoV2.getActiveRequestsTotalPages(origin, destination, minAvailableVolume, minAvailableWeight, minPrice, maxPrice, departureDate, arrivalDate, type);
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
        User user = userDao.getUserById(userid).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getTotalPagesActiveTripsOrRequests(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getTotalPagesAcceptedTripsAndRequests(Integer userid) {
        User user = userDao.getUserById(userid).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getTotalPagesAcceptedTripsAndRequests(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Trip> getTripOrRequestByIdAndUserId(int id, User user){
        Optional<Trip> optionalTrip = tripDaoV2.getTripOrRequestById(id);

        if(!optionalTrip.isPresent())
            return Optional.empty();

        Trip trip = optionalTrip.get();
        //si esta aceptado y es usuario anonimo devuelvo empty
        if(trip.getProvider() != null && trip.getTrucker() != null && user == null)
            return Optional.empty();

        //si es usuario anonimo y alguno de los dos es null (no aceptado) lo devuelvo
        if(user == null && (trip.getProvider() == null || trip.getTrucker() == null))
            return optionalTrip;

        if(user != null && ((trip.getTrucker() != null && !Objects.equals(trip.getTrucker().getUserId(), user.getUserId())) && (trip.getProvider() != null && !Objects.equals(trip.getProvider().getUserId(), user.getUserId())))){
            return Optional.empty();
        }

        optionalTrip.get().setReview(reviewDao.getReviewByTripAndUserId(trip, Objects.equals(user.getUserId(), trip.getTrucker() == null ? null : trip.getTrucker().getUserId()) ? trip.getProvider() : trip.getTrucker()).orElse(null));
        optionalTrip.get().setOffer(tripDaoV2.getOffer(user, optionalTrip.get()).orElse(null));
        return optionalTrip;
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
    public Integer getTotalPagesExpiredPublications(User user){
        return tripDaoV2.getTotalPagesExpiredPublications(user);
    }
    @Transactional
    @Override
    public Integer getTotalPagesActivePublications(User user){
        return tripDaoV2.getTotalPagesActivePublications(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllOngoingPublications(Integer userId){
        return tripDaoV2.getAllOngoingPublications(userId);
    }


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

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllOngoingTrips(Integer userId) {
        User user = userDao.getUserById(userId).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getAllOngoingTrips(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllPastTrips(Integer userId) {
        User user = userDao.getUserById(userId).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getAllPastTrips(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trip> getAllFutureTrips(Integer userId) {
        User user = userDao.getUserById(userId).orElseThrow(NoSuchElementException::new);
        return tripDaoV2.getAllFutureTrips(user);
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

}
