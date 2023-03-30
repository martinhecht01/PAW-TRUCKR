package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Trip;

import java.util.Date;

public interface TripService {
    Trip createTrip(String licensePlate, Number availableWeight, Number availableVolume, Date departureDate, Date arrivalDate, String origin, String destination);
}
