package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Trip;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TripDao {
    Trip create(int userid,
                       String licensePlate,
                       int availableWeight,
                       int availableVolume,
                       LocalDateTime departureDate,
                       LocalDateTime arrivalDate,
                       String origin,
                       String destination,
                       String type);
    List<Trip> getAllTrips();
    Trip getTripById(int tripid);
}
