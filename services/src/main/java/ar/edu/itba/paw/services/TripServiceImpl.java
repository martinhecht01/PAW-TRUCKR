package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesServices.TripService;
import ar.edu.itba.paw.models.Trip;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TripServiceImpl implements TripService {

    @Override
    public Trip createTrip(int tripId, String userId, String licensePlate, int availableWeight, int availableVolume, Date departureDate, Date arrivalDate, String origin, String destination, String type) {
        return new Trip(tripId, userId, licensePlate, availableWeight, availableVolume, departureDate, arrivalDate, origin, destination, type);
    }
}
