package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.*;
import org.glassfish.jersey.server.Uri;

import javax.persistence.*;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.sql.Timestamp;

public class TripDto {

    private Integer tripId;
    private URI self;
    private URI provider;
    private URI trucker;
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
    private URI image;
    private URI proposals;
    private int proposalCount;
    private Proposal offer;
    private boolean truckerSubmittedHisReview;
    private boolean providerSubmittedHisReview;

    public static TripDto fromTrip(final UriInfo uri, final Trip trip){
        TripDto dto = new TripDto();
        dto.self = uri.getBaseUriBuilder().path("/trips/").path(String.valueOf(trip.getTripId())).build();
        dto.tripId = trip.getTripId();
        if(trip.getProvider() != null)
            dto.provider = uri.getBaseUriBuilder().path("/users/").path(String.valueOf(trip.getProvider().getUserId())).build();
        if(trip.getTrucker() != null)
            dto.trucker = uri.getBaseUriBuilder().path("/users/").path(String.valueOf(trip.getTrucker().getUserId())).build();
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
        if(trip.getConfirmationDate() != null)
            dto.confirmationDate = Timestamp.valueOf(trip.getConfirmationDate());
        else dto.confirmationDate = null;
        dto.image = uri.getBaseUriBuilder().path("/images/").path(String.valueOf(trip.getImage().getImageid())).build();

        //TODO revisar
        dto.proposals = uri.getBaseUriBuilder().path("/proposals/").path(String.valueOf(trip.getTripId())).build();

        dto.truckerSubmittedHisReview = trip.getTruckerSubmittedHisReview();
        dto.providerSubmittedHisReview = trip.getProviderSubmittedHisReview();

        dto.proposalCount = trip.getProposalCount();
        dto.offer = trip.getOffer();

        return dto;
    }

    public String getLicensePlate() {
        return licensePlate;
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

    public URI getProvider() {
        return provider;
    }

    public void setProvider(URI provider) {
        this.provider = provider;
    }

    public URI getTrucker() {
        return trucker;
    }

    public void setTrucker(URI trucker) {
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

    public URI getImage() {
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

    public boolean getTruckerSubmittedHisReview() {
        return truckerSubmittedHisReview;
    }

    public void setTruckerSubmittedHisReview(boolean truckerSubmittedHisReview) {
        this.truckerSubmittedHisReview = truckerSubmittedHisReview;
    }

    public boolean getProviderSubmittedHisReview() {
        return providerSubmittedHisReview;
    }

    public void setProviderSubmittedHisReview(boolean providerSubmittedHisReview) {
        this.providerSubmittedHisReview = providerSubmittedHisReview;
    }
}
