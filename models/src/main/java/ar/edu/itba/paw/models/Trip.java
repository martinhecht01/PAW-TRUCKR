package ar.edu.itba.paw.models;

import java.util.Date;

public class Trip {
    private final String licensePlate; //Linked to Truck.licensePlate
    private final Number availableWeight;
    private final Number availableVolume;
    private final Date departureDate;
    private final Date arrivalDate;
    private final String origin;
    private final String destination;

    public Trip(String licensePlate, Number availableWeight, Number availableVolume, Date departureDate, Date arrivalDate, String origin, String destination) {
        this.licensePlate = licensePlate;
        this.availableWeight = availableWeight;
        this.availableVolume = availableVolume;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.origin = origin;
        this.destination = destination;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Number getAvailableWeight() {
        return availableWeight;
    }

    public Number getAvailableVolume() {
        return availableVolume;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }
}
