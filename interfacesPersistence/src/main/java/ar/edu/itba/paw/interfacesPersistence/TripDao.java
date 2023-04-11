package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Trip;

import java.util.Date;
import java.util.List;

public interface TripDao {
    Trip create(int userid,
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
