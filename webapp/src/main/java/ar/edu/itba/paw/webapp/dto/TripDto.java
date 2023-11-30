package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.*;

import javax.persistence.*;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

public class TripDto {

    private Integer tripId;

    private URI self;

    private User provider;


    private User trucker;


    private String licensePlate;


    private Integer weight;


    private Integer volume;


    private Timestamp departureDate;


    private Timestamp arrivalDate;


    private String origin;


    private String destination;


    private String type;


    private Integer price;

    private Boolean truckerConfirmation;

    private Boolean providerConfirmation;


    private Timestamp confirmationDate;


    private Image image;


    private URI proposals;


    private int proposalCount;


    private Proposal offer;


    private Review review;

    public static TripDto fromTrip(final UriInfo uri, final Trip trip){
        final TripDto dto = new TripDto();
        dto.self = uri.getAbsolutePathBuilder().path("/trips/").path(String.valueOf(trip.getTripId())).build();
        dto.tripId = trip.getTripId();
        dto.provider = trip.getProvider();
        dto.trucker = trip.getTrucker();
        dto.licensePlate = trip.getLicensePlate();
        dto.weight = trip.getWeight();
        dto.volume = trip.getVolume();
        dto.departureDate = Timestamp.valueOf(trip.getDepartureDate());
        dto.arrivalDate = Timestamp.valueOf(trip.getArrivalDate());
        dto.origin = trip.getOrigin();
        dto.destination = trip.getDestination();
        dto.type = trip.getType();
        dto.price = trip.getPrice();
        dto.truckerConfirmation = trip.getTruckerConfirmation();
        dto.providerConfirmation = trip.getProviderConfirmation();
        dto.confirmationDate = Timestamp.valueOf(trip.getConfirmationDate());
        dto.image = trip.getImage();
        dto.proposals = uri.getBaseUriBuilder().path("/proposals/").path(String.valueOf(trip.getTripId())).build();
        dto.proposalCount = trip.getProposalCount();
        dto.offer = trip.getOffer();
        dto.review = trip.getReview();
        return dto;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setImage(Image image) {
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

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public User getTrucker() {
        return trucker;
    }

    public void setTrucker(User trucker) {
        this.trucker = trucker;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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

    public Boolean getTruckerConfirmation() {
        return truckerConfirmation;
    }

    public void setTruckerConfirmation(Boolean truckerConfirmation) {
        this.truckerConfirmation = truckerConfirmation;
    }

    public Boolean getProviderConfirmation() {
        return providerConfirmation;
    }

    public void setProviderConfirmation(Boolean providerConfirmation) {
        this.providerConfirmation = providerConfirmation;
    }

    public Timestamp getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Timestamp confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public Image getImage() {
        return image;
    }

    public URI getProposals() {
        return proposals;
    }

    public void setProposals(URI proposals) {
        this.proposals = proposals;
    }

    public int getProposalCount() {
        return proposalCount;
    }

    public void setProposalCount(int proposalCount) {
        this.proposalCount = proposalCount;
    }

    public Proposal getOffer() {
        return offer;
    }

    public void setOffer(Proposal offer) {
        this.offer = offer;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
