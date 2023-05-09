package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Pair;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;

import java.time.LocalDateTime;
import java.util.List;
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
                       int price);

    void confirmTrip(int tripId, int userId);

    Proposal createProposal(int tripId, int userId, String description);

    List<Proposal> getAllProposalsForTripId(int tripId);

    Optional<Proposal> getProposalById(int proposalId);

    List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag);

    List<Trip> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag);

    List<Trip> getAllActiveTripsAndRequestsByUserId(Integer userId);

    Optional<Trip> getTripById(int tripid);

    void acceptProposal(int proposalId);

    List<Pair<Trip, Integer>> getAllActiveTripsOrRequestsAndProposalsCount(Integer userId);

    List<Trip> getAllAcceptedTripsAndRequestsByUserId(Integer userId);

    Optional<Trip> getTripOrRequestByIdAndUserId(int id, int userid);
}
