package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Trip;

import java.util.Date;

public interface TripDao {
    public Trip create(String userid,
                       String licensePlate,
                       int availableWeight,
                       int availableVolume,
                       Date departureDate,
                       Date arrivalDate,
                       String origin,
                       String destination,
                       String type);

}
