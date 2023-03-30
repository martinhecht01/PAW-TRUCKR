package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesServices.TripService;
import ar.edu.itba.paw.models.Trip;

import java.util.Date;

public class TripServiceImpl implements TripService {

    @Override
    public Trip createTrip(String licensePlate, Number availableWeight, Number availableVolume, Date departureDate, Date arrivalDate, String origin, String destination) {
        return new Trip(licensePlate, availableWeight, availableVolume, departureDate, arrivalDate, origin, destination);
    }
}
