package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface TripServiceV2 {
    Trip createTrip(int truckerId,
                    String licensePlate,
                    int weight,
                    int volume,
                    LocalDateTime departureDate,
                    LocalDateTime arrivalDate,
                    String origin,
                    String destination,
                    String type,
                    int price);

    Trip createRequest(int providerId,
                       int weight,
                       int volume,
                       LocalDateTime departureDate,
                       LocalDateTime arrivalDate,
                       String origin,
                       String destination,
                       String type,
                       int price,
                       Locale locale);

    void confirmTrip(int tripId, int userId,Locale locale);

    Proposal createProposal(int tripId, int userId, String description, int price, Locale locale);

    List<Proposal> getAllProposalsForTripId(int tripId);

    Optional<Proposal> getProposalById(int proposalId);

    List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String type, Integer pag);

    List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String type, Integer pag);

    Integer getActiveTripsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate, String type);

    Integer getActiveRequestsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate, String type);

    Optional<Trip> getTripOrRequestById(int tripid);

    void acceptProposal(int proposalId, Locale locale);

    List<Trip> getAllActiveTripsOrRequestsAndProposalsCount(Integer userId, Integer pag);

    List<Trip> getAllAcceptedTripsAndRequestsByUserId(Integer userId, Integer pag);

    Integer getTotalPagesActiveTripsOrRequests(Integer userid);

    Integer getTotalPagesAcceptedTripsAndRequests(Integer userid);

    Optional<Trip> getTripOrRequestByIdAndUserId(int id, User user);


    List<Trip> getAllActivePublications(Integer userId, Integer pag);


    List<Trip> getAllExpiredPublications(Integer userId, Integer pag);


    Integer getTotalPagesExpiredPublications(User user);

    Integer getTotalPagesActivePublications(User user);

    List<Trip> getAllOngoingPublications(Integer userId);

    void updateTripPicture(Integer userId, Integer imageId);

    byte[] getTripPicture(Integer userId);

    void deleteOffer(int offerId);

    List<Trip> getAllOngoingTrips(Integer userId);

    List<Trip> getAllPastTrips(Integer userId);

    List<Trip> getAllFutureTrips(Integer userId);

    Integer getCompletedTripsCount(Integer userId);

    Optional<Proposal> getOffer(User user, Trip trip);
    Optional<Proposal> sendCounterOffer(Integer originalId, User user, String description, Integer price);
    void rejectCounterOffer(Integer offerId);

    void acceptCounterOffer(Integer offerId);

    void deleteCounterOffer(Integer offerId);
}
