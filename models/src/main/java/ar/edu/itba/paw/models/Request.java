package ar.edu.itba.paw.models;

import java.time.LocalDateTime;

public class Request {

    private final int requestId;

    private final int userId;
    private int acceptUserId;
    private final Number requestedWeight;
    private final Number requestedVolume;
    private final Number maxPrice;
    private final LocalDateTime minDepartureDate;
    private final LocalDateTime maxArrivalDate;
    private final String type;
    private final String origin;
    private final String destination;


    public Request(int requestId,
                   int userId,
                   Number requestedWeight,
                   Number requestedVolume,
                   LocalDateTime minDepartureDate,
                   LocalDateTime maxArrivalDate,
                   String origin,
                   String destination,
                   String type,
                   Number maxPrice,
                   int acceptUserId) {

        this.requestId = requestId;
        this.userId = userId;
        //Linked to User.userId
        this.requestedWeight=requestedWeight;
        this.requestedVolume = requestedVolume;
        this.maxPrice = maxPrice;
        this.minDepartureDate = minDepartureDate;
        this.maxArrivalDate = maxArrivalDate;
        this.type = type;
        this.origin = origin;
        this.destination = destination;
        this.acceptUserId = acceptUserId;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getUserId() {
        return userId;
    }

    public Number getRequestedVolume() {
        return requestedVolume;
    }

    public Number getMaxPrice() {
        return maxPrice;
    }

    public LocalDateTime getMinDepartureDate() {
        return minDepartureDate;
    }

    public LocalDateTime getMaxArrivalDate() {
        return maxArrivalDate;
    }

    public String getType() {
        return type;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setAcceptUserId(int acceptUserId) {
        this.acceptUserId=acceptUserId;
    }

    public int getAcceptUserId() {
        return acceptUserId;
    }

    public Number getRequestedWeight() {
        return requestedWeight;
    }
}

