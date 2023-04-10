package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Trip;

import java.util.Date;

public interface TripService {
    Trip createTrip(
                    int userId,
                    String licensePlate,
                    int availableWeight,
                    int availableVolume,
                    Date departureDate,
                    Date arrivalDate,
                    String origin,
                    String destination,
                    String type);
}
