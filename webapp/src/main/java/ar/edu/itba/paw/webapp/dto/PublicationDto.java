package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.sql.Timestamp;

public class PublicationDto {

    private Integer tripId;
    private URI self;
    private URI creator;
    private Integer weight;
    private Integer volume;
    private Timestamp departureDate;
    private Timestamp arrivalDate;
    private String origin;
    private String destination;
    private String type;
    private Integer price;
    private URI image;


    public static PublicationDto fromTrip(final UriInfo uri, final Trip trip){
        PublicationDto dto = new PublicationDto();
        dto.self = uri.getBaseUriBuilder().path("/trips/").path(String.valueOf(trip.getTripId())).build();
        dto.tripId = trip.getTripId();
        if(trip.getProvider() != null)
            dto.creator = uri.getBaseUriBuilder().path("/users/").path(String.valueOf(trip.getProvider().getUserId())).build();
        if(trip.getTrucker() != null)
            dto.creator = uri.getBaseUriBuilder().path("/users/").path(String.valueOf(trip.getTrucker().getUserId())).build();
        dto.departureDate = Timestamp.valueOf(trip.getDepartureDate());
        dto.arrivalDate = Timestamp.valueOf(trip.getArrivalDate());
        dto.weight = trip.getWeight();
        dto.volume = trip.getVolume();
        dto.origin = trip.getOrigin();
        dto.destination = trip.getDestination();
        dto.type = trip.getType();
        dto.price = trip.getPrice();
        dto.image = uri.getBaseUriBuilder().path("/images/").path(String.valueOf(trip.getImage().getImageid())).build();
        return dto;
    }


    public void setImage(URI image) {
        this.image = image;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getCreator() {
        return creator;
    }

    public void setCreator(URI creator) {
        this.creator = creator;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Timestamp getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Timestamp departureDate) {
        this.departureDate = departureDate;
    }

    public Timestamp getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Timestamp arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public URI getImage() {
        return image;
    }
}
