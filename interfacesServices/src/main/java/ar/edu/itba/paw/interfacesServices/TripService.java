package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Trip;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
                    String type);

    List<Trip> getAllActiveTrips();

    Optional<Trip> getTripById(int tripid);

    Trip acceptTrip(int tripId,String email, String name, String cuit );
}
