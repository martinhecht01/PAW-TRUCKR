package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TripDaoV2 {
    Trip createTrip(User trucker,
                    String licensePlate,
                    int weight,
                    int volume,
                    Timestamp departureDate,
                    Timestamp arrivalDate,
                    String origin,
                    String destination,
                    String type,
                    int price);

    Trip createRequest(User provider,
                       int weight,
                       int volume,
                       Timestamp departureDate,
                       Timestamp arrivalDate,
                       String origin,
                       String destination,
                       String type,
                       int price);

    void confirmTrip(Trip trip, User user);

    Proposal createProposal(Trip trip, User user, String description);

   List<Proposal> getAllProposalsForTripId(int tripId, int pag);

    Integer getActiveTripsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate);

    Integer getActiveRequestsTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String departureDate, String arrivalDate);

    Optional<Proposal> getProposalById(int proposalId);

    List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag);

    List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag);

    Optional<Trip> getTripOrRequestById(int tripid);

    void acceptProposal(Proposal proposal);

    List<Trip> getAllActiveTripsOrRequestAndProposalsCount(Integer userid, Integer pag);

    List<Trip> getAllAcceptedTripsAndRequestsByUserId(Integer userid, Integer pag);

    Integer getTotalPagesActiveTripsOrRequests(Integer userid);

    Integer getTotalPagesAcceptedTripsAndRequests(Integer userid);

    Optional<Trip> getTripOrRequestByIdAndUserId(int id, int userid);

    void setImage(Trip trip, Image image);

    Image getImage(int tripId);
}
