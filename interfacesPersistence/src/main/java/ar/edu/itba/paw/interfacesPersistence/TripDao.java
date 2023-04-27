package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TripDao {
    Trip create(int userid,
                String licensePlate,
                int availableWeight,
                int availableVolume,
                LocalDateTime departureDate,
                LocalDateTime arrivalDate,
                String origin,
                String destination,
                String type,
                int price);

    Proposal createProposal(int tripid, int userid, String description);

    List<Proposal> getProposalsForTripId(int tripid);

    Optional<Proposal> getProposalById(int proposalId);

    List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag);

    Integer getTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate);

    Optional<Trip> getTripById(int tripid);

    List<Trip> getAllActiveTripsByUserId(Integer userid);
    void acceptTrip(int proposalId);
}
