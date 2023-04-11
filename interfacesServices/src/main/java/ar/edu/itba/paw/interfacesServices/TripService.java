package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Trip;

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
                    Date departureDate,
                    Date arrivalDate,
                    String origin,
                    String destination,
                    String type);

    List<Trip> getAllTrips();
}
