package ar.edu.itba.paw.models;

import java.time.LocalDateTime;
import java.util.Date;

public class Trip {
    private final int tripId;

    private final int userId; //Linked to User.userId
    private int acceptUserId; //Linked to User.userId
    private final String licensePlate; //Linked to Truck.licensePlate
    private final Number availableWeight;
    private final Number availableVolume;
    private final LocalDateTime departureDate;
    private final LocalDateTime arrivalDate;
    private final String origin;
    private final String destination;
    private final String type;
    private final int price;

    public Trip(int tripId,
                int userId,
                String licensePlate,
                int availableWeight,
                int availableVolume,
                LocalDateTime departureDate,
                LocalDateTime arrivalDate,
                String origin,
                String destination,
                String type,
                int price,
                int acceptUserId)
    {

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
        this.acceptUserId = acceptUserId;
        this.price = price;
    }


    public void setAcceptUserId(int acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public int getTripId() {
        return tripId;
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

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getAcceptUserId() {
        return acceptUserId;
    }

    public int getPrice() {
        return price;
    }
}
