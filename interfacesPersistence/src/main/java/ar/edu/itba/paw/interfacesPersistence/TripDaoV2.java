package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Pair;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TripDaoV2 {
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
                       int price);

    void confirmTrip(int tripId, int userId);

    Proposal createProposal(int tripId, int userId, String description);

    List<Proposal> getAllProposalsForTripId(int tripId);

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

    void setImageId(int tripId, int imageId);

    int getImageId(int tripId);
}
