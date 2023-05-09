package ar.edu.itba.paw.models;

import java.time.LocalDateTime;
import java.util.Date;

public class Trip {
    private final int tripId;

    private final Integer truckerId; //Linked to User.userId
    private final Integer providerId; //Linked to User.userId
    private final String licensePlate; //Linked to Truck.licensePlate
    private final Number weight;
    private final Number volume;
    private final LocalDateTime departureDate;
    private final LocalDateTime arrivalDate;
    private final String origin;
    private final String destination;
    private final String type;
    private final Number price;

    private final Boolean sender_confirmation;
    private final Boolean receiver_confirmation;

    private final LocalDateTime confirmation_date;

    public Trip(int tripId,
                Integer truckerId,
                Integer providerId,
                String licensePlate,
                Number weight,
                Number volume,
                LocalDateTime departureDate,
                LocalDateTime arrivalDate,
                String origin,
                String destination,
                String type,
                Number price,
                Boolean sender_confirmation,
                Boolean receiver_confirmation,
                LocalDateTime confirmation_date) {
        this.tripId = tripId;
        this.truckerId = truckerId;
        this.providerId = providerId;
        this.licensePlate = licensePlate;
        this.weight = weight;
        this.volume = volume;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.origin = origin;
        this.destination = destination;
        this.type = type;
        this.price = price;
        this.sender_confirmation = sender_confirmation;
        this.receiver_confirmation = receiver_confirmation;
        this.confirmation_date = confirmation_date;
    }

    public int getTripId() {
        return tripId;
    }

    public Integer getTruckerId() {
        return truckerId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Number getWeight() {
        return weight;
    }

    public Number getVolume() {
        return volume;
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

    public String getType() {
        return type;
    }

    public Number getPrice() {
        return price;
    }

    public Boolean getSender_confirmation() {
        return sender_confirmation;
    }

    public Boolean getReceiver_confirmation() {
        return receiver_confirmation;
    }

    public LocalDateTime getConfirmation_date() {
        return confirmation_date;
    }
}
