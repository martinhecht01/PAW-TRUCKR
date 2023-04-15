package ar.edu.itba.paw.interfacesPersistence;

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
                       String type);
    List<Trip> getAllActiveTrips();
    Optional<Trip> getTripById(int tripid);
    Trip acceptTrip(Trip trip, int acceptUserId);
}
