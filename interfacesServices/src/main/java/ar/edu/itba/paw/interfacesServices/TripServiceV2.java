package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface TripServiceV2 {
    Trip createTrip(User user,
                    String licensePlate,
                    int weight,
                    int volume,
                    LocalDateTime departureDate,
                    LocalDateTime arrivalDate,
                    String origin,
                    String destination,
                    String type,
                    int price);

    Trip createRequest(User user,
                       int weight,
                       int volume,
                       LocalDateTime departureDate,
                       LocalDateTime arrivalDate,
                       String origin,
                       String destination,
                       String type,
                       int price,
                       Locale locale);

    Trip confirmTrip(int tripId, User user,Locale locale);

    Proposal createProposal(int tripId, User user, String description, int price, Integer parentOfferId, Locale locale);

    List<Proposal> getAllProposalsForTripId(int tripId,Integer page, Integer pageSize);

    List<Proposal> findOffers(Integer tripId, Integer userId, Integer page, Integer pageSize);


    Integer getProposalCountForTripId(int tripId);

    Integer getProposalCountForUserId(int userId);

    Integer findOfferCount(Integer tripId, Integer userId);

    Optional<Proposal> getProposalById(int proposalId);

    List<Trip> getAllActiveTripsOrRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String cargoType, String tripType, Integer page, Integer pageSize);

    Integer getActiveTripsOrRequestsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate, String cargoType, String tripType);

    Optional<Trip> getTripOrRequestById(int tripid);

    void actOnOffer(int proposalId, String action, Locale locale);

    List<Trip> getTrips(Integer userId, String status, Integer page, Integer pageSize);

    Integer getTotalPagesTrips(User user, String status);

    Integer getTotalPagesAllPastTrips(Integer userId);

    List<Trip> getAllActiveTripsOrRequestsAndProposalsCount(Integer userId, Integer pag);

    List<Trip> getAllAcceptedTripsAndRequestsByUserId(Integer userId, Integer pag);

    Integer getTotalPagesActiveTripsOrRequests(Integer userid);

    Integer getTotalPagesAcceptedTripsAndRequests(Integer userid);

    List<Trip> getPublications(Integer userId, String status, Integer page);

    List<Trip> getAllActivePublications(Integer userId, Integer pag);

    List<Trip> getAllExpiredPublications(Integer userId, Integer pag);

    Integer getTotalPagesExpiredPublications(User user);

    Integer getTotalPagesPublications(User user, String status);

    Integer getTotalPagesActivePublications(User user);

    List<Trip> getAllOngoingPublications(Integer userId);

    void updateTripPicture(Integer tripId, Integer imageId);

    byte[] getTripPicture(Integer userId);

    void deleteOffer(int offerId);

    Integer getTotalPagesAllOngoingTrips(Integer userId);

    List<Trip> getAllOngoingTrips(Integer userId,Integer pag);

    List<Trip> getAllPastTrips(Integer userId);

    List<Trip> getAllFutureTrips(Integer userId, Integer page);

    Integer getTotalPagesAllFutureTrips(Integer userId);

    Integer getCompletedTripsCount(Integer userId);

    Optional<Proposal> getOffer(User user, Trip trip);

    Proposal sendCounterOffer(Integer originalId, User user, String description, Integer price);

    void rejectCounterOffer(Integer offerId);

    void acceptCounterOffer(Integer offerId);

    void deleteCounterOffer(Integer offerId);

    Trip getTripOrRequestByIdAndUserId(Integer id, User user);
}
