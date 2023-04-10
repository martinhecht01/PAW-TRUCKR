package ar.edu.itba.paw.models;

import java.util.Date;

public class Trip {
    private final int tripId;

    private final int userId; //Linked to User.userId
    private final String licensePlate; //Linked to Truck.licensePlate
    private final Number availableWeight;
    private final Number availableVolume;
    private final Date departureDate;
    private final Date arrivalDate;
    private final String origin;
    private final String destination;

    private final String type;

    public Trip(int tripId,
                int userId,
                String licensePlate,
                int availableWeight,
                int availableVolume,
                Date departureDate,
                Date arrivalDate,
                String origin,
                String destination,
                String type) {

        this.userId = userId;
        this.licensePlate = licensePlate;
        this.availableWeight = availableWeight;
        this.availableVolume = availableVolume;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.origin = origin;
        this.destination = destination;
        this.type = type;
        this.tripId = tripId;
    }

    public int getUserId() {
        return userId;
    }

    public String getType() {
        return type;
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
