package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Request;
import ar.edu.itba.paw.models.Trip;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RequestDao {
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

    Request create(int userid,
                   int availableWeight,
                   int availableVolume,
                   LocalDateTime departureDate,
                   LocalDateTime arrivalDate,
                   String origin,
                   String destination,
                   String type,
                   int price);

    List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag);

    Integer getTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate);

    Optional<Trip> getTrById(int tripid);

    List<Trip> getAllActiveTripsByUserId(Integer userid);
    Trip acceptTrip(Trip trip, int acceptUserId);

    List<Request> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag);
}
