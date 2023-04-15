package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Trip;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TripService {
    Trip createTrip(
                    String email,
                    String name,
                    String id,
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

    List<Trip> getAllActiveTrips(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate);

    Trip getTripById(int tripid);

    Trip acceptTrip(Trip trip, int acceptUserId);
}
