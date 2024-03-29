package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TripDaoV2 {
    Trip createTrip(User trucker,
                    String licensePlate,
                    int weight,
                    int volume,
                    LocalDateTime departureDate,
                    LocalDateTime arrivalDate,
                    String origin,
                    String destination,
                    String type,
                    int price);

    Trip createRequest(User provider,
                       int weight,
                       int volume,
                       LocalDateTime departureDate,
                       LocalDateTime arrivalDate,
                       String origin,
                       String destination,
                       String type,
                       int price);

    void confirmTrip(Trip trip, User user);

    Proposal createProposal(Trip trip, User user, String description, Integer price);

    List<Proposal> getAllProposalsForTripId(int tripId, int pag, int pagesize);


    //PAGINACION
    Integer getActiveTripsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate, String type);

    //PAGINACION
    Integer getActiveRequestsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate, String type);

    Optional<Proposal> getProposalById(int proposalId);

    Optional<Trip> getTripOrRequestById(int tripid);

    List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String type, Integer pag);

    List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, String type, Integer pag);

    void acceptProposal(Proposal proposal);

    List<Trip> getAllActiveTripsOrRequestAndProposalsCount(Integer userid, Integer pag);

    List<Trip> getAllActivePublications(Integer userId, Integer pag);

    Integer getTotalPagesExpiredPublications(User user);

    Integer getTotalPagesAllPastTrips(User user);

    Integer getTotalPagesActivePublications(User user);

    List<Trip> getAllExpiredPublications(Integer userId, Integer pag);

    Integer getTotalPagesAllOngoingTrips(User user);

    List<Trip> getAllOngoingTrips(User user, Integer page);

    List<Trip> getAllOngoingPublications(Integer userId);

    List<Trip> getAllAcceptedTripsAndRequestsByUserId(Integer userid, Integer pag);

    Integer getTotalPagesActiveTripsOrRequests(User user);

    Integer getTotalPagesAcceptedTripsAndRequests(User user);

    Optional<Trip> getTripOrRequestByIdAndUserId(Trip trip, User user);

    void setImage(Trip trip, Image image);

    Image getImage(int tripId);

    void deleteOffer(Proposal proposal);


    List<Trip> getAllPastTrips(User user);

    List<Trip> getAllFutureTrips(User user, Integer page);

    Integer getTotalPagesAllFutureTrips(User user);

    Integer getCompletedTripsCount(User user);

    Optional<Proposal> getOffer(User user, Trip trip);

    Optional<Proposal> sendCounterOffer(Proposal original, User user, String description, Integer price);

    Proposal createCounterProposal(Trip trip, User user, String description, Integer price, Proposal parentProposal);

    void acceptCounterOffer(Proposal counterProposal);

    void rejectCounterOffer(Proposal counterOffer);

    void deleteCounterOffer(Proposal counterOffer);

    Integer getProposalsCountForTripId(int tripId);

    Integer getProposalsCountForUserId(int userId);

    void deletePublication(Trip publication);

    List<Proposal> getAllProposalsForUser(int userId, int page, int pageSize);
}
