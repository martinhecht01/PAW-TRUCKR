package ar.edu.itba.paw.models;

import java.time.LocalDateTime;
import java.util.Date;

public class Trip {
    private final int tripId;

    private final int userId; //Linked to User.userId
    private final int acceptUserId; //Linked to User.userId
    private final String licensePlate; //Linked to Truck.licensePlate
    private final Number availableWeight;
    private final Number availableVolume;
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
                int acceptUserId,
                Boolean sender_confirmation,
                Boolean receiver_confirmation,
                LocalDateTime confirmation_date)
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
        this.sender_confirmation = sender_confirmation;
        this.receiver_confirmation = receiver_confirmation;
        this.confirmation_date = confirmation_date;
    }

    public Boolean getSenderConfirmation() {
        return sender_confirmation;
    }

    public Boolean getReceiverConfirmation() {
        return receiver_confirmation;
    }

    public LocalDateTime getConfirmationDate() {
        return confirmation_date;
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

    public Boolean getSender_confirmation() {
        return sender_confirmation;
    }

    public Boolean getReceiver_confirmation() {
        return receiver_confirmation;
    }

    public LocalDateTime getConfirmation_date() {
        return confirmation_date;
    }

    public Number getPrice() {return price;}

}
