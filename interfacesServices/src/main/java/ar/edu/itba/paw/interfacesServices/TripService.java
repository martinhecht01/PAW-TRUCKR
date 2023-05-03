package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.Pair;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TripService {
    Trip createTrip(String cuit,
                    String licensePlate,
                    int availableWeight,
                    int availableVolume,
                    LocalDateTime departureDate,
                    LocalDateTime arrivalDate,
                    String origin,
                    String destination,
                    String type,
                    int price
    );

    List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag);

    Optional<Trip> getTripById(int tripid);

    Optional<Trip> getTripByIdAndUserId(int tripid, int userid);

    void acceptTrip(int proposalId);

    List<Proposal> getProposalsForTripId(int tripId);

    List<Pair<Trip, Integer>> getAllActiveTripsAndProposalCount(Integer userid);

    List<Trip> getAllActiveTripsByUserId(Integer userid);

    Proposal sendProposal(int tripId, int userid, String description) throws MessagingException;

    Integer getTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate);

    List<Trip> getAllProposedTripsByUserId(Integer userid);
    List<Trip> getAllAcceptedTripsByUserId(Integer userid);

}
